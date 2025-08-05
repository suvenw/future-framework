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

import com.suven.framework.sys.mapper.SysDepartRolePermissionMapper;
import com.suven.framework.sys.entity.SysDepartRolePermission;
import com.suven.framework.sys.dto.enums.SysDepartRolePermissionQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysDepartRolePermissionDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:36
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 的数据库查询逻辑实现类
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



@Service("sysDepartRolePermissionDao")
public class SysDepartRolePermissionDao extends AbstractMyBatisDao<SysDepartRolePermissionMapper, SysDepartRolePermission> implements IBaseExcelData{

    @Autowired
    private SysDepartRolePermissionMapper  sysDepartRolePermissionMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDepartRolePermission>  iPage =  new Query<SysDepartRolePermission>().getPage(params);

        QueryWrapper<SysDepartRolePermission> query =  new QueryWrapper<>();

        IPage<SysDepartRolePermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDepartRolePermission,并且保存到缓存中
     * @param sysDepartRolePermission
     * @author suven
     * @date 2022-02-28 16:13:36
     */

    public SysDepartRolePermission saveId(SysDepartRolePermission sysDepartRolePermission){
            if(null == sysDepartRolePermission){
                return  null;
           }
          long id = sysDepartRolePermissionMapper.saveId(sysDepartRolePermission);
          if (returnBool(id)){
                return sysDepartRolePermission;
          }
           return null;


    }

     /**
         * 保存创建SysDepartRolePermission,并且保存到缓存中
         * @param sysDepartRolePermission
         * @author suven
         * @date 2022-02-28 16:13:36
         */

        public SysDepartRolePermission saveToId(SysDepartRolePermission sysDepartRolePermission){
                if(null == sysDepartRolePermission){
                    return  null;
               }
              long id = sysDepartRolePermissionMapper.saveToId(sysDepartRolePermission);
              if (returnBool(id)){
                    return sysDepartRolePermission;
              }
               return null;


        }

    /**
     * 批量保存创建SysDepartRolePermission,并且保存到缓存中
     * @param sysDepartRolePermissionList
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    public boolean saveBatchId(List<SysDepartRolePermission> sysDepartRolePermissionList){
            if(null == sysDepartRolePermissionList)
                return  false;
           long id =  sysDepartRolePermissionMapper.saveBatch(sysDepartRolePermissionList);
           return returnBool(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDepartRolePermission> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDepartRolePermission.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDepartRolePermission信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    public List<SysDepartRolePermission> getListByPage(IPage<SysDepartRolePermission> iPage, QueryWrapper<SysDepartRolePermission> queryWrapper ){


        List<SysDepartRolePermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        IPage<SysDepartRolePermission> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDepartRolePermission>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDepartRolePermission信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    public List<SysDepartRolePermission> getListByQuery( QueryWrapper<SysDepartRolePermission> queryWrapper ){


        List<SysDepartRolePermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        List<SysDepartRolePermission> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDepartRolePermission不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    public QueryWrapper<SysDepartRolePermission> builderQueryEnum(SysDepartRolePermissionQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDepartRolePermission> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDepartRolePermission  sysDepartRolePermission = SysDepartRolePermission.build().clone(queryObject);
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