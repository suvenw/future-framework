package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysUserRoleRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 17:00:29
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 http业务接口交互数据请求参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class SysUserRoleRequestVo extends HttpRequestByIdPageVo{





 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

		@ApiDesc(value = "登录账号", required = 0)
		private String username;



    public static SysUserRoleRequestVo build(){
        return new SysUserRoleRequestVo();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SysUserRoleRequestVo toUsername(String username) {
		this.username = username;
		return this;
	}

	public void setUserId(long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserRoleRequestVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRoleRequestVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}






}
