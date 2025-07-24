package com.suven.framework.core.redis;

import com.suven.framework.util.json.StringFormat;

import java.util.Collection;
import java.util.Map;


/**   
 * @Title: CacheSetParam.java 
 * @author Joven.wang   
 * @date   2016年2月26日
 * @version V1.0  
 * Description: TODO(说明)
 */

public class CacheSetParam {

	/**
	 * 缓存到redis中的集合Set的key
	 */
	private String redisPrefixKey; 
	/**
	 * 缓存集合set中的字段名称;
	 */
	private Object setMemberKey;
	/**
	 * 缓存到redis set中排序的字段值;
	 */
	private long orderScoreVale;
	
	private Map<String,Double> map;
	
	private Collection<Object> colle;
	
	public CacheSetParam(String redisPrefixKey) {
		super();
		this.redisPrefixKey = redisPrefixKey;
	}
	
	public CacheSetParam(String redisPrefixKey, Object setMemberKey,
			long orderScoreVale) {
		super();
		this.redisPrefixKey = redisPrefixKey;
		this.setMemberKey = setMemberKey == null ? "" : String.valueOf(setMemberKey);
		this.orderScoreVale = orderScoreVale;
	}

	/**
	 * 缓存到redis中的集合Set的key
	 */
	public String getRedisPrefixKey() {
		return redisPrefixKey;
	}

	/**
	 * 缓存集合set中的字段名称;
	 */
	public String getSetMemberKey() {
		return setMemberKey == null ? "" : String.valueOf(setMemberKey);
	}
	/**
	 * 缓存集合set中的字段名称;
	 */
	public void setSetMemberKey(Object setMemberKey) {
		this.setMemberKey = setMemberKey;
	}

	/**
	 * 缓存到redis set中排序的字段值;
	 */
	public long getOrderScoreVale() {
		return orderScoreVale;
	}

	/**
	 * 缓存到redis set中排序的字段值;
	 */
	public void setOrderScoreVale(long orderScoreVale) {
		this.orderScoreVale = orderScoreVale;
	}
	

	public Map<String, Double> getMap() {
		return map;
	}

	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	public String[] getColle() {
		return StringFormat.toArray(colle);
	}

	public void setColle(Collection<Object> colle) {
		this.colle = colle;
	}
}
