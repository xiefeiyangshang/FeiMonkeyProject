package com.whatyplugin.base.photoview;



import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MCDefaultOnDoubleTapListener implements android.view.GestureDetector.OnDoubleTapListener {
    private MCPhotoViewAttacher photoViewAttacher;

    public MCDefaultOnDoubleTapListener(MCPhotoViewAttacher photoViewAttacher) {
        super();
        this.setPhotoViewAttacher(photoViewAttacher);
    }

    public boolean onDoubleTap(MotionEvent ev) {
        boolean flag = true;
        if(this.photoViewAttacher == null) {
            return false;
        }

        try {
            float scale = this.photoViewAttacher.getScale();
            float x = ev.getX();
            float y = ev.getY();
            if(scale < this.photoViewAttacher.getMediumScale()) {
                this.photoViewAttacher.setScale(this.photoViewAttacher.getMediumScale(), x, y, true);
                return flag;
            }

            if(scale >= this.photoViewAttacher.getMediumScale() && scale < this.photoViewAttacher.getMaximumScale()) {
                this.photoViewAttacher.setScale(this.photoViewAttacher.getMaximumScale(), x, y, true);
                return flag;
            }

            this.photoViewAttacher.setScale(this.photoViewAttacher.getMinimumScale(), x, y, true);
        }
        catch(ArrayIndexOutOfBoundsException v4) {
        }

        return flag;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        boolean flag = false;
        if(this.photoViewAttacher != null) {
            ImageView imageView = this.photoViewAttacher.getImageView();
            if(this.photoViewAttacher.getOnPhotoTapListener() != null) {
                RectF rectF = this.photoViewAttacher.getDisplayRect();
                if(rectF != null) {
                    float x = e.getX();
                    float y = e.getY();
                    if(rectF.contains(x, y)) {
                        this.photoViewAttacher.getOnPhotoTapListener().onPhotoTap(((View)imageView), (x - rectF.left) / rectF.width(), (y - rectF.top) / rectF.height());
                        return true;
                    }
                }
            }

            if(this.photoViewAttacher.getOnViewTapListener() == null) {
                return flag;
            }

            this.photoViewAttacher.getOnViewTapListener().onViewTap(((View)imageView), e.getX(), e.getY());
        }

        return flag;
    }

    public void setPhotoViewAttacher(MCPhotoViewAttacher newPhotoViewAttacher) {
        this.photoViewAttacher = newPhotoViewAttacher;
    }
}

