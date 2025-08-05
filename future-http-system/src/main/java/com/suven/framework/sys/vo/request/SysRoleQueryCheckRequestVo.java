package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;


/**
 * ClassName: SysRoleQueryRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 http业务接口交互数据请求参数实现类
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

public class SysRoleQueryCheckRequestVo extends HttpRequestByIdVo {






 		/** role_code 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;





    public static SysRoleQueryCheckRequestVo build(){
        return new SysRoleQueryCheckRequestVo();
    }





 		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
 		public SysRoleQueryCheckRequestVo toRoleCode(String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}





}
