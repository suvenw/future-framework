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


import com.suven.framework.sys.entity.SysDict;
import com.suven.framework.sys.dao.SysDictDao;
import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysDictServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:09
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 后台字典类型表 RPC业务接口逻辑实现类
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
public class SysDictServiceImpl  implements SysDictService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDictDao  sysDictDao;



    /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法
     * @param sysDictRequestDto SysDictResponseDto
     * @return
     */
    @Override
    public SysDictResponseDto saveSysDict(SysDictRequestDto sysDictRequestDto){
        if(sysDictRequestDto== null){
            return null;
        }
        SysDict sysDict = SysDict.build().clone(sysDictRequestDto);
        boolean result = sysDictDao.save(sysDict);
        if(!result){
            return null;
        }
        SysDictResponseDto sysDictResponseDto = SysDictResponseDto.build().clone(sysDict);
        return sysDictResponseDto;


    }

    /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDictRequestDto SysDictResponseDto
     * @return
     */
    @Override
    public SysDictResponseDto saveIdSysDict(SysDictRequestDto sysDictRequestDto){
        if(sysDictRequestDto== null){
            return null;
        }
        SysDict sysDict = SysDict.build().clone(sysDictRequestDto);
        sysDict = sysDictDao.saveId(sysDict);
        if(null == sysDict){
            return null;
        }
        SysDictResponseDto sysDictResponseDto = SysDictResponseDto.build().clone(sysDict);
        return sysDictResponseDto;


    }
    /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDictRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDict(Collection<SysDictRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDict> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDict.build().clone(e)));
        boolean result = sysDictDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存后台字典类型表同时更新数据库和缓存的实现方法
     * @param entityList SysDictRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDict(Collection<SysDictRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDict> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDict.build().clone(e)));
        boolean result = sysDictDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDict(Collection<SysDictRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDict> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDict.build().clone(e)));
        boolean result = sysDictDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDictRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDict> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDict.build().clone(e)));
        boolean result =  sysDictDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新后台字典类型表同时更新数据库和缓存的实现方法
     * @param sysDictRequestDto  SysDictResponseDto
     * @return
     */
    @Override
    public boolean updateSysDict(SysDictRequestDto sysDictRequestDto){

          if(null ==  sysDictRequestDto){
              return false;
          }

        SysDict sysDict = SysDict.build().clone(sysDictRequestDto);

        return sysDictDao.updateById(sysDict);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDictByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDictDao.removeById(idList.get(0));
        }else {
            result =  sysDictDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象后台字典类型表实现缓存和数据库更新的方法
     * @param  sysDictId
     * @return
     */
    @Override
    public SysDictResponseDto getSysDictById(long sysDictId){
        if(sysDictId < 0 ){
            return null;
        }
        SysDict sysDict =  sysDictDao.getById(sysDictId);
        if(sysDict == null){
            return null;
        }
        SysDictResponseDto sysDictResponseDto = SysDictResponseDto.build().clone(sysDict);

        return sysDictResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象后台字典类型表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDictResponseDto getSysDictByOne( SysDictQueryEnum queryEnum,SysDictRequestDto sysDictRequestDto){
          if(sysDictRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDict> queryWrapper = sysDictDao.builderQueryEnum( queryEnum, sysDictRequestDto);
            //分页对象        PageHelper
           Page<SysDict> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDict>  list = sysDictDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDict sysDict = list.get(0);
           SysDictResponseDto sysDictResponseDto = SysDictResponseDto.build().clone(sysDict);

            return sysDictResponseDto ;
       }


 /**
   * 通过条件查询SysDict信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:10:09
   */
  @Override
  public List<SysDictResponseDto> getSysDictListByQuery( Object  paramObject, SysDictQueryEnum queryEnum){

      QueryWrapper<SysDict> queryWrapper = sysDictDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDict>  list = sysDictDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDictResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDict信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    @Override
    public List<SysDictResponseDto> getSysDictListByPage(Pager page, SysDictQueryEnum queryEnum){

        QueryWrapper<SysDict> queryWrapper =sysDictDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDict> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDict>  list = sysDictDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDict 后台字典类型表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    @Override
    public PageResult<SysDictResponseDto> getSysDictByQueryPage(Pager page, SysDictQueryEnum queryEnum){

        PageResult<SysDictResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysDict> queryWrapper = sysDictDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDict> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDict>  list = sysDictDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList);
        return PageResult;
    }

    /**
     * 通过分页获取SysDict信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    @Override
    public PageResult<SysDictResponseDto> getSysDictByNextPage(Pager page, SysDictQueryEnum queryEnum){
        PageResult<SysDictResponseDto> PageResult = new PageResult<>();
        QueryWrapper<SysDict> queryWrapper = sysDictDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDict> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDict>  list = sysDictDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        PageResult.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return PageResult;

    }



    /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDict  setSysDict(){
        SysDict sysDict = new SysDict();
        /**
 			//sysDict.setDictName (String dictName);
 			//sysDict.setDictCode (String dictCode);
 			//sysDict.setDescription (String description);
 			//sysDict.setDelFlag (int delFlag);
 			//sysDict.setCreateBy (String createBy);
 			//sysDict.setCreateTime (Date createTime);
 			//sysDict.setUpdateBy (String updateBy);
 			//sysDict.setUpdateTime (Date updateTime);
 			//sysDict.setType (int type);
		**/

        return sysDict;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDictDao, SysDict.class,0);
    }


}
