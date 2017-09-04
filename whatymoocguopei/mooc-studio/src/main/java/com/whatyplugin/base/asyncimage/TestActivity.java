package com.whatyplugin.base.asyncimage;

import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;

public class TestActivity extends MCBaseActivity {
    public TestActivity() {
        super();
    }

    protected void onCreate(Bundle savedInstanceState) {
        int v10 = -2;
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(((Context)this));
        this.setContentView(((View)layout));
        layout.setOrientation(1);
        int i;
        for(i = 0; i < 10; ++i) {
            MCImageView imageView = new MCImageView(((Context)this));
            imageView.setLayoutParams(new AbsListView.LayoutParams(v10, v10));
            imageView.setImageUrl("http://img.mukewang.com/52d11ce00001e81a06000338.jpg", MCCacheManager.getInstance().getImageLoader(), (i + 1) * 100, (i + 1) * 100, false, ImageType.RECTANGULAR_IMAGE, new MCImageHandleInterface() {
                public Bitmap handleBitmapShape(Bitmap bitmap, ImageType type) {
                    return bitmap;
                }

                public Bitmap scaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
                    return null;
                }
            });
            layout.addView(((View)imageView));
        }
    }
}

