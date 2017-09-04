package com.whatyplugin.base.asyncimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageContainer mImageContainer;
    private MCImageHandleInterface mImageHandleConfig;
    private int mImageHeight;
    private ImageLoader mImageLoader;
    private int mImageWidth;
    private String mUrl;
    private ImageType type;
    private Context mContext;
    private static String TAG = "MCImageView";
    private OnLoadFinishListener mFinishListener;
    private  static  int random;

    //图片加载完成的回调
    public interface OnLoadFinishListener {
        public abstract void onFinish();
    }

    public MCImageView(Context context) {
        this(context, null);

    }

    public MCImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MCImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        int v1 = -2;
        try {
            int v9 = this.getWidth();
            int v6 = this.getHeight();
            int v7 = this.getLayoutParams() == null || this.getLayoutParams().height != v1 || this.getLayoutParams().width != v1 ? 0 : 1;
            if (v9 != 0 || v6 != 0 || v7 != 0) {
                if (TextUtils.isEmpty(this.mUrl)) {
                    if (this.mImageContainer != null) {
                        this.mImageContainer.cancelRequest();
                        this.mImageContainer = null;
                    }

                    this.setDefaultImageOrNull();
                    return;
                }

                if (this.mImageContainer != null && this.mImageContainer.getRequestUrl() != null) {
                    if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                        this.mImageContainer.cancelRequest();
                        this.setDefaultImageOrNull();
                    } else {
                        return;
                    }
                }

                if (this.mUrl == null || !this.mUrl.startsWith("http")) {
                    MCLog.e(TAG, "无法进行网络请求的路径(设置为默认图标)：" + this.mUrl);
                    MCImageView.this.setImageResource(MCImageView.this.mDefaultImageId);
                    return;
                }

                this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageListener() {
                    public void onErrorResponse(VolleyError error) {
                        if (MCImageView.this.mErrorImageId != 0) {
                            MCImageView.this.setImageResource(MCImageView.this.mErrorImageId);
                        }
                    }

                    public void onResponse(final ImageContainer response, boolean isImmediate) {
                        MCImageView view = MCImageView.this;
                        if ((isImmediate) && (isInLayoutPass)) {
                            view.post(new Runnable() {
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                        } else if (response.getBitmap() != null) {
                            view.setImageBitmap(response.getBitmap());
                            if (mFinishListener != null) {
                                mFinishListener.onFinish();
                            }
                        } else if (MCImageView.this.mDefaultImageId != 0) {
                            view.setImageResource(MCImageView.this.mDefaultImageId);
                            if (mFinishListener != null) {
                                mFinishListener.onFinish();
                            }
                        }
                    }
                }, this.mImageWidth, this.mImageHeight);
            }

            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    protected void onDetachedFromWindow() {
        Bitmap v1 = null;
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            this.setImageBitmap(v1);
            this.mImageContainer = null;
        }

        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        this.loadImageIfNecessary(true);
    }

    private void setDefaultImageOrNull() {
        if (this.mDefaultImageId != 0) {
            this.setImageResource(this.mDefaultImageId);
        }
    }

    public void setDefaultImageResId(int defaultImage) {
        this.mDefaultImageId = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.mErrorImageId = errorImage;
    }

    public void setImageBitmap(Bitmap bm) {
        if (this.mImageHandleConfig != null) {
            Bitmap bitmap = this.mImageHandleConfig.scaleBitmap(bm, this.mImageWidth, this.mImageHeight);
            if (bitmap != null) {
                bm = bitmap;
            }

            bm = this.mImageHandleConfig.handleBitmapShape(bm, this.type);
        }

        super.setImageBitmap(bm);
    }

    /**
     * 外部用到
     * @param url
     * @param imageLoader
     * @param width
     * @param height
     * @param isNeedScaled
     * @param type
     * @param config
     */
    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled, ImageType type, MCImageHandleInterface config) {

        //用于处理用户中心头像问题  只更新自己的头像
        String loginId = MCSaveData.getUserInfo(Contants.USERID, mContext).toString();
        if (url != null && this.random != 0 && loginId != null && url.matches("[:0-9a-zA-Z\\/\\.]*(" + loginId + "\\/*)*[0-9a-zA-Z\\/\\.]*")) {
            url = url + "?" + random;
        }
        this.mUrl = StringUtils.encodeUrl(url);
        //用于补全 非 全路径的图片路径
        if (!this.mUrl.startsWith("http")) {
            this.mUrl = RequestUrl.getInstance().startUrl + this.mUrl;
        }

        this.mImageLoader = imageLoader;
        this.mImageWidth = width;
        this.mImageHeight = height;
        this.type = type;
        this.mImageHandleConfig = config;
        this.loadImageIfNecessary(false);
    }

    /**
     * 外部用到
     * @param url
     */
    public void setImageUrl(String url) {
        ImageLoader imageLoader = MCCacheManager.getInstance().getImageLoader();
        this.setImageUrl(url, imageLoader, 0, 0, false, null);
    }

    /**
     * 外部用到
     * @param url
     * @param random
     */
    public void setImageUrl(String url, int random) {
        ImageLoader imageLoader = MCCacheManager.getInstance().getImageLoader();
        this.setImageUrl(url, imageLoader, 0, 0, false, null, random);
    }

    /**
     * 外部用到
     * @param url
     * @param imageLoader
     */
    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.setImageUrl(url, imageLoader, 0, 0, false, null);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled, ImageType type, int random) {
        this.random = random;
        this.setImageUrl(url, imageLoader, width, height, isNeedScaled, null, null);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled, ImageType type) {
        this.setImageUrl(url, imageLoader, width, height, isNeedScaled, null, null);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height) {
        this.setImageUrl(url, imageLoader, width, height, false, null);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled) {
        this.setImageUrl(url, imageLoader, width, height, isNeedScaled, null);
    }

    public void setOnLoadFinishListener(OnLoadFinishListener listener) {
        this.mFinishListener = listener;
    }
}

