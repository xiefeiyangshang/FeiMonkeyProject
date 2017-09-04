package com.whaty.imooc.ui.homework;

import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.homework.MCHomeworkDetailActivity;

public class GPHomeWorkDatileActivity extends MCHomeworkDetailActivity {

	public void reQuestDataHomeWorkDetail() {
		// 已提交或者 已有成绩
		int homeWorkStatu = homeworkModel.getLocalStatus();
		// 有成绩的
		if (homeWorkStatu == 3) {
			showAnswer();
			showScore(homeworkModel);
		}
		//只有答案的
		if (homeWorkStatu == 2) {
			showAnswer();
		}

	}

	private void showAnswer() {
		WebViewUtil.loadContentWithPic(homeworkModel.getNote(), this.answer_label, this);
	}

}
