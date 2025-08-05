package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.mapper.SysDepartMapper;
import com.suven.framework.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:31
 * Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 组织机构表 的数据库查询逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by "<a href="https://www.suven.top">微学网络</a>"
 **/



@Service("sysDepartDao")
public class SysDepartDao extends AbstractMyBatisDao<SysDepartMapper, SysDepart> implements IBaseExcelData{



    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDepart>  iPage =  new Query<SysDepart>().getPage(params);

        QueryWrapper<SysDepart> query =  new QueryWrapper<>();

        IPage<SysDepart> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDepart,并且保存到缓存中
     * @param sysDepart 部门信息
     * @author suven
     * date 2022-02-28 16:13:31
     */

    public SysDepart saveId(SysDepart sysDepart){
            if(null == sysDepart){
                return  null;
           }
          long id = this.getBaseMapper().saveId(sysDepart);
          if (returnResult(id)){
                return sysDepart;
          }
           return null;


    }

     /**
         * 保存创建SysDepart,并且保存到缓存中
         * @param sysDepart  部门信息
         * @author suven
         * date 2022-02-28 16:13:31
         */

        public SysDepart saveToId(SysDepart sysDepart){
                if(null == sysDepart){
                    return  null;
               }
              long id = this.getBaseMapper().saveToId(sysDepart);
              if (returnResult(id)){
                    return sysDepart;
              }
               return null;


        }

    /**
     * 批量保存创建SysDepart,并且保存到缓存中
     * param sysDepartList  部门信息列表
     * @author suven
     * date 2022-02-28 16:13:31
     */
    public boolean saveBatchId(List<SysDepart> sysDepartList){
            if(null == sysDepartList)
                return  false;
           long id =  this.getBaseMapper().saveBatch(sysDepartList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDepart> dataList = new ArrayList<>();
        list.forEach(e -> dataList.add(SysDepart.build().clone(e)));
        super.saveBatch(dataList, BATCH_SIZE);
    }

    /**
     * 通过分页获取SysDepart信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage 分页对象
     * @return List<SysDepart> 部门信息列表
     * @author suven
     * date 2022-02-28 16:13:31
     */
    public List<SysDepart> getListByPage(IPage<SysDepart> iPage, QueryWrapper<SysDepart> queryWrapper ){


        List<SysDepart> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysDepart> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDepart>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDepart信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return List<SysDepart> 部门信息列表
     * @author suven
     * date 2022-02-28 16:13:31
     */
    public List<SysDepart> getListByQuery( QueryWrapper<SysDepart> queryWrapper ){


        List<SysDepart> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysDepart> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDepart不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return List<SysDepart> 部门信息列表
     * @author suven
     * date 2022-02-28 16:13:31
     */
    public QueryWrapper<SysDepart> builderQueryEnum(SysDepartQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDepart  sysDepart = SysDepart.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{

                   queryWrapper.orderByDesc("id");
                   break;
               }
               case DEPART_NAME :{
                   if(StringUtils.isNoneBlank(sysDepart.getDepartName())){
                       queryWrapper.like("depart_name" , sysDepart.getDepartName());
                   }
                   queryWrapper.orderByDesc("id");
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }
}