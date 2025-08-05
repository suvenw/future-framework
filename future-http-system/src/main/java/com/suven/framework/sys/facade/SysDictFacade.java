package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysDictService;
import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.vo.request.SysDictRequestVo;
import com.suven.framework.sys.vo.response.SysDictResponseVo;




/**
  * ClassName: SysDictFacade.java
  *
  * @author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * CreateDate 创建时间: 2022-02-28 16:10:09
  * @version 版本: v1.0.0
  * <pre>
  *
  *  Description: 后台字典类型表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * RequestMapping("/sys/sysDict")
  * </pre>
  * Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysDictFacade {

	@Autowired
	private SysDictService  sysDictService;

	



}
