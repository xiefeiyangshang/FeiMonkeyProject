package com.whatyplugin.imooc.logic.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.whatyplugin.base.log.MCLog;

public class SendMailUtil {
	
	public static class Email_Authenticator extends Authenticator{
		String userName = null;
	    String password = null;
	    public Email_Authenticator() {
	    }
	    public Email_Authenticator(String username, String password) {
	        this.userName = username;
	        this.password = password;
	    }
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(userName, password);
	    }
	}
	
	/**
	 * 发送邮件
	 * content 邮件内容
	 * addTo 接收人
	 * title 邮件标题
	 */
	public static void sendEmailInner(String content, String title) {
		try {
			MCLog.e("SendMailUtil", "程序已崩溃，发送日志邮件：" + title);
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.qq.com");
			properties.put("mail.smtp.port", "25");
			properties.put("mail.smtp.auth", "true");
			Authenticator authenticator = new Email_Authenticator("1583884738@qq.com", "xiaomage880525");
			javax.mail.Session sendMailSession = javax.mail.Session.getDefaultInstance(properties, authenticator);
			MimeMessage mailMessage = new MimeMessage(sendMailSession);
			mailMessage.setFrom(new InternetAddress("1583884738@qq.com"));

			Address[] tos = null;
			String[] receivers = Const.SENDMAIL;
			if (receivers != null && receivers.length > 0) {
				// 为每个邮件接收者创建一个地址
				tos = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					tos[i] = new InternetAddress(receivers[i]);
				}
			} else {
				return;
			}
			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			mailMessage.setSubject(title, "UTF-8");
			mailMessage.setSentDate(new Date());

			mailMessage.setContent(content, "text/plain");

			Transport.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
