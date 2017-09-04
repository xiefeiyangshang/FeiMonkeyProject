package com.whatyplugin.imooc.logic.model;

public class AnsRule {
	/**
	 * 超过限制次数后是否允许继续不算成绩的测试 false代表不允许，true代表允许
	 */
	public boolean localtest = false;
	/**
	 * 是否立即得到成绩 1代表立即、0代表不立即
	 */
	public int scoreshownow;
	/**
	 * 答题后是否显示答案 all(可以查看)/serverback（服务器端决定）/none(一直不允许)
	 */
	public String showanswer;
	/**
	 * 查看答案时是否有影响 文本 none 没影响/cannotdo 不能再做题（执行相对应的接口
	 */
	public String onanswerbuttonclick;
	/**
	 * 是否支持显示知识点 true/false
	 */
	public boolean showpointsofknowledge;
}
