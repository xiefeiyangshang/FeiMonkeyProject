package com.whatyplugin.imooc.ui.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;



public class MCCommonAlertDialog extends Dialog {

	public static final int MB_OK = 0;//只有确定类型的对话框
	public static final int MB_CANCELOK = 1;//确定取消都有的对话框
	public static final int MB_LOADING = 2;//正在加载的等待框
	public static final int NO = 0;//取消，设置监听事件的时候用
	public static final int YES = 1;//确认，设置监听事件的时候用
	
	private View.OnClickListener commitListener;
	private View.OnClickListener cancelListener;
	private Button bt_cancel;
	private Button bt_commit;
	private TextView dialog_message;
	private TextView loading_message;

	private int type;
	public MCCommonAlertDialog(Context context) {
		super(context);
		initView(context, 1);
	}

	
	public MCCommonAlertDialog(Context context, int type) {
		super(context);
		initView(context, type);
	}
	

	/**
	 * 按钮类型+信息
	 * @param context
	 * @param type
	 * @param message
	 */
	public MCCommonAlertDialog(Context context, int type, String message) {
		super(context);
		initView(context, type);
		setMessage(message);
	}
	
	private void initView(Context context, int type) {
		this.type = type;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LinearLayout view = (LinearLayout) View.inflate(context, R.layout.common_dialog_layout, null);
		setContentView(view);
		this.dialog_message = (TextView) view.findViewById(R.id.dialog_message);
		this.loading_message = (TextView) view.findViewById(R.id.loading_message);
		this.bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		this.bt_commit = (Button) view.findViewById(R.id.bt_commit);
		this.bt_commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(commitListener!=null){
					commitListener.onClick(v);
				}
				MCCommonAlertDialog.this.dismiss();
			}
		});
		this.bt_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cancelListener!=null){
					cancelListener.onClick(v);
				}
				MCCommonAlertDialog.this.dismiss();
			}
		});

		if(type == MB_OK){
			this.bt_cancel.setVisibility(View.GONE);
			view.findViewById(R.id.divid_line).setVisibility(View.GONE);
			view.findViewById(R.id.loading_dialog).setVisibility(View.GONE);
		}else if(type == MB_CANCELOK){
			view.findViewById(R.id.loading_dialog).setVisibility(View.GONE);
		}else if(type ==MB_LOADING){
			view.findViewById(R.id.tip_dialog).setVisibility(View.GONE);
			view.findViewById(R.id.loading_dialog).setVisibility(View.VISIBLE);
			
			ImageView image = (ImageView) view.findViewById(R.id.loading_pic);
		    Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_loading_anim);
		    image.startAnimation(animation);
		}
		
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setBackgroundDrawableResource(R.drawable.shape_corner);  //设置圆角属性   在父布局中写比较好点
		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); 
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		lp.width = (int) (dm.widthPixels * 0.9); // 设置宽度
		lp.alpha=0.88f;//设置透明度
		dialogWindow.setAttributes(lp);
		
		//点击边缘是否关闭对话框
		setCanceledOnTouchOutside(false);
	}

	/**
	 * 设置监听事件，自带销毁，只要点击了按钮就会销毁了。
	 * @param listener
	 * @param type
	 */
	public MCCommonAlertDialog setOnClickListener(android.view.View.OnClickListener listener, int type){
		if(type == 1){
			this.commitListener = listener;
		}else if(type == 0){
			this.cancelListener = listener;
		}
		return this;
	}
	
	/**
	 * 设置显示消息的内容
	 * @param message
	 * @return
	 */
	public MCCommonAlertDialog setMessage(String message) {
		if(this.type ==MB_LOADING){
			if (this.loading_message != null) {
				this.loading_message.setText(message);
			}
		}else{
			if (this.dialog_message != null) {
				this.dialog_message.setText(message);
			}
		}
		return this;
	}

}
