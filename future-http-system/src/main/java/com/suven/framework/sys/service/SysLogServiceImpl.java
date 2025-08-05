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


import com.suven.framework.sys.entity.SysLog;
import com.suven.framework.sys.dao.SysLogDao;
import com.suven.framework.sys.dto.request.SysLogRequestDto;
import com.suven.framework.sys.dto.response.SysLogResponseDto;
import com.suven.framework.sys.dto.enums.SysLogQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysLogServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:19
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 RPC业务接口逻辑实现类
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
public class SysLogServiceImpl  implements SysLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysLogDao  sysLogDao;



    /**
     * 保存系统日志表同时更新数据库和缓存的实现方法
     * @param sysLogRequestDto SysLogResponseDto
     * @return
     */
    @Override
    public SysLogResponseDto saveSysLog(SysLogRequestDto sysLogRequestDto){
        if(sysLogRequestDto== null){
            return null;
        }
        SysLog sysLog = SysLog.build().clone(sysLogRequestDto);
        boolean result = sysLogDao.save(sysLog);
        if(!result){
            return null;
        }
        SysLogResponseDto sysLogResponseDto = SysLogResponseDto.build().clone(sysLog);
        return sysLogResponseDto;


    }

    /**
     * 保存系统日志表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysLogRequestDto SysLogResponseDto
     * @return
     */
    @Override
    public SysLogResponseDto saveIdSysLog(SysLogRequestDto sysLogRequestDto){
        if(sysLogRequestDto== null){
            return null;
        }
        SysLog sysLog = SysLog.build().clone(sysLogRequestDto);
        sysLog = sysLogDao.saveId(sysLog);
        if(null == sysLog){
            return null;
        }
        SysLogResponseDto sysLogResponseDto = SysLogResponseDto.build().clone(sysLog);
        return sysLogResponseDto;


    }
    /**
     * 保存系统日志表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysLogRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysLog(Collection<SysLogRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysLog.build().clone(e)));
        boolean result = sysLogDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存系统日志表同时更新数据库和缓存的实现方法
     * @param entityList SysLogRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysLog(Collection<SysLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysLog.build().clone(e)));
        boolean result = sysLogDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysLog(Collection<SysLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysLog.build().clone(e)));
        boolean result = sysLogDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysLogRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysLog> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysLog.build().clone(e)));
        boolean result =  sysLogDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新系统日志表同时更新数据库和缓存的实现方法
     * @param sysLogRequestDto  SysLogResponseDto
     * @return
     */
    @Override
    public boolean updateSysLog(SysLogRequestDto sysLogRequestDto){

          if(null ==  sysLogRequestDto){
              return false;
          }

        SysLog sysLog = SysLog.build().clone(sysLogRequestDto);

        return sysLogDao.updateById(sysLog);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysLogByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysLogDao.removeById(idList.get(0));
        }else {
            result =  sysLogDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象系统日志表实现缓存和数据库更新的方法
     * @param  sysLogId
     * @return
     */
    @Override
    public SysLogResponseDto getSysLogById(long sysLogId){
        if(sysLogId < 0 ){
            return null;
        }
        SysLog sysLog =  sysLogDao.getById(sysLogId);
        if(sysLog == null){
            return null;
        }
        SysLogResponseDto sysLogResponseDto = SysLogResponseDto.build().clone(sysLog);

        return sysLogResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象系统日志表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysLogResponseDto getSysLogByOne( SysLogQueryEnum queryEnum,SysLogRequestDto sysLogRequestDto){
          if(sysLogRequestDto == null ){
              return null;
          }
           QueryWrapper<SysLog> queryWrapper = sysLogDao.builderQueryEnum( queryEnum, sysLogRequestDto);
            //分页对象        PageHelper
           Page<SysLog> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysLog>  list = sysLogDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysLog sysLog = list.get(0);
           SysLogResponseDto sysLogResponseDto = SysLogResponseDto.build().clone(sysLog);

            return sysLogResponseDto ;
       }


 /**
   * 通过条件查询SysLog信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:10:19
   */
  @Override
  public List<SysLogResponseDto> getSysLogListByQuery( Object  paramObject, SysLogQueryEnum queryEnum){

      QueryWrapper<SysLog> queryWrapper = sysLogDao.builderQueryEnum( queryEnum, paramObject);

      List<SysLog>  list = sysLogDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysLogResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysLog信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    @Override
    public List<SysLogResponseDto> getSysLogListByPage(Pager page, SysLogQueryEnum queryEnum){

        QueryWrapper<SysLog> queryWrapper =sysLogDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysLog>  list = sysLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysLogResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysLog 系统日志表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    @Override
    public ResponseResultPageVo<SysLogResponseDto> getSysLogByQueryPage(Pager page, SysLogQueryEnum queryEnum){

        ResponseResultPageVo<SysLogResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysLog> queryWrapper = sysLogDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysLog>  list = sysLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysLogResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysLog信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:19
     */
    @Override
    public ResponseResultPageVo<SysLogResponseDto> getSysLogByNextPage(Pager page, SysLogQueryEnum queryEnum){
        ResponseResultPageVo<SysLogResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysLog> queryWrapper = sysLogDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysLog> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysLog>  list = sysLogDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysLogResponseDto>  resDtoList =  IterableConvert.convertList(list,SysLogResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存系统日志表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysLog  setSysLog(){
        SysLog sysLog = new SysLog();
        /**
 			//sysLog.setLogType (int logType);
 			//sysLog.setLogContent (String logContent);
 			//sysLog.setOperateType (int operateType);
 			//sysLog.setUserId (String userId);
 			//sysLog.setUserName (String userName);
 			//sysLog.setIp (String ip);
 			//sysLog.setMethod (String method);
 			//sysLog.setRequestUrl (String requestUrl);
 			//sysLog.setRequestParam (String requestParam);
 			//sysLog.setRequestType (String requestType);
 			//sysLog.setCostTime (long costTime);
 			//sysLog.setCreateBy (String createBy);
 			//sysLog.setCreateTime (Date createTime);
 			//sysLog.setUpdateBy (String updateBy);
 			//sysLog.setUpdateTime (Date updateTime);
		**/

        return sysLog;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysLogDao, SysLog.class,0);
    }


}
