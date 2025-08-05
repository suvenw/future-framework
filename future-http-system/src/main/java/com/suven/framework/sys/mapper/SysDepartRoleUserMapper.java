package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysDepartRoleUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysDepartRoleUserMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:53:58
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 的数据库sql编写实现类
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
public interface SysDepartRoleUserMapper extends BaseMapper<SysDepartRoleUser> {


    final String SQL_INSERT = "INSERT INTO sys_depart_role_user ( user_id, depart_role_id, depart_id, create_time, update_time ) "
				+ " VALUES( #{sysDepartRoleUser.userId},  #{sysDepartRoleUser.departRoleId},  #{sysDepartRoleUser.departId},  #{sysDepartRoleUser.createTime},  #{sysDepartRoleUser.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_depart_role_user (id, user_id, depart_role_id, depart_id, create_time, update_time) "
              + " VALUES( #{sysDepartRoleUser.id},  #{sysDepartRoleUser.userId},  #{sysDepartRoleUser.departRoleId},  #{sysDepartRoleUser.departId},  #{sysDepartRoleUser.createTime},  #{sysDepartRoleUser.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysDepartRoleUser")  SysDepartRoleUser sysDepartRoleUser);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysDepartRoleUser") SysDepartRoleUser sysDepartRoleUser);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysDepartRoleUser> sysDepartRoleUser);


	
}
