package com.whatyplugin.uikit.dialog;

import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.uikit.resolution.MCResolution;

public class MCListViewDialog {
	private TextView tv_title;
	private TextView tv_content;
	private ImageView iv_content;
	public OnItemClickListener listener;
	private ListView listView;

	/**
	 * listView中只有一个TextView
	 * 
	 * 设置 dialog 弹框的大小在主布局里面设置 这样比较灵活
	 * 
	 * @param mcontext
	 *            不解释
	 * @param title
	 *            dialog的标题
	 * @param data
	 *            需要显示的数据这里适配器是SimpAdapter
	 * @return Dialog 返回你需要的
	 */

	public Dialog showOnlytextViewDialog(Context mcontext, String title, List<Map<String, String>> data, String from) {
		Dialog dialog = dialogView(title, mcontext);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setWindowAnimations(R.style.DialogShowStyle);
		window.setBackgroundDrawableResource(android.R.color.transparent);
		SimpleAdapter contentListViewAdpater = new SimpleAdapter(mcontext, data, R.layout.listview_dialog_textview_items, new String[] { from },
				new int[] { R.id.onlytextview });
		listView.setAdapter(contentListViewAdpater);

		listView.setOnItemClickListener(listener);

		initDialogHeight(mcontext, data);
		return dialog;
	}

	/**
	 * 前面是 imageView 后面是 TextView 这里用的还是simpeAdapter 图片使用资源文件ID
	 * 
	 * 设置 dialog 弹框的大小在主布局里面设置 这样比较灵活
	 * 
	 * @param mcontext
	 *            不解释
	 * @param title
	 *            标题
	 * @param data
	 *            数据
	 * @return Dialog
	 */

	public Dialog showImageViewAndTextView(Context mcontext, String title, List<Map<String, Object>> data, String[] from) {

		Dialog dialog = dialogView(title, mcontext);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setWindowAnimations(R.style.DialogShowStyle);
		window.setBackgroundDrawableResource(android.R.color.transparent);
		SimpleAdapter contentListViewAdpater = new SimpleAdapter(mcontext, data, R.layout.listview_dialog_img_textview_items, from,
				new int[] { R.id.onlytextview });
		listView.setAdapter(contentListViewAdpater);

		listView.setOnItemClickListener(listener);

		initDialogHeight(mcontext, data);
		return dialog;
	}

	private void initDialogHeight(Context mcontext, List data) {
		if (data.size() > 6) {
			int height = MCResolution.getInstance(mcontext).getDevHeight(mcontext);
			LinearLayout parent = (LinearLayout) listView.getParent();
			ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) parent.getLayoutParams();
			lp.height = height * 2 / 3;
			parent.setLayoutParams(lp);
		}
	}

	/**
	 * 初始化 数据
	 * 
	 */

	private Dialog dialogView(String title, Context mContext) {
		Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.listview_dialog);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setWindowAnimations(R.style.DialogShowStyle);
		window.setBackgroundDrawableResource(android.R.color.transparent);
		tv_title = (TextView) dialog.findViewById(R.id.dialogtitle);
		tv_title.setText(title);
		listView = (ListView) dialog.findViewById(R.id.listviewdialog);
		return dialog;
	}

}
