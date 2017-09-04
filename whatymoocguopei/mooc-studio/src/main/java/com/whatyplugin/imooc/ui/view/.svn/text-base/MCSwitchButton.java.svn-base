package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import cn.com.whatyplugin.mooc.R;

public class MCSwitchButton extends CheckBox {
	
	/**
	 * 点击的回调， 并返回是否拦截事件 true 拦截
	 * @author 马彦君
	 *
	 */
	public static abstract interface OnClickHandler {
		
		public abstract boolean onClick();
	}
	
    final class PerformClick implements Runnable {
        PerformClick(MCSwitchButton arg1, PerformClick arg2) {
        }

        private PerformClick(MCSwitchButton arg1) {
            super();
        }

        public void run() {
            MCSwitchButton.this.performClick();
        }
    }

    final class SwitchAnimation implements Runnable {
        SwitchAnimation(MCSwitchButton arg1, SwitchAnimation arg2) {
            super();
        }

        private SwitchAnimation(MCSwitchButton arg1) {
            super();
        }

        public void run() {
            if(MCSwitchButton.this.mAnimating) {
                MCSwitchButton.this.doAnimation();
                FrameAnimationController.requestAnimationFrame(((Runnable)this));
            }
        }
    }

    private final float EXTENDED_OFFSET_Y;
    private final int MAX_ALPHA;
    private final float VELOCITY;
    private int mAlpha;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private float mAnimationPosition;
    private Bitmap mBottom;
    private boolean mBroadcasting;
    private float mBtnInitPos;
    private Bitmap mBtnNormal;
    private float mBtnOffPos;
    private float mBtnOnPos;
    private float mBtnPos;
    private Bitmap mBtnPressed;
    private float mBtnWidth;
    private boolean mChecked;
    private int mClickTimeout;
    private Bitmap mCurBtnPic;
    private float mExtendOffsetY;
    private float mFirstDownX;
    private float mFirstDownY;
    private Bitmap mFrame;
    private Bitmap mMask;
    private float mMaskHeight;
    private float mMaskWidth;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    private Paint mPaint;
    private ViewParent mParent;
    private PerformClick mPerformClick;
    private float mRealPos;
    private RectF mSaveLayerRectF;
    private int mTouchSlop;
    private boolean mTurningOn;
    private float mVelocity;
    private PorterDuffXfermode mXfermode;
    private OnClickHandler onClickHandler;

    public MCSwitchButton(Context context) {
        this(context, null);
    }

