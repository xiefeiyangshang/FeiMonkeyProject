package com.whatyplugin.imooc.logic.model;


import com.whatyplugin.base.model.MCAdditionalData;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCExtraResult;
import com.whatyplugin.base.type.MCDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MCServiceResult extends MCCommonResult implements Serializable {
    private int errorcode;
    private MCExtraResult extraResult;
    private MCDate timestamp;
    private MCAdditionalData addtionalData;
    
    public MCServiceResult() {
        super();
    }

    public int getErrorcode() {
        return this.errorcode;
    }

    public MCExtraResult getExtraResult() {
        return this.extraResult;
    }

    public MCDate getTimestamp() {
        return this.timestamp;
    }

    
    public static MCServiceResult resultWithData(MCResultCode resultCode, String resultDesc) {
    	MCServiceResult result = new MCServiceResult();
        result.setResultCode(resultCode);
        result.setResultDesc(resultDesc);
        return result;
    }
    
    public boolean isExposedToUser() {
        boolean flag = this.getErrorcode() < 2000 || this.getErrorcode() > 4000 ? false : true;
        return flag;
    }

    public boolean isReNickName() {
        boolean flag = this.getErrorcode() == 4009 ? true : false;
        return flag;
    }

    public static MCServiceResult resultWithData(String responseObject) {
        MCServiceResult result = new MCServiceResult();
        try {
            JSONObject obj = new JSONObject(responseObject);
            int code = 1;
            
            if("{}".equals(responseObject)){
            	code = 0;
            }else{  
	            try {
	            	if("unknow".equals(obj.getString("errorCode"))){
	            		code = 0; //如果errorCode是unknow， 说明失败
	            	}else{
	            		code = obj.getInt("errorCode");
	            	}
				} catch (Exception e) {
					try {
						if(!obj.isNull("loginStatus"))
							code = obj.getInt("loginStatus");
						if(!obj.isNull("loginTip")){
							String loginTip=obj.getString("loginTip");
							if(loginTip!=null&&"本用户名有多个站点".equals(loginTip)){
								code=1;
							}
						}
					} catch (Exception e2) {
						code = 0;
					}
				}
            }
            result.setErrorcode(code);
           
            if(code == 1) {//暂时线上的errorcode的值还没有保持一致，暂时都按成功算
                result.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);//1是成功
            } else if(code == 1005) {
                result.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
            } else if(code == 0) {
                result.setResultCode(MCResultCode.MC_RESULT_CODE_FAILURE);//0是失败
            } else if(code == 1000){
            	result.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);//1是成功
            }

            try {
				result.setResultDesc(obj.getString("errorDesc"));
			} catch (Exception e) {
				try {
					if(!obj.isNull("errorMsg")){
						result.setResultDesc(obj.getString("errorMsg"));
					}else if(!obj.isNull("errorMessage")){
						result.setResultDesc(obj.getString("errorMessage"));
					}else if(!obj.isNull("loginTip")){
						result.setResultDesc(obj.getString("loginTip"));
					}else{
						result.setResultDesc("登陆失败");
					}
				} catch (Exception e2) {
					result.setResultDesc("");
				}
			}
            try {
				result.setTimestamp(MCDate.dateWithMilliseconds(obj.getLong("timestamp")));
			} catch (Exception e) {
			}
            if(!obj.has("ext")) {
                return result;
            }

            JSONObject extJson = new JSONObject(obj.getString("ext"));
            if(!extJson.has("timestamp")) {
                return result;
            }

            long timestamp = 0L;
            try {
            	timestamp = extJson.getLong("timestamp");
			} catch (Exception e) {
				timestamp = System.currentTimeMillis()-2000;
			}
            MCExtraResult eResult = new MCExtraResult();
            eResult.setTimestamp(timestamp);
            result.setExtraResult(eResult);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

   

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public void setExtraResult(MCExtraResult extraResult) {
        this.extraResult = extraResult;
    }

    public MCAdditionalData getAddtionalData() {
		return addtionalData;
	}

	public void setAddtionalData(MCAdditionalData addtionalData) {
		this.addtionalData = addtionalData;
	}

	public void setTimestamp(MCDate timestamp) {
        this.timestamp = timestamp;
    }

    public void show() {
    }

    public void show(String resultDesc) {
    }
}

