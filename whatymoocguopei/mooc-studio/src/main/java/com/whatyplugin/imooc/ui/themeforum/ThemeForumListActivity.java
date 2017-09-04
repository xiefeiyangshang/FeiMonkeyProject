package com.whatyplugin.imooc.ui.themeforum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.logic.model.MCForumModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCFourmService;
import com.whatyplugin.imooc.logic.service_.MCFourmServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;

/***
 * 
 * 讨论列表
 *
 */
public class ThemeForumListActivity extends MCBaseListActivity {
	private String courseId; // 课程ID
	private MCFourmServiceInterface service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "讨论列表为空";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.courseId = this.getIntent().getStringExtra("courseId");
		this.service = new MCFourmService();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 整体的数据请求
	 */
	public void requestData() {
		service.getForumListBycourseId(courseId, mCurrentPage, Const.PAGESIZE, ((MCAnalyzeBackBlock) this), this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MCForumCommon.DETAIL_FORUM_CODE && resultCode == MCForumCommon.DETAIL_FORUM_RESULT) {
			MCForumModel forumModel = (MCForumModel) data.getSerializableExtra("MCForumModel");
			this.adapter.replaceModel(forumModel);
		}
	}

	@Override
	public String getFunctionTitle() {
		return "主题讨论";
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.listview_themeforum_items) {

			@Override
			protected void convert(BaseAdapterHelper helper, Object arg2) {
				MCForumModel forumModel = (MCForumModel) arg2;
				helper.setText(R.id.theme_title, forumModel.getForumName());
				String lastTime = String.format("最后回帖: %s", forumModel.getLastTime());
				SpannableString sp = new SpannableString(lastTime);
				// 设置斜体  汉字没成功，  还是不用斜体了吧
				((TextView) helper.getView(R.id.theme_time)).setText(sp);
				helper.setText(R.id.theme_reply_num, forumModel.getReplyNum());
				helper.setText(R.id.theme_topic_num, forumModel.getTopicNum());
				// 防止复用产生的莫名其妙的问题
				helper.setTextColor(R.id.theme_title, 0XFF000000);
				helper.setTextColor(R.id.theme_reply_num, getResources().getColor(R.color.theme_color));
				helper.setTextColor(R.id.theme_topic_num, getResources().getColor(R.color.theme_color));
				helper.setImageResource(R.id.theme_img, R.drawable.forum_talk_ico);
				helper.setVisible(R.id.forum_new, false);
				helper.setVisible(R.id.theme_discuss, false);
				helper.setVisible(R.id.theme_about_to_begin, false);
				switch (forumModel.getState()) {
				case 1: // 即将开始
					helper.setVisible(R.id.theme_about_to_begin, true);
					break;
				case 2: // 新的
					helper.setVisible(R.id.forum_new, true);
					break;
				case 3: // 正在进行
					helper.setVisible(R.id.theme_discuss, true);
					break;
				case 4: // 已结束
					int color = Color.rgb(140, 149, 155);
					helper.setTextColor(R.id.theme_title, color);
					helper.setTextColor(R.id.theme_reply_num, color);
					helper.setTextColor(R.id.theme_topic_num, color);
					TextView tv_OutTime =(TextView) helper.getView().findViewById(R.id.theme_about_to_begin);
					tv_OutTime.setText("已过期");
					tv_OutTime.setTextColor(color);
					tv_OutTime.setVisibility(View.VISIBLE);
					helper.setImageResource(R.id.theme_img, R.drawable.forum_talk_h_ico);
					break;

				default:
					break;
				}

			}
		};
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCForumModel forumModel = (MCForumModel) obj;
		Intent intent = new Intent(this, ThemeForumInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("ForumModel", forumModel);
		bundle.putString("courseId", courseId);
		intent.putExtras(bundle);
		startActivityForResult(intent, MCForumCommon.DETAIL_FORUM_CODE);
	}

}
