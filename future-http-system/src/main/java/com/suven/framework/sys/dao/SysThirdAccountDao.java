package com.suven.framework.sys.dao;


import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysThirdAccountMapper;
import com.suven.framework.sys.entity.SysThirdAccount;
import com.suven.framework.sys.dto.enums.SysThirdAccountQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * ClassName: SysThirdAccountDao.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:47
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 的数据库查询逻辑实现类
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



@Service("sysThirdAccountDao")
public class SysThirdAccountDao extends AbstractMyBatisRepository<SysThirdAccountMapper, SysThirdAccount> implements IBaseExcelData{

    @Autowired
    private SysThirdAccountMapper  sysThirdAccountMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysThirdAccount>  iPage =  new Query<SysThirdAccount>().getPage(params);

        QueryWrapper<SysThirdAccount> query =  new QueryWrapper<>();

        IPage<SysThirdAccount> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysThirdAccount,并且保存到缓存中
     * @param sysThirdAccount
     * @author suven
     * date 2022-02-28 16:09:47
     */

    public SysThirdAccount saveId(SysThirdAccount sysThirdAccount){
            if(null == sysThirdAccount){
                return  null;
           }
          long id = sysThirdAccountMapper.saveId(sysThirdAccount);
          if (returnResult(id)){
                return sysThirdAccount;
          }
           return null;


    }

     /**
         * 保存创建SysThirdAccount,并且保存到缓存中
         * @param sysThirdAccount
         * @author suven
         * date 2022-02-28 16:09:47
         */

        public SysThirdAccount saveToId(SysThirdAccount sysThirdAccount){
                if(null == sysThirdAccount){
                    return  null;
               }
              long id = sysThirdAccountMapper.saveToId(sysThirdAccount);
              if (returnResult(id)){
                    return sysThirdAccount;
              }
               return null;


        }

    /**
     * 批量保存创建SysThirdAccount,并且保存到缓存中
     * @param sysThirdAccountList
     * @author suven
     * date 2022-02-28 16:09:47
     */
    public boolean saveBatchId(List<SysThirdAccount> sysThirdAccountList){
            if(null == sysThirdAccountList)
                return  false;
           long id =  sysThirdAccountMapper.saveBatch(sysThirdAccountList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysThirdAccount> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysThirdAccount.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysThirdAccount信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:09:47
     */
    public List<SysThirdAccount> getListByPage(IPage<SysThirdAccount> iPage, QueryWrapper<SysThirdAccount> queryWrapper ){


        List<SysThirdAccount> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysThirdAccount> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysThirdAccount>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysThirdAccount信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * date 2022-02-28 16:09:47
     */
    public List<SysThirdAccount> getListByQuery( QueryWrapper<SysThirdAccount> queryWrapper ){


        List<SysThirdAccount> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysThirdAccount> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysThirdAccount不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * date 2022-02-28 16:09:47
     */
    public QueryWrapper<SysThirdAccount> builderQueryEnum(SysThirdAccountQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysThirdAccount> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysThirdAccount  sysThirdAccount = SysThirdAccount.build().clone(queryObject);
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