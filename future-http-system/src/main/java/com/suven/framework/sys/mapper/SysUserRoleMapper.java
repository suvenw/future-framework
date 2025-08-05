package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysUserRoleMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:11:27
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 的数据库sql编写实现类
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    final String SQL_INSERT = "INSERT INTO sys_user_role ( user_id, role_id, create_time, update_time ) "
				+ " VALUES( #{sysUserRole.userId},  #{sysUserRole.roleId},  #{sysUserRole.createTime},  #{sysUserRole.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_user_role (id, user_id, role_id, create_time, update_time) "
              + " VALUES( #{sysUserRole.id},  #{sysUserRole.userId},  #{sysUserRole.roleId},  #{sysUserRole.createTime},  #{sysUserRole.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysUserRole")  SysUserRole sysUserRole);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysUserRole") SysUserRole sysUserRole);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysUserRole> sysUserRole);


	
}
