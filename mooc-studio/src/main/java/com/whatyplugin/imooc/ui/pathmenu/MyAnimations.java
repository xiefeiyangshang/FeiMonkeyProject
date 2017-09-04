package com.whatyplugin.imooc.ui.pathmenu;

import android.R.anim;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class MyAnimations {
	
	protected static final String TAG = "MyAnimations";
		// �������䲻ͬ�ķֱ���
		private static int xOffset = 15;
		private static int yOffset = -13;

		public static void initOffset(Context context) {
			xOffset = (int) (10.667 * context.getResources().getDisplayMetrics().density);
			yOffset = -(int) (8.667 * context.getResources().getDisplayMetrics().density);
		}
		
		// ͼ��Ķ���(�붯��)
		public static void startAnimationsIn(ViewGroup viewgroup, int durationMillis) {
			for(int i = 0;i<viewgroup.getChildCount();i++)
			{
				View inoutimagebutton = viewgroup.getChildAt(i);
				viewgroup.setVisibility(View.VISIBLE);
				inoutimagebutton.setVisibility(0);
				inoutimagebutton.setClickable(true);
				inoutimagebutton.setFocusable(true);
				MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton.getLayoutParams();
				Animation animation = new TranslateAnimation(mlp.rightMargin - xOffset, 0F, yOffset + mlp.bottomMargin, 0F);
				animation.setFillAfter(true);
				animation.setDuration(durationMillis);
				animation.setStartOffset((i * 100) / (-1 + viewgroup.getChildCount()));// ��һ��������ƫ��ʱ��
				animation.setInterpolator(new OvershootInterpolator(2F));// ������Ч�� �����ٻ�����Ч��
				inoutimagebutton.startAnimation(animation);
			}
		}
		
		// �ӺŵĶ���
		public static Animation getRotateAnimation(float fromDegrees, float toDegrees, int durationMillis) {
			RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotate.setDuration(durationMillis);
			rotate.setFillAfter(true);
			return rotate;
		}
		
		// ͼ��Ķ���(������)
		public static void startAnimationsOut(final ViewGroup viewgroup, int durationMillis) {
			for (int i = 0; i < viewgroup.getChildCount(); i++) {
				View inoutimagebutton = viewgroup.getChildAt(i);
				MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton.getLayoutParams();
				Animation animation = new TranslateAnimation(0F, mlp.rightMargin - xOffset, 0F, yOffset + mlp.bottomMargin);

				animation.setFillAfter(true);
				animation.setDuration(durationMillis);
				animation.setStartOffset(((viewgroup.getChildCount() - i) * 100) / (-1 + viewgroup.getChildCount()));// ��һ��������ƫ��ʱ��
				
				inoutimagebutton.startAnimation(animation);
				inoutimagebutton.setClickable(false);
				animation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						Log.i(TAG, "onAnimationStart");
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
						Log.i(TAG, "onAnimationRepeat");
					}
					@Override
					public void onAnimationEnd(Animation animation) {
						Log.i(TAG, "onAnimationEnd");
						if(viewgroup.getVisibility() == View.VISIBLE)
							viewgroup.setVisibility(View.GONE);
					}
				});
			}
		}
		// icon��С��ʧ�Ķ���
		public static Animation getMiniAnimation(int durationMillis) {
			Animation miniAnimation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			miniAnimation.setDuration(durationMillis);
			miniAnimation.setFillAfter(true);
			return miniAnimation;
		}

		// icon�Ŵ󽥱���ʧ�Ķ���
		public static Animation getMaxAnimation(int durationMillis) {
			AnimationSet animationset = new AnimationSet(true);

			Animation maxAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			Animation alphaAnimation = new AlphaAnimation(1, 0);

			animationset.addAnimation(maxAnimation);
			animationset.addAnimation(alphaAnimation);

			animationset.setDuration(durationMillis);
			animationset.setFillAfter(true);
			return animationset;
		}
}
