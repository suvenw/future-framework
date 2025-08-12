package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.sys.vo.request.AllStatusRequestVo;
import com.suven.framework.http.data.vo.PageResult;




/**
 * ClassName: SysUserService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 RPC业务接口逻辑实现类
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


public interface SysUserService {



    /**
     * 保存用户表更新数据库和缓存的实现方法
     * @param sysUserRequestDto  SysUserRequestDto
     * @return
     */
    SysUserResponseDto saveSysUser(SysUserRequestDto sysUserRequestDto);


     /**
     * 保存用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysUserRequestDto  SysUserRequestDto
     * @return
     */
    SysUserResponseDto saveIdSysUser(SysUserRequestDto sysUserRequestDto);


     /**
     * 保存用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysUser(Collection<SysUserRequestDto> entityList);

    /**
     * 保存用户表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize);

    /**
     * 保存用户表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize);



    /**
     * 更新用户表同时更新数据库和缓存的实现方法
     * @param sysUserRequestDto  SysUserRequestDto
     * @return
     */
    boolean updateSysUser (SysUserRequestDto sysUserRequestDto);

    boolean updateSysUserPassWord(SysUserRequestDto sysUserRequestDto);

    /**
     * 更新用户表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysUserRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysUserIds
     * @return
     */
    int delSysUserByIds(List<Long>  sysUserIds);


    /**
     * 通过主键ID更新对象用户表实现缓存和数据库更新的方法
     * @param  sysUserId
     * @return
     */
        SysUserResponseDto getSysUserById(long sysUserId);

    /**
     * 通过参数limit0,1获取对象用户表的查询方法
     * @param  queryEnum
     * @return
     */
    SysUserResponseDto getSysUserByOne( SysUserQueryEnum queryEnum,SysUserRequestDto sysUserRequestDto);


    /**
    * 通过分页和枚举条件获取SysUser信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:09:37
    */
    List<SysUserResponseDto> getSysUserListByQuery(Object  paramObject, SysUserQueryEnum queryEnum);


    /**
     * 通过分页获取SysUser信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    List<SysUserResponseDto> getSysUserListByPage(Pager page, SysUserQueryEnum queryEnum);




    /**
     * 通过分页获取SysUser 用户表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    PageResult<SysUserResponseDto> getSysUserByNextPage(Pager page, SysUserQueryEnum queryEnum);

    /**
     * 通过分页获取SysUser 用户表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    PageResult<SysUserResponseDto> getSysUserByQueryPage(Pager page, SysUserQueryEnum queryEnum);



     boolean addSysUserRole(long roleId, List<Long> idList);

    boolean deleteUserRole(long roleId, List<Long> idList) ;

    /**
     * @return
     * Title: 启用用户表信息
     * Description:
     * @throw
     * @author suven
     * date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    public boolean turnOn(List<Long> idList);

    /**
     * @return
     * Title: 禁用用户表信息
     * Description:
     * @throw
     * @author suven
     * date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    public boolean turnOff(List<Long> idList);

    /**
     * 冻结用户
     *
     * @param idList
     * @return
     */
    public boolean frozenBatch(List<Long> idList , int status);

    /**
     * 处理禁言
     *
     * @param actionReq
     * @return
     */
    public boolean handleMuted(AllStatusRequestVo actionReq);

    /**
     * 处理封号
     *
     * @param actionReq
     * @return
     */
    public boolean handleBan(AllStatusRequestVo actionReq);

    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

    PageResult<SysUserResponseDto> getUserByDepIdPage( long depId);


    PageResult<SysUserResponseDto> getSysUserRoleId(Pager basePage, long roleId , String username);
}