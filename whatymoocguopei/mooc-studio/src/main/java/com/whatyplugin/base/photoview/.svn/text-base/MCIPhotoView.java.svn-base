package com.whatyplugin.base.photoview;



import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnMatrixChangedListener;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnPhotoTapListener;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnViewTapListener;

public interface MCIPhotoView {
    public static final float DEFAULT_MAX_SCALE = 0f;
    public static final float DEFAULT_MID_SCALE = 0f;
    public static final float DEFAULT_MIN_SCALE = 0f;
    public static final int DEFAULT_ZOOM_DURATION = 200;

    boolean canZoom();

    Matrix getDisplayMatrix();

    RectF getDisplayRect();

    MCIPhotoView getIPhotoViewImplementation();

    @Deprecated float getMaxScale();

    float getMaximumScale();

    float getMediumScale();

    @Deprecated float getMidScale();

    @Deprecated float getMinScale();

    float getMinimumScale();

    OnPhotoTapListener getOnPhotoTapListener();

    OnViewTapListener getOnViewTapListener();

    float getScale();

    ImageView.ScaleType getScaleType();

    Bitmap getVisibleRectangleBitmap();

    void setAllowParentInterceptOnEdge(boolean arg1);

    boolean setDisplayMatrix(Matrix arg1);

    @Deprecated void setMaxScale(float arg1);

    void setMaximumScale(float arg1);

    void setMediumScale(float arg1);

    @Deprecated void setMidScale(float arg1);

    @Deprecated void setMinScale(float arg1);

    void setMinimumScale(float arg1);

    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener arg1);

    void setOnLongClickListener(View.OnLongClickListener arg1);

    void setOnMatrixChangeListener(OnMatrixChangedListener arg1);

    void setOnPhotoTapListener(OnPhotoTapListener arg1);

    void setOnViewTapListener(OnViewTapListener arg1);

    void setPhotoViewRotation(float arg1);

    void setRotationBy(float arg1);

    void setRotationTo(float arg1);

    void setScale(float arg1);

    void setScale(float arg1, float arg2, float arg3, boolean arg4);

    void setScale(float arg1, boolean arg2);

    void setScaleType(ImageView.ScaleType arg1);

    void setZoomTransitionDuration(int arg1);

    void setZoomable(boolean arg1);
}

