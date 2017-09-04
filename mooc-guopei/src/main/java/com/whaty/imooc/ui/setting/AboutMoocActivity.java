package com.whaty.imooc.ui.setting;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;

import cn.com.whatyguopei.mooc.R;

public class AboutMoocActivity extends MCBaseActivity {
	private TextView version;
	private BaseTitleView bt_Center;

	@Override
	protected void onCreate(Bundle arg0) {
		try {
			PackageInfo info = null;
			super.onCreate(arg0);
			this.setContentView(R.layout.about_layout);
			bt_Center = (BaseTitleView) findViewById(R.id.rl_titile);
			bt_Center.setTitle("关于国培");
			this.version = (TextView) this.findViewById(R.id.version);
			PackageManager pm = this.getPackageManager();
			info = pm.getPackageInfo(this.getPackageName(), 0);
			this.version.setText(this.getString(R.string.version_label, new Object[] { info.versionName }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
