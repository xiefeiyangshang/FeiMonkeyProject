package com.whatyplugin.imooc.ui.showmooc;


import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseDetailModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseFragment;
import com.whatyplugin.uikit.resolution.MCResolution;


public class CourseDetailFragment extends MCBaseFragment implements MCAnalyzeBackBlock {
    private TextView course_info;
    private TextView course_name;
    private boolean isVisible;
    private MCCourseModel mCourse;
    private MCCourseServiceInterface mCourseService;
    private TextView no_info;
    private LinearLayout teacher_layout;
	private TextView choiceTarget;

    public CourseDetailFragment() {
        super();
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List arg21) {
        int v11 = 0;
        int v5 = 0;
        int v4 = 0;
        MCCourseDetailModel courseDetailModel = null;
      
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            courseDetailModel = (MCCourseDetailModel) arg21.get(0);
        	if(courseDetailModel==null){
        		return;
        	}
        	//添加模拟数据
//        	v14 = new MCCourseDetailModel();
//        	result = new MCServiceResult();
//            Const.addFalkDateCourseDetail(result, v14);
            if(courseDetailModel != null){
	            if(!TextUtils.isEmpty(courseDetailModel.getDescription())&&!"null".equals(courseDetailModel.getDescription()))
	            	this.course_info.setText(Html.fromHtml(courseDetailModel.getDescription()));
	            else{
	            	this.course_info.setText("暂无");
	            }
	            if(!TextUtils.isEmpty(courseDetailModel.getTarget())&&!"null".equals(courseDetailModel.getTarget())){
	            	 this.choiceTarget.setText(Html.fromHtml(courseDetailModel.getTarget()));
	            }else{
	            	this.choiceTarget.setText("暂无");
	            }
            }
            ShowMoocActivity thisActivity = (ShowMoocActivity) this.getActivity();
            try {
            	this.course_name.setText(thisActivity.getmCourse().getName());
                v4 = MCResolution.getInstance(this.getActivity()).scaleWidth(Contants.USER_PIC_WIDTH_COURSEINFO);
                v5 = v4;
                ViewGroup.LayoutParams v13 = this.no_info.getLayoutParams();
                v13.height = v5;
                this.no_info.setLayoutParams(v13);
                if(this.teacher_layout.getChildCount() > 0) {
                    this.teacher_layout.removeAllViews();
                }
                
                if(((MCCourseDetailModel)courseDetailModel).getTeacher() != null && ((MCCourseDetailModel)courseDetailModel).getTeacher().size() == 0) {
                    this.no_info.setVisibility(0);
                    return;
                }

                v11 = 0;
            } catch(Exception v10) {
            	v10.printStackTrace();
            	  this.teacher_layout.setVisibility(8);
                  this.no_info.setVisibility(0);
                  return;
            }
        }

        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
            return;
        }
        try {
        	if(courseDetailModel == null || ((MCCourseDetailModel)courseDetailModel).getTeacher()==null){
        		return;
        	}
            while(v11 < ((MCCourseDetailModel)courseDetailModel).getTeacher().size()) {
            	MCUserModel userModel = (MCUserModel) ((MCCourseDetailModel)courseDetailModel).getTeacher().get(v11);
                TeacherLayout teacherLayout = new TeacherLayout(this.getActivity());
//                MCHeadImageView v1 = (MCHeadImageView) teacherLayout.findViewById(R.id.teacher_pic);
//                ViewGroup.LayoutParams v15 = ((MCHeadImageView)v1).getLayoutParams();
//                v15.width = MCResolution.getInstance(this.getActivity()).scaleWidth(Contants.USER_PIC_WIDTH_COURSEINFO);
//                v15.height = v15.width;
//                v1.setLayoutParams(v15);
                WebView tvTeacerInfo = (WebView) teacherLayout.findViewById(R.id.teacher_info);
                TextView tvTeacherName = (TextView) teacherLayout.findViewById(R.id.teacher_name);
                View v9 = teacherLayout.findViewById(R.id.bottom_line);
//                v1.setMCUserModel( userModel);
                tvTeacherName.setText(userModel.getNickname());
               
               
                 WebViewUtil.loadContentWithPic(userModel.getDesc(), tvTeacerInfo, getActivity());
//                v1.setImageUrl(userModel.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), v4, v5, true, ImageType.CICLE_IMAGE, new MCImageHandleInterface() {
//                    public Bitmap handleBitmapShape(Bitmap bitmap, ImageType type) {
//                        if(ImageType.CICLE_IMAGE == type) {
//                            bitmap = MCBitmapScaledAndHandled.toRoundBitmap(bitmap);
//                        }
//
//                        return bitmap;
//                    }
//
//                    public Bitmap scaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
//                        return null;
//                    }
//                });
                if(v11 == ((MCCourseDetailModel)courseDetailModel).getTeacher().size() - 1) {
                    v9.setVisibility(8);
                }

                this.teacher_layout.addView(((View)teacherLayout));
                this.no_info.setVisibility(8);
                ++v11;
            }

            return;
        }
        catch(Exception v10) {
        	v10.printStackTrace();
            this.teacher_layout.setVisibility(8);
            this.no_info.setVisibility(0);
            return;
        }
       // result.isExposedToUser();
    }

    public boolean getVisible() {
        return this.isVisible;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        this.mCourseService = new MCCourseService();
        ShowMoocActivity thisActivity = (ShowMoocActivity) this.getActivity();
        this.mCourse = thisActivity.getmCourse();
        this.teacher_layout = (LinearLayout) this.getActivity().findViewById(R.id.teacher_layout);
        this.course_info = (TextView) this.getActivity().findViewById(R.id.course_info);
        this.no_info = (TextView) this.getActivity().findViewById(R.id.no_info);
        this.course_name = (TextView) this.getActivity().findViewById(R.id.course_name);
        this.mCourseService.getCourseDetailByCourseId(null, this.mCourse.getId(), ((MCAnalyzeBackBlock)this), this.getActivity());

        this.choiceTarget = (TextView) this.getActivity().findViewById(R.id.target_info);

        super.onActivityCreated(savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_detail_layout, null);
    }

    public void refreshByCourse(MCCourseModel course) {
        if(this.mCourse != null && this.mCourse.getId() != course.getId()) {
            this.mCourseService.getCourseDetailByCourseId(null, course.getId(), ((MCAnalyzeBackBlock)this), this.getActivity());
        }
    }

    public void setVisible(boolean isVisible) {
        int v2 = R.string.course_details_page_name;  // R.string.course_details_page_name
        this.isVisible = isVisible;
        if(this.getActivity() != null && (this.isAdded())) {
            if(isVisible) {
             //   MCBaiduStatService.onPageStart(this.getActivity(), this.getResources().getString(v2));
            }
            else {
              //  MCBaiduStatService.onPageEnd(this.getActivity(), this.getResources().getString(v2));
            }
        }
    }
}

