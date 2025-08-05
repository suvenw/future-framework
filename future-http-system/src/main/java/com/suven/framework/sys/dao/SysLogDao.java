package com.suven.framework.sys.dao;


import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysLogMapper;
import com.suven.framework.sys.entity.SysLog;
import com.suven.framework.sys.dto.enums.SysLogQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysLogDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 的数据库查询逻辑实现类
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



@Service("sysLogDao")
public class SysLogDao extends AbstractMyBatisDao<SysLogMapper, SysLog> implements IBaseExcelData{

    @Autowired
    private SysLogMapper  sysLogMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysLog>  iPage =  new Query<SysLog>().getPage(params);

        QueryWrapper<SysLog> query =  new QueryWrapper<>();

        IPage<SysLog> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysLog,并且保存到缓存中
     * @param sysLog
     * @author suven
     * @date 2022-02-28 16:10:19
     */

    public SysLog saveId(SysLog sysLog){
            if(null == sysLog){
                return  null;
           }
          long id = sysLogMapper.saveId(sysLog);
          if (returnResult(id)){
                return sysLog;
          }
           return null;


    }

     /**
         * 保存创建SysLog,并且保存到缓存中
         * @param sysLog
         * @author suven
         * @date 2022-02-28 16:10:19
         */

        public SysLog saveToId(SysLog sysLog){
                if(null == sysLog){
                    return  null;
               }
              long id = sysLogMapper.saveToId(sysLog);
              if (returnResult(id)){
                    return sysLog;
              }
               return null;


        }

    /**
     * 批量保存创建SysLog,并且保存到缓存中
     * @param sysLogList
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    public boolean saveBatchId(List<SysLog> sysLogList){
            if(null == sysLogList)
                return  false;
           long id =  sysLogMapper.saveBatch(sysLogList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysLog> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysLog.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysLog信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    public List<SysLog> getListByPage(IPage<SysLog> iPage, QueryWrapper<SysLog> queryWrapper ){


        List<SysLog> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysLog> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysLog>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysLog信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    public List<SysLog> getListByQuery( QueryWrapper<SysLog> queryWrapper ){


        List<SysLog> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysLog> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysLog不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    public QueryWrapper<SysLog> builderQueryEnum(SysLogQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysLog  sysLog = SysLog.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }
}