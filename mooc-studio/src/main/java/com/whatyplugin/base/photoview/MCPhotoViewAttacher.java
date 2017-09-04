package com.whatyplugin.base.photoview;



import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.whatyplugin.base.photoview.gestures.MCGestureDetector;
import com.whatyplugin.base.photoview.gestures.MCOnGestureListener;
import com.whatyplugin.base.photoview.gestures.MCVersionedGestureDetector;
import com.whatyplugin.base.photoview.scrollerproxy.MCScrollerProxy;

public class MCPhotoViewAttacher implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, MCIPhotoView, MCOnGestureListener {
    class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime;
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(MCPhotoViewAttacher arg3, float currentZoom, float targetZoom, float focalX, float focalY) {
            super();
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            this.mStartTime = System.currentTimeMillis();
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
        }

        private float interpolate() {
            return MCPhotoViewAttacher.sInterpolator.getInterpolation(Math.min(1f, (((float)(System.currentTimeMillis() - this.mStartTime))) * 1f / (((float)MCPhotoViewAttacher.this.ZOOM_DURATION))));
        }

        public void run() {
            ImageView v1 = MCPhotoViewAttacher.this.getImageView();
            if(v1 != null) {
                float v3 = this.interpolate();
                float v0 = (this.mZoomStart + (this.mZoomEnd - this.mZoomStart) * v3) / MCPhotoViewAttacher.this.getScale();
                MCPhotoViewAttacher.this.mSuppMatrix.postScale(v0, v0, this.mFocalX, this.mFocalY);
                MCPhotoViewAttacher.this.checkAndDisplayMatrix();
                if(v3 < 1f) {
                    MCCompat.postOnAnimation(((View)v1), ((Runnable)this));
                }
            }
        }
    }

    class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final MCScrollerProxy mScroller;

        public FlingRunnable(MCPhotoViewAttacher arg2, Context context) {
            super();
            this.mScroller = MCScrollerProxy.getScroller(context);
        }

        public void cancelFling() {
            this.mScroller.forceFinished(true);
        }

        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            int v8;
            int v7;
            int v6;
            int v5;
            RectF v11 = MCPhotoViewAttacher.this.getDisplayRect();
            if(v11 != null) {
                int v1 = Math.round(-v11.left);
                if((((float)viewWidth)) < v11.width()) {
                    v5 = 0;
                    v6 = Math.round(v11.width() - (((float)viewWidth)));
                }
                else {
                    v6 = v1;
                    v5 = v1;
                }

                int v2 = Math.round(-v11.top);
                if((((float)viewHeight)) < v11.height()) {
                    v7 = 0;
                    v8 = Math.round(v11.height() - (((float)viewHeight)));
                }
                else {
                    v8 = v2;
                    v7 = v2;
                }

                this.mCurrentX = v1;
                this.mCurrentY = v2;
                if(v1 == v6 && v2 == v8) {
                    return;
                }

                this.mScroller.fling(v1, v2, velocityX, velocityY, v5, v6, v7, v8, 0, 0);
            }
        }

        public void run() {
            if(!this.mScroller.isFinished()) {
                ImageView v0 = MCPhotoViewAttacher.this.getImageView();
                if(v0 != null && (this.mScroller.computeScrollOffset())) {
                    int v1 = this.mScroller.getCurrX();
                    int v2 = this.mScroller.getCurrY();
                    MCPhotoViewAttacher.this.mSuppMatrix.postTranslate(((float)(this.mCurrentX - v1)), ((float)(this.mCurrentY - v2)));
                    MCPhotoViewAttacher.this.setImageViewMatrix(MCPhotoViewAttacher.this.getDrawMatrix());
                    this.mCurrentX = v1;
                    this.mCurrentY = v2;
                    MCCompat.postOnAnimation(((View)v0), ((Runnable)this));
                }
            }
        }
    }

    public interface OnMatrixChangedListener {
        void onMatrixChanged(RectF arg1);
    }

    public interface OnPhotoTapListener {
        void onPhotoTap(View arg1, float arg2, float arg3);
    }

    public interface OnViewTapListener {
        void onViewTap(View arg1, float arg2, float arg3);
    }

    private static boolean DEBUG = false;
    static final int EDGE_BOTH = 2;
    static final int EDGE_LEFT = 0;
    static final int EDGE_NONE = -1;
    static final int EDGE_RIGHT = 1;
    private static final String LOG_TAG = "PhotoViewAttacher";
    int ZOOM_DURATION;
    private boolean isNoLimit;
    private boolean mAllowParentInterceptOnEdge;
    private final Matrix mBaseMatrix;
    private FlingRunnable mCurrentFlingRunnable;
    private final RectF mDisplayRect;
    private final Matrix mDrawMatrix;
    private GestureDetector mGestureDetector;
    private WeakReference mImageView;
    private int mIvBottom;
    private int mIvLeft;
    private int mIvRight;
    private int mIvTop;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private final float[] mMatrixValues;
    private float mMaxScale;
    private float mMidScale;
    private float mMinScale;
    private OnPhotoTapListener mPhotoTapListener;
    private MCGestureDetector mScaleDragDetector;
    private ImageView.ScaleType mScaleType;
    private int mScrollEdge;
    private final Matrix mSuppMatrix;
    private OnViewTapListener mViewTapListener;
    private boolean mZoomEnabled;
    static Interpolator sInterpolator;

  //  static int[] .SWITCH_TABLE.android.widget.ImageView.ScaleType() {}

    static {
        MCPhotoViewAttacher.DEBUG = Log.isLoggable("PhotoViewAttacher", 3);
        MCPhotoViewAttacher.sInterpolator = new AccelerateDecelerateInterpolator();
    }

    public MCPhotoViewAttacher(ImageView imageView) {
        super();
        this.ZOOM_DURATION = 200;
        this.mMinScale = 1f;
        this.mMidScale = 1.75f;
        this.mMaxScale = 3f;
        this.mAllowParentInterceptOnEdge = true;
        this.isNoLimit = false;
        this.mBaseMatrix = new Matrix();
        this.mDrawMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mDisplayRect = new RectF();
        this.mMatrixValues = new float[9];
        this.mScrollEdge = 2;
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mImageView = new WeakReference(imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.setOnTouchListener(((View.OnTouchListener)this));
        ViewTreeObserver v0 = imageView.getViewTreeObserver();
        if(v0 != null) {
            v0.addOnGlobalLayoutListener(((ViewTreeObserver.OnGlobalLayoutListener)this));
        }

        MCPhotoViewAttacher.setImageViewScaleTypeMatrix(imageView);
        if(!imageView.isInEditMode()) {
            this.mScaleDragDetector = MCVersionedGestureDetector.newInstance(imageView.getContext(), ((MCOnGestureListener)this));
            this.mGestureDetector = new GestureDetector(imageView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                public void onLongPress(MotionEvent e) {
                    if(MCPhotoViewAttacher.this.mLongClickListener != null) {
                        MCPhotoViewAttacher.this.mLongClickListener.onLongClick(MCPhotoViewAttacher.this.getImageView());
                    }
                }
            });
            this.mGestureDetector.setOnDoubleTapListener(new MCDefaultOnDoubleTapListener(this));
            this.setZoomable(true);
        }
    }

    static Matrix access$0(MCPhotoViewAttacher arg1) {
        return arg1.mSuppMatrix;
    }

    static void access$1(MCPhotoViewAttacher arg0) {
        arg0.checkAndDisplayMatrix();
    }

    static boolean access$2() {
        return MCPhotoViewAttacher.DEBUG;
    }

    static void access$3(MCPhotoViewAttacher arg0, Matrix arg1) {
        arg0.setImageViewMatrix(arg1);
    }

    static View.OnLongClickListener access$5(MCPhotoViewAttacher arg1) {
        return arg1.mLongClickListener;
    }

    public boolean canZoom() {
        return this.mZoomEnabled;
    }

    private void cancelFling() {
        if(this.mCurrentFlingRunnable != null) {
            this.mCurrentFlingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    private void checkAndDisplayMatrix() {
        if(this.isNoLimit) {
            this.setImageViewMatrix(this.getDrawMatrix());
        }
        else if(this.checkMatrixBounds()) {
            this.setImageViewMatrix(this.getDrawMatrix());
        }
    }

    private void checkImageViewScaleType() {
        ImageView v0 = this.getImageView();
        if(v0 != null && !(v0 instanceof MCIPhotoView) && !ImageView.ScaleType.MATRIX.equals(v0.getScaleType())) {
            throw new IllegalStateException("The ImageView\'s ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    private boolean checkMatrixBounds() {
		return true;}

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if(minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        }

        if(midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    public void cleanup() {
        View.OnTouchListener v3 = null;
        if(this.mImageView != null) {
            Object v0 = this.mImageView.get();
            if(v0 != null) {
                ViewTreeObserver v1 = ((ImageView)v0).getViewTreeObserver();
                if(v1 != null && (v1.isAlive())) {
                    v1.removeGlobalOnLayoutListener(((ViewTreeObserver.OnGlobalLayoutListener)this));
                }

                ((ImageView)v0).setOnTouchListener(v3);
                this.cancelFling();
            }

            if(this.mGestureDetector != null) {
                this.mGestureDetector.setOnDoubleTapListener(((GestureDetector.OnDoubleTapListener)v3));
            }

            this.mMatrixChangeListener = ((OnMatrixChangedListener)v3);
            this.mPhotoTapListener = ((OnPhotoTapListener)v3);
            this.mViewTapListener = ((OnViewTapListener)v3);
            this.mImageView = ((WeakReference)v3);
        }
    }

    public Matrix getDisplayMatrix() {
        return new Matrix(this.getDrawMatrix());
    }

    public RectF getDisplayRect() {
        if(!this.isNoLimit) {
            this.checkMatrixBounds();
        }

        return this.getDisplayRect(this.getDrawMatrix());
    }

    private RectF getDisplayRect(Matrix matrix) {
        RectF v2;
        ImageView v1 = this.getImageView();
        if(v1 != null) {
            Drawable v0 = v1.getDrawable();
            if(v0 != null) {
                this.mDisplayRect.set(0f, 0f, ((float)v0.getIntrinsicWidth()), ((float)v0.getIntrinsicHeight()));
                matrix.mapRect(this.mDisplayRect);
                v2 = this.mDisplayRect;
            }
            else {
            	  v2 = null;
            }
        }
        else {
            v2 = null;
        }

        return v2;
    }

    public Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    public MCIPhotoView getIPhotoViewImplementation() {
        return this;
    }

    public ImageView getImageView() {
        Object v0 = null;
        if(this.mImageView != null) {
            v0 = this.mImageView.get();
        }

        if(v0 == null) {
            this.cleanup();
            Log.i("PhotoViewAttacher", "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
        }

        return ((ImageView)v0);
    }

    private int getImageViewHeight(ImageView imageView) {
        int v0 = imageView == null ? 0 : imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
        return v0;
    }

    private int getImageViewWidth(ImageView imageView) {
        int v0 = imageView == null ? 0 : imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
        return v0;
    }

    @Deprecated public float getMaxScale() {
        return this.getMaximumScale();
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    @Deprecated public float getMidScale() {
        return this.getMediumScale();
    }

    @Deprecated public float getMinScale() {
        return this.getMinimumScale();
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    public float getScale() {
        return FloatMath.sqrt((((float)Math.pow(((double)this.getValue(this.mSuppMatrix, 0)), 2))) + (((float)Math.pow(((double)this.getValue(this.mSuppMatrix, 3)), 2))));
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[whichValue];
    }

    public Bitmap getVisibleRectangleBitmap() {
        ImageView v0 = this.getImageView();
        Bitmap v1 = v0 == null ? null : v0.getDrawingCache();
        return v1;
    }

    private static boolean hasDrawable(ImageView imageView) {
        boolean v0 = imageView == null || imageView.getDrawable() == null ? false : true;
        return v0;
    }

    private static boolean isSupportedScaleType(ImageView.ScaleType scaleType) {
        boolean v0;
        if(scaleType == null) {
            v0 = false;
        }
        else {
//            switch(MCPhotoViewAttacher..SWITCH_TABLE.android.widget.ImageView.ScaleType()[scaleType.ordinal()]) {
//                case 8: {
//                    goto label_9;
//                }
//                default: {
//                    return true;
//                }
//            }
        }

        throw new IllegalArgumentException(String.valueOf(scaleType.name()) + " is not supported in PhotoView");
    }

    public void onDrag(float dx, float dy) {
        if(!this.mScaleDragDetector.isScaling()) {
            ImageView v0 = this.getImageView();
            this.mSuppMatrix.postTranslate(dx, dy);
            this.checkAndDisplayMatrix();
            ViewParent v1 = v0.getParent();
            if((this.mAllowParentInterceptOnEdge) && !this.mScaleDragDetector.isScaling()) {
                if(this.mScrollEdge != 2 && (this.mScrollEdge != 0 || dx < 1f)) {
                    if(this.mScrollEdge != 1) {
                        return;
                    }

                    if(dx > -1f) {
                        return;
                    }
                }

                if(v1 == null) {
                    return;
                }

                v1.requestDisallowInterceptTouchEvent(false);
                return;
            }

            if(v1 == null) {
                return;
            }

            v1.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void onFling(float startX, float startY, float velocityX, float velocityY) {
        if(!this.isNoLimit) {
            ImageView v0 = this.getImageView();
            this.mCurrentFlingRunnable = new FlingRunnable(this, v0.getContext());
            this.mCurrentFlingRunnable.fling(this.getImageViewWidth(v0), this.getImageViewHeight(v0), ((int)velocityX), ((int)velocityY));
            v0.post(this.mCurrentFlingRunnable);
        }
    }

    public void onGlobalLayout() {
        ImageView v1 = this.getImageView();
        if(v1 != null) {
            if(this.mZoomEnabled) {
                int v4 = v1.getTop();
                int v3 = v1.getRight();
                int v0 = v1.getBottom();
                int v2 = v1.getLeft();
                if(v4 == this.mIvTop && v0 == this.mIvBottom && v2 == this.mIvLeft && v3 == this.mIvRight) {
                    return;
                }

                this.updateBaseMatrix(v1.getDrawable());
                this.mIvTop = v4;
                this.mIvRight = v3;
                this.mIvBottom = v0;
                this.mIvLeft = v2;
            }
            else {
                this.updateBaseMatrix(v1.getDrawable());
            }
        }
    }

    public void onScale(float scaleFactor, float focusX, float focusY) {
        if(this.getScale() < this.mMaxScale || scaleFactor < 1f) {
            this.mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            this.checkAndDisplayMatrix();
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean v6 = false;
        if((this.mZoomEnabled) && (MCPhotoViewAttacher.hasDrawable((ImageView) v))) {
            ViewParent v7 = v.getParent();
            switch(ev.getAction()) {
                case 0: {
                    if(v7 != null) {
                        v7.requestDisallowInterceptTouchEvent(true);
                    }
                    else {
                        Log.i("PhotoViewAttacher", "onTouch getParent() returned null");
                    }

                    this.cancelFling();
                    break;
                }
                case 1: 
                case 3: {
                    if(this.getScale() >= this.mMinScale) {
                    	if(this.mScaleDragDetector != null && (this.mScaleDragDetector.onTouchEvent(ev))) {
                            v6 = true;
                        }
                    }

                    RectF v8 = this.getDisplayRect();
                    if(v8 == null) {
                    	if(this.mScaleDragDetector != null && (this.mScaleDragDetector.onTouchEvent(ev))) {
                            v6 = true;
                        }
                    }

                    v.post(new AnimatedZoomRunnable(this, this.getScale(), this.mMinScale, v8.centerX(), v8.centerY()));
                    v6 = true;
                    break;
                }
            }

            if(this.mScaleDragDetector != null && (this.mScaleDragDetector.onTouchEvent(ev))) {
                v6 = true;
            }

            if(this.mGestureDetector == null) {
                return v6;
            }

            if(!this.mGestureDetector.onTouchEvent(ev)) {
                return v6;
            }

            v6 = true;
        }

        return v6;
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        this.setImageViewMatrix(this.getDrawMatrix());
        if(!this.isNoLimit) {
            this.checkMatrixBounds();
        }
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    public void setCanDragNoLimit(boolean isNoLimit) {
        this.isNoLimit = isNoLimit;
    }

    public boolean setDisplayMatrix(Matrix finalMatrix) {
        boolean v1 = false;
        if(finalMatrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }

        ImageView v0 = this.getImageView();
        if(v0 != null && v0.getDrawable() != null) {
            this.mSuppMatrix.set(finalMatrix);
            this.setImageViewMatrix(this.getDrawMatrix());
            if(!this.isNoLimit) {
                this.checkMatrixBounds();
            }

            v1 = true;
        }

        return v1;
    }

    private void setImageViewMatrix(Matrix matrix) {
        ImageView v1 = this.getImageView();
        if(v1 != null) {
            this.checkImageViewScaleType();
            v1.setImageMatrix(matrix);
            if(this.mMatrixChangeListener != null) {
                RectF v0 = this.getDisplayRect(matrix);
                if(v0 != null) {
                    this.mMatrixChangeListener.onMatrixChanged(v0);
                }
            }
        }
    }

    private static void setImageViewScaleTypeMatrix(ImageView imageView) {
        if(imageView != null && !(imageView instanceof MCIPhotoView) && !ImageView.ScaleType.MATRIX.equals(imageView.getScaleType())) {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
    }

    @Deprecated public void setMaxScale(float maxScale) {
        this.setMaximumScale(maxScale);
    }

    public void setMaximumScale(float maximumScale) {
        MCPhotoViewAttacher.checkZoomLevels(this.mMinScale, this.mMidScale, maximumScale);
        this.mMaxScale = maximumScale;
    }

    public void setMediumScale(float mediumScale) {
        MCPhotoViewAttacher.checkZoomLevels(this.mMinScale, mediumScale, this.mMaxScale);
        this.mMidScale = mediumScale;
    }

    @Deprecated public void setMidScale(float midScale) {
        this.setMediumScale(midScale);
    }

    @Deprecated public void setMinScale(float minScale) {
        this.setMinimumScale(minScale);
    }

    public void setMinimumScale(float minimumScale) {
        MCPhotoViewAttacher.checkZoomLevels(minimumScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minimumScale;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        if(newOnDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
        }
        else {
            this.mGestureDetector.setOnDoubleTapListener(new MCDefaultOnDoubleTapListener(this));
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.mMatrixChangeListener = listener;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    public void setPhotoViewRotation(float degrees) {
        this.mSuppMatrix.setRotate(degrees % 360f);
        this.checkAndDisplayMatrix();
    }

    public void setRotationBy(float degrees) {
        this.mSuppMatrix.postRotate(degrees % 360f);
        this.checkAndDisplayMatrix();
    }

    public void setRotationTo(float degrees) {
        this.mSuppMatrix.setRotate(degrees % 360f);
        this.checkAndDisplayMatrix();
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        ImageView v6 = this.getImageView();
        if(v6 != null && scale >= this.mMinScale && scale <= this.mMaxScale) {
            if(animate) {
                v6.post(new AnimatedZoomRunnable(this, this.getScale(), scale, focalX, focalY));
            }
            else {
                this.mSuppMatrix.setScale(scale, scale, focalX, focalY);
                this.checkAndDisplayMatrix();
            }
        }
    }

    public void setScale(float scale) {
        this.setScale(scale, false);
    }

    public void setScale(float scale, boolean animate) {
        ImageView v0 = this.getImageView();
        if(v0 != null) {
            this.setScale(scale, ((float)(v0.getRight() / 2)), ((float)(v0.getBottom() / 2)), animate);
        }
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if((MCPhotoViewAttacher.isSupportedScaleType(scaleType)) && scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.update();
        }
    }

    public void setZoomTransitionDuration(int milliseconds) {
        if(milliseconds < 0) {
            milliseconds = 200;
        }

        this.ZOOM_DURATION = milliseconds;
    }

    public void setZoomable(boolean zoomable) {
        this.mZoomEnabled = zoomable;
        this.update();
    }

    public void update() {
        ImageView v0 = this.getImageView();
        if(v0 != null) {
            if(this.mZoomEnabled) {
                MCPhotoViewAttacher.setImageViewScaleTypeMatrix(v0);
                this.updateBaseMatrix(v0.getDrawable());
            }
            else {
                this.resetMatrix();
            }
        }
    }

    private void updateBaseMatrix(Drawable d) {
        float v6;
        float v13 = 2f;
        ImageView v3 = this.getImageView();
        if(v3 != null && d != null) {
            float v8 = ((float)this.getImageViewWidth(v3));
            float v7 = ((float)this.getImageViewHeight(v3));
            int v1 = d.getIntrinsicWidth();
            int v0 = d.getIntrinsicHeight();
            this.mBaseMatrix.reset();
            float v9 = v8 / (((float)v1));
            float v2 = v7 / (((float)v0));
            if(this.mScaleType == ImageView.ScaleType.CENTER) {
                this.mBaseMatrix.postTranslate((v8 - (((float)v1))) / v13, (v7 - (((float)v0))) / v13);
            }
            else if(this.mScaleType == ImageView.ScaleType.CENTER_CROP) {
                v6 = Math.max(v9, v2);
                this.mBaseMatrix.postScale(v6, v6);
                this.mBaseMatrix.postTranslate((v8 - (((float)v1)) * v6) / v13, (v7 - (((float)v0)) * v6) / v13);
            }
            else if(this.mScaleType == ImageView.ScaleType.CENTER_INSIDE) {
                v6 = Math.min(1f, Math.min(v9, v2));
                this.mBaseMatrix.postScale(v6, v6);
                this.mBaseMatrix.postTranslate((v8 - (((float)v1)) * v6) / v13, (v7 - (((float)v0)) * v6) / v13);
            }
            else {
                RectF v5 = new RectF(0f, 0f, ((float)v1), ((float)v0));
                RectF v4 = new RectF(0f, 0f, v8, v7);
                int temp = 0;
                switch(temp) {//switch xxtype
                    case 4: {
                    	 this.mBaseMatrix.setRectToRect(v5, v4, Matrix.ScaleToFit.CENTER);
                    }
                    case 5: {
                    	 this.mBaseMatrix.setRectToRect(v5, v4, Matrix.ScaleToFit.END);
                    }
                    case 6: {
                    	 this.mBaseMatrix.setRectToRect(v5, v4, Matrix.ScaleToFit.START);
                    }
                    case 7: {
                    	this.mBaseMatrix.setRectToRect(v5, v4, Matrix.ScaleToFit.FILL);
                    }
                }

               this.resetMatrix();
            }
        }
    }
}

