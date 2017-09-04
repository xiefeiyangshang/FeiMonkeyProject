package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class SlidingTitileView extends LinearLayout implements View.OnClickListener {
    public interface ILeftListener {
        void onLeftListener();
    }

    public interface IMidListener {
        void onMidListener();
    }

    public interface IRightListener {
        void onRightListener();
    }
    
    public interface IRightTextListener {
        void onRightTextListener();
    }

    private ImageView arrow_img;
    private ImageView left_img;
    private Context mContext;
    private ILeftListener mLeftListener;
    private IMidListener mMidListener;
    private IRightListener mRightListener;
    private LinearLayout mid_layout;
    private TextView mid_tv;
    private ImageView right_tv;
    private TextView right_title_tv;
    
	private IRightTextListener mRightTextListener;

    public SlidingTitileView(Context context) {
        super(context, null);
    }

    public SlidingTitileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.init();
    }

    public ImageView getArrowImageView() {
        return this.arrow_img;
    }

    public String getMidViewText() {
        return this.mid_tv.getText().toString();
    }

    private void init() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.sliding_title_layout, null);
        
        //中间文本
        this.mid_tv = (TextView)view.findViewById(R.id.mid_tv);
        this.mid_layout = (LinearLayout)view.findViewById(R.id.mid_layout);
        this.mid_layout.setOnClickListener(((View.OnClickListener)this));
        
        //右侧搜索
        this.right_tv = (ImageView)view.findViewById(R.id.right_tv);
        this.right_tv.setOnClickListener(((View.OnClickListener)this));
        
        this.right_title_tv = (TextView)view.findViewById(R.id.right_title_tv);
        this.right_title_tv.setOnClickListener(this);
        
        //左侧菜单
        this.left_img = (ImageView)view.findViewById(R.id.left_img);
        this.left_img.setOnClickListener(((View.OnClickListener)this));
        this.arrow_img = (ImageView)view.findViewById(R.id.arrow_image);
        this.addView(view);
    }

    
    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.left_img) {
			if(this.mLeftListener == null) {
			    return;
			}
			this.mLeftListener.onLeftListener();
		} else if (id == R.id.right_tv) {
			if(this.mRightListener == null) {
			    return;
			}
			this.mRightListener.onRightListener();
		} else if (id == R.id.mid_layout) {
			if(this.mMidListener == null) {
			    return;
			}
			this.mMidListener.onMidListener();
		} else if (id == R.id.right_title_tv) {
			if(this.mRightTextListener == null) {
				return;
			}
			this.mRightTextListener.onRightTextListener();
		}
    }

    //上下小三角是否显示
    public void setArrowImageVisibility(boolean visibility) {
        ImageView imageView = this.arrow_img;
        int value = visibility ? View.VISIBLE : View.GONE;
        imageView.setVisibility(value);
    }

    //左侧菜单图标是否显示
    public void setLeftViewVisibility(boolean visibility) {
        ImageView imageView = this.left_img;
        int value = visibility ? View.VISIBLE : View.GONE;
        imageView.setVisibility(value);
    }

    public void setMidViewText(String text) {
        this.mid_tv.setText(((CharSequence)text));
    }

    /**
     * 中间的下拉是否显示
     * @param visibility
     */
    public void setMidViewVisibility(boolean visibility) {
        LinearLayout layout = this.mid_layout;
        int value = visibility ? View.VISIBLE : View.GONE;
        layout.setVisibility(value);
    }

    public void setOnLeftListener(ILeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setOnMidListener(IMidListener listener) {
        this.mMidListener = listener;
    }

    public void setOnRightListener(IRightListener listener) {
        this.mRightListener = listener;
    }
    
    public void setOnRightTextListener(IRightTextListener listener) {
    	this.mRightTextListener = listener;
    }

    public void setRightViewImage(int image) {
        this.right_tv.setImageResource(image);
    }

    public void setRightViewVisibility(boolean visibility) {
        ImageView imageView = this.right_tv;
        int value = visibility ? 0 : 4;
        imageView.setVisibility(value);
        this.right_title_tv.setVisibility(View.GONE);
    }
    
    public void setRightText(String info){
    	this.right_tv.setVisibility(View.GONE);
    	this.right_title_tv.setVisibility(View.VISIBLE);
    	this.right_title_tv.setText(info);
    }
}

