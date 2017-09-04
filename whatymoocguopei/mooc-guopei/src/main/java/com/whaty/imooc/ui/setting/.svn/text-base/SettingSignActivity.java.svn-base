package com.whaty.imooc.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.uikit.toast.MCToast;

import cn.com.whatyguopei.mooc.R;


public class SettingSignActivity extends MCBaseActivity {
	private BaseTitleView tv_title;
	private EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_sign);
		initViewAndClickListener();
		Intent intent = getIntent();
		String oldSignName = intent.getStringExtra("oldSign");
		if (oldSignName != null) {
			et_content.setText(oldSignName);
			//光标定位到最后一行提升用户体验
			et_content.setSelection(oldSignName.length());
		}
	}

	/**
	 * 这里比较少 直接初始化跟加上点击事件
	 * 
	 */
	private void initViewAndClickListener() {
		tv_title = (BaseTitleView) this.findViewById(R.id.rl_titile);
		et_content = (EditText) this.findViewById(R.id.sign);
		tv_title.setRigTextListener(new RightClickListener() {
			
			@Override
			public void onRightViewClick() {
				String newSign = et_content.getText().toString();
				if (newSign.trim().equals("")) {
					MCToast.show(getApplicationContext(), "写几个字吧");
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("newSign", newSign);
				setResult(4, intent);
				finish();
			}
		});
	}
}
