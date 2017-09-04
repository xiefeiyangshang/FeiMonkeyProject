package com.whatyplugin.imooc.logic.whatydb.exception;

public class NoEnoughMoneyException extends Exception {

	private static final long serialVersionUID = -8903669370084041626L;

	public NoEnoughMoneyException() {
		super("user have no enough money to transfer");
	}

}
