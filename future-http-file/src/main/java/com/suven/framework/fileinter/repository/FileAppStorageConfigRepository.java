package com.suven.framework.fileinter.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.AssertEx;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisDao;
import com.suven.framework.fileinter.dto.enums.FileAppStorageConfigQueryEnum;
import com.suven.framework.fileinter.entity.FileAppStorageConfig;
import com.suven.framework.fileinter.mapper.FileAppStorageConfigMapper;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;



/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
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

@Repository("fileAppStorageConfigRepository")
public class FileAppStorageConfigRepository extends AbstractMyBatisDao<FileAppStorageConfigMapper, FileAppStorageConfig> implements IBaseExcelData {





    /**
     * 保存创建FileAppStorageConfig,并且保存到缓存中
     * @param fileAppStorageConfig 表对象
     * @author suven 作者
     * date 2024-04-19 00:21:54 创建时间
     */

    public FileAppStorageConfig saveId(FileAppStorageConfig fileAppStorageConfig){
            if(null == fileAppStorageConfig){
                return  null;
           }

          long id = this.getBaseMapper().saveId(fileAppStorageConfig);
          if (returnBool(id)){
                return fileAppStorageConfig;
          }
           return null;


    }

     /**
         * 保存创建FileAppStorageConfig,并且保存到缓存中
         * @param fileAppStorageConfig 表参数对象
         * @author suven 作者
         * date 2024-04-19 00:21:54 创建时间
         */

        public FileAppStorageConfig saveToId(FileAppStorageConfig fileAppStorageConfig){
                if(null == fileAppStorageConfig){
                    return  null;
               }

              long id = this.getBaseMapper().saveToId(fileAppStorageConfig);
              if (returnBool(id)){
                    return fileAppStorageConfig;
              }
               return null;


        }

    /**
     * 批量保存创建FileAppStorageConfig,并且保存到缓存中
     * @param fileAppStorageConfigList 批量保存表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    public boolean saveBatchId(List<FileAppStorageConfig> fileAppStorageConfigList){
            if(null == fileAppStorageConfigList){
                return  false;
           }
           long id =  this.getBaseMapper().saveBatch(fileAppStorageConfigList);
           return returnBool(id);

    }

    /**
     * 批量导入列表数据
     * @param list 表对象集合信息
     */
    @Override
    public void saveData(List<Object> list) {
        List<FileAppStorageConfig> dataList = IterableConvert.convertList(list,FileAppStorageConfig.class);
        this.saveBatch(dataList, BATCH_SIZE);
    }

    /**
     * 通过分页获取FileAppStorageConfig信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    public List<FileAppStorageConfig> getListByPage(Pager<FileAppStorageConfig> pager, Wrapper<FileAppStorageConfig> queryWrapper ){

        List<FileAppStorageConfig> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }
        Page<FileAppStorageConfig> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileAppStorageConfig> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page)){
          return resDtoList;
        }
        List<FileAppStorageConfig>  list = page.getRecords();
        pager.setTotal(page.getTotal());
        if(ObjectTrue.isEmpty(list)){
          return resDtoList;
        }
          return list;
        }
    /**
     * 通过分页获取FileAppStorageConfig信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    public List<FileAppStorageConfig> getListByQuery( Wrapper<FileAppStorageConfig> queryWrapper ){
        List<FileAppStorageConfig> resDtoList = new ArrayList<>();
        if(ObjectTrue.isEmpty(queryWrapper)){
            queryWrapper = new QueryWrapper<>();
        }
        List<FileAppStorageConfig> list = super.list(queryWrapper);
        if(ObjectTrue.isEmpty(list)){
            return resDtoList;
        }
        return list;
        }

    /**
     * 通过枚举实现FileAppStorageConfig不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    public Wrapper<FileAppStorageConfig> builderQueryEnum(FileAppStorageConfigQueryEnum queryEnum, Object queryObject){
           QueryWrapper<FileAppStorageConfig> queryWrapper = new QueryWrapper<>();
//           LambdaQueryWrapper<FileUploadActionWater> queryLambdaueryWrapper = this.queryLambda();
//           KtQueryChainWrapper<FileUploadActionWater> queryKtWrapper = this.queryKt();
            if(ObjectTrue.isEmpty(queryEnum)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            if(ObjectTrue.isEmpty(queryObject)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            FileAppStorageConfig  fileAppStorageConfig = FileAppStorageConfig.build().clone(queryObject);
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