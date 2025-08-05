package com.suven.framework.fileinter.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.fileinter.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.fileinter.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.fileinter.entity.FileUploadActionWater;
import com.suven.framework.fileinter.repository.FileUploadActionWaterRepository;
import com.suven.framework.fileinter.service.FileUploadActionWaterService;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;







/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:14:12
 * <pre>
 *
 *  Description:  RPC业务接口逻辑实现类
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

@Service
public class FileUploadActionWaterServiceImpl  implements FileUploadActionWaterService {


    @Autowired
    private FileUploadActionWaterRepository  fileUploadActionWaterRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileUploadActionWaterRequestDto FileUploadActionWaterResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadActionWaterResponseDto saveFileUploadActionWater(FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto){
        if(fileUploadActionWaterRequestDto== null){
            return null;
        }
        FileUploadActionWater fileUploadActionWater = FileUploadActionWater.build().clone(fileUploadActionWaterRequestDto);
        boolean result = fileUploadActionWaterRepository.save(fileUploadActionWater);
        if(!result){
            return null;
        }
        FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = FileUploadActionWaterResponseDto.build().clone(fileUploadActionWater);
        return fileUploadActionWaterResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadActionWaterRequestDto FileUploadActionWaterResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadActionWaterResponseDto saveIdFileUploadActionWater(FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto){
        if(fileUploadActionWaterRequestDto== null){
            return null;
        }
        FileUploadActionWater fileUploadActionWater = FileUploadActionWater.build().clone(fileUploadActionWaterRequestDto);
        fileUploadActionWater = fileUploadActionWaterRepository.saveId(fileUploadActionWater);
        if(null == fileUploadActionWater){
            return null;
        }
        FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = FileUploadActionWaterResponseDto.build().clone(fileUploadActionWater);
        return fileUploadActionWaterResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileUploadActionWaterRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileUploadActionWater> list = IterableConvert.convertList(entityList,FileUploadActionWater.class);
        boolean result = fileUploadActionWaterRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileUploadActionWaterRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadActionWater> list = IterableConvert.convertList(entityList,FileUploadActionWater.class);
        boolean result = fileUploadActionWaterRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileUploadActionWaterResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadActionWater> list = IterableConvert.convertList(entityList,FileUploadActionWater.class);
        boolean result = fileUploadActionWaterRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileUploadActionWaterResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileUploadActionWater> list = IterableConvert.convertList(entityList,FileUploadActionWater.class);
        boolean result =  fileUploadActionWaterRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadActionWaterRequestDto  FileUploadActionWaterResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileUploadActionWater(FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto){

          if(null ==  fileUploadActionWaterRequestDto){
              return false;
          }

        FileUploadActionWater fileUploadActionWater = FileUploadActionWater.build().clone(fileUploadActionWaterRequestDto);

        return fileUploadActionWaterRepository.updateById(fileUploadActionWater);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileUploadActionWaterByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileUploadActionWaterRepository.removeById(idList.get(0));
        }else {
            result =  fileUploadActionWaterRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadActionWaterId 对象id
     * @return 查询表对象
     */
    @Override
    public FileUploadActionWaterResponseDto getFileUploadActionWaterById(long fileUploadActionWaterId){
        if(fileUploadActionWaterId < 0 ){
            return null;
        }
        FileUploadActionWater fileUploadActionWater =  fileUploadActionWaterRepository.getById(fileUploadActionWaterId);
        if(fileUploadActionWater == null){
            return null;
        }
        FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = FileUploadActionWaterResponseDto.build().clone(fileUploadActionWater);

        return fileUploadActionWaterResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileUploadActionWaterResponseDto getFileUploadActionWaterByOne( FileUploadActionWaterQueryEnum queryEnum,FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto){
          if(fileUploadActionWaterRequestDto == null ){
              return null;
          }
           Wrapper<FileUploadActionWater> queryWrapper = fileUploadActionWaterRepository.builderQueryEnum( queryEnum, fileUploadActionWaterRequestDto);
            //分页对象        PageHelper
           Pager<FileUploadActionWater>  pager = Pager.build(0,1);
           pager.setSearchCount(false);
           List<FileUploadActionWater>  list = fileUploadActionWaterRepository.getListByPage(pager,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           FileUploadActionWater fileUploadActionWater = list.get(0);
           FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = FileUploadActionWaterResponseDto.build().clone(fileUploadActionWater);

            return fileUploadActionWaterResponseDto ;
       }


     /**
       * 通过条件查询FileUploadActionWater信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * @date 2024-04-19 00:14:12
       */
      @Override
      public List<FileUploadActionWaterResponseDto> getFileUploadActionWaterListByQuery(FileUploadActionWaterQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileUploadActionWater> queryWrapper = fileUploadActionWaterRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileUploadActionWater>  list = fileUploadActionWaterRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileUploadActionWaterResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadActionWaterResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileUploadActionWater信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    @Override
    public List<FileUploadActionWaterResponseDto> getFileUploadActionWaterListByPage(FileUploadActionWaterQueryEnum queryEnum,Pager pager){

        Wrapper<FileUploadActionWater> queryWrapper =fileUploadActionWaterRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileUploadActionWater>  list = fileUploadActionWaterRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadActionWaterResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadActionWaterResponseDto.class);
        return resDtoList;

    }

    /**
     * 通过分页获取FileUploadActionWater信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    @Override
    public ResultPageVo<FileUploadActionWaterResponseDto> getFileUploadActionWaterByNextPage(FileUploadActionWaterQueryEnum queryEnum,Pager pager){

        ResultPageVo<FileUploadActionWaterResponseDto> resultPage = getFileUploadActionWaterByNextPage(queryEnum,pager,false);
        return resultPage;
    }

    /**
     * 通过分页获取FileUploadActionWater信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    @Override
    public ResultPageVo<FileUploadActionWaterResponseDto> getFileUploadActionWaterByNextPage(FileUploadActionWaterQueryEnum queryEnum,Pager pager,boolean searchCount){
        ResultPageVo<FileUploadActionWaterResponseDto> resultPage = ResultPageVo.build();
        Wrapper<FileUploadActionWater> queryWrapper = fileUploadActionWaterRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        List<FileUploadActionWater>  list = fileUploadActionWaterRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadActionWaterResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadActionWaterResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        ResultPageVo<FileUploadActionWaterResponseDto> resultList = ResultPageVo.build().convertBuild(resDtoList,isNext,pager.getTotal());

        return resultList;

    }

     /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     */
    @Override
    public List<FileUploadActionWaterResponseDto> getFileUploadActionWaterByIdList(Collection<Long> idList){

        Collection<FileUploadActionWater> dbList =  this.fileUploadActionWaterRepository.listByIds(idList);
        List<FileUploadActionWaterResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileUploadActionWaterResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileUploadActionWater  setFileUploadActionWater(){
        FileUploadActionWater fileUploadActionWater = new FileUploadActionWater();
        /**
 			//fileUploadActionWater.setCompanyId (String companyId);
 			//fileUploadActionWater.setCompanyName (String companyName);
 			//fileUploadActionWater.setDeptId (long deptId);
 			//fileUploadActionWater.setDeptName (String deptName);
 			//fileUploadActionWater.setUploadUserId (long uploadUserId);
 			//fileUploadActionWater.setUploadUserName (String uploadUserName);
 			//fileUploadActionWater.setFileProductName (String fileProductName);
 			//fileUploadActionWater.setFileBusinessName (String fileBusinessName);
 			//fileUploadActionWater.setAppId (String appId);
 			//fileUploadActionWater.setClientId (long clientId);
 			//fileUploadActionWater.setUseBusinessId (long useBusinessId);
 			//fileUploadActionWater.setStorageConfigId (String storageConfigId);
 			//fileUploadActionWater.setFileUploadStorageId (long fileUploadStorageId);
 			//fileUploadActionWater.setFileSourceName (String fileSourceName);
 			//fileUploadActionWater.setFileOssName (String fileOssName);
 			//fileUploadActionWater.setCreateDate (Date createDate);
 			//fileUploadActionWater.setModifyDate (Date modifyDate);
 			//fileUploadActionWater.setTenantId (long tenantId);
 			//fileUploadActionWater.setDeleted (int deleted);
		**/

        return fileUploadActionWater;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileUploadActionWaterRepository, FileUploadActionWater.class,0);
    }


}
