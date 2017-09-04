package com.whatyplugin.imooc.ui.setting;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUpgradeModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCSettingService;
import com.whatyplugin.imooc.logic.service_.MCSettingServiceInterface;
import com.whatyplugin.imooc.logic.upgrade.MCDownloadManager;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.view.UpdateDailogView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.dialog.MCCommonDialog.INetworkListener;
import com.whatyplugin.uikit.toast.MCToast;


public class CheckedUpgrade implements MCAnalyzeBackBlock, INetworkListener {
    private Dialog dialog;
    private Handler handler;
    private static CheckedUpgrade instance;
    private Context mContext;
    private MCSettingServiceInterface mSettingService;
    private MCDownloadManager manager;
    private int type;
    private MCUpgradeModel upgrade;
    private MCCreateDialog createDialog = new MCCreateDialog();
    private MCCommonDialog waitingDialog;
    private MCCommonDialog commonDialog;
    
    public CheckedUpgrade(){
    	super();
    }
    
    public CheckedUpgrade(Context context, Handler handler) {
        super();
        this.waitingDialog = null;
        this.mContext = context;
        this.handler = handler;
        this.manager = new MCDownloadManager(this.mContext,1);
        this.mSettingService = new MCSettingService();
    }
    
    /**
	 * 获取版本号
	 * 
	 * @return 当前的版本号
	 * @throws Exception
	 */
	public String getVersionName(Context context) throws Exception {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

    public void OnAnalyzeBackBlock(MCServiceResult result, List arg9) {
        UpdateDailogView updateDialogView;
        int v6 = R.style.NetworkDialogStyle;  // R.style.NetworkDialogStyle
        if(this.waitingDialog != null) {
            this.waitingDialog.dismiss();
        }
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            this.upgrade = (MCUpgradeModel) arg9.get(0);
            try {
                if (getVersionName(mContext).equals(upgrade.getAppVersion()) || getVersionName(mContext).compareTo(upgrade.getAppVersion()) > 0) {
					upgrade.setType(MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE);
				}else{
					upgrade.setType(MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE);
					if(upgrade.getEnforeUpdate() == 1){
						upgrade.setType(MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				upgrade.setType(MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE);
			}
            if(this.upgrade.getType() == MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE) {
                MCSaveData.saveIsUpdated(Boolean.valueOf(false), this.mContext);
            }
            else {
                MCSaveData.saveIsUpdated(Boolean.valueOf(true), this.mContext);
            }

            if(this.upgrade.getType() == MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE && this.type == 1) {
                Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.no_update_label), 1).show();
                return;
            }

            if(this.upgrade.getType() == MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE) {
            	createCommonDialog(this.upgrade.getContent(), R.layout.customdialog_layout,MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE, this.handler);
            	commonDialog.setNetworkListener(((INetworkListener)CheckedUpgrade.this));
                return;
            }

            if(this.upgrade.getType() != MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE) {
                return;
            }

            createCommonDialog(this.upgrade.getContent(), R.layout.customdialog_layout,MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE, this.handler);
            commonDialog.setNetworkListener(((INetworkListener)CheckedUpgrade.this));
        }
        else {
            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
                MCSaveData.saveIsUpdated(Boolean.valueOf(false), this.mContext);
                return;
            }

            if(result.isExposedToUser()) {
                return;
            }

            result.getResultCode();
        }
    }

    public void cancel() {
        this.dismissDialog();
    }

    public void checkedUpgrade(int type) {
    	
    	if(TextUtils.isEmpty(Const.DOWNURL)){
    		return;
    	}
        if(!MCNetwork.checkedNetwork(this.mContext)) {
            MCToast.show(this.mContext, this.mContext.getResources().getString(R.string.no_network_label));
            return;
        }

        this.type = type;
        try {
            if(this.manager != null && (this.manager.isDownloading())) {
                this.manager.showDialog();
                return;
            }

            PackageInfo v8 = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            if(type == 1) {
            	//正在加载的图标
            	waitingDialog = createDialog.createLoadingDialog((Activity)mContext, "正在检测更新",0);
            	waitingDialog.setCancelable(false);
            }

            this.mSettingService.checkedUpgrade(v8.versionName, v8.versionCode, 1, 2, this, this.mContext);
        }
        catch(Exception e) {
        }
    }

    private void dismissDialog() {
        /*if(this.dialog != null && (this.dialog.isShowing())) {
            this.dialog.dismiss();
            this.dialog = null;
        }*/
    	if(this.commonDialog!=null)
    		commonDialog.dismiss();
    }

    public static CheckedUpgrade getInstance(Context context, Handler handler) {
        if(CheckedUpgrade.instance == null) {
            CheckedUpgrade.instance = new CheckedUpgrade(context, handler);
        }
        return CheckedUpgrade.instance;
    }

    public void update(MCUpgradeType type) {
        if(this.manager != null && this.upgrade != null) {
        	this.dismissDialog();
            this.manager.setDownloadUrl(this.upgrade.getUrl());
            this.manager.startDownLoad(type, this.handler);
        }
    }
    public void createCommonDialog(String content,int resId,MCUpgradeType type, Handler handler){
		commonDialog = new MCCommonDialog("更新","升级",content,resId,type,handler);
		Activity ac = (Activity) mContext;
		FragmentTransaction ft = ac.getFragmentManager().beginTransaction();
    	commonDialog.show(ft, "update");
    	handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				commonDialog.setCommitText("升级");
				commonDialog.setCommitListener(new android.view.View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(commonDialog.getListener()!=null)
							commonDialog.getListener().update(MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE);
					}
				});
				commonDialog.setCancelListener(new android.view.View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(commonDialog.getListener() == null) {
						    return;
						}
						if(commonDialog.getType() == MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE) {
						    if(commonDialog.getHandler()!= null) {
						        commonDialog.getHandler().sendEmptyMessage(0);
						        return;
						    }
						    commonDialog.getListener().cancel();
						    return;
						}
						commonDialog.getListener().cancel();
					}
				});
			}
		}, 200);
    	return;
	}

	@Override
	public void backgrounder() {
		// TODO Auto-generated method stub
		
	}
}

