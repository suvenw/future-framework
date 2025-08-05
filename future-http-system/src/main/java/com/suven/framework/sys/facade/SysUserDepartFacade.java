package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.vo.request.SysUserDepartRequestVo;
import com.suven.framework.sys.vo.response.SysUserDepartResponseVo;




/**
  * @ClassName: SysUserDepartFacade.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:14:14
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: 用户部门关系表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * @RequestMapping("/sys/sysUserDepart")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysUserDepartFacade {

	@Autowired
	private SysUserDepartService  sysUserDepartService;

	



}
