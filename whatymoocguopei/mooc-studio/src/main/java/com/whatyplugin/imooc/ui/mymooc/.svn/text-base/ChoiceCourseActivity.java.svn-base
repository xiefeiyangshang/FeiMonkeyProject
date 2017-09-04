package com.whatyplugin.imooc.ui.mymooc;

import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseDetailModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;
import com.whatyplugin.imooc.ui.showmooc.TeacherLayout;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

public class ChoiceCourseActivity extends MCBaseActivity implements
		OnClickListener, MCAnalyzeBackBlock {
	private TextView course_info;
	private MCCourseModel mCourse;
	private MCCourseServiceInterface mCourseService;
	private TextView no_info;
	private LinearLayout teacher_layout;
	private BaseTitleView titleView;
	private RelativeLayout choice_course;
	private Dialog dialog_loading;
    private Animation hyperspaceJumpAnimation;
    
    private TextView courseName;
    private TextView mainTeacher;
    private TextView timeCredit;
    private RelativeLayout contentLayout;
    private MCImageView coursePic;
    private TextView startTime;
    private TextView choiceCount;
    private TextView choiceTarget;
    private TextView choice_course_text;
    private BroadcastReceiver receiver;
    
	public ChoiceCourseActivity() {
		super();
	}

	public void onClick(View v) {
		int id = v.getId();
	if (id == R.id.choice_course) {
			String uid = MCSaveData.getUserInfo(Contants.UID, this).toString();
			//如果登录了就选课操作，没登录进跳到登录
			if(!TextUtils.isEmpty(uid)){
				//进入课程
				if("IN".equals(choice_course_text.getTag())){
					
					//开启进入课程的
			        Intent intent = new Intent(this, ShowMoocActivity.class);
			        Bundle bundle = new Bundle();
			        bundle.putSerializable("course", this.mCourse);
			        intent.putExtras(bundle);
			        this.startActivityForResult(intent, 10);
			        
			        //结束当前activity
			        Intent retIntent = new Intent();
			        Bundle retBundle = new Bundle();
			        retBundle.putString("courseId", this.mCourse.getId());
			        retIntent.putExtras(retBundle);
			        this.setResult(-1, retIntent);
			        this.finish();
				}else{//选课
					this.dialog_loading = MCLoadDialog.createLoadingDialog(this, this.getString(R.string.choice_dialog_loading), R.drawable.dialog_loading, this.hyperspaceJumpAnimation);
					
				    this.mCourseService.choiceCourse(this.mCourse.getId(), new MCAnalyzeBackBlock() {
				      public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
				          if(MCResultCode.MC_RESULT_CODE_SUCCESS == result.getResultCode()) {
				             //加入课程改成进入课程
				        	 choice_course_text.setText("进入课程");
				        	 choice_course_text.setTag("IN");//选课成功的标记
				        	 MCToast.show(ChoiceCourseActivity.this, ChoiceCourseActivity.this.getString(R.string.choice_course_success));
				             //原来的提示框该去掉的都去掉
				          } else if(MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE == result.getResultCode()) {
				              MCToast.show(ChoiceCourseActivity.this, ChoiceCourseActivity.this.getResources().getString(R.string.download_nonetwork_label));
				          } else if(MCResultCode.MC_RESULT_CODE_FAILURE == result.getResultCode()) {
				              MCToast.show(ChoiceCourseActivity.this, ChoiceCourseActivity.this.getResources().getString(R.string.choice_course_error));
				          }
				          
				          //去掉显示的提示框
						  if(ChoiceCourseActivity.this.dialog_loading != null && (ChoiceCourseActivity.this.dialog_loading.isShowing())) {
							 ChoiceCourseActivity.this.dialog_loading.dismiss();
						  }
				      }
				    }, this);
					this.dialog_loading.show();
				}
			}else{
				
				MCLoginProxy.loginInstance().login(this, ((MCAnalyzeBackBlock)this));
				this.finish();
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		
		//调用super
		super.onCreate(savedInstanceState);
		
		//设置布局
		this.setContentView(R.layout.course_choice_layout);
		
		initView();
        initData();
		initEvent();
		
       
	}

	private void initEvent() {
		this.choice_course.setOnClickListener(this);
		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("IN".equals(choice_course_text.getTag())){
				    //结束当前activity
				    Intent retIntent = new Intent();
				    Bundle retBundle = new Bundle();
				    retBundle.putString("courseId", mCourse.getId());
				    retIntent.putExtras(retBundle);
				    ChoiceCourseActivity.this.setResult(-1, retIntent);
				}
				ChoiceCourseActivity.this.finish();
			}
		});
	}

	private void initData() {
		this.mCourse = (MCCourseModel) this.getIntent().getSerializableExtra("course");
		this.mCourseService = new MCCourseService();
		String uid = MCSaveData.getUserInfo(Contants.UID, this).toString();
		this.mCourseService.getCourseDetailByCourseId(uid, this.mCourse.getId(), ((MCAnalyzeBackBlock) this), this);
		 //根据图片来设置尺寸
        ViewGroup.LayoutParams picParams = coursePic.getLayoutParams();
        picParams.width = MCResolution.getInstance(this).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
        picParams.height = MCResolution.getInstance(this).scaleHeightByScaleWidth(picParams.width);
        coursePic.setLayoutParams(picParams);
        ViewGroup.LayoutParams contentParams = contentLayout.getLayoutParams();
        contentParams.width = picParams.width;
        contentParams.height = picParams.height;
        contentLayout.setLayoutParams(contentParams);
        
        //定义一个广播接收者 onReceive 做不同处理
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Contants.USER_LOGIN_ACTION)) {
                    ChoiceCourseActivity.this.refreshAfterLogin();
                }else if(intent.getAction().equals(Contants.USER_LOGOUT_ACTION)) {
                    ChoiceCourseActivity.this.refreshAfterLogin();
                }
            }
        };
        this.courseName.bringToFront();//课程名称在学分图片的上面，同时父布局重新绘制。
        this.contentLayout.requestLayout();
        this.contentLayout.invalidate();
	}

	private void initView() {
		this.choice_course =(RelativeLayout) this.findViewById(R.id.choice_course);
		this.teacher_layout = (LinearLayout) this.findViewById(R.id.teacher_layout);
		this.course_info = (TextView) this.findViewById(R.id.course_info);
		this.no_info = (TextView) this.findViewById(R.id.no_info);
		this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.dialog_loading_anim);
		this.courseName = (TextView) this.findViewById(R.id.name);
		this.mainTeacher = (TextView) this.findViewById(R.id.main_teacher);
		this.timeCredit = (TextView) this.findViewById(R.id.time_credit);
		this.contentLayout = (RelativeLayout) this.findViewById(R.id.content_layout);
		this.coursePic = (MCImageView) this.findViewById(R.id.image);
		this.startTime = (TextView) this.findViewById(R.id.start_time);
		this.choiceCount = (TextView) this.findViewById(R.id.choice_count);
		this.choiceTarget = (TextView) this.findViewById(R.id.target_info);
        this.choice_course_text = (TextView) this.findViewById(R.id.choice_course_text);
        this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
	}
	
	 protected void refreshAfterLogin() {
		 String uid = MCSaveData.getUserInfo(Contants.UID, this).toString();
		 this.mCourseService.getCourseDetailByCourseId(uid, this.mCourse.getId(), ((MCAnalyzeBackBlock) this), this);
	}

	public void OnAnalyzeBackBlock(MCServiceResult result, List arg21) {
	        int v5 = 0;
	        int v4 = 0;
	        MCCourseDetailModel model = null;
			    //模拟数据
	        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
	        	if(!(arg21.get(0) instanceof MCCourseDetailModel)){
	        		return;
	        	}
	            model = (MCCourseDetailModel) arg21.get(0);
	        	if(model==null){
	        		return;
	        	}

	           
	            try {
	                v4 = MCResolution.getInstance(this).scaleWidth(Contants.USER_PIC_WIDTH_COURSEINFO);
	                v5 = v4;
	                ViewGroup.LayoutParams v13 = this.no_info.getLayoutParams();
	                v13.height = v5;
	                this.no_info.setLayoutParams(v13);
	                if(this.teacher_layout.getChildCount() > 0) {
	                    this.teacher_layout.removeAllViews();
	                }

	                if(model.getTeacher().size() == 0) {
	                    this.no_info.setVisibility(View.VISIBLE);
	                }

	            } catch(Exception v10) {
	            	v10.printStackTrace();
	            	  this.teacher_layout.setVisibility(View.GONE);
	                  this.no_info.setVisibility(View.VISIBLE);
	            }

	            ViewGroup.LayoutParams picParams = coursePic.getLayoutParams();
	            
	            //图片、名字直接从原来传过来的值里取
	            this.coursePic.setImageUrl(this.mCourse.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), picParams.width, picParams.height, false, ImageType.CICLE_IMAGE, null);
	            this.courseName.setText(this.mCourse.getName());
	            
	            this.course_info.setText(Html.fromHtml(model.getDescription()));
	            this.mainTeacher.setText(this.getString(R.string.course_main_teacher_label, new Object[]{model.getMainTeacher()==null?"":model.getMainTeacher()}));
	            this.timeCredit.setText(this.getString(R.string.course_time_credit_label, new Object[]{10+"      ", model.getCredit()==null?"":model.getCredit()}));
	            this.startTime.setText(this.getString(R.string.course_start_date_label, new Object[]{model.getStartDate()==null?"":model.getStartDate()}));
	            this.choiceCount.setText(this.getString(R.string.course_choice_count_label, new Object[]{model.getCount()}));
	            this.choiceTarget.setText(Html.fromHtml(model.getTarget()));
	            this.choice_course.setVisibility(View.VISIBLE);//选课按钮设置为可见
	        }

	        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
	            return;
	        }
	        try {
	        	if(model == null || model.getTeacher()==null){
	        		return;
	        	}
	            for(int i=0;i<model.getTeacher().size();i++) {
	            	MCUserModel teacher = (MCUserModel) model.getTeacher().get(i);
	                TeacherLayout teaLayout = new TeacherLayout(this);
//	                MCHeadImageView teaPic = (MCHeadImageView) teaLayout.findViewById(R.id.teacher_pic);
//	                teaPic.setOnClickListener(null);//清除掉点击事件
//	                ViewGroup.LayoutParams v15 = ((MCHeadImageView)teaPic).getLayoutParams();
//	                v15.width = MCResolution.getInstance(this).scaleWidth(Contants.USER_PIC_WIDTH_COURSEINFO);
//	                v15.height = v15.width;
//	                teaPic.setLayoutParams(v15);
	                WebView teaInfo = (WebView) teaLayout.findViewById(R.id.teacher_info);
	               
	                
	                TextView v18 = (TextView) teaLayout.findViewById(R.id.teacher_name);
	                View bottomLine = teaLayout.findViewById(R.id.bottom_line);
//	                teaPic.setMCUserModel( teacher);
	                v18.setText(teacher.getNickname());
	        		
	        		WebViewUtil.loadContentWithPic(teacher.getDesc()==null?"":teacher.getDesc(),teaInfo,this );
	                
//	                teaPic.setImageUrl(teacher.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), v4, v5, true, ImageType.CICLE_IMAGE, new MCImageHandleInterface() {
//	                    public Bitmap handleBitmapShape(Bitmap bitmap, ImageType type) {
//	                        if(ImageType.CICLE_IMAGE == type) {
//	                            bitmap = MCBitmapScaledAndHandled.toRoundBitmap(bitmap);
//	                        }
//
//	                        return bitmap;
//	                    }
//
//	                    public Bitmap scaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
//	                        return null;
//	                    }
//	                });
	                if(i == model.getTeacher().size() - 1) {
	                    bottomLine.setVisibility(View.GONE);
	                }

	                this.teacher_layout.addView(teaLayout);
	                this.no_info.setVisibility(View.GONE);
	            }

	            return;
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	            this.teacher_layout.setVisibility(View.GONE);
	            this.no_info.setVisibility(View.VISIBLE);
	            return;
	        }
	    }

}
