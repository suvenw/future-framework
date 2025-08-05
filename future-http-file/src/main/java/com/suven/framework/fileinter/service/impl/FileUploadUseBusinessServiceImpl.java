package com.suven.framework.fileinter.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.entity.FileUploadUseBusiness;
import com.suven.framework.fileinter.repository.FileUploadUseBusinessRepository;
import com.suven.framework.fileinter.service.FileUploadUseBusinessService;
import com.suven.framework.fileinter.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadUseBusinessQueryEnum;



import com.suven.framework.core.IterableConvert;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:42
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
public class FileUploadUseBusinessServiceImpl  implements FileUploadUseBusinessService {


    @Autowired
    private FileUploadUseBusinessRepository  fileUploadUseBusinessRepository;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param fileUploadUseBusinessRequestDto FileUploadUseBusinessResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadUseBusinessResponseDto saveFileUploadUseBusiness(FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto){
        if(fileUploadUseBusinessRequestDto== null){
            return null;
        }
        FileUploadUseBusiness fileUploadUseBusiness = FileUploadUseBusiness.build().clone(fileUploadUseBusinessRequestDto);
        boolean result = fileUploadUseBusinessRepository.save(fileUploadUseBusiness);
        if(!result){
            return null;
        }
        FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = FileUploadUseBusinessResponseDto.build().clone(fileUploadUseBusiness);
        return fileUploadUseBusinessResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadUseBusinessRequestDto FileUploadUseBusinessResponseDto
     * @return 返回表对象
     */
    @Override
    public FileUploadUseBusinessResponseDto saveIdFileUploadUseBusiness(FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto){
        if(fileUploadUseBusinessRequestDto== null){
            return null;
        }
        FileUploadUseBusiness fileUploadUseBusiness = FileUploadUseBusiness.build().clone(fileUploadUseBusinessRequestDto);
        fileUploadUseBusiness = fileUploadUseBusinessRepository.saveId(fileUploadUseBusiness);
        if(null == fileUploadUseBusiness){
            return null;
        }
        FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = FileUploadUseBusinessResponseDto.build().clone(fileUploadUseBusiness);
        return fileUploadUseBusinessResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList FileUploadUseBusinessRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchIdFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<FileUploadUseBusiness> list = IterableConvert.convertList(entityList,FileUploadUseBusiness.class);
        boolean result = fileUploadUseBusinessRepository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList FileUploadUseBusinessRequestDto集合
     * @return  true/ false
     */
    @Override
    public boolean saveBatchFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadUseBusiness> list = IterableConvert.convertList(entityList,FileUploadUseBusiness.class);
        boolean result = fileUploadUseBusinessRepository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 批量保存或更新同时更新数据库和缓存的实现方法
     * @param entityList  FileUploadUseBusinessResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean saveOrUpdateBatchFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0){
            return false;
        }
        List<FileUploadUseBusiness> list = IterableConvert.convertList(entityList,FileUploadUseBusiness.class);
        boolean result = fileUploadUseBusinessRepository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 批量更新同时更新数据库和缓存的实现方法
     * @param  entityList  FileUploadUseBusinessResponseDto 对象id列表
     * @return true/ false
     */
    @Override
    public boolean updateBatchById(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize) {
        if(null == entityList || batchSize <= 0 ){
            return false;
        }

        List<FileUploadUseBusiness> list = IterableConvert.convertList(entityList,FileUploadUseBusiness.class);
        boolean result =  fileUploadUseBusinessRepository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadUseBusinessRequestDto  FileUploadUseBusinessResponseDto 对象
     * @return true/ false
     */
    @Override
    public boolean updateFileUploadUseBusiness(FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto){

          if(null ==  fileUploadUseBusinessRequestDto){
              return false;
          }

        FileUploadUseBusiness fileUploadUseBusiness = FileUploadUseBusiness.build().clone(fileUploadUseBusinessRequestDto);

        return fileUploadUseBusinessRepository.updateById(fileUploadUseBusiness);
    }



    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 表对象id列表
     * @return 0/1
     */
    @Override
    public int delFileUploadUseBusinessByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = fileUploadUseBusinessRepository.removeById(idList.get(0));
        }else {
            result =  fileUploadUseBusinessRepository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadUseBusinessId 对象id
     * @return 查询表对象
     */
    @Override
    public FileUploadUseBusinessResponseDto getFileUploadUseBusinessById(long fileUploadUseBusinessId){
        if(fileUploadUseBusinessId < 0 ){
            return null;
        }
        FileUploadUseBusiness fileUploadUseBusiness =  fileUploadUseBusinessRepository.getById(fileUploadUseBusinessId);
        if(fileUploadUseBusiness == null){
            return null;
        }
        FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = FileUploadUseBusinessResponseDto.build().clone(fileUploadUseBusiness);

        return fileUploadUseBusinessResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 查询条件枚举
     * @return 查询表对象
     */
     @Override
     public   FileUploadUseBusinessResponseDto getFileUploadUseBusinessByOne( FileUploadUseBusinessQueryEnum queryEnum,FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto){
          if(fileUploadUseBusinessRequestDto == null ){
              return null;
          }
           Wrapper<FileUploadUseBusiness> queryWrapper = fileUploadUseBusinessRepository.builderQueryEnum( queryEnum, fileUploadUseBusinessRequestDto);
            //分页对象        PageHelper
           Pager<FileUploadUseBusiness>   pager = Pager.build(0,1);
           pager.setSearchCount(false);
           List<FileUploadUseBusiness>  list = fileUploadUseBusinessRepository.getListByPage(pager,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           FileUploadUseBusiness fileUploadUseBusiness = list.get(0);
           FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = FileUploadUseBusinessResponseDto.build().clone(fileUploadUseBusiness);

            return fileUploadUseBusinessResponseDto ;
       }


     /**
       * 通过条件查询FileUploadUseBusiness信息列表,实现查找缓存和数据库的方法,但不统计总页数
       * @param paramObject Object 参数对像
       * @return 查询表对象列表
       * @author suven
       * @date 2024-04-19 00:21:42
       */
      @Override
      public List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessListByQuery(FileUploadUseBusinessQueryEnum queryEnum, Object  paramObject ){

          Wrapper<FileUploadUseBusiness> queryWrapper = fileUploadUseBusinessRepository.builderQueryEnum( queryEnum, paramObject);

          List<FileUploadUseBusiness>  list = fileUploadUseBusinessRepository.getListByQuery(queryWrapper);
          if(null == list ){
              list = new ArrayList<>();
          }
          List<FileUploadUseBusinessResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadUseBusinessResponseDto.class);
          return resDtoList;

      }


    /**
     * 通过分页获取FileUploadUseBusiness信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager Pager 分页查询对象
     * @return 查询表对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    @Override
    public List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessListByPage(FileUploadUseBusinessQueryEnum queryEnum,Pager pager){

        Wrapper<FileUploadUseBusiness> queryWrapper =fileUploadUseBusinessRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        List<FileUploadUseBusiness>  list = fileUploadUseBusinessRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadUseBusinessResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadUseBusinessResponseDto.class);
        return resDtoList;

    }

    /**
     * 通过分页获取FileUploadUseBusiness信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    @Override
    public ResponseResultPageVo<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByNextPage(FileUploadUseBusinessQueryEnum queryEnum, Pager pager){

        ResponseResultPageVo<FileUploadUseBusinessResponseDto> resultPage = getFileUploadUseBusinessByNextPage(queryEnum,pager,false);
        return resultPage;
    }

    /**
     * 通过分页获取FileUploadUseBusiness信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param  queryEnum 查询枚举对象
     * @param pager Pager 分页查询条件
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页结果对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    @Override
    public ResponseResultPageVo<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByNextPage(FileUploadUseBusinessQueryEnum queryEnum, Pager pager, boolean searchCount){
        ResponseResultPageVo<FileUploadUseBusinessResponseDto> resultPage = new ResponseResultPageVo();
        Wrapper<FileUploadUseBusiness> queryWrapper = fileUploadUseBusinessRepository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        pager.setSearchCount(searchCount);
        List<FileUploadUseBusiness>  list = fileUploadUseBusinessRepository.getListByPage(pager,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<FileUploadUseBusinessResponseDto>  resDtoList =  IterableConvert.convertList(list,FileUploadUseBusinessResponseDto.class);
        boolean isNext =  pager.isNextPage(resDtoList);
        ResponseResultPageVo<FileUploadUseBusinessResponseDto> resultList = new ResponseResultPageVo().convertBuild(resDtoList,isNext,pager.getTotal());

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
    public List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByIdList(Collection<Long> idList){

        Collection<FileUploadUseBusiness> dbList =  this.fileUploadUseBusinessRepository.listByIds(idList);
        List<FileUploadUseBusinessResponseDto>  responseDtoList = IterableConvert.convertList(dbList,FileUploadUseBusinessResponseDto.class);
        return responseDtoList;
    }





    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return 返回表对象
     */
    public FileUploadUseBusiness  setFileUploadUseBusiness(){
        FileUploadUseBusiness fileUploadUseBusiness = new FileUploadUseBusiness();
        /**
 			//fileUploadUseBusiness.setAppId (String appId);
 			//fileUploadUseBusiness.setClientId (String clientId);
 			//fileUploadUseBusiness.setCompanyId (String companyId);
 			//fileUploadUseBusiness.setCompanyName (String companyName);
 			//fileUploadUseBusiness.setFileProductName (String fileProductName);
 			//fileUploadUseBusiness.setFileBusinessName (String fileBusinessName);
 			//fileUploadUseBusiness.setCallbackService (String callbackService);
 			//fileUploadUseBusiness.setCallbackAsyncService (String callbackAsyncService);
 			//fileUploadUseBusiness.setDataForm (String dataForm);
 			//fileUploadUseBusiness.setFormType (String formType);
 			//fileUploadUseBusiness.setInterpretData (int interpretData);
 			//fileUploadUseBusiness.setCheckData (int checkData);
 			//fileUploadUseBusiness.setAccessExpirationTime (String accessExpirationTime);
 			//fileUploadUseBusiness.setAccessCount (int accessCount);
 			//fileUploadUseBusiness.setAccessUrlFormat (String accessUrlFormat);
 			//fileUploadUseBusiness.setCreateDate (Date createDate);
 			//fileUploadUseBusiness.setModifyDate (Date modifyDate);
 			//fileUploadUseBusiness.setTenantId (long tenantId);
 			//fileUploadUseBusiness.setDeleted (int deleted);
		**/

        return fileUploadUseBusiness;
    }

    /**
     * 上传文件流
     * @return true/false
     */
    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,fileUploadUseBusinessRepository, FileUploadUseBusiness.class,0);
    }


}
