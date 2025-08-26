package com.suven.framework.fileinter.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.entity.FileUploadApp;
import com.suven.framework.fileinter.repository.FileUploadAppRepository;
import com.suven.framework.fileinter.service.FileUploadAppService;
import com.suven.framework.fileinter.dto.request.FileUploadAppRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadAppResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadAppQueryEnum;



import com.suven.framework.core.IterableConvert;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:49
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
public class FileUploadAppServiceImpl  implements FileUploadAppService {


    @Autowired
    private FileUploadAppRepository  fileUploadAppRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileUploadAppRequestDto FileUploadAppResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadAppResponseDto saveFileUploadApp(FileUploadAppRequestDto fileUploadAppRequestDto){
        if(fileUploadAppRequestDto== null){
            return null;
        }
        FileUploadApp fileUploadApp = FileUploadApp.build().clone(fileUploadAppRequestDto);
        boolean result = fileUploadAppRepository.save(fileUploadApp);
        if(!result){
            return null;
        }
        FileUploadAppResponseDto fileUploadAppResponseDto = FileUploadAppResponseDto.build().clone(fileUploadApp);
        return fileUploadAppResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadAppRequestDto FileUploadAppResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadAppResponseDto saveIdFileUploadApp(FileUploadAppRequestDto fileUploadAppRequestDto){
        if(fileUploadAppRequestDto== null){
            return null;
        }
        FileUploadApp fileUploadApp = FileUploadApp.build().clone(fileUploadAppRequestDto);
        fileUploadApp = fileUploadAppRepository.saveId(fileUploadApp);
        if(null == fileUploadApp){
            return null;
        }
        FileUploadAppResponseDto fileUploadAppResponseDto = FileUploadAppResponseDto.build().clone(fileUploadApp);
        return fileUploadAppResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileUploadAppRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileUploadApp(Collection<FileUploadAppRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileUploadApp> list = IterableConvert.convertList(entityList,FileUploadApp.class);
        boolean result = fileUploadAppRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileUploadAppRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileUploadApp(Collection<FileUploadAppRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadApp> list = IterableConvert.convertList(entityList,FileUploadApp.class);
        boolean result = fileUploadAppRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileUploadAppResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileUploadApp(Collection<FileUploadAppRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadApp> list = IterableConvert.convertList(entityList,FileUploadApp.class);
        boolean result = fileUploadAppRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileUploadAppResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileUploadAppRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileUploadApp> list = IterableConvert.convertList(entityList,FileUploadApp.class);
        boolean result =  fileUploadAppRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadAppRequestDto  FileUploadAppResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileUploadApp(FileUploadAppRequestDto fileUploadAppRequestDto){

          if(null ==  fileUploadAppRequestDto){
              return false;
          }

        FileUploadApp fileUploadApp = FileUploadApp.build().clone(fileUploadAppRequestDto);

        return fileUploadAppRepository.updateById(fileUploadApp);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileUploadAppByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileUploadAppRepository.removeById(idList.get(0));
        }else {
            result =  fileUploadAppRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadAppId 对象id
     * @return 查询表对象
     */
    @Override
    public FileUploadAppResponseDto getFileUploadAppById(long fileUploadAppId){
        if(fileUploadAppId < 0 ){
            return null;
        }
        FileUploadApp fileUploadApp =  fileUploadAppRepository.getById(fileUploadAppId);
        if(fileUploadApp == null){
            return null;
        }
        FileUploadAppResponseDto fileUploadAppResponseDto = FileUploadAppResponseDto.build().clone(fileUploadApp);

        return fileUploadAppResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileUploadAppResponseDto getFileUploadAppByOne( FileUploadAppQueryEnum queryEnum,FileUploadAppRequestDto fileUploadAppRequestDto){
          if(fileUploadAppRequestDto == null ){
              return null;
          }
           Wrapper<FileUploadApp> queryWrapper = fileUploadAppRepository.builderQueryEnum( queryEnum, fileUploadAppRequestDto);
            //分页对象        PageHelper
           Pager<FileUploadApp>   pager = Pager.of(0,1);
           pager.setSearchCount(false);
           List<FileUploadApp>  list = fileUploadAppRepository.getListByPage(pager,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           FileUploadApp fileUploadApp = list.get(0);
           FileUploadAppResponseDto fileUploadAppResponseDto = FileUploadAppResponseDto.build().clone(fileUploadApp);

            return fileUploadAppResponseDto ;
       }


     /**
       * 通过条件查询FileUploadApp信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * date 2024-04-19 00:21:49
       */
      @Override
      public List<FileUploadAppResponseDto> getFileUploadAppListByQuery(FileUploadAppQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileUploadApp> queryWrapper = fileUploadAppRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileUploadApp>  list = fileUploadAppRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileUploadAppResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadAppResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileUploadApp信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     */
    @Override
    public List<FileUploadAppResponseDto> getFileUploadAppListByPage(FileUploadAppQueryEnum queryEnum,Pager pager){

        Wrapper<FileUploadApp> queryWrapper =fileUploadAppRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileUploadApp>  list = fileUploadAppRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadAppResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadAppResponseDto.class);
        return resDtoList;

    }

    /**
     * 通过分页获取FileUploadApp信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     */
    @Override
    public PageResult<FileUploadAppResponseDto> getFileUploadAppByNextPage(FileUploadAppQueryEnum queryEnum, Pager pager){

        PageResult<FileUploadAppResponseDto> resultPage = getFileUploadAppByNextPage(queryEnum,pager,false);
        return resultPage;
    }

    /**
     * 通过分页获取FileUploadApp信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     */
    @Override
    public PageResult<FileUploadAppResponseDto> getFileUploadAppByNextPage(FileUploadAppQueryEnum queryEnum, Pager pager, boolean searchCount){
        PageResult<FileUploadAppResponseDto> resultPage = new PageResult<>();
        Wrapper<FileUploadApp> queryWrapper = fileUploadAppRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        List<FileUploadApp>  list = fileUploadAppRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadAppResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadAppResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        PageResult<FileUploadAppResponseDto> resultList = new PageResult().convertBuild(resDtoList,isNext,pager.getTotal());

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
    public List<FileUploadAppResponseDto> getFileUploadAppByIdList(Collection<Long> idList){

        Collection<FileUploadApp> dbList =  this.fileUploadAppRepository.listByIds(idList);
        List<FileUploadAppResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileUploadAppResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileUploadApp  setFileUploadApp(){
        FileUploadApp fileUploadApp = new FileUploadApp();
        /**
 			//fileUploadApp.setAppId (String appId);
 			//fileUploadApp.setAppName (String appName);
 			//fileUploadApp.setClientId (String clientId);
 			//fileUploadApp.setClientSecret (String clientSecret);
 			//fileUploadApp.setClientSalt (String clientSalt);
 			//fileUploadApp.setPathName (String pathName);
 			//fileUploadApp.setClientUrl (String clientUrl);
 			//fileUploadApp.setAccessExpirationTime (String accessExpirationTime);
 			//fileUploadApp.setAccessCount (int accessCount);
 			//fileUploadApp.setAccessUrlFormat (String accessUrlFormat);
 			//fileUploadApp.setAccessUrl (String accessUrl);
 			//fileUploadApp.setFileAppStorageConfigId (long fileAppStorageConfigId);
 			//fileUploadApp.setEndpoint (String endpoint);
 			//fileUploadApp.setBucket (String bucket);
 			//fileUploadApp.setAccessKey (String accessKey);
 			//fileUploadApp.setAccessSecret (String accessSecret);
 			//fileUploadApp.setCreateDate (Date createDate);
 			//fileUploadApp.setModifyDate (Date modifyDate);
 			//fileUploadApp.setTenantId (long tenantId);
 			//fileUploadApp.setDeleted (int deleted);
		**/

        return fileUploadApp;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileUploadAppRepository, FileUploadApp.class,0);
    }


}
