package com.whaty.imooc.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.whaty.imooc.ui.index.MCMainActivity;
import com.whaty.imooc.ui.login.MCNewLoginActivity;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.MCConstant;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;

import cn.com.whatyguopei.mooc.R;

public class MCWelcome extends MCBaseActivity {
	private ImageView im_Welcome;
	private int flagClass = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		im_Welcome = (ImageView) findViewById(R.id.im_welcome);
		im_Welcome.setBackgroundResource(MCConstant.WELCOME_IMAGE);
		start();
	}

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					Class startClazz = isLogined() ? MCMainActivity.class : MCNewLoginActivity.class;
					Thread.sleep(MCConstant.SLEEP_TIME);
					Intent intent = new Intent(MCWelcome.this, startClazz);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private boolean isLogined() {
		String banji1 = getValue(GPContants.USER_BANJIID_1);
		String banji2 = getValue(GPContants.USER_BANJIID_2);
		String banji = getValue(GPContants.USER_BANJIID);
		boolean flag = (banji1.equals(Contants.DEFAULT_UID) && banji2.equals(Contants.DEFAULT_UID))&&banji.equals(Contants.DEFAULT_UID) ? false : true;
		System.out.println("flag====   " + flag+"    "+banji);
		return flag;
	}

	private boolean TimeOutClass() {
		// 先判断班级 是否都过期
		Boolean one_TimeOut = false, two_TimeOut = false; // true
		String one_ClassEndTime = getValue(GPContants.USER_ENDTIME_1);
		String one_ClassStartTime = getValue(GPContants.USER_STARTTIME_1);
		String two_ClassEndTime = getValue(GPContants.USER_ENDTIME_2);
		String two_ClassStartTime = getValue(GPContants.USER_STARTTIME_2);
		if (!"".equals(one_ClassEndTime) && !"".equals(one_ClassStartTime)) {
			one_TimeOut = inTime(one_ClassStartTime, one_ClassEndTime);
		} else {
			one_TimeOut = !getValue(GPContants.USER_BANJIID_1).equals("");
		}

		if (!"".equals(two_ClassEndTime) && !"".equals(two_ClassStartTime)) {
			two_TimeOut = inTime(two_ClassStartTime, two_ClassEndTime);
		} else {
			two_TimeOut = !getValue(GPContants.USER_BANJIID_1).equals("");
		}
		if (one_TimeOut && two_TimeOut) {
			return true;
		} else {
			if (one_TimeOut) { // 第一个班级 自动切换到 第一个班级
				flagClass = 1;
				saveUserBanJi();
				return true;
			}
			if (two_TimeOut) { // 第二个班级 自动切换到第二个班级
				flagClass = 2;
				saveUserBanJi();
				return true;
			}

		}
		SharedClassInfo.clear(); // 都超时就 删除用户信息
		return false;
	}

	private String getValue(String key) {
		return SharedClassInfo.getKeyValue(key);
	}

	// 第一个班级 还是第二个数据
	private void saveUserBanJi() {
		SharedClassInfo.saveUserOnlyOneClass("true");
		SharedClassInfo.saveUserBanjiId(getValue(GPContants.USER_BANJIID + flagClass));
		SharedClassInfo.saveUserHeadTeacherName(getValue(GPContants.USER_HEADTEACHERNAME + flagClass));
		SharedClassInfo.saveUserOrganId(getValue(GPContants.USER_ORGANID + flagClass));
		SharedClassInfo.saveUserBanjiName(getValue(GPContants.USER_BANJINAME + flagClass));
		SharedClassInfo.saveUserProjectId(getValue(GPContants.USER_PROJECTID + flagClass));
		SharedClassInfo.saveUserHeadTeacherPhone(getValue(GPContants.USER_HEADTEACHERPHONE + flagClass));
		SharedClassInfo.saveUserHomeWordCourseId(getValue(GPContants.USER_HOMECOURSEID + flagClass));
	}

	private Boolean inTime(String startTime, String endTime) {
		System.out.println("打印时间===" + startTime + "   " + endTime);
		return DateUtil.YYYYMMDDBeforeNow(endTime) && !DateUtil.YYYYMMDDBeforeNow(startTime);
	}

}
