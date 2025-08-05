package com.suven.framework.sys.dao;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysRoleMapper;
import com.suven.framework.sys.entity.SysRole;
import com.suven.framework.sys.dto.enums.SysRoleQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysRoleDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 的数据库查询逻辑实现类
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



@Service("sysRoleDao")
public class SysRoleDao extends AbstractMyBatisDao<SysRoleMapper, SysRole> implements IBaseExcelData{

    @Autowired
    private SysRoleMapper  sysRoleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRole>  iPage =  new Query<SysRole>().getPage(params);

        QueryWrapper<SysRole> query =  new QueryWrapper<>();

        IPage<SysRole> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysRole,并且保存到缓存中
     * @param sysRole
     * @author suven
     * date 2022-02-28 16:10:43
     */

    public SysRole saveId(SysRole sysRole){
            if(null == sysRole){
                return  null;
           }
          long id = sysRoleMapper.saveId(sysRole);
          if (returnResult(id)){
                return sysRole;
          }
           return null;


    }

     /**
         * 保存创建SysRole,并且保存到缓存中
         * @param sysRole
         * @author suven
         * date 2022-02-28 16:10:43
         */

        public SysRole saveToId(SysRole sysRole){
                if(null == sysRole){
                    return  null;
               }
              long id = sysRoleMapper.saveToId(sysRole);
              if (returnResult(id)){
                    return sysRole;
              }
               return null;


        }

    /**
     * 批量保存创建SysRole,并且保存到缓存中
     * @param sysRoleList
     * @author suven
     * date 2022-02-28 16:10:43
     */
    public boolean saveBatchId(List<SysRole> sysRoleList){
            if(null == sysRoleList)
                return  false;
           long id =  sysRoleMapper.saveBatch(sysRoleList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysRole> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysRole.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysRole信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    public List<SysRole> getListByPage(IPage<SysRole> iPage, QueryWrapper<SysRole> queryWrapper ){


        List<SysRole> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysRole> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysRole>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysRole信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    public List<SysRole> getListByQuery( QueryWrapper<SysRole> queryWrapper ){


        List<SysRole> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysRole> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysRole不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    public QueryWrapper<SysRole> builderQueryEnum(SysRoleQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysRole  sysRole = SysRole.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   if(StringUtils.isNoneBlank(sysRole.getRoleName())){
                       queryWrapper.eq("role_name",sysRole.getRoleName());
                   }
                   if(StringUtils.isNoneBlank(sysRole.getRoleCode())){
                       queryWrapper.eq("role_code",sysRole.getRoleCode());
                   }
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case ROLE_CODE :{
                   queryWrapper.eq("role_code",sysRole.getRoleCode());
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }
}