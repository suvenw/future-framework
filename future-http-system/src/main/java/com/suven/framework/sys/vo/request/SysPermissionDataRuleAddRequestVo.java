package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

/**
 * @ClassName: SysPermissionDataRuleAddRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:35
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 http业务接口交互数据请求参数实现类
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


public class SysPermissionDataRuleAddRequestVo extends HttpRequestByIdVo{




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

 		/** create_time 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createTime;

 		/** create_by   */
 		@ApiDesc(value = "", required = 0)
 		private String createBy;

 		/** update_time 修改时间  */
 		@ApiDesc(value = "修改时间", required = 0)
 		private Date updateTime;

 		/** update_by 修改人  */
 		@ApiDesc(value = "修改人", required = 0)
 		private String updateBy;





    public static SysPermissionDataRuleAddRequestVo build(){
        return new SysPermissionDataRuleAddRequestVo();
    }




 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public SysPermissionDataRuleAddRequestVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public void setRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		}

 		public String getRuleName(){
 		 		return this.ruleName;
 		}
 		public SysPermissionDataRuleAddRequestVo toRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		 return this ;
 		}

 		public void setRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		}

 		public String getRuleColumn(){
 		 		return this.ruleColumn;
 		}
 		public SysPermissionDataRuleAddRequestVo toRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		 return this ;
 		}

 		public void setRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		}

 		public String getRuleConditions(){
 		 		return this.ruleConditions;
 		}
 		public SysPermissionDataRuleAddRequestVo toRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		 return this ;
 		}

 		public void setRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		}

 		public String getRuleValue(){
 		 		return this.ruleValue;
 		}
 		public SysPermissionDataRuleAddRequestVo toRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		 return this ;
 		}

 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysPermissionDataRuleAddRequestVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysPermissionDataRuleAddRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysPermissionDataRuleAddRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysPermissionDataRuleAddRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysPermissionDataRuleAddRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}






}
