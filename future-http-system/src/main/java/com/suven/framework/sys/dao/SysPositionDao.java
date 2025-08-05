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

import com.suven.framework.sys.mapper.SysPositionMapper;
import com.suven.framework.sys.entity.SysPosition;
import com.suven.framework.sys.dto.enums.SysPositionQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysPositionDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:52
 * @Version 版本: v1.0.0
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



@Service("sysPositionDao")
public class SysPositionDao extends AbstractMyBatisDao<SysPositionMapper, SysPosition> implements IBaseExcelData{

    @Autowired
    private SysPositionMapper  sysPositionMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPosition>  iPage =  new Query<SysPosition>().getPage(params);

        QueryWrapper<SysPosition> query =  new QueryWrapper<>();

        IPage<SysPosition> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysPosition,并且保存到缓存中
     * @param sysPosition
     * @author suven
     * @date 2022-02-28 16:13:52
     */

    public SysPosition saveId(SysPosition sysPosition){
            if(null == sysPosition){
                return  null;
           }
          long id = sysPositionMapper.saveId(sysPosition);
          if (returnResult(id)){
                return sysPosition;
          }
           return null;


    }

     /**
         * 保存创建SysPosition,并且保存到缓存中
         * @param sysPosition
         * @author suven
         * @date 2022-02-28 16:13:52
         */

        public SysPosition saveToId(SysPosition sysPosition){
                if(null == sysPosition){
                    return  null;
               }
              long id = sysPositionMapper.saveToId(sysPosition);
              if (returnResult(id)){
                    return sysPosition;
              }
               return null;


        }

    /**
     * 批量保存创建SysPosition,并且保存到缓存中
     * @param sysPositionList
     * @author suven
     * @date 2022-02-28 16:13:52
     */
    public boolean saveBatchId(List<SysPosition> sysPositionList){
            if(null == sysPositionList)
                return  false;
           long id =  sysPositionMapper.saveBatch(sysPositionList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysPosition> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysPosition.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysPosition信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:52
     */
    public List<SysPosition> getListByPage(IPage<SysPosition> iPage, QueryWrapper<SysPosition> queryWrapper ){


        List<SysPosition> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysPosition> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysPosition>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysPosition信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:13:52
     */
    public List<SysPosition> getListByQuery( QueryWrapper<SysPosition> queryWrapper ){


        List<SysPosition> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysPosition> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysPosition不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:13:52
     */
    public QueryWrapper<SysPosition> builderQueryEnum(SysPositionQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysPosition> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysPosition  sysPosition = SysPosition.build().clone(queryObject);
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