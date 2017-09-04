package com.whatyplugin.imooc.ui.showimage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageHandleInterface;
import com.whatyplugin.base.photoview.MCPhotoView;
import com.whatyplugin.base.photoview.MCPhotoViewAttacher.OnViewTapListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.utils.UploadUtils;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.uikit.toast.MCToast;

public class MCShowBigImageActivity extends MCBaseActivity implements View.OnClickListener {
    class ImageAdapter extends PagerAdapter {
        private Context context;

        public ImageAdapter(MCShowBigImageActivity arg1, Context context) {
            super();
            this.context = context;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View)object));
        }

        public int getCount() {
            int count = MCShowBigImageActivity.this.mImageUrlList == null || MCShowBigImageActivity.this.mImageUrlList.size() <= 0 ? 0 : MCShowBigImageActivity.this.mImageUrlList.size();
            return count;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            MCPhotoView photoView = new MCPhotoView(this.context);
            Object url = MCShowBigImageActivity.this.mImageUrlList.get(position);
            ((ViewPager)container).addView(((View)photoView), -1, -1);
            if(MCShowBigImageActivity.this.isNeedSetScaleType) {
                photoView.setScaleType(ScaleType.CENTER_INSIDE);
            }

            photoView.setImageUrl(((String)url), MCCacheManager.getInstance().getImageLoader(), 0, 0, true, ImageType.RECTANGULAR_IMAGE, new MCImageHandleInterface() {
                public Bitmap handleBitmapShape(Bitmap bitmap, ImageType type) {
                    return bitmap;
                }

                public Bitmap scaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
                    return null;
                }
            });
            photoView.setOnViewTapListener(new OnViewTapListener() {
                public void onViewTap(View view, float x, float y) {
                    MCShowBigImageActivity.this.finishAcitiviy((MCPhotoView) view);
                }
            });
            if(MCShowBigImageActivity.this.b != null && !MCShowBigImageActivity.this.b.containsKey(Contants.SINGLE_URL) && (MCShowBigImageActivity.this.b.containsKey(Contants.MULTI_URL))) {
                photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if(MCShowBigImageActivity.this.mPop == null) {
                            MCShowBigImageActivity.this.mPop = MCShowBigImageActivity.this.initPicPop();
                        }

                        MCShowBigImageActivity.this.mPop.showAtLocation(LayoutInflater.from(MCShowBigImageActivity.this).inflate(R.layout.show_big_image, null), 80, 0, 0);
                        MCShowBigImageActivity.this.mPop.setFocusable(true);
                        return false;
                    }
                });
            }

            return photoView;
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            boolean flag = arg0 == (((View)arg1)) ? true : false;
            return flag;
        }
    }

    private final float ORIGINAL_SCALE;
    private Bundle b;
    private String imageUrl;
    private boolean isNeedSetScaleType;
    private List mImageUrlList;
    private MCPhotoView mPhotoView;
    private PopupWindow mPop;
    private ViewPager mViewPager;
    private OnPageChangeListener pageChangeListener;
    private int pageIndex;
    private String path;
    private File sdcardDir;

    public MCShowBigImageActivity() {
        super();
        this.ORIGINAL_SCALE = 1f;
        this.mImageUrlList = new ArrayList();
        this.sdcardDir = Environment.getExternalStorageDirectory();
        this.path = String.valueOf(this.sdcardDir.getPath()) + "/cn.com.open.mooc/image";
        this.isNeedSetScaleType = false;
        this.pageChangeListener = new OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int arg0) {
                MCShowBigImageActivity.this.pageIndex = arg0;
            }
        };
    }

    static void access$0(MCShowBigImageActivity arg0, int arg1) {
        arg0.pageIndex = arg1;
    }

    static List access$1(MCShowBigImageActivity arg1) {
        return arg1.mImageUrlList;
    }

    static boolean access$2(MCShowBigImageActivity arg1) {
        return arg1.isNeedSetScaleType;
    }

    static void access$3(MCShowBigImageActivity arg0, MCPhotoView arg1) {
        arg0.finishAcitiviy(arg1);
    }

    static Bundle access$4(MCShowBigImageActivity arg1) {
        return arg1.b;
    }

    static PopupWindow access$5(MCShowBigImageActivity arg1) {
        return arg1.mPop;
    }

    static PopupWindow access$6(MCShowBigImageActivity arg1) {
        return arg1.initPicPop();
    }

    static void access$7(MCShowBigImageActivity arg0, PopupWindow arg1) {
        arg0.mPop = arg1;
    }

    public boolean fileIsExists() {
        boolean isExist;
        if("mounted".equals(Environment.getExternalStorageState())) {
            File file = new File(this.path);
            if(!file.exists()) {
                file.mkdirs();
            }

            isExist = true;
        }
        else {
            isExist = false;
        }

        return isExist;
    }

    public void finish() {
        super.finish();
    }

    private void finishAcitiviy(MCPhotoView photoView) {
        float v1 = 1f;
        if(photoView != null) {
            if(photoView.getScale() != v1) {
                photoView.setScale(v1, true);
            }
            else {
                this.finish();
                this.overridePendingTransition(0, R.anim.show_image_out);
            }
        }
    }

    private void initData() {
        this.b = this.getIntent().getExtras();
        if(this.b != null) {
            if(this.b.containsKey(Contants.SINGLE_URL)) {
                this.mImageUrlList.add(this.b.getString(Contants.SINGLE_URL));
            }
            else if(this.b.containsKey(Contants.MULTI_URL)) {
                this.mImageUrlList.addAll((Collection) this.b.getSerializable(Contants.MULTI_URL));
            }

            this.isNeedSetScaleType = this.b.getBoolean("scaleType");
        }
    }

    private PopupWindow initPicPop() {
        View v3 = LayoutInflater.from(((Context)this)).inflate(R.layout.multi_image_layout, null);
        PopupWindow v1 = new PopupWindow(v3, -1, -2);
        v1.setContentView(v3);
        v1.setOutsideTouchable(true);
        v1.setAnimationStyle(R.style.PopwindowStyle);
        v1.setBackgroundDrawable(this.getResources().getDrawable(R.color.edittext_bg));
        v3.findViewById(R.id.saveimage).setOnClickListener(((View.OnClickListener)this));
        v3.findViewById(R.id.cancel).setOnClickListener(((View.OnClickListener)this));
        return v1;
    }

    private void initView() {
        this.mViewPager = (ViewPager)this.findViewById(R.id.view_pager);
        this.mViewPager.setAdapter(new ImageAdapter(this, ((Context)this)));
        this.mViewPager.setOnPageChangeListener(this.pageChangeListener);
        this.mViewPager.setOffscreenPageLimit(3);
    }

    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.saveimage) {
			if(this.mViewPager == null) {
			    return;
			}
			if(this.mImageUrlList == null) {
			    return;
			}
			if(this.mImageUrlList.size() <= 0) {
			    return;
			}
			View childview = this.mViewPager.getChildAt(this.pageIndex);
			Object url = this.mImageUrlList.get(this.pageIndex);
			Bitmap bitmap = null;//((MCPhotoView)v3).getDrawable().getBitmap();
			String suburl = ((String)url).substring(((String)url).lastIndexOf(47));
			if(!this.fileIsExists()) {
			    return;
			}
			if(UploadUtils.saveBitmapToFile(bitmap, String.valueOf(this.path) + suburl)) {
			    this.setResult(-1);
			    MCToast.show(((Context)this), this.getResources().getString(R.string.save_image_path_tip, new Object[]{String.valueOf(this.path) + suburl}));
			    return;
			}
			this.setResult(0);
			MCToast.show(((Context)this), this.getResources().getString(R.string.save_image_error));
		} else if (id == R.id.cancel) {
			this.finish();
		}
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.show_big_image);
        this.initData();
        this.initView();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mImageUrlList.clear();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean v1;
        if(keyCode == 4) {
            if(this.mViewPager != null) {
                this.finishAcitiviy((MCPhotoView) this.mViewPager.getChildAt(this.pageIndex));
            }

            v1 = true;
        }
        else {
            v1 = super.onKeyUp(keyCode, event);
        }

        return v1;
    }
}

