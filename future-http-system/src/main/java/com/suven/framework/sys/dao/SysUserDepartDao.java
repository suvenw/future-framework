package com.suven.framework.sys.dao;


import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysUserDepartMapper;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysUserDepartDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:14
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 的数据库查询逻辑实现类
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



@Service("sysUserDepartDao")
public class SysUserDepartDao extends AbstractMyBatisDao<SysUserDepartMapper, SysUserDepart> implements IBaseExcelData{

    @Autowired
    private SysUserDepartMapper  sysUserDepartMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserDepart>  iPage =  new Query<SysUserDepart>().getPage(params);

        QueryWrapper<SysUserDepart> query =  new QueryWrapper<>();

        IPage<SysUserDepart> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysUserDepart,并且保存到缓存中
     * @param sysUserDepart
     * @author suven
     * @date 2022-02-28 16:14:14
     */

    public SysUserDepart saveId(SysUserDepart sysUserDepart){
            if(null == sysUserDepart){
                return  null;
           }
          long id = sysUserDepartMapper.saveId(sysUserDepart);
          if (returnResult(id)){
                return sysUserDepart;
          }
           return null;


    }

     /**
         * 保存创建SysUserDepart,并且保存到缓存中
         * @param sysUserDepart
         * @author suven
         * @date 2022-02-28 16:14:14
         */

        public SysUserDepart saveToId(SysUserDepart sysUserDepart){
                if(null == sysUserDepart){
                    return  null;
               }
              long id = sysUserDepartMapper.saveToId(sysUserDepart);
              if (returnResult(id)){
                    return sysUserDepart;
              }
               return null;


        }

    /**
     * 批量保存创建SysUserDepart,并且保存到缓存中
     * @param sysUserDepartList
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    public boolean saveBatchId(List<SysUserDepart> sysUserDepartList){
            if(null == sysUserDepartList)
                return  false;
           long id =  sysUserDepartMapper.saveBatch(sysUserDepartList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysUserDepart> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysUserDepart.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysUserDepart信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    public List<SysUserDepart> getListByPage(IPage<SysUserDepart> iPage, QueryWrapper<SysUserDepart> queryWrapper ){


        List<SysUserDepart> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysUserDepart> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysUserDepart>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysUserDepart信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    public List<SysUserDepart> getListByQuery( QueryWrapper<SysUserDepart> queryWrapper ){


        List<SysUserDepart> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysUserDepart> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysUserDepart不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    public QueryWrapper<SysUserDepart> builderQueryEnum(SysUserDepartQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();
        if(queryEnum == null){
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL);
        }
           if(queryObject == null){
               return queryWrapper;
           }
            SysUserDepart  sysUserDepart = SysUserDepart.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case USER_ID :{
                   queryWrapper.eq("user_id", sysUserDepart.getUserId());
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }

    public SysUserDepart getUserDepartByDepIdAndUserId(long sysDepId, long sysUserId){
        QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserId);
        queryWrapper.eq("dep_id", sysDepId);
        Page<SysUserDepart> iPage = new Page<>(0, 1);
        iPage.setSearchCount(false);
        List<SysUserDepart>  list = this.getListByPage(iPage,queryWrapper);
        if(null == list || list.isEmpty()){
            return null;
        }
        SysUserDepart sysUserDepart = list.get(0);

        return sysUserDepart ;
    }

    public List<Long> getUserIdByDepId(long depId) {
        this.slaveDataSource();
        return this.baseMapper.getUserIdByDepId(depId);
    }
}