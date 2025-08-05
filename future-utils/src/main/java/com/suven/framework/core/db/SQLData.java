package com.suven.framework.core.db;

/**
 * Title: SQLData.java
 * @author Joven.wang
 * date 2016年1月21日
 * @version V1.0
 * Description: TODO(说明) 用于保存对象到数据存的实现返回对象;
 */
public class SQLData {

	private String sql;
	private Object[] value;

	public SQLData(String sql, Object[] value) {
		super();
		this.sql = sql;
		this.value = value;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getValue() {
		return value;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}

}