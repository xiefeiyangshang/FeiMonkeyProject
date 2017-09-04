package com.whaty.imooc.ui.workshop;

import java.util.List;

import android.os.Bundle;

import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.themeforum.SandReplyActivity;

public class GPSendReplyActivity extends SandReplyActivity {
	private GPPerformanceServiceInterface service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onCreate(savedInstanceState);

	}

	/**
	 * 重写上传图片的方法
	 * 这里虽然重写了  但是没有用到
	 */

	public void sendPic(List<String> pathList, MCAnalyzeBackBlock backBlockForUplodload) {
		this.service.uploadFiles(pathList, backBlockForUplodload, this);
	}

	/**
	 * 重写上传图片的方法
	 * 
	 */
	public void sendReply(String itemId, String conment, MCAnalyzeBackBlock sandSavePostReply) {
		service.replyBlog(this, itemId, conment, sandSavePostReply);
	}

	/**
	 * 不显示上传图片
	 * 
	 */
	public Boolean showPic() {
		return false;
	}

}
