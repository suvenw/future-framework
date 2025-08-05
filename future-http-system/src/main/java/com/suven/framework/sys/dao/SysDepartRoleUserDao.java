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

import com.suven.framework.sys.mapper.SysDepartRoleUserMapper;
import com.suven.framework.sys.entity.SysDepartRoleUser;
import com.suven.framework.sys.dto.enums.SysDepartRoleUserQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysDepartRoleUserDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:53:58
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 的数据库查询逻辑实现类
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



@Service("sysDepartRoleUserDao")
public class SysDepartRoleUserDao extends AbstractMyBatisDao<SysDepartRoleUserMapper, SysDepartRoleUser> implements IBaseExcelData{

    @Autowired
    private SysDepartRoleUserMapper  sysDepartRoleUserMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDepartRoleUser>  iPage =  new Query<SysDepartRoleUser>().getPage(params);

        QueryWrapper<SysDepartRoleUser> query =  new QueryWrapper<>();

        IPage<SysDepartRoleUser> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDepartRoleUser,并且保存到缓存中
     * @param sysDepartRoleUser
     * @author suven
     * date 2022-02-28 16:53:58
     */

    public SysDepartRoleUser saveId(SysDepartRoleUser sysDepartRoleUser){
            if(null == sysDepartRoleUser){
                return  null;
           }
          long id = sysDepartRoleUserMapper.saveId(sysDepartRoleUser);
          if (returnResult(id)){
                return sysDepartRoleUser;
          }
           return null;


    }

     /**
         * 保存创建SysDepartRoleUser,并且保存到缓存中
         * @param sysDepartRoleUser
         * @author suven
         * date 2022-02-28 16:53:58
         */

        public SysDepartRoleUser saveToId(SysDepartRoleUser sysDepartRoleUser){
                if(null == sysDepartRoleUser){
                    return  null;
               }
              long id = sysDepartRoleUserMapper.saveToId(sysDepartRoleUser);
              if (returnResult(id)){
                    return sysDepartRoleUser;
              }
               return null;


        }

    /**
     * 批量保存创建SysDepartRoleUser,并且保存到缓存中
     * @param sysDepartRoleUserList
     * @author suven
     * date 2022-02-28 16:53:58
     */
    public boolean saveBatchId(List<SysDepartRoleUser> sysDepartRoleUserList){
            if(null == sysDepartRoleUserList)
                return  false;
           long id =  sysDepartRoleUserMapper.saveBatch(sysDepartRoleUserList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDepartRoleUser> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDepartRoleUser.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDepartRoleUser信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:53:58
     */
    public List<SysDepartRoleUser> getListByPage(IPage<SysDepartRoleUser> iPage, QueryWrapper<SysDepartRoleUser> queryWrapper ){


        List<SysDepartRoleUser> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysDepartRoleUser> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDepartRoleUser>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDepartRoleUser信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:53:58
     */
    public List<SysDepartRoleUser> getListByQuery( QueryWrapper<SysDepartRoleUser> queryWrapper ){


        List<SysDepartRoleUser> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysDepartRoleUser> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDepartRoleUser不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:53:58
     */
    public QueryWrapper<SysDepartRoleUser> builderQueryEnum(SysDepartRoleUserQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDepartRoleUser> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDepartRoleUser  sysDepartRoleUser = SysDepartRoleUser.build().clone(queryObject);
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