package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysRoleMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 的数据库sql编写实现类
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
public interface SysRoleMapper extends BaseMapper<SysRole> {


    final String SQL_INSERT = "INSERT INTO sys_role ( role_name, role_code, description, create_by, create_time, update_by, update_time ) "
				+ " VALUES( #{sysRole.roleName},  #{sysRole.roleCode},  #{sysRole.description},  #{sysRole.createBy},  #{sysRole.createTime},  #{sysRole.updateBy},  #{sysRole.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_role (id, role_name, role_code, description, create_by, create_time, update_by, update_time) "
              + " VALUES( #{sysRole.id},  #{sysRole.roleName},  #{sysRole.roleCode},  #{sysRole.description},  #{sysRole.createBy},  #{sysRole.createTime},  #{sysRole.updateBy},  #{sysRole.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysRole")  SysRole sysRole);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysRole") SysRole sysRole);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysRole> sysRole);


	
}
