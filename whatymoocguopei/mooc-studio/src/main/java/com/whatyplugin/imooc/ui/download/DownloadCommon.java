package com.whatyplugin.imooc.ui.download;

import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;

import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

public class DownloadCommon {

	private static Context context;
	/**
	 * 视频路径切换的时候把路径内文件复制到目标卡里面
	 * 
	 * @param path
	 */
	public static void operateAfterSavePathChanged(final String path, final Context mContext) {
		String orgPath = Contants.VIDEO_PATH;
		context = mContext;
		String targetPath = path + Contants.BASE_FOLDER_PATH + "/video";
		if (!targetPath.equals(orgPath)) {
			FileUtils.filelist.clear();// 清理后再用
			FileUtils.getFiles(orgPath, "");

			if (FileUtils.filelist.size() > 0) {
				String info = FileUtils.filelist.size() + "个文件需要转移，请稍后……";
				
				//检测目标空间是剩余容量是否够
				boolean haveSpace = FileUtils.checkSpace(FileUtils.filelist, path, mContext);
				if (!haveSpace) {
					MCToast.show(mContext, "目标卡空间不足，请删除部分缓存内容再试！");
					return;
				}
				MCDownloadService downloadService = new MCDownloadService();
//				final MCAlertDialog loading_dialog = new MCAlertDialog(mContext, MCAlertDialog.MB_LOADING, info);
				final MCCommonDialog loading_dialog = new MCCommonDialog(null,info,R.layout.loading_dialog);
				downloadService.removeVideoToNewDisk(FileUtils.filelist, orgPath, targetPath, true, loading_dialog.getContent(), new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
						loading_dialog.dismiss();

						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							MCToast.show(mContext, "已下载的文件已转移成功， 切换缓存目录完成！");
							FileUtils.filelist.clear();// 卸磨杀驴
							MCSaveData.saveDownloadSDcardPath(path, MoocApplication.getInstance());
							Contants.updateVideoPath();
						} else {
							MCToast.show(mContext, "操作失败！");
						}
					}
				}, mContext);
				loading_dialog.setCancelable(false);
				loading_dialog.show(getFragmentTransaction(),"tag");
			} else {
				MCToast.show(mContext, "切换成功！");
				MCSaveData.saveDownloadSDcardPath(path, MoocApplication.getInstance());
				Contants.updateVideoPath();
			}
		}
	}
	public static FragmentTransaction getFragmentTransaction(){
		Activity ac = (Activity)context;
		FragmentTransaction ft = ac.getFragmentManager().beginTransaction();
		return ft;
	}

}
