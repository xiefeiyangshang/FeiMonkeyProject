package com.whatyplugin.imooc.logic.whatydb.exception;

public class NoSuchAnnotationException extends Exception {

	private static final long serialVersionUID = 3596871460076725252L;

	public NoSuchAnnotationException() {
		super("please check your bean with the annotation of @ACCOUNT");
	}

}
