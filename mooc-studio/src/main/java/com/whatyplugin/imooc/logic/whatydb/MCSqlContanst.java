package com.whatyplugin.imooc.logic.whatydb;


public class MCSqlContanst {

	public static final String TABLE_SFPDWONLOAD_NAME = "";
	public static final String CREATE_TABLE_SFPDWONLOAD = " CREATE TABLE IF NOT EXISTS " + TABLE_SFPDWONLOAD_NAME + " ( " + "_id"
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + 
			"courseId" + " VARCHAR , " + 
			"courseName" + " VARCHAR , "
		  + "courseImageUrl" + " VARCHAR , " + 
			"chapter_seq" + " INTEGER DEFAULT 0, " + 
			"section_seq" + " INTEGER , " + 
			"sectionId" + " VARCHAR , "
		  + "sectionName" + " VARCHAR , " + 
			"filename" + " VARCHAR , " + 
			"fileSize" + " LONG , " + 
			"downloadSize" + " LONG , " + 
			"downloadUrl" + " VARCHAR, " + 
			"type" + " VARCHAR," + 
			"resourceSection VARCHAR)";

}
