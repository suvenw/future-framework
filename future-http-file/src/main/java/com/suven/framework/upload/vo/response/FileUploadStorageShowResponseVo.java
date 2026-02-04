package com.suven.framework.upload.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseTenantEntity;
import com.suven.framework.http.data.entity.BaseIdEntity;
import com.suven.framework.http.api.ApiDesc;
import com.alibaba.excel.annotation.ExcelProperty;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-18 23:55:18
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

public class FileUploadStorageShowResponseVo extends BaseIdEntity  implements Serializable {



 		/** idempotent   */
 		@ApiDesc(value = "", required = 0)
 		private long idempotent;
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
 		/** file_source_name 文件名称,原来文件上传的名称  */
 		@ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
 		private String fileSourceName;
 		/** file_md5 文件md5字节流,长度大于128,建议使用前128个节,否则前部进行md5加密,防止文件重复上传  */
 		@ApiDesc(value = "文件md5字节流,长度大于128,建议使用前128个节,否则前部进行md5加密,防止文件重复上传", required = 0)
 		private String fileMd5;
 		/** file_type 文件名称类型:txt, xml,jpg 等  */
 		@ApiDesc(value = "文件名称类型:txt, xml,jpg 等", required = 0)
 		private String fileType;
 		/** file_size 文件大小  */
 		@ApiDesc(value = "文件大小", required = 0)
 		private long fileSize;
 		/** bucket_name 文件存储的桶的名称  */
 		@ApiDesc(value = "文件存储的桶的名称", required = 0)
 		private String bucketName;
 		/** oss_key 文件存储的桶的key  */
 		@ApiDesc(value = "文件存储的桶的key", required = 0)
 		private String ossKey;
 		/** file_oss_name 文件名称,存储到中间件名称,eg, abc.jpg   */
 		@ApiDesc(value = "文件名称,存储到中间件名称,eg, abc.jpg ", required = 0)
 		private String fileOssName;
 		/** file_oss_store_path 文件名称,存储到中间件名称  */
 		@ApiDesc(value = "文件名称,存储到中间件名称", required = 0)
 		private String fileOssStorePath;
 		/** file_domain 文件访问代理域名前缀 https://file.aiin.vip  */
 		@ApiDesc(value = "文件访问代理域名前缀 https://file.aiin.vip", required = 0)
 		private String fileDomain;
 		/** file_access_url 文件访问完整域名,由 fileDomain+fileOssStorePath; == https://file.aiin.vip/img/abc.jpg  */
 		@ApiDesc(value = "文件访问完整域名,由 fileDomain+fileOssStorePath; == https://file.aiin.vip/img/abc.jpg", required = 0)
 		private String fileAccessUrl;
 		/** access_expiration_time 有效时间:当前时间+毫秒数,默认按app business中,也可以独立配置,优先考虑个性设置  */
 		@ApiDesc(value = "有效时间:当前时间+毫秒数,默认按app business中,也可以独立配置,优先考虑个性设置", required = 0)
 		private String accessExpirationTime;
 		/** access_count url授权可访问次数,默认按app business中,也可以独立配置,优先考虑个性设置  */
 		@ApiDesc(value = "url授权可访问次数,默认按app business中,也可以独立配置,优先考虑个性设置", required = 0)
 		private int accessCount;
 		/** access_url_format 生成临时访问url规则  */
 		@ApiDesc(value = "生成临时访问url规则", required = 0)
 		private String accessUrlFormat;
 		/** callback_validate 请求接口,回调返回的参数字符串,用于回调业务安全验证  */
 		@ApiDesc(value = "请求接口,回调返回的参数字符串,用于回调业务安全验证", required = 0)
 		private String callbackValidate;
 		/** interpret_data 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否  */
 		@ApiDesc(value = "是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否", required = 0)
 		private int interpretData;
 		/** interpret_data_total interpretData 为1时有时,默认为0,对应存储到mongodb记录总条数据  */
 		@ApiDesc(value = "interpretData 为1时有时,默认为0,对应存储到mongodb记录总条数据", required = 0)
 		private int interpretDataTotal;
 		/** file_history 是否将修改验证后的正确数据,生成文件替换历史文件,0.未替换,1.替换,对应fileHistoryStorePath存历史url,其它对应字段为新文件信息  */
 		@ApiDesc(value = "是否将修改验证后的正确数据,生成文件替换历史文件,0.未替换,1.替换,对应fileHistoryStorePath存历史url,其它对应字段为新文件信息", required = 0)
 		private int fileHistory;
 		/** file_history_store_path 是否将修改验证后的正确数据,生成文件替换历史文件  */
 		@ApiDesc(value = "是否将修改验证后的正确数据,生成文件替换历史文件", required = 0)
 		private String fileHistoryStorePath;
 		/** remark 备注  */
 		@ApiDesc(value = "备注", required = 0)
 		private String remark;
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

    public static FileUploadStorageShowResponseVo build(){
        return new FileUploadStorageShowResponseVo();
    }
 		public FileUploadStorageShowResponseVo toIdempotent( long idempotent){
 		 		this.idempotent = idempotent ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toClientId( long clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toUseBusinessId( long useBusinessId){
 		 		this.useBusinessId = useBusinessId ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileSourceName( String fileSourceName){
 		 		this.fileSourceName = fileSourceName ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileMd5( String fileMd5){
 		 		this.fileMd5 = fileMd5 ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileType( String fileType){
 		 		this.fileType = fileType ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileSize( long fileSize){
 		 		this.fileSize = fileSize ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toBucketName( String bucketName){
 		 		this.bucketName = bucketName ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toOssKey( String ossKey){
 		 		this.ossKey = ossKey ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileOssName( String fileOssName){
 		 		this.fileOssName = fileOssName ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileOssStorePath( String fileOssStorePath){
 		 		this.fileOssStorePath = fileOssStorePath ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileDomain( String fileDomain){
 		 		this.fileDomain = fileDomain ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileAccessUrl( String fileAccessUrl){
 		 		this.fileAccessUrl = fileAccessUrl ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toAccessExpirationTime( String accessExpirationTime){
 		 		this.accessExpirationTime = accessExpirationTime ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toAccessCount( int accessCount){
 		 		this.accessCount = accessCount ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toAccessUrlFormat( String accessUrlFormat){
 		 		this.accessUrlFormat = accessUrlFormat ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toCallbackValidate( String callbackValidate){
 		 		this.callbackValidate = callbackValidate ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toInterpretData( int interpretData){
 		 		this.interpretData = interpretData ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toInterpretDataTotal( int interpretDataTotal){
 		 		this.interpretDataTotal = interpretDataTotal ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileHistory( int fileHistory){
 		 		this.fileHistory = fileHistory ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toFileHistoryStorePath( String fileHistoryStorePath){
 		 		this.fileHistoryStorePath = fileHistoryStorePath ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toRemark( String remark){
 		 		this.remark = remark ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toCreateDate( Date createDate){
 		 		this.createDate = createDate ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toModifyDate( Date modifyDate){
 		 		this.modifyDate = modifyDate ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toTenantId( long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}
 		public FileUploadStorageShowResponseVo toDeleted( int deleted){
 		 		this.deleted = deleted ; 
 		 		 return this ;
 		}





}
