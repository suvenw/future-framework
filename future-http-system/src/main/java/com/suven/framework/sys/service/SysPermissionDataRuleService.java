package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * @ClassName: SysPermissionDataRuleService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:35
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 RPC业务接口逻辑实现类
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


public interface SysPermissionDataRuleService {



    /**
     * 保存菜单权限规则表更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleRequestDto
     * @return
     */
    SysPermissionDataRuleResponseDto saveSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);


     /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleRequestDto
     * @return
     */
    SysPermissionDataRuleResponseDto saveIdSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);


     /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList);

    /**
     * 保存菜单权限规则表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);

    /**
     * 保存菜单权限规则表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);



    /**
     * 更新菜单权限规则表同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleRequestDto
     * @return
     */
    boolean updateSysPermissionDataRule (SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);

    /**
     * 更新菜单权限规则表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysPermissionDataRuleIds
     * @return
     */
    int delSysPermissionDataRuleByIds(List<Long>  sysPermissionDataRuleIds);


    /**
     * 通过主键ID更新对象菜单权限规则表实现缓存和数据库更新的方法
     * @param  sysPermissionDataRuleId
     * @return
     */
        SysPermissionDataRuleResponseDto getSysPermissionDataRuleById(long sysPermissionDataRuleId);

    /**
     * 通过参数limit0,1获取对象菜单权限规则表的查询方法
     * @param  queryEnum
     * @return
     */
    SysPermissionDataRuleResponseDto getSysPermissionDataRuleByOne( SysPermissionDataRuleQueryEnum queryEnum,SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);


    /**
    * 通过分页和枚举条件获取SysPermissionDataRule信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:10:35
    */
    List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleListByQuery(Object  paramObject, SysPermissionDataRuleQueryEnum queryEnum);


    /**
     * 通过分页获取SysPermissionDataRule信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleListByPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum);




    /**
     * 通过分页获取SysPermissionDataRule 菜单权限规则表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    ResponseResultPageVo<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByNextPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum);

    /**
     * 通过分页获取SysPermissionDataRule 菜单权限规则表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    ResponseResultPageVo<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByQueryPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}