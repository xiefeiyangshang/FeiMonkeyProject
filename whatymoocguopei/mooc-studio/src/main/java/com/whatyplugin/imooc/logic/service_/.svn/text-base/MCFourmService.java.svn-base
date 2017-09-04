package com.whatyplugin.imooc.logic.service_;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.model.MCForumModel;
import com.whatyplugin.imooc.logic.model.MCThemeForumreply;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCFourmService extends MCBaseService implements MCFourmServiceInterface {

	@Override
	public void sendConmentToRepyly(String forumId, String fromUserId, String toforumId) {
	}

	@Override
	public void getForumListBycourseId(String courseId, int curPage, int pageSize, final MCAnalyzeBackBlock callBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_LIST_FORUM;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.courseId", courseId);
		map.put("page.pageSize", pageSize + "");
		map.put("page.curPage", curPage + "");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String responseData) {
				MCFourmService.this.analyzeDataWithResult(arg1, responseData, MCForumModel.class, callBack);
			}
		};

		MCNetwork.post(request, context);
	}

	@Override
	public void getRepylyListByNoteId(String courseId, String noteId, int curPage, int pageSize, String topicType, final MCAnalyzeBackBlock callBack,
			Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_FORUM_INFO;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.itemId", noteId);
		map.put("page.searchItem.topicType", topicType);
		map.put("page.pageSize", pageSize + "");
		map.put("page.curPage", curPage + "");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String responseData) {
				MCFourmService.this.analyzeNoticeListResult(arg1, responseData, MCThemeForumreply.class, callBack);
			}
		};

		MCNetwork.post(request, context);
	}

	@Override
	public void saveOrDeleteZan(String postId, String mainId, String opt, final MCAnalyzeBackBlock callBack, Context context) {

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_OR_DELETE_ZAN;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.postId", postId);
		map.put("entity.mainId", mainId);
		map.put("entity.opt", opt);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String responseData) {
			}
		};

		MCNetwork.post(request, context);

	}

	@Override
	public void sendComment(String postId, String mainId, String detail, MCAnalyzeBackBlock callBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_REPLY;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.postId", postId);
		map.put("entity.rePostId", mainId);
		map.put("entity.detail", detail);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String responseData) {
			}
		};

		MCNetwork.post(request, context);

	}

	@Override
	public void sendRepyly(String courseId, String itemId, String detail, final MCAnalyzeBackBlock callBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_POST_REPLY;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.courseId", courseId);
		map.put("entity.itemId", itemId);
		map.put("entity.detail", detail);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String responseData) {
				MCFourmService.this.analyzeDataWithResult(arg1, responseData, MCThemeForumreply.class, callBack);
			}
		};

		MCNetwork.post(request, context);
	}

}
