package com.suven.framework.fileinter.mapper;

import com.suven.framework.fileinter.entity.FileUploadActionWater;
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
 *  date 创建时间: 2024-04-19 00:14:12
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
public interface FileUploadActionWaterMapper extends BaseMapper<FileUploadActionWater> {


    final String SQL_INSERT = "INSERT INTO file_upload_action_water ( company_id, company_name, dept_id, dept_name, upload_user_id, upload_user_name, file_product_name, file_business_name, app_id, client_id, use_business_id, storage_config_id, file_upload_storage_id, file_source_name, file_oss_name, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileUploadActionWater.companyId},  #{fileUploadActionWater.companyName},  #{fileUploadActionWater.deptId},  #{fileUploadActionWater.deptName},  #{fileUploadActionWater.uploadUserId},  #{fileUploadActionWater.uploadUserName},  #{fileUploadActionWater.fileProductName},  #{fileUploadActionWater.fileBusinessName},  #{fileUploadActionWater.appId},  #{fileUploadActionWater.clientId},  #{fileUploadActionWater.useBusinessId},  #{fileUploadActionWater.storageConfigId},  #{fileUploadActionWater.fileUploadStorageId},  #{fileUploadActionWater.fileSourceName},  #{fileUploadActionWater.fileOssName},  #{fileUploadActionWater.createDate},  #{fileUploadActionWater.modifyDate},  #{fileUploadActionWater.tenantId},  #{fileUploadActionWater.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_upload_action_water (id, company_id, company_name, dept_id, dept_name, upload_user_id, upload_user_name, file_product_name, file_business_name, app_id, client_id, use_business_id, storage_config_id, file_upload_storage_id, file_source_name, file_oss_name, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileUploadActionWater.id},  #{fileUploadActionWater.companyId},  #{fileUploadActionWater.companyName},  #{fileUploadActionWater.deptId},  #{fileUploadActionWater.deptName},  #{fileUploadActionWater.uploadUserId},  #{fileUploadActionWater.uploadUserName},  #{fileUploadActionWater.fileProductName},  #{fileUploadActionWater.fileBusinessName},  #{fileUploadActionWater.appId},  #{fileUploadActionWater.clientId},  #{fileUploadActionWater.useBusinessId},  #{fileUploadActionWater.storageConfigId},  #{fileUploadActionWater.fileUploadStorageId},  #{fileUploadActionWater.fileSourceName},  #{fileUploadActionWater.fileOssName},  #{fileUploadActionWater.createDate},  #{fileUploadActionWater.modifyDate},  #{fileUploadActionWater.tenantId},  #{fileUploadActionWater.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileUploadActionWater")  FileUploadActionWater fileUploadActionWater);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileUploadActionWater") FileUploadActionWater fileUploadActionWater);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileUploadActionWater> fileUploadActionWater);


	
}
