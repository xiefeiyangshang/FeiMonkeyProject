package com.whaty.imooc.ui.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whaty.imooc.utile.SharedPrefsUtil;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

/**
 * Created by fhk on 2017/9/1.
 */

public class NewChangeClassDialog extends DialogFragment implements View.OnClickListener {


	private View view;
	private Button submit;
	private Button cancel;
	private ListView list_class;
	private List<Map<String, Object>> mData;
	private int number;
	private Context mContext;
	private MyAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
		this.mContext = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		mData = new ArrayList<Map<String, Object>>();

			for (int i = 0; i <5 ; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", "班级"+ i);
				mData.add(map);
			}
			


		getDialog().getWindow().setGravity(Gravity.CENTER);
		getDialog().getWindow().setBackgroundDrawableResource(R.color.none);
//		Window window = getDialog().getWindow();
//		WindowManager.LayoutParams params = window.getAttributes();
//
		// 去掉dialogfragment自带的title
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = inflater.inflate(R.layout.changclass_dialog, container);
		initView();
		initEvent();
		return view;
	}

	private void initView() {
		submit = (Button) view.findViewById(R.id.submit);
		cancel = (Button) view.findViewById(R.id.cancel);
		list_class = (ListView) view.findViewById(R.id.list_class_1);
		adapter = new MyAdapter();
		list_class.setAdapter(adapter);

	}

	private void initEvent() {

		AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

			@Override

			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//记录选择的班级
				number = position;
				SharedPrefsUtil.putLong("CLASS_POSITION",position);
				adapter.notifyDataSetChanged();

			}
		};
		list_class.setOnItemClickListener(listener);
		cancel.setOnClickListener(this);
		submit.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel:
				dismiss();
			case R.id.submit:
				saveUserBanJi(number);
                // 在这里发广播通知吧
                getActivity().sendBroadcast(new Intent(Contants.USER_LOGIN_ACTION));
                getDialog().dismiss();
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}


	/**
	 * 保存当前选择的班级数据
 	 */

	private void saveUserBanJi(int number) {
		SharedClassInfo.saveUserBanjiId(getKeyValue(GPContants.USER_BANJIID + number));
		SharedClassInfo.saveUserHeadTeacherName(getKeyValue(GPContants.USER_HEADTEACHERNAME + number));
		SharedClassInfo.saveUserOrganId(getKeyValue(GPContants.USER_ORGANID + number));
		SharedClassInfo.saveUserBanjiName(getKeyValue(GPContants.USER_BANJINAME + number));
		SharedClassInfo.saveUserProjectId(getKeyValue(GPContants.USER_PROJECTID + number));
		SharedClassInfo.saveUserHeadTeacherPhone(getKeyValue(GPContants.USER_HEADTEACHERPHONE + number));
		SharedClassInfo.saveUserHomeWordCourseId(getKeyValue(GPContants.USER_HOMECOURSEID + number));
	    SharedClassInfo.saveUserStatusName(getKeyValue(GPContants.USER_STATUSNAME+ number));
	}

	private String getKeyValue(String key) {
		return SharedClassInfo.getKeyValue(key);
	}


	private class MyAdapter extends BaseAdapter {
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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.class_item, null);
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

	private final class ViewHolder {

		TextView class_name;

	}
}
