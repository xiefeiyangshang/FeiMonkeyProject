package com.whatyplugin.imooc.logic.db;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class MoocContentProvider extends ContentProvider {
	public static final int ADDRESS = 7;
    public static final int CACHE = 8;
    public static final int CHAPTER = 3;
    public static final int COURSE = 2;
    public static final int DOWNLOADINFO = 1;
    public static final int DURATION = 9;
    public static final int MSG = 5;
    public static final int OFFLINEREPORT = 10;
    public static final int SECTION = 4;
    public static final int USER = 6;
    public static String DOWNLOADINFO_TABLE_NAME;
    public static String HOMEWORK_TABLE_NAME;
    public static String DOWNLOADRESOURCE_TABLE_NAME;
	public static String COURSE_TABLE_NAME;
	public static String CHAPTER_TABLE_NAME;
	public static String SECTION_TABLE_NAME;
	public static String MSG_TABLE_NAME;
	public static String USER_TABLE_NAME;
	public static String ADDRESS_TABLE_NAME;
	public static String CACHE_TABLE_NAME;
	public static String MEDIADURATION_TABLE_NAME;
	public static String OFFLINEREPORT_TABLE_NAME;
	public DBOpenHelper mOpenHelper;
	public static UriMatcher sURLMatcher;

    static {
        MoocContentProvider.DOWNLOADINFO_TABLE_NAME = "downloadinfo";
        MoocContentProvider.HOMEWORK_TABLE_NAME = "homework_info";
        MoocContentProvider.DOWNLOADRESOURCE_TABLE_NAME = "resourceinfo";
        MoocContentProvider.COURSE_TABLE_NAME = "course";
        MoocContentProvider.CHAPTER_TABLE_NAME = "chapter";
        MoocContentProvider.SECTION_TABLE_NAME = "section";
        MoocContentProvider.MSG_TABLE_NAME = "message";
        MoocContentProvider.USER_TABLE_NAME = "user";
        MoocContentProvider.ADDRESS_TABLE_NAME = "address";
        MoocContentProvider.CACHE_TABLE_NAME = "cache";
        MoocContentProvider.MEDIADURATION_TABLE_NAME = "duration";
        MoocContentProvider.OFFLINEREPORT_TABLE_NAME = "offlinereport";
    }

	public static void initMatchUri() {
		MoocContentProvider.sURLMatcher = new UriMatcher(-1);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "downloadinfo", 1);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "course", 2);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "chapter", 3);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "section", 4);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "message", 5);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "user", 6);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "address", 7);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "cache", 8);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "duration", 9);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "offlinereport", 10);
        MoocContentProvider.sURLMatcher.addURI(MoocApplication.getMainPackageName(), "homework_info", 11);
	}

    public MoocContentProvider() {
        super();
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
    	long result=0;
        SQLiteDatabase dataBase = null;
        int type = MoocContentProvider.sURLMatcher.match(uri);
        try {
            dataBase = this.mOpenHelper.getWritableDatabase();
            dataBase.beginTransaction();
            for(int i = 0; i < values.length; ++i) {
                ContentValues content = values[i];
                if(content != null) {
                    switch(type) {
                        case 1: {
                        	result = dataBase.insert(MoocContentProvider.DOWNLOADINFO_TABLE_NAME, null, content);
                        	break;
                        }
                        case 2: {
                        	result = dataBase.insert(MoocContentProvider.COURSE_TABLE_NAME, null, content);
                        	break;
                        }
                        case 3: {
                        	result = dataBase.insert(MoocContentProvider.CHAPTER_TABLE_NAME, null, content);
                        	break;
                        }
                        case 4: {
                        	 result = dataBase.insert(MoocContentProvider.SECTION_TABLE_NAME, null, content);
                        	 break;
                        }
                        case 7: {
                        	result = dataBase.insert(MoocContentProvider.ADDRESS_TABLE_NAME, null, content);
                        	break;
                        }
                        default:
                        	System.out.println("Unknow URI");
                    }
       
                    this.getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri, new StringBuilder(String.valueOf(result)).toString()), null);
                }
            }

            dataBase.setTransactionSuccessful();
            dataBase.endTransaction();
        }catch (Exception e){
        	e.printStackTrace();
        }
        
        return (int) result;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = 0;
        int caseNum = MoocContentProvider.sURLMatcher.match(uri);
        try {
            SQLiteDatabase dataBase = this.mOpenHelper.getWritableDatabase();
            switch(caseNum) {
                case 1: {
                	result = dataBase.delete(MoocContentProvider.DOWNLOADINFO_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 2: {
                	result = dataBase.delete(MoocContentProvider.COURSE_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 3: {
                	result = dataBase.delete(MoocContentProvider.CHAPTER_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 4: {
                	result = dataBase.delete(MoocContentProvider.SECTION_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 5: {
                	result = dataBase.delete(MoocContentProvider.MSG_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 6: {
                	result = dataBase.delete(MoocContentProvider.USER_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 7: {
                	 result = dataBase.delete(MoocContentProvider.ADDRESS_TABLE_NAME, selection, selectionArgs);
                	 break;
                }
                case 8: {
                	result = dataBase.delete(MoocContentProvider.CACHE_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 9: {
                	 result = dataBase.delete(MoocContentProvider.MEDIADURATION_TABLE_NAME, selection, selectionArgs);
                	 break;
                }
                case 10: {
                	result = dataBase.delete(MoocContentProvider.OFFLINEREPORT_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                case 11: {
                	result = dataBase.delete(MoocContentProvider.HOMEWORK_TABLE_NAME, selection, selectionArgs);
                	break;
                }
                default:
                	System.out.println("Unknown URL " + uri);
            }

	        if(result > 0) {
	            this.getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri, new StringBuilder(String.valueOf(result)).toString()), null);
	        }
        }catch (Exception e){
        	e.printStackTrace();
        }

        return result;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        ContentObserver content = null;
        long result = 0;
        if(values != null) {
            try {
                SQLiteDatabase dataBase = this.mOpenHelper.getWritableDatabase();
                switch(MoocContentProvider.sURLMatcher.match(uri)) {
                    case 1: {
                    	 result = dataBase.insert(MoocContentProvider.DOWNLOADINFO_TABLE_NAME, null, values);
                         break;
                    }
                    case 2: {
                    	 result = dataBase.insert(MoocContentProvider.COURSE_TABLE_NAME, null, values);
                         break;
                    }
                    case 3: {
                    	 result = dataBase.insert(MoocContentProvider.CHAPTER_TABLE_NAME, null, values);
                         break;
                    }
                    case 4: {
                    	 result = dataBase.insert(MoocContentProvider.SECTION_TABLE_NAME, null, values);
                         break;
                    }
                    case 5: {
                    	result = dataBase.insert(MoocContentProvider.MSG_TABLE_NAME, null, values);
                        break;
                    }
                    case 6: {
                    	 result = dataBase.insert(MoocContentProvider.USER_TABLE_NAME, null, values);
                    	 break;
                    }
                    case 7: {
                    	result = dataBase.insert(MoocContentProvider.ADDRESS_TABLE_NAME, null, values);
                        break;
                    }
                    case 8: {
                    	 result = dataBase.insert(MoocContentProvider.CACHE_TABLE_NAME, null, values);
                         break;
                    }
                    case 9: {
                    	result = dataBase.insert(MoocContentProvider.MEDIADURATION_TABLE_NAME, null, values);
                        break;
                    }
                    case 10: {
                    	 result = dataBase.insert(MoocContentProvider.OFFLINEREPORT_TABLE_NAME, null, values);
                         break;
                    }
                    case 11: {
                    	result = dataBase.insert(MoocContentProvider.HOMEWORK_TABLE_NAME, null, values);
                    	break;
                    }
                    default:
                    	System.out.println("Unknown URL " + uri);
                }
                uri = Uri.withAppendedPath(uri, new StringBuilder(String.valueOf(result)).toString());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        this.getContext().getContentResolver().notifyChange(uri, content);
        return uri;
    }

    public boolean onCreate() {
        this.mOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int matchNum = MoocContentProvider.sURLMatcher.match(uri);
        Cursor crusor = null;
        try {
            SQLiteDatabase dataBase = this.mOpenHelper.getReadableDatabase();
            switch(matchNum) {
                case 1: {
                	 return dataBase.query(MoocContentProvider.DOWNLOADINFO_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 2: {
                	return dataBase.query(MoocContentProvider.COURSE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 3: {
                	 return dataBase.query(MoocContentProvider.CHAPTER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 4: {
                	return dataBase.query(MoocContentProvider.SECTION_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 5: {
                	return dataBase.query(MoocContentProvider.MSG_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 6: {
                	return dataBase.query(MoocContentProvider.USER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 7: {
                	 return dataBase.query(MoocContentProvider.ADDRESS_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 8: {
                	 return dataBase.query(MoocContentProvider.CACHE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 9: {
                	return dataBase.query(MoocContentProvider.MEDIADURATION_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 10: {
                	return dataBase.query(MoocContentProvider.OFFLINEREPORT_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                case 11: {
                	return dataBase.query(MoocContentProvider.HOMEWORK_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                default:
                	System.out.println("Unknown URL " + uri);
                	return null;
            }
       }
        catch(Exception e) {
            e.printStackTrace();
        }

        return crusor;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int result = 0;
        int matchNum = MoocContentProvider.sURLMatcher.match(uri);
        try {
            SQLiteDatabase dataBase = this.mOpenHelper.getWritableDatabase();
            switch(matchNum) {
                case 1: {
                	 result = dataBase.update(MoocContentProvider.DOWNLOADINFO_TABLE_NAME, values, selection, selectionArgs);
                     break;
                }
                case 2: {
                	 result = dataBase.update(MoocContentProvider.COURSE_TABLE_NAME, values, selection, selectionArgs);
                	 break;
                }
                case 3: {
                	 result = dataBase.update(MoocContentProvider.CHAPTER_TABLE_NAME, values, selection, selectionArgs);
                     break;
                }
                case 4: {
                	result = dataBase.update(MoocContentProvider.SECTION_TABLE_NAME, values, selection, selectionArgs);
                }
                case 5: {
                	 result = dataBase.update(MoocContentProvider.MSG_TABLE_NAME, values, selection, selectionArgs);
                     break;
                }
                case 6: {
                	  result = dataBase.update(MoocContentProvider.USER_TABLE_NAME, values, selection, selectionArgs);
                      break;
                }
                case 7: {
                	result = dataBase.update(MoocContentProvider.ADDRESS_TABLE_NAME, values, selection, selectionArgs);
                    break;
                }
                case 8: {
                	result = dataBase.update(MoocContentProvider.CACHE_TABLE_NAME, values, selection, selectionArgs);
                    break;
                }
                case 9: {
                	 result = dataBase.update(MoocContentProvider.MEDIADURATION_TABLE_NAME, values, selection, selectionArgs);
                     break;
                }
                case 10: {
                	 result = dataBase.update(MoocContentProvider.OFFLINEREPORT_TABLE_NAME, values, selection, selectionArgs);
                     break;
                }
                case 11: {
                	result = dataBase.update(MoocContentProvider.HOMEWORK_TABLE_NAME, values, selection, selectionArgs);
                	break;
                }
                default:
                	System.out.println("Unknown URL " + uri);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(matchNum != 1 && result > 0) {
            this.getContext().getContentResolver().notifyChange(Uri.withAppendedPath(uri, new StringBuilder(String.valueOf(result)).toString()), null);
        }

        return result;
    }
}

