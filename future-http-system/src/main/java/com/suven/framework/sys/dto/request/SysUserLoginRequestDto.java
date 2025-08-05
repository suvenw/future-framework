package com.suven.framework.sys.dto.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseBeanClone;

/**
 * 登录表单
 *
 * @Author scott
 * @since  2019-01-18
 */
public class SysUserLoginRequestDto extends BaseBeanClone {

    /** username 登录账号  */
    @ApiDesc(value = "登录账号", required = 1)
    private String username;
    /** password 密码  */
    @ApiDesc(value = "密码", required = 1)
    private String password;

	@ApiDesc(value = "验证码", required = 1)
    private String captcha;

	@ApiDesc(value = "验证码key", required = 1)
    private String checkKey;

    public static SysUserLoginRequestDto build() {
        return new SysUserLoginRequestDto();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

	public String getCheckKey() {
		return checkKey;
	}

	public void setCheckKey(String checkKey) {
		this.checkKey = checkKey;
	}
    
}