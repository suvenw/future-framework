package com.suven.framework.fileinter.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.fileinter.service.FileAppStorageConfigService;
import com.suven.framework.fileinter.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.fileinter.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.fileinter.vo.request.FileAppStorageConfigRequestVo;
import com.suven.framework.fileinter.vo.response.FileAppStorageConfigResponseVo;




/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
 * <pre>
 *
 *  Description:  http业务接口交互数据请求参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@Component
public class FileAppStorageConfigFacade {

	@Autowired
	private FileAppStorageConfigService  fileAppStorageConfigService;

	



}
