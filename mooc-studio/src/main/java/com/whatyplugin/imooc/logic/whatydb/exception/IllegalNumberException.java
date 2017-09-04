package com.whatyplugin.imooc.logic.whatydb.exception;

public class IllegalNumberException extends Exception {

	private static final long serialVersionUID = -292832662032911090L;

	public IllegalNumberException() {
		super("the transfer money must great than zero");
	}

}
