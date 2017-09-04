package com.whatyplugin.base.utils;



import java.util.Date;

public class MCDataUtil {
    public MCDataUtil() {
        super();
    }

    public static boolean booleanForObject(Object obj) {
        boolean result = false;
        if(obj != null) {
            try {
                result = Boolean.valueOf(obj.toString()).booleanValue();
            }
            catch(Exception e) {
            }
        }

        return result;
    }

    public static Date dateForObject(Object obj) {
        Date result;
        try {
            result = new Date();
        }
        catch(Exception e) {
            result = new Date();
        }

        return result;
    }

    public static double doubleForObject(Object obj) {
        double result = 0;
        if(obj != null) {
            try {
                result = Double.valueOf(obj.toString()).doubleValue();
            }
            catch(Exception e) {
            }
        }

        return result;
    }

    public static float floatForObject(Object obj) {
        float result = 0f;
        if(obj != null) {
            try {
                result = Float.valueOf(obj.toString()).floatValue();
            }
            catch(Exception e) {
            }
        }

        return result;
    }

    public static int intForObject(Object obj) {
        int result = 0;
        if(obj != null) {
            try {
                result = Integer.valueOf(obj.toString()).intValue();
            }
            catch(Exception e) {
            }
        }

        return result;
    }

    public static long longForObject(Object obj) {
        long result = 0;
        if(obj != null) {
            try {
                result = Long.valueOf(obj.toString()).longValue();
            }
            catch(Exception e) {
            }
        }

        return result;
    }

    public static String stringForObject(Object obj) {
        String result;
        if(obj != null) {
        	 result = String.valueOf(obj.toString());
        }

        try {
            result = "";
            return result;
        }
        catch(Exception e) {
            result = "";
        }

        return result;
    }
}

