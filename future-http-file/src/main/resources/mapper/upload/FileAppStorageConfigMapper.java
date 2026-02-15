package com.suven.framework.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.upload.entity.FileAppStorageConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
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
public interface FileAppStorageConfigMapper extends BaseMapper<FileAppStorageConfig> {


    final String SQL_INSERT = "INSERT INTO file_app_storage_config ( app_id, app_name, client_id, storage_config_id, config_filed_name, config_filed_value, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileAppStorageConfig.appId},  #{fileAppStorageConfig.appName},  #{fileAppStorageConfig.clientId},  #{fileAppStorageConfig.storageConfigId},  #{fileAppStorageConfig.configFiledName},  #{fileAppStorageConfig.configFiledValue},  #{fileAppStorageConfig.createDate},  #{fileAppStorageConfig.modifyDate},  #{fileAppStorageConfig.tenantId},  #{fileAppStorageConfig.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_app_storage_config (id, app_id, app_name, client_id, storage_config_id, config_filed_name, config_filed_value, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileAppStorageConfig.id},  #{fileAppStorageConfig.appId},  #{fileAppStorageConfig.appName},  #{fileAppStorageConfig.clientId},  #{fileAppStorageConfig.storageConfigId},  #{fileAppStorageConfig.configFiledName},  #{fileAppStorageConfig.configFiledValue},  #{fileAppStorageConfig.createDate},  #{fileAppStorageConfig.modifyDate},  #{fileAppStorageConfig.tenantId},  #{fileAppStorageConfig.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileAppStorageConfig")  FileAppStorageConfig fileAppStorageConfig);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileAppStorageConfig") FileAppStorageConfig fileAppStorageConfig);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileAppStorageConfig> fileAppStorageConfig);


	
}
