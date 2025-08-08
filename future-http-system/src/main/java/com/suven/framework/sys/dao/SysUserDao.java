package com.suven.framework.sys.dao;


import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysUserMapper;
import com.suven.framework.sys.entity.SysUser;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysUserDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 的数据库查询逻辑实现类
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



@Service("sysUserDao")
public class SysUserDao extends AbstractMyBatisRepository<SysUserMapper, SysUser> implements IBaseExcelData{

    @Autowired
    private SysUserMapper  sysUserMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUser>  iPage =  new Query<SysUser>().getPage(params);

        QueryWrapper<SysUser> query =  new QueryWrapper<>();

        IPage<SysUser> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysUser,并且保存到缓存中
     * @param sysUser
     * @author suven
     * date 2022-02-28 16:09:37
     */

    public SysUser saveId(SysUser sysUser){
            if(null == sysUser){
                return  null;
           }
          long id = sysUserMapper.saveId(sysUser);
          if (returnResult(id)){
                return sysUser;
          }
           return null;


    }

     /**
         * 保存创建SysUser,并且保存到缓存中
         * @param sysUser
         * @author suven
         * date 2022-02-28 16:09:37
         */

        public SysUser saveToId(SysUser sysUser){
                if(null == sysUser){
                    return  null;
               }
              long id = sysUserMapper.saveToId(sysUser);
              if (returnResult(id)){
                    return sysUser;
              }
               return null;


        }

    /**
     * 批量保存创建SysUser,并且保存到缓存中
     * @param sysUserList
     * @author suven
     * date 2022-02-28 16:09:37
     */
    public boolean saveBatchId(List<SysUser> sysUserList){
            if(null == sysUserList)
                return  false;
           long id =  sysUserMapper.saveBatch(sysUserList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysUser> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysUser.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysUser信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    public List<SysUser> getListByPage(IPage<SysUser> iPage, QueryWrapper<SysUser> queryWrapper ){


        List<SysUser> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysUser> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysUser>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysUser信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    public List<SysUser> getListByQuery( QueryWrapper<SysUser> queryWrapper ){


        List<SysUser> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysUser> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysUser不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:09:37
     */
    public QueryWrapper<SysUser> builderQueryEnum(SysUserQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
              throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL);
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysUser  sysUser = SysUser.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   if(StringUtils.isNoneBlank(sysUser.getUsername())){
                       queryWrapper.eq("username",sysUser.getUsername());
                   }
                   if(StringUtils.isNoneBlank(sysUser.getPhone())){
                       queryWrapper.eq("phone",sysUser.getPhone());
                   }
                   if(sysUser.getSex() >0){
                       queryWrapper.eq("sex",sysUser.getSex());
                   }
                   if(sysUser.getStatus() >0){
                       queryWrapper.eq("status",sysUser.getStatus());
                   }
                   if(StringUtils.isNoneBlank(sysUser.getRealname())){
                       queryWrapper.eq("realname",sysUser.getRealname());
                   }
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case USER_NAME :{
                   queryWrapper.eq("username",sysUser.getUsername());
                   break;
               }
               case USER_NAME_OR_PHONE :{
                   queryWrapper.eq("username",sysUser.getUsername());
                   queryWrapper.or().eq("phone",sysUser.getUsername());
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }

    public List<SysUser> getSysUserRoleId(Page<SysUser> iPage, long roleId, String username) {
        IPage<SysUser> sysUserIPage =  sysUserMapper.getUserByRoleId(iPage,roleId,username);
        return sysUserIPage.getRecords();
    }
}