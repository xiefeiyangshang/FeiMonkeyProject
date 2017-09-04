package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCDate;
import com.whatyplugin.base.type.MCTime;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCSectionModel extends MCDataModel implements Serializable {
    public enum MCCourseFocusStatus {
            MC_COURSE_FOCUSED("MC_COURSE_FOCUSED", 0),
            MC_COURSE_UNFOCUSED("MC_COURSE_UNFOCUSED", 1);

        private MCCourseFocusStatus(String arg1, int arg2) {
        }
    }

    public enum MCSectionStatus {

    		MC_SECTION_UNPLAY("MC_SECTION_UNPLAY", 0),
            MC_SECTION_PLAYING("MC_SECTION_PLAYING", 1),
            MC_SECTION_PLAYED("MC_SECTION_PLAYED", 2),
            MC_SECTION_FINISH("MC_SECTION_FINISH", 3);
    	private String key;
    	private int value;
    	
        private MCSectionStatus(String key, int value) {
        	this.key = key;
        	this.value = value;
        }
        
        public int value(){
        	return value;
        }
    }

    private int chapterId;
    private int chapterSeq;
    private long downloadMediaSize;
    private MCTime duration;
    private String id;
    private MCCourseFocusStatus isFocused;
    private MCDate lastdate;
    private long mediaSize;
    private String mediaUrl;
    private String name;
    private String orgName;
    private MCTime progress;
    private int seq;
    private String sharedUrl;
    private MCSectionStatus status;
    private MCSectionStatus orgStatus;
    private MCMediaType type;
    private String courseId;//方便保存笔记的时候传递参数
    private String note;
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public MCSectionModel() {
        super();
    }

    MCCourseFocusStatus convertCourseFocusStatusFromInt(int type) {
        MCCourseFocusStatus status = type == 1 ? MCCourseFocusStatus.MC_COURSE_FOCUSED : MCCourseFocusStatus.MC_COURSE_UNFOCUSED;
        return status;
    }

    /**
     * 	untouch   未学习
		incomplete 未完成
		completed  完成
     * @param status
     * @return
     */
    MCSectionStatus convertStatusFromString(String str) {
        MCSectionStatus status = null;
        if("untouch".equals(str)) {
        	status = MCSectionStatus.MC_SECTION_UNPLAY;
        } else if("incomplete".equals(str)) {
        	status = MCSectionStatus.MC_SECTION_PLAYED;
        } else if("completed".equals(str)){
        	status = MCSectionStatus.MC_SECTION_FINISH;
        }else{
        	status = MCSectionStatus.MC_SECTION_UNPLAY;
        }
        return status;
    }

    /*  KC("kc","课件目录"),//Scorm课件的目录
		JZ("jz","课件节点"),//Scorm课件的节点
		SECTION("section","章节"),
		VIDEO("video","视频"),
		DOC("doc","文档"),
		TEXT("text","图文"),
		RESOURCE("resource","下载资料"),
		COURSEWARE("courseware","电子课件"),
		LINK("link","链接"),
		HOMEWORK("homework","作业"),
		TOPIC("topic","主题讨论"),
		TEST("test","自测"); */
    MCMediaType convertMediaTypeFromString(String type) {
        MCMediaType mediaType = null;
        if("video".equals(type)) {
            mediaType = MCMediaType.MC_VIDEO_TYPE;
        } else if ("text".equals(type)) {
            mediaType = MCMediaType.MC_PROGRAMME_TYPE;
        } else if ("doc".equals(type)) {
            mediaType = MCMediaType.MC_DOC_TYPE;
        } else if ("resource".equals(type)) {
            mediaType = MCMediaType.MC_RESOURCE_TYPE;
        } else if ("courseware".equals(type)) {
            mediaType = MCMediaType.MC_COURSEWARE_TYPE;
        } else if ("homework".equals(type)) {
            mediaType = MCMediaType.MC_HOMEWORK_TYPE;
        } else if ("topic".equals(type)) {
            mediaType = MCMediaType.MC_TOPIC_TYPE;
        } else if ("test".equals(type)) {
            mediaType = MCMediaType.MC_EVALUATION_TYPE;
        } else if ("link".equals(type)) {
            mediaType = MCMediaType.MC_LINK_TYPE;
        } 

        return mediaType;
    }

    public int getChapterId() {
        return this.chapterId;
    }

    public int getChapterSeq() {
        return this.chapterSeq;
    }

    public long getDownloadMediaSize() {
        return this.downloadMediaSize;
    }

    public MCTime getDuration() {
        return this.duration;
    }

    public MCCourseFocusStatus getIsFocused() {
        return this.isFocused;
    }

    public MCDate getLastdate() {
        return this.lastdate;
    }

    public long getMediaSize() {
        return this.mediaSize;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public String getName() {
        return this.name;
    }

    public MCTime getProgress() {
        return this.progress;
    }

    public int getSeq() {
        return this.seq;
    }

    public String getSharedUrl() {
        return this.sharedUrl;
    }

    public MCSectionStatus getStatus() {
        return this.status;
    }

    public MCMediaType getType() {
        return this.type;
    }

    public MCSectionModel modelWithData(Object data) {
        MCSectionModel sectionModel = null;
        JSONObject jsonObject=null;
        if(data!=null) {
            try {
            	jsonObject = new JSONObject(data.toString());
            	sectionModel = new MCSectionModel();
            	if(jsonObject.has("id")){
            		sectionModel.setId(jsonObject.getString("id"));
            	}
            	if(jsonObject.has("title")){
            		sectionModel.setName(jsonObject.getString("title"));
            		sectionModel.setOrgName(jsonObject.getString("title"));
            	}
            	
            	if(jsonObject.has("type")){
            		sectionModel.setType(this.convertMediaTypeFromString(jsonObject.getString("type")));
            	}
            	
            	if(jsonObject.has("link")){
            		String link = jsonObject.getString("link");
            		if(sectionModel.getType() != MCMediaType.MC_VIDEO_TYPE){
            			if(!link.startsWith("http:")){
                			link = RequestUrl.getInstance().MODEL_BASE + link; 
                		}else{
                			if(link.contains("http://www.webtrn.cn"))
                				link = link.replace("http://www.webtrn.cn", "http://www.webtrn.cn/learning");
                		}
            			sectionModel.setMediaUrl(link);//三分屏的会用
                		sectionModel.setSharedUrl(link);//文档下载会用
            		}else{
            			if(link.startsWith("/")){
            				link = link.substring(1, link.length());
            			}
            			if(link.endsWith(".mp4") && !link.startsWith("http:")){//如果是mp4拼出全路径
            				link = RequestUrl.getInstance().MODEL_BASE  + link;
            			}
            			sectionModel.setMediaUrl(link);
            		}
            	
            	}

            	if(jsonObject.has("note")){
            		if(!jsonObject.isNull("note")){
            			sectionModel.setNote(jsonObject.getString("note"));
            		}
            	}


				if (sectionModel.getType() == MCMediaType.MC_COURSEWARE_TYPE) {
					if (sectionModel.getName().contains("FLASH")) {
						//sectionModel.setMediaUrl("http://192.168.22.133/test/NORMAL/imsmanifest.xml");
						//sectionModel.setMediaUrl("http://inside.lms.demo.webtrn.cn/ysjt/CourseWareNoThumb/imsmanifest.xml");
					} else {
						//sectionModel.setMediaUrl("http://inside.lms.demo.webtrn.cn/ysjt/CourseWare/imsmanifest.xml");
					}
					
				//	sectionModel.setMediaUrl("http://192.168.45.131/test/bx004/imsmanifest.xml");
					//sectionModel.setMediaUrl("http://inside.kfkc.webtrn.cn/learnspace/CourseImports/inside/20150202003/0001/imsmanifest.xml");
					
					//sectionModel.setMediaUrl("http://inside.kfkc.webtrn.cn/learnspace/CourseImports/inside/20150202003/0001/imsmanifest.xml");
				}
            	sectionModel.setStatus(convertStatusFromString(jsonObject.optString("status")));//解析学习记录
            	sectionModel.setOrgStatus(sectionModel.getStatus());
            	if(sectionModel.getType() == MCMediaType.MC_VIDEO_TYPE){
            		int minute = 0;
            		if(jsonObject.has("time")){
            			minute = jsonObject.getInt("time");
                	}
            		
            		sectionModel.setDuration(MCTime.timeWithMilliseconds(minute*1000));
                        		
            		//网盘的mp4
            		//sectionModel.setDownloadMediaUrl("http://stream.2.bgp.webtrn.cn/videoForSeg/yunPan/fjsd/tch002/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150317/%E4%B8%AD%E5%9B%BD%E7%8E%B0%E5%BD%93%E4%BB%A3%E6%95%A3%E6%96%87%E7%A0%94%E7%A9%B6-%E5%AE%A3%E4%BC%A0%E7%89%87.mp4.VIDEOSEGMENTS/11139a75022eb529bda27a18d18a0f9b.mp4");
            		
            		//项目里的mp4
            		//sectionModel.setDownloadMediaUrl("http://mooc.fjtu.com.cn/learnspace/incoming/fjsd/video/20141212/1418367870201-0.mp4");
            		sectionModel.setProgress(MCTime.timeWithMilliseconds(0));
            		sectionModel.setDownloadMediaSize(1);
            	}
            	
            }catch(Exception e){
            	e.printStackTrace();
            }
        }

        return sectionModel;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public void setChapterSeq(int chapterSeq) {
        this.chapterSeq = chapterSeq;
    }

    public void setDownloadMediaSize(long downloadMediaSize) {
        this.downloadMediaSize = downloadMediaSize;
    }

    public void setDuration(MCTime duration) {
        this.duration = duration;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsFocused(MCCourseFocusStatus isFocused) {
        this.isFocused = isFocused;
    }

    public void setLastdate(MCDate lastdate) {
        this.lastdate = lastdate;
    }

    public void setMediaSize(long mediaSize) {
        this.mediaSize = mediaSize;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgress(MCTime progress) {
        this.progress = progress;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    public void setStatus(MCSectionStatus status) {
        this.status = status;
    }

    public void setType(MCMediaType type) {
        this.type = type;
    }

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public MCSectionStatus getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(MCSectionStatus orgStatus) {
		this.orgStatus = orgStatus;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}

