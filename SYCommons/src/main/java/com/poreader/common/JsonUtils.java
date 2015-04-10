package com.poreader.common;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	public static String toString(Object obj) {
		return JSON.toJSONString(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(String json) {
		return (T)JSON.parse(json);
	}
}
