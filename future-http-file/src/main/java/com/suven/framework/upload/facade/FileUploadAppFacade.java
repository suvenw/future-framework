package com.suven.framework.upload.facade;

import com.suven.framework.upload.service.FileAppStorageConfigService;
import com.suven.framework.upload.service.FileUploadAppService;
import com.suven.framework.upload.service.FileUploadUseBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:49
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
public class FileUploadAppFacade {

	@Autowired
	private FileUploadAppService  fileUploadAppService;

	@Autowired
	private FileAppStorageConfigService fileAppStorageConfigService;
	@Autowired
	private FileUploadUseBusinessService fileUploadUseBusinessService;


	/**
	 *  配置:
	 *  *  1.创建app应用,  FileUploadApp 表存储
	 *  *  2.每个应用支持多个存储空间配置; FileAppStorageConfig 表存储相关信息,与(1)的关系,多对1的关系,1个应用有多个配置存储空间信息
	 *  *  3.一个app应用,有多种场景业务,FileUploadUseBusiness 表存储相关信息, 与(1),(2)的关系,是多对1的关系;即一个应用有支持多种业务场景使用;
	 */

//	public boolean appByConfigAndBusiness(FileUploadCreateRequestVo uploadCreateRequestVo){
//		FileUploadAppRequestDto uploadAppRequestDto = FileUploadAppRequestDto.build().clone(uploadCreateRequestVo);
////		initUploadAppInfo(uploadAppRequestDto);
//
//
//
//		FileAppStorageConfigRequestDto appStorageConfigRequestDto = FileAppStorageConfigRequestDto.build().clone(uploadCreateRequestVo);
//		appStorageConfigRequestDto.setAppId(uploadAppRequestDto.getAppId());
//		appStorageConfigRequestDto.setClientId(uploadAppRequestDto.getClientId());
//
//		FileUploadUseBusinessRequestDto fileUploadUseBusiness = FileUploadUseBusinessRequestDto.build().clone(uploadCreateRequestVo);
//		fileUploadUseBusiness.setAppId(uploadAppRequestDto.getAppId());
//		fileUploadUseBusiness.setClientId(uploadAppRequestDto.getClientId());
//
//		fileUploadAppService.saveFileUploadApp(uploadAppRequestDto);
//		fileAppStorageConfigService.saveFileAppStorageConfig(appStorageConfigRequestDto);
//		fileUploadUseBusinessService.saveFileUploadUseBusiness(fileUploadUseBusiness);
//		return true;
//	}
	
//	private void initUploadAppInfo(FileUploadAppRequestDto uploadAppRequestDto){
//		List<Long> appIds = uploadAppRequestDto.initIds(4);
//		long id = appIds.get(0);
//		String appId = String.valueOf(appIds.get(1));
//		String clientId = String.valueOf(appIds.get(2));
//		String clientSalt = String.valueOf(appIds.get(3));
//		String clientSecret=  GUID.instance.getUUIDoffSpace();
//		uploadAppRequestDto.setId(id);
//		uploadAppRequestDto.setAppId(appId);
//		uploadAppRequestDto.setClientId(clientId);
//		uploadAppRequestDto.setClientSecret(clientSecret);
//		uploadAppRequestDto.setClientSalt(clientSalt);
//
//	}
//



}
