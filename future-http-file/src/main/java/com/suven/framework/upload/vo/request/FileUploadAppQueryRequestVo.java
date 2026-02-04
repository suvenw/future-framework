package com.suven.framework.upload.vo.request;


import java.io.Serializable;
import java.util.Date;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FileUploadAppQueryRequestVo extends HttpRequestByIdPageVo{




 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		private String appId;

 		/** app_name 注册应用名称  */
 		@ApiDesc(value = "注册应用名称", required = 0)
 		private String appName;

 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		private String clientId;

 		/** client_secret 注册应用授权密匙  */
 		@ApiDesc(value = "注册应用授权密匙", required = 0)
 		private String clientSecret;

 		/** client_salt 注册应用授权加密干扰盐  */
 		@ApiDesc(value = "注册应用授权加密干扰盐", required = 0)
 		private String clientSalt;

 		/** path_name 注册应用业务前缀path,用于区别业务  */
 		@ApiDesc(value = "注册应用业务前缀path,用于区别业务", required = 0)
 		private String pathName;

 		/** client_url 注册应用回调地址  */
 		@ApiDesc(value = "注册应用回调地址", required = 0)
 		private String clientUrl;

 		/** access_expiration_time 有效时间:当前时间+毫秒数  */
 		@ApiDesc(value = "有效时间:当前时间+毫秒数", required = 0)
 		private String accessExpirationTime;

 		/** access_count url授权可访问次数  */
 		@ApiDesc(value = "url授权可访问次数", required = 0)
 		private int accessCount;

 		/** access_url_format 生成临时访问url规则  */
 		@ApiDesc(value = "生成临时访问url规则", required = 0)
 		private String accessUrlFormat;

 		/** access_url 生成临时访问url  */
 		@ApiDesc(value = "生成临时访问url", required = 0)
 		private String accessUrl;

 		/** file_app_storage_config_id 文件存储配置id  */
 		@ApiDesc(value = "文件存储配置id", required = 0)
 		private long fileAppStorageConfigId;

 		/** endpoint 节点地址,默认值  */
 		@ApiDesc(value = "节点地址,默认值", required = 0)
 		private String endpoint;

 		/** bucket bucket,默认值  */
 		@ApiDesc(value = "bucket,默认值", required = 0)
 		private String bucket;

 		/** access_key accessKey 不能为空,默认值  */
 		@ApiDesc(value = "accessKey 不能为空,默认值", required = 0)
 		private String accessKey;

 		/** access_secret accessSecret 不能为空,默认值  */
 		@ApiDesc(value = "accessSecret 不能为空,默认值", required = 0)
 		private String accessSecret;

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




    public static FileUploadAppQueryRequestVo build(){
        return new FileUploadAppQueryRequestVo();
    }




 		public FileUploadAppQueryRequestVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAppName( String appName){
 		 		this.appName = appName ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toClientSecret( String clientSecret){
 		 		this.clientSecret = clientSecret ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toClientSalt( String clientSalt){
 		 		this.clientSalt = clientSalt ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toPathName( String pathName){
 		 		this.pathName = pathName ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toClientUrl( String clientUrl){
 		 		this.clientUrl = clientUrl ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessExpirationTime( String accessExpirationTime){
 		 		this.accessExpirationTime = accessExpirationTime ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessCount( int accessCount){
 		 		this.accessCount = accessCount ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessUrlFormat( String accessUrlFormat){
 		 		this.accessUrlFormat = accessUrlFormat ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessUrl( String accessUrl){
 		 		this.accessUrl = accessUrl ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toFileAppStorageConfigId( long fileAppStorageConfigId){
 		 		this.fileAppStorageConfigId = fileAppStorageConfigId ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toEndpoint( String endpoint){
 		 		this.endpoint = endpoint ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toBucket( String bucket){
 		 		this.bucket = bucket ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessKey( String accessKey){
 		 		this.accessKey = accessKey ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toAccessSecret( String accessSecret){
 		 		this.accessSecret = accessSecret ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toCreateDate( Date createDate){
 		 		this.createDate = createDate ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toModifyDate( Date modifyDate){
 		 		this.modifyDate = modifyDate ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toTenantId( long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}


 		public FileUploadAppQueryRequestVo toDeleted( int deleted){
 		 		this.deleted = deleted ; 
 		 		 return this ;
 		}





}
