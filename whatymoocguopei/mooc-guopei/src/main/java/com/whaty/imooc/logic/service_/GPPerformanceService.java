package com.whaty.imooc.logic.service_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.whaty.imooc.logic.model.ClassInfoModel;
import com.whaty.imooc.logic.model.GPAssessmentModel;
import com.whaty.imooc.logic.model.GPCourseMode;
import com.whaty.imooc.logic.model.GPHomeWorkModel;
import com.whaty.imooc.logic.model.GPLikeListModel;
import com.whaty.imooc.logic.model.GPNoticModel;
import com.whaty.imooc.logic.model.GPReplyBlogListMode;
import com.whaty.imooc.logic.model.GPScoreModel;
import com.whaty.imooc.logic.model.ThemeListModel;
import com.whaty.imooc.logic.model.WorkShopModel;
import com.whaty.imooc.logic.model.deleteUpdateModel;
import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whaty.imooc.utile.SharedPrefsUtil;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class GPPerformanceService extends MCCommonBaseService implements GPPerformanceServiceInterface {

	@Override
	public void getAssessmentAndCriteria(String projectID, final MCAnalyzeBackBlock mCAnalyzeBackBlock, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "scheme");
		map.put("page.searchItem.projectId", SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID).toString());
		map.put("page.curPage", "1");
		map.put("page.pageSize", "100");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2.toString());
				GPPerformanceService.this.GPanalyzeData(result, arg2, GPAssessmentModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getProjectId(Context context, String LoginId, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_PROJECT_ID;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", "1");
		map.put("page.pageSize", "100");
		map.put("page.searchItem.siteCode", GPRequestUrl.getInstance().getSiteCode());
		map.put("page.searchItem.loginId", LoginId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取projectID" + arg2);
				GPPerformanceService.this.GPanalyzeInfoData(result, arg2, ClassInfoModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void getHotAndNewBlog(Context context, int curPage, String ClassId, String queryId, String loginId, final Class Clazz,
			final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();

		map.put("page.searchItem.queryId", queryId);
		map.put("page.searchItem.projectId", SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID));
		map.put("page.curPage", curPage + "");
		map.put("page.pageSize", "10");
		map.put("page.searchItem.classId", GPRequestUrl.getInstance().getClassId());
		map.put("page.searchItem.loginId", loginId);
		map.put("page.searchItem.siteCode", SharedClassInfo.getKeyValue(GPContants.CODESITE));
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2);
				GPPerformanceService.this.GPanalyzeData(result, arg2, Clazz, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void getWorkShopList(Context context, String type, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "getGroupAndActivity");
		map.put("page.curPage", "1");
		map.put("page.pageSize", "100");
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());

		map.put("page.searchItem.type", type);
		map.put("page.searchItem.projectId", GPRequestUrl.getInstance().getProjectId());
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2);
				GPPerformanceService.this.GPanalyzeWorkShopData(result, arg2, WorkShopModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getThemeList(Context context, int curPage, String activityId, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "getThemeMessage");
		map.put("page.curPage", curPage + "");
		map.put("page.pageSize", "10");
		map.put("page.searchItem.activity", activityId);
		map.put("page.searchItem.showSql", "0");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2);
				GPPerformanceService.this.GPanalyzeData(result, arg2, ThemeListModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void getNoticList(Context context, int curPage, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "getAllBulletin");
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", "10");
		map.put("page.searchItem.siteCode",GPRequestUrl.getInstance().SITDECODE);
		map.put("page.searchItem.projectId", SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID));
		map.put("page.curPage", curPage + "");
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("page.searchItem.classId", GPRequestUrl.getInstance().getClassId());

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeData(result, arg2, GPNoticModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void setNoticAndOne(Context context, String noticId, String type, String viewCount, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().SAVE_NOTIC_READ_AND_ONE;
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("peBulletin.id", noticId);
		map.put("viewCount", viewCount);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("setNoticAndOne  " + arg2);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void setRead(Context context, String noticId, String type, MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().SAVE_NOTIC_READ;
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("peBulletin.id", noticId);
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("setRead " + arg2);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void uploadFiles(List<String> imgPaths, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().UPLOAD_IMAGE;
		List<NameValuePair> fileParams = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("opt", "1");// 1是图片 2是其他
		if (imgPaths != null && imgPaths.size() > 0) {
			fileParams = new ArrayList<NameValuePair>();
			for (int i = 0; i < imgPaths.size(); i++) {
				String path = imgPaths.get(i);
				fileParams.add(new BasicNameValuePair("file", path));
				map.put("fileType", "pic");
				map.put("rules", path.substring(path.lastIndexOf(".") + 1, path.length())); // 格式
				String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
				map.put("filename", fileName);
			}
			request.fileParams = fileParams;
		}
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				GPPerformanceService.this.analyzeDataWithResult(result, responeData, MCUploadModel.class, resultBack);
			}
		};
		MCNetwork.upload(request, context);
	}

	@Override
	public void updateViewCount(Context context, String blogId, String viewCount) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().UPDATE_VIEWCOUNT_BLOG;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", blogId);
		map.put("viewCount", viewCount);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("updateViewCount " + arg2);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void updateMyBlog(Context context, String title, String content, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().UPDATE_MYBLOG;
		Map<String, String> map = new HashMap<String, String>();
		map.put("article.title", title);
		map.put("article.content", content);
		map.put("article.isPublished", "true");
		map.put("article.commentable", "true");
		map.put("article.abstractContent", content.length() < 10 ? content : content.substring(0, 10));
		map.put("article.tags", "");
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2);
				GPPerformanceService.this.analyzeDataWithResult(result, arg2, deleteUpdateModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void saveMyBlog(Context context, String BlogId, String title, String content, String abstractContent, final MCAnalyzeBackBlock mCAnalyzeBackBlock) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().SAVE_MYBLOG;
		Map<String, String> map = new HashMap<String, String>();
		// 修改
		if (BlogId != null) {
			map.put("id", BlogId);
			request.requestUrl = GPRequestUrl.getInstance().UPDATE_MYBLOG;
		}

		map.put("article.title", title);
		map.put("article.content", content);
		map.put("article.isPublished", "true");
		map.put("article.commentable", "true");
		map.put("article.abstractContent", abstractContent);
		map.put("article.tags", "");
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("siteCode",GPRequestUrl.getInstance().getSiteCode());
		map.put("article.signId", GPRequestUrl.getInstance().getClassId());

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("获取的数据" + arg2);
				GPPerformanceService.this.analyzeDataWithResult(result, arg2, deleteUpdateModel.class, mCAnalyzeBackBlock);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void deleteMyBlog(Context context, String blogId, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().DELETE_MYBLOG;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", blogId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.analyzeDataWithResult(result, arg2, deleteUpdateModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void likeBlog(Context context, String blogId) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().UPDATE_BLOG_LIKE;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", blogId);
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("siteCode",GPRequestUrl.getInstance().getSiteCode());
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("likeBlog " + arg2);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getLikeList(Context context, String blogId, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();

		map.put("page.searchItem.blogId", blogId);
		map.put("page.curPage", "1");
		map.put("page.pageSize", "100");
		map.put("page.searchItem.queryId", "getBlogLikes");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeInfoData(result, arg2, GPLikeListModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void replyBlog(Context context, String blogId, String detail, final MCAnalyzeBackBlock resultBack) {

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().REPLY_BLOG;
		Map<String, String> map = new HashMap<String, String>();

		map.put("itemId", blogId);
		map.put("courseId", GPRequestUrl.getInstance().SITDECODE);
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("userType", "0");
		map.put("detail", detail);
		map.put("siteCode", GPRequestUrl.getInstance().getSiteCode());
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPUpdateOrDelete(result, arg2, deleteUpdateModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void getReplyBlogList(Context context, String blogId, int curPage, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().REPLY_BLOG_LIST;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", "10");
		map.put("page.searchItem.itemId", blogId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeBlogListData(result, arg2, GPReplyBlogListMode.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void rePeplyFollowUp(Context context, String postId, String rePostId, String detail, final MCAnalyzeBackBlock resultBack) {

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().REPLY_REPLY_BLOG;
		Map<String, String> map = new HashMap<String, String>();
		map.put("postId", postId);
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("userType", "0");
		map.put("detail", detail);
		map.put("siteCode", GPRequestUrl.getInstance().getSiteCode());
		if (rePostId != null) {
			map.put("rePostId", rePostId);
		}
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				if (resultBack != null) {
					GPPerformanceService.this.GPUpdateOrDelete(result, arg2, deleteUpdateModel.class, resultBack);
				}
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void likeUnlikeTheme(Context context, String postId, String mainId, String opt, MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().LIKE_UNLIKE_THEME;
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginId", MCSaveData.getUserInfo(Contants.USERID, MoocApplication.getInstance()).toString());
		map.put("siteCode", GPRequestUrl.getInstance().getSiteCode());
		map.put("mainId", mainId);
		map.put("postId", postId);
		map.put("opt", opt);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				System.out.println("likeUnlikeTheme " + arg2);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getThemeListReplyNum(Context context, String ids, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "getCommentByThemeId");
		// ids的格式是
		// 3c54ada1-f310-490c-9baa-00bc3d5f11ae','f45932d7-4aef-428e-89c7-6e7e21f5fbda
		map.put("page.searchItem.ids", ids);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeData(result, arg2, ThemeListModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void getHomeWorkList(Context context, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().HOMEWORKLIST;
		Map<String, String> map = new HashMap<String, String>();
		map.put("courseId", GPRequestUrl.getInstance().getHomeWorkCourseId());
		map.put("loginId", GPRequestUrl.getInstance().getLogId());

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeMoocData(result, arg2, GPHomeWorkModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getCourseList(Context context, String pageNum, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().COURSE_LIST;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.loginId", GPRequestUrl.getInstance().getLogId());
		map.put("page.searchItem.code", GPRequestUrl.getInstance().getSiteCode());
		map.put("page.searchItem.queryId", "allCourseList");
		map.put("page.searchItem.classId", GPRequestUrl.getInstance().getClassId());
		map.put("page.curPage", pageNum);
		map.put("page.searchItem.isCache", "0");
		map.put("page.pageSize", "10");

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeData(result, arg2, GPCourseMode.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

	@Override
	public void studentScore(Context context, final MCAnalyzeBackBlock resultBack) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPRequestUrl.getInstance().GET_ASSESSMENT_CRITERIA;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.queryId", "mobileXianchangScore");
		map.put("page.searchItem.siteCode", GPRequestUrl.getInstance().SITDECODE);
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());
		map.put("page.searchItem.classId", GPRequestUrl.getInstance().getClassId());

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String arg2) {
				GPPerformanceService.this.GPanalyzeData(result, arg2, GPScoreModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

}
