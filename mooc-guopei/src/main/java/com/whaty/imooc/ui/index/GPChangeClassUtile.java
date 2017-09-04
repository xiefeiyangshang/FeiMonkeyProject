package com.whaty.imooc.ui.index;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.whaty.imooc.ui.login.ChangClassDiag;
import com.whaty.imooc.ui.login.ClassDialog;
import com.whaty.imooc.ui.login.NewChangeClassDialog;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.imooc.logic.contants.Contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

public class GPChangeClassUtile {

    private static ClassDialog dialog;
    private static ArrayList<Map<String, Object>> mData;

    public static boolean isChandClass() {
        return SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID).equals("");
    }

    public static boolean noClass() {
        return SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID_1).equals("") && SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID_2).equals("");
    }

    public static void createDialog(Activity ac) {
//		ChangClassDiag changClassDiag = new ChangClassDiag();
//		changClassDiag.show(MCCreateDialog.getFragmentTransaction(ac), "changclass");
        NewChangeClassDialog newChangeClassDialog = new NewChangeClassDialog();
        newChangeClassDialog.show(MCCreateDialog.getFragmentTransaction(ac), "newchangclass");
    }

    public static String getClassName() {
        return SharedClassInfo.getKeyValue(GPContants.USER_BANJINAME);
    }

    public static boolean StuHaveOneClass() {
        return SharedClassInfo.getKeyValue(GPContants.USER_ONLYHAVEONECLASS).equals("true");
    }

    // 1是有效学习
    public static Boolean isStatuName() {
        return SharedClassInfo.getKeyValue(GPContants.USER_STATUSNAME).equals("1");
    }


    /**
     * 弹出班级列表的Dialog
     *
     * @param ac
     */

    public static void check(final Activity ac) {

        mData = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "班级" + i);
            mData.add(map);
        }

        final ClassDialog.Builder builder = new ClassDialog.Builder(ac);
        builder.setAdapter(new MyAdapter(ac, mData));
        builder.setListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.saveUserBanJi(position);
                Toast.makeText(ac, "当前条目"+position, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setTitle("班级列表");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 在这里发广播通知吧
//                ac.sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private static class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context context;
        private ArrayList<Map<String, Object>> mData;

        public MyAdapter(Context context, ArrayList<Map<String, Object>> mData) {
            this.context = context;
            this.mData = mData;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.class_item, null);
                holder.class_name = (TextView) convertView.findViewById(R.id.class_name);
                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            String title = (String) mData.get(position).get("title");
            holder.class_name.setText(title);

            return convertView;
        }
    }

    private static class ViewHolder {

        TextView class_name;

    }

}
