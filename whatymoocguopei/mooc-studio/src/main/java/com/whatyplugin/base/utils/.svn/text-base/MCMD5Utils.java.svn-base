package com.whatyplugin.base.utils;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MCMD5Utils {
    private static String[] strDigits;

    static {
        MCMD5Utils.strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    }

    public MCMD5Utils() {
        super();
    }

    public static String GetMD5Code(String strObj) {
        String result = null;

        try {
            return MCMD5Utils.byteToString(MessageDigest.getInstance("MD5").digest(strObj.getBytes()));
        }
        catch(NoSuchAlgorithmException e) {
            result = strObj;
        }

       
        return result;
    }

    private static String byteToArrayString(byte bByte) {
        if(bByte < 0) {
        	bByte += 256;
        }

        return String.valueOf(MCMD5Utils.strDigits[bByte / 16]) + MCMD5Utils.strDigits[bByte % 16];
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer buffer = new StringBuffer();
        int i;
        for(i = 0; i < bByte.length; ++i) {
            buffer.append(MCMD5Utils.byteToArrayString(bByte[i]));
        }

        return buffer.toString();
    }
}

