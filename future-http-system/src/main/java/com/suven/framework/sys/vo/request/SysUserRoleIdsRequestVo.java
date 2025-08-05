package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.List;


/**
 * @ClassName: SysUserRoleRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 17:00:29
 * @Version 版本: v1.0.0
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

public class SysUserRoleIdsRequestVo extends RequestParserVo {



		@ApiDesc(value= "用户id的主键列表聚合,使用char k = 7,分隔的字符口串; ",required = 1)
		private List<Long> userIdList;

 		/** role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 1)
 		private long roleId;







    public static SysUserRoleIdsRequestVo build(){
        return new SysUserRoleIdsRequestVo();
    }


	public List<Long> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}

	public SysUserRoleIdsRequestVo toUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
		return this;
	}

	public void setRoleId(long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRoleIdsRequestVo toRoleId(long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}






}
