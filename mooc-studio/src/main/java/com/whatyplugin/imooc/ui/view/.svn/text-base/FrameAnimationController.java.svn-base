package com.whatyplugin.imooc.ui.view;



import android.os.Handler;
import android.os.Message;

public class FrameAnimationController {
	public static class AnimationHandler extends Handler {
        AnimationHandler(AnimationHandler arg1) {
            super();
        }

        private AnimationHandler() {
            super();
        }

        public void handleMessage(Message m) {
            switch(m.what) {
                case 1000: {
                    if(m.obj != null) {
                        ((Runnable) m.obj).run();
                    }

                    break;
                }
            }
        }
    }

    public static final int ANIMATION_FRAME_DURATION = 16;
    private static final int MSG_ANIMATE = 1000;
    private static final Handler mHandler= new AnimationHandler(null);

    private FrameAnimationController() {
        super();
        throw new UnsupportedOperationException();
    }

    public static void requestAnimationFrame(Runnable runnable) {
        Message msg = new Message();
        msg.what = 1000;
        msg.obj = runnable;
        FrameAnimationController.mHandler.sendMessageDelayed(msg, 16);
    }

    public static void requestFrameDelay(Runnable runnable, long delay) {
        Message msg = new Message();
        msg.what = 1000;
        msg.obj = runnable;
        FrameAnimationController.mHandler.sendMessageDelayed(msg, delay);
    }
}

