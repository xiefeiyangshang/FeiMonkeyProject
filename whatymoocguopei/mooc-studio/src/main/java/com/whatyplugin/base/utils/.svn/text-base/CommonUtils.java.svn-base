package com.whatyplugin.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Environment;

import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class CommonUtils {

	// 解析后的课件节点信息存储的位置
	public static String basePath;

	static {
		basePath = Environment.getExternalStorageDirectory() + "/Android/data/" + MoocApplication.getInstance().getPackageName() + "/ware/xml/";
	}

	/**
	 * 从网络下载文件到本地
	 * 
	 * @param httpUrl
	 *            网络地址
	 * @param dir
	 *            本地存储的目录
	 * @return
	 */
	public static String downloadFile(String httpUrl, String dir) {

		String newFilename = StringUtils.getFileNameByPath(httpUrl);
		File file = new File(dir + newFilename);
		file.mkdirs();
		// 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
		if (file.exists()) {
			file.delete();
		}

		try {
			// 构造URL
			URL url = new URL(StringUtils.wrapUrlWithToken(httpUrl));
			// 打开连接
			URLConnection con = url.openConnection();

			List<String> infoList = new ArrayList<String>();
			
			//如果utf-8的没成功就用gb2312重新请求一下
			if(!getContentByURL(con, infoList, "utf-8")){
				infoList.clear();
				con = url.openConnection();
				getContentByURL(con, infoList, "gb2312");
			}

			writeToFile(infoList, dir, newFilename);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return file.getAbsolutePath();
	}

	private static boolean getContentByURL(URLConnection con, List<String> infoList, String byteCode) throws IOException,
			UnsupportedEncodingException {
		InputStream is = con.getInputStream();

		BufferedReader readerUFT = new BufferedReader(new InputStreamReader(is, byteCode));
		String temp = null;

		while ((temp = readerUFT.readLine()) != null) {
			infoList.add(temp);
		}
		is.close();
		
		boolean flag = false;
		if(infoList!=null && infoList.size()>0){
			String title = infoList.get(0);
			if(title.contains(byteCode) || title.contains(byteCode.toUpperCase())){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 向文件写入路径
	 * 
	 * @param pathList
	 *            保存文件路径
	 * @param filePath
	 *            filePath 文件名
	 */
	public static void writeToFile(List<String> pathList, String filePath, String fileName) {
		File imageFileDir = new File(filePath);
		if (!imageFileDir.exists()) {
			// 如果目录不存在，创建目录
			imageFileDir.mkdirs();
		}
		File imageFile = new File(filePath + "/" + fileName);
		if (!imageFile.exists()) {
			// 如果文件不存在，创建文件
			try {
				imageFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			FileOutputStream outStream = new FileOutputStream(imageFile);
			for (String str : pathList) {
				// 写入数据
				outStream.write((str + "\n").getBytes());
			}
			// 关闭输出流
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将00:30:22这种格式的时间转换为long类型 转为毫秒
	 * 
	 * @param time
	 *            示例：00:30:22
	 * @return
	 */
	public static int turnToTime(String time) {
		if (time == null || time.length() == 0 || !time.contains(":")) {
			return 0;
		}
		String[] arr = time.split(":");
		int result = Integer.valueOf(arr[0]) * 3600 + Integer.valueOf(arr[1]) * 60 + Integer.valueOf(arr[2]);
		return result * 1000;
	}

	/**
	 * 打印 List<String[]>
	 * 
	 * @param list
	 */
	public static void printListArray(List<String[]> list) {
		for (String[] arr : list) {
			int length = arr.length;
			for (int i = 0; i < length; i++) {
				System.out.print(arr[i] + " : ");
			}
			System.out.println();
		}
	}

	/**
	 * 打印 Map<String, List<String>> map
	 * 
	 * @param map
	 */
	public static void printMap(Map<String, List<String>> map) {
		Set<String> set = map.keySet();
		for (String str : set) {
			List<String> list = map.get(str);
			System.out.println("key = " + str);
			printList(list);
		}
	}

	/**
	 * 打印Map<String, List<String[]>> map
	 * 
	 * @param map
	 */
	public static void printMapArr(Map<String, List<String[]>> map) {
		Set<String> set = map.keySet();
		for (String str : set) {
			List<String[]> list = map.get(str);
			System.out.println("key = " + str);
			for (String[] arr : list) {
				for (int i = 0; i < arr.length; i++) {
					System.out.print(arr[i] + ":");
				}
				System.out.println();
			}
		}
	}

	/**
	 * 打印List<String> titleList
	 * 
	 * @param titleList
	 */
	public static void printList(List<String> titleList) {
		for (String str : titleList) {
			System.out.println(str);
		}
	}

}
