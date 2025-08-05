package com.suven.framework.sys.entity;

import com.suven.framework.http.data.entity.BaseByTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

/**
  * @ClassName: SysPosition.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:13:52
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description:  数据库表对应的实现类
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

@DS(DataSourceModuleName.module_name_sys)
public class SysPosition extends BaseByTimeEntity{

private static final long serialVersionUID = 1L;



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





 		/** sys_org_code 组织机构编码  */
 		@ApiDesc(value = "组织机构编码", required = 0)
 		private String sysOrgCode;


    public static SysPosition build(){
        return new SysPosition();
    }

 		public void setCode( String code){
 		 		this.code = code ; 
 		 		}
 		public SysPosition toCode( String code){
 		 		this.code = code ; 
 		 		 return this ;
 		}

 		public String getCode(){
 		 		return this.code;
 		}
 		public void setName( String name){
 		 		this.name = name ; 
 		 		}
 		public SysPosition toName( String name){
 		 		this.name = name ; 
 		 		 return this ;
 		}

 		public String getName(){
 		 		return this.name;
 		}
 		public void setPostRank( String postRank){
 		 		this.postRank = postRank ; 
 		 		}
 		public SysPosition toPostRank( String postRank){
 		 		this.postRank = postRank ; 
 		 		 return this ;
 		}

 		public String getPostRank(){
 		 		return this.postRank;
 		}
 		public void setCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		}
 		public SysPosition toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}

 		public String getCompanyId(){
 		 		return this.companyId;
 		}
 		public void setSysOrgCode( String sysOrgCode){
 		 		this.sysOrgCode = sysOrgCode ; 
 		 		}
 		public SysPosition toSysOrgCode( String sysOrgCode){
 		 		this.sysOrgCode = sysOrgCode ; 
 		 		 return this ;
 		}

 		public String getSysOrgCode(){
 		 		return this.sysOrgCode;
 		}
}