package com.suven.framework.sys.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.entity.SysThirdAccount;
import com.suven.framework.sys.dao.SysThirdAccountDao;
import com.suven.framework.sys.dto.request.SysThirdAccountRequestDto;
import com.suven.framework.sys.dto.response.SysThirdAccountResponseDto;
import com.suven.framework.sys.dto.enums.SysThirdAccountQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysThirdAccountServiceImpl.java
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

@Service
public class SysThirdAccountServiceImpl  implements SysThirdAccountService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysThirdAccountDao  sysThirdAccountDao;



    /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法
     * @param sysThirdAccountRequestDto SysThirdAccountResponseDto
     * @return
     */
    @Override
    public SysThirdAccountResponseDto saveSysThirdAccount(SysThirdAccountRequestDto sysThirdAccountRequestDto){
        if(sysThirdAccountRequestDto== null){
            return null;
        }
        SysThirdAccount sysThirdAccount = SysThirdAccount.build().clone(sysThirdAccountRequestDto);
        boolean result = sysThirdAccountDao.save(sysThirdAccount);
        if(!result){
            return null;
        }
        SysThirdAccountResponseDto sysThirdAccountResponseDto = SysThirdAccountResponseDto.build().clone(sysThirdAccount);
        return sysThirdAccountResponseDto;


    }

    /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysThirdAccountRequestDto SysThirdAccountResponseDto
     * @return
     */
    @Override
    public SysThirdAccountResponseDto saveIdSysThirdAccount(SysThirdAccountRequestDto sysThirdAccountRequestDto){
        if(sysThirdAccountRequestDto== null){
            return null;
        }
        SysThirdAccount sysThirdAccount = SysThirdAccount.build().clone(sysThirdAccountRequestDto);
        sysThirdAccount = sysThirdAccountDao.saveId(sysThirdAccount);
        if(null == sysThirdAccount){
            return null;
        }
        SysThirdAccountResponseDto sysThirdAccountResponseDto = SysThirdAccountResponseDto.build().clone(sysThirdAccount);
        return sysThirdAccountResponseDto;


    }
    /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysThirdAccountRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysThirdAccount> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysThirdAccount.build().clone(e)));
        boolean result = sysThirdAccountDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存第三方登陆表同时更新数据库和缓存的实现方法
     * @param entityList SysThirdAccountRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysThirdAccount> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysThirdAccount.build().clone(e)));
        boolean result = sysThirdAccountDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysThirdAccount(Collection<SysThirdAccountRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysThirdAccount> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysThirdAccount.build().clone(e)));
        boolean result = sysThirdAccountDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysThirdAccountRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysThirdAccount> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysThirdAccount.build().clone(e)));
        boolean result =  sysThirdAccountDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新第三方登陆表同时更新数据库和缓存的实现方法
     * @param sysThirdAccountRequestDto  SysThirdAccountResponseDto
     * @return
     */
    @Override
    public boolean updateSysThirdAccount(SysThirdAccountRequestDto sysThirdAccountRequestDto){

          if(null ==  sysThirdAccountRequestDto){
              return false;
          }

        SysThirdAccount sysThirdAccount = SysThirdAccount.build().clone(sysThirdAccountRequestDto);

        return sysThirdAccountDao.updateById(sysThirdAccount);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysThirdAccountByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysThirdAccountDao.removeById(idList.get(0));
        }else {
            result =  sysThirdAccountDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象第三方登陆表实现缓存和数据库更新的方法
     * @param  sysThirdAccountId
     * @return
     */
    @Override
    public SysThirdAccountResponseDto getSysThirdAccountById(long sysThirdAccountId){
        if(sysThirdAccountId < 0 ){
            return null;
        }
        SysThirdAccount sysThirdAccount =  sysThirdAccountDao.getById(sysThirdAccountId);
        if(sysThirdAccount == null){
            return null;
        }
        SysThirdAccountResponseDto sysThirdAccountResponseDto = SysThirdAccountResponseDto.build().clone(sysThirdAccount);

        return sysThirdAccountResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象第三方登陆表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysThirdAccountResponseDto getSysThirdAccountByOne( SysThirdAccountQueryEnum queryEnum,SysThirdAccountRequestDto sysThirdAccountRequestDto){
          if(sysThirdAccountRequestDto == null ){
              return null;
          }
           QueryWrapper<SysThirdAccount> queryWrapper = sysThirdAccountDao.builderQueryEnum( queryEnum, sysThirdAccountRequestDto);
            //分页对象        PageHelper
           Page<SysThirdAccount> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysThirdAccount>  list = sysThirdAccountDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysThirdAccount sysThirdAccount = list.get(0);
           SysThirdAccountResponseDto sysThirdAccountResponseDto = SysThirdAccountResponseDto.build().clone(sysThirdAccount);

            return sysThirdAccountResponseDto ;
       }


 /**
   * 通过条件查询SysThirdAccount信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:09:47
   */
  @Override
  public List<SysThirdAccountResponseDto> getSysThirdAccountListByQuery( Object  paramObject, SysThirdAccountQueryEnum queryEnum){

      QueryWrapper<SysThirdAccount> queryWrapper = sysThirdAccountDao.builderQueryEnum( queryEnum, paramObject);

      List<SysThirdAccount>  list = sysThirdAccountDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysThirdAccountResponseDto>  resDtoList =  IterableConvert.convertList(list,SysThirdAccountResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysThirdAccount信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    @Override
    public List<SysThirdAccountResponseDto> getSysThirdAccountListByPage(Pager page, SysThirdAccountQueryEnum queryEnum){

        QueryWrapper<SysThirdAccount> queryWrapper =sysThirdAccountDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysThirdAccount> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysThirdAccount>  list = sysThirdAccountDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysThirdAccountResponseDto>  resDtoList =  IterableConvert.convertList(list,SysThirdAccountResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysThirdAccount 第三方登陆表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    @Override
    public ResponseResultList<SysThirdAccountResponseDto> getSysThirdAccountByQueryPage(Pager page, SysThirdAccountQueryEnum queryEnum){

        ResponseResultList<SysThirdAccountResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysThirdAccount> queryWrapper = sysThirdAccountDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysThirdAccount> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysThirdAccount>  list = sysThirdAccountDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysThirdAccountResponseDto>  resDtoList =  IterableConvert.convertList(list,SysThirdAccountResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList);
        return responseResultList;
    }

    /**
     * 通过分页获取SysThirdAccount信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:09:47
     */
    @Override
    public ResponseResultList<SysThirdAccountResponseDto> getSysThirdAccountByNextPage(Pager page, SysThirdAccountQueryEnum queryEnum){
        ResponseResultList<SysThirdAccountResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysThirdAccount> queryWrapper = sysThirdAccountDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysThirdAccount> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysThirdAccount>  list = sysThirdAccountDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysThirdAccountResponseDto>  resDtoList =  IterableConvert.convertList(list,SysThirdAccountResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return responseResultList;

    }



    /**
     * 保存第三方登陆表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysThirdAccount  setSysThirdAccount(){
        SysThirdAccount sysThirdAccount = new SysThirdAccount();
        /**
 			//sysThirdAccount.setSysUserId (String sysUserId);
 			//sysThirdAccount.setAvatar (String avatar);
 			//sysThirdAccount.setStatus (int status);
 			//sysThirdAccount.setDelFlag (int delFlag);
 			//sysThirdAccount.setRealname (String realname);
 			//sysThirdAccount.setThirdUserUuid (String thirdUserUuid);
 			//sysThirdAccount.setThirdUserId (String thirdUserId);
 			//sysThirdAccount.setThirdType (String thirdType);
		**/

        return sysThirdAccount;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysThirdAccountDao, SysThirdAccount.class,0);
    }


}
