package com.whaty.imooc.ui.index;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.umeng.analytics.MobclickAgent;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.manager.MCManager;
import com.whatyplugin.imooc.logic.service_.MCLearningRecordService;
import com.whatyplugin.imooc.logic.service_.MCLearningRecordServiceInterface;
import com.whatyplugin.imooc.ui.setting.CheckedUpgrade;
import com.whatyplugin.uikit.dialog.MCCommonDialog;

import cn.com.whatyguopei.mooc.R;

public class MCMainActivity extends MCFragmentChangeActivity {
	private Handler mHandler;
	// private Dialog dialog;
	private MCCommonDialog exitDialog;

	
	public MCMainActivity() {
		super(R.string.allcourse, new CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float percent = ((float) ((((double) percentOpen)) * 0.25 + 0.75));
				canvas.scale(percent, percent, ((float) (canvas.getWidth() / 2)), ((float) (canvas.getHeight() / 2)));
			}
		});

		this.mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0: {
					MCMainActivity.this.release();
					break;
				}
				}
			}
		};
	}

	public void onCreate(Bundle savedInstanceState) {

		CheckedUpgrade.getInstance(this, this.mHandler).checkedUpgrade(0);
		MCManager.initialize(this.getApplicationContext());

		if (MCNetwork.checkedNetwork(this)) {// 启动的时候有网络就上传日志
			MCLearningRecordServiceInterface service = new MCLearningRecordService();
			service.recordWhatyLog(this);
		}
		super.onCreate(savedInstanceState);

	}

	/**
	 * 再次返回退出的处理
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == 4) {
			if (!this.getSlidingMenu().isMenuShowing()) {
				this.toggle();
				return flag;
			}
			if (MCDownloadQueue.getInstance().getDownloadingTaskCount() == 0) {
				this.showExitDialog(R.layout.normal_exit_dialog);
			} else {
				this.showExitDialog(R.layout.exit_dialog_layout);
			}

			flag = false;
		} else {
			flag = super.onKeyUp(keyCode, event);
		}

		return flag;
	}

	private void release() {
		MobclickAgent.onKillProcess(this);
		this.saveUnReadNum();
		MCManager.destroy();

		this.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void saveUnReadNum() {
		if (MCManager.mUnReadMsgNum != null) {
			if (MCManager.mUnReadMsgNum.size() != 0) {
				int i;
				for (i = 0; i < MCManager.mUnReadMsgNum.size(); ++i) {
					MCUserDefaults.getUserDefaults(this, Contants.UNREAD_MSG).putInt(
							new StringBuilder(String.valueOf(MCManager.mUnReadMsgNum.keyAt(i))).toString(), MCManager.mUnReadMsgNum.valueAt(i));
				}
			} else {
				MCUserDefaults.getUserDefaults(this, Contants.UNREAD_MSG);
				MCUserDefaults.clearDate(this);
			}
		}
	}

	private void showExitDialog(final int layoutId) {
		createCommonDialog(null, R.layout.normal_exit_dialog);
	}

	public void createCommonDialog(String content, int resId) {
		exitDialog = new MCCommonDialog("退出", content, resId);
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		exitDialog.show(ft, "退出");
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				exitDialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						exitDialog.dismiss();
						MCMainActivity.this.release();
					}
				});
			}
		}, 200);
	}

}
