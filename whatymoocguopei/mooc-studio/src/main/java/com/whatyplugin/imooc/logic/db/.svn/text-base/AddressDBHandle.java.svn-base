package com.whatyplugin.imooc.logic.db;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.whatyplugin.imooc.logic.db.DBCommon.AddressColumns;
import com.whatyplugin.imooc.logic.model.MCAddressModel;

public class AddressDBHandle extends DBHandle {
    public static String AREANO;
    public static String ID;
    public static String NAME;
    public static String OD;
    public static String PID;
    public static String PINYIN;
    public static String SUBNAME;
    public static String TYPE;
    public static String _ID;

    static {
        AddressDBHandle._ID = "_id";
        AddressDBHandle.ID = "id";
        AddressDBHandle.NAME = "name";
        AddressDBHandle.SUBNAME = "subname";
        AddressDBHandle.PID = "pid";
        AddressDBHandle.TYPE = "type";
        AddressDBHandle.PINYIN = "pinyin";
        AddressDBHandle.AREANO = "areano";
        AddressDBHandle.OD = "od";
    }

    public AddressDBHandle(Context context) {
        super(context);
    }

    public void deleteAddress(Context context) {
        context.getContentResolver().delete(AddressColumns.CONTENT_URI_ADDRESS, null, null);
    }

