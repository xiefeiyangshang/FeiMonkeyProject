package com.whatyplugin.imooc.logic.service_;

import android.os.AsyncTask;

public class MCAsyncTask extends AsyncTask<Integer, Integer, String> {

	public static final String SUCCESS = "SUCCESS";// 成功
	public static final String FAIL = "FAIL";// 失败

	private MCAsyncTaskInterface taskInterface;

	public MCAsyncTask(MCAsyncTaskInterface doWhat) {
		this.taskInterface = doWhat;
	}

	public MCAsyncTask() {
	}

	// 调用自己写的后台逻辑
	@Override
	protected String doInBackground(Integer... arg0) {
		return taskInterface.DoInBackground(this);
	}

	// 调用自己写的后台逻辑完成后做什么
	@Override
	protected void onPostExecute(String result) {
		taskInterface.DoAfterExecute(result);
	}

	// 调用自己写的更新
	@Override
	protected void onProgressUpdate(Integer... values) {
		try {
			int value = values[0];
			taskInterface.onProgressUpdate(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 调用AsyncTask 的 publishProgress
	protected void publishProgress(Integer value) {
		super.publishProgress(value);
	}
}
