package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;

import java.util.Date;


/**
 * ClassName: SysUserRoleQueryRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 17:00:29
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 http业务接口交互数据请求参数实现类
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

public class SysUserIdQueryRequestVo extends HttpRequestByIdPageVo{




 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;




    public static SysUserIdQueryRequestVo build(){
        return new SysUserIdQueryRequestVo();
    }



 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public SysUserIdQueryRequestVo toUserId(long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
 		public SysUserIdQueryRequestVo toRoleId(long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysUserIdQueryRequestVo toCreateTime(LocalDateTime createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysUserIdQueryRequestVo toUpdateTime(LocalDateTime updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}





}
