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

import com.suven.framework.sys.mapper.SysDictMapper;
import com.suven.framework.sys.entity.SysDict;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysDictDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:09
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 后台字典类型表 的数据库查询逻辑实现类
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



@Service("sysDictDao")
public class SysDictDao extends AbstractMyBatisDao<SysDictMapper, SysDict> implements IBaseExcelData{

    @Autowired
    private SysDictMapper  sysDictMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDict>  iPage =  new Query<SysDict>().getPage(params);

        QueryWrapper<SysDict> query =  new QueryWrapper<>();

        IPage<SysDict> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDict,并且保存到缓存中
     * @param sysDict
     * @author suven
     * date 2022-02-28 16:10:09
     */

    public SysDict saveId(SysDict sysDict){
            if(null == sysDict){
                return  null;
           }
          long id = sysDictMapper.saveId(sysDict);
          if (returnResult(id)){
                return sysDict;
          }
           return null;


    }

     /**
         * 保存创建SysDict,并且保存到缓存中
         * @param sysDict
         * @author suven
         * date 2022-02-28 16:10:09
         */

        public SysDict saveToId(SysDict sysDict){
                if(null == sysDict){
                    return  null;
               }
              long id = sysDictMapper.saveToId(sysDict);
              if (returnResult(id)){
                    return sysDict;
              }
               return null;


        }

    /**
     * 批量保存创建SysDict,并且保存到缓存中
     * @param sysDictList
     * @author suven
     * date 2022-02-28 16:10:09
     */
    public boolean saveBatchId(List<SysDict> sysDictList){
            if(null == sysDictList)
                return  false;
           long id =  sysDictMapper.saveBatch(sysDictList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDict> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDict.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDict信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    public List<SysDict> getListByPage(IPage<SysDict> iPage, QueryWrapper<SysDict> queryWrapper ){


        List<SysDict> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysDict> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDict>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDict信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    public List<SysDict> getListByQuery( QueryWrapper<SysDict> queryWrapper ){


        List<SysDict> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysDict> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDict不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    public QueryWrapper<SysDict> builderQueryEnum(SysDictQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDict  sysDict = SysDict.build().clone(queryObject);
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