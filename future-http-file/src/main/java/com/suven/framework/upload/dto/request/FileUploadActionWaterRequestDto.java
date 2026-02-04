package com.suven.framework.upload.dto.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:20:02
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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FileUploadActionWaterRequestDto extends BaseTenantEntity implements Serializable{




 		/** company_id 业务公司id  */
 		@ApiDesc(value = "业务公司id", required = 0)
 		private String companyId;

 		/** company_name 业务公司名称  */
 		@ApiDesc(value = "业务公司名称", required = 0)
 		private String companyName;

 		/** dept_id 业务公司人员的部门id  */
 		@ApiDesc(value = "业务公司人员的部门id", required = 0)
 		private long deptId;

 		/** dept_name 业务公司人员的部门名称  */
 		@ApiDesc(value = "业务公司人员的部门名称", required = 0)
 		private String deptName;

 		/** upload_user_id 上传人员的id  */
 		@ApiDesc(value = "上传人员的id", required = 0)
 		private long uploadUserId;

 		/** upload_user_name 上传人员的名称  */
 		@ApiDesc(value = "上传人员的名称", required = 0)
 		private String uploadUserName;

 		/** file_product_name 上传业务产品名称  */
 		@ApiDesc(value = "上传业务产品名称", required = 0)
 		private String fileProductName;

 		/** file_business_name 上传业务名称  */
 		@ApiDesc(value = "上传业务名称", required = 0)
 		private String fileBusinessName;

 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		private String appId;

 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		private long clientId;

 		/** use_business_id 使用业务Id  */
 		@ApiDesc(value = "使用业务Id", required = 0)
 		private long useBusinessId;

 		/** storage_config_id 注册应用授权Id,对应 FileStorageEnum  */
 		@ApiDesc(value = "注册应用授权Id,对应 FileStorageEnum", required = 0)
 		private String storageConfigId;

 		/** file_upload_storage_id 文件存储信息id  */
 		@ApiDesc(value = "文件存储信息id", required = 0)
 		private long fileUploadStorageId;

 		/** file_source_name 文件名称,原来文件上传的名称  */
 		@ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
 		private String fileSourceName;

 		/** file_oss_name 文件名称,存储到中间件名称,eg, abc.jpg   */
 		@ApiDesc(value = "文件名称,存储到中间件名称,eg, abc.jpg ", required = 0)
 		private String fileOssName;






        public static FileUploadActionWaterRequestDto build(){
            return new FileUploadActionWaterRequestDto();
        }


 		public FileUploadActionWaterRequestDto toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toCompanyName( String companyName){
 		 		this.companyName = companyName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toDeptId( long deptId){
 		 		this.deptId = deptId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toDeptName( String deptName){
 		 		this.deptName = deptName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toUploadUserId( long uploadUserId){
 		 		this.uploadUserId = uploadUserId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toUploadUserName( String uploadUserName){
 		 		this.uploadUserName = uploadUserName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toFileProductName( String fileProductName){
 		 		this.fileProductName = fileProductName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toFileBusinessName( String fileBusinessName){
 		 		this.fileBusinessName = fileBusinessName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toClientId( long clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toUseBusinessId( long useBusinessId){
 		 		this.useBusinessId = useBusinessId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toFileUploadStorageId( long fileUploadStorageId){
 		 		this.fileUploadStorageId = fileUploadStorageId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toFileSourceName( String fileSourceName){
 		 		this.fileSourceName = fileSourceName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterRequestDto toFileOssName( String fileOssName){
 		 		this.fileOssName = fileOssName ; 
 		 		 return this ;
 		}




	


}
