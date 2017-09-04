package com.whatyplugin.imooc.ui.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.ui.showimage.PicFullScreenShowActivity;

/**
 * 从本地选取图片、拍照的公用控件
 * 
 * @author Administrator
 * 
 */
public class ChoicePicFromLocalView extends RelativeLayout implements View.OnClickListener {
	public static final int RESULT_OK = -1;
	private RelativeLayout layout = null;
	private LinearLayout content_img;
	private Context context;
	private String cameraFile;

	private ImageView camera_img;
	private ImageView choice_img;

	private String orgPicPath = "";

	// 用来保存所有的图片控件
	private List<EditPictureView> picList = new ArrayList<EditPictureView>();

	public ChoicePicFromLocalView(Context context) {
		super(context);
		initView(context);
		this.context = context;
	}

	public ChoicePicFromLocalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		initView(context);
		this.context = context;
	}

	public ChoicePicFromLocalView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		this.context = context;
	}

	private void initView(Context context) {
		if (this.layout == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = (RelativeLayout) inflater.inflate(R.layout.pic_choice_view_layout, this);
		}

		this.content_img = (LinearLayout) this.layout.findViewById(R.id.content_img);
		this.camera_img = (ImageView) this.layout.findViewById(R.id.camera_img);
		this.choice_img = (ImageView) this.layout.findViewById(R.id.choice_img);

		this.camera_img.setOnClickListener(this);
		this.choice_img.setOnClickListener(this);

	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.camera_img) {
			if (checkNumbers()) {
				cameraFile = FileUtils.getTakePhotoPath();
				File file = new File(cameraFile);
				if (!file.getParentFile().exists()) {
					// 如果不存在则创建
					file.getParentFile().mkdir();
				}
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraFile)));
				((Activity) this.context).startActivityForResult(intent, 1);
			}
		} else if (id == R.id.choice_img) {
			if (checkNumbers()) {
				Intent picIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				((Activity) this.context).startActivityForResult(picIntent, 2);
			}
		} else {
		}
	}

	private boolean checkNumbers() {
		if (getAllFilePaths().size() >= 3) {
			Toast.makeText(context, "最多只能选取三张图片哦", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				addShowPicView(cameraFile);
			}
		}

		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				String path = getFilePath(data.getData());
				addShowPicView(path);
			}
		}

	}

	/**
	 * 添加图片视图
	 * 
	 * @param path
	 */
	private void addShowPicView(String path) {
		if (getOrgFilePaths().contains(path)) {
			Toast.makeText(context, "该照片已经存在了呢！", Toast.LENGTH_SHORT).show();
			return;
		}

		try {
			final EditPictureView pic_img = new EditPictureView(this.context);
			File file = new File(path);
			if (file.exists()) {

				Bitmap bitmap = FileUtils.getBitmap(path);
				pic_img.setImageBitmap(bitmap); // 设置Bitmap
				String compressPath = FileUtils.saveImage(bitmap);

				if (!TextUtils.isEmpty(compressPath)) {

					pic_img.setCompressPath(compressPath);
					pic_img.setOrgPath(path);
					content_img.addView(pic_img);
					pic_img.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							showFullScreenPic(pic_img);
						}
					});
					picList.add(pic_img);
				} else {
					Toast.makeText(context, "图片操作失败", Toast.LENGTH_SHORT).show();
					bitmap.recycle();
				}
			} else {
				Toast.makeText(context, "照片不存在", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showFullScreenPic(EditPictureView pic_img) {
		Intent intent = new Intent(context, PicFullScreenShowActivity.class);
		List<String> imgPath = this.getAllFilePaths();
		Bundle bundle = new Bundle();
		bundle.putSerializable("imgPath", (Serializable) imgPath);
		bundle.putSerializable("startIndex", imgPath.indexOf(pic_img.getCompressPath()));
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	@Override
	protected void onDetachedFromWindow() {
		for (EditPictureView pic : picList) {
			pic.recycleBitmap();
		}
		super.onDetachedFromWindow();
	}

	/**
	 * 获得所有处理后的图片路径
	 * 
	 * @return
	 */
	public List<String> getAllFilePaths() {
		List<String> imgPaths = new ArrayList<String>();
		for (EditPictureView pic : picList) {
			if (pic.getVisibility() == View.VISIBLE) {
				imgPaths.add(pic.getCompressPath());
			}
		}
		return imgPaths;
	}

	/**
	 * 获得所有原图片路径
	 * 
	 * @return
	 */
	public List<String> getOrgFilePaths() {
		List<String> imgPaths = new ArrayList<String>();
		for (EditPictureView pic : picList) {
			if (pic.getVisibility() == View.VISIBLE) {
				imgPaths.add(pic.getOrgPath());
			}
		}
		return imgPaths;
	}

	private String getFilePath(Uri uri) {
		try {
			if (uri.getScheme().equals("file")) {
				return uri.getPath();
			} else {
				return getFilePathByUri(uri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
		String imgPath;
		Cursor cursor = context.getContentResolver().query(mUri, null, null, null, null);
		cursor.moveToFirst();
		imgPath = cursor.getString(1);
		cursor.close();
		return imgPath;
	}

	public void setContentWrap(LinearLayout pic_content_img) {
		this.content_img = pic_content_img;
	}

	public void setOrgPic(List<String> picPaths) {
		if (picPaths == null) {
			return;
		}
		for (String path : picPaths) {
			addShowPicView(path);
			orgPicPath += path;
		}
	}

	// 图片是否有变动
	public boolean isValueChanged() {
		List<String> list = getOrgFilePaths();
		String result = "";
		for (String str : list) {
			result += str;
		}
		return result.equals(orgPicPath);
	}

}
