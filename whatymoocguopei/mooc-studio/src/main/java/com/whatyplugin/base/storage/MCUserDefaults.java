package com.whatyplugin.base.storage;



import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class MCUserDefaults {
    private static String DEFAULTSNAME;
    private static MCUserDefaults mMCUserDefaults;
    private static SharedPreferences sp;

    static {
        MCUserDefaults.DEFAULTSNAME = "imooc";
        MCUserDefaults.mMCUserDefaults = null;
        MCUserDefaults.sp = null;
    }

    private MCUserDefaults(Context context) {
        this(context, MCUserDefaults.DEFAULTSNAME);
    }

    private MCUserDefaults(Context context, String name) {
        super();
        MCUserDefaults.sp = context.getSharedPreferences(name, 0);
    }

    public static void clearDate(Context context) {
        try {
            MCUserDefaults.sp.edit().clear().commit();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean containsKey(String key) {
        return MCUserDefaults.sp.contains(key);
    }

    public boolean getBoolean(String key) {
        boolean result = false;
        try {
            result = MCUserDefaults.sp.getBoolean(key, false);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean getBoolean(String key, boolean value) {
        boolean result;
        try {
            result = MCUserDefaults.sp.getBoolean(key, value);
        }
        catch(Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    public int getCount() {
        Map all = MCUserDefaults.sp.getAll();
        int count = all != null ? all.size() : 0;
        return count;
    }

    public float getFloat(String key) {
        float result = 0f;
        try {
            result = MCUserDefaults.sp.getFloat(key, 0f);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getInt(String key) {
        int result =0 ;
        try {
            result = MCUserDefaults.sp.getInt(key, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public float getLong(String key) {
        float result = 0f;
        try {
            result = ((float)MCUserDefaults.sp.getLong(key, 0));
        }
        catch(Exception e) {
            e.printStackTrace();
            result = 0f;
        }

        return result;
    }

    public String getString(String key) {
        String result;
        try {
            result = MCUserDefaults.sp.getString(key, "");
        }
        catch(Exception e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    public String getString(String key, String value) {
        String result;
        try {
            result = MCUserDefaults.sp.getString(key, value);
        }
        catch(Exception e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    public static MCUserDefaults getUserDefaults(Context context, String name) {
        return new MCUserDefaults(context, name);
    }

    public void putBoolean(String key, boolean value) {
        try {
            MCUserDefaults.sp.edit().putBoolean(key, value).commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putFloat(String key, float value) {
        try {
            MCUserDefaults.sp.edit().putFloat(key, value).commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putInt(String key, int value) {
        try {
            MCUserDefaults.sp.edit().putInt(key, value).commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putLong(String key, long value) {
        try {
            MCUserDefaults.sp.edit().putLong(key, value).commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putString(String key, String value) {
        try {
            MCUserDefaults.sp.edit().putString(key, value).commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void removeValue(String key) {
        MCUserDefaults.sp.edit().remove(key).commit();
    }

    public static MCUserDefaults standardUserDefaults(Context context) {
        if(MCUserDefaults.mMCUserDefaults == null) {
            MCUserDefaults.mMCUserDefaults = new MCUserDefaults(context);
        }

        return MCUserDefaults.mMCUserDefaults;
    }
}

