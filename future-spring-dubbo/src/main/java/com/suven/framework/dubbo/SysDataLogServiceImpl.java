package com.suven.framework.dubbo;


import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;







/**
 * ClassName: SysDataLogServiceImpl.java
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

@DubboService
@Service
public class SysDataLogServiceImpl  implements SysDataLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());




    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param SysDataLog SysDataLog
     * @return
     */
    @Override
    public SysDataLog saveSysDataLog(SysDataLog SysDataLog){
        if(SysDataLog== null){
            return null;
        }
        SysDataLog sysDataLog = SysDataLog.build();
        return sysDataLog;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param SysDataLog SysDataLog
     * @return
     */
    @Override
    public SysDataLog saveIdSysDataLog(SysDataLog SysDataLog){
        if(SysDataLog== null){
            return null;
        }
        SysDataLog sysDataLog = SysDataLog.build();
        return sysDataLog;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDataLog集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDataLog(Collection<SysDataLog> entityList) {
        if(null == entityList ){
            return false;
        }

        return true;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList SysDataLog集合
     * @return
     */
    @Override
    public boolean saveBatchSysDataLog(Collection<SysDataLog> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }

        boolean result = true;
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDataLog(Collection<SysDataLog> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        boolean result = true;
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDataLog> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        boolean result = true;
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param SysDataLog  SysDataLog
     * @return
     */
    @Override
    public boolean updateSysDataLog(SysDataLog SysDataLog){

          if(null ==  SysDataLog){
              return false;
          }


        return true;


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDataLogByIds(List<Long> idList){
        boolean result = false;
        return 1;

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysDataLogId
     * @return
     */
    @Override
    public SysDataLog getSysDataLogById(long sysDataLogId){
        if(sysDataLogId < 0 ){
            return null;
        }
        SysDataLog sysDataLog =  SysDataLog.build();

        return sysDataLog ;

    }



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDataLog  setSysDataLog(){
        SysDataLog sysDataLog = new SysDataLog();
        /**
 			//sysDataLog.setCreateBy (String createBy);
 			//sysDataLog.setCreateTime (Date createTime);
 			//sysDataLog.setUpdateBy (String updateBy);
 			//sysDataLog.setUpdateTime (Date updateTime);
 			//sysDataLog.setDataTable (String dataTable);
 			//sysDataLog.setDataId (String dataId);
 			//sysDataLog.setDataContent (String dataContent);
 			//sysDataLog.setDataVersion (int dataVersion);
		**/

        return sysDataLog;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
       return true;
    }


}
