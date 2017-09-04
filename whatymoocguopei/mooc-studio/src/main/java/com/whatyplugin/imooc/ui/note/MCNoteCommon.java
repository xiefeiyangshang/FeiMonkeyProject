package com.whatyplugin.imooc.ui.note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCMyNoteModel;
import com.whatyplugin.uikit.resolution.MCResolution;

/**
 * 笔记部分的公用代码
 * 
 * @author 马彦君
 * 
 */
public class MCNoteCommon {
	/**
	 * 我的笔记的适配器
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initMyNoteAdapter(final Context context) {
		return new QuickAdapter(context, R.layout.item_note_mine) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCMyNoteModel) arg2));
			}

			protected void convert(BaseAdapterHelper helper, MCMyNoteModel item) {
				helper.setText(R.id.note_name, item.getTitle());
				Date date;

				date = DateUtil.parseAll(item.getCreateDate());

				helper.setText(R.id.note_time, DateUtil.format(date, DateUtil.FORMAT_NEW_SHORT));
				helper.setText(R.id.note_content, Html.fromHtml(item.getContent()).toString());

				if (item.isTRecommend()) {
					helper.getView(R.id.iv_recmd).setVisibility(View.VISIBLE);
				} else {
					helper.getView(R.id.iv_recmd).setVisibility(View.GONE);
				}
			}
		};

	}

	/**
	 * 所有笔记的适配器
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initAllNoteAdapter(final Context context) {

		return new QuickAdapter(context, R.layout.item_note) {

			@Override
			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCMyNoteModel) model));
			}

			protected void convert(BaseAdapterHelper helper, MCMyNoteModel item) {
				helper.setText(R.id.note_user_name, item.getSsoUserTrueName());
				helper.setText(R.id.note_name, item.getTitle());
				Date date = DateUtil.parseAll(item.getCreateDate());
				helper.setText(R.id.note_time, DateUtil.format(date, DateUtil.FORMAT_NEW_SHORT));
				helper.setText(R.id.note_content, Html.fromHtml(item.getContent()).toString());

				if (item.isTRecommend()) {
					helper.getView(R.id.iv_recmd).setVisibility(View.VISIBLE);
				} else {
					helper.getView(R.id.iv_recmd).setVisibility(View.GONE);
				}

				MCImageView note_headimage = (MCImageView) helper.getView(R.id.note_headimage);
				note_headimage.setImageUrl(item.getPhoto());
				note_headimage.setDefaultImageResId(R.drawable.user_default_img);
			}
		};
	}

	/**
	 * 按课程分类的笔记展示
	 * 
	 * @param context
	 * @return
	 */
	public static QuickAdapter initCourseNoteAdapter(final Context context) {
		return new QuickAdapter(context, R.layout.item_coursenote_layout) {
			protected void convert(BaseAdapterHelper helper, MCCourseModel item) {
				helper.setText(R.id.name, item.getName());
				helper.setText(R.id.desc, item.getDescription());
				helper.setText(R.id.learnedtime_tv, item.getnCount() + "笔记");
				ViewGroup.LayoutParams imageParams = helper.getView(R.id.image).getLayoutParams();
				imageParams.width = MCResolution.getInstance(context).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
				imageParams.height = MCResolution.getInstance(context).scaleHeightByScaleWidth(imageParams.width);
				helper.getView(R.id.image).setLayoutParams(imageParams);
				ViewGroup.LayoutParams contentParams = helper.getView(R.id.content_layout).getLayoutParams();
				contentParams.width = imageParams.width;
				contentParams.height = imageParams.height;
				helper.getView(R.id.content_layout).setLayoutParams(contentParams);
				helper.setDefImage(R.id.image, R.drawable.course_default_bg);
				helper.setImageUrl(R.id.image, item.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), imageParams.width, imageParams.height, false,
						ImageType.CICLE_IMAGE, null);
			}

			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCCourseModel) model));
			}
		};
	}

	/**
	 * 把笔记数量为0的从结果集中删除
	 * 
	 * @param resultList
	 */
	public static void removeZeroCountNoteFromList(List<MCCourseModel> resultList) {
		if (resultList != null && resultList.size() > 0) {
			List<MCCourseModel> list = new ArrayList<MCCourseModel>();
			for (MCCourseModel model : (List<MCCourseModel>) resultList) {
				if (model != null && model.getnCount() > 0) {
					list.add(model);
				}
			}
			resultList.clear();
			resultList.addAll(list);
		}
	}
}
