package com.whatyplugin.base.utils;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MCJsonUtils {
    public MCJsonUtils() {
        super();
    }

    public static String format(String jsonStr) {
        String result;
        try {
            result = MCJsonUtils.format("", MCJsonUtils.fromJson(jsonStr));
        }
        catch(Throwable e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    private static String format(String sepStr, HashMap arg10) {
        char splitStr = '\"';
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\n");
        String v2 = String.valueOf(sepStr) + "\t";
        int i = 0;
        Iterator iterator = arg10.entrySet().iterator();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            if(i > 0) {
                buffer.append(",\n");
            }

            buffer.append(v2).append(splitStr).append(((Map.Entry)item).getKey()).append("\":");
            Object value = ((Map.Entry)item).getValue();
            if((value instanceof HashMap)) {
                buffer.append(MCJsonUtils.format(v2, ((HashMap)value)));
            }
            else if((value instanceof ArrayList)) {
                buffer.append(MCJsonUtils.format(v2, ((ArrayList)value)));
            }
            else if((value instanceof String)) {
                buffer.append(splitStr).append(value).append(splitStr);
            }
            else {
                buffer.append(value);
            }

            ++i;
        }

        buffer.append('\n').append(sepStr).append('}');
        return buffer.toString();
    }

    private static String format(String sepStr, ArrayList arg8) {
        char splitStr = '\"';
        StringBuffer v2 = new StringBuffer();
        v2.append("[\n");
        String v1 = String.valueOf(sepStr) + "\t";
        int i = 0;
        Iterator iterator = arg8.iterator();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            if(i > 0) {
                v2.append(",\n");
            }

            v2.append(v1);
            if((item instanceof HashMap)) {
                v2.append(MCJsonUtils.format(v1, ((HashMap)item)));
            }
            else if((item instanceof ArrayList)) {
                v2.append(MCJsonUtils.format(v1, ((ArrayList)item)));
            }
            else if((item instanceof String)) {
                v2.append(splitStr).append(item).append(splitStr);
            }
            else {
                v2.append(item);
            }

            ++i;
        }

        v2.append('\n').append(sepStr).append(']');
        return v2.toString();
    }

    public static String fromHashMap(HashMap arg2) {
        String result;
        try {
            result = MCJsonUtils.getJSONObject(arg2).toString();
        }
        catch(Throwable e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    public static HashMap fromJson(String jsonStr) {
        HashMap v2;
        try {
            if((jsonStr.startsWith("[")) && (jsonStr.endsWith("]"))) {
                jsonStr = "{\"fakelist\":" + jsonStr + "}";
            }

            v2 = MCJsonUtils.fromJson(new JSONObject(jsonStr));
        }
        catch(Throwable v1) {
            v1.printStackTrace();
            v2 = new HashMap();
        }

        return v2;
    }

    private static ArrayList fromJson(JSONArray array) throws JSONException {
        ArrayList v1 = new ArrayList();
        int i = 0;
        int v2 = array.length();
        while(i < v2) {
            Object v3 = array.opt(i);
            if((v3 instanceof JSONObject)) {
                HashMap v3_1 = MCJsonUtils.fromJson(((JSONObject)v3));
            }
            else if((v3 instanceof JSONArray)) {
                ArrayList v3_2 = MCJsonUtils.fromJson(((JSONArray)v3));
            }

            v1.add(v3);
            ++i;
        }

        return v1;
    }

    private static HashMap fromJson(JSONObject json) throws JSONException {
        HashMap hashMap = new HashMap();
        Iterator iterator = json.keys();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            Object value = json.opt(((String)item));
            if(JSONObject.NULL.equals(value)) {
                value = null;
            }

            if(value == null) {
                continue;
            }

            if((value instanceof JSONObject)) {
                HashMap v3_1 = MCJsonUtils.fromJson(((JSONObject)value));
            }
            else if((value instanceof JSONArray)) {
                ArrayList v3_2 = MCJsonUtils.fromJson(((JSONArray)value));
            }

            hashMap.put(item, value);
        }

        return hashMap;
    }

    private static JSONArray getJSONArray(ArrayList arg4) throws JSONException {
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        Iterator iterator = arg4.iterator();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            if((item instanceof HashMap)) {
                jsonObject = MCJsonUtils.getJSONObject(((HashMap)item));
            }
            else if((item instanceof ArrayList)) {
                JSONArray v1_2 = MCJsonUtils.getJSONArray(((ArrayList)item));
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    private static JSONObject getJSONObject(HashMap arg5) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject jsonObject = new JSONObject();
        Iterator iterator = arg5.entrySet().iterator();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            Object value = ((Map.Entry)item).getValue();
            if((value instanceof HashMap)) {
                JSONObject v2_1 = MCJsonUtils.getJSONObject(((HashMap)value));
            }
            else if((value instanceof ArrayList)) {
                jsonArray = MCJsonUtils.getJSONArray(((ArrayList)value));
            }

            jsonObject.put((String) ((Map.Entry)item).getKey(), jsonArray);
        }

        return jsonObject;
    }
}

