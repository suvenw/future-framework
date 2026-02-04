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

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FileAppStorageConfigShowResponseVo extends BaseIdEntity  implements Serializable {



 		/** app_id 注册应用id  */
 		@ApiDesc(value = "注册应用id", required = 0)
 		private String appId;
 		/** app_name 注册应用名称  */
 		@ApiDesc(value = "注册应用名称", required = 0)
 		private String appName;
 		/** client_id 注册应用授权Id  */
 		@ApiDesc(value = "注册应用授权Id", required = 0)
 		private String clientId;
 		/** storage_config_id 注册应用授权Id,对应FileStorageEnum  */
 		@ApiDesc(value = "注册应用授权Id,对应FileStorageEnum", required = 0)
 		private String storageConfigId;
 		/** config_filed_name 动态配置,业务自定义自段名;1.本地;basePath   */
 		@ApiDesc(value = "动态配置,业务自定义自段名;1.本地;basePath ", required = 0)
 		private String configFiledName;
 		/** config_filed_value 动态配置,业务自定义自段名值  */
 		@ApiDesc(value = "动态配置,业务自定义自段名值", required = 0)
 		private String configFiledValue;
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

    public static FileAppStorageConfigShowResponseVo build(){
        return new FileAppStorageConfigShowResponseVo();
    }
 		public FileAppStorageConfigShowResponseVo toAppId( String appId){
 		 		this.appId = appId ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toAppName( String appName){
 		 		this.appName = appName ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toClientId( String clientId){
 		 		this.clientId = clientId ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toStorageConfigId( String storageConfigId){
 		 		this.storageConfigId = storageConfigId ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toConfigFiledName( String configFiledName){
 		 		this.configFiledName = configFiledName ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toConfigFiledValue( String configFiledValue){
 		 		this.configFiledValue = configFiledValue ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toCreateDate( Date createDate){
 		 		this.createDate = createDate ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toModifyDate( Date modifyDate){
 		 		this.modifyDate = modifyDate ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toTenantId( long tenantId){
 		 		this.tenantId = tenantId ; 
 		 		 return this ;
 		}
 		public FileAppStorageConfigShowResponseVo toDeleted( int deleted){
 		 		this.deleted = deleted ; 
 		 		 return this ;
 		}





}
