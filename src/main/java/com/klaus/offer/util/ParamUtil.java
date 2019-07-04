package com.klaus.offer.util;


import com.klaus.offer.exception.ParamsErrorException;

import java.util.Map;

public class ParamUtil {
	public static String checkParam(Object param) throws ParamsErrorException {
		String paraValue = null;
		if (param == null || (paraValue = param.toString()).isEmpty()) {
			throw new ParamsErrorException("error param");
		}
		return paraValue;
	}
	
	public static <T> T getValue(Map map, Object key, T defaultValue) {
		if (map.get(key) != null) {
			return (T) map.get(key);
		} else {
			return defaultValue;
		}
	}
}
