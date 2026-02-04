package com.suven.framework.fileinter.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.fileinter.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




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


public interface FileUploadStorageService {



    /**
     * 保存更新数据库和缓存的实现方法
     * @param fileUploadStorageRequestDto  FileUploadStorageRequestDto
     * @return 返回表对象
     */
    FileUploadStorageResponseDto saveFileUploadStorage(FileUploadStorageRequestDto fileUploadStorageRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param fileUploadStorageRequestDto  FileUploadStorageRequestDto
     * @return 返回表对象
     */
    FileUploadStorageResponseDto saveIdFileUploadStorage(FileUploadStorageRequestDto fileUploadStorageRequestDto);


     /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return true/false
     */
    boolean saveBatchIdFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveBatchFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean saveOrUpdateBatchFileUploadStorage(Collection<FileUploadStorageRequestDto> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param fileUploadStorageRequestDto  FileUploadStorageRequestDto
     * @return true/false
     */
    boolean updateFileUploadStorage (FileUploadStorageRequestDto fileUploadStorageRequestDto);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return true/false
     */
    boolean updateBatchById(Collection<FileUploadStorageRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  fileUploadStorageIds 对象id列表
     * @return 0/1
     */
    int delFileUploadStorageByIds(List<Long>  fileUploadStorageIds);


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  fileUploadStorageId
     * @return 返回列表对象
     */
        FileUploadStorageResponseDto getFileUploadStorageById(long fileUploadStorageId);

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum 枚举查询条件
     * @return 返回列表对象
     */
    FileUploadStorageResponseDto getFileUploadStorageByOne( FileUploadStorageQueryEnum queryEnum,FileUploadStorageRequestDto fileUploadStorageRequestDto);


    /**
    * 通过分页和枚举条件获取FileUploadStorage信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return 返回列表对象列表
    * @author suven
    * date 2024-04-18 23:55:18
    */
    List<FileUploadStorageResponseDto> getFileUploadStorageListByQuery(FileUploadStorageQueryEnum queryEnum,Object  paramObject);


    /**
     * 通过分页获取FileUploadStorage信息实现查找缓存和数据库的方法
     * @param pager Pager 分页查询对象
     * @return 返回列表对象 列表
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    List<FileUploadStorageResponseDto> getFileUploadStorageListByPage(FileUploadStorageQueryEnum queryEnum,Pager pager);




    /**
     * 通过分页获取FileUploadStorage 信息实现查找缓存和数据库的方法,包括查总页数
     * @param pager Pager 分页查询对象
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    PageResult<FileUploadStorageResponseDto> getFileUploadStorageByNextPage(FileUploadStorageQueryEnum queryEnum, Pager pager);

    /**
    * 通过分页获取FileUploadStorage信息列表,实现查找缓存和数据库的方法,并且查询总页数
    * @param pager pager分页查询对象
    * @return 返回分页对象
    * @author suven  作者
    * date 2024-04-18 22:13:59 创建时间
    */
    PageResult<FileUploadStorageResponseDto> getFileUploadStorageByNextPage(FileUploadStorageQueryEnum queryEnum, Pager pager, boolean searchCount);
    /**
     * 通过分页获取FileUploadStorage 信息实现查找缓存和数据库的方法,不查总页数
     * @param pager Pager 分页查询对象
     * @return 返回列表对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */
    PageResult<FileUploadStorageResponseDto> getFileUploadStorageByQueryPage(FileUploadStorageQueryEnum queryEnum , Pager pager);


    /**
     * 通过idList聚合 查找信息列表的方法
     * @param idList Collection<Long> 表对象id列表
     * @return 返回对象列表
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     */

    List<FileUploadStorageResponseDto> getFileUploadStorageByIdList(Collection<Long> idList);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream 上传流
    * @return true/false
    */
    public boolean saveData(InputStream initialStream);

}