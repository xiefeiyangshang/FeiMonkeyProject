package com.whatyplugin.imooc.logic.proxy;

import android.text.TextUtils;

import com.whatyplugin.imooc.logic.utils.Const;

public class MCResourceProxy {
    private static MCResourceProxy instance;
    private final static int DECODE_TYPE = 1;

    public static MCResourceProxy getInstance() {
        if (instance == null) {
            instance = new MCResourceProxy();
        }
        return instance;
    }

    public void lock(String path) {
        if (TextUtils.isEmpty(path) || !Const.DO_VC) {
            return;
        }

        if (DECODE_TYPE == 1) {
            MCResourceDecoderNative.encode(path);
        } else {
            try {
                MCResourceDecoderLocal decoder = new MCResourceDecoderLocal();
                decoder.init(path);
                decoder.encode();
                decoder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unlock(String path) {
        if (TextUtils.isEmpty(path) || !Const.DO_VC) {
            return;
        }
        if (DECODE_TYPE == 1) {
            MCResourceDecoderNative.decode(path);
        } else {
            try {
                MCResourceDecoderLocal decoder = new MCResourceDecoderLocal();
                decoder.init(path);
                decoder.decode();
                decoder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
