package com.whatyplugin.imooc.logic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class RegexValidateUtil {
	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		 boolean flag = Pattern.compile("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z].", 2).matcher(((CharSequence)email)).matches() ? true : false;
		 return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		if (TextUtils.isEmpty(mobileNumber)||mobileNumber.length()!=11)
			flag = false;
		else {
			try {
				Pattern regex = Pattern
						.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
				Matcher matcher = regex.matcher(mobileNumber);
				flag = matcher.matches();
			} catch (Exception e) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 验证QQ号码
	 * 
	 * @param QQ
	 * @return
	 */
	public static boolean checkQQ(String QQ) {
		String regex = "^[1-9][0-9]{4,}$";
		return check(QQ, regex);
	}

	public static boolean check(String str, String regex) {
		boolean flag;
		if (TextUtils.isEmpty(str) || str.length()>13)
			flag = false;
		else {
			try {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(str);
				flag = matcher.matches();
			} catch (Exception e) {
				flag = false;
			}
		}
		return flag;
	}
	
	 /** 
     * 电话号码验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }  
}
