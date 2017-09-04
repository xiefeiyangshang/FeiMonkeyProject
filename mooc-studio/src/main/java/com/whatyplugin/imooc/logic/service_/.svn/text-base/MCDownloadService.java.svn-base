package com.whatyplugin.imooc.logic.service_;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.widget.TextView;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon.DownloadColumns;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.utils.FileUtils;

public class MCDownloadService implements MCDownloadServiceInterface {
    private static String TAG;

    static {
        MCDownloadService.TAG = MCDownloadService.class.getSimpleName();
    }

    public MCDownloadService() {
        super();
    }

	@Override
	public void getResourceBySectionId(String sectionId, MCAnalyzeBackBlock callBack,
			Context context) {
		String[] v4 = null;
        Cursor cursor =null;
        List<MCDownloadVideoNode> list = new ArrayList<MCDownloadVideoNode>();
        try {
        	cursor =  context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects, 
        			"resourceSection=" + "'" + sectionId +"' and " + "type='" + MCDownloadNodeType.MC_RESOURCE_TYPE.value() +"'", v4, null);
            while(cursor.moveToNext()) {
            	  MCDownloadVideoNode node = new MCDownloadVideoNode(context);
                  node.setCourseId(cursor.getString(cursor.getColumnIndex("courseId")));
                  node.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                  node.setCourseImageUrl(cursor.getString(cursor.getColumnIndex("courseImageUrl")));
                  node.setChapter_seq(cursor.getInt(cursor.getColumnIndex("chapter_seq")));
                  node.setSection_seq(cursor.getInt(cursor.getColumnIndex("section_seq")));
                  node.setSectionId(cursor.getString(cursor.getColumnIndex("sectionId")));
                  node.setSectionName(cursor.getString(cursor.getColumnIndex("sectionName")));
                  node.setResourceSection(cursor.getString(cursor.getColumnIndex("resourceSection")));
                  node.downloadUrl = cursor.getString(cursor.getColumnIndex("downloadUrl"));
                  node.filename = cursor.getString(cursor.getColumnIndex("filename"));
                  node.setNodeType(MCDownloadNodeType.MC_RESOURCE_TYPE);
                  node.fileSize = cursor.getLong(cursor.getColumnIndex("fileSize"));
                  node.downloadSize = cursor.getLong(cursor.getColumnIndex("downloadSize"));
                  list.add(node);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null), list);
            return;
        }finally{
        	if(cursor != null) {
                cursor.close();
            }
        }
        
        if(list.size() > 0) {
            callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), list);
        }else{
        	callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, null), list);
        }
        return;
	}
	
    /**
     * 某一课程所有节点占用总空间
     */
	public void getTotalSizeByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock resultBack, Context context) {
		Cursor cursor = context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects,
				createSqlByType(downloadNodeType) + " and courseId=" + "'" + courseId + "'", null, null);
		List<Long> retList = new ArrayList<Long>();
		long result = 0;
		try {
			while (cursor.moveToNext()) {

				String typeStr = cursor.getString(cursor.getColumnIndex("type"));
				String sectionId = cursor.getString(cursor.getColumnIndex("sectionId"));
				int type = Integer.valueOf(typeStr);
				long downloadSize = cursor.getLong(cursor.getColumnIndex("downloadSize"));
				if (type == MCDownloadNodeType.MC_SFP_TYPE.value() && sectionId.endsWith(Contants.SFP_SUFFIX)) {
					result += downloadSize;
				} else if(type != MCDownloadNodeType.MC_SFP_TYPE.value()){
					result += downloadSize;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retList.add(result);
			resultBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null), retList);
			return;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		retList.add(result);

		if (retList.size() > 0) {
			resultBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), retList);
		} else {
			resultBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, null), retList);
		}
		return;
	}

    public void getAllDownloadCourse(MCDownloadNodeType downloadNodeType, MCAnalyzeBackBlock callBack, Context context) {
        String[] v4 = null;
        if(context == null) {
            return;
        }

        Cursor cursor = null;
        List<MCDownloadVideoNode> list = new ArrayList<MCDownloadVideoNode>();
        try {
        	String condition = "";
        	if(downloadNodeType!=null){
        		if(downloadNodeType==MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE){
        			condition = createSqlByType(downloadNodeType) + " group by courseId";
        		}else{
        			condition = "type='" + downloadNodeType.value() +"'" + " group by courseId";
        		}
        	}else{
        		condition = " 1=1 group by courseId";
        	}
        	cursor = context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects, condition, v4, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					MCDownloadVideoNode node = new MCDownloadVideoNode(context);
					node.setCourseId(cursor.getString(cursor.getColumnIndex("courseId")));
					node.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
					node.setCourseImageUrl(cursor.getString(cursor.getColumnIndex("courseImageUrl")));
					node.setChapter_seq(cursor.getInt(cursor.getColumnIndex("chapter_seq")));
					node.setSection_seq(cursor.getInt(cursor.getColumnIndex("section_seq")));
					node.setSectionId(cursor.getString(cursor.getColumnIndex("sectionId")));
					node.setSectionName(cursor.getString(cursor.getColumnIndex("sectionName")));
					String type = cursor.getString(cursor.getColumnIndex("type"));
					node.setNodeType(MCDownloadNodeType.initWithString(type));
					list.add(node);
				}
			}
        } catch(Exception v7) {
        	 v7.printStackTrace();
             callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null), list);
             if(cursor == null) {
                 return;
             }
        }finally{
	        if(cursor != null) {
	            cursor.close();
	        }
        }

        if(list.size() > 0) {
            callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), list);
        }else{
        	callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, null), list);
        }
        return;
    }


    /**
     * 节点数量
     */
	public void getSectionCountByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock resultBack, Context context) {
		Cursor cursor = null;
		List<Integer> countList = new ArrayList<Integer>();
		int count = 0;
		try {
			cursor = context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects,
					createSqlByType(downloadNodeType) + " and courseId=" + "'" + courseId + "'", null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					String typeStr = cursor.getString(cursor.getColumnIndex("type"));
					int type = Integer.valueOf(typeStr);
					if (MCDownloadNodeType.MC_SFP_TYPE.value() == type) {
						String sectionId = cursor.getString(cursor.getColumnIndex("sectionId"));
						if (sectionId != null && sectionId.endsWith(Contants.SFP_SUFFIX)) {
							count++;
						}
					} else {
						// 不是下载完成的
						if (cursor.getLong(cursor.getColumnIndex("downloadSize")) != cursor.getLong(cursor.getColumnIndex("fileSize"))) {
							count++;
							continue;
						}

						// SD卡上存在
						if (FileUtils.isFileExistsInPhoneOrSdcard(cursor.getString(cursor.getColumnIndex("filename")), context)) {
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null), countList);
			return;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		countList.add(count);
		resultBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), countList);
		return;
	}

    public void getAllDownloadSectionByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock callBack, Context context) {
        String[] v4 = null;
        Cursor cursor =null;
        List<MCDownloadVideoNode> list = new ArrayList<MCDownloadVideoNode>();
        try {
        	String condition = "";
        	if(downloadNodeType!=null){
        		if(downloadNodeType==MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE){
        			condition = createSqlByType(downloadNodeType) + "and";
        		}else{
        			condition = "type='" + downloadNodeType.value() +"'" + "and";
        		}
        	}
        	
        	condition += " courseId=" + "'" + courseId +"'";
        	cursor =  context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects, condition, v4, null);
            while(cursor.moveToNext()) {
            	  MCDownloadVideoNode node = new MCDownloadVideoNode(context);
                  node.setCourseId(cursor.getString(cursor.getColumnIndex("courseId")));
                  node.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                  node.setCourseImageUrl(cursor.getString(cursor.getColumnIndex("courseImageUrl")));
                  node.setChapter_seq(cursor.getInt(cursor.getColumnIndex("chapter_seq")));
                  node.setSection_seq(cursor.getInt(cursor.getColumnIndex("section_seq")));
                  node.setSectionId(cursor.getString(cursor.getColumnIndex("sectionId")));

                  node.setSectionName(cursor.getString(cursor.getColumnIndex("sectionName")));
                  node.setResourceSection(cursor.getString(cursor.getColumnIndex("resourceSection")));
                  node.downloadUrl = cursor.getString(cursor.getColumnIndex("downloadUrl"));
                  node.filename = cursor.getString(cursor.getColumnIndex("filename"));
                  node.fileSize = cursor.getLong(cursor.getColumnIndex("fileSize"));
                  node.downloadSize = cursor.getLong(cursor.getColumnIndex("downloadSize"));
                  String type = cursor.getString(cursor.getColumnIndex("type"));
                  node.setNodeType(MCDownloadNodeType.initWithString(type));
                  list.add(node);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null), list);
            return;
        }finally{
        	if(cursor != null) {
                cursor.close();
            }
        }
        
        if(list.size() > 0) {
            callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), list);
        }else{
        	callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, null), list);
        }
        return;
        
    }

    public void getSFPFilenameBySectionId(String sectionId, MCAnalyzeBackBlock callBack, Context context) {
        Cursor cursor =null;
        List<String> list = new ArrayList<String>();
        try {
        	String condition = " resourceSection=" + "'" + sectionId +"'";
        	cursor =  context.getContentResolver().query(DownloadColumns.CONTENT_URI_DOWNLOADINFO, DownloadColumns.projects, condition, null, null);
            while(cursor.moveToNext()) {
                  String fileName = cursor.getString(cursor.getColumnIndex("filename"));
                  if(!TextUtils.isEmpty(fileName)){
                	  list.add(fileName);
                  }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }finally{
        	if(cursor != null) {
                cursor.close();
            }
        }
        
        callBack.OnAnalyzeBackBlock(MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null), list);
    }
    
	private String createSqlByType(MCDownloadNodeType nodeType) {
		if(nodeType==MCDownloadNodeType.MC_SFP_AND_VIDEO_TYPE){
			return " (type='" + MCDownloadNodeType.MC_SFP_TYPE.value() +"' or type = '" + MCDownloadNodeType.MC_VIDEO_TYPE.value() +"')  " ;
		}else{
			return " type='" + MCDownloadNodeType.MC_SFP_TYPE.value() +"' ";
		}
	}

	/**
	 * 把文件从原始路径复制到新路径
	 * fileList			要处理的文件集合
	 * orgPath			原始路径
	 * targetPath 		目标路径
	 * deleteOrg 		是否删除旧的
	 */
	@Override
	public void removeVideoToNewDisk(final List<String> fileList, final String orgPath, final String targetPath, final boolean deleteOrg,
			final TextView tipView, final MCAnalyzeBackBlock resultBack, Context mContext) {
		final MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
			@Override
			public String DoInBackground(MCAsyncTask task) {
				try {
					if (fileList == null || fileList.size() == 0) {
						return MCAsyncTask.SUCCESS;
					}
					// 暂停所有缓存队列
					MCDownloadQueue.getInstance().pauseDownLoadingTask();
					int count = 0;
					for (String str : fileList) {
						count++;
						task.publishProgress(count);
						String targetPaht = str.replace(orgPath, targetPath);
						FileUtils.fileChannelCopyByPath(str, targetPaht);
					}
					if (deleteOrg) {
						for (String str : fileList) {
							File orgFile = new File(str);
							if (orgFile.exists()) {
								orgFile.delete();
							}
						}
					}

					// 重新开始原来的缓存队列
				} catch (Exception e) {
					e.printStackTrace();
					return MCAsyncTask.FAIL;
				} finally {
					// 最终恢复所有下载
					MCDownloadQueue.getInstance().resumeDownLoadingTask();
				}
				return MCAsyncTask.SUCCESS;
			}

			@Override
			public void DoAfterExecute(String result) {
				MCServiceResult statusResult = null;
				if (MCAsyncTask.SUCCESS.equals(result)) {
					statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, result);
				} else {
					statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, result);
				}
				resultBack.OnAnalyzeBackBlock(statusResult, null);
			}

			@Override
			public void onProgressUpdate(Integer value) {
				if (tipView != null) {
					String info = "[" + value + "/" + fileList.size() + "] 已缓存文件处理中……";
					tipView.setText(info);
				}
			}
		});
		asyncTask.execute(500);

	}

}

