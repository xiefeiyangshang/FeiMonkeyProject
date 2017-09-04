package com.whatyplugin.base.photoview;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.whatyplugin.base.asyncimage.MCImageHandleInterface;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnMatrixChangedListener;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnPhotoTapListener;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnViewTapListener;

public class MCPhotoView extends MCImageView implements MCIPhotoView {
    private final MCPhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public MCPhotoView(Context context) {
        this(context, null);
    }

    public MCPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MCPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new MCPhotoViewAttacher(((ImageView)this));
        if(this.mPendingScaleType != null) {
            this.setScaleType(this.mPendingScaleType);
            this.mPendingScaleType = null;
        }
    }

    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    public Matrix getDisplayMatrix() {
        return this.mAttacher.getDrawMatrix();
    }

    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    public MCIPhotoView getIPhotoViewImplementation() {
        return this.mAttacher;
    }

    @Deprecated public float getMaxScale() {
        return this.getMaximumScale();
    }

    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
    }

    @Deprecated public float getMidScale() {
        return this.getMediumScale();
    }

    @Deprecated public float getMinScale() {
        return this.getMinimumScale();
    }

    public float getMinimumScale() {
        return this.mAttacher.getMinimumScale();
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    public float getScale() {
        return this.mAttacher.getScale();
    }

    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    public Bitmap getVisibleRectangleBitmap() {
        return this.mAttacher.getVisibleRectangleBitmap();
    }

    protected void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setCanDragNoLimit(boolean isNoLimit) {
        this.mAttacher.setCanDragNoLimit(isNoLimit);
    }

    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return this.mAttacher.setDisplayMatrix(finalRectangle);
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if(this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if(this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    public void setImageURI(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled, ImageType type, MCImageHandleInterface config) {
        this.setImageUrl(url, imageLoader, width, height, isNeedScaled, type, config);
        if(this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    @Deprecated public void setMaxScale(float maxScale) {
        this.setMaximumScale(maxScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.mAttacher.setMaximumScale(maximumScale);
    }

    public void setMediumScale(float mediumScale) {
        this.mAttacher.setMediumScale(mediumScale);
    }

    @Deprecated public void setMidScale(float midScale) {
        this.setMediumScale(midScale);
    }

    @Deprecated public void setMinScale(float minScale) {
        this.setMinimumScale(minScale);
    }

    public void setMinimumScale(float minimumScale) {
        this.mAttacher.setMinimumScale(minimumScale);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        this.mAttacher.setOnDoubleTapListener(newOnDoubleTapListener);
    }

    public void setOnLongClickListener(View.OnLongClickListener l) {
        this.mAttacher.setOnLongClickListener(l);
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.mAttacher.setOnMatrixChangeListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    public void setPhotoViewRotation(float rotationDegree) {
        this.mAttacher.setRotationTo(rotationDegree);
    }

    public void setRotationBy(float rotationDegree) {
        this.mAttacher.setRotationBy(rotationDegree);
    }

    public void setRotationTo(float rotationDegree) {
        this.mAttacher.setRotationTo(rotationDegree);
    }

    public void setScale(float scale, boolean animate) {
        this.mAttacher.setScale(scale, animate);
    }

    public void setScale(float scale) {
        this.mAttacher.setScale(scale);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.mAttacher.setScale(scale, focalX, focalY, animate);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if(this.mAttacher != null) {
            this.mAttacher.setScaleType(scaleType);
        }
        else {
            this.mPendingScaleType = scaleType;
        }
    }

    public void setZoomTransitionDuration(int milliseconds) {
        this.mAttacher.setZoomTransitionDuration(milliseconds);
    }

    public void setZoomable(boolean zoomable) {
        this.mAttacher.setZoomable(zoomable);
    }
}

