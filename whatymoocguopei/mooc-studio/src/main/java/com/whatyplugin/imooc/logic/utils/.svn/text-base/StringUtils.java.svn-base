/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whatyplugin.imooc.logic.utils;

import android.text.TextUtils;
import android.util.Log;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {
	public static String join(Object[] elements, CharSequence separator) {
		return join(Arrays.asList(elements), separator);
	}

	public static String join(Iterable<? extends Object> elements, CharSequence separator) {
		StringBuilder builder = new StringBuilder();

		if (elements != null) {
			Iterator<? extends Object> iter = elements.iterator();
			if (iter.hasNext()) {
				builder.append(String.valueOf(iter.next()));
				while (iter.hasNext()) {
					builder.append(separator).append(String.valueOf(iter.next()));
				}
			}
		}

		return builder.toString();
	}

	public static String fixLastSlash(String str) {
		String res = str == null ? "/" : str.trim() + "/";
		if (res.length() > 2 && res.charAt(res.length() - 2) == '/')
			res = res.substring(0, res.length() - 1);
		return res;
	}

	public static int convertToInt(String str) throws NumberFormatException {
		int s, e;
		for (s = 0; s < str.length(); s++)
			if (Character.isDigit(str.charAt(s)))
				break;
		for (e = str.length(); e > 0; e--)
			if (Character.isDigit(str.charAt(e - 1)))
				break;
		if (e > s) {
			try {
				return Integer.parseInt(str.substring(s, e));
			} catch (NumberFormatException ex) {
				Log.e("convertToInt", ex.toString());
				throw new NumberFormatException();
			}
		} else {
			throw new NumberFormatException();
		}
	}

	public static String generateTime(long time) {
		int totalSeconds = (int) (time / 1000);
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * 判断输入的字符串为全空格
	 * 
	 * ^\\s*$ 全空格的正则
	 * 
	 * @param input
	 *            输入字符串 eg:"    " return false
	 * @return boolean
	 */

	public static boolean isWhiteSpace(String input) {
		if (input == null || input.length() == 0) {
			return true;
		}
		return Pattern.compile("^\\s*$").matcher(input).find();
	}

	/**
	 * 去除前面域名的方法
	 * http://inside.kfkc.webtrn.cn:80/learnspace/incoming/inside/image
	 * /20150424/1429865372032-7.jpg
	 * 返回：learnspace/incoming/inside/image/20150424/1429865372032-7.jpg
	 * 
	 */

	public static String rmSiteUrl(String inputRealmName) {
		return inputRealmName.replaceAll("^http://[^/]*/", "");
	}

	/**
	 * 
	 * http://inside.kfkc.webtrn.cn:80/incoming/inside/image
	 * /20150424/1429865372032-7.jpg
	 * 返回：http://inside.kfkc.webtrn.cn:80/learnspace
	 * /learnspace/incoming/inside/image/20150424/1429865372032-7.jpg
	 * 
	 */

	public static String addNameSpase(String noNameSpaseUrl) {
		return Const.SITE_LOCAL_URL + Const.BASE_PATH + "/" + noNameSpaseUrl.replaceAll("^http://[^/]*/", "");

	}

	/**
	 * 去除前面域名的方法
	 * http://inside.kfkc.webtrn.cn:80/learnspace/incoming/inside/image
	 * /20150424/1429865372032-7.jpg learnspace
	 * 返回：incoming/inside/image/20150424/1429865372032-7.jpg
	 * 
	 */

	public static String rmSiteUrlAndPath(String inputRealmName) {
		return inputRealmName.replaceAll("^http://[^/]*" + Const.BASE_PATH + "/", "");
	}

	/**
	 * 去除空格，包括中文空格
	 * 
	 * @param 要处理的字符串
	 * @return
	 */
	public static String trimSpace(String str) {
		if (str == null) {
			return null;
		}
		String returnStr = str.trim();
		// 这里的双引号里是中文空格 　
		while (returnStr.startsWith("　")) {
			returnStr = returnStr.substring(1, returnStr.length());
		}
		while (returnStr.endsWith("　")) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	/**
	 * 去除前后的
	 * <p>
	 * </p>
	 * 从而webView显示的时候，减少两个空行
	 * 
	 * @param data
	 * @return
	 */

	public static String rmWebViewLabelP(String data) {

		return data.replaceFirst("^<p>", "").replaceFirst("</p>$", "");
	}

	public static void printRequestInfo(String requestUrl, List<BasicNameValuePair> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(requestUrl + "\n");
		List<String> filter = new ArrayList<String>();
		// filter.add("unTyxlLoginToken");
		filter.add("uid");

		StringBuffer item = new StringBuffer();
		for (BasicNameValuePair pair : params) {
			if (!filter.contains(pair.getName())) {
				sb.append(pair.getName() + "=" + pair.getValue() + "\n");
				// "Name=params.courseId",
				// "Value=402814a14a2981de014a2cb874aa1cf2", ENDITEM,
				item.append("    \"Name=" + pair.getName() + "\", \"Value=" + pair.getValue() + "\", ENDITEM, \n");
			}
		}

		sb.append("\n");
		System.out.println("请求信息：" + sb);
		String script = createScript(requestUrl, item.toString());
		saveInfo(Contants.BASE_PATH, script);

	}

	private static String createScript(String url, String item) {

		String info = "    lr_start_transaction(\"$action$\");\n" + "    web_submit_data(\"$action$\", \n" + "    \"Action=$url$\", \n"
				+ "    \"Method=POST\",\n" + "    \"RecContentType=text/html\", \n" + "    \"Mode=HTML\", \n" + "    ITEMDATA, \n" + "$item$"
				+ "    LAST);\n\n" + "    lr_end_transaction(\"$action$\",LR_AUTO);\n\n\n\n";

		String action = url.substring(url.lastIndexOf("/"));
		return info.replace("$action$", action).replace("$url$", url).replace("$item$", item);
	}

	/**
	 * 将信息保存到文件中
	 * 
	 * @param outFolder
	 * @param buffer
	 */
	public static void saveInfo(String outFolder, String buffer) {
		String infoFile = outFolder + "/request.txt";
		BufferedWriter out = null;
		try {
			File parent = new File(outFolder);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			out = new BufferedWriter(new FileWriter(infoFile, true));
			out.write(buffer);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 2015.06.03->2015.3.6
	 * 
	 * @param orgTime
	 * @return
	 */
	public static String dateRelpaceZero(String orgTime) {
		if (TextUtils.isEmpty(orgTime)) {
			return "";
		}
		return orgTime.replace(".0", ".");
	}

	/**
	 * 解决网络请求的中文路径问题
	 * 
	 * @param url
	 * @return String
	 */
	public static String encodeUrl(String url) {
		if (url == null)
			return "";
		String link = URLEncoder.encode(url);
		return link.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%3F", "?").replaceAll("%3D", "=").replace("%26","&");
	}

	/**
	 * 通过url获取文件名
	 * 
	 * @param url
	 * @return String
	 */
	public static String getFileNameByPath(String url) {
		if (url == null)
			return "";

		String newFilename = url.substring(url.lastIndexOf("/") + 1);

		if (newFilename.contains("?")) {
			newFilename = newFilename.substring(0, newFilename.indexOf("?"));
		}
		return newFilename;
	}

	/**
	 * textString标签的替换成htmlstring
	 * 
	 */

	public static String TextStrTohtmlStr(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		str = str.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll("\"", "&#34;");
		str = str.replaceAll("eval\\((.*)\\)", "");
		str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		return str;
	}

	/**
	 * 转换Web页面传入的字符串中特殊字符转为Java字符串
	 */
	public static String htmlStrToTextStr(String source) {
		if (TextUtils.isEmpty(source)) {
			return "";
		}
		source = source.replaceAll("&lt;", "<");
		source = source.replaceAll("&gt;", ">");
		source = source.replaceAll("&nbsp;", " ");
		source = source.replaceAll("&amp;", "&");
		source = source.replaceAll("&quot;", "\\\\\"");
		source = source.replaceAll("&apos;", "''");
		source = source.replaceAll("&#34;", "\\\\\"");
		return source;
	}

	/**
	 * 给链接添加token
	 */
	public static String wrapUrlWithToken(String url) {
		if (TextUtils.isEmpty(url)) {
			return "";
		}
		String token = MCSaveData.getUserInfo(Contants.LOGINTYPE,
				MoocApplication.getInstance()).toString();
		String learnLoginType = MCSaveData.getUserInfo(Contants.LEARLOGINTYPE,
				MoocApplication.getInstance()).toString();
		url = url + "?loginType=" + token + "&unTyxlLoginToken=" + learnLoginType;
		return url;
	}
}
