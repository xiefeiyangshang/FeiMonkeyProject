package com.whatyplugin.uikit.popupwindow;



import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.uikit.toast.MCToast;

public class MCPopupwindiw {
    public MCPopupwindiw() {
        super();
    }

    public static PopupWindow show(Context context, View contentView, View dropView) {
        PopupWindow popWindow = new PopupWindow(contentView, -1, -2);
        popWindow.setContentView(contentView);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.allcourse_type_bg));
        popWindow.showAsDropDown(dropView, 0, 0);
        return popWindow;
    }

    public static PopupWindow show(Context context, View contentView, View locationView, int gravity, int x, int y) {
        PopupWindow popWindow = new PopupWindow(contentView, -2, 200);
        MCToast.show(context, "height:" + contentView.getHeight());
        popWindow.setContentView(contentView);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.allcourse_type_bg));
        popWindow.showAtLocation(locationView, gravity, x, y);
        return popWindow;
    }
}

