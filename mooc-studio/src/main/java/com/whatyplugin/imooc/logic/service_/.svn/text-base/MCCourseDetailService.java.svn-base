package com.whatyplugin.imooc.logic.service_;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCMyNoteModel;
import com.whatyplugin.imooc.logic.model.MCNoteModel;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCCourseDetailService extends MCBaseService implements
		MCCourseDetailServiceInterface {
	public MCCourseDetailService() {
		super();
	}

	/**
	 * 保存笔记
	 */
	public void getSendNote(String courseId, String desc, String thumbkey,
			int currentDuratoin, final MCAnalyzeBackBlock resultBack,
			Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_NOTE_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("opt", "0");// 0 保存 1 更新
		map.put("entity.courseId", courseId);
		map.put("entity.content", desc);
		// map.put("entity.isPublic", "");//公开标志：1公开 0私密，默认为公开
		// map.put("entity.isTRecommend", "");//教师推荐标志：0未推荐 1推荐，默认为未推荐
		String title = desc.trim();
		if(title.length()>10){
			title = title.substring(0, 10);
		}
		map.put("entity.title", title);//笔记名称，默认为“笔记”

		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCCourseDetailService.this.analyzeDataWithResult(result,
						responeData, MCNoteModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void updateNote(String courseId, String content, String noteId,
			String updateType, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_NOTE_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("opt", "1");// 0 保存 1 更新
		map.put("entity.courseId", courseId);
		map.put("entity.noteId", noteId);
		map.put("entity.content", content);
		map.put("entity.updateType", updateType);
		String title = content.trim();
		if (title.length() > 10) {
			title = title.substring(0, 10);
		}
		map.put("entity.title", title);// 笔记名称，不传的话默认为“笔记”
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCCourseDetailService.this.analyzeDataWithResult(result,
						responeData, MCMyNoteModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void delNote(String noteId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_NOTE_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("opt", "2");
		map.put("entity.noteId", noteId);
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCCourseDetailService.this.analyzeDataWithResult(result,
						responeData, MCMyNoteModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getNoteList(String courseId, int curPage, int pageSize,
			String type, String keyword, final MCAnalyzeBackBlock resultBack,
			Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_NOTE_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", String.valueOf(pageSize));
		map.put("page.searchItem.opt", type);// 筛选条件：0所有笔记、1教师推荐笔记、2我的笔记
		map.put("page.searchItem.courseId", courseId);
		if (!TextUtils.isEmpty(keyword))
			map.put("page.searchItem.keyTagWord", keyword);
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCCourseDetailService.this.analyzeNoteWithResult(result,
						responeData, MCMyNoteModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getMyNoteList(int curPage, int pageSize, String keyword,
			final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_MYNOTE_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", String.valueOf(pageSize));
		if (!TextUtils.isEmpty(keyword))
			map.put("page.searchItem.keyTagWord", keyword);
		else
			map.put("page.searchItem.keyTagWord", "");
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCCourseDetailService.this.analyzeNoteWithResult(result,
						responeData, MCCourseModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

}
