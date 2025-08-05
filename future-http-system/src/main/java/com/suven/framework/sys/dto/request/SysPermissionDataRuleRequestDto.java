package com.suven.framework.sys.dto.request;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysPermissionDataRuleRequestDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:35
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 RPC业务接口交互数据请求参数实现类
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


public class SysPermissionDataRuleRequestDto extends BaseByTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysPermissionDataRuleRequestDto.class);


 		/** permission_id 菜单ID  */
 		@ApiDesc(value = "菜单ID", required = 0)
 		private long permissionId;

 		/** rule_name 规则名称  */
 		@ApiDesc(value = "规则名称", required = 0)
 		private String ruleName;

 		/** rule_column 字段  */
 		@ApiDesc(value = "字段", required = 0)
 		private String ruleColumn;

 		/** rule_conditions 条件  */
 		@ApiDesc(value = "条件", required = 0)
 		private String ruleConditions;

 		/** rule_value 规则值  */
 		@ApiDesc(value = "规则值", required = 0)
 		private String ruleValue;

 		/** status   `status` int DEFAULT NULL COMMENT '删除状态(1-正常,0-已删除)',  */
 		@ApiDesc(value = "  `status` int DEFAULT NULL COMMENT '删除状态(1-正常,0-已删除)',", required = 0)
 		private int status;






        public static SysPermissionDataRuleRequestDto build(){
            return new SysPermissionDataRuleRequestDto();
        }

 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public void setRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		 return this ;
 		}

 		public String getRuleName(){
 		 		return this.ruleName;
 		}
 		public void setRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		 return this ;
 		}

 		public String getRuleColumn(){
 		 		return this.ruleColumn;
 		}
 		public void setRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		 return this ;
 		}

 		public String getRuleConditions(){
 		 		return this.ruleConditions;
 		}
 		public void setRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		 return this ;
 		}

 		public String getRuleValue(){
 		 		return this.ruleValue;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}
 		public SysPermissionDataRuleRequestDto toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public int getStatus(){
 		 		return this.status;
 		}




	


}
