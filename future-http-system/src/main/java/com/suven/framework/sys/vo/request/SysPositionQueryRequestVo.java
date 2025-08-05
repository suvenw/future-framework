package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * ClassName: SysPositionQueryRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:52
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  http业务接口交互数据请求参数实现类
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

public class SysPositionQueryRequestVo extends HttpRequestByIdPageVo{




 		/** code 职务编码  */
 		@ApiDesc(value = "职务编码", required = 0)
 		private String code;

 		/** name 职务名称  */
 		@ApiDesc(value = "职务名称", required = 0)
 		private String name;

 		/** post_rank 职级  */
 		@ApiDesc(value = "职级", required = 0)
 		private String postRank;

 		/** company_id 公司id  */
 		@ApiDesc(value = "公司id", required = 0)
 		private String companyId;

 		/** create_by 创建人  */
 		@ApiDesc(value = "创建人", required = 0)
 		private String createBy;

 		/** create_time 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createTime;

 		/** update_by 修改人  */
 		@ApiDesc(value = "修改人", required = 0)
 		private String updateBy;

 		/** update_time 修改时间  */
 		@ApiDesc(value = "修改时间", required = 0)
 		private Date updateTime;

 		/** sys_org_code 组织机构编码  */
 		@ApiDesc(value = "组织机构编码", required = 0)
 		private String sysOrgCode;




    public static SysPositionQueryRequestVo build(){
        return new SysPositionQueryRequestVo();
    }



 		public void setCode( String code){
 		 		this.code = code ; 
 		 		}

 		public String getCode(){
 		 		return this.code;
 		}
 		public SysPositionQueryRequestVo toCode( String code){
 		 		this.code = code ; 
 		 		 return this ;
 		}

 		public void setName( String name){
 		 		this.name = name ; 
 		 		}

 		public String getName(){
 		 		return this.name;
 		}
 		public SysPositionQueryRequestVo toName( String name){
 		 		this.name = name ; 
 		 		 return this ;
 		}

 		public void setPostRank( String postRank){
 		 		this.postRank = postRank ; 
 		 		}

 		public String getPostRank(){
 		 		return this.postRank;
 		}
 		public SysPositionQueryRequestVo toPostRank( String postRank){
 		 		this.postRank = postRank ; 
 		 		 return this ;
 		}

 		public void setCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		}

 		public String getCompanyId(){
 		 		return this.companyId;
 		}
 		public SysPositionQueryRequestVo toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}

 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysPositionQueryRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysPositionQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysPositionQueryRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysPositionQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

 		public void setSysOrgCode( String sysOrgCode){
 		 		this.sysOrgCode = sysOrgCode ; 
 		 		}

 		public String getSysOrgCode(){
 		 		return this.sysOrgCode;
 		}
 		public SysPositionQueryRequestVo toSysOrgCode( String sysOrgCode){
 		 		this.sysOrgCode = sysOrgCode ; 
 		 		 return this ;
 		}





}
