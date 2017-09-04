package com.whatyplugin.base.model;


import com.whatyplugin.base.error.MCError;

public class MCCommonResult {
    public enum MCResultCode {
        MC_RESULT_CODE_EMPTY("MC_RESULT_CODE_EMPTY", 0),
        MC_RESULT_CODE_FAILURE("MC_RESULT_CODE_FAILURE", 1),
        MC_RESULT_CODE_NETWORK_FAILURE("MC_RESULT_CODE_NETWORK_FAILURE", 2),
        MC_RESULT_CODE_SUCCESS("MC_RESULT_CODE_SUCCESS", 3);
        private String nCode;
        private int type;
        private  static MCResultCode[] ENUM;
        static {
            ENUM = new MCResultCode[]{MCResultCode.MC_RESULT_CODE_SUCCESS, MCResultCode.MC_RESULT_CODE_FAILURE, MCResultCode.MC_RESULT_CODE_EMPTY, MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE};
        }

        private MCResultCode(String arg1, int arg2) {
        	this.nCode = arg1;
        	this.type = arg2;
        }
    }

    private MCResultCode resultCode;
    private String resultData;
    private String resultDesc;
    private String resultShare;

    public MCCommonResult() {
        super();
    }

    public MCResultCode getResultCode() {
        return this.resultCode;
    }

    public String getResultData() {
        return this.resultData;
    }

    public String getResultDesc() {
        return this.resultDesc;
    }

    public static boolean isExposedToUser(MCResultCode resultCode) {
        return true;
    }

    public boolean isExposedToUser() {
        return true;
    }

    public static MCCommonResult resultWithData(MCResultCode resultCode, String resultDesc) {
        MCCommonResult commonResult = new MCCommonResult();
        commonResult.setResultCode(resultCode);
        commonResult.setResultDesc(resultDesc);
        return commonResult;
    }

    public static MCCommonResult resultWithData(MCResultCode resultCode, String resultDesc, String resultData) {
        MCCommonResult result = new MCCommonResult();
        result.setResultCode(resultCode);
        result.setResultDesc(resultDesc);
        result.setResultData(resultData);
        return result;
    }

    public static MCCommonResult resultWithData(String responseObject) {
        return new MCCommonResult();
    }

    public static MCCommonResult resultWithError(MCError error) {
        return new MCCommonResult();
    }

    public static MCCommonResult resultWithErrorCode(int errorcode) {
        return new MCCommonResult();
    }

    public void setResultCode(MCResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}

