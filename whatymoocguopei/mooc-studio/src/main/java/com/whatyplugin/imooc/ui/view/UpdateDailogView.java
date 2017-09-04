package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;

public class UpdateDailogView extends LinearLayout implements View.OnClickListener {
    public interface INetworkListener {
        void cancel();

        void update(MCUpgradeType arg1);
    }

    private Handler handler;
    public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	private INetworkListener listener;
    private Context mContext;
    private MCUpgradeType type;
    public UpdateDailogView(Context context, String content, MCUpgradeType type, Handler handler) {
        super(context);
        this.mContext = context;
        this.type = type;
        this.handler = handler;
        View v3 = LayoutInflater.from(this.mContext).inflate(R.layout.customdialog_layout, null);
        View v1 = v3.findViewById(R.id.content);
        if(v1 != null) {
            ((TextView)v1).setText(((CharSequence)content));
        }

        View cancelView = v3.findViewById(R.id.cancelButton);
        View updateView = v3.findViewById(R.id.updateButton);
        ((TextView)cancelView).setOnClickListener(((View.OnClickListener)this));
        ((TextView)updateView).setOnClickListener(((View.OnClickListener)this));
        this.addView(v3);
    }

    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.updateButton) {
			if(this.listener == null) {
			    return;
			}
			this.listener.update(this.type);
		} else if (id == R.id.cancelButton) {
			if(this.listener == null) {
			    return;
			}
			if(this.type == MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE) {
			    if(this.handler != null) {
			        this.handler.sendEmptyMessage(0);
			        return;
			    }
			    this.listener.cancel();
			    return;
			}
			this.listener.cancel();
		}
    }

    public void setNetworkListener(INetworkListener l) {
        this.listener = l;
    }
}

