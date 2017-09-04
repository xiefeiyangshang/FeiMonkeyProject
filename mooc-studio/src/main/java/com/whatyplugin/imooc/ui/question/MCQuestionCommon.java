package com.whatyplugin.imooc.ui.question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCImgUrl;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.ui.showimage.PicFullScreenShowActivity;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.uikit.resolution.MCResolution;

/**
 * 答疑部分的功用代码
 * 
 * @author 马彦君
 * 
 */
public class MCQuestionCommon {
  public final static String  QUESTION=" 个问题"; 
	
	/**
	 * 向问题列表中封装三张图片 helper作为参数
	 * 
	 * @param helper
	 * @param question
	 * @param context
	 */
	public static void showPicInItem(BaseAdapterHelper helper, List<MCImgUrl> imgUrlList, Context context) {
		List<MCImageView> imageList = new ArrayList<MCImageView>();

		imageList.add((MCImageView) helper.getView(R.id.pic_one));
		imageList.add((MCImageView) helper.getView(R.id.pic_two));
		imageList.add((MCImageView) helper.getView(R.id.pic_three));
		View view = helper.getView(R.id.pic_content);

		createQuestionPicLayout(imgUrlList, context, imageList, view);
	}

	/**
	 * 向问题列表中封装三张图片 容器view作为参数
	 * 
	 * @param helper
	 * @param question
	 * @param context
	 */
	public static void showPicInItemWithView(View itemLayout, List<MCImgUrl> imgUrlList, Context context) {
		List<MCImageView> imageList = new ArrayList<MCImageView>();

		imageList.add((MCImageView) itemLayout.findViewById(R.id.pic_one));
		imageList.add((MCImageView) itemLayout.findViewById(R.id.pic_two));
		imageList.add((MCImageView) itemLayout.findViewById(R.id.pic_three));
		View picContentView = itemLayout.findViewById(R.id.pic_content);
		createQuestionPicLayout(imgUrlList, context, imageList, picContentView);
	}

	/**
	 * 向问题列表中封装三张图片 activity作为参数
	 * 
	 * @param helper
	 * @param question
	 * @param context
	 */
	public static void showPicInItemWithActivity(Activity activity, List<MCImgUrl> imgUrlList, Context context) {
		List<MCImageView> imageList = new ArrayList<MCImageView>();

		imageList.add((MCImageView) activity.findViewById(R.id.pic_one));
		imageList.add((MCImageView) activity.findViewById(R.id.pic_two));
		imageList.add((MCImageView) activity.findViewById(R.id.pic_three));
		View picContentView = activity.findViewById(R.id.pic_content);
		createQuestionPicLayout(imgUrlList, context, imageList, picContentView);
	}

	public static void showFullScreenPic(List<String> list, int i, Context context) {
		Activity thisActivity = (Activity) context;
		Intent intent = new Intent(thisActivity, PicFullScreenShowActivity.class);
		List<String> imgPath = list;
		Bundle bundle = new Bundle();
		bundle.putSerializable("imgPath", (Serializable) imgPath);
		bundle.putSerializable("startIndex", i);
		intent.putExtras(bundle);
		thisActivity.startActivity(intent);
	}

