package com.suven.framework.sys.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.InputStream;


import com.suven.framework.sys.entity.SysRole;
import com.suven.framework.sys.dao.SysRoleDao;
import com.suven.framework.sys.dto.request.SysRoleRequestDto;
import com.suven.framework.sys.dto.response.SysRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysRoleQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysRoleServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 RPC业务接口逻辑实现类
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
public class SysRoleServiceImpl  implements SysRoleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRoleDao  sysRoleDao;



    /**
     * 保存角色表同时更新数据库和缓存的实现方法
     * @param sysRoleRequestDto SysRoleResponseDto
     * @return
     */
    @Override
    public SysRoleResponseDto saveSysRole(SysRoleRequestDto sysRoleRequestDto){
        if(sysRoleRequestDto== null){
            return null;
        }
        Date date = new Date();
        SysRole sysRole = SysRole.build().clone(sysRoleRequestDto);
        sysRole.toCreateTime(date);
        sysRole.toUpdateTime(date);
        boolean result = sysRoleDao.save(sysRole);
        if(!result){
            return null;
        }
        SysRoleResponseDto sysRoleResponseDto = SysRoleResponseDto.build().clone(sysRole);
        return sysRoleResponseDto;


    }

    /**
     * 保存角色表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysRoleRequestDto SysRoleResponseDto
     * @return
     */
    @Override
    public SysRoleResponseDto saveIdSysRole(SysRoleRequestDto sysRoleRequestDto){
        if(sysRoleRequestDto== null){
            return null;
        }
        SysRole sysRole = SysRole.build().clone(sysRoleRequestDto);
        Date date = new Date();
        sysRole.toCreateTime(date);
        sysRole.toUpdateTime(date);
        sysRole = sysRoleDao.saveId(sysRole);
        if(null == sysRole){
            return null;
        }
        SysRoleResponseDto sysRoleResponseDto = SysRoleResponseDto.build().clone(sysRole);
        return sysRoleResponseDto;


    }
    /**
     * 保存角色表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysRoleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysRole(Collection<SysRoleRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRole.build().clone(e)));
        boolean result = sysRoleDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存角色表同时更新数据库和缓存的实现方法
     * @param entityList SysRoleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysRole(Collection<SysRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRole.build().clone(e)));
        boolean result = sysRoleDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysRole(Collection<SysRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRole.build().clone(e)));
        boolean result = sysRoleDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysRoleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRole> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRole.build().clone(e)));
        boolean result =  sysRoleDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     * @param sysRoleRequestDto  SysRoleResponseDto
     * @return
     */
    @Override
    public boolean updateSysRole(SysRoleRequestDto sysRoleRequestDto){

          if(null ==  sysRoleRequestDto){
              return false;
          }

        SysRole sysRole = SysRole.build().clone(sysRoleRequestDto);
        sysRole.setUpdateTime(new Date());
        return sysRoleDao.updateById(sysRole);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysRoleByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysRoleDao.removeById(idList.get(0));
        }else {
            result =  sysRoleDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象角色表实现缓存和数据库更新的方法
     * @param  sysRoleId
     * @return
     */
    @Override
    public SysRoleResponseDto getSysRoleById(long sysRoleId){
        if(sysRoleId < 0 ){
            return null;
        }
        SysRole sysRole =  sysRoleDao.getById(sysRoleId);
        if(sysRole == null){
            return null;
        }
        SysRoleResponseDto sysRoleResponseDto = SysRoleResponseDto.build().clone(sysRole);

        return sysRoleResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象角色表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysRoleResponseDto getSysRoleByOne( SysRoleQueryEnum queryEnum,SysRoleRequestDto sysRoleRequestDto){
          if(sysRoleRequestDto == null ){
              return null;
          }
           QueryWrapper<SysRole> queryWrapper = sysRoleDao.builderQueryEnum( queryEnum, sysRoleRequestDto);
            //分页对象        PageHelper
           Page<SysRole> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysRole>  list = sysRoleDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysRole sysRole = list.get(0);
           SysRoleResponseDto sysRoleResponseDto = SysRoleResponseDto.build().clone(sysRole);

            return sysRoleResponseDto ;
       }


 /**
   * 通过条件查询SysRole信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:10:43
   */
  @Override
  public List<SysRoleResponseDto> getSysRoleListByQuery( Object  paramObject, SysRoleQueryEnum queryEnum){

      QueryWrapper<SysRole> queryWrapper = sysRoleDao.builderQueryEnum( queryEnum, paramObject);

      List<SysRole>  list = sysRoleDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRoleResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysRole信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @Override
    public List<SysRoleResponseDto> getSysRoleListByPage(Pager page, SysRoleQueryEnum queryEnum){

        QueryWrapper<SysRole> queryWrapper =sysRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysRole>  list = sysRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRoleResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysRole 角色表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @Override
    public ResponseResultPageVo<SysRoleResponseDto> getSysRoleByQueryPage(Pager page, SysRoleQueryEnum queryEnum){

        ResponseResultPageVo<SysRoleResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysRole> queryWrapper = sysRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysRole>  list = sysRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRoleResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysRole信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @Override
    public ResponseResultPageVo<SysRoleResponseDto> getSysRoleByNextPage(Pager page, SysRoleQueryEnum queryEnum){
        ResponseResultPageVo<SysRoleResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysRole> queryWrapper = sysRoleDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysRole> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysRole>  list = sysRoleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRoleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRoleResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存角色表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysRole  setSysRole(){
        SysRole sysRole = new SysRole();
        /**
 			//sysRole.setRoleName (String roleName);
 			//sysRole.setRoleCode (String roleCode);
 			//sysRole.setDescription (String description);
 			//sysRole.setCreateBy (String createBy);
 			//sysRole.setCreateTime (Date createTime);
 			//sysRole.setUpdateBy (String updateBy);
 			//sysRole.setUpdateTime (Date updateTime);
		**/

        return sysRole;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysRoleDao, SysRole.class,0);
    }

    /**
     * 通过分页获取SysRoleResponseDto信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    @Override
    public List<SysRoleResponseDto> getSysDepartByIdList(Collection<Long> idList){

        Collection<SysRole> dbList =  this.sysRoleDao.listByIds(idList);
        List<SysRoleResponseDto>  responseDtoList = IterableConvert.convertList(dbList,SysRoleResponseDto.class);
        return responseDtoList;
    }


}