    public MCSwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 16842860);
    }

    public MCSwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.MAX_ALPHA = 255;
        this.mAlpha = 255;
        this.mChecked = false;
        this.VELOCITY = 350f;
        this.EXTENDED_OFFSET_Y = 15f;
        this.initView(context);
    }

    static boolean access$0(MCSwitchButton arg1) {
        return arg1.mAnimating;
    }

    static void access$1(MCSwitchButton arg0) {
        arg0.doAnimation();
    }

    private void attemptClaimDrag() {
        this.mParent = this.getParent();
        if(this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void doAnimation() {
        this.mAnimationPosition += this.mAnimatedVelocity * 16f / 1000f;
        if(this.mAnimationPosition >= this.mBtnOnPos) {
            this.stopAnimation();
            this.mAnimationPosition = this.mBtnOnPos;
            this.setCheckedDelayed(true);
        }
        else if(this.mAnimationPosition <= this.mBtnOffPos) {
            this.stopAnimation();
            this.mAnimationPosition = this.mBtnOffPos;
            this.setCheckedDelayed(false);
        }

        this.moveView(this.mAnimationPosition);
    }

    private float getRealPos(float btnPos) {
        return btnPos - this.mBtnWidth / 2f;
    }

    private void initView(Context context) {
        float v4 = 0.5f;
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        Resources v1 = context.getResources();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mBottom = BitmapFactory.decodeResource(v1, R.drawable.bottom);
        this.mBtnPressed = BitmapFactory.decodeResource(v1, R.drawable.btn_pressed);
        this.mBtnNormal = BitmapFactory.decodeResource(v1, R.drawable.btn_pressed);
        this.mFrame = BitmapFactory.decodeResource(v1, R.drawable.frame);
        this.mMask = BitmapFactory.decodeResource(v1, R.drawable.mask);
        this.mCurBtnPic = this.mBtnNormal;
        this.mBtnWidth = ((float)this.mBtnPressed.getWidth());
        this.mMaskWidth = ((float)this.mMask.getWidth());
        this.mMaskHeight = ((float)this.mMask.getHeight());
        this.mBtnOnPos = this.mBtnWidth / 2f;
        this.mBtnOffPos = this.mMaskWidth - this.mBtnWidth / 2f;
        float v2 = this.mChecked ? this.mBtnOnPos : this.mBtnOffPos;
        this.mBtnPos = v2;
        this.mRealPos = this.getRealPos(this.mBtnPos);
        float desity = this.getResources().getDisplayMetrics().density;
        this.mVelocity = ((float)(((int)(350f * desity + v4))));
        this.mExtendOffsetY = ((float)(((int)(15f * desity + v4))));
        this.mSaveLayerRectF = new RectF(0f, this.mExtendOffsetY, ((float)this.mMask.getWidth()), (((float)this.mMask.getHeight())) + this.mExtendOffsetY);
        this.mXfermode = new PorterDuffXfermode(Mode.SRC_IN);
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    private void moveView(float position) {
        this.mBtnPos = position;
        this.mRealPos = this.getRealPos(this.mBtnPos);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        canvas.saveLayerAlpha(this.mSaveLayerRectF, this.mAlpha, 31);
        canvas.drawBitmap(this.mMask, 0f, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode(this.mXfermode);
        canvas.drawBitmap(this.mBottom, this.mRealPos, this.mExtendOffsetY, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.drawBitmap(this.mFrame, 0f, this.mExtendOffsetY, this.mPaint);
        canvas.drawBitmap(this.mCurBtnPic, this.mRealPos, this.mExtendOffsetY, this.mPaint);
        canvas.restore();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(((int)this.mMaskWidth), ((int)(this.mMaskHeight + 2f * this.mExtendOffsetY)));
    }

    public boolean onTouchEvent(MotionEvent event) {
        int mAction = event.getAction();
        float mX = event.getX();
        float mY = event.getY();
        float xgap = Math.abs(mX - this.mFirstDownX);
        float ygap = Math.abs(mY - this.mFirstDownY);
        switch(mAction) {
            case MotionEvent.ACTION_DOWN: {
                this.attemptClaimDrag();
                this.mFirstDownX = mX;
                this.mFirstDownY = mY;
                this.mCurBtnPic = this.mBtnPressed;
                float v6_1 = this.mChecked ? this.mBtnOnPos : this.mBtnOffPos;
                this.mBtnInitPos = v6_1;
                break;
            }
            case MotionEvent.ACTION_UP: {
            	boolean intercept = false;
            	if(onClickHandler!=null){
            		intercept = onClickHandler.onClick();
            	}
            	
            	//如果返回的是拦截就不往下执行了
            	if(intercept){
            		return true;
            	}
            	
                this.mCurBtnPic = this.mBtnNormal;
                float mTimeGap = ((float)(event.getEventTime() - event.getDownTime()));
                if(ygap < (((float)this.mTouchSlop)) && xgap < (((float)this.mTouchSlop)) && mTimeGap < (((float)this.mClickTimeout))) {
                    if(this.mPerformClick == null) {
                        this.mPerformClick = new PerformClick(this, null);
                    }

                    if(this.post(this.mPerformClick)) {
                    	 this.invalidate();
                         return this.isEnabled();
                    }

                    this.performClick();
                    this.invalidate();
                    return this.isEnabled();
                }

                this.startAnimation(this.mTurningOn);
                break;
            }
            case MotionEvent.ACTION_MOVE: {//去掉滑动，不然确认取消不好处理了
                /*event.getEventTime();
                event.getDownTime();
                this.mBtnPos = this.mBtnInitPos + event.getX() - this.mFirstDownX;
                if(this.mBtnPos <= this.mBtnOffPos) {
                    this.mBtnPos = this.mBtnOffPos;
                }

                if(this.mBtnPos >= this.mBtnOnPos) {
                    this.mBtnPos = this.mBtnOnPos;
                }

                boolean v6 = this.mBtnPos > (this.mBtnOffPos - this.mBtnOnPos) / 2f + this.mBtnOnPos ? true : false;
                this.mTurningOn = v6;
                this.mRealPos = this.getRealPos(this.mBtnPos);*/
                break;
            }
        }
        this.invalidate();
        return this.isEnabled();
    }

    public boolean performClick() {
        boolean flag = this.mChecked ? false : true;
        this.startAnimation(flag);
        return true;
    }

    public void setChecked(boolean checked) {
        if(this.mChecked != checked) {
            this.mChecked = checked;
            float pos = checked ? this.mBtnOnPos : this.mBtnOffPos;
            this.mBtnPos = pos;
            this.mRealPos = this.getRealPos(this.mBtnPos);
            this.invalidate();
            if(this.mBroadcasting) {
                return;
            }

            this.mBroadcasting = true;
            if(this.mOnCheckedChangeListener != null) {
                this.mOnCheckedChangeListener.onCheckedChanged(((CompoundButton)this), this.mChecked);
            }

            if(this.mOnCheckedChangeWidgetListener != null) {
                this.mOnCheckedChangeWidgetListener.onCheckedChanged(((CompoundButton)this), this.mChecked);
            }

            this.mBroadcasting = false;
        }
    }

    private void setCheckedDelayed(final boolean checked) {
        this.postDelayed(new Runnable() {
            public void run() {
                MCSwitchButton.this.setChecked(checked);
            }
        }, 10);
    }

    public void setEnabled(boolean enabled) {
        int enable = enabled ? 255 : 127;
        this.mAlpha = enable;
        super.setEnabled(enabled);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    void setOnCheckedChangeWidgetListener(CompoundButton.OnCheckedChangeListener listener) {
        this.mOnCheckedChangeWidgetListener = listener;
    }

    private void startAnimation(boolean turnOn) {
        this.mAnimating = true;
        float velocity = turnOn ? this.mVelocity : -this.mVelocity;
        this.mAnimatedVelocity = velocity;
        this.mAnimationPosition = this.mBtnPos;
        new SwitchAnimation(this, null).run();
    }

    private void stopAnimation() {
        this.mAnimating = false;
    }

    public void toggle() {
        boolean checked = this.mChecked ? false : true;
        this.setChecked(checked);
    }

	public void setOnClickHandler(OnClickHandler onClickHandler) {
		this.onClickHandler = onClickHandler;
	}
    
}

