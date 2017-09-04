package com.whatyplugin.imooc.logic.whatydb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识主键
 * @author maizi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
	/**
	 * 表示主键是否是自增的
	 * @return
	 */
	boolean autoIncrement();
}
