package com.whatyplugin.imooc.ui.view;


import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.dialog.MCLoadDialog.TimeCount;
import com.whatyplugin.uikit.toast.MCToast;

public class MCSendTalkView extends LinearLayout implements View.OnClickListener, MCAnalyzeBackBlock {
	private static final String TAG = MCSendTalkView.class.getSimpleName();
	
	public class MaxLengthWatcher implements TextWatcher {
        private EditText editText;
        private int maxLen;

        public MaxLengthWatcher(MCSendTalkView arg2, int maxLen, EditText editText) {
            super();
            this.maxLen = 0;
            this.editText = null;
            this.maxLen = maxLen;
            this.editText = editText;
        }

        public void afterTextChanged(Editable arg0) {
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            Editable editable = this.editText.getText();
            int currentLen = editable.length();
            MCSendTalkView.this.tv_write_num.setText((this.maxLen - editable.toString().length()+"/"+this.maxLen));
            if(currentLen > this.maxLen) {
            	MCSendTalkView.this.tv_write_num.setText("0");
            	MCToast.show(mContext, "超过最大长度啦！");
            }
        }
    }

    private MCLoadDialog dialog;
    private Dialog dialog_load_error;
    private static  Dialog dialog_load_success;
    private Dialog dialog_loading;
    private EditText etInput;
    private Handler handler;
    private Animation hyperspaceJumpAnimation;
    private InputMethodManager imm;
    private ImageView iv_cancel;
    private ImageView iv_send;
    private Context mContext;
    private MCCourseDetailService mCourseDetailService;
    private MCStudyServiceInterface mCStudyService;
    private ViewPager mViewPager;
    private PopupWindow pop;
    private String courseId;
    private String share_content;
    private String title;
    private TextView tv_title_lable;
    private TextView tv_write_num;
    private View view;
  


    public MCSendTalkView(Context context, String courseId, String title, ViewPager mViewPager) {
    	super(context);
        Animation v5 = null;
        this.handler = new Handler();
        this.mContext = context;
        this.courseId = courseId;
        this.title = title;
        this.mViewPager = mViewPager;
        this.mCourseDetailService = new MCCourseDetailService();
        this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.dialog_loading_anim);
        this.dialog = new MCLoadDialog();
        MCSendTalkView.dialog_load_success = MCLoadDialog.createLoadingDialog(this.mContext, this.mContext.getString(R.string.dialog_load_success), R.drawable.dialog_load_success, v5);
        this.dialog_load_error = MCLoadDialog.createLoadingDialog(this.mContext, this.mContext.getString(R.string.dialog_load_error), R.drawable.dialog_load_error, v5);
        
       	   this.mCStudyService = new MCStudyService();
       

        this.view = LayoutInflater.from(this.mContext).inflate(R.layout.share_layout, null);
        this.iv_cancel = (ImageView) this.view.findViewById(R.id.back);
        this.iv_send = (ImageView) this.view.findViewById(R.id.send);
        this.tv_title_lable = (TextView) this.view.findViewById(R.id.title_label);
        this.tv_title_lable.setText(((CharSequence)title));

        //绑定关闭、发送的事件
        this.iv_cancel.setOnClickListener(this);
        this.iv_send.setOnClickListener(this);
      
        this.tv_write_num = (TextView) this.view.findViewById(R.id.write_prompt);
        this.etInput = (EditText) this.view.findViewById(R.id.content);
        this.etInput.setText(this.share_content);
        this.tv_write_num.setText(this.mContext.getString(R.string.write_prompt, new Object[]{Integer.valueOf(400 - this.etInput.getText().toString().length())}));
        this.etInput.addTextChangedListener(new MaxLengthWatcher(this, 400, this.etInput));
        this.popupInputMethodWindow();
        this.addView(this.view);
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
        long v1 = 2000;
        long v3 = 1000;
        MCLog.i(TAG, "sendtalk_time:" + objs);
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            if(this.pop != null && (this.pop.isShowing())) {
                this.pop.setFocusable(false);
                this.pop.dismiss();
            }

            if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
                this.dialog_loading.dismiss();
            }

            MCSendTalkView.dialog_load_success.show();
            new TimeCount(v1, v3, MCSendTalkView.dialog_load_success).start();
            if(!this.title.equals(this.getResources().getString(R.string.course_note_label))) {
                return;
            }

            if(this.mViewPager != null) {
                this.mViewPager.setCurrentItem(2);
            }

        }
        else {
            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
                if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
                    this.dialog_loading.dismiss();
                }

                this.dialog_load_error.show();
                new TimeCount(v1, v3, this.dialog_load_error).start();
                return;
            }

            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
                if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
                    this.dialog_loading.dismiss();
                }

                this.dialog_load_error.show();
                new TimeCount(v1, v3, this.dialog_load_error).start();
                return;
            }

            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
                if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
                    this.dialog_loading.dismiss();
                }

                MCToast.show(this.mContext, this.mContext.getString(R.string.download_nonetwork_label));
                return;
            }

            if(result.isExposedToUser()) {
                return;
            }

            if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
                this.dialog_loading.dismiss();
            }

            this.dialog_load_error.show();
            new TimeCount(v1, v3, this.dialog_load_error).start();
        }
    }
    
    

    public void onClick(View v) {
        int id = v.getId();
		if (id == R.id.back) {
			this.pop.setFocusable(false);
			this.pop.dismiss();
			this.imm.hideSoftInputFromWindow(this.view.getWindowToken(), 0);
		} else if (id == R.id.send) {
			String text = this.etInput.getText().toString();
			this.dialog_loading = MCLoadDialog.createLoadingDialog(this.mContext, this.mContext.getString(R.string.dialog_loading), R.drawable.dialog_loading, this.hyperspaceJumpAnimation);
			if(TextUtils.isEmpty(text)) {//为空
			    MCToast.show(this.getContext(), this.getResources().getString(R.string.send_null_tip), 0);
			    return;
			}
			if(StringUtils.isWhiteSpace(text)) {//只是符号
			    MCToast.show(this.getContext(), this.getResources().getString(R.string.send_nullstring_tip), 0);
			    return;
			}
			if(text.length() > 400) {//验证长度
			    MCToast.show(this.getContext(), this.getResources().getString(R.string.send_longstring_tip), 0);
			    return;
			}
			if(text.contains("😏")){
				 MCToast.show(this.getContext(), "该功能不支持输入表情！", 0);
			     return;
			}
			//发送笔记的核心service
			if (this.title.toString().equals("笔记")) {
				this.mCourseDetailService.getSendNote(this.courseId, this.etInput.getText().toString(), null, 0, this, this.mContext);
			}
			else {
				if (this.etInput.getText().toString().length()<10) {
					Toast.makeText( this.mContext, "至少十个字，不解释...", Toast.LENGTH_SHORT).show();
					  return;
				}else {
					this.mCStudyService.sendQuestion( this.courseId, this.etInput.getText().toString(), null, this, this.mContext);
					
				}
			}
			
			//进度提示效果
			this.dialog_loading.show();
			return;
		} 
    }

    /**
     * 打开输入法
     */
    private void popupInputMethodWindow() {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                MCSendTalkView.this.imm = (InputMethodManager) MCSendTalkView.this.etInput.getContext().getSystemService("input_method");
                MCSendTalkView.this.imm.toggleSoftInput(0, 2);
            }
        }, 100);
    }

    public void setPop(PopupWindow pop) {
        this.pop = pop;
    }
}

