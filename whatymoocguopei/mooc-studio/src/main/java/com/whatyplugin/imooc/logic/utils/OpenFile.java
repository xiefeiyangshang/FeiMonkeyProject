package com.whatyplugin.imooc.logic.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.uikit.toast.MCToast;

public class OpenFile {
	// android获取一个用于打开HTML文件的intent
	public static Intent getHtmlFileIntent(File file) {
		Uri uri = Uri.parse(file.toString()).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(file.toString()).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	 public static Intent getImageFileCoryIntent(Intent intent ,int outPutX,int outPutY,String crop){
		 Uri uri= intent.getData();
		 Intent intentCory = new Intent("com.android.camera.action.CROP");
		 intentCory.setDataAndType(uri, "image/*");
		 intentCory.putExtra("crop", crop);
		 intentCory.putExtra("aspectX", 1);
		 intentCory.putExtra("aspectY", 1);
		 intentCory.putExtra("outPutY", outPutY);
		 intentCory.putExtra("outPutX", outPutX);
		 intent.putExtra("return-data", true);
		 return intentCory;
		 
		 
	 }
	
	
	// android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	// android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "text/plain");
		return intent;
	}

	// android获取一个用于打开音频文件的intent
	public static Intent getAudioFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// android获取一个用于打开视频文件的intent
	public static Intent getVideoFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// android获取一个用于打开PPT文件的intent
	public static Intent getPPTFileIntent(File file) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// android获取一个用于打开apk文件的intent
	public static Intent getApkFileIntent(File file) {
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.fromFile(file),
//				"application/vnd.android.package-archive");
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/*");
		return intent;
	}

	public static boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
	
	public static void openFile(File currentPath, Context context){
		if(currentPath!=null&&currentPath.isFile())
        {
            String fileName = currentPath.toString().toLowerCase();
            Intent intent;
            try {
				if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingImage))){
				    intent = getImageFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingWebText))){
				    intent = getHtmlFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingPackage))){
				    intent = getApkFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingAudio))){
				    intent = getAudioFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingVideo))){
				    intent = getVideoFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingText))){
				    intent = getTextFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingPdf))){
				    intent = getPdfFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingWord))){
				    intent = getWordFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingExcel))){
				    intent = getExcelFileIntent(currentPath);
				    context.startActivity(intent);
				}else if(checkEndsWithInStringArray(fileName, context.getResources().
				        getStringArray(R.array.fileEndingPPT))){
				    intent = getPPTFileIntent(currentPath);
				    context.startActivity(intent);
				}else{
					MCToast.show(context, "无法打开，请安装相应的软件！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				MCToast.show(context, "打开文件失败，请安装相应的软件！");
			}
        }else
        {
        	MCToast.show(context, "对不起，这不是文件！");
        }
	}
}
