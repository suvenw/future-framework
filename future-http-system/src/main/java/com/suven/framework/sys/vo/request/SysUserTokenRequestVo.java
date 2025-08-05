package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysUserRequestVo.java
* Description: 用户表的数据交互处理类
* @author xxx.xxx
* date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysUserTokenRequestVo extends RequestParserVo {

	    @ApiDesc(value = "登录用户账号")
        private long userId;

		@ApiDesc(value = "登录用户账号")
		private String username;

	@ApiDesc(value = "登录用户账号")
	private String token;


    public static SysUserTokenRequestVo build(){
        return new SysUserTokenRequestVo();
    }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}