    public List<MCAddressModel> getAddress(String type, Integer pid) {
        Cursor cursor = null;
        List<MCAddressModel> list = new ArrayList<MCAddressModel>();
        try {
            cursor = this.mContext.getContentResolver().query(AddressColumns.CONTENT_URI_ADDRESS, AddressColumns.projects, "type=? and pid=?", new String[]{type, pid.toString()}, null);
            while(cursor.moveToNext()) {
                MCAddressModel model = new MCAddressModel();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                model.setSubname(cursor.getString(cursor.getColumnIndexOrThrow("subname")));
                model.setPid(cursor.getInt(cursor.getColumnIndexOrThrow("pid")));
                model.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                model.setPinyin(cursor.getString(cursor.getColumnIndexOrThrow("pinyin")));
                model.setAreano(cursor.getInt(cursor.getColumnIndexOrThrow("areano")));
                model.setOd(cursor.getInt(cursor.getColumnIndexOrThrow("od")));
                list.add(model);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	if(cursor!=null){
        		cursor.close();
        	}
        }
        return list;
    }

    public MCAddressModel getAddressName(String type, String name) {
        Cursor cursor = null;
        MCAddressModel addressModel = null;
        try {
            cursor = this.mContext.getContentResolver().query(AddressColumns.CONTENT_URI_ADDRESS, AddressColumns.projects, "type=? and name=?", new String[]{type, name}, null);
            cursor.moveToFirst();
            if(cursor != null && cursor.getCount() > 0) {
                addressModel = new MCAddressModel();
                addressModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                addressModel.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                addressModel.setSubname(cursor.getString(cursor.getColumnIndexOrThrow("subname")));
                addressModel.setPid(cursor.getInt(cursor.getColumnIndexOrThrow("pid")));
                addressModel.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                addressModel.setPinyin(cursor.getString(cursor.getColumnIndexOrThrow("pinyin")));
                addressModel.setAreano(cursor.getInt(cursor.getColumnIndexOrThrow("areano")));
                addressModel.setOd(cursor.getInt(cursor.getColumnIndexOrThrow("od")));
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	if(cursor!=null){
        		cursor.close();
        	}
        }
        return addressModel;
    }

    public MCAddressModel getAddressSubName(String type, String subname) {
        MCAddressModel addressModel = null;
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(AddressColumns.CONTENT_URI_ADDRESS, AddressColumns.projects, "type=? and subname=?", new String[]{type, subname}, null);
            cursor.moveToFirst();
            if(cursor != null && cursor.getCount() > 0) {
                addressModel = new MCAddressModel();
                addressModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                addressModel.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                addressModel.setSubname(cursor.getString(cursor.getColumnIndexOrThrow("subname")));
                addressModel.setPid(cursor.getInt(cursor.getColumnIndexOrThrow("pid")));
                addressModel.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                addressModel.setPinyin(cursor.getString(cursor.getColumnIndexOrThrow("pinyin")));
                addressModel.setAreano(cursor.getInt(cursor.getColumnIndexOrThrow("areano")));
                addressModel.setOd(cursor.getInt(cursor.getColumnIndexOrThrow("od")));
            }

        }
        catch(Exception e) {
        	e.printStackTrace();
        }finally{
        	if(cursor!=null){
        		cursor.close();
        	}
        }
        return addressModel;
    }

    public List<MCAddressModel> getMsgAddress() {
        Cursor cursor = null;
        List<MCAddressModel> list = new ArrayList<MCAddressModel>();
        try {
            cursor = this.mContext.getContentResolver().query(AddressColumns.CONTENT_URI_ADDRESS, AddressColumns.projects, null, null, null);
            while(cursor.moveToNext()) {
                MCAddressModel model = new MCAddressModel();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                model.setSubname(cursor.getString(cursor.getColumnIndexOrThrow("subname")));
                model.setPid(cursor.getInt(cursor.getColumnIndexOrThrow("pid")));
                model.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
                model.setPinyin(cursor.getString(cursor.getColumnIndexOrThrow("pinyin")));
                model.setAreano(cursor.getInt(cursor.getColumnIndexOrThrow("areano")));
                model.setOd(cursor.getInt(cursor.getColumnIndexOrThrow("od")));
                list.add(model);
            }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally{
        	if(cursor!=null){
        		cursor.close();
        	}
        }
        return list;
    }

    public List<MCAddressModel> isAddressData(Context context) {
        Cursor cursor = context.getContentResolver().query(AddressColumns.CONTENT_URI_ADDRESS, null, null, null, null);
        List<MCAddressModel> list = new ArrayList<MCAddressModel>();
        if(cursor.moveToFirst()) {
            for(int i = 0; i < cursor.getCount(); ++i) {
                MCAddressModel model = new MCAddressModel();
                cursor.move(i);
                model.setId(cursor.getInt(cursor.getColumnIndex("id")));
                model.setName(cursor.getString(cursor.getColumnIndex("name")));
                model.setSubname(cursor.getString(cursor.getColumnIndex("subname")));
                model.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                model.setType(cursor.getString(cursor.getColumnIndex("type")));
                model.setPinyin(cursor.getString(cursor.getColumnIndex("pinyin")));
                model.setAreano(cursor.getInt(cursor.getColumnIndex("areano")));
                model.setOd(cursor.getInt(cursor.getColumnIndex("od")));
                list.add(model);
            }
        }

        return list;
    }

    public void saveCity(MCAddressModel address) {
        if(address != null) {
            ContentValues content = new ContentValues();
            content.put("id", Integer.valueOf(address.getId()));
            content.put("name", address.getName());
            content.put("subname", address.getSubname());
            content.put("pid", Integer.valueOf(address.getPid()));
            content.put("type", "city");
            content.put("pinyin", address.getPinyin());
            content.put("areano", Integer.valueOf(address.getAreano()));
            content.put("od", Integer.valueOf(address.getOd()));
            this.insert(AddressColumns.CONTENT_URI_ADDRESS, content);
        }
    }

    public void saveCountry(MCAddressModel address) {
        if(address != null) {
            ContentValues content = new ContentValues();
            content.put("id", Integer.valueOf(address.getId()));
            content.put("name", address.getName());
            content.put("subname", address.getSubname());
            content.put("pid", Integer.valueOf(address.getPid()));
            content.put("type", "country");
            content.put("pinyin", address.getPinyin());
            content.put("areano", Integer.valueOf(address.getAreano()));
            content.put("od", Integer.valueOf(address.getOd()));
            this.insert(AddressColumns.CONTENT_URI_ADDRESS, content);
        }
    }

    public void saveDistrict(MCAddressModel address) {
        if(address != null) {
            ContentValues content = new ContentValues();
            content.put("id", Integer.valueOf(address.getId()));
            content.put("name", address.getName());
            content.put("subname", address.getSubname());
            content.put("pid", Integer.valueOf(address.getPid()));
            content.put("type", "district");
            content.put("pinyin", address.getPinyin());
            content.put("areano", Integer.valueOf(address.getAreano()));
            content.put("od", Integer.valueOf(address.getOd()));
            this.insert(AddressColumns.CONTENT_URI_ADDRESS, content);
        }
    }

    public void saveMultiAddress(List list) {
        ContentValues[] result = new ContentValues[list.size()];
        for(int i = 0; i < list.size(); ++i) {
            ContentValues content = new ContentValues();
            MCAddressModel model = (MCAddressModel) list.get(i);
            content.put("id", Integer.valueOf(model.getId()));
            content.put("name", model.getName());
            content.put("subname", model.getSubname());
            content.put("pid", Integer.valueOf(model.getPid()));
            content.put("type", "country");
            content.put("pinyin", model.getPinyin());
            content.put("areano", Integer.valueOf(model.getAreano()));
            content.put("od", Integer.valueOf(model.getOd()));
            result[i] = content;
        }

        this.multiInsert(AddressColumns.CONTENT_URI_ADDRESS, result);
    }

    public void saveMultiCity(List list) {
        ContentValues[] result = new ContentValues[list.size()];
        for(int i = 0; i < list.size(); ++i) {
            ContentValues content = new ContentValues();
            MCAddressModel model = (MCAddressModel) list.get(i);
            content.put("id", Integer.valueOf(model.getId()));
            content.put("name", model.getName());
            content.put("subname", model.getSubname());
            content.put("pid", Integer.valueOf(model.getPid()));
            content.put("type", "city");
            content.put("pinyin", model.getPinyin());
            content.put("areano", Integer.valueOf(model.getAreano()));
            content.put("od", Integer.valueOf(model.getOd()));
            result[i] = content;
        }

        this.multiInsert(AddressColumns.CONTENT_URI_ADDRESS, result);
    }

    public void saveMultiDistrict(List list) {
        ContentValues[] result = new ContentValues[list.size()];
        for(int i = 0; i < list.size(); ++i) {
            ContentValues content = new ContentValues();
            MCAddressModel model = (MCAddressModel) list.get(i);
            content.put("id", Integer.valueOf(model.getId()));
            content.put("name", model.getName());
            content.put("subname", model.getSubname());
            content.put("pid", Integer.valueOf(model.getPid()));
            content.put("type", "district");
            content.put("pinyin", model.getPinyin());
            content.put("areano", Integer.valueOf(model.getAreano()));
            content.put("od", Integer.valueOf(model.getOd()));
            result[i] = content;
        }

        this.multiInsert(AddressColumns.CONTENT_URI_ADDRESS, result);
    }

    public void saveMultiProvince(List list) {
        ContentValues[] v1 = new ContentValues[list.size()];
        for( int i = 0; i < list.size(); ++i) {
            ContentValues content = new ContentValues();
            MCAddressModel model = (MCAddressModel) list.get(i);
            content.put("id", Integer.valueOf(model.getId()));
            content.put("name", model.getName());
            content.put("subname", model.getSubname());
            content.put("pid", Integer.valueOf(model.getPid()));
            content.put("type", "province");
            content.put("pinyin", model.getPinyin());
            content.put("areano", Integer.valueOf(model.getAreano()));
            content.put("od", Integer.valueOf(model.getOd()));
            v1[i] = content;
        }

        this.multiInsert(AddressColumns.CONTENT_URI_ADDRESS, v1);
    }

    public void saveProvince(MCAddressModel address) {
        if(address != null) {
            ContentValues content = new ContentValues();
            content.put("id", Integer.valueOf(address.getId()));
            content.put("name", address.getName());
            content.put("subname", address.getSubname());
            content.put("pid", Integer.valueOf(address.getPid()));
            content.put("type", "province");
            content.put("pinyin", address.getPinyin());
            content.put("areano", Integer.valueOf(address.getAreano()));
            content.put("od", Integer.valueOf(address.getOd()));
            this.insert(AddressColumns.CONTENT_URI_ADDRESS, content);
        }
    }
}

