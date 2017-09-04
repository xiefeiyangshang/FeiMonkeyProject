package com.whatyplugin.imooc.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class BaseTitleView extends RelativeLayout {

	private ImageView ivLeft;
	private TextView centerTv;
	private ImageView ivRight;
	private TextView tvRight;
	private Drawable drawable;

	/**
	 * 标题右侧图标的点击回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface RightClickListener {

		public abstract void onRightViewClick();
	}

	public BaseTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		initItem(context, attrs);
	}

	public BaseTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		initItem(context, attrs);
	}

	public BaseTitleView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 初始化 自定义的组合控件
	 * 
	 * @param context
	 *            上下文
	 */
	public void initView(final Context context) {
		// 把xml文件转化成view对象,挂载在自己身上 当前view对象上 this
		View.inflate(context, R.layout.base_titlebar, this);

		ivLeft = (ImageView) this.findViewById(R.id.left_img);
		centerTv = (TextView) this.findViewById(R.id.center_tv);
		tvRight = (TextView) this.findViewById(R.id.right_tv);
		ivRight = (ImageView) this.findViewById(R.id.right_img);

		ivLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (context instanceof Activity) {
					((Activity) context).finish();
				}
			}
		});

	}

	public void initItem(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleView);
		String title = a.getString(R.styleable.BaseTitleView_middle_title);
		drawable = a.getDrawable(R.styleable.BaseTitleView_rightimgicon);
		if (drawable != null) {
			ImageView imageView = (ImageView) this.findViewById(R.id.right_img);
			imageView.setImageDrawable(drawable);
		}
		String rightText = a.getString(R.styleable.BaseTitleView_righttext);
		boolean rightImgVisible = a.getBoolean(R.styleable.BaseTitleView_rightimgvisible, false);
		boolean rightTextVisible = a.getBoolean(R.styleable.BaseTitleView_righttextvisible, false);
		setTitle(title);
		setRightText(rightText);
		if (rightImgVisible && rightTextVisible) {
			LayoutParams layoutParams = (LayoutParams) ivRight.getLayoutParams();
			layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.right_tv);
			layoutParams.width = LayoutParams.WRAP_CONTENT;
			layoutParams.height = LayoutParams.MATCH_PARENT;
			ivRight.setLayoutParams(layoutParams);
		}
		setRightImgVisible(rightImgVisible);
		setRightTextVisible(rightTextVisible);
	}

	/**
	 * 设置标题
	 * 
	 * @param text
	 */
	public void setTitle(String text) {
		centerTv.setText(text);
	}

	public void setRightText(String text) {
		tvRight.setText(text);
	}

	public void setRightImgVisible(boolean visible) {
		ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public void setRightTextVisible(boolean visible) {
		tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public void setRigImageListener(final RightClickListener rightListener) {
		setRightImgVisible(true);
		ivRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rightListener.onRightViewClick();
			}
		});
	}

	public void setRigTextListener(final RightClickListener rightListener) {
		setRightTextVisible(true);
		tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rightListener.onRightViewClick();
			}
		});
	}
}
