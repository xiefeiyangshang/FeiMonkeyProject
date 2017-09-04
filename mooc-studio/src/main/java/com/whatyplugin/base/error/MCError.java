package com.whatyplugin.base.error;



public class MCError {
    private int errorCode;
    private String errorDesc;

    public MCError() {
        super();
    }

    public static MCError descriptionWithCode(int errorCode) {
        return new MCError();
    }

    public static MCError errorWithCode(int errorCode) {
        return MCError.initWithCode(errorCode);
    }

    private static MCError initWithCode(int errorCode) {
        return new MCError();
    }
}

