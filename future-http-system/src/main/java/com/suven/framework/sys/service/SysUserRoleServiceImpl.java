package com.suven.framework.sys.service;


import com.suven.framework.sys.dao.SysPermissionDao;
import com.suven.framework.sys.dao.SysRolePermissionDao;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.entity.SysPermission;
import com.suven.framework.sys.entity.SysRolePermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.InputStream;
import java.util.stream.Collectors;


import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.dao.SysUserRoleDao;
import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysUserRoleServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:11:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 RPC业务接口逻辑实现类
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
public class SysUserRoleServiceImpl  implements SysUserRoleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserRoleDao  sysUserRoleDao;

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Autowired
    private RoleTokenOutService roleTokenOutService;


    @Override
    public Set<String> getSysPermissionListByUserId(long id) {
        Set<String> permissionSet = new HashSet<>();
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id" , id);
        List<SysUserRole>  list = sysUserRoleDao.getListByQuery(queryWrapper);
        if(list.isEmpty() ){
            return permissionSet;
        }
        Set<Long> roleIds = new HashSet<>();
        list.forEach(i-> roleIds.add(i.getRoleId()));
        QueryWrapper<SysRolePermission> roleQuery = new QueryWrapper<>();
        roleQuery.in("role_id" , roleIds);

        List<SysRolePermission> rolePermissionList = sysRolePermissionDao.getListByQuery(roleQuery);
        if(rolePermissionList.isEmpty()){
            return permissionSet;
        }

        roleIds.clear();
        rolePermissionList.forEach(i-> roleIds.add(i.getPermissionId()));
        QueryWrapper<SysPermission> permissionQuery = new QueryWrapper<>();
        permissionQuery.in("id" , roleIds);
        List<SysPermission> permissionList = sysPermissionDao.getListByQuery(permissionQuery);
        if(permissionList.isEmpty()){
            return permissionSet;
        }
        permissionList.forEach(per ->{
            if(StringUtils.isNoneBlank(per.getPerms())){
                permissionSet.add(per.getPerms());
            }
        });
        return permissionSet;
    }


    /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法
     * @param sysUserRoleRequestDto SysUserRoleResponseDto
     * @return
     */
    @Override
    public SysUserRoleResponseDto saveSysUserRole(SysUserRoleRequestDto sysUserRoleRequestDto){
        if(sysUserRoleRequestDto== null){
            return null;
        }
        SysUserRole sysUserRole = SysUserRole.build().clone(sysUserRoleRequestDto);
        boolean result = sysUserRoleDao.save(sysUserRole);
        if(!result){
            return null;
        }
        SysUserRoleResponseDto sysUserRoleResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);
        return sysUserRoleResponseDto;


    }

    /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysUserRoleRequestDto SysUserRoleResponseDto
     * @return
     */
    @Override
    public SysUserRoleResponseDto saveIdSysUserRole(SysUserRoleRequestDto sysUserRoleRequestDto){
        if(sysUserRoleRequestDto== null){
            return null;
        }
        SysUserRole sysUserRole = SysUserRole.build().clone(sysUserRoleRequestDto);
        sysUserRole = sysUserRoleDao.saveId(sysUserRole);
        if(null == sysUserRole){
            return null;
        }
        SysUserRoleResponseDto sysUserRoleResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);
        return sysUserRoleResponseDto;


    }

    public void editRole(long userId, List<Long> roleIds) {
        deleteAll(userId);
        if (null != roleIds && !roleIds.isEmpty()) {
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds){
                if(null != roleId && roleId > 0) {
                    SysUserRole sysUserRole = SysUserRole.build().toUserId(userId).toRoleId(roleId);
                    list.add(sysUserRole);
                }
            }
            if (null != list && !list.isEmpty()) {
                this.sysUserRoleDao.saveBatch(list);
            }

        }
        this.roleTokenOutService.delUserTokenInRedisByUserIds(userId);
    }

    public void deleteAll(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        sysUserRoleDao.remove(queryWrapper);
    }


    /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysUserRoleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysUserRole(Collection<SysUserRoleRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysUserRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserRole.build().clone(e)));
        boolean result = sysUserRoleDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存用户角色关系表同时更新数据库和缓存的实现方法
     * @param entityList SysUserRoleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysUserRole(Collection<SysUserRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysUserRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserRole.build().clone(e)));
        boolean result = sysUserRoleDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysUserRole(Collection<SysUserRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserRole.build().clone(e)));
        boolean result = sysUserRoleDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysUserRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserRole.build().clone(e)));
        boolean result =  sysUserRoleDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新用户角色关系表同时更新数据库和缓存的实现方法
     * @param sysUserRoleRequestDto  SysUserRoleResponseDto
     * @return
     */
    @Override
    public boolean updateSysUserRole(SysUserRoleRequestDto sysUserRoleRequestDto){

          if(null ==  sysUserRoleRequestDto){
              return false;
          }

        SysUserRole sysUserRole = SysUserRole.build().clone(sysUserRoleRequestDto);

        return sysUserRoleDao.updateById(sysUserRole);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysUserRoleByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysUserRoleDao.removeById(idList.get(0));
        }else {
            result =  sysUserRoleDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象用户角色关系表实现缓存和数据库更新的方法
     * @param  sysUserRoleId
     * @return
     */
    @Override
    public SysUserRoleResponseDto getSysUserRoleById(long sysUserRoleId){
        if(sysUserRoleId < 0 ){
            return null;
        }
        SysUserRole sysUserRole =  sysUserRoleDao.getById(sysUserRoleId);
        if(sysUserRole == null){
            return null;
        }
        SysUserRoleResponseDto sysUserRoleResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);

        return sysUserRoleResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象用户角色关系表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysUserRoleResponseDto getSysUserRoleByOne( SysUserRoleQueryEnum queryEnum,SysUserRoleRequestDto sysUserRoleRequestDto){
          if(sysUserRoleRequestDto == null ){
              return null;
          }
           QueryWrapper<SysUserRole> queryWrapper = sysUserRoleDao.builderQueryEnum( queryEnum, sysUserRoleRequestDto);
            //分页对象        PageHelper
           Page<SysUserRole> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysUserRole>  list = sysUserRoleDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysUserRole sysUserRole = list.get(0);
           SysUserRoleResponseDto sysUserRoleResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);

            return sysUserRoleResponseDto ;
       }


 /**
   * 通过条件查询SysUserRole信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:11:27
   */
  @Override
  public List<SysUserRoleResponseDto> getSysUserRoleListByQuery( Object  paramObject, SysUserRoleQueryEnum queryEnum){

      QueryWrapper<SysUserRole> queryWrapper = sysUserRoleDao.builderQueryEnum( queryEnum, paramObject);

      List<SysUserRole>  list = sysUserRoleDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysUserRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserRoleResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysUserRole信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    @Override
    public List<SysUserRoleResponseDto> getSysUserRoleListByPage(Pager page, SysUserRoleQueryEnum queryEnum){

        QueryWrapper<SysUserRole> queryWrapper =sysUserRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysUserRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUserRole>  list = sysUserRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysUserRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysUserRoleResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysUserRole 用户角色关系表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    @Override
    public PageResult<SysUserRoleResponseDto> getSysUserRoleByQueryPage(Pager page, SysUserRoleQueryEnum queryEnum){

        PageResult<SysUserRoleResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysUserRole> queryWrapper = sysUserRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysUserRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysUserRole>  list = sysUserRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list, SysUserRoleResponseDto.class,iPage.getPages(),iPage.getTotal());
        return pageResult;
    }

    /**
     * 通过分页获取SysUserRole信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    @Override
    public PageResult<SysUserRoleResponseDto> getSysUserRoleByNextPage(Pager page, SysUserRoleQueryEnum queryEnum){
        PageResult<SysUserRoleResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysUserRole> queryWrapper = sysUserRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysUserRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysUserRole>  list = sysUserRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list, SysUserRoleResponseDto.class,iPage.getPages(),iPage.getTotal());
        return pageResult;

    }



    /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysUserRole  setSysUserRole(){
        SysUserRole sysUserRole = new SysUserRole();
        /**
 			//sysUserRole.setUserId (long userId);
 			//sysUserRole.setRoleId (long roleId);
 			//sysUserRole.setCreateTime (Date createTime);
 			//sysUserRole.setUpdateTime (Date updateTime);
		**/

        return sysUserRole;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysUserRoleDao, SysUserRole.class,0);
    }


    /**
     * 为指定角色批量添加用户关系
     * @param roleId
     * @param userIdList
     * @return
     */
    @Override
    public boolean addSysUserRole(long roleId, List<Long> userIdList) {

        SysUserRole sysUserRole = SysUserRole.build().toRoleId(roleId);
        QueryWrapper<SysUserRole> queryWrapper = sysUserRoleDao.builderQueryEnum( SysUserRoleQueryEnum.ROLE_ID, sysUserRole);
        List<SysUserRole>  list = sysUserRoleDao.getListByQuery(queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        Set<Long> userIdSet =  list.stream().map(userRole -> userRole.getUserId()).collect(Collectors.toSet());
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        userIdList.removeAll(userIdSet);
        userIdList.forEach(userId ->{
            SysUserRole role = SysUserRole.build().toUserId(userId).toRoleId(roleId);
            sysUserRoleList.add(role);
        });
       this.sysUserRoleDao.saveBatch(sysUserRoleList,sysUserRoleList.size());
        return true;
    }

}
