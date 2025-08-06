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


import com.suven.framework.sys.entity.SysDepartRoleUser;
import com.suven.framework.sys.dao.SysDepartRoleUserDao;
import com.suven.framework.sys.dto.request.SysDepartRoleUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRoleUserResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRoleUserQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysDepartRoleUserServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:21
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 RPC业务接口逻辑实现类
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
public class SysDepartRoleUserServiceImpl  implements SysDepartRoleUserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDepartRoleUserDao  sysDepartRoleUserDao;



    /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法
     * @param sysDepartRoleUserRequestDto SysDepartRoleUserResponseDto
     * @return
     */
    @Override
    public SysDepartRoleUserResponseDto saveSysDepartRoleUser(SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto){
        if(sysDepartRoleUserRequestDto== null){
            return null;
        }
        SysDepartRoleUser sysDepartRoleUser = SysDepartRoleUser.build().clone(sysDepartRoleUserRequestDto);
        boolean result = sysDepartRoleUserDao.save(sysDepartRoleUser);
        if(!result){
            return null;
        }
        SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = SysDepartRoleUserResponseDto.build().clone(sysDepartRoleUser);
        return sysDepartRoleUserResponseDto;


    }

    /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRoleUserRequestDto SysDepartRoleUserResponseDto
     * @return
     */
    @Override
    public SysDepartRoleUserResponseDto saveIdSysDepartRoleUser(SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto){
        if(sysDepartRoleUserRequestDto== null){
            return null;
        }
        SysDepartRoleUser sysDepartRoleUser = SysDepartRoleUser.build().clone(sysDepartRoleUserRequestDto);
        sysDepartRoleUser = sysDepartRoleUserDao.saveId(sysDepartRoleUser);
        if(null == sysDepartRoleUser){
            return null;
        }
        SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = SysDepartRoleUserResponseDto.build().clone(sysDepartRoleUser);
        return sysDepartRoleUserResponseDto;


    }
    /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDepartRoleUserRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDepartRoleUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRoleUser.build().clone(e)));
        boolean result = sysDepartRoleUserDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存部门角色用户表同时更新数据库和缓存的实现方法
     * @param entityList SysDepartRoleUserRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDepartRoleUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRoleUser.build().clone(e)));
        boolean result = sysDepartRoleUserDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartRoleUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRoleUser.build().clone(e)));
        boolean result = sysDepartRoleUserDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartRoleUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRoleUser.build().clone(e)));
        boolean result =  sysDepartRoleUserDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新部门角色用户表同时更新数据库和缓存的实现方法
     * @param sysDepartRoleUserRequestDto  SysDepartRoleUserResponseDto
     * @return
     */
    @Override
    public boolean updateSysDepartRoleUser(SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto){

          if(null ==  sysDepartRoleUserRequestDto){
              return false;
          }

        SysDepartRoleUser sysDepartRoleUser = SysDepartRoleUser.build().clone(sysDepartRoleUserRequestDto);

        return sysDepartRoleUserDao.updateById(sysDepartRoleUser);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDepartRoleUserByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDepartRoleUserDao.removeById(idList.get(0));
        }else {
            result =  sysDepartRoleUserDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象部门角色用户表实现缓存和数据库更新的方法
     * @param  sysDepartRoleUserId
     * @return
     */
    @Override
    public SysDepartRoleUserResponseDto getSysDepartRoleUserById(long sysDepartRoleUserId){
        if(sysDepartRoleUserId < 0 ){
            return null;
        }
        SysDepartRoleUser sysDepartRoleUser =  sysDepartRoleUserDao.getById(sysDepartRoleUserId);
        if(sysDepartRoleUser == null){
            return null;
        }
        SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = SysDepartRoleUserResponseDto.build().clone(sysDepartRoleUser);

        return sysDepartRoleUserResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象部门角色用户表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDepartRoleUserResponseDto getSysDepartRoleUserByOne( SysDepartRoleUserQueryEnum queryEnum,SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto){
          if(sysDepartRoleUserRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDepartRoleUser> queryWrapper = sysDepartRoleUserDao.builderQueryEnum( queryEnum, sysDepartRoleUserRequestDto);
            //分页对象        PageHelper
           Page<SysDepartRoleUser> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDepartRoleUser>  list = sysDepartRoleUserDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDepartRoleUser sysDepartRoleUser = list.get(0);
           SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = SysDepartRoleUserResponseDto.build().clone(sysDepartRoleUser);

            return sysDepartRoleUserResponseDto ;
       }


 /**
   * 通过条件查询SysDepartRoleUser信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:14:21
   */
  @Override
  public List<SysDepartRoleUserResponseDto> getSysDepartRoleUserListByQuery( Object  paramObject, SysDepartRoleUserQueryEnum queryEnum){

      QueryWrapper<SysDepartRoleUser> queryWrapper = sysDepartRoleUserDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDepartRoleUser>  list = sysDepartRoleUserDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDepartRoleUserResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRoleUserResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDepartRoleUser信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:21
     */
    @Override
    public List<SysDepartRoleUserResponseDto> getSysDepartRoleUserListByPage(Pager page, SysDepartRoleUserQueryEnum queryEnum){

        QueryWrapper<SysDepartRoleUser> queryWrapper =sysDepartRoleUserDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartRoleUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartRoleUser>  list = sysDepartRoleUserDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRoleUserResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRoleUserResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDepartRoleUser 部门角色用户表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:21
     */
    @Override
    public ResponseResultPageVo<SysDepartRoleUserResponseDto> getSysDepartRoleUserByQueryPage(Pager page, SysDepartRoleUserQueryEnum queryEnum){

        ResponseResultPageVo<SysDepartRoleUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        QueryWrapper<SysDepartRoleUser> queryWrapper = sysDepartRoleUserDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartRoleUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartRoleUser>  list = sysDepartRoleUserDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRoleUserResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRoleUserResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysDepartRoleUser信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:21
     */
    @Override
    public ResponseResultPageVo<SysDepartRoleUserResponseDto> getSysDepartRoleUserByNextPage(Pager page, SysDepartRoleUserQueryEnum queryEnum){
        ResponseResultPageVo<SysDepartRoleUserResponseDto> ResponseResultPageVo = new ResponseResultPageVo<>();
        QueryWrapper<SysDepartRoleUser> queryWrapper = sysDepartRoleUserDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDepartRoleUser> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDepartRoleUser>  list = sysDepartRoleUserDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRoleUserResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRoleUserResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDepartRoleUser  setSysDepartRoleUser(){
        SysDepartRoleUser sysDepartRoleUser = new SysDepartRoleUser();
        /**
 			//sysDepartRoleUser.setUserId (String userId);
 			//sysDepartRoleUser.setDroleId (String droleId);
 			//sysDepartRoleUser.setCreateTime (Date createTime);
 			//sysDepartRoleUser.setUpdateTime (Date updateTime);
		**/

        return sysDepartRoleUser;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDepartRoleUserDao, SysDepartRoleUser.class,0);
    }


}
