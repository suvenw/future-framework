package com.suven.framework.sys.service;


import com.suven.framework.sys.dto.response.SysLogResponseDto;
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


import com.suven.framework.sys.entity.SysPosition;
import com.suven.framework.sys.dao.SysPositionDao;
import com.suven.framework.sys.dto.request.SysPositionRequestDto;
import com.suven.framework.sys.dto.response.SysPositionResponseDto;
import com.suven.framework.sys.dto.enums.SysPositionQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysPositionServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:52
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  RPC业务接口逻辑实现类
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
public class SysPositionServiceImpl  implements SysPositionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPositionDao  sysPositionDao;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param sysPositionRequestDto SysPositionResponseDto
     * @return
     */
    @Override
    public SysPositionResponseDto saveSysPosition(SysPositionRequestDto sysPositionRequestDto){
        if(sysPositionRequestDto== null){
            return null;
        }
        SysPosition sysPosition = SysPosition.build().clone(sysPositionRequestDto);
        boolean result = sysPositionDao.save(sysPosition);
        if(!result){
            return null;
        }
        SysPositionResponseDto sysPositionResponseDto = SysPositionResponseDto.build().clone(sysPosition);
        return sysPositionResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysPositionRequestDto SysPositionResponseDto
     * @return
     */
    @Override
    public SysPositionResponseDto saveIdSysPosition(SysPositionRequestDto sysPositionRequestDto){
        if(sysPositionRequestDto== null){
            return null;
        }
        SysPosition sysPosition = SysPosition.build().clone(sysPositionRequestDto);
        sysPosition = sysPositionDao.saveId(sysPosition);
        if(null == sysPosition){
            return null;
        }
        SysPositionResponseDto sysPositionResponseDto = SysPositionResponseDto.build().clone(sysPosition);
        return sysPositionResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysPositionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysPosition(Collection<SysPositionRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysPosition> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPosition.build().clone(e)));
        boolean result = sysPositionDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList SysPositionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysPosition(Collection<SysPositionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysPosition> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPosition.build().clone(e)));
        boolean result = sysPositionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysPosition(Collection<SysPositionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPosition> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPosition.build().clone(e)));
        boolean result = sysPositionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysPositionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPosition> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPosition.build().clone(e)));
        boolean result =  sysPositionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param sysPositionRequestDto  SysPositionResponseDto
     * @return
     */
    @Override
    public boolean updateSysPosition(SysPositionRequestDto sysPositionRequestDto){

          if(null ==  sysPositionRequestDto){
              return false;
          }

        SysPosition sysPosition = SysPosition.build().clone(sysPositionRequestDto);

        return sysPositionDao.updateById(sysPosition);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysPositionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysPositionDao.removeById(idList.get(0));
        }else {
            result =  sysPositionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysPositionId
     * @return
     */
    @Override
    public SysPositionResponseDto getSysPositionById(long sysPositionId){
        if(sysPositionId < 0 ){
            return null;
        }
        SysPosition sysPosition =  sysPositionDao.getById(sysPositionId);
        if(sysPosition == null){
            return null;
        }
        SysPositionResponseDto sysPositionResponseDto = SysPositionResponseDto.build().clone(sysPosition);

        return sysPositionResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysPositionResponseDto getSysPositionByOne( SysPositionQueryEnum queryEnum,SysPositionRequestDto sysPositionRequestDto){
          if(sysPositionRequestDto == null ){
              return null;
          }
           QueryWrapper<SysPosition> queryWrapper = sysPositionDao.builderQueryEnum( queryEnum, sysPositionRequestDto);
            //分页对象        PageHelper
           Page<SysPosition> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysPosition>  list = sysPositionDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysPosition sysPosition = list.get(0);
           SysPositionResponseDto sysPositionResponseDto = SysPositionResponseDto.build().clone(sysPosition);

            return sysPositionResponseDto ;
       }


 /**
   * 通过条件查询SysPosition信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:13:52
   */
  @Override
  public List<SysPositionResponseDto> getSysPositionListByQuery( Object  paramObject, SysPositionQueryEnum queryEnum){

      QueryWrapper<SysPosition> queryWrapper = sysPositionDao.builderQueryEnum( queryEnum, paramObject);

      List<SysPosition>  list = sysPositionDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysPositionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPositionResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysPosition信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:52
     */
    @Override
    public List<SysPositionResponseDto> getSysPositionListByPage(Pager page, SysPositionQueryEnum queryEnum){

        QueryWrapper<SysPosition> queryWrapper =sysPositionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPosition> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPosition>  list = sysPositionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPositionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPositionResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysPosition 信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:52
     */
    @Override
    public PageResult<SysPositionResponseDto> getSysPositionByQueryPage(Pager page, SysPositionQueryEnum queryEnum){

        PageResult<SysPositionResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysPosition> queryWrapper = sysPositionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPosition> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPosition>  list = sysPositionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list, SysPositionResponseDto.class,iPage.getPages(),iPage.getTotal());
        return pageResult;
    }

    /**
     * 通过分页获取SysPosition信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:52
     */
    @Override
    public PageResult<SysPositionResponseDto> getSysPositionByNextPage(Pager page, SysPositionQueryEnum queryEnum){
        PageResult<SysPositionResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysPosition> queryWrapper = sysPositionDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysPosition> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysPosition>  list = sysPositionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list, SysPositionResponseDto.class,iPage.getPages(),iPage.getTotal());
        return pageResult;

    }



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return
     */
    public SysPosition  setSysPosition(){
        SysPosition sysPosition = new SysPosition();
        /**
 			//sysPosition.setCode (String code);
 			//sysPosition.setName (String name);
 			//sysPosition.setPostRank (String postRank);
 			//sysPosition.setCompanyId (String companyId);
 			//sysPosition.setCreateBy (String createBy);
 			//sysPosition.setCreateTime (Date createTime);
 			//sysPosition.setUpdateBy (String updateBy);
 			//sysPosition.setUpdateTime (Date updateTime);
 			//sysPosition.setSysOrgCode (String sysOrgCode);
		**/

        return sysPosition;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysPositionDao, SysPosition.class,0);
    }


}
