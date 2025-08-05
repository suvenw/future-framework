package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

/**
 * ClassName: SysDepartAddRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:33:38
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 组织机构表 http业务接口交互数据请求参数实现类
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


public class SysDepartAddRequestVo extends HttpRequestByIdVo{




 		/** parent_id 父机构ID  */
 		@ApiDesc(value = "父机构ID", required = 0)
 		private long parentId;

 		/** depart_name 机构/部门名称  */
 		@ApiDesc(value = "机构/部门名称", required = 0)
 		private String departName;

 		/** depart_name_en 英文名  */
 		@ApiDesc(value = "英文名", required = 0)
 		private String departNameEn;

 		/** depart_name_abbr 缩写  */
 		@ApiDesc(value = "缩写", required = 0)
 		private String departNameAbbr;

 		/** depart_order 排序  */
 		@ApiDesc(value = "排序", required = 0)
 		private int departOrder;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** org_category 机构类别 1公司，2组织机构，2岗位  */
 		@ApiDesc(value = "机构类别 1公司，2组织机构，2岗位", required = 0)
 		private int orgCategory;

 		/** org_type 机构类型 1一级部门 2子部门  */
 		@ApiDesc(value = "机构类型 1一级部门 2子部门", required = 0)
 		private int orgType;

 		/** org_code 机构编码  */
 		@ApiDesc(value = "机构编码", required = 0)
 		private String orgCode;

 		/** mobile 手机号  */
 		@ApiDesc(value = "手机号", required = 0)
 		private String mobile;

 		/** fax 传真  */
 		@ApiDesc(value = "传真", required = 0)
 		private String fax;

 		/** address 地址  */
 		@ApiDesc(value = "地址", required = 0)
 		private String address;

 		/** memo 备注  */
 		@ApiDesc(value = "备注", required = 0)
 		private String memo;

 		/** status 状态（1启用，0不启用）  */
 		@ApiDesc(value = "状态（1启用，0不启用）", required = 0)
 		private int status;

 		/** del_flag 删除状态（0，正常，1已删除）  */
 		@ApiDesc(value = "删除状态（0，正常，1已删除）", required = 0)
 		private int delFlag;

 		/** qywx_identifier 对接企业微信的ID  */
 		@ApiDesc(value = "对接企业微信的ID", required = 0)
 		private String qywxIdentifier;

 		/** create_by 创建人  */
 		@ApiDesc(value = "创建人", required = 0)
 		private String createBy;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_by 更新人  */
 		@ApiDesc(value = "更新人", required = 0)
 		private String updateBy;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;





    public static SysDepartAddRequestVo build(){
        return new SysDepartAddRequestVo();
    }




 		public void setParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		}

 		public long getParentId(){
 		 		return this.parentId;
 		}
 		public SysDepartAddRequestVo toParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		 return this ;
 		}

 		public void setDepartName( String departName){
 		 		this.departName = departName ; 
 		 		}

 		public String getDepartName(){
 		 		return this.departName;
 		}
 		public SysDepartAddRequestVo toDepartName( String departName){
 		 		this.departName = departName ; 
 		 		 return this ;
 		}

 		public void setDepartNameEn( String departNameEn){
 		 		this.departNameEn = departNameEn ; 
 		 		}

 		public String getDepartNameEn(){
 		 		return this.departNameEn;
 		}
 		public SysDepartAddRequestVo toDepartNameEn( String departNameEn){
 		 		this.departNameEn = departNameEn ; 
 		 		 return this ;
 		}

 		public void setDepartNameAbbr( String departNameAbbr){
 		 		this.departNameAbbr = departNameAbbr ; 
 		 		}

 		public String getDepartNameAbbr(){
 		 		return this.departNameAbbr;
 		}
 		public SysDepartAddRequestVo toDepartNameAbbr( String departNameAbbr){
 		 		this.departNameAbbr = departNameAbbr ; 
 		 		 return this ;
 		}

 		public void setDepartOrder( int departOrder){
 		 		this.departOrder = departOrder ; 
 		 		}

 		public int getDepartOrder(){
 		 		return this.departOrder;
 		}
 		public SysDepartAddRequestVo toDepartOrder( int departOrder){
 		 		this.departOrder = departOrder ; 
 		 		 return this ;
 		}

 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public SysDepartAddRequestVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public void setOrgCategory( int orgCategory){
 		 		this.orgCategory = orgCategory ; 
 		 		}

 		public int getOrgCategory(){
 		 		return this.orgCategory;
 		}
 		public SysDepartAddRequestVo toOrgCategory( int orgCategory){
 		 		this.orgCategory = orgCategory ; 
 		 		 return this ;
 		}

 		public void setOrgType( int orgType){
 		 		this.orgType = orgType ; 
 		 		}

 		public int getOrgType(){
 		 		return this.orgType;
 		}
 		public SysDepartAddRequestVo toOrgType( int orgType){
 		 		this.orgType = orgType ; 
 		 		 return this ;
 		}

 		public void setOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		}

 		public String getOrgCode(){
 		 		return this.orgCode;
 		}
 		public SysDepartAddRequestVo toOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		 return this ;
 		}

 		public void setMobile( String mobile){
 		 		this.mobile = mobile ; 
 		 		}

 		public String getMobile(){
 		 		return this.mobile;
 		}
 		public SysDepartAddRequestVo toMobile( String mobile){
 		 		this.mobile = mobile ; 
 		 		 return this ;
 		}

 		public void setFax( String fax){
 		 		this.fax = fax ; 
 		 		}

 		public String getFax(){
 		 		return this.fax;
 		}
 		public SysDepartAddRequestVo toFax( String fax){
 		 		this.fax = fax ; 
 		 		 return this ;
 		}

 		public void setAddress( String address){
 		 		this.address = address ; 
 		 		}

 		public String getAddress(){
 		 		return this.address;
 		}
 		public SysDepartAddRequestVo toAddress( String address){
 		 		this.address = address ; 
 		 		 return this ;
 		}

 		public void setMemo( String memo){
 		 		this.memo = memo ; 
 		 		}

 		public String getMemo(){
 		 		return this.memo;
 		}
 		public SysDepartAddRequestVo toMemo( String memo){
 		 		this.memo = memo ; 
 		 		 return this ;
 		}

 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysDepartAddRequestVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public SysDepartAddRequestVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public void setQywxIdentifier( String qywxIdentifier){
 		 		this.qywxIdentifier = qywxIdentifier ; 
 		 		}

 		public String getQywxIdentifier(){
 		 		return this.qywxIdentifier;
 		}
 		public SysDepartAddRequestVo toQywxIdentifier( String qywxIdentifier){
 		 		this.qywxIdentifier = qywxIdentifier ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysDepartAddRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDepartAddRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysDepartAddRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDepartAddRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}






}
