package com.suven.framework.upload.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpFromRequestVo;
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

public class FileUploadCreateRequestVo extends HttpFromRequestVo {


	/** app_name 注册应用名称  */
	@ApiDesc(value = "注册应用名称", required = 0)
	private String appName;
	/** 业务公司id **/
	private String  companyId;
	/** 业务公司名称 **/
	private String companyName;
	/** 上传业务产品名称 **/
	@ApiDesc(value = "上传业务产品名称", required = 0)
	private String fileProductName;
	/** 上传业务名称 **/
	@ApiDesc(value = "上传业务名称", required = 0)
	private String fileBusinessName;
	/** 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式; **/
	@ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式;", required = 0)
	private String   callbackService;
	@ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方;", required = 0)
	/** 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方式; **/
	private String   callbackAsyncService;


	/** 回调数据格式, 如何为空时,post json 请求,否则to map<key,Value> 结果处理数据 **/
	@ApiDesc(value = "回调数据格式, 如何为空时,post json 请求,否则to map<key,Value> 结果处理数据 ", required = 0)
	private String dataForm;
	/** 回调数据格式 post/get **/
	@ApiDesc(value = "回调数据格式 post/get", required = 0)
	private String formType;

	/** 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中**/
	@ApiDesc(value = "是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中", required = 0)
	private int interpretData;
	//=== 数据 回调业务方 == //
	/** 这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要 **/
	@ApiDesc(value = "这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要", required = 0)
	private int checkData;

	@ApiDesc(value = "有效时间:当前时间+毫秒数,默认按app设计,也可以独立配置", required = 0)
	private String accessExpirationTime; //有效时间
	@ApiDesc(value = "url授权可访问次数,默认按app设计,也可以独立配置", required = 0)
	private int accessCount; // 可访问次数
	//sourcepath = http://app.img.abc.jpg, aesUrl = http://app.img.key.jpg
	//aes format = key=appId+clientId+clientSalt+time+@abc,
	@ApiDesc(value = "生成临时访问url规则", required = 0)
	private String accessUrlFormat;//

	/** path_name 注册应用业务前缀path,用于区别业务  */
	@ApiDesc(value = "注册应用业务前缀path,用于区别业务", required = 0)
	private String pathName;

	/** client_url 注册应用回调地址  */
	@ApiDesc(value = "注册应用回调地址", required = 0)
	private String clientUrl;


	/** access_url 生成临时访问url  */
	@ApiDesc(value = "生成临时访问url", required = 0)
	private String accessUrl;

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


	/** tenant_id 租户ID,租户主键值  */
	@ApiDesc(value = "租户ID,租户主键值", required = 0)
	private long tenantId;






    public static FileUploadCreateRequestVo build(){
        return new FileUploadCreateRequestVo();
    }





 		public FileUploadCreateRequestVo toAppName(String appName){
 		 		this.appName = appName ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toPathName(String pathName){
 		 		this.pathName = pathName ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toClientUrl(String clientUrl){
 		 		this.clientUrl = clientUrl ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessExpirationTime(String accessExpirationTime){
 		 		this.accessExpirationTime = accessExpirationTime ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessCount(int accessCount){
 		 		this.accessCount = accessCount ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessUrlFormat(String accessUrlFormat){
 		 		this.accessUrlFormat = accessUrlFormat ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessUrl(String accessUrl){
 		 		this.accessUrl = accessUrl ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toEndpoint(String endpoint){
 		 		this.endpoint = endpoint ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toBucket(String bucket){
 		 		this.bucket = bucket ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessKey(String accessKey){
 		 		this.accessKey = accessKey ; 
 		 		 return this ;
 		}


 		public FileUploadCreateRequestVo toAccessSecret(String accessSecret){
 		 		this.accessSecret = accessSecret ; 
 		 		 return this ;
 		}




 		public FileUploadCreateRequestVo toTenantId(long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}







}
