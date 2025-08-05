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

import com.suven.framework.sys.mapper.SysPermissionDataRuleMapper;
import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysPermissionDataRuleDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:35
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 的数据库查询逻辑实现类
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



@Service("sysPermissionDataRuleDao")
public class SysPermissionDataRuleDao extends AbstractMyBatisDao<SysPermissionDataRuleMapper, SysPermissionDataRule> implements IBaseExcelData{

    @Autowired
    private SysPermissionDataRuleMapper  sysPermissionDataRuleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPermissionDataRule>  iPage =  new Query<SysPermissionDataRule>().getPage(params);

        QueryWrapper<SysPermissionDataRule> query =  new QueryWrapper<>();

        IPage<SysPermissionDataRule> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysPermissionDataRule,并且保存到缓存中
     * @param sysPermissionDataRule
     * @author suven
     * @date 2022-02-28 16:10:35
     */

    public SysPermissionDataRule saveId(SysPermissionDataRule sysPermissionDataRule){
            if(null == sysPermissionDataRule){
                return  null;
           }
          long id = sysPermissionDataRuleMapper.saveId(sysPermissionDataRule);
          if (returnBool(id)){
                return sysPermissionDataRule;
          }
           return null;


    }

     /**
         * 保存创建SysPermissionDataRule,并且保存到缓存中
         * @param sysPermissionDataRule
         * @author suven
         * @date 2022-02-28 16:10:35
         */

        public SysPermissionDataRule saveToId(SysPermissionDataRule sysPermissionDataRule){
                if(null == sysPermissionDataRule){
                    return  null;
               }
              long id = sysPermissionDataRuleMapper.saveToId(sysPermissionDataRule);
              if (returnBool(id)){
                    return sysPermissionDataRule;
              }
               return null;


        }

    /**
     * 批量保存创建SysPermissionDataRule,并且保存到缓存中
     * @param sysPermissionDataRuleList
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    public boolean saveBatchId(List<SysPermissionDataRule> sysPermissionDataRuleList){
            if(null == sysPermissionDataRuleList)
                return  false;
           long id =  sysPermissionDataRuleMapper.saveBatch(sysPermissionDataRuleList);
           return returnBool(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysPermissionDataRule> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysPermissionDataRule.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysPermissionDataRule信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    public List<SysPermissionDataRule> getListByPage(IPage<SysPermissionDataRule> iPage, QueryWrapper<SysPermissionDataRule> queryWrapper ){


        List<SysPermissionDataRule> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        IPage<SysPermissionDataRule> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysPermissionDataRule>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysPermissionDataRule信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    public List<SysPermissionDataRule> getListByQuery( QueryWrapper<SysPermissionDataRule> queryWrapper ){


        List<SysPermissionDataRule> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        List<SysPermissionDataRule> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysPermissionDataRule不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     */
    public QueryWrapper<SysPermissionDataRule> builderQueryEnum(SysPermissionDataRuleQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysPermissionDataRule> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysPermissionDataRule  sysPermissionDataRule = SysPermissionDataRule.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case PERMISSION_ID :{
                   queryWrapper.eq("permission_id",sysPermissionDataRule.getPermissionId());
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }
}