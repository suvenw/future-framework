package com.suven.framework.sys.service;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.inters.IResultCodeEnum;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.InputStream;


import com.suven.framework.sys.entity.SysRolePermission;
import com.suven.framework.sys.dao.SysRolePermissionDao;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysRolePermissionQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysRolePermissionServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:49
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 RPC业务接口逻辑实现类
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
public class SysRolePermissionServiceImpl  implements SysRolePermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRolePermissionDao  sysRolePermissionDao;



    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto SysRolePermissionResponseDto
     * @return
     */
    @Override
    public SysRolePermissionResponseDto saveSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto){
        if(sysRolePermissionRequestDto== null){
            return null;
        }
        SysRolePermission sysRolePermission = SysRolePermission.build().clone(sysRolePermissionRequestDto);
        boolean result = sysRolePermissionDao.save(sysRolePermission);
        if(!result){
            return null;
        }
        SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);
        return sysRolePermissionResponseDto;


    }

    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysRolePermissionRequestDto SysRolePermissionResponseDto
     * @return
     */
    @Override
    public SysRolePermissionResponseDto saveIdSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto){
        if(sysRolePermissionRequestDto== null){
            return null;
        }
        SysRolePermission sysRolePermission = SysRolePermission.build().clone(sysRolePermissionRequestDto);
        sysRolePermission = sysRolePermissionDao.saveId(sysRolePermission);
        if(null == sysRolePermission){
            return null;
        }
        SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);
        return sysRolePermissionResponseDto;


    }
    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysRolePermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysRolePermission(Collection<SysRolePermissionRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result = sysRolePermissionDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存角色权限表同时更新数据库和缓存的实现方法
     * @param entityList SysRolePermissionRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result = sysRolePermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result = sysRolePermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result =  sysRolePermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto  SysRolePermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto){

          if(null ==  sysRolePermissionRequestDto){
              return false;
          }

        SysRolePermission sysRolePermission = SysRolePermission.build().clone(sysRolePermissionRequestDto);

        return sysRolePermissionDao.updateById(sysRolePermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysRolePermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysRolePermissionDao.removeById(idList.get(0));
        }else {
            result =  sysRolePermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象角色权限表实现缓存和数据库更新的方法
     * @param  sysRolePermissionId
     * @return
     */
    @Override
    public SysRolePermissionResponseDto getSysRolePermissionById(long sysRolePermissionId){
        if(sysRolePermissionId < 0 ){
            return null;
        }
        SysRolePermission sysRolePermission =  sysRolePermissionDao.getById(sysRolePermissionId);
        if(sysRolePermission == null){
            return null;
        }
        SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);

        return sysRolePermissionResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象角色权限表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysRolePermissionResponseDto getSysRolePermissionByOne( SysRolePermissionQueryEnum queryEnum,SysRolePermissionRequestDto sysRolePermissionRequestDto){
          if(sysRolePermissionRequestDto == null ){
              return null;
          }
           QueryWrapper<SysRolePermission> queryWrapper = sysRolePermissionDao.builderQueryEnum( queryEnum, sysRolePermissionRequestDto);
            //分页对象        PageHelper
           Page<SysRolePermission> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysRolePermission>  list = sysRolePermissionDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysRolePermission sysRolePermission = list.get(0);
           SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);

            return sysRolePermissionResponseDto ;
       }


 /**
   * 通过条件查询SysRolePermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:10:49
   */
  @Override
  public List<SysRolePermissionResponseDto> getSysRolePermissionListByQuery( Object  paramObject, SysRolePermissionQueryEnum queryEnum){

      QueryWrapper<SysRolePermission> queryWrapper = sysRolePermissionDao.builderQueryEnum( queryEnum, paramObject);

      List<SysRolePermission>  list = sysRolePermissionDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRolePermissionResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysRolePermission信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    @Override
    public List<SysRolePermissionResponseDto> getSysRolePermissionListByPage(Pager page, SysRolePermissionQueryEnum queryEnum){

        QueryWrapper<SysRolePermission> queryWrapper =sysRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysRolePermission>  list = sysRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRolePermissionResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysRolePermission 角色权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    @Override
    public ResponseResultPageVo<SysRolePermissionResponseDto> getSysRolePermissionByQueryPage(Pager page, SysRolePermissionQueryEnum queryEnum){

        ResponseResultPageVo<SysRolePermissionResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysRolePermission> queryWrapper = sysRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysRolePermission>  list = sysRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRolePermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysRolePermission信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     */
    @Override
    public ResponseResultPageVo<SysRolePermissionResponseDto> getSysRolePermissionByNextPage(Pager page, SysRolePermissionQueryEnum queryEnum){
        ResponseResultPageVo<SysRolePermissionResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysRolePermission> queryWrapper = sysRolePermissionDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysRolePermission> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysRolePermission>  list = sysRolePermissionDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysRolePermissionResponseDto>  resDtoList =  IterableConvert.convertList(list,SysRolePermissionResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysRolePermission  setSysRolePermission(){
        SysRolePermission sysRolePermission = new SysRolePermission();
        /**
 			//sysRolePermission.setRoleId (long roleId);
 			//sysRolePermission.setPermissionId (long permissionId);
 			//sysRolePermission.setDataRuleIds (String dataRuleIds);
 			//sysRolePermission.setOperateIp (String operateIp);
 			//sysRolePermission.setCreateBy (String createBy);
 			//sysRolePermission.setCreateTime (Date createTime);
 			//sysRolePermission.setUpdateBy (String updateBy);
 			//sysRolePermission.setUpdateTime (Date updateTime);
		**/

        return sysRolePermission;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysRolePermissionDao, SysRolePermission.class,0);
    }



    @Override
    public IResultCodeEnum saveRolePermission(long roleId, List<Long> permissionIds, List<Long> lastPermissionIds) {
        List<Long> add = getDiff(lastPermissionIds,permissionIds);
        if(add!=null && add.size()>0) {
            List<SysRolePermission> list = new ArrayList<>();
            for (long p : add) {
                if(p>0) {
                    SysRolePermission rolepms = SysRolePermission.build().toRoleId(roleId).toPermissionId(p);
                    list.add(rolepms);
                }
            }
            sysRolePermissionDao.saveBatch(list,list.size());
        }

        List<Long> delete = getDiff(permissionIds,lastPermissionIds);
        if(delete!=null && delete.size()>0) {
            for (long permissionId : delete) {
                sysRolePermissionDao.remove(new QueryWrapper<SysRolePermission>().eq("role_id", roleId).eq("permission_id", permissionId));
            }
        }
        return SysResultCodeEnum.SYS_SUCCESS;
    }

    @Override
    public List<SysRolePermissionResponseDto> queryRolePermissionByRoleId(long roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysRolePermission> list = sysRolePermissionDao.list(queryWrapper);
        List<SysRolePermissionResponseDto>  resDtoList = new ArrayList<>();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysRolePermission -> {
            SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);
            resDtoList.add(sysRolePermissionResponseDto);
        });
        return resDtoList;
    }

    /**
     * 从diff中找出main中没有的元素
     * @param main
     * @param diff
     * @return
     */
    private List<Long> getDiff(List<Long> main,List<Long> diff){

        if(ObjectTrue.isEmpty(diff)) {
            return null;
        }
        Map<Long, Integer> map = new HashMap<>();
        if(!ObjectTrue.isEmpty(main)) {
            for (long string : main) {
                map.put(string, 1);
            }
        }

        List<Long> res = new ArrayList<>();
        for (long key : diff) {
            if(key > 0 && !map.containsKey(key)) {
                res.add(key);
            }
        }
        return res;
    }

}
