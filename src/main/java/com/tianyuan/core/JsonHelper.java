package com.tianyuan.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {
	private static Gson gson;
	public static Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().setLenient()// json宽松
					.enableComplexMapKeySerialization()// 支持Map的key为复杂对象的形式
					.serializeNulls() // 智能null
					.setPrettyPrinting()// 调教格式
					.disableHtmlEscaping() // 默认是GSON把HTML 转义的
					.setDateFormat("yyyy-MM-dd HH:mm:ss")//日期格式化
					.create();
		}
		return gson;
	}
	
}
