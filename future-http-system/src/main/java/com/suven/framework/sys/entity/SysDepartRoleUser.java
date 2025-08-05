package com.suven.framework.sys.entity;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

/**
  * @ClassName: SysDepartRoleUser.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:53:58
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: 部门角色用户表 数据库表对应的实现类
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
public class SysDepartRoleUser extends BaseTimeEntity{

private static final long serialVersionUID = 1L;


 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depart_role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long departRoleId;

 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;




    public static SysDepartRoleUser build(){
        return new SysDepartRoleUser();
    }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysDepartRoleUser toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		}
 		public SysDepartRoleUser toDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		 return this ;
 		}

 		public long getDepartRoleId(){
 		 		return this.departRoleId;
 		}
 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartRoleUser toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
}