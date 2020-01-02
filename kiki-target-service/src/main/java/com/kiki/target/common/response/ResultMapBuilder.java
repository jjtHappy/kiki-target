package com.kiki.target.common.response;

import java.util.HashMap;
import java.util.Map;

import com.kiki.target.common.vo.PageVo;

public class ResultMapBuilder {

	public static Map<String, Object> buildSuccessMap(Object data, String message, PageVo pageVo) {
		Map<String, Object> map = createResultMap();
		map.put("status", true);
		map.put("data", data);
		map.put("code", 200);
		if (null == message || message.trim().isEmpty()) {
			message = "success";
		}
		map.put("message", message);
		if (pageVo != null) {
			map.put("page", pageVo);
		}
		return map;
	}

	public static Map<String, Object> buildSuccessMap(Object data, String message) {
		return buildSuccessMap(data, message, null);
	}

	public static Map<String, Object> buildSuccessMap(String message) {
		return buildSuccessMap(null, message);
	}

	public static Map<String, Object> buildSuccessMap() {
		return buildSuccessMap(null, null);
	}

	public static Map<String, Object> buildErrorMap(String message,int code) {
		if (null == message || message.trim().isEmpty()) {
			message = "fail";
		}
		Map<String, Object> map = createResultMap();
		map.put("status", false);
		map.put("message", message);
		map.put("code", 400);
		return map;
	}
	
	public static Map<String, Object> buildErrorMap(String message) {
		return buildErrorMap(message,400);
	}

	public static Map<String, Object> buildErrorMap() {
		return buildErrorMap(null);
	}

	private static Map<String, Object> createResultMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	// public static void main(String[] args) {
	// String password = "cj123456";
	// }
}
