package com.whatyplugin.imooc.logic.whatydb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解决表和实体的一一对应关系
 * @author maizi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {
	 /**
	  * 表名
	  * @return
	  */
	String value();
}
