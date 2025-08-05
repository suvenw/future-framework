package com.suven.framework.sys.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseBeanClone;
import com.suven.framework.http.api.ApiDesc;


/**
 * @ClassName: SysDepartRoleUserShowResponseVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:21
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 http业务接口交互数据返回参数实现类
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


public class SysDepartRoleUserShowResponseVo extends BaseBeanClone  implements Serializable {

   /** id 对应的业务表的主键id  */
    @ApiDesc(value = "对应的业务表的主键id ")
    private long id;

 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private String userId;
 		/** drole_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private String droleId;
 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;
 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;

    public static SysDepartRoleUserShowResponseVo build(){
        return new SysDepartRoleUserShowResponseVo();
    }
 		public void setUserId( String userId){
 		 		this.userId = userId ; 
 		 		}

 		public String getUserId(){
 		 		return this.userId;
 		}
 		public SysDepartRoleUserShowResponseVo toUserId( String userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}
 		public void setDroleId( String droleId){
 		 		this.droleId = droleId ; 
 		 		}

 		public String getDroleId(){
 		 		return this.droleId;
 		}
 		public SysDepartRoleUserShowResponseVo toDroleId( String droleId){
 		 		this.droleId = droleId ; 
 		 		 return this ;
 		}
 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDepartRoleUserShowResponseVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}
 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDepartRoleUserShowResponseVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
