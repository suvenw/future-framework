package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @ClassName: SysRoleResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:43
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 http业务接口交互数据返回参数实现类
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

public class SysRoleResponseVo  extends BaseByTimeEntity implements Serializable {




 		/** role_name 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		@ExcelProperty(value = "角色名称")
 		private String roleName;

 		/** role_code 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		@ExcelProperty(value = "角色编码")
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		@ExcelProperty(value = "描述")
 		private String description;






    public static SysRoleResponseVo build(){
        return new SysRoleResponseVo();
    }

    
     		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}
 		public SysRoleResponseVo toRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
    
     		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}
 		public SysRoleResponseVo toRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
    
     		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysRoleResponseVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
    
    
    
    
    






}
