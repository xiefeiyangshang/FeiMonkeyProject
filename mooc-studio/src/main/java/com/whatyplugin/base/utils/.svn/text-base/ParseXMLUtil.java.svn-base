package com.whatyplugin.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import android.text.TextUtils;

import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class ParseXMLUtil {

	public static String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<nodes version=\"1.1\" >"
			+ "</nodes>";

	/**
	 * 查找所有子元素里面第一个name为给定值的子元素。
	 * @param name	节点name值
	 * @param parentElement	父元素
	 * @return
	 */
	public static Element getMatchElementByName(String name,
			Element parentElement) {
		if (name == null || parentElement == null) {
			return null;
		}
		List<Element> elements = parentElement.elements();
		for (Element element : elements) {
			if (name.equals(element.getName())) {
				return element;
			}
		}

		return null;
	}

	/**
	 * 解析imsmanifest， 从中获取所有章节信息对应的目录结构
	 * @param filePath
	 * @return
	 */
	public static List<String> parseImsmanifest(String filePath) {
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			document = sax.read(new File(filePath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();

		Element element = getMatchElementByName("organizations", rootElement);
		element = getMatchElementByName("organization", element);
		List<String> list = new ArrayList<String>();

		List<Element> elements = element.elements();
		for (Element inner : elements) {
			if (inner.getName().equals("item")) {
				String key = inner.attribute("identifierref").getText();
				list.add(key);
			}
		}

		Element resourceElement = getMatchElementByName("resources", rootElement);
		List<Element> resourceElements = resourceElement.elements();
		Map<String, String> map = new HashMap<String, String>();
		for (Element inner : resourceElements) {
			if (inner.getName().equals("resource")) {
				String identifier = inner.attribute("identifier").getText();
				String href = inner.attribute("href").getText();
				href = href.substring(0, href.indexOf("/")+1) + "/WhatyFlash/Data/";
				map.put(identifier, href);
			}
		}
		
		List<String> resultList = new ArrayList<String>();
		for(String str:list){
			String value = map.get(str);
			if(!TextUtils.isEmpty(value)){
				resultList.add(value);
			}
		}
		
		return resultList;
	}

	/**
	 * 解析 parsePresentation, 向传入的modelList里累加节点信息
	 * @param link		网络根地址
	 * @param xmlPath	xml下载后的地址
	 * @param key		文件夹的名字
	 * @param modelList 要存储到的节点集合。
	 */
	public static void parsePresentation(String link, String xmlPath,
			String key, List<MCXmlSectionModel> modelList) {
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			document = sax.read(new File(xmlPath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();

		String baseHttpPath = link + key + "/WhatyFlash/";
		int totalCount = 0;
		Element propertiyElement = getMatchElementByName("properties", rootElement);
		MCXmlSectionModel model = new MCXmlSectionModel();
		model.setParent(true);
		
		String videoUrl = getNodeTextFromParent("video", propertiyElement);
		if(videoUrl!=null && videoUrl.endsWith(".json")){
			model.setVideoUrl(videoUrl);
		}else{
			model.setVideoUrl(StringUtils.wrapUrlWithToken(baseHttpPath + videoUrl));
		}
		
		String screentUrl = getNodeTextFromParent("screen", propertiyElement);
		if(screentUrl!=null && screentUrl.endsWith(".json")){
			model.setScreenUrl(screentUrl);
		}else{
			model.setScreenUrl(StringUtils.wrapUrlWithToken(baseHttpPath + screentUrl));
		}
		
		model.setTitle(getNodeTextFromParent("presentationtitle", propertiyElement));
		model.setId(key + totalCount++);
		modelList.add(model);
	    int totalTime = 0;
		Element element = getMatchElementByName("slides", rootElement);

		List<Element> elements = element.elements();
		for (Element inner : elements) {
			if (inner.getName().equals("slide")) {
				MCXmlSectionModel innerModel = new MCXmlSectionModel();
				innerModel.setParent(false);
				innerModel.setTitle(getNodeTextFromParent("title", inner));
				innerModel.setId(key + totalCount++);
				innerModel.setParentModel(model);
				String time = getNodeTextFromParent("time", inner);
				innerModel.setTime(CommonUtils.turnToTime(time));
				innerModel.setStrTime(time);
				innerModel.setStrTime(time);
				String thumbImage = getNodeTextFromParent("slideimage", inner);
				
				//因为有可能只有回车换行什么的就会导致判断不准确。
				if(!TextUtils.isEmpty(thumbImage) && (thumbImage.contains(".jpg")||thumbImage.contains(".png"))){
					innerModel.setThumbImage(StringUtils.wrapUrlWithToken(baseHttpPath + thumbImage));
				}
				totalTime += innerModel.getTime();
				modelList.add(innerModel);
			}
		}
		
		model.setTime(totalTime);
	}

	/**
	 * 获得父节点中指定名称的子节点的text值
	 * @param str
	 * @param element
	 * @return
	 */
	private static String getNodeTextFromParent(String str, Element element) {
		try {
			return getMatchElementByName(str, element).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将解析后的结果保存到本地的xml中。
	 * @param filePath
	 * @param modelList
	 */
	public static void writeToXml(String filePath, List<MCXmlSectionModel> modelList) {

		File file = new File(filePath);
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}
		try {
			// 输出的文件流
			OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			os.write(xmlContent);
			// 完毕，关闭所有链接
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			SAXReader sax = new SAXReader();
			Document document = sax.read(new File(filePath));
			Element rootEelement = document.getRootElement();
			boolean isPic = false;
			for (MCXmlSectionModel model : modelList) {
				if(model.getThumbImage()!=null){
					isPic = true;
					break;
				}
			}
			rootEelement.addAttribute("type", isPic ? "pic" : "normal");
			Element tempElem = null;
			for (MCXmlSectionModel model : modelList) {
				if (model.isParent()) {
					tempElem = rootEelement.addElement("chapter");
					tempElem.addAttribute("id", model.getId());
					tempElem.addAttribute("videoUrl", model.getVideoUrl());
					tempElem.addAttribute("screenUrl", model.getScreenUrl());
					tempElem.addAttribute("time", model.getTime() + "");
					tempElem.addAttribute("strTime", model.getStrTime());
					tempElem.addAttribute("title", model.getTitle());
				} else {
					Element propertyElement = tempElem.addElement("section");
					propertyElement.addAttribute("strTime", model.getStrTime());
					propertyElement.addAttribute("id", model.getId());
					propertyElement.addAttribute("videoUrl", model.getParentModel().getVideoUrl());
					propertyElement.addAttribute("screenUrl", model.getParentModel().getScreenUrl());
					propertyElement.addAttribute("time", model.getTime() + "");
					propertyElement.addAttribute("title", model.getTitle());
					propertyElement.addAttribute("thumbImage", model.getThumbImage());
				}
			}

			OutputFormat format = OutputFormat.createPrettyPrint(); // 设置XML文档输出格式
			format.setEncoding("utf-8"); // 设置XML文档的编码类型
			// 输出全部原始数据，在编译器中显示
			XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从本地保存好的xml中读取节点信息
	 * @param file
	 * @param modelList
	 */
	public static void getNodeListFromLocal(File file, List<MCXmlSectionModel> modelList) {
		try {
			SAXReader sax = new SAXReader();
			Document document = sax.read(file);
			Element rootEelement = document.getRootElement();
			String type = "normal";
			if(rootEelement.attribute("type")!=null) {
				rootEelement.attribute("type").getText();
			}
			List<Element> elements = rootEelement.elements();
			
			for(Element element : elements){
				MCXmlSectionModel model = new MCXmlSectionModel();
				
				model.setId(element.attribute("id").getText());
				model.setTitle(element.attribute("title").getText());
				model.setVideoUrl(element.attribute("videoUrl").getText());
				model.setScreenUrl(element.attribute("screenUrl").getText());
				model.setStrTime(element.attribute("strTime")==null?"":element.attribute("strTime").getText());
				model.setTime(Integer.valueOf(element.attribute("time").getText()));
				model.setParent(true);
				model.setType(type);
				modelList.add(model);
				
				List<Element> innerEle = element.elements();
				for(Element inner : innerEle){
					MCXmlSectionModel innerModel = new MCXmlSectionModel();
					
					innerModel.setId(inner.attribute("id").getText());
					innerModel.setTitle(inner.attribute("title").getText());
					innerModel.setThumbImage(inner.attribute("thumbImage")==null?null:inner.attribute("thumbImage").getText());
					innerModel.setVideoUrl(inner.attribute("videoUrl").getText());
					innerModel.setScreenUrl(inner.attribute("screenUrl").getText());
					innerModel.setStrTime(element.attribute("strTime")==null?"":element.attribute("strTime").getText());
					innerModel.setTime(Integer.valueOf(inner.attribute("time").getText()));
					innerModel.setParentModel(model);
					innerModel.setType(type);
					modelList.add(innerModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析双视频的相关xml
	 * @param link	网络根地址，课件的根路径 的  imsmanifest.xml 全路径
	 * @param modelList
	 */
	public static void parseDoubleMp4(String link, List<MCXmlSectionModel> modelList) {
		String filePath = CommonUtils.downloadFile(link, CommonUtils.basePath);//这里得设置超时时间，不然会请求不到网络
		
		List<String> list = ParseXMLUtil.parseImsmanifest(filePath);
		
		String basePath = link.replace("imsmanifest.xml", "");
		for(String dir: list){
			String path = basePath + dir + "whatyPresentation.xml";
			String savePath = CommonUtils.basePath + dir;
			String retPath = CommonUtils.downloadFile(path, savePath);
			String key = dir.replace("/WhatyFlash/Data/", "").replace("/", "");
			
			ParseXMLUtil.parsePresentation(basePath, retPath, key, modelList);
		}
	}
	
}
