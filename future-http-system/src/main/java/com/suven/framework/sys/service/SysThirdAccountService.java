package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysThirdAccountRequestDto;
import com.suven.framework.sys.dto.response.SysThirdAccountResponseDto;
import com.suven.framework.sys.dto.enums.SysThirdAccountQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultList;




/**
 * @ClassName: SysThirdAccountService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:47
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 RPC业务接口逻辑实现类
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


public interface SysThirdAccountService {



    /**
     * 保存第三方登陆表更新数据库和缓存的实现方法
     * @param sysThirdAccountRequestDto  SysThirdAccountRequestDto
     * @return
     */
    SysThirdAccountResponseDto saveSysThirdAccount(SysThirdAccountRequestDto sysThirdAccountRequestDto);


     /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysThirdAccountRequestDto  SysThirdAccountRequestDto
     * @return
     */
    SysThirdAccountResponseDto saveIdSysThirdAccount(SysThirdAccountRequestDto sysThirdAccountRequestDto);


     /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList);

    /**
     * 保存第三方登陆表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList, int batchSize);

    /**
     * 保存第三方登陆表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList, int batchSize);



    /**
     * 更新第三方登陆表同时更新数据库和缓存的实现方法
     * @param sysThirdAccountRequestDto  SysThirdAccountRequestDto
     * @return
     */
    boolean updateSysThirdAccount (SysThirdAccountRequestDto sysThirdAccountRequestDto);

    /**
     * 更新第三方登陆表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysThirdAccountRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysThirdAccountIds
     * @return
     */
    int delSysThirdAccountByIds(List<Long>  sysThirdAccountIds);


    /**
     * 通过主键ID更新对象第三方登陆表实现缓存和数据库更新的方法
     * @param  sysThirdAccountId
     * @return
     */
        SysThirdAccountResponseDto getSysThirdAccountById(long sysThirdAccountId);

    /**
     * 通过参数limit0,1获取对象第三方登陆表的查询方法
     * @param  queryEnum
     * @return
     */
    SysThirdAccountResponseDto getSysThirdAccountByOne( SysThirdAccountQueryEnum queryEnum,SysThirdAccountRequestDto sysThirdAccountRequestDto);


    /**
    * 通过分页和枚举条件获取SysThirdAccount信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:09:47
    */
    List<SysThirdAccountResponseDto> getSysThirdAccountListByQuery(Object  paramObject, SysThirdAccountQueryEnum queryEnum);


    /**
     * 通过分页获取SysThirdAccount信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    List<SysThirdAccountResponseDto> getSysThirdAccountListByPage(Pager page, SysThirdAccountQueryEnum queryEnum);




    /**
     * 通过分页获取SysThirdAccount 第三方登陆表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    ResponseResultList<SysThirdAccountResponseDto> getSysThirdAccountByNextPage(Pager page, SysThirdAccountQueryEnum queryEnum);

    /**
     * 通过分页获取SysThirdAccount 第三方登陆表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    ResponseResultList<SysThirdAccountResponseDto> getSysThirdAccountByQueryPage(Pager page, SysThirdAccountQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}