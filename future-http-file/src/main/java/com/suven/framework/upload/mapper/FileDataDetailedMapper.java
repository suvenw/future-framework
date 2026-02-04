package com.suven.framework.upload.mapper;

import com.suven.framework.upload.entity.FileDataDetailed;
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
 *  date 创建时间: 2024-04-19 00:20:28
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
public interface FileDataDetailedMapper extends BaseMapper<FileDataDetailed> {


    final String SQL_INSERT = "INSERT INTO file_data_detailed ( app_id, app_name, client_id, company_id, company_name, file_product_name, file_business_name, use_business_id, storage_config_id, file_source_name, file_type, error_code, error_message, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileDataDetailed.appId},  #{fileDataDetailed.appName},  #{fileDataDetailed.clientId},  #{fileDataDetailed.companyId},  #{fileDataDetailed.companyName},  #{fileDataDetailed.fileProductName},  #{fileDataDetailed.fileBusinessName},  #{fileDataDetailed.useBusinessId},  #{fileDataDetailed.storageConfigId},  #{fileDataDetailed.fileSourceName},  #{fileDataDetailed.fileType},  #{fileDataDetailed.errorCode},  #{fileDataDetailed.errorMessage},  #{fileDataDetailed.createDate},  #{fileDataDetailed.modifyDate},  #{fileDataDetailed.tenantId},  #{fileDataDetailed.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_data_detailed (id, app_id, app_name, client_id, company_id, company_name, file_product_name, file_business_name, use_business_id, storage_config_id, file_source_name, file_type, error_code, error_message, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileDataDetailed.id},  #{fileDataDetailed.appId},  #{fileDataDetailed.appName},  #{fileDataDetailed.clientId},  #{fileDataDetailed.companyId},  #{fileDataDetailed.companyName},  #{fileDataDetailed.fileProductName},  #{fileDataDetailed.fileBusinessName},  #{fileDataDetailed.useBusinessId},  #{fileDataDetailed.storageConfigId},  #{fileDataDetailed.fileSourceName},  #{fileDataDetailed.fileType},  #{fileDataDetailed.errorCode},  #{fileDataDetailed.errorMessage},  #{fileDataDetailed.createDate},  #{fileDataDetailed.modifyDate},  #{fileDataDetailed.tenantId},  #{fileDataDetailed.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileDataDetailed")  FileDataDetailed fileDataDetailed);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileDataDetailed") FileDataDetailed fileDataDetailed);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileDataDetailed> fileDataDetailed);


	
}
