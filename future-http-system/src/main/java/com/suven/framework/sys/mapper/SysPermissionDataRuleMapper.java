package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysPermissionDataRuleMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:35
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 的数据库sql编写实现类
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
public interface SysPermissionDataRuleMapper extends BaseMapper<SysPermissionDataRule> {


    final String SQL_INSERT = "INSERT INTO sys_permission_data_rule ( permission_id, rule_name, rule_column, rule_conditions, rule_value, status, create_time, create_by, update_time, update_by ) "
				+ " VALUES( #{sysPermissionDataRule.permissionId},  #{sysPermissionDataRule.ruleName},  #{sysPermissionDataRule.ruleColumn},  #{sysPermissionDataRule.ruleConditions},  #{sysPermissionDataRule.ruleValue},  #{sysPermissionDataRule.status},  #{sysPermissionDataRule.createTime},  #{sysPermissionDataRule.createBy},  #{sysPermissionDataRule.updateTime},  #{sysPermissionDataRule.updateBy} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_permission_data_rule (id, permission_id, rule_name, rule_column, rule_conditions, rule_value, status, create_time, create_by, update_time, update_by) "
              + " VALUES( #{sysPermissionDataRule.id},  #{sysPermissionDataRule.permissionId},  #{sysPermissionDataRule.ruleName},  #{sysPermissionDataRule.ruleColumn},  #{sysPermissionDataRule.ruleConditions},  #{sysPermissionDataRule.ruleValue},  #{sysPermissionDataRule.status},  #{sysPermissionDataRule.createTime},  #{sysPermissionDataRule.createBy},  #{sysPermissionDataRule.updateTime},  #{sysPermissionDataRule.updateBy} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysPermissionDataRule")  SysPermissionDataRule sysPermissionDataRule);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysPermissionDataRule") SysPermissionDataRule sysPermissionDataRule);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysPermissionDataRule> sysPermissionDataRule);


	
}
