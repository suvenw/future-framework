package com.suven.framework.fileinter.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.AssertEx;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.fileinter.dto.enums.FileDataDetailedQueryEnum;
import com.suven.framework.fileinter.entity.FileDataDetailed;
import com.suven.framework.fileinter.mapper.FileDataDetailedMapper;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:20:28
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

@Repository("fileDataDetailedRepository")
public class FileDataDetailedRepository extends AbstractMyBatisRepository<FileDataDetailedMapper, FileDataDetailed> implements IBaseExcelData {





    /**
     * 保存创建FileDataDetailed,并且保存到缓存中
     * @param fileDataDetailed 表对象
     * @author suven 作者
     * date 2024-04-19 00:20:28 创建时间
     */

    public FileDataDetailed saveId(FileDataDetailed fileDataDetailed){
            if(null == fileDataDetailed){
                return  null;
           }

          long id = this.getBaseMapper().saveId(fileDataDetailed);
          if (returnResult(id)){
                return fileDataDetailed;
          }
           return null;


    }

     /**
         * 保存创建FileDataDetailed,并且保存到缓存中
         * @param fileDataDetailed 表参数对象
         * @author suven 作者
         * date 2024-04-19 00:20:28 创建时间
         */

        public FileDataDetailed saveToId(FileDataDetailed fileDataDetailed){
                if(null == fileDataDetailed){
                    return  null;
               }

              long id = this.getBaseMapper().saveToId(fileDataDetailed);
              if (returnResult(id)){
                    return fileDataDetailed;
              }
               return null;


        }

    /**
     * 批量保存创建FileDataDetailed,并且保存到缓存中
     * @param fileDataDetailedList 批量保存表对象列表
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    public boolean saveBatchId(List<FileDataDetailed> fileDataDetailedList){
            if(null == fileDataDetailedList){
                return  false;
           }
           long id =  this.getBaseMapper().saveBatch(fileDataDetailedList);
           return returnResult(id);

    }

    /**
     * 批量导入列表数据
     * @param list 表对象集合信息
     */
    @Override
    public void saveData(List<Object> list) {
        List<FileDataDetailed> dataList = IterableConvert.convertList(list,FileDataDetailed.class);
        this.saveBatch(dataList, BATCH_SIZE);
    }

    /**
     * 通过分页获取FileDataDetailed信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    public PageResult<FileDataDetailed> getListByPage(Pager<FileDataDetailed> pager, Wrapper<FileDataDetailed> queryWrapper ){

        PageResult<FileDataDetailed> pageVo = new PageResult<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileDataDetailed> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileDataDetailed> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())){
          return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
          return pageVo;
        }
    /**
     * 通过分页获取FileDataDetailed信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    public List<FileDataDetailed> getListByQuery( Wrapper<FileDataDetailed> queryWrapper ){
        List<FileDataDetailed> resDtoList = new ArrayList<>();
        if(ObjectTrue.isEmpty(queryWrapper)){
            queryWrapper = new QueryWrapper<>();
        }
        List<FileDataDetailed> list = super.list(queryWrapper);
        if(ObjectTrue.isEmpty(list)){
            return resDtoList;
        }
        return list;
        }

    /**
     * 通过枚举实现FileDataDetailed不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    public Wrapper<FileDataDetailed> builderQueryEnum(FileDataDetailedQueryEnum queryEnum, Object queryObject){
           QueryWrapper<FileDataDetailed> queryWrapper = new QueryWrapper<>();
//           LambdaQueryWrapper<FileUploadActionWater> queryLambdaueryWrapper = this.queryLambda();
//           KtQueryChainWrapper<FileUploadActionWater> queryKtWrapper = this.queryKt();
            if(ObjectTrue.isEmpty(queryEnum)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            if(ObjectTrue.isEmpty(queryObject)){
                AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
            }
            FileDataDetailed  fileDataDetailed = FileDataDetailed.build().clone(queryObject);
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