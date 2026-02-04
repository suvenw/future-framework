package com.suven.framework.fileinter.mapper;

import com.suven.framework.fileinter.entity.FileUploadStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-18 23:55:18
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
public interface FileUploadStorageMapper extends BaseMapper<FileUploadStorage> {


    final String SQL_INSERT = "INSERT INTO file_upload_storage ( idempotent, app_id, client_id, use_business_id, storage_config_id, file_source_name, file_md5, file_type, file_size, bucket_name, oss_key, file_oss_name, file_oss_store_path, file_domain, file_access_url, access_expiration_time, access_count, access_url_format, callback_validate, interpret_data, interpret_data_total, file_history, file_history_store_path, remark, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileUploadStorage.idempotent},  #{fileUploadStorage.appId},  #{fileUploadStorage.clientId},  #{fileUploadStorage.useBusinessId},  #{fileUploadStorage.storageConfigId},  #{fileUploadStorage.fileSourceName},  #{fileUploadStorage.fileMd5},  #{fileUploadStorage.fileType},  #{fileUploadStorage.fileSize},  #{fileUploadStorage.bucketName},  #{fileUploadStorage.ossKey},  #{fileUploadStorage.fileOssName},  #{fileUploadStorage.fileOssStorePath},  #{fileUploadStorage.fileDomain},  #{fileUploadStorage.fileAccessUrl},  #{fileUploadStorage.accessExpirationTime},  #{fileUploadStorage.accessCount},  #{fileUploadStorage.accessUrlFormat},  #{fileUploadStorage.callbackValidate},  #{fileUploadStorage.interpretData},  #{fileUploadStorage.interpretDataTotal},  #{fileUploadStorage.fileHistory},  #{fileUploadStorage.fileHistoryStorePath},  #{fileUploadStorage.remark},  #{fileUploadStorage.createDate},  #{fileUploadStorage.modifyDate},  #{fileUploadStorage.tenantId},  #{fileUploadStorage.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_upload_storage (id, idempotent, app_id, client_id, use_business_id, storage_config_id, file_source_name, file_md5, file_type, file_size, bucket_name, oss_key, file_oss_name, file_oss_store_path, file_domain, file_access_url, access_expiration_time, access_count, access_url_format, callback_validate, interpret_data, interpret_data_total, file_history, file_history_store_path, remark, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileUploadStorage.id},  #{fileUploadStorage.idempotent},  #{fileUploadStorage.appId},  #{fileUploadStorage.clientId},  #{fileUploadStorage.useBusinessId},  #{fileUploadStorage.storageConfigId},  #{fileUploadStorage.fileSourceName},  #{fileUploadStorage.fileMd5},  #{fileUploadStorage.fileType},  #{fileUploadStorage.fileSize},  #{fileUploadStorage.bucketName},  #{fileUploadStorage.ossKey},  #{fileUploadStorage.fileOssName},  #{fileUploadStorage.fileOssStorePath},  #{fileUploadStorage.fileDomain},  #{fileUploadStorage.fileAccessUrl},  #{fileUploadStorage.accessExpirationTime},  #{fileUploadStorage.accessCount},  #{fileUploadStorage.accessUrlFormat},  #{fileUploadStorage.callbackValidate},  #{fileUploadStorage.interpretData},  #{fileUploadStorage.interpretDataTotal},  #{fileUploadStorage.fileHistory},  #{fileUploadStorage.fileHistoryStorePath},  #{fileUploadStorage.remark},  #{fileUploadStorage.createDate},  #{fileUploadStorage.modifyDate},  #{fileUploadStorage.tenantId},  #{fileUploadStorage.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileUploadStorage")  FileUploadStorage fileUploadStorage);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileUploadStorage") FileUploadStorage fileUploadStorage);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileUploadStorage> fileUploadStorage);


	
}
