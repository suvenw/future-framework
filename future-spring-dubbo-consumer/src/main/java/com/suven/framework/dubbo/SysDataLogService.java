package com.suven.framework.dubbo;


import java.io.InputStream;
import java.util.Collection;
import java.util.List;




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
     * @param SysDataLog  SysDataLog
     * @return
     */
    SysDataLog saveSysDataLog(SysDataLog SysDataLog);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param SysDataLog  SysDataLog
     * @return
     */
    SysDataLog saveIdSysDataLog(SysDataLog SysDataLog);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDataLog(Collection<SysDataLog> entityList);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDataLog(Collection<SysDataLog> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDataLog(Collection<SysDataLog> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param SysDataLog  SysDataLog
     * @return
     */
    boolean updateSysDataLog (SysDataLog SysDataLog);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDataLog> entityList, int batchSize);


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
        SysDataLog getSysDataLogById(long sysDataLogId);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}