package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class PersonDailogView extends LinearLayout implements View.OnClickListener {
    public interface IPersonListener {
        void onetext();

        void threetext();

        void twotext();
    }

    private IPersonListener listener;
    private Context mContext;

    public PersonDailogView(Context context, String first, String second) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.person_sex_dialog_layout, null);
        TextView text_first = (TextView) view.findViewById(R.id.text1);
        if(first != null) {
            text_first.setText(first);
        }

        TextView text_second = (TextView) view.findViewById(R.id.text2);
        if(second != null) {
            text_second.setText(second);
        }

        text_first.setOnClickListener(this);
        text_second.setOnClickListener(this);
        this.addView(view);
    }

    public PersonDailogView(Context context, String first, String second, String third) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.person_dialog_layout, null);
        TextView text_first = (TextView) view.findViewById(R.id.text1);
        if(first != null) {
            text_first.setText(first);
        }

        TextView text_second = (TextView) view.findViewById(R.id.text2);
        if(second != null) {
            text_second.setText(second);
        }
        
        TextView text_third = (TextView) view.findViewById(R.id.text3);
        if(third != null) {
        	text_third.setText(third);
        }

        text_first.setOnClickListener(this);
        text_second.setOnClickListener(this);
        text_third.setOnClickListener(this);
        this.addView(view);
    }

    public PersonDailogView(Context context) {
        this(context, null, null, null);
    }

    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.text1) {
			this.listener.onetext();
		} else if (id == R.id.text2) {
			this.listener.twotext();
		} else if (id == R.id.text3) {
			this.listener.threetext();
		}
    }

    public void setPersonListener(IPersonListener l) {
        this.listener = l;
    }
}

