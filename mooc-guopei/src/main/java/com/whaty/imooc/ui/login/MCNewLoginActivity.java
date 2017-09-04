package com.whaty.imooc.ui.login;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.whaty.imooc.logic.model.ClassInfoModel;
import com.whaty.imooc.logic.model.MCMyUserModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.logic.service_.MCCommonService;
import com.whaty.imooc.ui.index.GPInitInformation;
import com.whaty.imooc.ui.index.MCMainActivity;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.manager.MCRunStartManager;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCSiteModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;
import ukplugin.co.senab.photoview.KeyboardRelativeLayout;
import ukplugin.co.senab.photoview.KeyboardRelativeLayout.onSizeChangedListener;

public class MCNewLoginActivity extends MCBaseActivity implements View.OnClickListener, MCAnalyzeBackBlock {
	private EditText et_NoKeyemail;
	private Dialog loading;
	private Context mContext;
	private Button bt_Login;
	private EditText et_Password;
	private KeyboardRelativeLayout rl_RootLogin;
	private Animation inAnimation;
	private Handler handler = new Handler();
	private ImageView im_HaveKeyImage;
	private ImageView iv_NoKeyImage;
	private String loginId;
	private GPPerformanceServiceInterface service;
	private boolean showKey = true;
	private static final String TAG = MCNewLoginActivity.class.getSimpleName();
	private MCCommonDialog listDialog = null;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private ProgressDialog progressDialog;
	private CheckBox mCheckBox;
	private SharedPreferences sp;
	private boolean isRemPwd;
	/**
	 * 在登录界面点击返回，直接退出桌面
	 * 
	 */

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && showKey) {
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.setFlags(268435456);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addCategory("android.intent.category.HOME");
			this.startActivity(intent);
			android.os.Process.killProcess(android.os.Process.myPid());

		}

		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		loading.dismiss();
		// 请求返回，不管结果怎么样，都把按钮制成可点击的红色
		bt_Login.setBackgroundResource(R.drawable.chat_on_bg);
		bt_Login.setEnabled(true);
		String resultDesc = result.getResultDesc();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			MCToast.show(this.mContext, this.mContext.getString(R.string.no_network_label));
		} else if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			String info = TextUtils.isEmpty(resultDesc) ? this.mContext.getString(R.string.login_failed) : resultDesc;
			MCToast.show(this.mContext, info);
		} else if (resultList.size() > 0) {

			final MCMyUserModel userModel = (MCMyUserModel) resultList.get(0);
			if (userModel.getSites() != null && userModel.getSites().size() == 1) {
				//判断是否属于 xngp 西南国培、2015ahdd 安徽电大、2015gkgp 国开国培
				if(userModel != null && !TextUtils.isEmpty(userModel.getSites().get(0).getCode())){
					String strCode = userModel.getSites().get(0).getCode();
					if("xngp".equals(strCode) ||"2015ahdd".equals(strCode)||"2015gkgp".equals(strCode)){

					}else{
						MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.siteCode_failed));
						return;
					}
				}
				MCToast.show(this.mContext, this.mContext.getString(R.string.login_completed));
				MCSaveData.saveUserInfo(userModel, this.mContext);
				SharedPreferences.Editor edit = sp.edit();
				edit.putBoolean("isRemPwd", mCheckBox.isChecked());
				edit.putString("name", loginId);
				edit.putString("pwd", password_text);
				edit.commit();
				// 登录成功后重新初始化站点域名
				Const.SITE_LOCAL_URL = userModel.getLearSpaceAddress();
				GPInitInformation.initPluginParams(MCNewLoginActivity.this);
				MCSaveData.saveUserInfo(userModel, mContext);
				service.getProjectId(this, loginId, new MCAnalyzeBackBlock<ClassInfoModel>() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result, List<ClassInfoModel> resultList) {

						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							if (resultList.size() < 0) {
								MCToast.show(MCNewLoginActivity.this, "班级获取失败，请联系管理员");
								return;
							}
							ClassInfoModel classInfoModel =	resultList.get(0);
							if(classInfoModel.isExpiratonTime()){
								MCToast.show(getApplicationContext(), "班级不在时间范围内");
								return;
							}

							SharedClassInfo.saveClassInfo(classInfoModel);
							Intent intent = new Intent(MCNewLoginActivity.this, MCMainActivity.class);
							startActivity(intent);
							finish();

							// 如果新人登陆就去刷新
							sendBroadcast(new Intent(GPContants.REFESHHANDPHONE));
							sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
						}

					}
				});
				return;
			}else if (userModel.getSites() != null && userModel.getSites().size() > 1) {
				MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.siteCode_failed));
