package com.whaty.imooc.ui.setting;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon.MsgColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.UserColumns;
import com.whatyplugin.imooc.logic.manager.MCManager;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCUserService;
import com.whatyplugin.imooc.logic.service_.MCUserServiceInterface;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.logic.utils.Utils;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;
import com.whatyplugin.imooc.ui.download.DownloadCommon;
import com.whatyplugin.imooc.ui.setting.CheckedUpgrade;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.imooc.ui.view.MCCommonAlertDialog;
import com.whatyplugin.imooc.ui.view.MCSwitchButton;
import com.whatyplugin.imooc.ui.view.SdCardFolderDailogView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

import cn.com.whatyguopei.mooc.R;

public class MCSettingFragment extends MCBaseV4Fragment implements OnClickListener {
	private MCCreateDialog createDialog = new MCCreateDialog();

	// 清理缓存用的
	class ClearTask extends AsyncTask {
		// private MCAlertDialog waitingDialog;
		MCCommonDialog waitingDialog;

		ClearTask(MCSettingFragment arg1) {
			super();
		}

		protected Integer doInBackground() {
			FileUtils.delFolder(Contants.APK_PATH);
			FileUtils.delFolder(Contants.IMAGE_PATH);
			try {
				MCSettingFragment.this.filesize = FileUtils.FormetFileSize(FileUtils.getFileSize(Contants.APK_PATH)
						+ FileUtils.getFileSize(Contants.IMAGE_PATH));
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		protected Object doInBackground(Object... arg0) {
			return this.doInBackground();
		}


		protected void onPostExecute(Integer result) {
			if (waitingDialog != null) {
				waitingDialog.dismiss();
			}
			MCToast.show(MCSettingFragment.this.getActivity(), "清理成功");
			MCSettingFragment.this.clear_size.setText(MCSettingFragment.this.filesize);
			super.onPostExecute(result);
		}

		protected void onPostExecute(Object arg1) {
			this.onPostExecute(((Integer) arg1));
		}

		protected void onPreExecute() {
			waitingDialog = createDialog.createLoadingDialog(MCSettingFragment.this.getActivity(), "正在清理缓存...",0);
			super.onPreExecute();
		}
	}

	private TextView clear_size;
	private String filesize;
	private Handler handler;
	private CircleImageView mHeadImageView;
	private TextView nickname;
	private BroadcastReceiver receiver;
	private TextView tv_logout;
	private String uid;
	private RelativeLayout update;
	private RelativeLayout usermessage;
	private MCCommonAlertDialog mCommonDialog;
	private MCUserServiceInterface userService;
	private TextView tvUpgrade;
	private View logout_view;
	private MCSwitchButton switch_view;
	private boolean mIsAllowNoWifi;
	private RelativeLayout sdcard;

	public MCSettingFragment() {
		super();
		this.filesize = "0B";
		this.uid = Contants.DEFAULT_UID;
		userService = new MCUserService();

		// 实时更新缓存容量和文件大小
		this.handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					MCSettingFragment.this.filesize = FileUtils.FormetFileSize(FileUtils.getFileSize(Contants.APK_PATH)
							+ FileUtils.getFileSize(Contants.IMAGE_PATH));
					MCSettingFragment.this.clear_size.setText(MCSettingFragment.this.filesize);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 10s一次的循环
				this.sendEmptyMessageDelayed(0, 10000);
				super.handleMessage(msg);
			}
		};
		this.receiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {

				String action =intent.getAction();
				if (action.equals(Contants.USER_LOGIN_ACTION)||action.equals(GPContants.REFESHHANDPHONE)) {
					MCSettingFragment.this.refreshAfterLogin();
				} else if (action.equals(Contants.USER_LOGOUT_ACTION)) {
					MCSettingFragment.this.refreshAfterLogout();
				} else if (action.equals(Contants.USER_UPDATE_HANDIMG_ACTION)) {
					// 广播修改头像显示
					MCSettingFragment.this.mHeadImageView.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra("path")));
				}
			}
		};
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 初始化view
		initView();

		// 初始化Event
		initEvent();

		// 初始化Data
		initData();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Contants.USER_LOGIN_ACTION);
		filter.addAction(Contants.USER_LOGOUT_ACTION);
		filter.addAction(Contants.SDCARD_STATUS_CHANGED);
		filter.addAction(Contants.USER_UPDATE_HANDIMG_ACTION);
		filter.addAction(GPContants.REFESHHANDPHONE);
		this.getActivity().registerReceiver(this.receiver, filter);
		this.handler.sendEmptyMessageDelayed(0, 10000);

	}

	private void initData() {
		this.clear_size.setText(this.filesize);
		try {
			this.filesize = FileUtils.FormetFileSize(FileUtils.getFileSize(Contants.APK_PATH) + FileUtils.getFileSize(Contants.IMAGE_PATH));
			this.clear_size.setText(this.filesize);
		} catch (Exception v2) {
			v2.printStackTrace();
		}

		try {
			this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception v11) {
		}
		if (this.uid.equals(Contants.DEFAULT_UID)) {
			this.refreshAfterLogout();
		} else {
			this.refreshAfterLogin();
		}
		int visibilyValue = MCSaveData.getIsUpdated(this.getActivity()) ? 0 : 4;
		this.tvUpgrade.setVisibility(visibilyValue);

	}

	private void initEvent() {
		this.getActivity().findViewById(R.id.suggest).setOnClickListener(this);
		this.getActivity().findViewById(R.id.about).setOnClickListener(this);
		this.getActivity().findViewById(R.id.clear).setOnClickListener(this);

		this.usermessage.setOnClickListener(this);
		this.logout_view.setOnClickListener(this);
		this.update.setOnClickListener(this);
		mIsAllowNoWifi = MCUserDefaults.getUserDefaults(this.getActivity(), Contants.NETWORK).getBoolean(Contants.NETWORK_SETTING);
		this.switch_view.setChecked(mIsAllowNoWifi);

		this.switch_view.setOnClickHandler(new MCSwitchButton.OnClickHandler() {

			@Override
			public boolean onClick() {
				if (!switch_view.isChecked()) {
					showAllowNoWifiDialog();
					return true;
				}
				return false;
			}
		});

		this.switch_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				MCUserDefaults.getUserDefaults(MCSettingFragment.this.getActivity(), Contants.NETWORK).putBoolean(Contants.NETWORK_SETTING, isChecked);
			}
		});
	}

	private void initView() {
		this.clear_size = (TextView) this.getActivity().findViewById(R.id.clear_size);
		this.usermessage = (RelativeLayout) this.getActivity().findViewById(R.id.usermessage_layout);
		this.nickname = (TextView) this.getActivity().findViewById(R.id.nickname_tv);
		this.mHeadImageView = (CircleImageView) this.getActivity().findViewById(R.id.headimage);
		this.tv_logout = (TextView) this.getActivity().findViewById(R.id.logout_label);
		this.tvUpgrade = (TextView) this.getActivity().findViewById(R.id.upgrade_tv);
		this.logout_view = this.getActivity().findViewById(R.id.logout);
		this.update = (RelativeLayout) this.getActivity().findViewById(R.id.update);
		this.switch_view = (MCSwitchButton) this.getActivity().findViewById(R.id.wifi_switch);
		this.sdcard = (RelativeLayout) this.getActivity().findViewById(R.id.sdcard);
		this.sdcard.setOnClickListener(this);
		if (FileUtils.isTwoSdcard(this.getActivity())) {
			this.sdcard.setVisibility(View.VISIBLE);
			this.getActivity().findViewById(R.id.line4).setVisibility(View.VISIBLE);
		} else {
			this.sdcard.setVisibility(View.GONE);
			this.getActivity().findViewById(R.id.line4).setVisibility(View.GONE);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		// 进入设置个人资料信息
		case R.id.usermessage_layout: {
			this.startActivity(new Intent(this.getActivity(), MCPersonInformationActivity.class));
			break;
		}
		// 清理缓存
		case R.id.clear: {
			mCommonDialog = new MCCommonAlertDialog(getActivity(), MCCommonAlertDialog.MB_CANCELOK, this.getString(R.string.clear_dialog_msg));
			mCommonDialog.setCancelable(false);
			mCommonDialog.setTitle(R.string.download_network_title);
			mCommonDialog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new ClearTask(MCSettingFragment.this).execute();
				}
			}, MCCommonAlertDialog.YES);
			mCommonDialog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mCommonDialog.dismiss();
				}
			}, MCCommonAlertDialog.NO);
			mCommonDialog.show();
			break;
		}
		case R.id.suggest: {
			this.startActivity(new Intent(this.getActivity(), SuggestActivity.class));
			break;
		}
		case R.id.update: {
			if (Utils.isFastDoubleClick()) {
				return;
			}

			CheckedUpgrade.getInstance(this.getActivity(), null).checkedUpgrade(1);
			break;
		}
		case R.id.about: {
			this.startActivity(new Intent(this.getActivity(), AboutMoocActivity.class));
			break;
		}
		case R.id.logout: {
			if (TextUtils.isEmpty(this.uid) || this.uid.equals(Contants.DEFAULT_UID)) {
				MCLoginProxy.loginInstance().login(this.getActivity(), null);
				// 广播清除 main中的fragment 防止第二个人再次登录出现问题
				return;
			}

			MCSaveData.clearUser(this.getActivity());
			SharedClassInfo.clear();

			// 向服务器请求退出 不然退出后在课程列表里面仍然会显示课程的在学状态
			this.userService.loginOut(this.getActivity());

			this.refreshAfterLogin();
			ContentResolver resolver = this.getActivity().getContentResolver();
			resolver.delete(MsgColumns.CONTENT_URI_MSG, null, null);
			resolver.delete(UserColumns.CONTENT_URI_USER, null, null);
			MCUserDefaults.getUserDefaults(this.getActivity(), Contants.FIRST_ENTER);
			MCUserDefaults.clearDate(this.getActivity());
			if (MCManager.mUnReadMsgNum != null) {
				MCManager.mUnReadMsgNum.clear();
			}

			this.getActivity().sendBroadcast(new Intent(Contants.USER_LOGOUT_ACTION));
			break;
		}
		case R.id.sdcard: {
			SdCardFolderDailogView view = new SdCardFolderDailogView(this.getActivity());
			final MCCommonAlertDialog dialog = new MCCommonAlertDialog(this.getActivity());
			view.setIExitListener(new SdCardFolderDailogView.IExitListener() {
				public void cancel() {
					dialog.dismiss();
				}

				public void save(String path) {
					dialog.dismiss();
					DownloadCommon.operateAfterSavePathChanged(path, MCSettingFragment.this.getActivity());
				}
			});
			dialog.setContentView(((View) view));
			dialog.show();
			break;
		}
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.setting_layout, null);
	}

	public void onDestroy() {
		this.getActivity().unregisterReceiver(this.receiver);
		this.handler.removeMessages(0);
		super.onDestroy();
	}

	private void refreshAfterLogin() {
		try {
			this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception e) {
			this.uid = Contants.DEFAULT_UID;
		}

		this.usermessage.setVisibility(View.VISIBLE);
		this.tv_logout.setText(this.getResources().getText(R.string.logout_label));
		this.nickname.setVisibility(View.VISIBLE);
		this.nickname.setText(MCSaveData.getUserInfo(Contants.NICKNAME, this.getActivity()).toString());
		this.mHeadImageView.setDefaultImageResId(R.drawable.set_user_default_img);
		this.mHeadImageView.setImageUrl(MCSaveData.getUserInfo(Contants.PIC, this.getActivity()).toString());
	}

	private void refreshAfterLogout() {
		try {
			this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception e) {
		}

		this.nickname.setVisibility(View.GONE);
		this.usermessage.setVisibility(View.GONE);
		this.tv_logout.setText(this.getResources().getText(R.string.login_label));
	}

	/**
	 * 展示无wifi下也 播放/缓存 视频的提示框
	 */
	private void showAllowNoWifiDialog() {
		mCommonDialog = new MCCommonAlertDialog(getActivity(), MCCommonAlertDialog.MB_CANCELOK, "温馨提示: 2G/3G/4G网络下载可能会导致超额流量,确定开启?");
		mCommonDialog.setCancelable(false);
		mCommonDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch_view.setChecked(true);
			}
		}, MCCommonAlertDialog.YES);
		mCommonDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCommonDialog.dismiss();
			}
		}, MCCommonAlertDialog.NO);
		mCommonDialog.show();
	}
}
