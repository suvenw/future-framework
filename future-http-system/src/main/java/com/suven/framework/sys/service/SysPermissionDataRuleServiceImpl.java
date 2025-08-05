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


import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.suven.framework.sys.dao.SysPermissionDataRuleDao;
import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * ClassName: SysPermissionDataRuleServiceImpl.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:35
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 RPC业务接口逻辑实现类
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
public class SysPermissionDataRuleServiceImpl  implements SysPermissionDataRuleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPermissionDataRuleDao  sysPermissionDataRuleDao;



    /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto SysPermissionDataRuleResponseDto
     * @return
     */
    @Override
    public SysPermissionDataRuleResponseDto saveSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){
        if(sysPermissionDataRuleRequestDto== null){
            return null;
        }
        SysPermissionDataRule sysPermissionDataRule = SysPermissionDataRule.build().clone(sysPermissionDataRuleRequestDto);
        boolean result = sysPermissionDataRuleDao.save(sysPermissionDataRule);
        if(!result){
            return null;
        }
        SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);
        return sysPermissionDataRuleResponseDto;


    }

    /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysPermissionDataRuleRequestDto SysPermissionDataRuleResponseDto
     * @return
     */
    @Override
    public SysPermissionDataRuleResponseDto saveIdSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){
        if(sysPermissionDataRuleRequestDto== null){
            return null;
        }
        SysPermissionDataRule sysPermissionDataRule = SysPermissionDataRule.build().clone(sysPermissionDataRuleRequestDto);
        sysPermissionDataRule = sysPermissionDataRuleDao.saveId(sysPermissionDataRule);
        if(null == sysPermissionDataRule){
            return null;
        }
        SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);
        return sysPermissionDataRuleResponseDto;


    }
    /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysPermissionDataRuleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result = sysPermissionDataRuleDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存菜单权限规则表同时更新数据库和缓存的实现方法
     * @param entityList SysPermissionDataRuleRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result = sysPermissionDataRuleDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result = sysPermissionDataRuleDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result =  sysPermissionDataRuleDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新菜单权限规则表同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleResponseDto
     * @return
     */
    @Override
    public boolean updateSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){

          if(null ==  sysPermissionDataRuleRequestDto){
              return false;
          }

        SysPermissionDataRule sysPermissionDataRule = SysPermissionDataRule.build().clone(sysPermissionDataRuleRequestDto);

        return sysPermissionDataRuleDao.updateById(sysPermissionDataRule);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysPermissionDataRuleByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysPermissionDataRuleDao.removeById(idList.get(0));
        }else {
            result =  sysPermissionDataRuleDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象菜单权限规则表实现缓存和数据库更新的方法
     * @param  sysPermissionDataRuleId
     * @return
     */
    @Override
    public SysPermissionDataRuleResponseDto getSysPermissionDataRuleById(long sysPermissionDataRuleId){
        if(sysPermissionDataRuleId < 0 ){
            return null;
        }
        SysPermissionDataRule sysPermissionDataRule =  sysPermissionDataRuleDao.getById(sysPermissionDataRuleId);
        if(sysPermissionDataRule == null){
            return null;
        }
        SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);

        return sysPermissionDataRuleResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象菜单权限规则表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysPermissionDataRuleResponseDto getSysPermissionDataRuleByOne( SysPermissionDataRuleQueryEnum queryEnum,SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){
          if(sysPermissionDataRuleRequestDto == null ){
              return null;
          }
           QueryWrapper<SysPermissionDataRule> queryWrapper = sysPermissionDataRuleDao.builderQueryEnum( queryEnum, sysPermissionDataRuleRequestDto);
            //分页对象        PageHelper
           Page<SysPermissionDataRule> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysPermissionDataRule>  list = sysPermissionDataRuleDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysPermissionDataRule sysPermissionDataRule = list.get(0);
           SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);

            return sysPermissionDataRuleResponseDto ;
       }


 /**
   * 通过条件查询SysPermissionDataRule信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * date 2022-02-28 16:10:35
   */
  @Override
  public List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleListByQuery( Object  paramObject, SysPermissionDataRuleQueryEnum queryEnum){

      QueryWrapper<SysPermissionDataRule> queryWrapper = sysPermissionDataRuleDao.builderQueryEnum( queryEnum, paramObject);

      List<SysPermissionDataRule>  list = sysPermissionDataRuleDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysPermissionDataRuleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionDataRuleResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysPermissionDataRule信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:35
     */
    @Override
    public List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleListByPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum){

        QueryWrapper<SysPermissionDataRule> queryWrapper =sysPermissionDataRuleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPermissionDataRule> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPermissionDataRule>  list = sysPermissionDataRuleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionDataRuleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionDataRuleResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysPermissionDataRule 菜单权限规则表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:35
     */
    @Override
    public ResponseResultPageVo<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByQueryPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum){

        ResponseResultPageVo<SysPermissionDataRuleResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysPermissionDataRule> queryWrapper = sysPermissionDataRuleDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysPermissionDataRule> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysPermissionDataRule>  list = sysPermissionDataRuleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionDataRuleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionDataRuleResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysPermissionDataRule信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:35
     */
    @Override
    public ResponseResultPageVo<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByNextPage(Pager page, SysPermissionDataRuleQueryEnum queryEnum){
        ResponseResultPageVo<SysPermissionDataRuleResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysPermissionDataRule> queryWrapper = sysPermissionDataRuleDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysPermissionDataRule> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysPermissionDataRule>  list = sysPermissionDataRuleDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysPermissionDataRuleResponseDto>  resDtoList =  IterableConvert.convertList(list,SysPermissionDataRuleResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存菜单权限规则表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysPermissionDataRule  setSysPermissionDataRule(){
        SysPermissionDataRule sysPermissionDataRule = new SysPermissionDataRule();
        /**
 			//sysPermissionDataRule.setPermissionId (long permissionId);
 			//sysPermissionDataRule.setRuleName (String ruleName);
 			//sysPermissionDataRule.setRuleColumn (String ruleColumn);
 			//sysPermissionDataRule.setRuleConditions (String ruleConditions);
 			//sysPermissionDataRule.setRuleValue (String ruleValue);
 			//sysPermissionDataRule.setStatus (int status);
 			//sysPermissionDataRule.setCreateTime (Date createTime);
 			//sysPermissionDataRule.setCreateBy (String createBy);
 			//sysPermissionDataRule.setUpdateTime (Date updateTime);
 			//sysPermissionDataRule.setUpdateBy (String updateBy);
		**/

        return sysPermissionDataRule;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysPermissionDataRuleDao, SysPermissionDataRule.class,0);
    }


}
