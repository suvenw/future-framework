package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysUserMapper.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 的数据库sql编写实现类
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
public interface SysUserMapper extends BaseMapper<SysUser> {


    final String SQL_INSERT = "INSERT INTO sys_user ( username, realname, password, salt, avatar, birthday, sex, email, phone, org_code, status, del_flag, third_id, third_type, activiti_sync, work_no, post, telephone, create_by, create_time, update_by, update_time, user_identity, depart_ids, rel_tenant_ids, client_id ) "
				+ " VALUES( #{sysUser.username},  #{sysUser.realname},  #{sysUser.password},  #{sysUser.salt},  #{sysUser.avatar},  #{sysUser.birthday},  #{sysUser.sex},  #{sysUser.email},  #{sysUser.phone},  #{sysUser.orgCode},  #{sysUser.status},  #{sysUser.delFlag},  #{sysUser.thirdId},  #{sysUser.thirdType},  #{sysUser.activitiSync},  #{sysUser.workNo},  #{sysUser.post},  #{sysUser.telephone},  #{sysUser.createBy},  #{sysUser.createTime},  #{sysUser.updateBy},  #{sysUser.updateTime},  #{sysUser.userIdentity},  #{sysUser.departIds},  #{sysUser.relTenantIds},  #{sysUser.clientId} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_user (id, username, realname, password, salt, avatar, birthday, sex, email, phone, org_code, status, del_flag, third_id, third_type, activiti_sync, work_no, post, telephone, create_by, create_time, update_by, update_time, user_identity, depart_ids, rel_tenant_ids, client_id) "
              + " VALUES( #{sysUser.id},  #{sysUser.username},  #{sysUser.realname},  #{sysUser.password},  #{sysUser.salt},  #{sysUser.avatar},  #{sysUser.birthday},  #{sysUser.sex},  #{sysUser.email},  #{sysUser.phone},  #{sysUser.orgCode},  #{sysUser.status},  #{sysUser.delFlag},  #{sysUser.thirdId},  #{sysUser.thirdType},  #{sysUser.activitiSync},  #{sysUser.workNo},  #{sysUser.post},  #{sysUser.telephone},  #{sysUser.createBy},  #{sysUser.createTime},  #{sysUser.updateBy},  #{sysUser.updateTime},  #{sysUser.userIdentity},  #{sysUser.departIds},  #{sysUser.relTenantIds},  #{sysUser.clientId} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysUser")  SysUser sysUser);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysUser") SysUser sysUser);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysUser> sysUser);

	IPage<SysUser> getUserByRoleId(Page page, @Param("roleId") long roleId, @Param("username") String username);
	
}
