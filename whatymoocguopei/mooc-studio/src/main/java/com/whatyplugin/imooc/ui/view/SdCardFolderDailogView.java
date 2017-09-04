package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.FileUtils;

public class SdCardFolderDailogView extends LinearLayout implements View.OnClickListener {
	public interface IExitListener {
		void cancel();

		void save(String path);
	}

	private IExitListener listener;
	private Context mContext;
	private ImageView phone_check_img;
	private ImageView sdcard_check_img;
	private String[] storageList;

	public SdCardFolderDailogView(Context context) {
		super(context);
		this.mContext = context;
		View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.sdcard_dialog_layout, null);
		View cancle_view = rootView.findViewById(R.id.cancle_tv);
		View continue_view = rootView.findViewById(R.id.continue_tv);
		rootView.findViewById(R.id.sdcard_layout).setOnClickListener(this);
		rootView.findViewById(R.id.phone_layout).setOnClickListener(this);
		this.phone_check_img = (ImageView) rootView.findViewById(R.id.phone_check_img);
		this.sdcard_check_img = (ImageView) rootView.findViewById(R.id.sdcard_check_img);
		((TextView) rootView.findViewById(R.id.phone_size)).setText(this.mContext.getString(R.string.sdcard_size_label,
				new Object[] { FileUtils.FormetFileSize(FileUtils.getAvailaleSDSize(FileUtils.storageList(this.mContext)[0])) }));
		((TextView) rootView.findViewById(R.id.sdcard_size)).setText(this.mContext.getString(R.string.sdcard_size_label,
				new Object[] { FileUtils.FormetFileSize(FileUtils.getAvailaleSDSize(FileUtils.storageList(this.mContext)[1])) }));
		cancle_view.setOnClickListener(this);
		continue_view.setOnClickListener(this);
		this.addView(rootView);
		this.storageList = FileUtils.storageList(this.mContext);
		if (MCSaveData.getDownloadSDcardPath(this.mContext).equals(this.storageList[0])) {
			this.phone_check_img.getDrawable().setLevel(1);
			this.sdcard_check_img.getDrawable().setLevel(0);
		} else {
			this.phone_check_img.getDrawable().setLevel(0);
			this.sdcard_check_img.getDrawable().setLevel(1);
		}
	}

	public void onClick(View v) {
		if (this.listener != null) {
			if (v.getId() == R.id.continue_tv) {
				if (this.phone_check_img.getDrawable().getLevel() == 0) {
					this.listener.save(this.storageList[1]);
				} else {
					this.listener.save(this.storageList[0]);
				}
			} else if (v.getId() == R.id.cancle_tv) {
				this.listener.cancel();
			} else if (v.getId() == R.id.phone_layout) {
				this.phone_check_img.getDrawable().setLevel(1);
				this.sdcard_check_img.getDrawable().setLevel(0);
			} else if (v.getId() == R.id.sdcard_layout) {
				this.phone_check_img.getDrawable().setLevel(0);
				this.sdcard_check_img.getDrawable().setLevel(1);
			}
		}

	}

	public void setIExitListener(IExitListener l) {
		this.listener = l;
	}
}
