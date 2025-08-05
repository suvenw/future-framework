package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysLogService;
import com.suven.framework.sys.dto.request.SysLogRequestDto;
import com.suven.framework.sys.dto.response.SysLogResponseDto;
import com.suven.framework.sys.vo.request.SysLogRequestVo;
import com.suven.framework.sys.vo.response.SysLogResponseVo;




/**
  * ClassName: SysLogFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:10:19
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 系统日志表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysLog")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysLogFacade {

	@Autowired
	private SysLogService  sysLogService;

	



}
