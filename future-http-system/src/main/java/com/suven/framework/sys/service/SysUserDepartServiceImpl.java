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


import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.dao.SysUserDepartDao;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysUserDepartServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:14
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 RPC业务接口逻辑实现类
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
public class SysUserDepartServiceImpl  implements SysUserDepartService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDepartDao  sysUserDepartDao;



    /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto SysUserDepartResponseDto
     * @return
     */
    @Override
    public SysUserDepartResponseDto saveSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto){
        if(sysUserDepartRequestDto== null){
            return null;
        }
        SysUserDepart sysUserDepart = SysUserDepart.build().clone(sysUserDepartRequestDto);
        boolean result = sysUserDepartDao.save(sysUserDepart);
        if(!result){
            return null;
        }
        SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);
        return sysUserDepartResponseDto;


    }

    /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysUserDepartRequestDto SysUserDepartResponseDto
     * @return
     */
    @Override
    public SysUserDepartResponseDto saveIdSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto){
        if(sysUserDepartRequestDto== null){
            return null;
        }
        SysUserDepart sysUserDepart = SysUserDepart.build().clone(sysUserDepartRequestDto);
        sysUserDepart = sysUserDepartDao.saveId(sysUserDepart);
        if(null == sysUserDepart){
            return null;
        }
        SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);
        return sysUserDepartResponseDto;


    }
    /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysUserDepartRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysUserDepart(Collection<SysUserDepartRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result = sysUserDepartDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存用户部门关系表同时更新数据库和缓存的实现方法
     * @param entityList SysUserDepartRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result = sysUserDepartDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result = sysUserDepartDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result =  sysUserDepartDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新用户部门关系表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartResponseDto
     * @return
     */
    @Override
    public boolean updateSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto){

          if(null ==  sysUserDepartRequestDto){
              return false;
          }

        SysUserDepart sysUserDepart = SysUserDepart.build().clone(sysUserDepartRequestDto);

        return sysUserDepartDao.updateById(sysUserDepart);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysUserDepartByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysUserDepartDao.removeById(idList.get(0));
        }else {
            result =  sysUserDepartDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象用户部门关系表实现缓存和数据库更新的方法
     * @param  sysUserDepartId
     * @return
     */
    @Override
    public SysUserDepartResponseDto getSysUserDepartById(long sysUserDepartId){
        if(sysUserDepartId < 0 ){
            return null;
        }
        SysUserDepart sysUserDepart =  sysUserDepartDao.getById(sysUserDepartId);
        if(sysUserDepart == null){
            return null;
        }
        SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);

        return sysUserDepartResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象用户部门关系表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysUserDepartResponseDto getSysUserDepartByOne( SysUserDepartQueryEnum queryEnum,SysUserDepartRequestDto sysUserDepartRequestDto){
          if(sysUserDepartRequestDto == null ){
              return null;
          }
           QueryWrapper<SysUserDepart> queryWrapper = sysUserDepartDao.builderQueryEnum( queryEnum, sysUserDepartRequestDto);
            //分页对象        PageHelper
           Page<SysUserDepart> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysUserDepart>  list = sysUserDepartDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysUserDepart sysUserDepart = list.get(0);
           SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);

            return sysUserDepartResponseDto ;
       }


 /**
   * 通过条件查询SysUserDepart信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:14:14
   */
  @Override
  public List<SysUserDepartResponseDto> getSysUserDepartListByQuery( Object  paramObject, SysUserDepartQueryEnum queryEnum){

      QueryWrapper<SysUserDepart> queryWrapper = sysUserDepartDao.builderQueryEnum( queryEnum, paramObject);

      List<SysUserDepart>  list = sysUserDepartDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysUserDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserDepartResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysUserDepart信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @Override
    public List<SysUserDepartResponseDto> getSysUserDepartListByPage(Pager page, SysUserDepartQueryEnum queryEnum){

        QueryWrapper<SysUserDepart> queryWrapper =sysUserDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysUserDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUserDepart>  list = sysUserDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysUserDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserDepartResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysUserDepart 用户部门关系表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @Override
    public PageResult<SysUserDepartResponseDto> getSysUserDepartByQueryPage(Pager page, SysUserDepartQueryEnum queryEnum){

        PageResult<SysUserDepartResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysUserDepart> queryWrapper = sysUserDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysUserDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUserDepart>  list = sysUserDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysUserDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserDepartResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList);
        return PageResult;
    }

    /**
     * 通过分页获取SysUserDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @Override
    public PageResult<SysUserDepartResponseDto> getSysUserDepartByNextPage(Pager page, SysUserDepartQueryEnum queryEnum){
        PageResult<SysUserDepartResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysUserDepart> queryWrapper = sysUserDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysUserDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysUserDepart>  list = sysUserDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysUserDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserDepartResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return PageResult;

    }



    /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysUserDepart  setSysUserDepart(){
        SysUserDepart sysUserDepart = new SysUserDepart();
        /**
 			//sysUserDepart.setUserId (long userId);
 			//sysUserDepart.setDepId (long depId);
 			//sysUserDepart.setCreateTime (Date createTime);
 			//sysUserDepart.setUpdateTime (Date updateTime);
		**/

        return sysUserDepart;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysUserDepartDao, SysUserDepart.class,0);
    }


    @Override
    public Boolean deleteUserInDepart(long depId,List<Long> idList) {
        QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dep_id", depId).in("user_id",idList);
        return sysUserDepartDao.remove(queryWrapper);
    }



    @Override
    public Boolean editSysDepartWithUser(SysUserDepartRequestDto dto) {

        long sysDepId = dto.getDepId();
        for (long sysUserId : dto.getUserIdList()) {
            SysUserDepart sysUserDepart = SysUserDepart.build().toUserId(sysUserId).toDepId(sysDepId);
            SysUserDepart one = sysUserDepartDao.getUserDepartByDepIdAndUserId(sysDepId,sysUserId);
            if (one == null) {
                sysUserDepartDao.save(sysUserDepart);
            }
        }
        return true;
    }
    /**
     * 查询用户角色信息by用户id
     * @param userId
     * @return
     */
    @Override
    public List<SysUserDepartResponseDto> getListByUserId(long userId) {
        SysUserDepartRequestDto requestDto =  SysUserDepartRequestDto.build().toUserId(userId);
        List<SysUserDepartResponseDto>   responseDtoList = this.getSysUserDepartListByQuery(requestDto,SysUserDepartQueryEnum.USER_ID);
        return responseDtoList;

    }


}
