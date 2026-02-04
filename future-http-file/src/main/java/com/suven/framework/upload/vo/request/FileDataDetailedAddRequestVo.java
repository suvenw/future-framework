package com.suven.framework.upload.vo.request;


import java.io.Serializable;
import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

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

public class FileDataDetailedAddRequestVo extends HttpRequestByIdVo{




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

 		/** create_date 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createDate;

 		/** modify_date 修改时间  */
 		@ApiDesc(value = "修改时间", required = 0)
 		private Date modifyDate;

 		/** tenant_id 租户ID,租户主键值  */
 		@ApiDesc(value = "租户ID,租户主键值", required = 0)
 		private long tenantId;

 		/** deleted 租户ID,租户主键值  */
 		@ApiDesc(value = "租户ID,租户主键值", required = 0)
 		private int deleted;





    public static FileDataDetailedAddRequestVo build(){
        return new FileDataDetailedAddRequestVo();
    }




 		public FileDataDetailedAddRequestVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toAppName( String appName){
 		 		this.appName = appName ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toClientId( long clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toCompanyName( String companyName){
 		 		this.companyName = companyName ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toFileProductName( String fileProductName){
 		 		this.fileProductName = fileProductName ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toFileBusinessName( String fileBusinessName){
 		 		this.fileBusinessName = fileBusinessName ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toUseBusinessId( long useBusinessId){
 		 		this.useBusinessId = useBusinessId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toFileSourceName( String fileSourceName){
 		 		this.fileSourceName = fileSourceName ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toFileType( String fileType){
 		 		this.fileType = fileType ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toErrorCode( String errorCode){
 		 		this.errorCode = errorCode ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toErrorMessage( String errorMessage){
 		 		this.errorMessage = errorMessage ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toCreateDate( Date createDate){
 		 		this.createDate = createDate ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toModifyDate( Date modifyDate){
 		 		this.modifyDate = modifyDate ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toTenantId( long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}
 
 		public FileDataDetailedAddRequestVo toDeleted( int deleted){
 		 		this.deleted = deleted ; 
 		 		 return this ;
 		}
 





}
