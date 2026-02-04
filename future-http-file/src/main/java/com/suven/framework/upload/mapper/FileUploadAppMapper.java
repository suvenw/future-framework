package com.suven.framework.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.upload.entity.FileUploadApp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:49
 * <pre>
 *
 *  Description:  的数据库sql编写实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@Mapper
public interface FileUploadAppMapper extends BaseMapper<FileUploadApp> {


    final String SQL_INSERT = "INSERT INTO file_upload_app ( app_id, app_name, client_id, client_secret, client_salt, path_name, client_url, access_expiration_time, access_count, access_url_format, access_url, file_app_storage_config_id, endpoint, bucket, access_key, access_secret, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileUploadApp.appId},  #{fileUploadApp.appName},  #{fileUploadApp.clientId},  #{fileUploadApp.clientSecret},  #{fileUploadApp.clientSalt},  #{fileUploadApp.pathName},  #{fileUploadApp.clientUrl},  #{fileUploadApp.accessExpirationTime},  #{fileUploadApp.accessCount},  #{fileUploadApp.accessUrlFormat},  #{fileUploadApp.accessUrl},  #{fileUploadApp.fileAppStorageConfigId},  #{fileUploadApp.endpoint},  #{fileUploadApp.bucket},  #{fileUploadApp.accessKey},  #{fileUploadApp.accessSecret},  #{fileUploadApp.createDate},  #{fileUploadApp.modifyDate},  #{fileUploadApp.tenantId},  #{fileUploadApp.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_upload_app (id, app_id, app_name, client_id, client_secret, client_salt, path_name, client_url, access_expiration_time, access_count, access_url_format, access_url, file_app_storage_config_id, endpoint, bucket, access_key, access_secret, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileUploadApp.id},  #{fileUploadApp.appId},  #{fileUploadApp.appName},  #{fileUploadApp.clientId},  #{fileUploadApp.clientSecret},  #{fileUploadApp.clientSalt},  #{fileUploadApp.pathName},  #{fileUploadApp.clientUrl},  #{fileUploadApp.accessExpirationTime},  #{fileUploadApp.accessCount},  #{fileUploadApp.accessUrlFormat},  #{fileUploadApp.accessUrl},  #{fileUploadApp.fileAppStorageConfigId},  #{fileUploadApp.endpoint},  #{fileUploadApp.bucket},  #{fileUploadApp.accessKey},  #{fileUploadApp.accessSecret},  #{fileUploadApp.createDate},  #{fileUploadApp.modifyDate},  #{fileUploadApp.tenantId},  #{fileUploadApp.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileUploadApp")  FileUploadApp fileUploadApp);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileUploadApp") FileUploadApp fileUploadApp);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileUploadApp> fileUploadApp);


	
}
