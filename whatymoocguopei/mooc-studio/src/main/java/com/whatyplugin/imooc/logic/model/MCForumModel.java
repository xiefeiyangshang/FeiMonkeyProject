package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCForumModel extends MCDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 帖子ID
	private String forumName; // 帖子名称
	private String body; // 帖子内容
	private String topicNum; // 精华数
	private String lastTime; // 最后回复时间
	private String replyNum; // 回帖数
	private int State; // 处于什么状态。即将开始的1，2新的，3可进入，4已结束
	private String startTime; // 开始时间
	private String endTime;// 结束时间
	private String myLastRepliedTime; // 用户最后回复时间
	private String startAndEndTime;  //如果外界有设置的值 就不用这个
    private String nodeId;   //此帖子的noteId 用以获取回帖
    private String[] allHint = new String[]{"帖子已过期","帖子暂未开始，开始日期：","亲，赶紧写个跟帖吧"};
    private String hint;
    
    
    @Override
	public MCDataModel modelWithData(Object arg1) {
		if ("{}".equals(arg1.toString()))
			return null;
		MCForumModel forum = new MCForumModel();
		try {
			JSONObject jsonObj = (JSONObject) arg1;
			forum.setId(jsonObj.optString("id"));
			forum.setForumName(jsonObj.optString("title"));
			//正则去掉最后的回车换行
			forum.setBody(jsonObj.getString("description"));
			String startTime = jsonObj.optString("startTime");
			String endTime = jsonObj.optString("endTime");
			forum.setEndTime(DateUtil.getFormatfromTimeStr(endTime, DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR));
			forum.setStartTime(DateUtil.getFormatfromTimeStr(startTime, DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR));
			forum.setState(!DateUtil.toBeforeNow(startTime) ? (!DateUtil.toBeforeNow(endTime) ? 4 : 3) : 1);
			
			forum.setHint(!DateUtil.toBeforeNow(startTime) ? (!DateUtil.toBeforeNow(endTime) ? allHint[0] : allHint[2]) : allHint[1]+forum.getStartTime());
			
			forum.setReplyNum(jsonObj.optString("totalReplyNum"));
			forum.setTopicNum(jsonObj.getString("eliteReplyNum"));
			forum.setMyLastRepliedTime(jsonObj.getString("myLastRepliedTime")); 
			forum.setLastTime(DateUtil.getFormatfromTimeStr(jsonObj.getString("lastReplyTime"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_MONTH));
		    forum.setNodeId(jsonObj.getString("nodeId"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forum;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getStartAndEndTime() {
		//时间格式改为2015.5.2
		return startAndEndTime ==null?StringUtils.dateRelpaceZero(getStartTime()) + "-" + StringUtils.dateRelpaceZero(getEndTime()):startAndEndTime;
	}

	public void setStartAndEndTime(String startAndEndTime) {
		this.startAndEndTime = startAndEndTime;
	}

	public String getId() {
		return id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMyLastRepliedTime() {
		return myLastRepliedTime;
	}

	public void setMyLastRepliedTime(String myLastRepliedTime) {
		this.myLastRepliedTime = myLastRepliedTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(String topicNum) {
		this.topicNum = topicNum;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}

	
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


}
