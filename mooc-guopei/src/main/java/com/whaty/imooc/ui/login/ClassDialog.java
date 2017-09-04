package com.whaty.imooc.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;

import cn.com.whatyguopei.mooc.R;


/**
 * 自定义班级选择dialog
 * Created by fhk on 2017/8/31.
 */

public class ClassDialog extends Dialog {
    public ClassDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title;
        private Adapter adapter;
        private AdapterView.OnItemClickListener listener;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAdapter(Adapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setListener(AdapterView.OnItemClickListener listener) {
            this.listener = listener;
            return this;
        }


        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public ClassDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ClassDialog dialog = new ClassDialog(context, R.style.dialog);
            View layout = inflater.inflate(R.layout.class_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.submit)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.submit).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.cancel)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.cancel).setVisibility(View.GONE);
            }
            if (adapter != null) {
                ((ListView) layout.findViewById(R.id.list_class)).setAdapter((ListAdapter) adapter);
                ((ListView) layout.findViewById(R.id.list_class)).setOnItemClickListener(listener);
            } else if (contentView != null) {
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

        private String getKeyValue(String key) {
            return SharedClassInfo.getKeyValue(key);
        }

        public void saveUserBanJi(int number) {
            SharedClassInfo.saveUserBanjiId(getKeyValue(GPContants.USER_BANJIID + number));
            SharedClassInfo.saveUserHeadTeacherName(getKeyValue(GPContants.USER_HEADTEACHERNAME + number));
            SharedClassInfo.saveUserOrganId(getKeyValue(GPContants.USER_ORGANID + number));
            SharedClassInfo.saveUserBanjiName(getKeyValue(GPContants.USER_BANJINAME + number));
            SharedClassInfo.saveUserProjectId(getKeyValue(GPContants.USER_PROJECTID + number));
            SharedClassInfo.saveUserHeadTeacherPhone(getKeyValue(GPContants.USER_HEADTEACHERPHONE + number));
            SharedClassInfo.saveUserHomeWordCourseId(getKeyValue(GPContants.USER_HOMECOURSEID + number));
            SharedClassInfo.saveUserStatusName(getKeyValue(GPContants.USER_STATUSNAME + number));
        }
    }


}
