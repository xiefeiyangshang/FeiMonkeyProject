package com.whatyplugin.imooc.logic.whatydb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解决实体中属性与数据库表中列的对应关系
 * @author maizi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 对应的数据库表中的列名
	 * @return
	 */
	String value();
}
