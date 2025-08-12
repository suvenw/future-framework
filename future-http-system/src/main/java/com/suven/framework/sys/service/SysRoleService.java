package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysRoleRequestDto;
import com.suven.framework.sys.dto.response.SysRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysRoleQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.PageResult;




/**
 * ClassName: SysRoleService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 RPC业务接口逻辑实现类
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


public interface SysRoleService {



    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @param sysRoleRequestDto  SysRoleRequestDto
     * @return
     */
    SysRoleResponseDto saveSysRole(SysRoleRequestDto sysRoleRequestDto);


     /**
     * 保存角色表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysRoleRequestDto  SysRoleRequestDto
     * @return
     */
    SysRoleResponseDto saveIdSysRole(SysRoleRequestDto sysRoleRequestDto);


     /**
     * 保存角色表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysRole(Collection<SysRoleRequestDto> entityList);

    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysRole(Collection<SysRoleRequestDto> entityList, int batchSize);

    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysRole(Collection<SysRoleRequestDto> entityList, int batchSize);



    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     * @param sysRoleRequestDto  SysRoleRequestDto
     * @return
     */
    boolean updateSysRole (SysRoleRequestDto sysRoleRequestDto);

    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysRoleRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysRoleIds
     * @return
     */
    int delSysRoleByIds(List<Long>  sysRoleIds);


    /**
     * 通过主键ID更新对象角色表实现缓存和数据库更新的方法
     * @param  sysRoleId
     * @return
     */
        SysRoleResponseDto getSysRoleById(long sysRoleId);

    /**
     * 通过参数limit0,1获取对象角色表的查询方法
     * @param  queryEnum
     * @return
     */
    SysRoleResponseDto getSysRoleByOne( SysRoleQueryEnum queryEnum,SysRoleRequestDto sysRoleRequestDto);


    /**
    * 通过分页和枚举条件获取SysRole信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:10:43
    */
    List<SysRoleResponseDto> getSysRoleListByQuery(Object  paramObject, SysRoleQueryEnum queryEnum);


    /**
     * 通过分页获取SysRole信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    List<SysRoleResponseDto> getSysRoleListByPage(Pager page, SysRoleQueryEnum queryEnum);




    /**
     * 通过分页获取SysRole 角色表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    PageResult<SysRoleResponseDto> getSysRoleByNextPage(Pager page, SysRoleQueryEnum queryEnum);

    /**
     * 通过分页获取SysRole 角色表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    PageResult<SysRoleResponseDto> getSysRoleByQueryPage(Pager page, SysRoleQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);


    /**
     * 通过分页获取SysRoleResponseDto信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
   List<SysRoleResponseDto> getSysDepartByIdList(Collection<Long> idList);

}