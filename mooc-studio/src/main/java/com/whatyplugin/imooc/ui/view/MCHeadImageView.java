package com.whatyplugin.imooc.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;

public class MCHeadImageView extends MCImageView {
    private Context mContext;
    private MCUserModel mUserModel;
    private String uid;

    public MCHeadImageView(Context context) {
        this(context, null);
    }

    public MCHeadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MCHeadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.uid = Contants.DEFAULT_UID;
        this.mContext = context;
        this.mUserModel = new MCUserModel();
        this.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    MCHeadImageView.this.uid = MCSaveData.getUserInfo(Contants.UID, MCHeadImageView.this.mContext).toString();
                }
                catch(Exception v2) {
                	v2.printStackTrace();
                }
            }
        });
    }

    public void setMCUserModel(MCUserModel user) {
        this.mUserModel = user;
    }
}

