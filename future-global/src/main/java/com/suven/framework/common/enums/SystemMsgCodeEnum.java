package com.suven.framework.common.enums;

import com.suven.framework.http.inters.IResultCodeEnum;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2022-02-21
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  后台管理系统错误码
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public enum SystemMsgCodeEnum implements IResultCodeEnum {
	/** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
	SYS_SC_OK_200 (200, "成功"),
	SYS_ERROR_500(500,"%s"),
	SYS_TOKEN_FAIL(501, "Token已经失效。"),
	SYS_LOGIN_CODE_FAIL(510, "验证码失效,访问权限认证未通过。"),
	SYS_USER_FAIL(511, "用户不存在。"),
	SYS_USER_PWD_FAIL(512, "用户名或密码错误。"),
	SYS_LOGOUT_FAIL(513, "退出登录失败。"),
	SYS_USER_SAVE_FAIL(514,"用户添加失败"),
	SYS_USER_FOUND_FAIL(515,"未找到角色信息"),
	SYS_USER_ROLE_FOUND_FAIL(516,"未找到用户相关角色信息"),
	SYS_USER_OLD_PWD_FAIL(517,"旧密码输入错误!"),
	SYS_USER_NEW_PWD_FAIL(518,"新密码不允许为空!"),
	SYS_USER_TWO_PWD_FAIL(519,"两次输入密码不一致!"),
	SYS_USER_NEW_PWD_LENGTH(520,"新密码长度至少6位"),
	SYS_USER_FIND_FAIL(521,"未找到角色信息"),
	SYS_USER_ROLE_FIND_FAIL(522,"未找到用户相关角色信息"),
	SYS_USER_DEPART_FIND_FAIL(523,"未找到用户相关部门信息"),
	SYS_USER_BAND_FAIL(524, "用户已被封禁。"),
	;
//

	private int code;
	private String message;


	SystemMsgCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return message;
	}


}