//				showChoiceSiteDialog(userModel);
			}

		} else {
			MCToast.show(this.mContext, this.mContext.getString(R.string.login_failed));
			return;
		}
	}
	private void showChoiceSiteDialog(MCMyUserModel mcFullUserModel) {
	/*	View v = this.findViewById(R.id.login);
		Resources resources = mContext.getResources();
		Drawable d = resources.getDrawable(R.drawable.sliding_title_backgroud_color);
		v.setBackground(d);
		v.setClickable(true);*/
		final List<MCSiteModel> mcSiteModelList = mcFullUserModel.getSites();
		final List<Map<String, String>> listMapData = new ArrayList<Map<String, String>>();
		final String key = "SITE-KEY";
		for (MCSiteModel model : mcSiteModelList) {
			Map<String, String> item = new HashMap<String, String>();
			item.put(key, model.getName());
			listMapData.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, listMapData, R.layout.listview_dialog_textview_items, new String[]{key}, new int[]{R.id.onlytextview});
		listDialog = new MCCommonDialog("选择站点", R.layout.listview_dialog, adapter);
		listDialog.show(createDialog.getFragmentTransaction(this), key);
		Handler myHandler = new Handler();
		myHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listDialog.setItemListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						listDialog.dismiss();
						MCSiteModel model = mcSiteModelList.get(position);
						MCLog.d(TAG, "选择的站点是" + model.getName());
						loginBySiteCode(model);
					}
				});
			}
		}, 200);
        /*listDialog.listener = new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog1.dismiss();
				MCSiteModel model = mcSiteModelList.get(position);
				MCLog.d(TAG, "选择的站点是" + model.getName());
				loginBySiteCode(model.getCode());
			}
		};*/
		//老的
    /*	MCListViewDialog listDialog = new MCListViewDialog();
        listDialog.listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectDialog.dismiss();
				MCSiteModel model = mcSiteModelList.get(position);
				MCLog.d(TAG, "选择的站点是" + model.getName());
				loginBySiteCode(model.getCode());
			}
		};
		selectDialog = listDialog.showOnlytextViewDialog(this, "选择站点", listMapData, key);
		selectDialog.show();*/
	}

	private void loginBySiteCode(final MCSiteModel mcSiteModel) {
//        this.loading = MCLoadDialog.createLoginLoadingDialog(this.mContext);
		// this.loading.show();
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("登录中...");
		progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
		new MCCommonService().loginByWhaty(loginId, password_text, mcSiteModel.getCode(), MCServiceType.MC_SERVICE_TYPE_APP, MCRunStartManager.getChannel(this.mContext), MCPlatType.MC_PLAT_TYPE_ANDROID, MCAPPType.MC_APP_TYPE_IMOOC, MCResolution.getInstance(this.mContext).getDevScreenSize(), new MCAnalyzeBackBlock() {
			public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
//                loading.dismiss();
				progressDialog.dismiss();
				if (objs.size() > 0) {
					MCMyUserModel mcFullUserModel = (MCMyUserModel) objs.get(0);
					if (mcFullUserModel.getSites() != null && mcFullUserModel.getSites().size() == 1) {
						if (!result.getResultCode().equals(MCResultCode.MC_RESULT_CODE_SUCCESS)) {
							if (!TextUtils.isEmpty(result.getResultDesc().toString()))
								MCToast.show(MCNewLoginActivity.this.mContext, result.getResultDesc().toString());
							else
								MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.login_failed));
							return;
						}

						MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.login_completed));
						try {
							MCFullUserModel model = (MCFullUserModel) objs.get(0);
							MCSiteModel siteModel = mcSiteModel;
							if (siteModel.getWebDoMain() == null) {
								siteModel.setCode(mcFullUserModel.getSites().get(0).getCode());
								GPInitInformation.WEBTRN_TEST_LOCAL_URL = "http://" + mcFullUserModel.getSites().get(0).getWebDoMain();
							}
							if (!siteModel.getWebDoMain().startsWith("http://")) {
								GPInitInformation.WEBTRN_TEST_LOCAL_URL = "http://" + siteModel.getWebDoMain();
							}
							siteModel.setWebDoMain(GPInitInformation.WEBTRN_TEST_LOCAL_URL);
							model.setSiteModel(siteModel);
							MCSaveData.saveUserInfo(model, MCNewLoginActivity.this.mContext);
						} catch (Exception v0) {
						}
						if (result.getResultCode().equals(MCResultCode.MC_RESULT_CODE_SUCCESS)) {
							MCNewLoginActivity.this.sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
						}
						if (mcFullUserModel.getLearSpaceAddress() != null && !"".equals(mcFullUserModel.getLearSpaceAddress())) {
							Const.SITE_LOCAL_URL = mcFullUserModel.getLearSpaceAddress();
							GPInitInformation.initPluginParams(MCNewLoginActivity.this);
						}
         /*               WebtrnUserInfoService webtrnService = new WebtrnUserInfoService();
                        webtrnService.getUserInfo(new MCAnalyzeBackBlock() {
                            @Override
                            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                                if (resultList != null && resultList.size() > 0) {
                                    WebtrnUserInfoModel model = (WebtrnUserInfoModel) resultList.get(0);
                                    if (model != null && model.getAvatar() != null) {
                                        MCSaveData.saveUserAvatar(model.getAvatar(), mContext);
                                        MCSaveData.saveUserGender(model.getGender(), mContext);
                                        MCSaveData.saveUserSign(model.getSign(), mContext);
                                        MCSaveData.saveUserLocalAvatar(null, mContext);
                                        Intent intent = new Intent();
                                        intent.setAction(Contants.USER_UPDATE_HANDIMG_ACTION);
                                        sendBroadcast(intent);
                                    }
                                }
                            }
                        }, mContext);*/
						SharedPreferences.Editor edit = sp.edit();
						edit.putBoolean("isRemPwd", mCheckBox.isChecked());
						edit.putString("name", loginId);
						edit.putString("pwd", password_text);
						edit.commit();
						MCNewLoginActivity.this.finish();
						MCNewLoginActivity.this.sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
						startActivity(new Intent(MCNewLoginActivity.this, MCMainActivity.class));
						return;
					} else if (mcFullUserModel.getSites() != null && mcFullUserModel.getSites().size() > 1) {
						showChoiceSiteDialog(mcFullUserModel);
					}
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
					MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.login_failed));
					return;
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					MCToast.show(MCNewLoginActivity.this.mContext, MCNewLoginActivity.this.mContext.getString(R.string.no_network_label));
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					MCToast.show(MCNewLoginActivity.this.mContext, result.getResultDesc());
				}
			}
		}, this);
	}
	String password_text ;
	public void onClick(View v) {
		loginId = this.et_NoKeyemail.getText().toString();
		 password_text = this.et_Password.getText().toString();
		if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password_text)) {
			Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.email_null_error), Toast.LENGTH_LONG).show();
			return;
		}

		if (!MCNetwork.checkedNetwork(this.mContext)) {
			MCToast.show(this.mContext, this.mContext.getString(R.string.no_network_label));
			return;
		}
		// 真正的登录
		this.loading = MCLoadDialog.createLoginLoadingDialog(this.mContext);
		this.loading.show();
		// 点击 登陆之前变成灰色and变成不可点击状态
		this.bt_Login.setBackgroundResource(R.drawable.chat_off_bg);
		this.bt_Login.setEnabled(false);
		new MCCommonService().loginByWhaty(loginId, password_text, MCServiceType.MC_SERVICE_TYPE_APP, MCRunStartManager.getChannel(this.mContext),
				MCPlatType.MC_PLAT_TYPE_ANDROID, MCAPPType.MC_APP_TYPE_IMOOC, MCResolution.getInstance(this.mContext).getDevScreenSize(), this, this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 111 && data != null) {
			String email = data.getStringExtra("email");
			String password = data.getStringExtra("password");
			this.et_NoKeyemail.setText(email);
			this.et_Password.setText(password);
			this.bt_Login.performClick();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newlogin_layout);
		inAnimation = AnimationUtils.loadAnimation(this, R.anim.in_key_login);
		this.mContext = this;
		service = new GPPerformanceService();

		initView();
		initEvent();
	}

	private void initView() {
		this.im_HaveKeyImage = (ImageView) findViewById(R.id.have_key_image);
		this.iv_NoKeyImage = (ImageView) findViewById(R.id.no_key_image);
		this.et_NoKeyemail = (EditText) this.findViewById(R.id.email_edit);
		this.et_Password = (EditText) this.findViewById(R.id.password_edit);
		this.bt_Login = (Button) this.findViewById(R.id.login);
		this.rl_RootLogin = (KeyboardRelativeLayout) this.findViewById(R.id.root);

		sp = getSharedPreferences("usrinfo", Context.MODE_PRIVATE);
		isRemPwd = sp.getBoolean("isRemPwd", false);
		final String name = sp.getString("name", "");
		final String pwd = sp.getString("pwd", "");
		this.mCheckBox = (CheckBox) this.findViewById(R.id.login_recordpwd_check);
		mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(!isChecked){
					isRemPwd = false;
				}
				if (isRemPwd) {
					et_NoKeyemail.setText(name);
					et_Password.setText(pwd);
				} else {
					//					email.setText("");
					//					password.setText("");
				}

			}
		});
		mCheckBox.setChecked(isRemPwd);
		if (Const.SHOW_USERNAME) {
			this.et_NoKeyemail.setText(Const.USERNAME);
			this.et_Password.setText(Const.PASSWORD);
		} else {
			String lastLoginId = MCUserDefaults.getUserDefaults(this, Contants.NETWORK).getString(Contants.LAST_LOGIN_ID);
			if (!TextUtils.isEmpty(lastLoginId)) {
				this.et_NoKeyemail.setText(lastLoginId);
			}
		}
	}

	private void initEvent() {
		this.bt_Login.setOnClickListener(this);
		rl_RootLogin.setOnSizeChangedListener(new onSizeChangedListener() {
			@Override
			public void onChanged(boolean showKeyboard) {
				showKey = showKeyboard;
				// 纯粹为了使用三元表达式减少代码行数
				int i = showKeyboard ? showOrHidden(im_HaveKeyImage, iv_NoKeyImage) : showOrHidden(iv_NoKeyImage, im_HaveKeyImage);
			}

		});

	}

	public int showOrHidden(final View showView, final View hiddenView) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				hiddenView.setVisibility(View.GONE);
				showView.setVisibility(View.VISIBLE);
				showView.startAnimation(inAnimation);
			}
		});

		return 1;
	}

}
