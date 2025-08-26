package com.suven.framework.fileinter.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.entity.FileAppStorageConfig;
import com.suven.framework.fileinter.repository.FileAppStorageConfigRepository;
import com.suven.framework.fileinter.service.FileAppStorageConfigService;
import com.suven.framework.fileinter.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.fileinter.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.fileinter.dto.enums.FileAppStorageConfigQueryEnum;



import com.suven.framework.core.IterableConvert;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
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
public class FileAppStorageConfigServiceImpl  implements FileAppStorageConfigService {


    @Autowired
    private FileAppStorageConfigRepository  fileAppStorageConfigRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileAppStorageConfigRequestDto FileAppStorageConfigResponseDto
     * @return 返回表对象
     */
    @Override
    public FileAppStorageConfigResponseDto saveFileAppStorageConfig(FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto){
        if(fileAppStorageConfigRequestDto== null){
            return null;
        }
        FileAppStorageConfig fileAppStorageConfig = FileAppStorageConfig.build().clone(fileAppStorageConfigRequestDto);
        boolean result = fileAppStorageConfigRepository.save(fileAppStorageConfig);
        if(!result){
            return null;
        }
        FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = FileAppStorageConfigResponseDto.build().clone(fileAppStorageConfig);
        return fileAppStorageConfigResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileAppStorageConfigRequestDto FileAppStorageConfigResponseDto
     * @return 返回表对象
     */
    @Override
    public FileAppStorageConfigResponseDto saveIdFileAppStorageConfig(FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto){
        if(fileAppStorageConfigRequestDto== null){
            return null;
        }
        FileAppStorageConfig fileAppStorageConfig = FileAppStorageConfig.build().clone(fileAppStorageConfigRequestDto);
        fileAppStorageConfig = fileAppStorageConfigRepository.saveId(fileAppStorageConfig);
        if(null == fileAppStorageConfig){
            return null;
        }
        FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = FileAppStorageConfigResponseDto.build().clone(fileAppStorageConfig);
        return fileAppStorageConfigResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileAppStorageConfigRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileAppStorageConfig(Collection<FileAppStorageConfigRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileAppStorageConfig> list = IterableConvert.convertList(entityList,FileAppStorageConfig.class);
        boolean result = fileAppStorageConfigRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileAppStorageConfigRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileAppStorageConfig(Collection<FileAppStorageConfigRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileAppStorageConfig> list = IterableConvert.convertList(entityList,FileAppStorageConfig.class);
        boolean result = fileAppStorageConfigRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileAppStorageConfigResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileAppStorageConfig(Collection<FileAppStorageConfigRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileAppStorageConfig> list = IterableConvert.convertList(entityList,FileAppStorageConfig.class);
        boolean result = fileAppStorageConfigRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileAppStorageConfigResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileAppStorageConfigRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileAppStorageConfig> list = IterableConvert.convertList(entityList,FileAppStorageConfig.class);
        boolean result =  fileAppStorageConfigRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileAppStorageConfigRequestDto  FileAppStorageConfigResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileAppStorageConfig(FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto){

          if(null ==  fileAppStorageConfigRequestDto){
              return false;
          }

        FileAppStorageConfig fileAppStorageConfig = FileAppStorageConfig.build().clone(fileAppStorageConfigRequestDto);

        return fileAppStorageConfigRepository.updateById(fileAppStorageConfig);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileAppStorageConfigByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileAppStorageConfigRepository.removeById(idList.get(0));
        }else {
            result =  fileAppStorageConfigRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileAppStorageConfigId 对象id
     * @return 查询表对象
     */
    @Override
    public FileAppStorageConfigResponseDto getFileAppStorageConfigById(long fileAppStorageConfigId){
        if(fileAppStorageConfigId < 0 ){
            return null;
        }
        FileAppStorageConfig fileAppStorageConfig =  fileAppStorageConfigRepository.getById(fileAppStorageConfigId);
        if(fileAppStorageConfig == null){
            return null;
        }
        FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = FileAppStorageConfigResponseDto.build().clone(fileAppStorageConfig);

        return fileAppStorageConfigResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileAppStorageConfigResponseDto getFileAppStorageConfigByOne( FileAppStorageConfigQueryEnum queryEnum,FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto){
          if(fileAppStorageConfigRequestDto == null ){
              return null;
          }
           Wrapper<FileAppStorageConfig> queryWrapper = fileAppStorageConfigRepository.builderQueryEnum( queryEnum, fileAppStorageConfigRequestDto);
            //分页对象        PageHelper
           Pager<FileAppStorageConfig>   pager = Pager.of(0,1);
           pager.setSearchCount(false);
           List<FileAppStorageConfig>  list = fileAppStorageConfigRepository.getListByPage(pager,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           FileAppStorageConfig fileAppStorageConfig = list.get(0);
           FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = FileAppStorageConfigResponseDto.build().clone(fileAppStorageConfig);

            return fileAppStorageConfigResponseDto ;
       }


     /**
       * 通过条件查询FileAppStorageConfig信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * date 2024-04-19 00:21:54
       */
      @Override
      public List<FileAppStorageConfigResponseDto> getFileAppStorageConfigListByQuery(FileAppStorageConfigQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileAppStorageConfig> queryWrapper = fileAppStorageConfigRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileAppStorageConfig>  list = fileAppStorageConfigRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileAppStorageConfigResponseDto>  resDtoList =  IterableConvert.convertList(list,FileAppStorageConfigResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileAppStorageConfig信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    @Override
    public List<FileAppStorageConfigResponseDto> getFileAppStorageConfigListByPage(FileAppStorageConfigQueryEnum queryEnum,Pager pager){

        Wrapper<FileAppStorageConfig> queryWrapper =fileAppStorageConfigRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileAppStorageConfig>  list = fileAppStorageConfigRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileAppStorageConfigResponseDto>  resDtoList =  IterableConvert.convertList(list,FileAppStorageConfigResponseDto.class);
        return resDtoList;

    }

    /**
     * 通过分页获取FileAppStorageConfig信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    @Override
    public PageResult<FileAppStorageConfigResponseDto> getFileAppStorageConfigByNextPage(FileAppStorageConfigQueryEnum queryEnum, Pager pager){

        PageResult<FileAppStorageConfigResponseDto> resultPage = getFileAppStorageConfigByNextPage(queryEnum,pager,false);
        return resultPage;
    }

    /**
     * 通过分页获取FileAppStorageConfig信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     */
    @Override
    public PageResult<FileAppStorageConfigResponseDto> getFileAppStorageConfigByNextPage(FileAppStorageConfigQueryEnum queryEnum, Pager pager, boolean searchCount){
        PageResult<FileAppStorageConfigResponseDto> resultPage = new PageResult<>();
        Wrapper<FileAppStorageConfig> queryWrapper = fileAppStorageConfigRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        List<FileAppStorageConfig>  list = fileAppStorageConfigRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileAppStorageConfigResponseDto>  resDtoList =  IterableConvert.convertList(list,FileAppStorageConfigResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        PageResult<FileAppStorageConfigResponseDto> resultList = new PageResult().convertBuild(resDtoList,isNext,pager.getTotal());

        return resultList;

    }

     /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    @Override
    public List<FileAppStorageConfigResponseDto> getFileAppStorageConfigByIdList(Collection<Long> idList){

        Collection<FileAppStorageConfig> dbList =  this.fileAppStorageConfigRepository.listByIds(idList);
        List<FileAppStorageConfigResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileAppStorageConfigResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileAppStorageConfig  setFileAppStorageConfig(){
        FileAppStorageConfig fileAppStorageConfig = new FileAppStorageConfig();
        /**
 			//fileAppStorageConfig.setAppId (String appId);
 			//fileAppStorageConfig.setAppName (String appName);
 			//fileAppStorageConfig.setClientId (String clientId);
 			//fileAppStorageConfig.setStorageConfigId (String storageConfigId);
 			//fileAppStorageConfig.setConfigFiledName (String configFiledName);
 			//fileAppStorageConfig.setConfigFiledValue (String configFiledValue);
 			//fileAppStorageConfig.setCreateDate (Date createDate);
 			//fileAppStorageConfig.setModifyDate (Date modifyDate);
 			//fileAppStorageConfig.setTenantId (long tenantId);
 			//fileAppStorageConfig.setDeleted (int deleted);
		**/

        return fileAppStorageConfig;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileAppStorageConfigRepository, FileAppStorageConfig.class,0);
    }


}
