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


import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.dao.SysDepartDao;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysDepartServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:31
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 组织机构表 RPC业务接口逻辑实现类
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
public class SysDepartServiceImpl  implements SysDepartService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDepartDao  sysDepartDao;



    /**
     * 保存组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto SysDepartResponseDto
     * @return
     */
    @Override
    public SysDepartResponseDto saveSysDepart(SysDepartRequestDto sysDepartRequestDto){
        if(sysDepartRequestDto== null){
            return null;
        }
        SysDepart sysDepart = SysDepart.build().clone(sysDepartRequestDto);
        boolean result = sysDepartDao.save(sysDepart);
        if(!result){
            return null;
        }
        SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);
        return sysDepartResponseDto;


    }

    /**
     * 保存组织机构表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRequestDto SysDepartResponseDto
     * @return
     */
    @Override
    public SysDepartResponseDto saveIdSysDepart(SysDepartRequestDto sysDepartRequestDto){
        if(sysDepartRequestDto== null){
            return null;
        }
        SysDepart sysDepart = SysDepart.build().clone(sysDepartRequestDto);
        sysDepart = sysDepartDao.saveId(sysDepart);
        if(null == sysDepart){
            return null;
        }
        SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);
        return sysDepartResponseDto;


    }
    /**
     * 保存组织机构表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDepartRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDepart(Collection<SysDepartRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result = sysDepartDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存组织机构表同时更新数据库和缓存的实现方法
     * @param entityList SysDepartRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result = sysDepartDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result = sysDepartDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result =  sysDepartDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartResponseDto
     * @return
     */
    @Override
    public boolean updateSysDepart(SysDepartRequestDto sysDepartRequestDto){

          if(null ==  sysDepartRequestDto){
              return false;
          }

        SysDepart sysDepart = SysDepart.build().clone(sysDepartRequestDto);

        return sysDepartDao.updateById(sysDepart);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDepartByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDepartDao.removeById(idList.get(0));
        }else {
            result =  sysDepartDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象组织机构表实现缓存和数据库更新的方法
     * @param  sysDepartId
     * @return
     */
    @Override
    public SysDepartResponseDto getSysDepartById(long sysDepartId){
        if(sysDepartId < 0 ){
            return null;
        }
        SysDepart sysDepart =  sysDepartDao.getById(sysDepartId);
        if(sysDepart == null){
            return null;
        }
        SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

        return sysDepartResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象组织机构表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDepartResponseDto getSysDepartByOne( SysDepartQueryEnum queryEnum,SysDepartRequestDto sysDepartRequestDto){
          if(sysDepartRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDepart> queryWrapper = sysDepartDao.builderQueryEnum( queryEnum, sysDepartRequestDto);
            //分页对象        PageHelper
           Page<SysDepart> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDepart>  list = sysDepartDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDepart sysDepart = list.get(0);
           SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

            return sysDepartResponseDto ;
       }


 /**
   * 通过条件查询SysDepart信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:13:31
   */
  @Override
  public List<SysDepartResponseDto> getSysDepartListByQuery( Object  paramObject, SysDepartQueryEnum queryEnum){

      QueryWrapper<SysDepart> queryWrapper = sysDepartDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDepart>  list = sysDepartDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     */
    @Override
    public List<SysDepartResponseDto> getSysDepartListByPage(Pager page, SysDepartQueryEnum queryEnum){

        QueryWrapper<SysDepart> queryWrapper =sysDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepart>  list = sysDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDepart 组织机构表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     */
    @Override
    public ResponseResultPageVo<SysDepartResponseDto> getSysDepartByQueryPage(Pager page, SysDepartQueryEnum queryEnum){

        ResponseResultPageVo<SysDepartResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysDepart> queryWrapper = sysDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDepart>  list = sysDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     */
    @Override
    public ResponseResultPageVo<SysDepartResponseDto> getSysDepartByNextPage(Pager page, SysDepartQueryEnum queryEnum){
        ResponseResultPageVo<SysDepartResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysDepart> queryWrapper = sysDepartDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDepart> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDepart>  list = sysDepartDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDepartResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDepartResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }

    /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     */
    @Override
    public List<SysDepartResponseDto> getSysDepartByIdList(Collection<Long> idList){

        Collection<SysDepart> dbList =  this.sysDepartDao.listByIds(idList);
        List<SysDepartResponseDto>  responseDtoList = IterableConvert.convertList(dbList,SysDepartResponseDto.class);
        return responseDtoList;
    }


    /**
     * 保存组织机构表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDepart  setSysDepart(){
        SysDepart sysDepart = new SysDepart();
        /**
 			//sysDepart.setParentId (long parentId);
 			//sysDepart.setDepartName (String departName);
 			//sysDepart.setDepartNameEn (String departNameEn);
 			//sysDepart.setDepartNameAbbr (String departNameAbbr);
 			//sysDepart.setDepartOrder (int departOrder);
 			//sysDepart.setDescription (String description);
 			//sysDepart.setOrgCategory (String orgCategory);
 			//sysDepart.setOrgType (String orgType);
 			//sysDepart.setOrgCode (String orgCode);
 			//sysDepart.setMobile (String mobile);
 			//sysDepart.setFax (String fax);
 			//sysDepart.setAddress (String address);
 			//sysDepart.setMemo (String memo);
 			//sysDepart.setStatus (String status);
 			//sysDepart.setDelFlag (String delFlag);
 			//sysDepart.setQywxIdentifier (String qywxIdentifier);
 			//sysDepart.setCreateBy (String createBy);
 			//sysDepart.setCreateTime (Date createTime);
 			//sysDepart.setUpdateBy (String updateBy);
 			//sysDepart.setUpdateTime (Date updateTime);
		**/

        return sysDepart;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDepartDao, SysDepart.class,0);
    }

    @Override
    public List<SysDepartResponseDto> getList() {
        List<SysDepartResponseDto> resDtoList = new ArrayList();
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
        ((QueryWrapper)queryWrapper.eq("status", 1)).orderByAsc("sort");
        List<SysDepart> dbList = this.sysDepartDao.list(queryWrapper);
        if (dbList != null && !dbList.isEmpty()) {
            dbList.forEach((sysDepart) -> {
                SysDepartResponseDto sysDepartResponseDto = (SysDepartResponseDto)SysDepartResponseDto.build().clone(sysDepart);
                resDtoList.add(sysDepartResponseDto);
            });
            return resDtoList;
        } else {
            return resDtoList;
        }
    }


}
