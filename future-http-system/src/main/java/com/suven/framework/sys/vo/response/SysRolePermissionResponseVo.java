package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @ClassName: SysRolePermissionResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:49
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 http业务接口交互数据返回参数实现类
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

public class SysRolePermissionResponseVo  extends BaseByTimeEntity implements Serializable {




 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		@ExcelProperty(value = "角色id")
 		private long roleId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		@ExcelProperty(value = "权限id")
 		private long permissionId;

 		/** data_rule_ids 数据权限ids  */
 		@ApiDesc(value = "数据权限ids", required = 0)
 		@ExcelProperty(value = "数据权限ids")
 		private String dataRuleIds;

 		/** operate_ip 操作ip  */
 		@ApiDesc(value = "操作ip", required = 0)
 		@ExcelProperty(value = "操作ip")
 		private String operateIp;






    public static SysRolePermissionResponseVo build(){
        return new SysRolePermissionResponseVo();
    }

    
     		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysRolePermissionResponseVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
    
     		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysRolePermissionResponseVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
    
     		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysRolePermissionResponseVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
    
     		public void setOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		}
 		public SysRolePermissionResponseVo toOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		 return this ;
 		}

 		public String getOperateIp(){
 		 		return this.operateIp;
 		}
    
    
    
    
    






}
