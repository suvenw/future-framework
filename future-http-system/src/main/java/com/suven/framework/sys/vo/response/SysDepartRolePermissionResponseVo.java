package com.suven.framework.sys.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * ClassName: SysDepartRolePermissionResponseVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:59:50
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 http业务接口交互数据返回参数实现类
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

public class SysDepartRolePermissionResponseVo  extends BaseTimeEntity implements Serializable {




 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		@ExcelProperty(value = "部门id")
 		private long departId;

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

 		/** operate_date 操作时间  */
 		@ApiDesc(value = "操作时间", required = 0)
 		@ExcelProperty(value = "操作时间")
 		private Date operateDate;

 		/** operate_ip 操作ip  */
 		@ApiDesc(value = "操作ip", required = 0)
 		@ExcelProperty(value = "操作ip")
 		private String operateIp;




    public static SysDepartRolePermissionResponseVo build(){
        return new SysDepartRolePermissionResponseVo();
    }

    
     		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
    
     		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
    
     		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
    
     		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
    
     		public void setOperateDate( Date operateDate){
 		 		this.operateDate = operateDate ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toOperateDate( Date operateDate){
 		 		this.operateDate = operateDate ; 
 		 		 return this ;
 		}

 		public Date getOperateDate(){
 		 		return this.operateDate;
 		}
    
     		public void setOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		}
 		public SysDepartRolePermissionResponseVo toOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		 return this ;
 		}

 		public String getOperateIp(){
 		 		return this.operateIp;
 		}
    
    
    






}
