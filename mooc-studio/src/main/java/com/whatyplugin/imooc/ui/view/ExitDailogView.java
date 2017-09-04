package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class ExitDailogView extends LinearLayout implements View.OnClickListener {
    public interface IExitListener {
        void cancel();

        void exit();

        void runback();
    }

    private TextView cancel_tv;
    private TextView content_tv;
    private TextView continue_tv;
    private View divide_line;
    private IExitListener listener;
    private Context mContext;
    private TextView runback_tv;
    private TextView title_tv;

    public ExitDailogView(Context context, int layoutId) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(layoutId, null);
        if(layoutId == R.layout.exit_dialog_layout) {
            this.cancel_tv = (TextView)view.findViewById(R.id.cancle_tv);
            this.continue_tv = (TextView)view.findViewById(R.id.continue_tv);
            this.runback_tv = (TextView)view.findViewById(R.id.runback_tv);
            this.cancel_tv.setOnClickListener(((View.OnClickListener)this));
            this.continue_tv.setOnClickListener(((View.OnClickListener)this));
            this.runback_tv.setOnClickListener(((View.OnClickListener)this));
        }
        else if(layoutId == R.layout.normal_exit_dialog) {
            this.cancel_tv = (TextView)view.findViewById(R.id.commit);
            this.continue_tv = (TextView)view.findViewById(R.id.cancel);
            this.title_tv = (TextView)view.findViewById(R.id.dialog_title);
            this.content_tv = (TextView)view.findViewById(R.id.dialog_content);
            this.divide_line = (View)view.findViewById(R.id.divide_line);
            this.cancel_tv.setOnClickListener(((View.OnClickListener)this));
            this.continue_tv.setOnClickListener(((View.OnClickListener)this));
        }

        this.addView(view);
    }

    public void onClick(View v) {
        if(this.listener != null) {
            int id = v.getId();
			if (id == R.id.runback_tv) {
				this.listener.runback();
				return;
			} else if (id == R.id.cancle_tv || id == R.id.cancel) {
				this.listener.cancel();
				return;
			} else if (id == R.id.continue_tv || id == R.id.commit) {
				this.listener.exit();
				return;
			}
        }
    }

    public void setCancelButtonName(String buttonName) {
        if(this.continue_tv != null) {
            this.continue_tv.setText(((CharSequence)buttonName));
        }
    }

    public void setContent(String contentMsg) {
        if(this.content_tv != null) {
            this.content_tv.setText(((CharSequence)contentMsg));
        }
    }

    public void setIExitListener(IExitListener l) {
        this.listener = l;
    }

    public void setSubmmitButtonName(String buttonName) {
        if(this.cancel_tv != null) {
            this.cancel_tv.setText(((CharSequence)buttonName));
        }
    }

    public void setTitle(String titleName) {
        if(this.title_tv != null) {
            this.title_tv.setText(((CharSequence)titleName));
        }
    }

    public void showCancelButton(boolean isShow) {
        if(this.continue_tv != null) {
            TextView textView = this.continue_tv;
            int value = isShow ? 0 : 8;
            textView.setVisibility(value);
        }
    }

    public void showDivieLine(boolean isShow) {
        if(this.divide_line != null) {
            View lineView = this.divide_line;
            int value = isShow ? 0 : 8;
            lineView.setVisibility(value);
        }
    }
}

