package com.suven.framework.sys.entity;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.core.db.ext.DS;

/**
  * @ClassName: SysUserDepart.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:14:14
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: 用户部门关系表 数据库表对应的实现类
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
public class SysUserDepart extends BaseTimeEntity {

private static final long serialVersionUID = 1L;




 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** dep_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;




    public static SysUserDepart build(){
        return new SysUserDepart();
    }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepart toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepart toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}
}