package com.tiny.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

	public static <T> List<T> executeJsonArray(JSONArray content, Class<T> clss) {
		if (content == null)
			return null;
		List<T> datalist = new ArrayList<T>();
		try{
			for (int i = 0; i < content.length(); i++) {
				JSONObject json;
				json = content.getJSONObject(i);
				T data = JSON.parseObject(json.toString(), clss);
				datalist.add(data);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return datalist;
	}
	public static <T> T executeObject(String jsonStr, Class<T> cls) {
		if (StringUtil.isEmpty(jsonStr)|| cls == null) {
			return null;
		}
		try {
			T json = JSON.parseObject(jsonStr, cls);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static <T> List<T> executeJsonArray(String content, Class<T> clss) {
		List<T> datalist = new ArrayList<T>();
		if (content == null)
			return datalist;
		JSONArray array;
		try {
			array = new JSONArray(content);
			for (int i = 0; i < array.length(); i++) {
					T data = executeObject(array.get(i).toString(),clss);
					datalist.add(data);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return datalist;
	}
	
}
