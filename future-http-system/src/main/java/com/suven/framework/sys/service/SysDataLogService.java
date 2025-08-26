package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDataLogRequestDto;
import com.suven.framework.sys.dto.response.SysDataLogResponseDto;
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




/**
 * ClassName: SysDataLogService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  RPC业务接口逻辑实现类
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


public interface SysDataLogService {



    /**
     * 保存更新数据库和缓存的实现方法
     * @param sysDataLogRequestDto  SysDataLogRequestDto
     * @return
     */
    SysDataLogResponseDto saveSysDataLog(SysDataLogRequestDto sysDataLogRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDataLogRequestDto  SysDataLogRequestDto
     * @return
     */
    SysDataLogResponseDto saveIdSysDataLog(SysDataLogRequestDto sysDataLogRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDataLog(Collection<SysDataLogRequestDto> entityList);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDataLog(Collection<SysDataLogRequestDto> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDataLog(Collection<SysDataLogRequestDto> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param sysDataLogRequestDto  SysDataLogRequestDto
     * @return
     */
    boolean updateSysDataLog (SysDataLogRequestDto sysDataLogRequestDto);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDataLogRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDataLogIds
     * @return
     */
    int delSysDataLogByIds(List<Long>  sysDataLogIds);


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysDataLogId
     * @return
     */
        SysDataLogResponseDto getSysDataLogById(long sysDataLogId);

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum
     * @return
     */
    SysDataLogResponseDto getSysDataLogByOne( SysDataLogQueryEnum queryEnum,SysDataLogRequestDto sysDataLogRequestDto);


    /**
    * 通过分页和枚举条件获取SysDataLog信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:10:02
    */
    List<SysDataLogResponseDto> getSysDataLogListByQuery(Object  paramObject, SysDataLogQueryEnum queryEnum);


    /**
     * 通过分页获取SysDataLog信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    List<SysDataLogResponseDto> getSysDataLogListByPage(Pager page, SysDataLogQueryEnum queryEnum);




    /**
     * 通过分页获取SysDataLog 信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    PageResult<SysDataLogResponseDto> getSysDataLogByNextPage(Pager page, SysDataLogQueryEnum queryEnum);

    /**
     * 通过分页获取SysDataLog 信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    PageResult<SysDataLogResponseDto> getSysDataLogByQueryPage(Pager page, SysDataLogQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}