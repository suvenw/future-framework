package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysPositionMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:52
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
public interface SysPositionMapper extends BaseMapper<SysPosition> {


    final String SQL_INSERT = "INSERT INTO sys_position ( code, name, post_rank, company_id, create_by, create_time, update_by, update_time, sys_org_code ) "
				+ " VALUES( #{sysPosition.code},  #{sysPosition.name},  #{sysPosition.postRank},  #{sysPosition.companyId},  #{sysPosition.createBy},  #{sysPosition.createTime},  #{sysPosition.updateBy},  #{sysPosition.updateTime},  #{sysPosition.sysOrgCode} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_position (id, code, name, post_rank, company_id, create_by, create_time, update_by, update_time, sys_org_code) "
              + " VALUES( #{sysPosition.id},  #{sysPosition.code},  #{sysPosition.name},  #{sysPosition.postRank},  #{sysPosition.companyId},  #{sysPosition.createBy},  #{sysPosition.createTime},  #{sysPosition.updateBy},  #{sysPosition.updateTime},  #{sysPosition.sysOrgCode} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysPosition")  SysPosition sysPosition);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysPosition") SysPosition sysPosition);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysPosition> sysPosition);


	
}
