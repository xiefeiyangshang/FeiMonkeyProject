package com.whaty.imooc.ui.login;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyguopei.mooc.R;

import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.GPStringUtile;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.imooc.logic.contants.Contants;

/**
 * 自己获取需要的数据吧
 * 
 * @author whaty
 * 
 */

public class ChangClassDiag extends DialogFragment implements OnClickListener {

//关联SVN

	private View view;
	private TextView tv_FistClass;
	private TextView tv_SecondClass;
	private Button bt_OK;
	private ImageView iv_SignOne;
	private ImageView iv_SignTwo;
	private String FRISTCLASS = "1"; // 点击的第一个班级
	private String SECONDCLASS = "2"; // 选择的第二个班级
	private String flagClass; // 存贮点击的位置
	private int redColor;
	private Button bt_Cancel;
	private RelativeLayout rl_ClassOne;
	private RelativeLayout rl_ClassTwo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getDialog().getWindow().setGravity(Gravity.CENTER);
		getDialog().getWindow().setBackgroundDrawableResource(R.color.none);
		// 去掉dialogfragment自带的title
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = inflater.inflate(R.layout.changthemeclass, container);
		initView();
		initEvent();
		initData();
		flagSubmit();
		return view;
	}

	private void initView() {
		tv_FistClass = (TextView) view.findViewById(R.id.class_one);
		tv_SecondClass = (TextView) view.findViewById(R.id.class_two);
		bt_OK = (Button) view.findViewById(R.id.submit);
		iv_SignOne = (ImageView) view.findViewById(R.id.rightsign_one);
		iv_SignTwo = (ImageView) view.findViewById(R.id.rightsign_two);
		bt_Cancel = (Button) view.findViewById(R.id.cancel);
		rl_ClassOne = (RelativeLayout) view.findViewById(R.id.rl_class_one);
		rl_ClassTwo = (RelativeLayout) view.findViewById(R.id.rl_class_two);
	}

	private void initEvent() {
		rl_ClassOne.setOnClickListener(this);
		rl_ClassTwo.setOnClickListener(this);
		bt_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

			}
		});
		bt_OK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
				// 查看悬着的
				saveUserBanJi();
				// 在这里发广播通知吧
				getActivity().sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
			}
		});
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}

	private void initData() {
		bt_OK.setEnabled(false);
		redColor = getcolor(R.color.theme_color);
		tv_FistClass.setText(getNameAndTime(GPContants.USER_BANJINAME_1, GPContants.USER_STARTTIME_1, GPContants.USER_ENDTIME_1));
		tv_SecondClass.setText(getNameAndTime(GPContants.USER_BANJINAME_2, GPContants.USER_STARTTIME_2, GPContants.USER_ENDTIME_2));
	}

	@Override
	public void onClick(View v) {
		int ClickId = v.getId();
		int classOne = R.id.rl_class_one; // 点击我的第一个班级
		int classtwo = R.id.rl_class_two; // 点击我的第二个班级
		InVisibleImageView();
		// submit2Red();
		if (ClickId == classOne) {
			saveClassOne();
		}
		if (ClickId == classtwo) {
			saveClassTwo();
		}

	}

	private String getNameAndTime(String classKey, String startTime, String EndTime) {
		String NameAndTime = getKeyValue(classKey) + GPStringUtile.StartTimeAndEndTime(getKeyValue(startTime), getKeyValue(EndTime));

		return NameAndTime;
	}

	private void saveClassOne() {
		iv_SignOne.setImageResource(R.drawable.select_class_on);
		tv_FistClass.setTextColor(redColor);
		flagClass = FRISTCLASS;
	}

	private void saveClassTwo() {
		iv_SignTwo.setImageResource(R.drawable.select_class_on);
		tv_SecondClass.setTextColor(redColor);
		flagClass = SECONDCLASS;
	}

	// 获取颜色值
	private int getcolor(int color) {
		return getActivity().getResources().getColor(color);
	}

	// 初始化颜色值，全部设置成默认的无色
	private void InVisibleImageView() {
		bt_OK.setEnabled(true);
		iv_SignOne.setImageResource(R.drawable.select_class_off);
		iv_SignTwo.setImageResource(R.drawable.select_class_off);
		int bodyColor = getcolor(R.color.body_color);
		tv_FistClass.setTextColor(bodyColor);
		tv_SecondClass.setTextColor(bodyColor);
	}

	// 第一个班级 还是第二个数据
	private void saveUserBanJi() {
		SharedClassInfo.saveUserBanjiId(getKeyValue(GPContants.USER_BANJIID + flagClass));
		SharedClassInfo.saveUserHeadTeacherName(getKeyValue(GPContants.USER_HEADTEACHERNAME + flagClass));
		SharedClassInfo.saveUserOrganId(getKeyValue(GPContants.USER_ORGANID + flagClass));
		SharedClassInfo.saveUserBanjiName(getKeyValue(GPContants.USER_BANJINAME + flagClass));
		SharedClassInfo.saveUserProjectId(getKeyValue(GPContants.USER_PROJECTID + flagClass));
		SharedClassInfo.saveUserHeadTeacherPhone(getKeyValue(GPContants.USER_HEADTEACHERPHONE + flagClass));
		SharedClassInfo.saveUserHomeWordCourseId(getKeyValue(GPContants.USER_HOMECOURSEID + flagClass));
	   SharedClassInfo.saveUserStatusName(getKeyValue(GPContants.USER_STATUSNAME+ flagClass));
	}

	private String getKeyValue(String key) {
		return SharedClassInfo.getKeyValue(key);
	}


	// 每次点击进入的点击状态
	private void flagSubmit() {
		Boolean isLoad = !SharedClassInfo.getKeyValue(GPContants.USER_PROJECTID).equals("");
		getDialog().setCanceledOnTouchOutside(isLoad);

		if (isLoad) {
			bt_OK.setEnabled(true);
			String banji = SharedClassInfo.getKeyValue(GPContants.USER_BANJIID).trim();
			String banji1 = SharedClassInfo.getKeyValue(GPContants.USER_BANJIID_1).trim();
			String banji2 = SharedClassInfo.getKeyValue(GPContants.USER_BANJIID_2).trim();
			// 不是第一个就是第二个班级
			if (banji.equals(banji1)) {
				saveClassOne();
			}
			if (banji.equals(banji2)) {
				saveClassTwo();
			}

		}

	}

}
