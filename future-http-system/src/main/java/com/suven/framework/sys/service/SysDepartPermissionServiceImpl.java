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


import com.suven.framework.sys.entity.SysDepartPermission;
import com.suven.framework.sys.dao.SysDepartPermissionDao;
import com.suven.framework.sys.dto.request.SysDepartPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartPermissionQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysDepartPermissionServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 RPC业务接口逻辑实现类
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
public class SysDepartPermissionServiceImpl  implements SysDepartPermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDepartPermissionDao  sysDepartPermissionDao;



    /**
     * 保存部门权限表同时更新数据库和缓存的实现方法
     * @param sysDepartPermissionRequestDto SysDepartPermissionResponseDto
     * @return
     */
    @Override
    public SysDepartPermissionResponseDto saveSysDepartPermission(SysDepartPermissionRequestDto sysDepartPermissionRequestDto){
        if(sysDepartPermissionRequestDto== null){
            return null;
        }
        SysDepartPermission sysDepartPermission = SysDepartPermission.build().clone(sysDepartPermissionRequestDto);
        boolean result = sysDepartPermissionDao.save(sysDepartPermission);
        if(!result){
            return null;
        }
        SysDepartPermissionResponseDto sysDepartPermissionResponseDto = SysDepartPermissionResponseDto.build().clone(sysDepartPermission);
        return sysDepartPermissionResponseDto;


    }

    /**
     * 保存部门权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartPermissionRequestDto SysDepartPermissionResponseDto
     * @return
     */
    @Override
    public SysDepartPermissionResponseDto saveIdSysDepartPermission(SysDepartPermissionRequestDto sysDepartPermissionRequestDto){
        if(sysDepartPermissionRequestDto== null){
            return null;
        }
        SysDepartPermission sysDepartPermission = SysDepartPermission.build().clone(sysDepartPermissionRequestDto);
        sysDepartPermission = sysDepartPermissionDao.saveId(sysDepartPermission);
        if(null == sysDepartPermission){
            return null;
        }
        SysDepartPermissionResponseDto sysDepartPermissionResponseDto = SysDepartPermissionResponseDto.build().clone(sysDepartPermission);
        return sysDepartPermissionResponseDto;


    }
    /**
     * 保存部门权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDepartPermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDepartPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartPermission.build().clone(e)));
        boolean result = sysDepartPermissionDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存部门权限表同时更新数据库和缓存的实现方法
     * @param entityList SysDepartPermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDepartPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartPermission.build().clone(e)));
        boolean result = sysDepartPermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartPermission.build().clone(e)));
        boolean result = sysDepartPermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDepartPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepartPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepartPermission.build().clone(e)));
        boolean result =  sysDepartPermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新部门权限表同时更新数据库和缓存的实现方法
     * @param sysDepartPermissionRequestDto  SysDepartPermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysDepartPermission(SysDepartPermissionRequestDto sysDepartPermissionRequestDto){

          if(null ==  sysDepartPermissionRequestDto){
              return false;
          }

        SysDepartPermission sysDepartPermission = SysDepartPermission.build().clone(sysDepartPermissionRequestDto);

        return sysDepartPermissionDao.updateById(sysDepartPermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDepartPermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDepartPermissionDao.removeById(idList.get(0));
        }else {
            result =  sysDepartPermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象部门权限表实现缓存和数据库更新的方法
     * @param  sysDepartPermissionId
     * @return
     */
    @Override
    public SysDepartPermissionResponseDto getSysDepartPermissionById(long sysDepartPermissionId){
        if(sysDepartPermissionId < 0 ){
            return null;
        }
        SysDepartPermission sysDepartPermission =  sysDepartPermissionDao.getById(sysDepartPermissionId);
        if(sysDepartPermission == null){
            return null;
        }
        SysDepartPermissionResponseDto sysDepartPermissionResponseDto = SysDepartPermissionResponseDto.build().clone(sysDepartPermission);

        return sysDepartPermissionResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象部门权限表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDepartPermissionResponseDto getSysDepartPermissionByOne( SysDepartPermissionQueryEnum queryEnum,SysDepartPermissionRequestDto sysDepartPermissionRequestDto){
          if(sysDepartPermissionRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDepartPermission> queryWrapper = sysDepartPermissionDao.builderQueryEnum( queryEnum, sysDepartPermissionRequestDto);
            //分页对象        PageHelper
           Page<SysDepartPermission> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDepartPermission>  list = sysDepartPermissionDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDepartPermission sysDepartPermission = list.get(0);
           SysDepartPermissionResponseDto sysDepartPermissionResponseDto = SysDepartPermissionResponseDto.build().clone(sysDepartPermission);

            return sysDepartPermissionResponseDto ;
       }


 /**
   * 通过条件查询SysDepartPermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:14:27
   */
  @Override
  public List<SysDepartPermissionResponseDto> getSysDepartPermissionListByQuery( Object  paramObject, SysDepartPermissionQueryEnum queryEnum){

      QueryWrapper<SysDepartPermission> queryWrapper = sysDepartPermissionDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDepartPermission>  list = sysDepartPermissionDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDepartPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartPermissionResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDepartPermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    @Override
    public List<SysDepartPermissionResponseDto> getSysDepartPermissionListByPage(Pager page, SysDepartPermissionQueryEnum queryEnum){

        QueryWrapper<SysDepartPermission> queryWrapper =sysDepartPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartPermission>  list = sysDepartPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartPermissionResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDepartPermission 部门权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    @Override
    public PageResult<SysDepartPermissionResponseDto> getSysDepartPermissionByQueryPage(Pager page, SysDepartPermissionQueryEnum queryEnum){

        PageResult<SysDepartPermissionResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysDepartPermission> queryWrapper = sysDepartPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepartPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepartPermission>  list = sysDepartPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartPermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList);
        return PageResult;
    }

    /**
     * 通过分页获取SysDepartPermission信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    @Override
    public PageResult<SysDepartPermissionResponseDto> getSysDepartPermissionByNextPage(Pager page, SysDepartPermissionQueryEnum queryEnum){
        PageResult<SysDepartPermissionResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysDepartPermission> queryWrapper = sysDepartPermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDepartPermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDepartPermission>  list = sysDepartPermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartPermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartPermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return PageResult;

    }



    /**
     * 保存部门权限表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDepartPermission  setSysDepartPermission(){
        SysDepartPermission sysDepartPermission = new SysDepartPermission();
        /**
 			//sysDepartPermission.setDepartId (long departId);
 			//sysDepartPermission.setPermissionId (long permissionId);
 			//sysDepartPermission.setDataRuleIds (String dataRuleIds);
 			//sysDepartPermission.setCreateTime (Date createTime);
 			//sysDepartPermission.setUpdateTime (Date updateTime);
		**/

        return sysDepartPermission;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDepartPermissionDao, SysDepartPermission.class,0);
    }


}
