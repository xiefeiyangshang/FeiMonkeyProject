package com.whatyplugin.imooc.ui.notic;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.whatydb.dao.base.NoticDao;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;

/**
 * 课程通知列表
 * 
 * @author bzy
 */
public class MCNoticeListActivity extends MCBaseListActivity {

	private String courseId;
	private MCStudyService service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "通知列表为空";
	}

	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent() != null) {
			this.courseId = getIntent().getStringExtra("courseId");
		}
		this.service = new MCStudyService();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 请求数据的主体
	 */
	public void requestData() {
		this.service.getNoticeList(this.courseId, this.mCurrentPage, this, this);
	}

	/**
	 * 查看答案后返回的结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 没有必要循环查找进行更新
		if (HttpURLConnection.HTTP_OK == resultCode) {
			// 刷新试图
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initAdapter() {
		final ImageGetter imgGetter = new Html.ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = MCNoticeListActivity.this.getResources().getDrawable(R.drawable.img_ico);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 10, drawable.getIntrinsicHeight() + 10);
				return drawable;
			}
		};
		this.adapter = new QuickAdapter(this, R.layout.item_notic_list) {
			protected void convert(BaseAdapterHelper helper, MCNotice question) {
				helper.setText(R.id.tv_time, question.getUpdateDate());
				helper.setText(R.id.tv_look_count, "浏览: " + question.getReadCount() + " 次");
				helper.setText(R.id.tv_title, question.getTitle());
				CharSequence test = Html.fromHtml(question.getNote(), imgGetter, null);
				((TextView) helper.getView(R.id.tv_desc)).setText(test.toString().replaceAll("￼", ""));

				helper.setVisible(R.id.iv_top, "1".equals(question.getIsTop()) ? true : false);
			}

			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCNotice) model));
			}
		};
	}

	@Override
	public void doSomethingWithResult(List resultList) {
		// 更新数据库
		if (this.mCurrentPage == 1 && resultList.size() > 0) {
			this.adapter.clear();
			// 更新数据库 比对 删除 和插入
			try {
				NoticDao dao = new NoticDao();
				List<MCNotice> list = new ArrayList<MCNotice>();
				list.addAll(resultList);
				dao.updateNotices(list, courseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改成点击的时候就去请求，以方便别人使用
	 * 
	 */

	@Override
	public void doAfterItemClick(Object obj) {
		MCNotice model = (MCNotice) obj;
		Intent intent = new Intent(this, MCNoticeDetailActivity.class);
		intent.putExtra("MCNotice", model);
		// 点击进去的时候 就进行更新，不做循环
		model.setReadCount(model.getReadCount() + 1);

		service.makeNoticeState(model.getId(), new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (MCResultCode.MC_RESULT_CODE_SUCCESS.equals(result.getResultCode())) {
				}
			}
		}, this);
		startActivityForResult(intent, 0);
	}

	@Override
	public String getFunctionTitle() {
		return "课程通知";
	}
}
