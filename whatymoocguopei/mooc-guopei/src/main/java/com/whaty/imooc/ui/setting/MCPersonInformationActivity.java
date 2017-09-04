package com.whaty.imooc.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.whaty.imooc.logic.model.WebtrnUserInfoModel;
import com.whaty.imooc.logic.service_.GPUserInfoService;
import com.whaty.imooc.ui.index.GPInitInformation;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

public class MCPersonInformationActivity extends MCBaseActivity implements OnClickListener, MCAnalyzeBackBlock {
	private CircleImageView Civ_handPhoto;
	private RelativeLayout Rela_hand;
	private RelativeLayout Rela_sign;
	private RelativeLayout Rela_sex;
	private Dialog dialog = null;
	private TextView tv_sign;
	private TextView tv_sex;
	private TextView tv_emile;
	// titleBar
	private TextView tv_title;
	private ImageView iv_back;
	private String newSign;
	private Bitmap newHandPhoto;
	private File fileHandPath = new File(HAND_IMAGE_PATH);

	private List<Map<String, String>> listMapData = new ArrayList<Map<String, String>>();
	private Map<String, String> item1 = new HashMap<String, String>();
	private Map<String, String> item2 = new HashMap<String, String>();
	// 常量
	public static String SETTINGMASE = "个人资料设置";
	public static String SEX = "sex";
	public static String SEX_TITLE = "性别";
	public static String SEX_BOY = "男";
	public static String SEX_GIRL = "女";
	public static String HAND = "hand";
	public static String HAND_TITLE = "修改头像";
	public static String HAND_IMAGE = "从手机相册选择";
	public static String HAND_CAMERA = "相机";
	public static String HAND_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/whaty/handimage";
	public static String photoName = "/temp.jpg";
	// 获取数据接口
	private GPUserInfoService service;
	private WebtrnUserInfoModel userInfo = null;
	private MCCommonDialog loadingDialog;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private MCCommonDialog listDialog;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_user_message);
		try {
			initView();
			addOnclickListener();
			this.service = new GPUserInfoService();
			getRequest();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取数据
	 * 
	 */
	private void getRequest() {
		this.service.getUserInfo(((MCAnalyzeBackBlock) this), this);
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		if (resultList.size() == 1) {
			userInfo = (WebtrnUserInfoModel) resultList.get(0);
			Civ_handPhoto.setImageUrl(userInfo.getAvatar());
			tv_emile.setText(userInfo.getTechProfession());// 所教
		}
		tv_sign.setText(userInfo.getSign());
		tv_sex.setText(userInfo.getGender());
	}

	@Override
	public void onClick(View v) {
		if (R.id.back == v.getId()) {
			// 返回
			this.finish();
			return;
		}
		if (userInfo == null) {
			return;
		}
		switch (v.getId()) {
		// 上传头像
		case R.id.relative_hand:
			if (!fileHandPath.exists())
				fileHandPath.mkdirs();
			settingHandPhoto();
			break;
		// 男女
		case R.id.relative_sex:
			settingSex();
			break;

		case R.id.relative_sign:
			settingSign();
			break;
		default:
			MCToast.show(getApplicationContext(), "您点击的无效！！！");
			break;
		}

	}

	/**
	 * 
	 * 初始化所有的View
	 */

	public void initView() {
		Rela_hand = (RelativeLayout) findViewById(R.id.relative_hand);
		Rela_sign = (RelativeLayout) findViewById(R.id.relative_sign);
		Rela_sex = (RelativeLayout) findViewById(R.id.relative_sex);
		Civ_handPhoto = (CircleImageView) findViewById(R.id.hand_image);
		tv_sign = (TextView) findViewById(R.id.sign_context);
		tv_sex = (TextView) findViewById(R.id.sex_context);
		tv_emile = (TextView) findViewById(R.id.emile_context);
		tv_title = (TextView) findViewById(R.id.title_label);
		tv_title.setText(SETTINGMASE);
		iv_back = (ImageView) findViewById(R.id.back);

	}

	public void addOnclickListener() {
		Rela_hand.setOnClickListener(this);
		Rela_sex.setOnClickListener(this);
		Rela_sign.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	/**
	 * 设置性别
	 * 
	 */
	public void settingSex() {
		listMapData.clear();
		item1.put(SEX, SEX_BOY);
		item2.put(SEX, SEX_GIRL);
		listMapData.add(item1);
		listMapData.add(item2);
		SimpleAdapter adapter = new SimpleAdapter(this, listMapData, R.layout.listview_dialog_textview_items, new String[] { SEX },
				new int[] { R.id.onlytextview });

		listDialog = new MCCommonDialog(SEX_TITLE, R.layout.listview_dialog, adapter);
		listDialog.show(createDialog.getFragmentTransaction(this), SEX_TITLE);
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {

				listDialog.setItemListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						listDialog.dismiss();
						userInfo.setGender(position == 0 ? SEX_BOY : SEX_GIRL);
						Map<String, Object> map = new HashMap<String, Object>();
						String gender = "0";
						if (userInfo.getGender().trim().equals("男"))
							gender = "1";
						map.put("gender", gender);
						service.SettingUserInfo(map, new MCAnalyzeBackBlock() {
							public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
								if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
									MCToast.show(MCPersonInformationActivity.this, "设置成功");
									MCSaveData.saveUserGender(userInfo.getGender(), MCPersonInformationActivity.this);
								} else
									MCToast.show(MCPersonInformationActivity.this, "设置失败");
							}

						}, MCPersonInformationActivity.this);
						tv_sex.setText(position == 0 ? SEX_BOY : SEX_GIRL);
					}
				});

			}
		}, 200);

	}

	/**
	 * 设置头像
	 * 
	 */

	public void settingHandPhoto() {

		listMapData.clear();
		item1.clear();
		item2.clear();
		item1.put(HAND, HAND_IMAGE);
		item2.put(HAND, HAND_CAMERA);
		listMapData.add(item1);
		listMapData.add(item2);
		SimpleAdapter adapter = new SimpleAdapter(this, listMapData, R.layout.listview_dialog_textview_items, new String[] { HAND },
				new int[] { R.id.onlytextview });
		listDialog = new MCCommonDialog(HAND_TITLE, R.layout.listview_dialog, adapter);
		listDialog.show(createDialog.getFragmentTransaction(this), HAND_TITLE);

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

				listDialog.setItemListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						listDialog.dismiss();
						if (position == 0) {

							Intent intent = new Intent(Intent.ACTION_PICK, null);

							intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(intent, 1);
						} else {

							Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							// 下面这句指定调用相机拍照后的照片存储的路径
							if (!fileHandPath.exists()) // 每使用一次就要检测一次是否存在
								fileHandPath.mkdirs();

							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(HAND_IMAGE_PATH, photoName)));
							startActivityForResult(intent, 2);

						}

					}
				});
			}
		}, 200);
	}

	/**
	 * 设置网名
	 * 
	 */

	public void settingSign() {
		Intent intent = new Intent(this, SettingSignActivity.class);
		intent.putExtra("oldSign", userInfo.getSign() != null ? userInfo.getSign() : "");
		startActivityForResult(intent, 4);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		// 如果是调用相机拍照时
		case 2:

			File temp = new File(HAND_IMAGE_PATH + photoName);
			startPhotoZoom(Uri.fromFile(temp));

			break;
		// 取得裁剪后的图片
		case 3:
			if (data != null) {
				setPicToView(data);
			}
			break;
		// 修改个性签名
		case 4:
			if (data != null) {
				newSign = data.getStringExtra("newSign");
				MCSaveData.saveUserSign(newSign, this);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sign", newSign);
				service.SettingUserInfo(map, new MCAnalyzeBackBlock() {
					public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
						System.out.println(result.getResultCode());
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							userInfo.setSign(newSign);
							tv_sign.setText(newSign);
							MCToast.show(MCPersonInformationActivity.this, "个性签名设置成功");
							MCSaveData.saveUserGender(userInfo.getSign(), MCPersonInformationActivity.this);
						} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
							MCToast.show(MCPersonInformationActivity.this, "请检查您的网络");
						} else {
							MCToast.show(MCPersonInformationActivity.this, "个性签名设置失败，请不要含有表情等字符");
						}
					}

				}, this);
			}
			break;

		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪实现方法
	 */
	public void startPhotoZoom(Uri uri) {
		// 开启本地的图片
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		intent.putExtra("crop", "false");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		FileOutputStream fileOut = null;
		if (extras != null) {
			newHandPhoto = extras.getParcelable("data");
			File filephone = new File(HAND_IMAGE_PATH);
			if (!filephone.exists()) {
				filephone.mkdirs();
			}
			File filw_phote = new File(HAND_IMAGE_PATH, photoName);
			// 判断是不是存在 存在就先删除文件
			if (filw_phote.exists())
				filw_phote.delete();

			try {
				// 存储到本地
				fileOut = new FileOutputStream(filw_phote);
				newHandPhoto.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
				loadingDialog = createDialog.createLoadingDialog(this, "亲，你的头像正在修改中，\n请耐心等待一下下~",0);
				Map<String,Object> parameter=new HashMap<String, Object>();
				parameter.put("url", GPInitInformation.SET_WEBTRN_USER_Avatar);
				parameter.put("phoneUrl",filw_phote.getAbsolutePath());
				service.settingAvatar(parameter, new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							service.getUserInfo(new MCAnalyzeBackBlock() {
								@Override
								public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
									loadingDialog.dismiss();
									if (resultList.size() == 1) {
										userInfo = (WebtrnUserInfoModel) resultList.get(0);
										// 发送广播，其他地方修改图片
										Civ_handPhoto.setImageBitmap(newHandPhoto);
										Intent handIntent = new Intent(Contants.USER_UPDATE_HANDIMG_ACTION);
										handIntent.putExtra("path", HAND_IMAGE_PATH + photoName);
										MCPersonInformationActivity.this.sendBroadcast(handIntent);

										// 修改全局变量中的头像路径
										MCUserDefaults.getUserDefaults(MCPersonInformationActivity.this, Contants.USERINFO_FILE).putString(Contants.PIC,
												userInfo.getAvatar());
										new MCCreateDialog().createOkDialog(MCPersonInformationActivity.this, "头像修改成功！");
									}
								}
							}, MCPersonInformationActivity.this);
						} else {
							loadingDialog.dismiss();
							new MCCreateDialog().createOkDialog(MCPersonInformationActivity.this, "附件上传失败~");
						}
					}

				}, this);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				{
					try {
						if (fileOut != null)
							fileOut.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
