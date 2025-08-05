package com.suven.framework.sys.dao;


import java.util.Map;

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

import com.suven.framework.sys.mapper.SysUserRoleMapper;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysUserRoleDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:11:27
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 的数据库查询逻辑实现类
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



@Service("sysUserRoleDao")
public class SysUserRoleDao extends AbstractMyBatisDao<SysUserRoleMapper, SysUserRole> implements IBaseExcelData{

    @Autowired
    private SysUserRoleMapper  sysUserRoleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRole>  iPage =  new Query<SysUserRole>().getPage(params);

        QueryWrapper<SysUserRole> query =  new QueryWrapper<>();

        IPage<SysUserRole> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysUserRole,并且保存到缓存中
     * @param sysUserRole
     * @author suven
     * @date 2022-02-28 16:11:27
     */

    public SysUserRole saveId(SysUserRole sysUserRole){
            if(null == sysUserRole){
                return  null;
           }
          long id = sysUserRoleMapper.saveId(sysUserRole);
          if (returnResult(id)){
                return sysUserRole;
          }
           return null;


    }

     /**
         * 保存创建SysUserRole,并且保存到缓存中
         * @param sysUserRole
         * @author suven
         * @date 2022-02-28 16:11:27
         */

        public SysUserRole saveToId(SysUserRole sysUserRole){
                if(null == sysUserRole){
                    return  null;
               }
              long id = sysUserRoleMapper.saveToId(sysUserRole);
              if (returnResult(id)){
                    return sysUserRole;
              }
               return null;


        }

    /**
     * 批量保存创建SysUserRole,并且保存到缓存中
     * @param sysUserRoleList
     * @author suven
     * @date 2022-02-28 16:11:27
     */
    public boolean saveBatchId(List<SysUserRole> sysUserRoleList){
            if(null == sysUserRoleList)
                return  false;
           long id =  sysUserRoleMapper.saveBatch(sysUserRoleList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysUserRole> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysUserRole.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysUserRole信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:11:27
     */
    public List<SysUserRole> getListByPage(IPage<SysUserRole> iPage, QueryWrapper<SysUserRole> queryWrapper ){


        List<SysUserRole> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysUserRole> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysUserRole>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysUserRole信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:11:27
     */
    public List<SysUserRole> getListByQuery( QueryWrapper<SysUserRole> queryWrapper ){


        List<SysUserRole> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysUserRole> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysUserRole不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:11:27
     */
    public QueryWrapper<SysUserRole> builderQueryEnum(SysUserRoleQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            if(queryEnum == null){
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL);
            }
           if(queryObject == null){
               return queryWrapper;
           }
            SysUserRole  sysUserRole = SysUserRole.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case USER_ID :{
                   queryWrapper.eq("user_id", sysUserRole.getUserId());
                   break;
               }
               case ROLE_ID :{
                   queryWrapper.eq("role_id", sysUserRole.getRoleId());
                   break;
               }

               default:
                   break;
           }
          return queryWrapper;
       }
}