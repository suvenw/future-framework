package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysLogRequestDto;
import com.suven.framework.sys.dto.response.SysLogResponseDto;
import com.suven.framework.sys.dto.enums.SysLogQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * @ClassName: SysLogService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 RPC业务接口逻辑实现类
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


public interface SysLogService {



    /**
     * 保存系统日志表更新数据库和缓存的实现方法
     * @param sysLogRequestDto  SysLogRequestDto
     * @return
     */
    SysLogResponseDto saveSysLog(SysLogRequestDto sysLogRequestDto);


     /**
     * 保存系统日志表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysLogRequestDto  SysLogRequestDto
     * @return
     */
    SysLogResponseDto saveIdSysLog(SysLogRequestDto sysLogRequestDto);


     /**
     * 保存系统日志表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysLog(Collection<SysLogRequestDto> entityList);

    /**
     * 保存系统日志表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysLog(Collection<SysLogRequestDto> entityList, int batchSize);

    /**
     * 保存系统日志表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysLog(Collection<SysLogRequestDto> entityList, int batchSize);



    /**
     * 更新系统日志表同时更新数据库和缓存的实现方法
     * @param sysLogRequestDto  SysLogRequestDto
     * @return
     */
    boolean updateSysLog (SysLogRequestDto sysLogRequestDto);

    /**
     * 更新系统日志表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysLogRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysLogIds
     * @return
     */
    int delSysLogByIds(List<Long>  sysLogIds);


    /**
     * 通过主键ID更新对象系统日志表实现缓存和数据库更新的方法
     * @param  sysLogId
     * @return
     */
        SysLogResponseDto getSysLogById(long sysLogId);

    /**
     * 通过参数limit0,1获取对象系统日志表的查询方法
     * @param  queryEnum
     * @return
     */
    SysLogResponseDto getSysLogByOne( SysLogQueryEnum queryEnum,SysLogRequestDto sysLogRequestDto);


    /**
    * 通过分页和枚举条件获取SysLog信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:10:19
    */
    List<SysLogResponseDto> getSysLogListByQuery(Object  paramObject, SysLogQueryEnum queryEnum);


    /**
     * 通过分页获取SysLog信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    List<SysLogResponseDto> getSysLogListByPage(Pager page, SysLogQueryEnum queryEnum);




    /**
     * 通过分页获取SysLog 系统日志表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    ResponseResultPageVo<SysLogResponseDto> getSysLogByNextPage(Pager page, SysLogQueryEnum queryEnum);

    /**
     * 通过分页获取SysLog 系统日志表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    ResponseResultPageVo<SysLogResponseDto> getSysLogByQueryPage(Pager page, SysLogQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}