package com.pawpaw.framework.core.common.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class JsonUtil {

	public static final TypeToken<Set<String>> stringSetType = new TypeToken<Set<String>>() {
	};
	public static final TypeToken<List<String>> stringListType = new TypeToken<List<String>>() {
	};

	private static Gson gson = new GsonBuilder().create();

	/**
	 * json转换成指定类型的对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T json2Object(String jsonStr, Class<T> clazz) {
		T t = gson.fromJson(jsonStr, clazz);
		return t;
	}

	/**
	 * json转换成指定类型的对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws Exception
	 */
	public static <T> T json2Object(String jsonStr, TypeToken<T> typeToken) {
		return gson.fromJson(jsonStr, typeToken.getType());

	}

	/**
	 * json转换成指定类型的对象
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> T json2Object(String jsonStr, Type type) {
		return gson.fromJson(jsonStr, type);
	}

	/**
	 * 对象转json
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String object2Json(Object obj) {
		return gson.toJson(obj);
	}

}
