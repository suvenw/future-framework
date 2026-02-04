package com.suven.framework.fileinter.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseTenantEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FileUploadActionWaterResponseVo  extends BaseTenantEntity implements Serializable {




 		/** company_id 业务公司id  */
 		@ApiDesc(value = "业务公司id", required = 0)
 		@ExcelProperty(value = "业务公司id")
 		private String companyId;

 		/** company_name 业务公司名称  */
 		@ApiDesc(value = "业务公司名称", required = 0)
 		@ExcelProperty(value = "业务公司名称")
 		private String companyName;

 		/** dept_id 业务公司人员的部门id  */
 		@ApiDesc(value = "业务公司人员的部门id", required = 0)
 		@ExcelProperty(value = "业务公司人员的部门id")
 		private long deptId;

 		/** dept_name 业务公司人员的部门名称  */
 		@ApiDesc(value = "业务公司人员的部门名称", required = 0)
 		@ExcelProperty(value = "业务公司人员的部门名称")
 		private String deptName;

 		/** upload_user_id 上传人员的id  */
 		@ApiDesc(value = "上传人员的id", required = 0)
 		@ExcelProperty(value = "上传人员的id")
 		private long uploadUserId;

 		/** upload_user_name 上传人员的名称  */
 		@ApiDesc(value = "上传人员的名称", required = 0)
 		@ExcelProperty(value = "上传人员的名称")
 		private String uploadUserName;

 		/** file_product_name 上传业务产品名称  */
 		@ApiDesc(value = "上传业务产品名称", required = 0)
 		@ExcelProperty(value = "上传业务产品名称")
 		private String fileProductName;

 		/** file_business_name 上传业务名称  */
 		@ApiDesc(value = "上传业务名称", required = 0)
 		@ExcelProperty(value = "上传业务名称")
 		private String fileBusinessName;

 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		@ExcelProperty(value = "注册应用id")
 		private String appId;

 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		@ExcelProperty(value = "注册应用授权Id")
 		private long clientId;

 		/** use_business_id 使用业务Id  */
 		@ApiDesc(value = "使用业务Id", required = 0)
 		@ExcelProperty(value = "使用业务Id")
 		private long useBusinessId;

 		/** storage_config_id 注册应用授权Id,对应 FileStorageEnum  */
 		@ApiDesc(value = "注册应用授权Id,对应 FileStorageEnum", required = 0)
 		@ExcelProperty(value = "注册应用授权Id,对应 FileStorageEnum")
 		private String storageConfigId;

 		/** file_upload_storage_id 文件存储信息id  */
 		@ApiDesc(value = "文件存储信息id", required = 0)
 		@ExcelProperty(value = "文件存储信息id")
 		private long fileUploadStorageId;

 		/** file_source_name 文件名称,原来文件上传的名称  */
 		@ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
 		@ExcelProperty(value = "文件名称,原来文件上传的名称")
 		private String fileSourceName;

 		/** file_oss_name 文件名称,存储到中间件名称,eg, abc.jpg   */
 		@ApiDesc(value = "文件名称,存储到中间件名称,eg, abc.jpg ", required = 0)
 		@ExcelProperty(value = "文件名称,存储到中间件名称,eg, abc.jpg ")
 		private String fileOssName;






    public static FileUploadActionWaterResponseVo build(){
        return new FileUploadActionWaterResponseVo();
    }


 		public FileUploadActionWaterResponseVo toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toCompanyName( String companyName){
 		 		this.companyName = companyName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toDeptId( long deptId){
 		 		this.deptId = deptId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toDeptName( String deptName){
 		 		this.deptName = deptName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toUploadUserId( long uploadUserId){
 		 		this.uploadUserId = uploadUserId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toUploadUserName( String uploadUserName){
 		 		this.uploadUserName = uploadUserName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toFileProductName( String fileProductName){
 		 		this.fileProductName = fileProductName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toFileBusinessName( String fileBusinessName){
 		 		this.fileBusinessName = fileBusinessName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toClientId( long clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toUseBusinessId( long useBusinessId){
 		 		this.useBusinessId = useBusinessId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toFileUploadStorageId( long fileUploadStorageId){
 		 		this.fileUploadStorageId = fileUploadStorageId ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toFileSourceName( String fileSourceName){
 		 		this.fileSourceName = fileSourceName ; 
 		 		 return this ;
 		}

 		public FileUploadActionWaterResponseVo toFileOssName( String fileOssName){
 		 		this.fileOssName = fileOssName ; 
 		 		 return this ;
 		}











}
