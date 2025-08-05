package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysLogMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 的数据库sql编写实现类
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
public interface SysLogMapper extends BaseMapper<SysLog> {


    final String SQL_INSERT = "INSERT INTO sys_log ( log_type, log_content, operate_type, user_id, user_name, ip, method, request_url, request_param, request_type, cost_time, create_by, create_time, update_by, update_time ) "
				+ " VALUES( #{sysLog.logType},  #{sysLog.logContent},  #{sysLog.operateType},  #{sysLog.userId},  #{sysLog.userName},  #{sysLog.ip},  #{sysLog.method},  #{sysLog.requestUrl},  #{sysLog.requestParam},  #{sysLog.requestType},  #{sysLog.costTime},  #{sysLog.createBy},  #{sysLog.createTime},  #{sysLog.updateBy},  #{sysLog.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_log (id, log_type, log_content, operate_type, user_id, user_name, ip, method, request_url, request_param, request_type, cost_time, create_by, create_time, update_by, update_time) "
              + " VALUES( #{sysLog.id},  #{sysLog.logType},  #{sysLog.logContent},  #{sysLog.operateType},  #{sysLog.userId},  #{sysLog.userName},  #{sysLog.ip},  #{sysLog.method},  #{sysLog.requestUrl},  #{sysLog.requestParam},  #{sysLog.requestType},  #{sysLog.costTime},  #{sysLog.createBy},  #{sysLog.createTime},  #{sysLog.updateBy},  #{sysLog.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysLog")  SysLog sysLog);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysLog") SysLog sysLog);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysLog> sysLog);


	
}
