package com.suven.framework.sys.vo.request;


import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

/**
 * @ClassName: SysDepartRoleUserAddRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:53:58
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 http业务接口交互数据请求参数实现类
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


public class SysDepartRoleUserAddRequestVo extends HttpRequestByIdVo{




 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depart_role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long departRoleId;

 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;





    public static SysDepartRoleUserAddRequestVo build(){
        return new SysDepartRoleUserAddRequestVo();
    }




 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public SysDepartRoleUserAddRequestVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public void setDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		}

 		public long getDepartRoleId(){
 		 		return this.departRoleId;
 		}
 		public SysDepartRoleUserAddRequestVo toDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		 return this ;
 		}

 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}
 		public SysDepartRoleUserAddRequestVo toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDepartRoleUserAddRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDepartRoleUserAddRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}






}
