package com.suven.framework.sys.dao;


import java.util.Arrays;
import java.util.Map;

import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysPermissionMapper;
import com.suven.framework.sys.entity.SysPermission;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysPermissionDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:30
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限表 的数据库查询逻辑实现类
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



@Service("sysPermissionDao")
public class SysPermissionDao extends AbstractMyBatisDao<SysPermissionMapper, SysPermission> implements IBaseExcelData{

    @Autowired
    private SysPermissionMapper  sysPermissionMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPermission>  iPage =  new Query<SysPermission>().getPage(params);

        QueryWrapper<SysPermission> query =  new QueryWrapper<>();

        IPage<SysPermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysPermission,并且保存到缓存中
     * @param sysPermission
     * @author suven
     * @date 2022-02-28 16:10:30
     */

    public SysPermission saveId(SysPermission sysPermission){
            if(null == sysPermission){
                return  null;
           }
          long id = sysPermissionMapper.saveId(sysPermission);
          if (returnResult(id)){
                return sysPermission;
          }
           return null;


    }

     /**
         * 保存创建SysPermission,并且保存到缓存中
         * @param sysPermission
         * @author suven
         * @date 2022-02-28 16:10:30
         */

        public SysPermission saveToId(SysPermission sysPermission){
                if(null == sysPermission){
                    return  null;
               }
              long id = sysPermissionMapper.saveToId(sysPermission);
              if (returnResult(id)){
                    return sysPermission;
              }
               return null;


        }

    /**
     * 批量保存创建SysPermission,并且保存到缓存中
     * @param sysPermissionList
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    public boolean saveBatchId(List<SysPermission> sysPermissionList){
            if(null == sysPermissionList)
                return  false;
           long id =  sysPermissionMapper.saveBatch(sysPermissionList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysPermission> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysPermission.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysPermission信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    public List<SysPermission> getListByPage(IPage<SysPermission> iPage, QueryWrapper<SysPermission> queryWrapper ){


        List<SysPermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysPermission> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysPermission>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysPermission信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    public List<SysPermission> getListByQuery( QueryWrapper<SysPermission> queryWrapper ){


        List<SysPermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysPermission> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysPermission不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    public QueryWrapper<SysPermission> builderQueryEnum(SysPermissionQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysPermission  sysPermission = SysPermission.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   //菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
                   if(sysPermission.getMenuType() != 3){
                       queryWrapper.eq("menu_type",sysPermission.getMenuType())
                               .eq("del_flag",0);
                   }
                   queryWrapper.orderByDesc("sort");
                   break;
               } case MENU_TYPE_DEL_FLAG :{
                   queryWrapper.eq("menu_type",sysPermission.getMenuType())
                           .eq("del_flag",sysPermission.getDelFlag());
                   break;
               }

               default:
                   break;
           }
          return queryWrapper;
       }
    public List<SysPermission> getSysPermissionList(SysPermission sysPermission) {

        List<SysPermissionResponseDto> resDtoList = new ArrayList<>();
        QueryWrapper<SysPermission> query = new QueryWrapper<>();
        if (sysPermission.getMenuType()>=0) {
            query.eq("menu_type",sysPermission.getMenuType());
        }
        if (sysPermission.getMenuType() == -2) {
            query.in("menu_type", Arrays.asList("0","1"));
        }
        if (sysPermission.getDelFlag()>=0) {
            query.eq("del_flag", sysPermission.getDelFlag());
        }
        if (sysPermission.getParentId()>0) {
            query.eq("parent_id", sysPermission.getParentId());
        }
        query.orderByDesc("sort");
        List<SysPermission> list = this.getListByQuery(query);

        return list;
    }

    public List<SysPermission> queryByUserId(long userId) {
        this.slaveDataSource();
        return this.baseMapper.queryByUserId(userId);
    }
}