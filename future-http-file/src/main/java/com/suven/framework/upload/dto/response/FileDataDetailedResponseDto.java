package com.suven.framework.upload.dto.response;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.http.data.entity.BaseTenantEntity;
import com.suven.framework.http.api.ApiDesc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:20:28
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
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FileDataDetailedResponseDto  extends BaseTenantEntity implements Serializable {



 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		private String appId;

 		/** app_name 注册应用名称  */
 		@ApiDesc(value = "注册应用名称", required = 0)
 		private String appName;

 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		private long clientId;

 		/** company_id 业务公司id  */
 		@ApiDesc(value = "业务公司id", required = 0)
 		private String companyId;

 		/** company_name 业务公司名称  */
 		@ApiDesc(value = "业务公司名称", required = 0)
 		private String companyName;

 		/** file_product_name 上传业务产品名称  */
 		@ApiDesc(value = "上传业务产品名称", required = 0)
 		private String fileProductName;

 		/** file_business_name 上传业务名称  */
 		@ApiDesc(value = "上传业务名称", required = 0)
 		private String fileBusinessName;

 		/** use_business_id 使用业务Id  */
 		@ApiDesc(value = "使用业务Id", required = 0)
 		private long useBusinessId;

 		/** storage_config_id 注册应用授权Id,对应 FileStorageEnum  */
 		@ApiDesc(value = "注册应用授权Id,对应 FileStorageEnum", required = 0)
 		private String storageConfigId;

 		/** file_source_name 文件名称,原来文件上传的名称  */
 		@ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
 		private String fileSourceName;

 		/** file_type 文件名称类型:txt, xml,jpg 等  */
 		@ApiDesc(value = "文件名称类型:txt, xml,jpg 等", required = 0)
 		private String fileType;

 		/** error_code 检查数据错误提示编码  */
 		@ApiDesc(value = "检查数据错误提示编码", required = 0)
 		private String errorCode;

 		/** error_message 检查数据错误提示信息  */
 		@ApiDesc(value = "检查数据错误提示信息", required = 0)
 		private String errorMessage;






        public static FileDataDetailedResponseDto build(){
                return new FileDataDetailedResponseDto();
        }

 		public FileDataDetailedResponseDto toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toAppName( String appName){
 		 		this.appName = appName ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toClientId( long clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toCompanyName( String companyName){
 		 		this.companyName = companyName ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toFileProductName( String fileProductName){
 		 		this.fileProductName = fileProductName ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toFileBusinessName( String fileBusinessName){
 		 		this.fileBusinessName = fileBusinessName ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toUseBusinessId( long useBusinessId){
 		 		this.useBusinessId = useBusinessId ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toFileSourceName( String fileSourceName){
 		 		this.fileSourceName = fileSourceName ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toFileType( String fileType){
 		 		this.fileType = fileType ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toErrorCode( String errorCode){
 		 		this.errorCode = errorCode ; 
 		 		 return this ;
 		}
 		public FileDataDetailedResponseDto toErrorMessage( String errorMessage){
 		 		this.errorMessage = errorMessage ; 
 		 		 return this ;
 		}







}
