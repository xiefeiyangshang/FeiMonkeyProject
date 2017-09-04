package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.uikit.resolution.MCResolution;


public class EditPictureView extends RelativeLayout {

	private ImageView show_pic;
	private ImageView del_pic;
	private RelativeLayout layout = null;
	private RelativeLayout root_content;
	private String orgPath;//原图路径（大图）
	private String compressPath;//压缩后的图片路径（小图）
	
	public EditPictureView(Context context) {
		super(context);
		initView(context);
	}

	public EditPictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		initView(context);
	}

	public EditPictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		if (this.layout == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = (RelativeLayout) inflater.inflate(R.layout.pic_item_layout, this);
		}
		this.root_content = (RelativeLayout) this.layout.findViewById(R.id.root_content);
		this.show_pic = (ImageView) this.layout.findViewById(R.id.show_pic);
		this.del_pic = (ImageView) this.layout.findViewById(R.id.del_pic);

		this.del_pic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recycleBitmap();
				EditPictureView.this.setVisibility(View.GONE);
			}
		});
		
		//动态调整大小
		int width = MCResolution.getInstance(context).getDevWidth(context);
		LayoutParams lp = (LayoutParams) this.root_content.getLayoutParams();
		lp.width = (int) (width * 0.3);
		lp.height = lp.width;
		this.setLayoutParams(lp);
	}

	public void setImageBitmap(Bitmap bitmap) {
		this.show_pic.setTag(bitmap);
		this.show_pic.setImageBitmap(bitmap);
		this.del_pic.setVisibility(View.VISIBLE);
	}

	//释放bitmap
	public void recycleBitmap(){
		Bitmap bm = (Bitmap) show_pic.getTag();
		if(bm!=null){
			bm.recycle();
		}
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public String getCompressPath() {
		return compressPath;
	}

	public void setCompressPath(String compressPath) {
		this.compressPath = compressPath;
	}
	
}
