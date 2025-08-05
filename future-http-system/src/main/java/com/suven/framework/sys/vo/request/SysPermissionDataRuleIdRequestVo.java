package com.suven.framework.sys.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;


/**
 * ClassName: SysPermissionDataRuleRequestVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:35
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 http业务接口交互数据请求参数实现类
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

public class SysPermissionDataRuleIdRequestVo extends RequestParserVo {





 		/** permission_id 菜单ID  */
 		@ApiDesc(value = "菜单ID", required = 0)
 		private long permissionId;






    public static SysPermissionDataRuleIdRequestVo build(){
        return new SysPermissionDataRuleIdRequestVo();
    }




 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysPermissionDataRuleIdRequestVo toPermissionId(long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}


}
