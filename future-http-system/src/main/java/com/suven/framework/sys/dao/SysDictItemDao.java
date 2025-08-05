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

import com.suven.framework.sys.mapper.SysDictItemMapper;
import com.suven.framework.sys.entity.SysDictItem;
import com.suven.framework.sys.dto.enums.SysDictItemQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysDictItemDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:15
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 的数据库查询逻辑实现类
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



@Service("sysDictItemDao")
public class SysDictItemDao extends AbstractMyBatisDao<SysDictItemMapper, SysDictItem> implements IBaseExcelData{

    @Autowired
    private SysDictItemMapper  sysDictItemMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDictItem>  iPage =  new Query<SysDictItem>().getPage(params);

        QueryWrapper<SysDictItem> query =  new QueryWrapper<>();

        IPage<SysDictItem> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDictItem,并且保存到缓存中
     * @param sysDictItem
     * @author suven
     * date 2022-02-28 16:10:15
     */

    public SysDictItem saveId(SysDictItem sysDictItem){
            if(null == sysDictItem){
                return  null;
           }
          long id = sysDictItemMapper.saveId(sysDictItem);
          if (returnResult(id)){
                return sysDictItem;
          }
           return null;


    }

     /**
         * 保存创建SysDictItem,并且保存到缓存中
         * @param sysDictItem
         * @author suven
         * date 2022-02-28 16:10:15
         */

        public SysDictItem saveToId(SysDictItem sysDictItem){
                if(null == sysDictItem){
                    return  null;
               }
              long id = sysDictItemMapper.saveToId(sysDictItem);
              if (returnResult(id)){
                    return sysDictItem;
              }
               return null;


        }

    /**
     * 批量保存创建SysDictItem,并且保存到缓存中
     * @param sysDictItemList
     * @author suven
     * date 2022-02-28 16:10:15
     */
    public boolean saveBatchId(List<SysDictItem> sysDictItemList){
            if(null == sysDictItemList)
                return  false;
           long id =  sysDictItemMapper.saveBatch(sysDictItemList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDictItem> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDictItem.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDictItem信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    public List<SysDictItem> getListByPage(IPage<SysDictItem> iPage, QueryWrapper<SysDictItem> queryWrapper ){


        List<SysDictItem> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysDictItem> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDictItem>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDictItem信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    public List<SysDictItem> getListByQuery( QueryWrapper<SysDictItem> queryWrapper ){


        List<SysDictItem> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysDictItem> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDictItem不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    public QueryWrapper<SysDictItem> builderQueryEnum(SysDictItemQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDictItem  sysDictItem = SysDictItem.build().clone(queryObject);
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