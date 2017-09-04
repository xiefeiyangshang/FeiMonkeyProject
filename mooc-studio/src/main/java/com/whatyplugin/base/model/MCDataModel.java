package com.whatyplugin.base.model;

public abstract class MCDataModel {

	public MCDataModel() {
		super();
	}

	public boolean isValidData(Object data) {
		return true;
	}

	public abstract MCDataModel modelWithData(Object arg1);

	public abstract String getId();
}
