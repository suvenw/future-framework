package com.suven.framework.sys.vo.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * ClassName: SysDepartPermissionResponseVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 17:00:36
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 http业务接口交互数据返回参数实现类
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

public class SysDepartPermissionResponseVo  extends BaseTimeEntity implements Serializable {




 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		@ExcelProperty(value = "部门id")
 		private long departId;

 		/** permission_id 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		@ExcelProperty(value = "权限id")
 		private long permissionId;

 		/** data_rule_ids 数据规则id  */
 		@ApiDesc(value = "数据规则id", required = 0)
 		@ExcelProperty(value = "数据规则id")
 		private String dataRuleIds;




    public static SysDepartPermissionResponseVo build(){
        return new SysDepartPermissionResponseVo();
    }

    
     		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartPermissionResponseVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
    
     		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysDepartPermissionResponseVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
    
     		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysDepartPermissionResponseVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
    
    
    






}
