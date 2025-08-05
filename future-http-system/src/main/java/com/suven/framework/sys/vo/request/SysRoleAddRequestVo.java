package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

/**
 * ClassName: SysRoleAddRequestVo.java
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


public class SysRoleAddRequestVo extends HttpRequestByIdVo{




 		/** role_name 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		private String roleName;

 		/** role_code 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** create_by 创建人  */
 		@ApiDesc(value = "创建人", required = 0)
 		private String createBy;

 		/** create_time 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createTime;

 		/** update_by 更新人  */
 		@ApiDesc(value = "更新人", required = 0)
 		private String updateBy;

 		/** update_time 更新时间  */
 		@ApiDesc(value = "更新时间", required = 0)
 		private Date updateTime;





    public static SysRoleAddRequestVo build(){
        return new SysRoleAddRequestVo();
    }




 		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
 		public SysRoleAddRequestVo toRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
 		public SysRoleAddRequestVo toRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public SysRoleAddRequestVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysRoleAddRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysRoleAddRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysRoleAddRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysRoleAddRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}






}
