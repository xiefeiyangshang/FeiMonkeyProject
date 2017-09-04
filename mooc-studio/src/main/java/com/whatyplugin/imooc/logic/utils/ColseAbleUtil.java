package com.whatyplugin.imooc.logic.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by liqingju on 2016/2/26.
 */
public class ColseAbleUtil  {

    public static  void colseable(Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
