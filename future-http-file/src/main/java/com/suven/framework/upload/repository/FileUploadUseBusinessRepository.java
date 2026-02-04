package com.suven.framework.upload.repository;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.AssertEx;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.upload.dto.enums.FileUploadUseBusinessQueryEnum;
import com.suven.framework.upload.entity.FileUploadUseBusiness;
import com.suven.framework.upload.mapper.FileUploadUseBusinessMapper;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:42
 * <pre>
 *
 *  Description:  的数据库查询逻辑实现类
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

@Repository("fileUploadUseBusinessRepository")
public class FileUploadUseBusinessRepository extends AbstractMyBatisRepository<FileUploadUseBusinessMapper, FileUploadUseBusiness> implements IBaseExcelData {





    /**
     * 保存创建FileUploadUseBusiness,并且保存到缓存中
     * @param fileUploadUseBusiness 表对象
     * @author suven 作者
     * date 2024-04-19 00:21:42 创建时间
     */

    public FileUploadUseBusiness saveId(FileUploadUseBusiness fileUploadUseBusiness){
            if(null == fileUploadUseBusiness){
                return  null;
           }

          long id = this.getBaseMapper().saveId(fileUploadUseBusiness);
          if (returnResult(id)){
                return fileUploadUseBusiness;
          }
           return null;


    }

     /**
         * 保存创建FileUploadUseBusiness,并且保存到缓存中
         * @param fileUploadUseBusiness 表参数对象
         * @author suven 作者
         * date 2024-04-19 00:21:42 创建时间
         */

        public FileUploadUseBusiness saveToId(FileUploadUseBusiness fileUploadUseBusiness){
                if(null == fileUploadUseBusiness){
                    return  null;
               }

              long id = this.getBaseMapper().saveToId(fileUploadUseBusiness);
              if (returnResult(id)){
                    return fileUploadUseBusiness;
              }
               return null;


        }

    /**
     * 批量保存创建FileUploadUseBusiness,并且保存到缓存中
     * @param fileUploadUseBusinessList 批量保存表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    public boolean saveBatchId(List<FileUploadUseBusiness> fileUploadUseBusinessList){
            if(null == fileUploadUseBusinessList){
                return  false;
           }
           long id =  this.getBaseMapper().saveBatch(fileUploadUseBusinessList);
           return returnResult(id);

    }

    /**
     * 批量导入列表数据
     * @param list 表对象集合信息
     */
    @Override
    public void saveData(List<Object> list) {
        List<FileUploadUseBusiness> dataList = IterableConvert.convertList(list,FileUploadUseBusiness.class);
        this.saveBatch(dataList, BATCH_SIZE);
    }

    /**
     * 通过分页获取FileUploadUseBusiness信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    public List<FileUploadUseBusiness> getListByPage(Pager<FileUploadUseBusiness> pager, Wrapper<FileUploadUseBusiness> queryWrapper ){

        List<FileUploadUseBusiness> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileUploadUseBusiness> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileUploadUseBusiness> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page)){
          return resDtoList;
        }
        List<FileUploadUseBusiness>  list = page.getRecords();
        pager.setTotal(page.getTotal());
        if(ObjectTrue.isEmpty(list)){
          return resDtoList;
        }
          return list;
        }
    /**
     * 通过分页获取FileUploadUseBusiness信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    public List<FileUploadUseBusiness> getListByQuery( Wrapper<FileUploadUseBusiness> queryWrapper ){
        List<FileUploadUseBusiness> resDtoList = new ArrayList<>();
        if(ObjectTrue.isEmpty(queryWrapper)){
            queryWrapper = new QueryWrapper<>();
        }
        List<FileUploadUseBusiness> list = super.list(queryWrapper);
        if(ObjectTrue.isEmpty(list)){
            return resDtoList;
        }
        return list;
        }

    /**
     * 通过枚举实现FileUploadUseBusiness不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    public Wrapper<FileUploadUseBusiness> builderQueryEnum(FileUploadUseBusinessQueryEnum queryEnum, Object queryObject){
           QueryWrapper<FileUploadUseBusiness> queryWrapper = new QueryWrapper<>();
//           LambdaQueryWrapper<FileUploadActionWater> queryLambdaueryWrapper = this.queryLambda();
//           KtQueryChainWrapper<FileUploadActionWater> queryKtWrapper = this.queryKt();
            if(ObjectTrue.isEmpty(queryEnum)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            if(ObjectTrue.isEmpty(queryObject)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            FileUploadUseBusiness  fileUploadUseBusiness = FileUploadUseBusiness.build().clone(queryObject);
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