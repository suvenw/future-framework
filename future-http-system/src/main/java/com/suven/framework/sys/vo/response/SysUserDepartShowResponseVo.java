package com.suven.framework.sys.vo.response;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTimeEntity;

import java.io.Serializable;


/**
 * ClassName: SysUserDepartShowResponseVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:14
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 http业务接口交互数据返回参数实现类
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


public class SysUserDepartShowResponseVo extends BaseTimeEntity implements Serializable {

   /** id 对应的业务表的主键id  */
    @ApiDesc(value = "对应的业务表的主键id ")
    private long id;

 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;
 		/** dep_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;


    public static SysUserDepartShowResponseVo build(){
        return new SysUserDepartShowResponseVo();
    }
 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public SysUserDepartShowResponseVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}
 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}

 		public long getDepId(){
 		 		return this.depId;
 		}
 		public SysUserDepartShowResponseVo toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
