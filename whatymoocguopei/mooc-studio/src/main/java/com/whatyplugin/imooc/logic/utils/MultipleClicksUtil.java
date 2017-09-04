package com.whatyplugin.imooc.logic.utils;

public class MultipleClicksUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        boolean result;
        long time = System.currentTimeMillis();
        if(time - MultipleClicksUtil.lastClickTime < 800) {
            result = true;
        }
        else {
            MultipleClicksUtil.lastClickTime = time;
            result = false;
        }

        return result;
    }
}
