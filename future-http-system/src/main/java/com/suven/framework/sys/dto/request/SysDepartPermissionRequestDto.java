package com.suven.framework.sys.dto.request;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysDepartPermissionRequestDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 17:00:36
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 RPC业务接口交互数据请求参数实现类
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


public class SysDepartPermissionRequestDto extends BaseTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysDepartPermissionRequestDto.class);


 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** data_rule_ids 数据规则id  */
 		@ApiDesc(value = "数据规则id", required = 0)
 		private String dataRuleIds;




        public static SysDepartPermissionRequestDto build(){
            return new SysDepartPermissionRequestDto();
        }

 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartPermissionRequestDto toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysDepartPermissionRequestDto toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysDepartPermissionRequestDto toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}




	


}
