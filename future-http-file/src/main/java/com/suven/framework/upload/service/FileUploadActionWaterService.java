package com.suven.framework.upload.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.upload.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.upload.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.upload.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




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


public interface FileUploadActionWaterService {



    /**
     * 保存更新数据库和缓存的实现方法
     * @param fileUploadActionWaterRequestDto  FileUploadActionWaterRequestDto
     * @return 返回表对象
     */
    FileUploadActionWaterResponseDto saveFileUploadActionWater(FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadActionWaterRequestDto  FileUploadActionWaterRequestDto
     * @return 返回表对象
     */
    FileUploadActionWaterResponseDto saveIdFileUploadActionWater(FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return true/false
     */
    boolean saveBatchIdFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveBatchFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveOrUpdateBatchFileUploadActionWater(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadActionWaterRequestDto  FileUploadActionWaterRequestDto
     * @return true/false
     */
    boolean updateFileUploadActionWater (FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean updateBatchById(Collection<FileUploadActionWaterRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  fileUploadActionWaterIds 对象id列表
     * @return 0/1
     */
    int delFileUploadActionWaterByIds(List<Long>  fileUploadActionWaterIds);


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadActionWaterId
     * @return 返回列表对象
     */
        FileUploadActionWaterResponseDto getFileUploadActionWaterById(long fileUploadActionWaterId);

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 枚举查询条件
     * @return 返回列表对象
     */
    FileUploadActionWaterResponseDto getFileUploadActionWaterByOne( FileUploadActionWaterQueryEnum queryEnum,FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto);


    /**
    * 通过分页和枚举条件获取FileUploadActionWater信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return 返回列表对象列表
    * @author suven
    * date 2024-04-19 00:14:12
    */
    List<FileUploadActionWaterResponseDto> getFileUploadActionWaterListByQuery(FileUploadActionWaterQueryEnum queryEnum,Object  paramObject);


    /**
     * 通过分页获取FileUploadActionWater信息实现查找缓存和数据库的方法
     * @param pager Pager 分页查询对象
     * @return 返回列表对象 列表
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    List<FileUploadActionWaterResponseDto> getFileUploadActionWaterListByPage(FileUploadActionWaterQueryEnum queryEnum,Pager pager);



    /**
     * 通过分页获取FileUploadActionWater 信息实现查找缓存和数据库的方法,包括查总页数
     * @param queryEnum Pager 分页枚举对象
     * @param pager Pager 分页查询对象
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    PageResult<FileUploadActionWaterResponseDto> getFileUploadActionWaterByNextPage(FileUploadActionWaterQueryEnum queryEnum, Pager pager);

    /**
     * 通过分页获取FileUploadActionWater 信息实现查找缓存和数据库的方法,不查总页数
     * @param queryEnum Pager 分页枚举对象
     * @param pager Pager 分页查询对象
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */
    PageResult<FileUploadActionWaterResponseDto> getFileUploadActionWaterByNextPage(FileUploadActionWaterQueryEnum queryEnum, Pager pager, boolean searchCount);



    /**
     * 通过idList聚合 查找信息列表的方法
     * @param idList Collection<Long> 表对象id列表
     * @return 返回对象列表
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     */

    List<FileUploadActionWaterResponseDto> getFileUploadActionWaterByIdList(Collection<Long> idList);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream 上传流
    * @return true/false
    */
    public boolean saveData(InputStream initialStream);

}