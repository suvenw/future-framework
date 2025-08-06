package com.suven.framework.fileinter.service.impl;


import com.suven.framework.core.ObjectTrue;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.entity.FileDataDetailed;
import com.suven.framework.fileinter.repository.FileDataDetailedRepository;
import com.suven.framework.fileinter.service.FileDataDetailedService;
import com.suven.framework.fileinter.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.fileinter.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.fileinter.dto.enums.FileDataDetailedQueryEnum;



import com.suven.framework.core.IterableConvert;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:20:28
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
public class FileDataDetailedServiceImpl  implements FileDataDetailedService {


    @Autowired
    private FileDataDetailedRepository  fileDataDetailedRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileDataDetailedRequestDto FileDataDetailedResponseDto
     * @return 返回表对象
     */
    @Override
    public FileDataDetailedResponseDto saveFileDataDetailed(FileDataDetailedRequestDto fileDataDetailedRequestDto){
        if(fileDataDetailedRequestDto== null){
            return null;
        }
        FileDataDetailed fileDataDetailed = FileDataDetailed.build().clone(fileDataDetailedRequestDto);
        boolean result = fileDataDetailedRepository.save(fileDataDetailed);
        if(!result){
            return null;
        }
        FileDataDetailedResponseDto fileDataDetailedResponseDto = FileDataDetailedResponseDto.build().clone(fileDataDetailed);
        return fileDataDetailedResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileDataDetailedRequestDto FileDataDetailedResponseDto
     * @return 返回表对象
     */
    @Override
    public FileDataDetailedResponseDto saveIdFileDataDetailed(FileDataDetailedRequestDto fileDataDetailedRequestDto){
        if(fileDataDetailedRequestDto== null){
            return null;
        }
        FileDataDetailed fileDataDetailed = FileDataDetailed.build().clone(fileDataDetailedRequestDto);
        fileDataDetailed = fileDataDetailedRepository.saveId(fileDataDetailed);
        if(null == fileDataDetailed){
            return null;
        }
        FileDataDetailedResponseDto fileDataDetailedResponseDto = FileDataDetailedResponseDto.build().clone(fileDataDetailed);
        return fileDataDetailedResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileDataDetailedRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileDataDetailed(Collection<FileDataDetailedRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileDataDetailed> list = IterableConvert.convertList(entityList,FileDataDetailed.class);
        boolean result = fileDataDetailedRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileDataDetailedRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileDataDetailed(Collection<FileDataDetailedRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileDataDetailed> list = IterableConvert.convertList(entityList,FileDataDetailed.class);
        boolean result = fileDataDetailedRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileDataDetailedResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileDataDetailed(Collection<FileDataDetailedRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileDataDetailed> list = IterableConvert.convertList(entityList,FileDataDetailed.class);
        boolean result = fileDataDetailedRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileDataDetailedResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileDataDetailedRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileDataDetailed> list = IterableConvert.convertList(entityList,FileDataDetailed.class);
        boolean result =  fileDataDetailedRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileDataDetailedRequestDto  FileDataDetailedResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileDataDetailed(FileDataDetailedRequestDto fileDataDetailedRequestDto){

          if(null ==  fileDataDetailedRequestDto){
              return false;
          }

        FileDataDetailed fileDataDetailed = FileDataDetailed.build().clone(fileDataDetailedRequestDto);

        return fileDataDetailedRepository.updateById(fileDataDetailed);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileDataDetailedByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileDataDetailedRepository.removeById(idList.get(0));
        }else {
            result =  fileDataDetailedRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileDataDetailedId 对象id
     * @return 查询表对象
     */
    @Override
    public FileDataDetailedResponseDto getFileDataDetailedById(long fileDataDetailedId){
        if(fileDataDetailedId < 0 ){
            return null;
        }
        FileDataDetailed fileDataDetailed =  fileDataDetailedRepository.getById(fileDataDetailedId);
        if(fileDataDetailed == null){
            return null;
        }
        FileDataDetailedResponseDto fileDataDetailedResponseDto = FileDataDetailedResponseDto.build().clone(fileDataDetailed);

        return fileDataDetailedResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileDataDetailedResponseDto getFileDataDetailedByOne( FileDataDetailedQueryEnum queryEnum,FileDataDetailedRequestDto fileDataDetailedRequestDto){
          if(fileDataDetailedRequestDto == null ){
              return null;
          }
           Wrapper<FileDataDetailed> queryWrapper = fileDataDetailedRepository.builderQueryEnum( queryEnum, fileDataDetailedRequestDto);
            //分页对象        PageHelper
           Pager<FileDataDetailed>   pager = Pager.of(0,1);
           pager.setSearchCount(false);
          ResponseResultPageVo<FileDataDetailed>   pageVo = fileDataDetailedRepository.getListByPage(pager,queryWrapper);
           if(null == pageVo || ObjectTrue.isEmpty(pageVo.getList()) ){
                 return null;
           }
           FileDataDetailed fileDataDetailed = pageVo.getList().get(0);
           FileDataDetailedResponseDto fileDataDetailedResponseDto = FileDataDetailedResponseDto.build().clone(fileDataDetailed);

            return fileDataDetailedResponseDto ;
       }


     /**
       * 通过条件查询FileDataDetailed信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * date 2024-04-19 00:20:28
       */
      @Override
      public List<FileDataDetailedResponseDto> getFileDataDetailedListByQuery(FileDataDetailedQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileDataDetailed> queryWrapper = fileDataDetailedRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileDataDetailed>  list = fileDataDetailedRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileDataDetailedResponseDto>  resDtoList =  IterableConvert.convertList(list,FileDataDetailedResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileDataDetailed信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    @Override
    public List<FileDataDetailedResponseDto> getFileDataDetailedListByPage(FileDataDetailedQueryEnum queryEnum,Pager pager){

        Wrapper<FileDataDetailed> queryWrapper =fileDataDetailedRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        ResponseResultPageVo<FileDataDetailed> pageVo = fileDataDetailedRepository.getListByPage(pager,queryWrapper);
        if(null == pageVo || null == pageVo.getList() ){
            return new ArrayList<>();
        }
        List<FileDataDetailedResponseDto>  resDtoList =  IterableConvert.convertList(pageVo.getList(),FileDataDetailedResponseDto.class);
        return resDtoList;

    }

    /**
     * 通过分页获取FileDataDetailed信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    @Override
    public ResponseResultPageVo<FileDataDetailedResponseDto> getFileDataDetailedByNextPage(FileDataDetailedQueryEnum queryEnum, Pager pager){

        ResponseResultPageVo<FileDataDetailedResponseDto> resultPage = getFileDataDetailedByNextPage(queryEnum,pager,false);
        return resultPage;
    }

    /**
     * 通过分页获取FileDataDetailed信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     */
    @Override
    public ResponseResultPageVo<FileDataDetailedResponseDto> getFileDataDetailedByNextPage(FileDataDetailedQueryEnum queryEnum, Pager pager, boolean searchCount){
        Wrapper<FileDataDetailed> queryWrapper = fileDataDetailedRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        ResponseResultPageVo<FileDataDetailed>   pageVo = fileDataDetailedRepository.getListByPage(pager,queryWrapper);
        if(null == pageVo || ObjectTrue.isEmpty(pageVo.getList()) ){
           return new ResponseResultPageVo<>();
        }
        ResponseResultPageVo<FileDataDetailedResponseDto> resultList = pageVo.convertBuild(FileDataDetailedResponseDto.class);

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
    public List<FileDataDetailedResponseDto> getFileDataDetailedByIdList(Collection<Long> idList){

        Collection<FileDataDetailed> dbList =  this.fileDataDetailedRepository.listByIds(idList);
        List<FileDataDetailedResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileDataDetailedResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileDataDetailed  setFileDataDetailed(){
        FileDataDetailed fileDataDetailed = new FileDataDetailed();
        /**
 			//fileDataDetailed.setAppId (String appId);
 			//fileDataDetailed.setAppName (String appName);
 			//fileDataDetailed.setClientId (long clientId);
 			//fileDataDetailed.setCompanyId (String companyId);
 			//fileDataDetailed.setCompanyName (String companyName);
 			//fileDataDetailed.setFileProductName (String fileProductName);
 			//fileDataDetailed.setFileBusinessName (String fileBusinessName);
 			//fileDataDetailed.setUseBusinessId (long useBusinessId);
 			//fileDataDetailed.setStorageConfigId (String storageConfigId);
 			//fileDataDetailed.setFileSourceName (String fileSourceName);
 			//fileDataDetailed.setFileType (String fileType);
 			//fileDataDetailed.setErrorCode (String errorCode);
 			//fileDataDetailed.setErrorMessage (String errorMessage);
 			//fileDataDetailed.setCreateDate (Date createDate);
 			//fileDataDetailed.setModifyDate (Date modifyDate);
 			//fileDataDetailed.setTenantId (long tenantId);
 			//fileDataDetailed.setDeleted (int deleted);
		**/

        return fileDataDetailed;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileDataDetailedRepository, FileDataDetailed.class,0);
    }


}
