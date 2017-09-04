package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class UpgradeDailogView extends LinearLayout implements View.OnClickListener {
    public interface INetworkListener {
        void backgrounder();

        void cancel();
    }

    private INetworkListener listener;
    private Context mContext;
	private TextView tvTitle;
	private TextView tvContent;

    public UpgradeDailogView(Context context, String cancel, String backgrounder) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.upgrade_dialog_layout, null);
        TextView v2 = (TextView)view.findViewById(R.id.update_cancel);
        if(cancel != null) {
            v2.setText(((CharSequence)cancel));
        }

        TextView v1 = (TextView)view.findViewById(R.id.update_backgrounder);
        if(backgrounder != null) {
            v1.setText(((CharSequence)backgrounder));
        }

        ((TextView)v2).setOnClickListener(((View.OnClickListener)this));
        ((TextView)v1).setOnClickListener(((View.OnClickListener)this));
        tvTitle = (TextView) view.findViewById(R.id.title);
        tvContent = (TextView) view.findViewById(R.id.content);
        this.addView(view);
    }
    
    public void setTitle(CharSequence title){
    	if(tvTitle != null){
    		tvTitle.setText(title);
    	}
    }
    public void setContent(CharSequence content){
    	if(tvContent != null){
    		tvContent.setText(content);
    	}
    }

    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.update_cancel) {
			if(this.listener == null) {
			    return;
			}
			this.listener.cancel();
		} else if (id == R.id.update_backgrounder) {
			if(this.listener == null) {
			    return;
			}
			this.listener.backgrounder();
		}
    }

    public void setNetworkListener(INetworkListener l) {
        this.listener = l;
    }
}

