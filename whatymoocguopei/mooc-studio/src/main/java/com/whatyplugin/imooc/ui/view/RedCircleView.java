package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RedCircleView extends View {

	public RedCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public RedCircleView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public RedCircleView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(0XFFff3e3e);
		paint.setStrokeWidth(0);
		canvas.drawCircle(12, 12, 12, paint);

	}
}
