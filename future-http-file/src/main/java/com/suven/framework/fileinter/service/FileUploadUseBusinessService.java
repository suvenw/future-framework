package com.suven.framework.fileinter.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadUseBusinessQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




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


public interface FileUploadUseBusinessService {



    /**
     * 保存更新数据库和缓存的实现方法
     * @param fileUploadUseBusinessRequestDto  FileUploadUseBusinessRequestDto
     * @return 返回表对象
     */
    FileUploadUseBusinessResponseDto saveFileUploadUseBusiness(FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadUseBusinessRequestDto  FileUploadUseBusinessRequestDto
     * @return 返回表对象
     */
    FileUploadUseBusinessResponseDto saveIdFileUploadUseBusiness(FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return true/false
     */
    boolean saveBatchIdFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveBatchFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveOrUpdateBatchFileUploadUseBusiness(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadUseBusinessRequestDto  FileUploadUseBusinessRequestDto
     * @return true/false
     */
    boolean updateFileUploadUseBusiness (FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean updateBatchById(Collection<FileUploadUseBusinessRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  fileUploadUseBusinessIds 对象id列表
     * @return 0/1
     */
    int delFileUploadUseBusinessByIds(List<Long>  fileUploadUseBusinessIds);


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadUseBusinessId
     * @return 返回列表对象
     */
        FileUploadUseBusinessResponseDto getFileUploadUseBusinessById(long fileUploadUseBusinessId);

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 枚举查询条件
     * @return 返回列表对象
     */
    FileUploadUseBusinessResponseDto getFileUploadUseBusinessByOne( FileUploadUseBusinessQueryEnum queryEnum,FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto);


    /**
    * 通过分页和枚举条件获取FileUploadUseBusiness信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return 返回列表对象列表
    * @author suven
    * date 2024-04-19 00:21:42
    */
    List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessListByQuery(FileUploadUseBusinessQueryEnum queryEnum,Object  paramObject);


    /**
     * 通过分页获取FileUploadUseBusiness信息实现查找缓存和数据库的方法
     * @param pager Pager 分页查询对象
     * @return 返回列表对象 列表
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessListByPage(FileUploadUseBusinessQueryEnum queryEnum,Pager pager);



    /**
     * 通过分页获取FileUploadUseBusiness 信息实现查找缓存和数据库的方法,包括查总页数
     * @param queryEnum Pager 分页枚举对象
     * @param pager Pager 分页查询对象
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    PageResult<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByNextPage(FileUploadUseBusinessQueryEnum queryEnum, Pager pager);

    /**
     * 通过分页获取FileUploadUseBusiness 信息实现查找缓存和数据库的方法,不查总页数
     * @param queryEnum Pager 分页枚举对象
     * @param pager Pager 分页查询对象
     * @param searchCount 是否询总条数, true/false, true为查询总条数,会多执行一次统计count sql
     * @return 返回分页对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */
    PageResult<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByNextPage(FileUploadUseBusinessQueryEnum queryEnum, Pager pager, boolean searchCount);



    /**
     * 通过idList聚合 查找信息列表的方法
     * @param idList Collection<Long> 表对象id列表
     * @return 返回对象列表
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     */

    List<FileUploadUseBusinessResponseDto> getFileUploadUseBusinessByIdList(Collection<Long> idList);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream 上传流
    * @return true/false
    */
    public boolean saveData(InputStream initialStream);

}