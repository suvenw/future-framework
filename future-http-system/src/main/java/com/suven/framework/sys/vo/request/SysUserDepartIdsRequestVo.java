package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.List;


/**
 * @ClassName: SysUserDepartRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:14
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 http业务接口交互数据请求参数实现类
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

public class SysUserDepartIdsRequestVo extends RequestParserVo {




 		/** dep_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;

	/** 用户id集合  */
	@ApiDesc(value = "用户id集合", required = 0)
	private List<Long> userIdList;





    public static SysUserDepartIdsRequestVo build(){
        return new SysUserDepartIdsRequestVo();
    }


	public List<Long> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}

	public void setDepId(long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartIdsRequestVo toDepId(long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}






}
