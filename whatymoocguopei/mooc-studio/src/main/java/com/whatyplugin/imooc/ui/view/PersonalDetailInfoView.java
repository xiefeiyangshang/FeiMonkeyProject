package com.whatyplugin.imooc.ui.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageHandleInterface;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.compression.MCBitmapScaledAndHandled;
import com.whatyplugin.base.define.MCBaseDefine.MCGender;
import com.whatyplugin.base.define.MCBaseDefine.MCUserType;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCUserService;
import com.whatyplugin.imooc.logic.service_.MCUserServiceInterface;
import com.whatyplugin.imooc.ui.showimage.MCShowBigImageActivity;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

public class PersonalDetailInfoView extends LinearLayout implements View.OnClickListener, MCAnalyzeBackBlock {
    private ImageView edit_img;
    private TextView from_tv;
    private MCFullUserModel fulluser;
    private MCImageView head_img;
    private TextView job_tv;
    private long lastClickTime;
    private Context mContext;
    private MCUserServiceInterface mUserService;
    private TextView mark_tv;
    private TextView name_tv;
    private ImageView sex_img;
    private String uid;
    private MCUserModel user;
    private RelativeLayout view;

    public PersonalDetailInfoView(Context context, String uid, MCUserModel user) {
        super(context);
        this.view = null;
        this.uid = Contants.DEFAULT_UID;
        this.mContext = context;
        this.uid = uid;
        this.user = user;
        this.init();
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List temp) {
        int v9 = R.string.personal_come_frome_lable;  // R.string.personal_come_frome_lable
        int v7 = 2;
        int v3 = 4;
        List<MCFullUserModel> arg12 = temp;
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            this.fulluser = arg12.get(0);
            this.name_tv.setText(this.fulluser.getNickname());
            int type = this.fulluser.getType() == MCUserType.MC_USER_TYPE_TEACHER ? 1 : 0;
            this.showOrHideImage(type);
            if(this.fulluser.getJobs().get(0).getName().isEmpty()) {
                this.job_tv.setVisibility(v3);
            }
            else {
                this.job_tv.setVisibility(View.VISIBLE);
                this.job_tv.setText(this.fulluser.getJobs().get(0).getName());
            }

            if(this.fulluser.getCity().isEmpty()) {
                this.from_tv.setText(this.mContext.getString(v9, new Object[]{this.mContext.getString(R.string.personal_unkonw_city)}));
            }
            else {
                this.from_tv.setText(this.mContext.getString(v9, new Object[]{this.fulluser.getCity().split(" ")[0]}));
            }

            this.mark_tv.setText(this.fulluser.getMark());
            if(this.fulluser.getGender() == MCGender.MC_GENDER_MALE) {
                this.sex_img.getDrawable().setLevel(0);
            }
            else if(this.fulluser.getGender() == MCGender.MC_GENDER_FEMALE) {
                this.sex_img.getDrawable().setLevel(1);
            }
            else {
                this.sex_img.getDrawable().setLevel(v7);
            }

            if(!this.uid.equals(this.user.getId())) {
                if(this.fulluser.getIs_friend()) {
                    this.edit_img.getDrawable().setLevel(1);
                }
                else {
                    this.edit_img.getDrawable().setLevel(v7);
                }
            }

            ViewGroup.LayoutParams v8 = this.head_img.getLayoutParams();
            v8.width = MCResolution.getInstance(this.mContext).scaleWidth(Contants.PERSONAL_COURSE_IMAGE_WIDTH);
            v8.height = v8.width;
            this.head_img.setLayoutParams(v8);
            this.head_img.setImageUrl(this.fulluser.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), v8.width, v8.height, true, ImageType.CICLE_IMAGE, new MCImageHandleInterface() {
                public Bitmap handleBitmapShape(Bitmap bitmap, ImageType type) {
                    if(ImageType.CICLE_IMAGE == type) {
                        bitmap = MCBitmapScaledAndHandled.toRoundBitmap(bitmap);
                    }

                    return bitmap;
                }

                public Bitmap scaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
                    return null;
                }
            });
        }
        else {
            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
                return;
            }

            if(result.isExposedToUser()) {
                return;
            }

            if(result.getResultCode() != MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
                return;
            }

            this.edit_img.setVisibility(v3);
            this.mark_tv.setVisibility(v3);
        }
    }

    static Context access$0(PersonalDetailInfoView arg1) {
        return arg1.mContext;
    }

    static void access$1(PersonalDetailInfoView arg0, String arg1) {
        arg0.uid = arg1;
    }

    private void init() {
        this.view = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.personal_detail__layout, null);
        this.head_img = (MCImageView)this.view.findViewById(R.id.image);
        this.from_tv = (TextView)this.view.findViewById(R.id.from_tv);
        this.job_tv = (TextView)this.view.findViewById(R.id.job_tv);
        this.name_tv = (TextView)this.view.findViewById(R.id.name_tv);
        this.mark_tv = (TextView)this.view.findViewById(R.id.mark_tv);
        this.sex_img = (ImageView)this.view.findViewById(R.id.sex_img);
        this.edit_img = (ImageView)this.view.findViewById(R.id.edit_img);
        this.head_img.setOnClickListener(((View.OnClickListener)this));
        this.edit_img.setOnClickListener(((View.OnClickListener)this));
        this.addView(this.view);
        this.mUserService = new MCUserService();
        if(this.user != null) {
            if(this.uid.equals(this.user.getId())) {
                try {
                    this.mUserService.getFullUserInfoByUid(this.uid, ((MCAnalyzeBackBlock)this), this.mContext);
                    this.name_tv.setText(MCSaveData.getUserInfo(Contants.NICKNAME, this.mContext).toString());
                    this.showOrHideImage(Integer.parseInt(String.valueOf(MCSaveData.getUserInfo(Contants.TEACHER_FLAG, this.mContext))));
                }
                catch(Exception e) {
                }

                return;
            }

            this.name_tv.setText(this.user.getNickname());
            int user_type = this.user.getType() == MCUserType.MC_USER_TYPE_TEACHER ? 1 : 0;
            this.showOrHideImage(user_type);
        }
    }

    private boolean isFastClick() {
        boolean isClick;
        long times = System.currentTimeMillis();
        if(times - this.lastClickTime < 1200) {
            this.lastClickTime = times;
            isClick = true;
        }
        else {
            isClick = false;
        }

        return isClick;
    }

    public void onClick(View v) {
        Bundle v1;
        Intent v3;
        int id = v.getId();
		if (id == R.id.image) {
			if(this.head_img.getDrawable().getConstantState() == this.getResources().getDrawable(R.drawable.personal_default_user_icon).getConstantState()) {
			    return;
			}
			if(this.isFastClick()) {
			    return;
			}
			v3 = new Intent(this.mContext, MCShowBigImageActivity.class);
			v1 = new Bundle();
			if(this.fulluser != null && !TextUtils.isEmpty(this.fulluser.getImageUrl())) {
			    v1.putString(Contants.SINGLE_URL, this.fulluser.getImageUrl());
			    v3.putExtras(v1);
			}
			this.mContext.startActivity(v3);
			if(!(this.mContext instanceof Activity)) {
			    return;
			}
			((Activity) this.mContext).overridePendingTransition(R.anim.show_image_in, 0);
		} else if (id == R.id.edit_img) {
			if(this.uid == Contants.DEFAULT_UID) {
			    MCLoginProxy.loginInstance().login(this.mContext, new MCAnalyzeBackBlock() {
			        public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
			            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			                MCToast.show(PersonalDetailInfoView.this.mContext, result.getResultDesc());
			                try {
			                    PersonalDetailInfoView.this.uid = MCSaveData.getUserInfo(Contants.UID, PersonalDetailInfoView.this.mContext).toString();
			                }
			                catch(Exception e) {
			                }

			                return;
			            }

			            if(result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
			                result.isExposedToUser();
			            }
			        }
			    });
			    return;
			}
			if(this.edit_img.getDrawable().getLevel() == 0) {
			   // this.mContext.startActivity(new Intent(this.mContext, MCPersonInformationActivity.class));
			    return;
			}
			if(this.edit_img.getDrawable().getLevel() != 2) {
			    return;
			}
		}
    }

    public void refreshUserInfo() {
        if(this.user != null) {
            if(this.uid == this.user.getId()) {
                this.mUserService.getFullUserInfoByUid(this.uid, ((MCAnalyzeBackBlock)this), this.mContext);
                this.name_tv.setText(MCSaveData.getUserInfo(Contants.NICKNAME, this.mContext).toString());
            }
            else {
                this.name_tv.setText(this.user.getNickname());
            }
        }
    }

    private void showOrHideImage(int userFlag) {
        Drawable v3 = null;
        if(userFlag == 1) {
            //this.name_tv.setCompoundDrawablesWithIntrinsicBounds(v3, v3, this.getResources().getDrawable(R.drawable.teacher_flag_big), v3);
        }
        else {
            this.name_tv.setCompoundDrawablesWithIntrinsicBounds(v3, v3, v3, v3);
        }
    }
}

