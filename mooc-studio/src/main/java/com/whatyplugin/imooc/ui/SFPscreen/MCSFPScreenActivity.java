package com.whatyplugin.imooc.ui.SFPscreen;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.imooc.logic.utils.PictureUtils;
import com.whatyplugin.imooc.ui.view.HorizontalListView;

public class MCSFPScreenActivity extends MCSFPScreenBaseActivity{

	
	private HorizontalListView hlv_thumbnail;
	
	
	private int lastIndex = 0;//记录scollTo的最后一次
	private Map<Integer, Integer> scollTimeMap = new HashMap<Integer, Integer>();//记录scollTo滚动的历史记录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//设置布局
		this.setMyContentView(R.layout.sfp_activity_layout);
		
		//设置视频尺寸
		videoSizeMap.put(R.id.fm_video_screen, 9.0/16);
		videoSizeMap.put(R.id.fm_video_ppt, 3.0/4);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public void childInitView() {
		//缩略图
		this.hlv_thumbnail = (HorizontalListView)this.findViewById(R.id.hlv_thumbnail);
		this.listAdapter = new QuickAdapter(this, R.layout.sfp_thumb_item_layout) {
			protected void convert(final BaseAdapterHelper helper, MCXmlSectionModel item) {
				
				MCImageView iv_ppt_thumb = (MCImageView) helper.getView(R.id.iv_ppt_thumb);
				
				if(currentModel !=null && item.getId().equals(currentModel.getId())){
					iv_ppt_thumb.setBackgroundColor(getResources().getColor(R.color.theme_color));
				}else{
					iv_ppt_thumb.setBackgroundColor(getResources().getColor(R.color.sfp_thumb_bg));
				}
				
				if(item.isLocal()){
					iv_ppt_thumb.setImageBitmap(PictureUtils.getBitmapByPath(item.getThumbImage()));
				}else{
					iv_ppt_thumb.setDefaultImageResId(R.drawable.course_default_bg);
					iv_ppt_thumb.setErrorImageResId(R.drawable.pic_load_fail_small);
					iv_ppt_thumb.setImageUrl(item.getThumbImage());
				}
				
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCXmlSectionModel) arg2));
			}
		};
		
		this.hlv_thumbnail.setAdapter(listAdapter);
		this.hlv_thumbnail.setOnItemClickListener(this);
	}
	
	@Override
	public void operateAfterToggle(int devWidth, int devHeight, LayoutParams videoParams) {
		super.operateAfterToggle(devWidth, devHeight, videoParams);
		
		//退出全屏的时候重新设置下宽度
		RelativeLayout.LayoutParams lp = (LayoutParams) this.hlv_thumbnail.getLayoutParams();
		lp.width = devWidth;
		this.hlv_thumbnail.setLayoutParams(lp);
		this.hlv_thumbnail.invalidate();
		this.hlv_thumbnail.scrollTo(hlv_thumbnail.getSingleChildWidth()*(lastIndex-2));
		
	}
	
	/**
	 * 进度条的变更引起的节点变化
	 */
	@Override
	public void afterSeekTo(int position) {
		
		//如果是不是人为的点击节点改变的进度就做如下处理
		MCXmlSectionModel resultModel = null;
		int index = 0;
		try {
			for(MCXmlSectionModel model : childSections){
				//首先父类是一个类， 因为视频的路径一样的都是有一个相同的parent
				if(model.getParentModel().getId().equals(this.currentModel.getParentModel().getId())){
					if(model.getTime()>position){
						break;
					}
					resultModel = model;
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultModel == null){
			return;
		}
		if(this.currentModel != resultModel){
			this.currentModel = resultModel;
			this.adapter.notifyDataSetChanged();
			this.listAdapter.notifyDataSetChanged();
			
		}
		
		if(scollTimeMap.get(index)==null){
			scollTimeMap.put(index, 0);
		}
		//这里每个新切换的节点都要执行2次， 不然只是进度条切换执行一次的话scroll是滚动不了的，要等下次进度更新的时候再执行一次
		if( index >= 2 && scollTimeMap.get(index)<2){
			
			//如果不是全屏就 记录次数
			if(!this.fm_video_screen.isFullScreen()){
				scollTimeMap.put(index, scollTimeMap.get(index)+1);
				this.hlv_thumbnail.scrollTo(hlv_thumbnail.getSingleChildWidth()*(index-2));
			}
			
			//第一次节点变更了就记录下上个节点
			if(lastIndex!=index){
				scollTimeMap.remove(lastIndex);
				lastIndex = index;
			}
		}
	}
}
