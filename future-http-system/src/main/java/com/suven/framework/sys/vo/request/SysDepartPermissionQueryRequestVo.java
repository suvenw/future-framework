package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysDepartPermissionQueryRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 17:00:36
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 http业务接口交互数据请求参数实现类
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

public class SysDepartPermissionQueryRequestVo extends HttpRequestByIdPageVo{




 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** data_rule_ids 数据规则id  */
 		@ApiDesc(value = "数据规则id", required = 0)
 		private String dataRuleIds;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;




    public static SysDepartPermissionQueryRequestVo build(){
        return new SysDepartPermissionQueryRequestVo();
    }



 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
 		public SysDepartPermissionQueryRequestVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public SysDepartPermissionQueryRequestVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
 		public SysDepartPermissionQueryRequestVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDepartPermissionQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDepartPermissionQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}





}
