package com.suven.framework.sys.vo.request;


import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.List;

/**
* @ClassName: SysRolePermissionRequestVo.java
* Description: 角色权限表的数据交互处理类
* @author xxx.xxx
* date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysRolePermissionSaveRequestVo extends RequestParserVo {


	private List<Long> lastpermissionIds;


	private List<Long> permissionIds;

	private long roleId;

	public List<Long> getLastpermissionIds() {
		return lastpermissionIds;
	}

	public void setLastpermissionIds(List<Long> lastpermissionIds) {
		this.lastpermissionIds = lastpermissionIds;
	}

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}
