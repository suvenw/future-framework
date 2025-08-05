package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * ClassName: SysDepartRolePermissionQueryRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:59:50
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 http业务接口交互数据请求参数实现类
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

public class SysDepartRolePermissionQueryRequestVo extends HttpRequestByIdPageVo{




 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;

 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** data_rule_ids 数据权限ids  */
 		@ApiDesc(value = "数据权限ids", required = 0)
 		private String dataRuleIds;

 		/** operate_date 操作时间  */
 		@ApiDesc(value = "操作时间", required = 0)
 		private Date operateDate;

 		/** operate_ip 操作ip  */
 		@ApiDesc(value = "操作ip", required = 0)
 		private String operateIp;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;




    public static SysDepartRolePermissionQueryRequestVo build(){
        return new SysDepartRolePermissionQueryRequestVo();
    }



 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
 		public SysDepartRolePermissionQueryRequestVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
 		public SysDepartRolePermissionQueryRequestVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public SysDepartRolePermissionQueryRequestVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
 		public SysDepartRolePermissionQueryRequestVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public void setOperateDate( Date operateDate){
 		 		this.operateDate = operateDate ; 
 		 		}

 		public Date getOperateDate(){
 		 		return this.operateDate;
 		}
 		public SysDepartRolePermissionQueryRequestVo toOperateDate( Date operateDate){
 		 		this.operateDate = operateDate ; 
 		 		 return this ;
 		}

 		public void setOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		}

 		public String getOperateIp(){
 		 		return this.operateIp;
 		}
 		public SysDepartRolePermissionQueryRequestVo toOperateIp( String operateIp){
 		 		this.operateIp = operateIp ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDepartRolePermissionQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDepartRolePermissionQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}





}
