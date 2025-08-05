package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysUserRequestVo.java
* Description: 用户表的数据交互处理类
* @author xxx.xxx
* @date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysUserUpdatePwdRequestVo extends RequestParserVo {


 		/** username 登录账号  */
 		@ApiDesc(value = "登录账号", required = 0)
 		private String username;


 		/** password 密码  */
 		@ApiDesc(value = "密码", required = 0)
 		private String password;

		/** oldpassword 旧密码  */
		@ApiDesc(value = "密码", required = 0)
		private String oldpassword;

 		/** confirmpassword 确认密码  */
 		@ApiDesc(value = "确认密码", required = 0)
 		private String confirmpassword;


    	public static SysUserUpdatePwdRequestVo build(){
        return new SysUserUpdatePwdRequestVo();
    }


	public String getUsername() {
		return username;
	}

	public SysUserUpdatePwdRequestVo setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SysUserUpdatePwdRequestVo setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public SysUserUpdatePwdRequestVo setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
		return this;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public SysUserUpdatePwdRequestVo setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
		return this;
	}
}
