package com.whatyplugin.imooc.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.uikit.resolution.MCResolution;

public class MCGuidanceView extends LinearLayout {
    private TextView download_tv;
    private ImageView guidance_img;
    private LinearLayout guidance_layout;
    private TextView guidance_tv;
    private LayoutInflater inflater;
    private Context mContext;
    private TextView reload_tv;
    private View view;

    public MCGuidanceView(Context context) {
        this(context, null);
    }

    public MCGuidanceView(Context context, AttributeSet attr) {
        super(context, attr);
        this.view = null;
        this.mContext = context;
        this.inflater = LayoutInflater.from(this.mContext);
        this.init();
    }

    private void init() {
        this.view = this.inflater.inflate(R.layout.guidance_layout, null);
        this.guidance_layout = (LinearLayout) this.view.findViewById(R.id.guidance_layout);
        this.guidance_img = (ImageView) this.view.findViewById(R.id.guidance_img);
        this.guidance_tv = (TextView) this.view.findViewById(R.id.guidance_label);
        this.download_tv = (TextView) this.view.findViewById(R.id.download_tv);
        this.reload_tv = (TextView) this.view.findViewById(R.id.reload_tv);
        int devHeight = MCResolution.getInstance(this.mContext).getDevHeight(this.mContext);
        if(devHeight != 0){
        	this.view.setLayoutParams(new LinearLayout.LayoutParams(-1, devHeight));
        }else{
        	this.view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        this.addView(this.view);
    }

    public void setGuidanceBackgroundColor(int colorsId) {
        this.view.setBackgroundColor(colorsId);
    }

    public void setGuidanceBitmap(int resId) {
        this.guidance_img.setImageResource(resId);
    }

    public void setGuidanceText(int resId) {
        this.setGuidanceText(resId, 0);
    }

    public void setGuidanceText(int resId, int style) {
        this.guidance_tv.setText(this.mContext.getResources().getString(resId));
        this.guidance_tv.setTextAppearance(this.mContext, style);
    }
    
    public void setGuidText(String text){
    	 this.guidance_tv.setText(text);
         this.guidance_tv.setTextAppearance(this.mContext, 0);
    }

    public void setLayoutMarginTop(int top) {
    	RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)this.guidance_layout.getLayoutParams();
        lp.topMargin = top;
        this.guidance_layout.setLayoutParams(lp);
    }
}

