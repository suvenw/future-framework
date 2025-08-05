package com.suven.framework.sys.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.InputStream;


import com.suven.framework.sys.entity.SysPermission;
import com.suven.framework.sys.dao.SysPermissionDao;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysPermissionServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:30
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限表 RPC业务接口逻辑实现类
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
public class SysPermissionServiceImpl  implements SysPermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPermissionDao  sysPermissionDao;





    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto SysPermissionResponseDto
     * @return
     */
    @Override
    public SysPermissionResponseDto saveSysPermission(SysPermissionRequestDto sysPermissionRequestDto){
        if(sysPermissionRequestDto== null){
            return null;
        }
        Date date = new Date();
        SysPermission sysPermission = SysPermission.build().clone(sysPermissionRequestDto);
        sysPermission.toCreateTime(date);
        sysPermission.toUpdateTime(date);
        boolean result = sysPermissionDao.save(sysPermission);
        if(!result){
            return null;
        }
        SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
        return sysPermissionResponseDto;


    }

    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysPermissionRequestDto SysPermissionResponseDto
     * @return
     */
    @Override
    public SysPermissionResponseDto saveIdSysPermission(SysPermissionRequestDto sysPermissionRequestDto){
        if(sysPermissionRequestDto== null){
            return null;
        }
        SysPermission sysPermission = SysPermission.build().clone(sysPermissionRequestDto);
        sysPermission = sysPermissionDao.saveId(sysPermission);
        if(null == sysPermission){
            return null;
        }
        SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
        return sysPermissionResponseDto;


    }
    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysPermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysPermission(Collection<SysPermissionRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result = sysPermissionDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存菜单权限表同时更新数据库和缓存的实现方法
     * @param entityList SysPermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result = sysPermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result = sysPermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result =  sysPermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysPermission(SysPermissionRequestDto sysPermissionRequestDto){

          if(null ==  sysPermissionRequestDto){
              return false;
          }

        SysPermission sysPermission = SysPermission.build().clone(sysPermissionRequestDto);
        sysPermission.toUpdateTime(new Date());
        return sysPermissionDao.updateById(sysPermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysPermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysPermissionDao.removeById(idList.get(0));
        }else {
            result =  sysPermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象菜单权限表实现缓存和数据库更新的方法
     * @param  sysPermissionId
     * @return
     */
    @Override
    public SysPermissionResponseDto getSysPermissionById(long sysPermissionId){
        if(sysPermissionId < 0 ){
            return null;
        }
        SysPermission sysPermission =  sysPermissionDao.getById(sysPermissionId);
        if(sysPermission == null){
            return null;
        }
        SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);

        return sysPermissionResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象菜单权限表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysPermissionResponseDto getSysPermissionByOne( SysPermissionQueryEnum queryEnum,SysPermissionRequestDto sysPermissionRequestDto){
          if(sysPermissionRequestDto == null ){
              return null;
          }
           QueryWrapper<SysPermission> queryWrapper = sysPermissionDao.builderQueryEnum( queryEnum, sysPermissionRequestDto);
            //分页对象        PageHelper
           Page<SysPermission> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysPermission>  list = sysPermissionDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysPermission sysPermission = list.get(0);
           SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);

            return sysPermissionResponseDto ;
       }


 /**
   * 通过条件查询SysPermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:10:30
   */
  @Override
  public List<SysPermissionResponseDto> getSysPermissionListByQuery( Object  paramObject, SysPermissionQueryEnum queryEnum){

      QueryWrapper<SysPermission> queryWrapper = sysPermissionDao.builderQueryEnum( queryEnum, paramObject);

      List<SysPermission>  list = sysPermissionDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysPermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    @Override
    public List<SysPermissionResponseDto> getSysPermissionListByPage(Pager page, SysPermissionQueryEnum queryEnum){

        QueryWrapper<SysPermission> queryWrapper =sysPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPermission>  list = sysPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysPermission 菜单权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    @Override
    public ResponseResultList<SysPermissionResponseDto> getSysPermissionByQueryPage(Pager page, SysPermissionQueryEnum queryEnum){

        ResponseResultList<SysPermissionResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysPermission> queryWrapper = sysPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPermission>  list = sysPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList);
        return responseResultList;
    }

    /**
     * 通过分页获取SysPermission信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    @Override
    public ResponseResultList<SysPermissionResponseDto> getSysPermissionByNextPage(Pager page, SysPermissionQueryEnum queryEnum){
        ResponseResultList<SysPermissionResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysPermission> queryWrapper = sysPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
//        Page<SysPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        Page<SysPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysPermission>  list = sysPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return responseResultList;

    }



    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysPermission  setSysPermission(){
        SysPermission sysPermission = new SysPermission();
        /**
 			//sysPermission.setParentId (long parentId);
 			//sysPermission.setName (String name);
 			//sysPermission.setUrl (String url);
 			//sysPermission.setComponent (String component);
 			//sysPermission.setComponentName (String componentName);
 			//sysPermission.setRedirect (String redirect);
 			//sysPermission.setMenuType (int menuType);
 			//sysPermission.setPerms (String perms);
 			//sysPermission.setPermsType (String permsType);
 			//sysPermission.setSortNo (double sortNo);
 			//sysPermission.setAlwaysShow (int alwaysShow);
 			//sysPermission.setIcon (String icon);
 			//sysPermission.setIsRoute (int isRoute);
 			//sysPermission.setIsLeaf (int isLeaf);
 			//sysPermission.setKeepAlive (int keepAlive);
 			//sysPermission.setHidden (int hidden);
 			//sysPermission.setHideTab (int hideTab);
 			//sysPermission.setDescription (String description);
 			//sysPermission.setCreateBy (String createBy);
 			//sysPermission.setCreateTime (Date createTime);
 			//sysPermission.setUpdateBy (String updateBy);
 			//sysPermission.setUpdateTime (Date updateTime);
 			//sysPermission.setDelFlag (int delFlag);
 			//sysPermission.setRuleFlag (int ruleFlag);
 			//sysPermission.setStatus (int status);
 			//sysPermission.setInternalOrExternal (int internalOrExternal);
		**/

        return sysPermission;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysPermissionDao, SysPermission.class,0);
    }

    @Override
    public List<SysPermissionResponseDto> getSysPermissionList(SysPermissionRequestDto dto) {

        SysPermission sysPermission =  SysPermission.build().clone(dto);
        List<SysPermission> list = sysPermissionDao.getSysPermissionList(sysPermission);
        List<SysPermissionResponseDto>  resDtoList = IterableConvert.convertList(list,SysPermissionResponseDto.class);
        return resDtoList;
    }

    @Override
    public List<SysPermissionResponseDto> queryByUser(long userId) {

        List<SysPermission> list = sysPermissionDao.queryByUserId(userId);
        List<SysPermissionResponseDto> resDtoList=  IterableConvert.convertList(list,SysPermissionResponseDto.class);
        return resDtoList;
    }

    @Override
    public List<SysPermissionResponseDto> getAuthListByType(Integer menuType) {
        SysPermissionRequestDto requestDto = SysPermissionRequestDto.build().toMenuType(menuType).toDelFlag(0);
        QueryWrapper<SysPermission> queryWrapper = sysPermissionDao.builderQueryEnum(SysPermissionQueryEnum.MENU_TYPE_DEL_FLAG,requestDto);
        List<SysPermission> list = sysPermissionDao.getListByQuery(queryWrapper);

        List<SysPermissionResponseDto> resDtoList  = IterableConvert.convertList(list,SysPermissionResponseDto.class);

        return resDtoList;

    }

}
