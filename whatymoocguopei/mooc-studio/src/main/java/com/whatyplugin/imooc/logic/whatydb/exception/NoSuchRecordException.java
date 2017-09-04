package com.whatyplugin.imooc.logic.whatydb.exception;

public class NoSuchRecordException extends Exception {
	
	private static final long serialVersionUID = 5878096867966274631L;

	public NoSuchRecordException() {
		super("no such record in this table");
	}
	
}
