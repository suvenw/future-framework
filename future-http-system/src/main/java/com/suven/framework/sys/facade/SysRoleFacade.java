package com.suven.framework.sys.facade;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;
import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.vo.response.UserRoleVo;
import com.suven.framework.core.ObjectTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysRoleService;
import com.suven.framework.sys.dto.response.SysRoleResponseDto;

import java.util.ArrayList;
import java.util.List;


/**
  * ClassName: SysRoleFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:10:43
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 角色表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysRole")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysRoleFacade {

	@Autowired
	private SysRoleService  sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;



	public List<UserRoleVo> queryUserRole(long userId) {

		if (userId <= 0) {
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_CHECK);
		}
		SysUserRoleRequestDto sysUserRoleRequestDto = SysUserRoleRequestDto.build().toUserId(userId);
		List<SysUserRoleResponseDto> userRoles = sysUserRoleService.getSysUserRoleListByQuery(sysUserRoleRequestDto, SysUserRoleQueryEnum.USER_ID);
		if (ObjectTrue.isEmpty(userRoles)) {
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_USER_ROLE_FIND_FAIL);
		}
		List<Long> roleIds = new ArrayList<>();
		userRoles.forEach(u -> {
			roleIds.add(u.getRoleId());
		});
		//查询用户角色信息SysRoleResponseDto
		List<SysRoleResponseDto> roleList = sysRoleService.getSysDepartByIdList(roleIds);
		List<UserRoleVo> userRoleVos = new ArrayList<>();
		roleList.forEach(r -> {
			UserRoleVo vo = UserRoleVo.build();
			vo.setId(r.getId());
			vo.setLabel(r.getRoleName());
			userRoleVos.add(vo);
		});
		return userRoleVos;
	}

}
