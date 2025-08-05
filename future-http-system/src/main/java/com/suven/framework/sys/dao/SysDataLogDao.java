package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;
import com.suven.framework.sys.entity.SysDataLog;
import com.suven.framework.sys.mapper.SysDataLogMapper;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
 * Version 版本: v1.0.0
 * <pre>
 *
 *  Description:  的数据库查询逻辑实现类
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


@Transactional
@Service("sysDataLogDao")
public class SysDataLogDao extends AbstractMyBatisDao<SysDataLogMapper, SysDataLog> implements IBaseExcelData{

    @Autowired
    private SysDataLogMapper  sysDataLogMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDataLog>  iPage =  new Query<SysDataLog>().getPage(params);

        QueryWrapper<SysDataLog> query =  new QueryWrapper<>();

        IPage<SysDataLog> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDataLog,并且保存到缓存中
     * @param sysDataLog
     * @author suven
     * @date 2022-02-28 16:10:02
     */

    public SysDataLog saveId(SysDataLog sysDataLog){
            if(null == sysDataLog){
                return  null;
           }
          long id = this.getBaseMapper().saveId(sysDataLog);
          if (returnBool(id)){
                return sysDataLog;
          }
           return null;


    }

     /**
         * 保存创建SysDataLog,并且保存到缓存中
         * @param sysDataLog
         * @author suven
         * @date 2022-02-28 16:10:02
         */

        public SysDataLog saveToId(SysDataLog sysDataLog){
                if(null == sysDataLog){
                    return  null;
               }
               this.masterDataSource();
              long id = this.getBaseMapper().saveToId(sysDataLog);
              if (returnBool(id)){
                    return sysDataLog;
              }
               return null;


        }

    /**
     * 批量保存创建SysDataLog,并且保存到缓存中
     * @param sysDataLogList
     * @author suven
     * @date 2022-02-28 16:10:02
     */
    public boolean saveBatchId(List<SysDataLog> sysDataLogList){
            if(null == sysDataLogList)
                return  false;
           long id =  this.getBaseMapper().saveBatch(sysDataLogList);
           return returnBool(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDataLog> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDataLog.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDataLog信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:02
     */
    public List<SysDataLog> getListByPage(IPage<SysDataLog> iPage, QueryWrapper<SysDataLog> queryWrapper ){


        List<SysDataLog> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        IPage<SysDataLog> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDataLog>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDataLog信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:10:02
     */
    public List<SysDataLog> getListByQuery( QueryWrapper<SysDataLog> queryWrapper ){


        List<SysDataLog> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        List<SysDataLog> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDataLog不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:10:02
     */
    public QueryWrapper<SysDataLog> builderQueryEnum(SysDataLogQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDataLog> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDataLog  sysDataLog = SysDataLog.build().clone(queryObject);
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