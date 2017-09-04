package com.whatyplugin.base.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Environment;

public class ResultWriteMySDKCard {
	// 保存笔记
	private static String SAVENOTE = "/笔记.txt";
	// 提交作业答案
	private static String SAVE_HOMEWORK = "/作业.txt";
	// 发送答疑
	private static String SEND_QUESTION = "/发送答疑.txt";
	// 发送主题讨论回帖
	public static String SEND_FROUM = "/主题讨论回帖.txt";
	// 发送答疑答案
	public static String SEND_QUESTION_ANSWER = "/答疑答案.txt";
	// 所有的发送请求
	public static String ALL_MOOC = "/moocrequest.txt";

	public static void writeFileSdcardFile(String ResultUrl, String Result) {
		Result = "请求时间: " + DateUtil.getNow(DateUtil.FORMAT_LONG) + "  请求地址: " + ResultUrl + "     返回数据:" + Result + "\r\n";
		// 保存笔记
		if (ResultUrl.endsWith("saveNote.json")) {
			writeTxtToFile(Result, SAVENOTE);
		}
		if (ResultUrl.endsWith("saveHomeworkStudent.json")) {
			writeTxtToFile(Result, SAVE_HOMEWORK);
		}
		if (ResultUrl.endsWith("saveQuestion.json")) {
			writeTxtToFile(Result, SEND_QUESTION);
		}
		if (ResultUrl.endsWith("savePostReply.json")) {
			writeTxtToFile(Result, SEND_FROUM);
		}
		if (ResultUrl.endsWith("saveAnswer.json")) {
			writeTxtToFile(Result, SEND_QUESTION_ANSWER);
		}
		// 所有的
		writeTxtToFile(Result, ALL_MOOC);
	}

	private  static void writeTxtToFile(String strcontent, String fileName) {
		String path = Environment.getExternalStorageDirectory().getPath() + "/whatyResultInfo";
		String strFilePath = path + fileName;
		RandomAccessFile raf = null;
		try {
			File file = new File(strFilePath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			raf = new RandomAccessFile(file, "rwd");
			raf.seek(file.length());
			raf.write(strcontent.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (raf != null)
					raf.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
