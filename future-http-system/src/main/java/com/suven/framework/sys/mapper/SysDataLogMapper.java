package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysDataLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysDataLogMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
 * @version 版本: v1.0.0
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
public interface SysDataLogMapper extends BaseMapper<SysDataLog> {


    final String SQL_INSERT = "INSERT INTO sys_data_log ( create_by, create_time, update_by, update_time, data_table, data_id, data_content, data_version ) "
				+ " VALUES( #{sysDataLog.createBy},  #{sysDataLog.createTime},  #{sysDataLog.updateBy},  #{sysDataLog.updateTime},  #{sysDataLog.dataTable},  #{sysDataLog.dataId},  #{sysDataLog.dataContent},  #{sysDataLog.dataVersion} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_data_log (id, create_by, create_time, update_by, update_time, data_table, data_id, data_content, data_version) "
              + " VALUES( #{sysDataLog.id},  #{sysDataLog.createBy},  #{sysDataLog.createTime},  #{sysDataLog.updateBy},  #{sysDataLog.updateTime},  #{sysDataLog.dataTable},  #{sysDataLog.dataId},  #{sysDataLog.dataContent},  #{sysDataLog.dataVersion} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysDataLog")  SysDataLog sysDataLog);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysDataLog") SysDataLog sysDataLog);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysDataLog> sysDataLog);


	
}
