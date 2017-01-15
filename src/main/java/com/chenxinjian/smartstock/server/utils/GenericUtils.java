package com.chenxinjian.smartstock.server.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具類
 *
 */
@SuppressWarnings("unchecked")
public class GenericUtils {
	
	public static Class getSuperClassGenericType(Class clazz, int index) {
		// 获取泛型父类
		Type type = clazz.getGenericSuperclass();
		// 如果没有实现ParameterizedType接口,则不支持泛型,直接返回Object.class
		if(!(type instanceof ParameterizedType)){
			return Object.class;
		}
		
		ParameterizedType pType = (ParameterizedType) type;
		Type[] genType = pType.getActualTypeArguments();
		if(index > genType.length || genType.length < 0){
			throw new RuntimeException("你输入的索引" + (index<0 ? "不能小于0" : "超出了参数的总数"));
		}
		
		if(!(genType[index] instanceof Class)){
			return Object.class;
		}
		System.out.println("GenericUtil .... " + (Class)genType[index]);
		return (Class)genType[index];
	}
	
	public static Class getSuperClassGenericType(Class clazz){
		return getSuperClassGenericType(clazz, 0);
	}
}
