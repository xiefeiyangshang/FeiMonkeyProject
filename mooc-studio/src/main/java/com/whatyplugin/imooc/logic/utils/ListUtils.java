package com.whatyplugin.imooc.logic.utils;

import java.util.Collections;
import java.util.List;

public class ListUtils {
	/**
	 * 比较两个集合中的内容是否一致,参与比较集合元素应该自行实现Comparable接口
	 */
	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
		if (a.size() != b.size()) {
			return false;
		}
		Collections.sort(a);
		Collections.sort(b);
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}
}
