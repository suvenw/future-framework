package com.suven.framework.sys.dto.request;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysUserRoleRequestDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 17:00:29
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 RPC业务接口交互数据请求参数实现类
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


public class SysUserRoleRequestDto extends BaseTimeEntity implements Serializable{


        private Logger logger = LoggerFactory.getLogger(SysUserRoleRequestDto.class);


 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;




        public static SysUserRoleRequestDto build(){
            return new SysUserRoleRequestDto();
        }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserRoleRequestDto toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRoleRequestDto toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}




	


}
