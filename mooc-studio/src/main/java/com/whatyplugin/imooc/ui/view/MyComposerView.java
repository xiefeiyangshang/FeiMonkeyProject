package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class MyComposerView extends RelativeLayout {

	private TextView tv_title;
	private ImageView ib_icon;
	private ImageView iv_point;
	public MyComposerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ComposerView);
		Drawable drawable = a.getDrawable(R.styleable.ComposerView_myicon);
		String title = a.getString(R.styleable.ComposerView_mytitle);
//		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.whaty.jpushdemo", "mytitle");
//		resource = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/com.whaty.jpushdemo", "myicon", 0);
		setTitle(title);
		setImageIcon(drawable);
	}

	public MyComposerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ComposerView);
		Drawable drawable = a.getDrawable(R.styleable.ComposerView_myicon);
		String title = a.getString(R.styleable.ComposerView_mytitle);
//		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.whaty.jpushdemo", "mytitle");
//		resource = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/com.whaty.jpushdemo", "myicon", 0);
		setTitle(title);
		setImageIcon(drawable);
	}

	public MyComposerView(Context context) {
		super(context);
		initView(context);
	}
	
	/**
	 * 初始化 自定义的组合控件
	 * @param context 上下文
	 */
	public void initView(Context context){
		//把xml文件转化成view对象,挂载在自己身上 当前view对象上  this
		View.inflate(context, R.layout.item_composer, this);
		tv_title = (TextView) this.findViewById(R.id.tvcomposer);
		ib_icon = (ImageView) this.findViewById(R.id.ibcomposer);
		iv_point = (ImageView) this.findViewById(R.id.ibcomposer_point);
		
	}
	
	/**
	 * 设置标题
	 * @param text
	 */
	public void setTitle(String text){
		tv_title.setText(text);
	}
	/**
	 * 设置ICon
	 * @param resId
	 */
	public void setImageIcon(Drawable dr){
		ib_icon.setBackgroundDrawable(dr);
	}

	/**
	 * 是否显示小红点
	 * @param visibility
	 */
	public void setPointVisibility(int visibility){
		iv_point.setVisibility(visibility);
	}
}
