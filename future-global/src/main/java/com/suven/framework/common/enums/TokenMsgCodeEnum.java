package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum TokenMsgCodeEnum {
	SUCCESS(0, "登陆成功"),
	ACCESS_TOKEN_FAIL(1, "ACCESS_TOKEN校验过期,请重新登陆"),
	RESET_PASSWORD(2, "该账号已被重置密码"),
	DEVICE_LOGIN(3, "账号在其他设备登录，请重新登录");

	private int index;

	private String value;

	private static Map<Integer, TokenMsgCodeEnum> typeMap = new HashMap<>();

	static {
		for (TokenMsgCodeEnum msgType : values()) {
			typeMap.put(msgType.index, msgType);
		}
	}

	TokenMsgCodeEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	public static TokenMsgCodeEnum getType(int index) {
		return typeMap.get(index);
	}

	public int getIndex() {
		return index;
	}

	public String getIndexString() {
		return String.valueOf(index);
	}

	public String getValue() {
		return value;
	}
}
