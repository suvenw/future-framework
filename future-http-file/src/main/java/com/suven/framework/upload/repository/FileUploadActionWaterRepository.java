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
import com.suven.framework.upload.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.upload.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.upload.entity.FileUploadActionWater;
import com.suven.framework.upload.mapper.FileUploadActionWaterMapper;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;




/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:14:12
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

@Repository("fileUploadActionWaterRepository")
public class FileUploadActionWaterRepository extends AbstractMyBatisRepository<FileUploadActionWaterMapper, FileUploadActionWater> implements IBaseExcelData {





    /**
     * 保存创建FileUploadActionWater,并且保存到缓存中
     * @param fileUploadActionWater 表对象
     * @author suven 作者
     * date 2024-04-19 00:14:12 创建时间
     */

    public FileUploadActionWater saveId(FileUploadActionWater fileUploadActionWater){
            if(null == fileUploadActionWater){
                return  null;
           }

          long id = this.getBaseMapper().saveId(fileUploadActionWater);
          if (returnResult(id)){
                return fileUploadActionWater;
          }
           return null;


    }

     /**
         * 保存创建FileUploadActionWater,并且保存到缓存中
         * @param fileUploadActionWater 表参数对象
         * @author suven 作者
         * date 2024-04-19 00:14:12 创建时间
         */

        public FileUploadActionWater saveToId(FileUploadActionWater fileUploadActionWater){
                if(null == fileUploadActionWater){
                    return  null;
               }

              long id = this.getBaseMapper().saveToId(fileUploadActionWater);
              if (returnResult(id)){
                    return fileUploadActionWater;
              }
               return null;


        }

    /**
     * 批量保存创建FileUploadActionWater,并且保存到缓存中
     * @param fileUploadActionWaterList 批量保存表对象列表
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    public boolean saveBatchId(List<FileUploadActionWater> fileUploadActionWaterList){
            if(null == fileUploadActionWaterList){
                return  false;
           }
           long id =  this.getBaseMapper().saveBatch(fileUploadActionWaterList);
           return returnResult(id);

    }

    /**
     * 批量导入列表数据
     * @param list 表对象集合信息
     */
    @Override
    public void saveData(List<Object> list) {
        List<FileUploadActionWater> dataList = IterableConvert.convertList(list,FileUploadActionWater.class);
        this.saveBatch(dataList, BATCH_SIZE);
    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
    public FileUploadActionWater getFileUploadActionWaterByOne(FileUploadActionWaterQueryEnum queryEnum, FileUploadActionWater fileUploadActionWater){
        if(fileUploadActionWater == null ){
            return null;
        }
        Wrapper<FileUploadActionWater> queryWrapper = this.builderQueryEnum( queryEnum, fileUploadActionWater);
        //分页对象        PageHelper
        Page<FileUploadActionWater> iPage = new Page<>(0, 1);
        iPage.setSearchCount(false);
        IPage<FileUploadActionWater> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())){
            return null;
        }
        FileUploadActionWater result = page.getRecords().getFirst();
        return result ;
    }

    /**
     * 通过分页获取FileUploadActionWater信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    public PageResult<FileUploadActionWater> getListByPage(Pager<FileUploadActionWater> pager, Wrapper<FileUploadActionWater> queryWrapper ){

        PageResult<FileUploadActionWater> pageVo = new PageResult<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileUploadActionWater> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileUploadActionWater> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())){
          return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
        }
    /**
     * 通过分页获取FileUploadActionWater信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    public List<FileUploadActionWater> getListByQuery( Wrapper<FileUploadActionWater> queryWrapper ){
        List<FileUploadActionWater> resDtoList = new ArrayList<>();
        if(ObjectTrue.isEmpty(queryWrapper)){
            queryWrapper = new QueryWrapper<>();
        }
        List<FileUploadActionWater> list = super.list(queryWrapper);
        if(ObjectTrue.isEmpty(list)){
            return resDtoList;
        }
        return list;
        }

    /**
     * 通过枚举实现FileUploadActionWater不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    public Wrapper<FileUploadActionWater> builderQueryEnum(FileUploadActionWaterQueryEnum queryEnum, Object queryObject){
           QueryWrapper<FileUploadActionWater> queryWrapper = new QueryWrapper<>();
//           LambdaQueryWrapper<FileUploadActionWater> queryLambdaueryWrapper = this.queryLambda();
//           KtQueryChainWrapper<FileUploadActionWater> queryKtWrapper = this.queryKt();
            if(ObjectTrue.isEmpty(queryEnum)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            if(ObjectTrue.isEmpty(queryObject)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            FileUploadActionWater  fileUploadActionWater = FileUploadActionWater.build().clone(queryObject);
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