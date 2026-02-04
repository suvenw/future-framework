package com.suven.framework.upload.mapper;

import com.suven.framework.upload.entity.FileUploadUseBusiness;
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
 *  date 创建时间: 2024-04-19 00:21:42
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
public interface FileUploadUseBusinessMapper extends BaseMapper<FileUploadUseBusiness> {


    final String SQL_INSERT = "INSERT INTO file_upload_use_business ( app_id, client_id, company_id, company_name, file_product_name, file_business_name, callback_service, callback_async_service, data_form, form_type, interpret_data, check_data, access_expiration_time, access_count, access_url_format, create_date, modify_date, tenant_id, deleted ) "
				+ " VALUES( #{fileUploadUseBusiness.appId},  #{fileUploadUseBusiness.clientId},  #{fileUploadUseBusiness.companyId},  #{fileUploadUseBusiness.companyName},  #{fileUploadUseBusiness.fileProductName},  #{fileUploadUseBusiness.fileBusinessName},  #{fileUploadUseBusiness.callbackService},  #{fileUploadUseBusiness.callbackAsyncService},  #{fileUploadUseBusiness.dataForm},  #{fileUploadUseBusiness.formType},  #{fileUploadUseBusiness.interpretData},  #{fileUploadUseBusiness.checkData},  #{fileUploadUseBusiness.accessExpirationTime},  #{fileUploadUseBusiness.accessCount},  #{fileUploadUseBusiness.accessUrlFormat},  #{fileUploadUseBusiness.createDate},  #{fileUploadUseBusiness.modifyDate},  #{fileUploadUseBusiness.tenantId},  #{fileUploadUseBusiness.deleted} ) ";

	final String SQL_INSERT_ID = "INSERT INTO file_upload_use_business (id, app_id, client_id, company_id, company_name, file_product_name, file_business_name, callback_service, callback_async_service, data_form, form_type, interpret_data, check_data, access_expiration_time, access_count, access_url_format, create_date, modify_date, tenant_id, deleted) "
              + " VALUES( #{fileUploadUseBusiness.id},  #{fileUploadUseBusiness.appId},  #{fileUploadUseBusiness.clientId},  #{fileUploadUseBusiness.companyId},  #{fileUploadUseBusiness.companyName},  #{fileUploadUseBusiness.fileProductName},  #{fileUploadUseBusiness.fileBusinessName},  #{fileUploadUseBusiness.callbackService},  #{fileUploadUseBusiness.callbackAsyncService},  #{fileUploadUseBusiness.dataForm},  #{fileUploadUseBusiness.formType},  #{fileUploadUseBusiness.interpretData},  #{fileUploadUseBusiness.checkData},  #{fileUploadUseBusiness.accessExpirationTime},  #{fileUploadUseBusiness.accessCount},  #{fileUploadUseBusiness.accessUrlFormat},  #{fileUploadUseBusiness.createDate},  #{fileUploadUseBusiness.modifyDate},  #{fileUploadUseBusiness.tenantId},  #{fileUploadUseBusiness.deleted} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("fileUploadUseBusiness")  FileUploadUseBusiness fileUploadUseBusiness);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("fileUploadUseBusiness") FileUploadUseBusiness fileUploadUseBusiness);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<FileUploadUseBusiness> fileUploadUseBusiness);


	
}
