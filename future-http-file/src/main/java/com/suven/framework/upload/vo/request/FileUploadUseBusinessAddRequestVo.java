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
 *  date 创建时间: 2024-04-19 00:21:42
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

public class FileUploadUseBusinessAddRequestVo extends HttpRequestByIdVo{




 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		private String appId;

 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		private String clientId;

 		/** company_id   */
 		@ApiDesc(value = "", required = 0)
 		private String companyId;

 		/** company_name   */
 		@ApiDesc(value = "", required = 0)
 		private String companyName;

 		/** file_product_name 上传业务产品名称  */
 		@ApiDesc(value = "上传业务产品名称", required = 0)
 		private String fileProductName;

 		/** file_business_name 上传业务名称  */
 		@ApiDesc(value = "上传业务名称", required = 0)
 		private String fileBusinessName;

 		/** callback_service 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式;  */
 		@ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式;", required = 0)
 		private String callbackService;

 		/** callback_async_service 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方;  */
 		@ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方;", required = 0)
 		private String callbackAsyncService;

 		/** data_form 回调数据格式, 如何为空时,post json 请求,否则to map<key,value> 结果处理数据 </key,value>  */
 		@ApiDesc(value = "回调数据格式, 如何为空时,post json 请求,否则to map<key,value> 结果处理数据 </key,value>", required = 0)
 		private String dataForm;

 		/** form_type 回调数据格式 post/get  */
 		@ApiDesc(value = "回调数据格式 post/get", required = 0)
 		private String formType;

 		/** interpret_data 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中  */
 		@ApiDesc(value = "是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中", required = 0)
 		private int interpretData;

 		/** check_data 这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要  */
 		@ApiDesc(value = "这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要", required = 0)
 		private int checkData;

 		/** access_expiration_time 有效时间:当前时间+毫秒数,默认按app设计,也可以独立配置  */
 		@ApiDesc(value = "有效时间:当前时间+毫秒数,默认按app设计,也可以独立配置", required = 0)
 		private String accessExpirationTime;

 		/** access_count url授权可访问次数,默认按app设计,也可以独立配置  */
 		@ApiDesc(value = "url授权可访问次数,默认按app设计,也可以独立配置", required = 0)
 		private int accessCount;

 		/** access_url_format 生成临时访问url规则  */
 		@ApiDesc(value = "生成临时访问url规则", required = 0)
 		private String accessUrlFormat;

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





    public static FileUploadUseBusinessAddRequestVo build(){
        return new FileUploadUseBusinessAddRequestVo();
    }




 		public FileUploadUseBusinessAddRequestVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCompanyId( String companyId){
 		 		this.companyId = companyId ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCompanyName( String companyName){
 		 		this.companyName = companyName ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toFileProductName( String fileProductName){
 		 		this.fileProductName = fileProductName ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toFileBusinessName( String fileBusinessName){
 		 		this.fileBusinessName = fileBusinessName ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCallbackService( String callbackService){
 		 		this.callbackService = callbackService ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCallbackAsyncService( String callbackAsyncService){
 		 		this.callbackAsyncService = callbackAsyncService ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toDataForm( String dataForm){
 		 		this.dataForm = dataForm ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toFormType( String formType){
 		 		this.formType = formType ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toInterpretData( int interpretData){
 		 		this.interpretData = interpretData ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCheckData( int checkData){
 		 		this.checkData = checkData ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toAccessExpirationTime( String accessExpirationTime){
 		 		this.accessExpirationTime = accessExpirationTime ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toAccessCount( int accessCount){
 		 		this.accessCount = accessCount ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toAccessUrlFormat( String accessUrlFormat){
 		 		this.accessUrlFormat = accessUrlFormat ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toCreateDate( Date createDate){
 		 		this.createDate = createDate ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toModifyDate( Date modifyDate){
 		 		this.modifyDate = modifyDate ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toTenantId( long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}
 
 		public FileUploadUseBusinessAddRequestVo toDeleted( int deleted){
 		 		this.deleted = deleted ; 
 		 		 return this ;
 		}
 





}
