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


import com.suven.framework.sys.entity.SysDataLog;
import com.suven.framework.sys.dao.SysDataLogDao;
import com.suven.framework.sys.dto.request.SysDataLogRequestDto;
import com.suven.framework.sys.dto.response.SysDataLogResponseDto;
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysDataLogServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
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
public class SysDataLogServiceImpl  implements SysDataLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDataLogDao  sysDataLogDao;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param sysDataLogRequestDto SysDataLogResponseDto
     * @return
     */
    @Override
    public SysDataLogResponseDto saveSysDataLog(SysDataLogRequestDto sysDataLogRequestDto){
        if(sysDataLogRequestDto== null){
            return null;
        }
        SysDataLog sysDataLog = SysDataLog.build().clone(sysDataLogRequestDto);
        boolean result = sysDataLogDao.save(sysDataLog);
        if(!result){
            return null;
        }
        SysDataLogResponseDto sysDataLogResponseDto = SysDataLogResponseDto.build().clone(sysDataLog);
        return sysDataLogResponseDto;


    }

    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDataLogRequestDto SysDataLogResponseDto
     * @return
     */
    @Override
    public SysDataLogResponseDto saveIdSysDataLog(SysDataLogRequestDto sysDataLogRequestDto){
        if(sysDataLogRequestDto== null){
            return null;
        }
        SysDataLog sysDataLog = SysDataLog.build().clone(sysDataLogRequestDto);
        sysDataLog = sysDataLogDao.saveId(sysDataLog);
        if(null == sysDataLog){
            return null;
        }
        SysDataLogResponseDto sysDataLogResponseDto = SysDataLogResponseDto.build().clone(sysDataLog);
        return sysDataLogResponseDto;


    }
    /**
     * 保存同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDataLogRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDataLog(Collection<SysDataLogRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDataLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDataLog.build().clone(e)));
        boolean result = sysDataLogDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存同时更新数据库和缓存的实现方法
     * @param entityList SysDataLogRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDataLog(Collection<SysDataLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDataLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDataLog.build().clone(e)));
        boolean result = sysDataLogDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDataLog(Collection<SysDataLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDataLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDataLog.build().clone(e)));
        boolean result = sysDataLogDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDataLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDataLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDataLog.build().clone(e)));
        boolean result =  sysDataLogDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param sysDataLogRequestDto  SysDataLogResponseDto
     * @return
     */
    @Override
    public boolean updateSysDataLog(SysDataLogRequestDto sysDataLogRequestDto){

          if(null ==  sysDataLogRequestDto){
              return false;
          }

        SysDataLog sysDataLog = SysDataLog.build().clone(sysDataLogRequestDto);

        return sysDataLogDao.updateById(sysDataLog);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDataLogByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDataLogDao.removeById(idList.get(0));
        }else {
            result =  sysDataLogDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysDataLogId
     * @return
     */
    @Override
    public SysDataLogResponseDto getSysDataLogById(long sysDataLogId){
        if(sysDataLogId < 0 ){
            return null;
        }
        SysDataLog sysDataLog =  sysDataLogDao.getById(sysDataLogId);
        if(sysDataLog == null){
            return null;
        }
        SysDataLogResponseDto sysDataLogResponseDto = SysDataLogResponseDto.build().clone(sysDataLog);

        return sysDataLogResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDataLogResponseDto getSysDataLogByOne( SysDataLogQueryEnum queryEnum,SysDataLogRequestDto sysDataLogRequestDto){
          if(sysDataLogRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDataLog> queryWrapper = sysDataLogDao.builderQueryEnum( queryEnum, sysDataLogRequestDto);
            //分页对象        PageHelper
           Page<SysDataLog> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDataLog>  list = sysDataLogDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDataLog sysDataLog = list.get(0);
           SysDataLogResponseDto sysDataLogResponseDto = SysDataLogResponseDto.build().clone(sysDataLog);

            return sysDataLogResponseDto ;
       }


 /**
   * 通过条件查询SysDataLog信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:10:02
   */
  @Override
  public List<SysDataLogResponseDto> getSysDataLogListByQuery( Object  paramObject, SysDataLogQueryEnum queryEnum){

      QueryWrapper<SysDataLog> queryWrapper = sysDataLogDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDataLog>  list = sysDataLogDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDataLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDataLogResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDataLog信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    @Override
    public List<SysDataLogResponseDto> getSysDataLogListByPage(Pager page, SysDataLogQueryEnum queryEnum){

        QueryWrapper<SysDataLog> queryWrapper =sysDataLogDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDataLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDataLog>  list = sysDataLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDataLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDataLogResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDataLog 信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    @Override
    public PageResult<SysDataLogResponseDto> getSysDataLogByQueryPage(Pager page, SysDataLogQueryEnum queryEnum){

        PageResult<SysDataLogResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysDataLog> queryWrapper = sysDataLogDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDataLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDataLog>  list = sysDataLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list,SysDataLogResponseDto.class,iPage.getPages(),iPage.getTotal());
        return pageResult;
    }

    /**
     * 通过分页获取SysDataLog信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     */
    @Override
    public PageResult<SysDataLogResponseDto> getSysDataLogByNextPage(Pager page, SysDataLogQueryEnum queryEnum){
        PageResult<SysDataLogResponseDto> pageResult = new PageResult<>();
        QueryWrapper<SysDataLog> queryWrapper = sysDataLogDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDataLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDataLog>  list = sysDataLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        pageResult.convertBuild(list,SysDataLogResponseDto.class,iPage.getPages(),iPage.getTotal());

        return pageResult;

    }



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDataLog  setSysDataLog(){
        SysDataLog sysDataLog = new SysDataLog();
        /**
 			//sysDataLog.setCreateBy (String createBy);
 			//sysDataLog.setCreateTime (Date createTime);
 			//sysDataLog.setUpdateBy (String updateBy);
 			//sysDataLog.setUpdateTime (Date updateTime);
 			//sysDataLog.setDataTable (String dataTable);
 			//sysDataLog.setDataId (String dataId);
 			//sysDataLog.setDataContent (String dataContent);
 			//sysDataLog.setDataVersion (int dataVersion);
		**/

        return sysDataLog;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDataLogDao, SysDataLog.class,0);
    }


}
