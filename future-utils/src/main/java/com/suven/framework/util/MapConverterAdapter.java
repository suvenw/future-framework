package com.suven.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vincentdeng
 * 
 */
public class MapConverterAdapter {

	private Map<?, ?> map;

	private final static Logger logger = LoggerFactory
			.getLogger(MapConverterAdapter.class);

	private MapConverterAdapter() {
	}

	public static MapConverterAdapter getInstance(Map<?, ?> map) {
		MapConverterAdapter mapperAdapter = new MapConverterAdapter();
		mapperAdapter.map = map;
		return mapperAdapter;
	}

	public Object get(String key) {
		return map.get(key);
	}
	
	public MapConverterAdapter getMap(String key) {
		
		Object mapReturn = null;
		if((mapReturn = map.get(key)) instanceof Map){
			return MapConverterAdapter.getInstance((Map<?, ?>) mapReturn);
		}
		
		Map<String, Object> stringMap = new HashMap<String, Object>();
		stringMap.put(key, mapReturn.toString());
		
		return MapConverterAdapter.getInstance(stringMap);
	}
	
	public List<?> getList(String key) {
		ObjectMapper om = new ObjectMapper();
		try {
			return (List<?>) om.readValue(map.get(key).toString(), List.class);
		} catch (IOException e) {
			logger.debug("converter list exception:{}", key);
		}
		return null;
	}

	public long getLong(String key) {
		String string = "";
		try {

			string = map.get(key).toString();
			return Long.parseLong(string);

		} catch (Exception e) {

			try {
				if (string.endsWith(".00")) {
					return Long.parseLong(string.substring(0,
							string.length() - 3));
				}
				if (string.endsWith(".0")) {
					return Long.parseLong(string.substring(0,
							string.length() - 2));
				}
			} catch (Exception ex) {
			}
			logger.debug("converter Long exception:{}", key);
		}
		return 0L;
	}

	public int getInt(String key) {
		String string = "";
		try {
			string = map.get(key).toString();
			return Integer.parseInt(string);
		} catch (Exception e) {
			try {
				if (string.endsWith(".00")) {
					return Integer.parseInt(string.substring(0,
							string.length() - 3));
				}
				
				if (string.endsWith(".0")) {
					return Integer.parseInt(string.substring(0,
							string.length() - 2));
				}
			} catch (Exception ex) {
			}
			logger.debug("converter int exception:{}", key);
		}

		return 0;
	}

	public float getFloat(String key) {
		try {
			return Float.parseFloat(map.get(key).toString());
		} catch (Exception e) {
			logger.debug("converter Float exception:{}", key);
		}
		return 0;
	}

	public String getString(String key) {
		try {
			return map.get(key).toString();
		} catch (Exception e) {
			logger.debug("converter String exception:{}", key);
		}
		return "";
	}

}
