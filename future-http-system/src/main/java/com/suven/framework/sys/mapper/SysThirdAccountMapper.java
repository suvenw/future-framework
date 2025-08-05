package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysThirdAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysThirdAccountMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:47
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 的数据库sql编写实现类
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
public interface SysThirdAccountMapper extends BaseMapper<SysThirdAccount> {


    final String SQL_INSERT = "INSERT INTO sys_third_account ( sys_user_id, avatar, status, del_flag, realname, third_user_uuid, third_user_id, third_type ) "
				+ " VALUES( #{sysThirdAccount.sysUserId},  #{sysThirdAccount.avatar},  #{sysThirdAccount.status},  #{sysThirdAccount.delFlag},  #{sysThirdAccount.realname},  #{sysThirdAccount.thirdUserUuid},  #{sysThirdAccount.thirdUserId},  #{sysThirdAccount.thirdType} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_third_account (id, sys_user_id, avatar, status, del_flag, realname, third_user_uuid, third_user_id, third_type) "
              + " VALUES( #{sysThirdAccount.id},  #{sysThirdAccount.sysUserId},  #{sysThirdAccount.avatar},  #{sysThirdAccount.status},  #{sysThirdAccount.delFlag},  #{sysThirdAccount.realname},  #{sysThirdAccount.thirdUserUuid},  #{sysThirdAccount.thirdUserId},  #{sysThirdAccount.thirdType} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysThirdAccount")  SysThirdAccount sysThirdAccount);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysThirdAccount") SysThirdAccount sysThirdAccount);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysThirdAccount> sysThirdAccount);


	
}
