package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysDictItemMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:15
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 的数据库sql编写实现类
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
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {


    final String SQL_INSERT = "INSERT INTO sys_dict_item ( dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time ) "
				+ " VALUES( #{sysDictItem.dictId},  #{sysDictItem.itemText},  #{sysDictItem.itemValue},  #{sysDictItem.description},  #{sysDictItem.sortOrder},  #{sysDictItem.status},  #{sysDictItem.createBy},  #{sysDictItem.createTime},  #{sysDictItem.updateBy},  #{sysDictItem.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_dict_item (id, dict_id, item_text, item_value, description, sort_order, status, create_by, create_time, update_by, update_time) "
              + " VALUES( #{sysDictItem.id},  #{sysDictItem.dictId},  #{sysDictItem.itemText},  #{sysDictItem.itemValue},  #{sysDictItem.description},  #{sysDictItem.sortOrder},  #{sysDictItem.status},  #{sysDictItem.createBy},  #{sysDictItem.createTime},  #{sysDictItem.updateBy},  #{sysDictItem.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysDictItem")  SysDictItem sysDictItem);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysDictItem") SysDictItem sysDictItem);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysDictItem> sysDictItem);


	
}
