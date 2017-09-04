package ukplugin.co.senab.photoview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 带监控输入法回调事件的RelativeLayout，true代表输入法弹出 false 代表输入法隐藏，主要根据，输入法出来之后的布局大小的变化
 * 
 * @author 李庆举
 * 
 * 
 */
public class KeyboardRelativeLayout extends RelativeLayout {
	private onSizeChangedListener mChangedListener;
	private static final String TAG = "KeyboardRelativeLayout";
	private boolean mShowKeyboard = false;
	private int mWidth;
	private int mHeight;

	public KeyboardRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public KeyboardRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyboardRelativeLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = widthMeasureSpec;
		mHeight = heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//取高度差 防止输入法切换字符输入法被关闭再打开引起的闪退输入法
		if (null != mChangedListener && 0 != oldw && 0 != oldh && Math.abs(h - oldh) > 400) {
			mShowKeyboard = h < oldh ? true : false;
			mChangedListener.onChanged(mShowKeyboard);
		}
	}

	public void setOnSizeChangedListener(onSizeChangedListener listener) {
		mChangedListener = listener;
	}

	public interface onSizeChangedListener {
		void onChanged(boolean showKeyboard);
	}
}
