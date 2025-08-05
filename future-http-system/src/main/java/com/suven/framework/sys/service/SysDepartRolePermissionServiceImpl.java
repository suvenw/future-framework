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


import com.suven.framework.sys.entity.SysDepartRolePermission;
import com.suven.framework.sys.dao.SysDepartRolePermissionDao;
import com.suven.framework.sys.dto.request.SysDepartRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRolePermissionQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysDepartRolePermissionServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:36
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 RPC业务接口逻辑实现类
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
public class SysDepartRolePermissionServiceImpl  implements SysDepartRolePermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDepartRolePermissionDao  sysDepartRolePermissionDao;



    /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法
     * @param sysDepartRolePermissionRequestDto SysDepartRolePermissionResponseDto
     * @return
     */
    @Override
    public SysDepartRolePermissionResponseDto saveSysDepartRolePermission(SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto){
        if(sysDepartRolePermissionRequestDto== null){
            return null;
        }
        SysDepartRolePermission sysDepartRolePermission = SysDepartRolePermission.build().clone(sysDepartRolePermissionRequestDto);
        boolean result = sysDepartRolePermissionDao.save(sysDepartRolePermission);
        if(!result){
            return null;
        }
        SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = SysDepartRolePermissionResponseDto.build().clone(sysDepartRolePermission);
        return sysDepartRolePermissionResponseDto;


    }

    /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRolePermissionRequestDto SysDepartRolePermissionResponseDto
     * @return
     */
    @Override
    public SysDepartRolePermissionResponseDto saveIdSysDepartRolePermission(SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto){
        if(sysDepartRolePermissionRequestDto== null){
            return null;
        }
        SysDepartRolePermission sysDepartRolePermission = SysDepartRolePermission.build().clone(sysDepartRolePermissionRequestDto);
        sysDepartRolePermission = sysDepartRolePermissionDao.saveId(sysDepartRolePermission);
        if(null == sysDepartRolePermission){
            return null;
        }
        SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = SysDepartRolePermissionResponseDto.build().clone(sysDepartRolePermission);
        return sysDepartRolePermissionResponseDto;


    }
    /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDepartRolePermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDepartRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRolePermission.build().clone(e)));
        boolean result = sysDepartRolePermissionDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存部门角色权限表同时更新数据库和缓存的实现方法
     * @param entityList SysDepartRolePermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDepartRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRolePermission.build().clone(e)));
        boolean result = sysDepartRolePermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRolePermission.build().clone(e)));
        boolean result = sysDepartRolePermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartRolePermission.build().clone(e)));
        boolean result =  sysDepartRolePermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新部门角色权限表同时更新数据库和缓存的实现方法
     * @param sysDepartRolePermissionRequestDto  SysDepartRolePermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysDepartRolePermission(SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto){

          if(null ==  sysDepartRolePermissionRequestDto){
              return false;
          }

        SysDepartRolePermission sysDepartRolePermission = SysDepartRolePermission.build().clone(sysDepartRolePermissionRequestDto);

        return sysDepartRolePermissionDao.updateById(sysDepartRolePermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDepartRolePermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDepartRolePermissionDao.removeById(idList.get(0));
        }else {
            result =  sysDepartRolePermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象部门角色权限表实现缓存和数据库更新的方法
     * @param  sysDepartRolePermissionId
     * @return
     */
    @Override
    public SysDepartRolePermissionResponseDto getSysDepartRolePermissionById(long sysDepartRolePermissionId){
        if(sysDepartRolePermissionId < 0 ){
            return null;
        }
        SysDepartRolePermission sysDepartRolePermission =  sysDepartRolePermissionDao.getById(sysDepartRolePermissionId);
        if(sysDepartRolePermission == null){
            return null;
        }
        SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = SysDepartRolePermissionResponseDto.build().clone(sysDepartRolePermission);

        return sysDepartRolePermissionResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象部门角色权限表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDepartRolePermissionResponseDto getSysDepartRolePermissionByOne( SysDepartRolePermissionQueryEnum queryEnum,SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto){
          if(sysDepartRolePermissionRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDepartRolePermission> queryWrapper = sysDepartRolePermissionDao.builderQueryEnum( queryEnum, sysDepartRolePermissionRequestDto);
            //分页对象        PageHelper
           Page<SysDepartRolePermission> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDepartRolePermission>  list = sysDepartRolePermissionDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDepartRolePermission sysDepartRolePermission = list.get(0);
           SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = SysDepartRolePermissionResponseDto.build().clone(sysDepartRolePermission);

            return sysDepartRolePermissionResponseDto ;
       }


 /**
   * 通过条件查询SysDepartRolePermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:13:36
   */
  @Override
  public List<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionListByQuery( Object  paramObject, SysDepartRolePermissionQueryEnum queryEnum){

      QueryWrapper<SysDepartRolePermission> queryWrapper = sysDepartRolePermissionDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDepartRolePermission>  list = sysDepartRolePermissionDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDepartRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRolePermissionResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDepartRolePermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    @Override
    public List<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionListByPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum){

        QueryWrapper<SysDepartRolePermission> queryWrapper =sysDepartRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartRolePermission>  list = sysDepartRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRolePermissionResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDepartRolePermission 部门角色权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    @Override
    public ResponseResultList<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionByQueryPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum){

        ResponseResultList<SysDepartRolePermissionResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysDepartRolePermission> queryWrapper = sysDepartRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartRolePermission>  list = sysDepartRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRolePermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList);
        return responseResultList;
    }

    /**
     * 通过分页获取SysDepartRolePermission信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    @Override
    public ResponseResultList<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionByNextPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum){
        ResponseResultList<SysDepartRolePermissionResponseDto> responseResultList = ResponseResultList.build();
        QueryWrapper<SysDepartRolePermission> queryWrapper = sysDepartRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDepartRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDepartRolePermission>  list = sysDepartRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartRolePermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        responseResultList.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return responseResultList;

    }



    /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDepartRolePermission  setSysDepartRolePermission(){
        SysDepartRolePermission sysDepartRolePermission = new SysDepartRolePermission();
        /**
 			//sysDepartRolePermission.setDepartId (long departId);
 			//sysDepartRolePermission.setRoleId (long roleId);
 			//sysDepartRolePermission.setPermissionId (long permissionId);
 			//sysDepartRolePermission.setDataRuleIds (String dataRuleIds);
 			//sysDepartRolePermission.setOperateDate (Date operateDate);
 			//sysDepartRolePermission.setOperateIp (String operateIp);
 			//sysDepartRolePermission.setCreateTime (Date createTime);
 			//sysDepartRolePermission.setUpdateTime (Date updateTime);
		**/

        return sysDepartRolePermission;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDepartRolePermissionDao, SysDepartRolePermission.class,0);
    }


}
