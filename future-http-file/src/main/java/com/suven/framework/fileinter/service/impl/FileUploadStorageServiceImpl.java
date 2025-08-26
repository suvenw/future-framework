package com.suven.framework.fileinter.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.fileinter.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.fileinter.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.fileinter.entity.FileUploadStorage;
import com.suven.framework.fileinter.repository.FileUploadStorageRepository;
import com.suven.framework.fileinter.service.FileUploadStorageService;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
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
 *  date 创建时间: 2024-04-18 23:55:18
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
public class FileUploadStorageServiceImpl  implements FileUploadStorageService {


    @Autowired
    private FileUploadStorageRepository  fileUploadStorageRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileUploadStorageRequestDto FileUploadStorageResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadStorageResponseDto saveFileUploadStorage(FileUploadStorageRequestDto fileUploadStorageRequestDto){
        if(fileUploadStorageRequestDto== null){
            return null;
        }
        FileUploadStorage fileUploadStorage = FileUploadStorage.build().clone(fileUploadStorageRequestDto);
        boolean result = fileUploadStorageRepository.save(fileUploadStorage);
        if(!result){
            return null;
        }
        FileUploadStorageResponseDto fileUploadStorageResponseDto = FileUploadStorageResponseDto.build().clone(fileUploadStorage);
        return fileUploadStorageResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadStorageRequestDto FileUploadStorageResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadStorageResponseDto saveIdFileUploadStorage(FileUploadStorageRequestDto fileUploadStorageRequestDto){
        if(fileUploadStorageRequestDto== null){
            return null;
        }
        FileUploadStorage fileUploadStorage = FileUploadStorage.build().clone(fileUploadStorageRequestDto);
        fileUploadStorage = fileUploadStorageRepository.saveId(fileUploadStorage);
        if(null == fileUploadStorage){
            return null;
        }
        FileUploadStorageResponseDto fileUploadStorageResponseDto = FileUploadStorageResponseDto.build().clone(fileUploadStorage);
        return fileUploadStorageResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileUploadStorageRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileUploadStorage> list = IterableConvert.convertList(entityList,FileUploadStorage.class);
        boolean result = fileUploadStorageRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileUploadStorageRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadStorage> list = IterableConvert.convertList(entityList,FileUploadStorage.class);
        boolean result = fileUploadStorageRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileUploadStorageResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadStorage> list = IterableConvert.convertList(entityList,FileUploadStorage.class);
        boolean result = fileUploadStorageRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileUploadStorageResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileUploadStorageRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileUploadStorage> list = IterableConvert.convertList(entityList,FileUploadStorage.class);
        boolean result =  fileUploadStorageRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadStorageRequestDto  FileUploadStorageResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileUploadStorage(FileUploadStorageRequestDto fileUploadStorageRequestDto){

          if(null ==  fileUploadStorageRequestDto){
              return false;
          }

        FileUploadStorage fileUploadStorage = FileUploadStorage.build().clone(fileUploadStorageRequestDto);

        return fileUploadStorageRepository.updateById(fileUploadStorage);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileUploadStorageByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileUploadStorageRepository.removeById(idList.get(0));
        }else {
            result =  fileUploadStorageRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadStorageId 对象id
     * @return 查询表对象
     */
    @Override
    public FileUploadStorageResponseDto getFileUploadStorageById(long fileUploadStorageId){
        if(fileUploadStorageId < 0 ){
            return null;
        }
        FileUploadStorage fileUploadStorage =  fileUploadStorageRepository.getById(fileUploadStorageId);
        if(fileUploadStorage == null){
            return null;
        }
        FileUploadStorageResponseDto fileUploadStorageResponseDto = FileUploadStorageResponseDto.build().clone(fileUploadStorage);

        return fileUploadStorageResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileUploadStorageResponseDto getFileUploadStorageByOne( FileUploadStorageQueryEnum queryEnum,FileUploadStorageRequestDto fileUploadStorageRequestDto){
          if(fileUploadStorageRequestDto == null ){
              return null;
          }
           Wrapper<FileUploadStorage> queryWrapper = fileUploadStorageRepository.builderQueryEnum( queryEnum, fileUploadStorageRequestDto);
            //分页对象        PageHelper
           Pager<FileUploadStorage>  pager = Pager.of(0,1);
           pager.setSearchCount(false);
           List<FileUploadStorage>  list = fileUploadStorageRepository.getListByPage(pager,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           FileUploadStorage fileUploadStorage = list.get(0);
           FileUploadStorageResponseDto fileUploadStorageResponseDto = FileUploadStorageResponseDto.build().clone(fileUploadStorage);

            return fileUploadStorageResponseDto ;
       }


     /**
       * 通过条件查询FileUploadStorage信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * date 2024-04-18 23:55:18
       */
      @Override
      public List<FileUploadStorageResponseDto> getFileUploadStorageListByQuery(FileUploadStorageQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileUploadStorage> queryWrapper = fileUploadStorageRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileUploadStorage>  list = fileUploadStorageRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileUploadStorageResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadStorageResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileUploadStorage信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    @Override
    public List<FileUploadStorageResponseDto> getFileUploadStorageListByPage(FileUploadStorageQueryEnum queryEnum,Pager pager){

        Wrapper<FileUploadStorage> queryWrapper =fileUploadStorageRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileUploadStorage>  list = fileUploadStorageRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadStorageResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadStorageResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取FileUploadStorage 信息实现查找缓存和数据库的方法,不查总页数
     * @param pager Pager 分页查询对象
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    @Override
    public PageResult<FileUploadStorageResponseDto> getFileUploadStorageByQueryPage(FileUploadStorageQueryEnum queryEnum, Pager pager){

        Wrapper<FileUploadStorage> queryWrapper = fileUploadStorageRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileUploadStorage>  list = fileUploadStorageRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadStorageResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadStorageResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        PageResult<FileUploadStorageResponseDto> resultList = new PageResult().convertBuild(resDtoList,isNext,pager.getTotal());
        return resultList;
    }

    /**
     * 通过分页获取FileUploadStorage 信息实现查找缓存和数据库的方法,包括查总页数
     *
     * @param queryEnum
     * @param pager     Pager 分页查询对象
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-18 22:49:53 创建时间
     */
    @Override
    public PageResult<FileUploadStorageResponseDto> getFileUploadStorageByNextPage(FileUploadStorageQueryEnum queryEnum, Pager pager ) {
        return getFileUploadStorageByNextPage(queryEnum,pager,false);
    }
    /**
     * 通过分页获取FileUploadStorage信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param pager pager分页查询对象
     * @param searchCount 分页查时,是否查询总条数,false为不查询,true 为查询count 语句查询总条数,有性能不优;
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    @Override
    public PageResult<FileUploadStorageResponseDto> getFileUploadStorageByNextPage(FileUploadStorageQueryEnum queryEnum, Pager pager, boolean searchCount){

        Wrapper<FileUploadStorage> queryWrapper = fileUploadStorageRepository.builderQueryEnum(queryEnum,  pager.getParamObject());;
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        List<FileUploadStorage>  list = fileUploadStorageRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadStorageResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadStorageResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        PageResult<FileUploadStorageResponseDto> resultList = new PageResult().convertBuild(resDtoList,isNext,pager.getTotal());

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
    public List<FileUploadStorageResponseDto> getFileUploadStorageByIdList(Collection<Long> idList){

        Collection<FileUploadStorage> dbList =  this.fileUploadStorageRepository.listByIds(idList);
        List<FileUploadStorageResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileUploadStorageResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileUploadStorage  setFileUploadStorage(){
        FileUploadStorage fileUploadStorage = new FileUploadStorage();
        /**
 			//fileUploadStorage.setIdempotent (long idempotent);
 			//fileUploadStorage.setAppId (String appId);
 			//fileUploadStorage.setClientId (long clientId);
 			//fileUploadStorage.setUseBusinessId (long useBusinessId);
 			//fileUploadStorage.setStorageConfigId (String storageConfigId);
 			//fileUploadStorage.setFileSourceName (String fileSourceName);
 			//fileUploadStorage.setFileMd5 (String fileMd5);
 			//fileUploadStorage.setFileType (String fileType);
 			//fileUploadStorage.setFileSize (long fileSize);
 			//fileUploadStorage.setBucketName (String bucketName);
 			//fileUploadStorage.setOssKey (String ossKey);
 			//fileUploadStorage.setFileOssName (String fileOssName);
 			//fileUploadStorage.setFileOssStorePath (String fileOssStorePath);
 			//fileUploadStorage.setFileDomain (String fileDomain);
 			//fileUploadStorage.setFileAccessUrl (String fileAccessUrl);
 			//fileUploadStorage.setAccessExpirationTime (String accessExpirationTime);
 			//fileUploadStorage.setAccessCount (int accessCount);
 			//fileUploadStorage.setAccessUrlFormat (String accessUrlFormat);
 			//fileUploadStorage.setCallbackValidate (String callbackValidate);
 			//fileUploadStorage.setInterpretData (int interpretData);
 			//fileUploadStorage.setInterpretDataTotal (int interpretDataTotal);
 			//fileUploadStorage.setFileHistory (int fileHistory);
 			//fileUploadStorage.setFileHistoryStorePath (String fileHistoryStorePath);
 			//fileUploadStorage.setRemark (String remark);
 			//fileUploadStorage.setCreateDate (Date createDate);
 			//fileUploadStorage.setModifyDate (Date modifyDate);
 			//fileUploadStorage.setTenantId (long tenantId);
 			//fileUploadStorage.setDeleted (int deleted);
		**/

        return fileUploadStorage;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileUploadStorageRepository, FileUploadStorage.class,0);
    }


}
