package com.whatyplugin.imooc.logic.model;

public class MCTestResultDeatilModel {
	/**
	 * 自测Id
	 */
	private String id;
	/**
	 * 自测标题
	 */
	private String title;
	/**
	 * 本次得分
	 */
	private String currentScore;
	/**
	 * 历史最高得分
	 */
	private String maxScore;
	/**
	 * 排名百分比
	 */
	private String position;
	/**
	 * 是否允许查看答案，true允许(默认值)，false不允许
	 */
	private String allowshowanswer;
	/**
	 * 不允许查看答案的原因，可为空
	 */
	private String forbidanswerreason;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(String currentScore) {
		this.currentScore = currentScore;
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAllowshowanswer() {
		return allowshowanswer;
	}
	public void setAllowshowanswer(String allowshowanswer) {
		this.allowshowanswer = allowshowanswer;
	}
	public String getForbidanswerreason() {
		return forbidanswerreason;
	}
	public void setForbidanswerreason(String forbidanswerreason) {
		this.forbidanswerreason = forbidanswerreason;
	}
	
	
}
