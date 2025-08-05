package com.suven.framework.sys.entity;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

/**
  * ClassName: SysRolePermission.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:10:49
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 角色权限表 数据库表对应的实现类
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

@DS(DataSourceModuleName.module_name_sys)
public class SysRolePermission extends BaseByTimeEntity{

private static final long serialVersionUID = 1L;




 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** data_rule_ids 数据权限ids  */
 		@ApiDesc(value = "数据权限ids", required = 0)
 		private String dataRuleIds;

 		/** operate_ip 操作ip  */
 		@ApiDesc(value = "操作ip", required = 0)
 		private String operateIp;






    public static SysRolePermission build(){
        return new SysRolePermission();
    }

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysRolePermission toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysRolePermission toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysRolePermission toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
 		public void setOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		}
 		public SysRolePermission toOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		 return this ;
 		}

 		public String getOperateIp(){
 		 		return this.operateIp;
 		}
}