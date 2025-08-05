package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysDepartRoleUserService;
import com.suven.framework.sys.dto.request.SysDepartRoleUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRoleUserResponseDto;
import com.suven.framework.sys.vo.request.SysDepartRoleUserRequestVo;
import com.suven.framework.sys.vo.response.SysDepartRoleUserResponseVo;




/**
  * ClassName: SysDepartRoleUserFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:14:21
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 部门角色用户表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysDepartRoleUser")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysDepartRoleUserFacade {

	@Autowired
	private SysDepartRoleUserService  sysDepartRoleUserService;

	



}
