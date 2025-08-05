package com.suven.framework.sys.dto.response;


import java.io.Serializable;

import com.suven.framework.http.data.entity.BaseTimeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.api.ApiDesc;

/**
 * ClassName: SysUserDepartResponseDto.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:14
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 RPC业务接口交互数据返回实现类
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

public class SysUserDepartResponseDto  extends BaseTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysUserDepartResponseDto.class);


 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** dep_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;




        public static SysUserDepartResponseDto build(){
                return new SysUserDepartResponseDto();
        }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepartResponseDto toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartResponseDto toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}







}
