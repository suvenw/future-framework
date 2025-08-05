package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysRolePermissionMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:49
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 的数据库sql编写实现类
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
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {


    final String SQL_INSERT = "INSERT INTO sys_role_permission ( role_id, permission_id, data_rule_ids, operate_ip, create_by, create_time, update_by, update_time ) "
				+ " VALUES( #{sysRolePermission.roleId},  #{sysRolePermission.permissionId},  #{sysRolePermission.dataRuleIds},  #{sysRolePermission.operateIp},  #{sysRolePermission.createBy},  #{sysRolePermission.createTime},  #{sysRolePermission.updateBy},  #{sysRolePermission.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_role_permission (id, role_id, permission_id, data_rule_ids, operate_ip, create_by, create_time, update_by, update_time) "
              + " VALUES( #{sysRolePermission.id},  #{sysRolePermission.roleId},  #{sysRolePermission.permissionId},  #{sysRolePermission.dataRuleIds},  #{sysRolePermission.operateIp},  #{sysRolePermission.createBy},  #{sysRolePermission.createTime},  #{sysRolePermission.updateBy},  #{sysRolePermission.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysRolePermission")  SysRolePermission sysRolePermission);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysRolePermission") SysRolePermission sysRolePermission);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysRolePermission> sysRolePermission);


	
}
