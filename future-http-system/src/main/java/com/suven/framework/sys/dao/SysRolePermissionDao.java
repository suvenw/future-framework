package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.sys.dto.enums.SysRolePermissionQueryEnum;
import com.suven.framework.sys.entity.SysRolePermission;
import com.suven.framework.sys.mapper.SysRolePermissionMapper;
import com.suven.framework.sys.vo.response.SysRolePermissionResponseVo;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @ClassName: SysRolePermissionDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:49
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 的数据库查询逻辑实现类
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



@Service("sysRolePermissionDao")
public class SysRolePermissionDao extends AbstractMyBatisDao<SysRolePermissionMapper, SysRolePermission> implements IBaseExcelData{

    @Autowired
    private SysRolePermissionMapper  sysRolePermissionMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRolePermission>  iPage =  new Query<SysRolePermission>().getPage(params);

        QueryWrapper<SysRolePermission> query =  new QueryWrapper<>();

        IPage<SysRolePermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysRolePermission,并且保存到缓存中
     * @param sysRolePermission
     * @author suven
     * @date 2022-02-28 16:10:49
     */

    public SysRolePermission saveId(SysRolePermission sysRolePermission){
            if(null == sysRolePermission){
                return  null;
           }
          long id = sysRolePermissionMapper.saveId(sysRolePermission);
          if (returnResult(id)){
                return sysRolePermission;
          }
           return null;


    }

     /**
         * 保存创建SysRolePermission,并且保存到缓存中
         * @param sysRolePermission
         * @author suven
         * @date 2022-02-28 16:10:49
         */

        public SysRolePermission saveToId(SysRolePermission sysRolePermission){
                if(null == sysRolePermission){
                    return  null;
               }
              long id = sysRolePermissionMapper.saveToId(sysRolePermission);
              if (returnResult(id)){
                    return sysRolePermission;
              }
               return null;


        }

    /**
     * 批量保存创建SysRolePermission,并且保存到缓存中
     * @param sysRolePermissionList
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    public boolean saveBatchId(List<SysRolePermission> sysRolePermissionList){
            if(null == sysRolePermissionList)
                return  false;
           long id =  sysRolePermissionMapper.saveBatch(sysRolePermissionList);
           return returnResult(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysRolePermission> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysRolePermission.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过ID列表获取SysRolePermission信息实现查找缓存和数据库的方法
     * @param idList
     * @return List<SysRolePermissionResponseVo>
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    List<SysRolePermissionResponseVo>   selectBatchIds(Collection<? extends Serializable > idList){
        List<SysRolePermission>  list =   super.baseMapper.selectBatchIds(idList);
        List<SysRolePermissionResponseVo> result =  list.stream().map(obj -> {
            SysRolePermissionResponseVo vo =  SysRolePermissionResponseVo.build();
            vo.clone(obj);
            return vo;
        }).collect(Collectors.toList());
        return result;
      }

    public List<SysRolePermission> getListByPage(IPage<SysRolePermission> iPage, QueryWrapper<SysRolePermission> queryWrapper ){
        List<SysRolePermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<SysRolePermission> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysRolePermission>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        return list;

        }
    /**
     * 通过分页获取SysRolePermission信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    public List<SysRolePermission> getListByQuery( QueryWrapper<SysRolePermission> queryWrapper ){


        List<SysRolePermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        List<SysRolePermission> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysRolePermission不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    public QueryWrapper<SysRolePermission> builderQueryEnum(SysRolePermissionQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysRolePermission  sysRolePermission = SysRolePermission.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               case ROLE_ID :{
                   queryWrapper.eq("role_id", sysRolePermission.getRoleId());
                   break;
               }

               default:
                   break;
           }
          return queryWrapper;
       }
}