package com.whaty.imooc.ui.traininglog;

import android.content.Intent;

import com.whaty.imooc.logic.model.BlogModel;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.base.adaper.QuickAdapter;

public class BlogFilter {

	public static  void onReceive(QuickAdapter adapter, Intent intent) {
		String action = intent.getAction();
		if (GPContants.RMOTHERITEMS.equals(action)) {
			String blogId = intent.getStringExtra("blogId");
			for (Object object : adapter.getAdapterList()) {
				BlogModel blogModel = (BlogModel) object;
				if (blogModel.getId().equals(blogId)) {
					adapter.getAdapterList().remove(blogModel);
					adapter.notifyDataSetChanged();
					break;
				}
			}

		}
		if (GPContants.UPDATEBLOG.equals(action)) {
			BlogModel getBlogModel = (BlogModel) intent.getSerializableExtra("blogModel");
			for (Object object : adapter.getAdapterList()) {
				BlogModel blogModel = (BlogModel) object;
				if (blogModel.getId().equals(getBlogModel.getId())) {
					blogModel.setTitle(getBlogModel.getTitle());
					blogModel.setContent(getBlogModel.getContent());
					blogModel.setAbstractContent(getBlogModel.getAbstractContent());
					blogModel.setViewNum(getBlogModel.getViewNum());
					blogModel.setLikeNum(getBlogModel.getLikeNum());
					adapter.notifyDataSetChanged();
					break;
				}
			}
		}

	}

}
