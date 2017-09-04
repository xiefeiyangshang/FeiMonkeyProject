package com.whatyplugin.base.error;


public enum MCBaseErrorCode {
        ERROR_COMMON("ERROR_COMMON", 0, 11000),
        ERROR_BATABASE_OPEN("ERROR_BATABASE_OPEN", 1, 11001),
        ERROR_ANALYZE_DATA("ERROR_ANALYZE_DATA", 2, 11002);
    int _value;
    private MCBaseErrorCode(String arg1, int arg2, int value) {
        this._value = value;
    }

    public int value() {
        return this._value;
    }

}