	/**
	 * 公用的创建三张图片列表 的布局
	 * 
	 * @param imgUrlList
	 * @param context
	 * @param imageList
	 * @param picContentView
	 */
	private static void createQuestionPicLayout(List<MCImgUrl> imgUrlList, final Context context, List<MCImageView> imageList, View picContentView) {
		int totalWidth = MCResolution.getInstance(context).getDevWidth(context);
		final List<String> bigUrlList = new ArrayList<String>();

		if (imgUrlList != null && imgUrlList.size() > 0) {// 证明有图片链接
			picContentView.setVisibility(View.VISIBLE);
			int listSize = imgUrlList == null ? 0 : imgUrlList.size();
			for (int i = 0; i < 3; i++) {

				MCImageView imageView = imageList.get(i);
				LayoutParams lp = imageView.getLayoutParams();
				lp.width = (int) (totalWidth * 0.3);
				lp.height = lp.width;
				imageView.setLayoutParams(lp);

				if (i < listSize) {
					// 需要显示的
					imageView.setVisibility(View.VISIBLE);

					MCImgUrl imgUrl = imgUrlList.get(i);
					bigUrlList.add(imgUrl.getOriginalUrl());
					imageView.setErrorImageResId(R.drawable.pic_load_fail_small);// 图片加载失败的时候显示的
					imageView.setImageUrl(imgUrl.getSquareUrl(), MCCacheManager.getInstance().getImageLoader());
					final int itemIndex = i;
					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							MCQuestionCommon.showFullScreenPic(bigUrlList, itemIndex, context);
						}
					});
				} else {
					// 不需要显示的
					imageView.setVisibility(View.INVISIBLE);
				}
			}
		} else {
			picContentView.setVisibility(View.GONE);
		}
	}

	/**
	 * 我的问题的适配器
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initMyQuestionAdapter(final Context context) {
		return new QuickAdapter(context, R.layout.item_question_mine) {
			protected void convert(BaseAdapterHelper helper, MCQuestionModel question) {
				helper.setText(R.id.tv_desc, question.getBody());
				Date data;
				data = DateUtil.parseAll(question.getPublishDate());
				helper.setText(R.id.tv_time, DateUtil.format(data, DateUtil.FORMAT_NEW_SHORT));
				if (Integer.parseInt(question.getReplyCount()) == 0) {
					helper.setText(R.id.tv_answer_count, "暂无答复");
				} else {// 为了测试，只要有答复的都有推荐图片显示
					helper.setText(R.id.tv_answer_count, question.getReplyCount() + "答复");
				}

				if (question.isTeacherReplay()) {
					helper.setVisible(R.id.iv_recmd, true);
				} else {
					helper.setVisible(R.id.iv_recmd, false);
				}

				MCQuestionCommon.showPicInItem(helper, question.getImgUrlList(), context);
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCQuestionModel) arg2));
			}
		};
	}

	/**
	 * 所有问题的适配器
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initAllQuestionAdapter(final Context context) {
		return new QuickAdapter(context, R.layout.item_question) {
			protected void convert(BaseAdapterHelper helper, final MCQuestionModel question) {

				helper.setText(R.id.tv_desc, question.getBody());
				helper.setText(R.id.tv_nick, question.getSubmituserName());
				CircleImageView question_headimage = (CircleImageView) helper.getView(R.id.question_headimage);
				question_headimage.setDefaultImageResId(R.drawable.user_default_img);

				question_headimage.setImageUrl(question.getUserPic(), MCCacheManager.getInstance().getImageLoader());

				Date data;
				data = DateUtil.parseAll(question.getPublishDate());
				helper.setText(R.id.tv_time, DateUtil.format(data, DateUtil.FORMAT_NEW_SHORT));
				if (Integer.parseInt(question.getReplyCount()) == 0) {
					helper.setText(R.id.tv_answer_count, "暂无答复");
				} else {// //为了测试，只要有答复的都有推荐图片显示
					helper.setText(R.id.tv_answer_count, question.getReplyCount() + "答复");
				}

				if (question.isTeacherReplay()) {
					helper.setVisible(R.id.iv_recmd, true);
				} else {
					helper.setVisible(R.id.iv_recmd, false);
				}

				MCQuestionCommon.showPicInItem(helper, question.getImgUrlList(), context);
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCQuestionModel) arg2));
			}
		};
	}

	/**
	 * 按课程分类的问题或者作业的展示
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initCourseQuestionOrHomeWorkAdapter(final Context context,final String strState) {
		return new QuickAdapter(context, R.layout.item_coursenote_layout) {

			protected void convert(BaseAdapterHelper helper, MCQuestionModel item) {

				helper.setText(R.id.name, item.getcName());

				helper.setText(R.id.learnedtime_tv, item.getaCount() +strState);
				ViewGroup.LayoutParams imageParams = helper.getView(R.id.image).getLayoutParams();
				imageParams.width = MCResolution.getInstance(context).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
				imageParams.height = MCResolution.getInstance(context).scaleHeightByScaleWidth(imageParams.width);
				helper.getView(R.id.image).setLayoutParams(imageParams);
				ViewGroup.LayoutParams params = helper.getView(R.id.content_layout).getLayoutParams();
				params.width = imageParams.width;
				params.height = imageParams.height;
				helper.getView(R.id.content_layout).setLayoutParams(params);
				helper.setDefImage(R.id.image, R.drawable.course_default_bg);
				helper.setImageUrl(R.id.image, item.getcPhoto(), MCCacheManager.getInstance().getImageLoader(), imageParams.width, imageParams.height, false,
						ImageType.CICLE_IMAGE, null);

			}

			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCQuestionModel) model));
			}
		};
	}
}
