package com.suven.framework.sys.dto.request;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * @ClassName: SysDepartRoleUserRequestDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:53:58
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 RPC业务接口交互数据请求参数实现类
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


public class SysDepartRoleUserRequestDto extends BaseTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysDepartRoleUserRequestDto.class);


 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depart_role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long departRoleId;

 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;




        public static SysDepartRoleUserRequestDto build(){
            return new SysDepartRoleUserRequestDto();
        }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysDepartRoleUserRequestDto toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		}
 		public SysDepartRoleUserRequestDto toDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		 return this ;
 		}

 		public long getDepartRoleId(){
 		 		return this.departRoleId;
 		}
 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartRoleUserRequestDto toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}




	


}
