package com.tianyuan.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class EntityBinder {
	
	
	public static <T> T entityBinder(ResultSet rs,Class<T> obj){
		
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			T newInstance = obj.newInstance();
	
			for (int i = 1; i <= count; i++) {
				String name = metaData.getColumnName(i).toLowerCase();
				name = toJavaField(name);
				String substring = name.substring(0, 1);
				String replace = name.replaceFirst(substring, substring.toUpperCase());
				
				Class<?> type = obj.getDeclaredField(name).getType();
				Method method = obj.getMethod("set" + replace, type);
				
				if(type.isAssignableFrom(String.class)){
					method.invoke(newInstance, rs.getString(i));
				}else if(type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)){
					method.invoke(newInstance, rs.getInt(i));
				}else if(type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)){
					method.invoke(newInstance, rs.getBoolean(i));
				}else if(type.isAssignableFrom(Date.class)){
					//method.invoke(newInstance, rs.getDate(i));
					method.invoke(newInstance, rs.getTimestamp(i));
				}else if(type.isAssignableFrom(double.class)||type.isAssignableFrom(Double.class)) {
					method.invoke(newInstance, rs.getDouble(i));
				}
				else if(type.isAssignableFrom(long.class)||type.isAssignableFrom(Long.class)) {
					method.invoke(newInstance, rs.getLong(i));
				}
			}
			return newInstance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
	

	}
	
	
	
	public static <T> ArrayList<T> entityListBinder(ResultSet rs, Class<T> obj) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		ArrayList<T> arrayList = new ArrayList<T>();
		ResultSetMetaData metaData = rs.getMetaData();
		/**
		 * 获取总列数
		 */
		int count = metaData.getColumnCount();
		while (rs.next()) {
			/**
			 * 创建对象实例
			 */
			T newInstance = obj.newInstance();
			for (int i = 1; i <= count; i++) {
				/**
				 * 给对象的某个属性赋值
				 */
				String name = metaData.getColumnName(i).toLowerCase();
				name = toJavaField(name);// 改变列名格式成java命名格式
				String substring = name.substring(0, 1);// 首字母大写
				String replace = name.replaceFirst(substring, substring.toUpperCase());
				Class<?> type = obj.getDeclaredField(name).getType();// 获取字段类型
				Method method = obj.getMethod("set" + replace, type);
				/**
				 * 判断读取数据的类型
				 */
				if(type.isAssignableFrom(String.class)){
					method.invoke(newInstance, rs.getString(i));
				}else if(type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)){
					method.invoke(newInstance, rs.getInt(i));
				}else if(type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)){
					method.invoke(newInstance, rs.getBoolean(i));
				}else if(type.isAssignableFrom(Date.class)){
					method.invoke(newInstance, rs.getDate(i));
				}
			}
			arrayList.add(newInstance);
		}
		return arrayList;
 
	}
	
	
	private static String toJavaField(String str) {
		 
		String[] split = str.split("_");
		StringBuilder builder = new StringBuilder();
		builder.append(split[0]);
		if (split.length > 1) {
			for (int i = 1; i < split.length; i++) {
				String string = split[i];
				String substring = string.substring(0, 1);
				split[i] = string.replaceFirst(substring, substring.toUpperCase());
				builder.append(split[i]);
			}
		}
		return builder.toString();
	}
}
