// Decompiled by JEB v1.5.201404100

package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.whatyplugin.uikit.refreshview.MCPullToRefreshView;

public class CustomScrollView extends ScrollView {
	public static final int SCROLL_STATE_DRAGGING = 1;
	public static final int SCROLL_STATE_IDLE = 0;
	public static final int SCROLL_STATE_SETTLING = 2;
	private int mActivePointerId;
	private int mGutterSize;
	private float mInitialMotionY;
	private float mLastMotionX;
	private float mLastMotionY;
	
	private int mTouchSlop;
	private VelocityTracker mVelocityTracker;

	public CustomScrollView(Context arg2) {
		super(arg2);
		this.mActivePointerId = -1;
		this.init();
	}

	public CustomScrollView(Context arg2, AttributeSet arg3) {
		super(arg2, arg3);
		this.mActivePointerId = -1;
		this.init();
	}

	protected boolean canScroll(View arg11, boolean arg12, int arg13, int arg14, int arg15) {
		if (arg13 < 0) {
			this.getChildAt(0);
			if (this.getScrollY() != this.getChildAt(0).getHeight() - this.getHeight()) {
				return false;
			}
		} else if ((arg11 instanceof MCPullToRefreshView)) {
			if ((this.getScrollY() != this.getChildAt(0).getHeight() - this.getHeight())
					&& ((MCPullToRefreshView) arg11).getFirstVisiblePosition() != 0) {
				return false;
			}
		}
		if ((arg11 instanceof ViewGroup)) {
			View v6 = arg11;
			int v8 = arg11.getScrollX();
			int v9 = arg11.getScrollY();
			int v7;
			for (v7 = ((ViewGroup) v6).getChildCount() - 1; v7 >= 0; --v7) {
				View v1 = ((ViewGroup) v6).getChildAt(v7);
				if (arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom()
						&& (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
					return true;
				}
			}
		}

		if ((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
			return true;
		}
		return false;
	}

	public boolean dispatchTouchEvent(MotionEvent arg2) {
		arg2.getAction();
		return super.dispatchTouchEvent(arg2);
	}

	void init() {
		this.setWillNotDraw(false);
		this.setDescendantFocusability(262144);
		this.setFocusable(true);
		ViewConfiguration configuration = ViewConfiguration.get(this.getContext());
		this.mTouchSlop = configuration.getScaledTouchSlop();
		
	}

	private boolean isGutterDrag(float arg4, float arg5) {
		boolean isdrag;
		if (arg4 >= (((float) this.mGutterSize)) || arg5 <= 0f) {
			if (arg4 > (((float) (this.getHeight() - this.mGutterSize))) && arg5 < 0f) {
				return true;
			}

			isdrag = false;
		} else {
			return true;
		}
		return isdrag;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		float v0_1;
		int v3 = -1;
		boolean v2 = false;
		int v0 = event.getAction() & 255;
		if (v0 == 3 || v0 == 1) {
			if (this.mVelocityTracker == null) {
				return v2;
			}

			this.mVelocityTracker.recycle();
			this.mVelocityTracker = null;
		} else {
			switch (v0) {
			case 0: {
				v0_1 = event.getY();
				this.mInitialMotionY = v0_1;
				this.mLastMotionY = v0_1;
				this.mLastMotionX = event.getX();
				this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
				
				break;
			}
			case 2: {
				v0 = this.mActivePointerId;
				if (v0 != v3) {
					v0 = MotionEventCompat.findPointerIndex(event, v0);
					if (v0 != v3) {
						float v6 = MotionEventCompat.getX(event, v0);
						float v7 = Math.abs(v6 - this.mLastMotionX);
						float v8 = MotionEventCompat.getY(event, v0);
						float v9 = v8 - this.mLastMotionY;
						float v10 = Math.abs(v8 - this.mLastMotionY);
						if (v9 != 0f && !this.isGutterDrag(this.mLastMotionY, v9)
								&& (this.canScroll(this, false, ((int) v9), ((int) v6), ((int) v8)))) {
							this.mLastMotionY = v8;
							this.mInitialMotionY = v8;
							this.mLastMotionX = v6;
							
							return v2;
						}

						if (v10 > this.mTouchSlop && v10 > v7) {
							v0_1 = v9 > 0f ? this.mInitialMotionY + this.mTouchSlop: this.mInitialMotionY - this.mTouchSlop;
							this.mLastMotionY = v0_1;
							break;
						}

						if (v7 <= (((float) this.mTouchSlop))) {
							break;
						}

					} else {
						break;
					}
				} else {
					break;
				}

				return v2;
			}
			}

			if (this.mVelocityTracker == null) {
				this.mVelocityTracker = VelocityTracker.obtain();
			}

			this.mVelocityTracker.addMovement(event);
			v2 = super.onInterceptTouchEvent(event);
		}

		return v2;
	}

	/**
	 * 这里默认焦点就到第一个子节点，防止scrollView跳到自己不希望的位置
	 */
	@Override
	public void requestChildFocus(View child, View focused) {
		View view = this.getChildAt(0);
		super.requestChildFocus(child, view);
	}
}
