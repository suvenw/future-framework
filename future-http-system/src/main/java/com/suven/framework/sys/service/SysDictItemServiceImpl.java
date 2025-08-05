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


import com.suven.framework.sys.entity.SysDictItem;
import com.suven.framework.sys.dao.SysDictItemDao;
import com.suven.framework.sys.dto.request.SysDictItemRequestDto;
import com.suven.framework.sys.dto.response.SysDictItemResponseDto;
import com.suven.framework.sys.dto.enums.SysDictItemQueryEnum;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.util.excel.ExcelUtils;







/**
 * @ClassName: SysDictItemServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:15
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 RPC业务接口逻辑实现类
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
public class SysDictItemServiceImpl  implements SysDictItemService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDictItemDao  sysDictItemDao;



    /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法
     * @param sysDictItemRequestDto SysDictItemResponseDto
     * @return
     */
    @Override
    public SysDictItemResponseDto saveSysDictItem(SysDictItemRequestDto sysDictItemRequestDto){
        if(sysDictItemRequestDto== null){
            return null;
        }
        SysDictItem sysDictItem = SysDictItem.build().clone(sysDictItemRequestDto);
        boolean result = sysDictItemDao.save(sysDictItem);
        if(!result){
            return null;
        }
        SysDictItemResponseDto sysDictItemResponseDto = SysDictItemResponseDto.build().clone(sysDictItem);
        return sysDictItemResponseDto;


    }

    /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDictItemRequestDto SysDictItemResponseDto
     * @return
     */
    @Override
    public SysDictItemResponseDto saveIdSysDictItem(SysDictItemRequestDto sysDictItemRequestDto){
        if(sysDictItemRequestDto== null){
            return null;
        }
        SysDictItem sysDictItem = SysDictItem.build().clone(sysDictItemRequestDto);
        sysDictItem = sysDictItemDao.saveId(sysDictItem);
        if(null == sysDictItem){
            return null;
        }
        SysDictItemResponseDto sysDictItemResponseDto = SysDictItemResponseDto.build().clone(sysDictItem);
        return sysDictItemResponseDto;


    }
    /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList SysDictItemRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchIdSysDictItem(Collection<SysDictItemRequestDto> entityList) {
        if(null == entityList ){
            return false;
        }
        List<SysDictItem> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDictItem.build().clone(e)));
        boolean result = sysDictItemDao.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存数据字典明细表同时更新数据库和缓存的实现方法
     * @param entityList SysDictItemRequestDto集合
     * @return
     */
    @Override
    public boolean saveBatchSysDictItem(Collection<SysDictItemRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDictItem> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDictItem.build().clone(e)));
        boolean result = sysDictItemDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysDictItem(Collection<SysDictItemRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDictItem> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDictItem.build().clone(e)));
        boolean result = sysDictItemDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    @Override
    public boolean updateBatchById(Collection<SysDictItemRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDictItem> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDictItem.build().clone(e)));
        boolean result =  sysDictItemDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新数据字典明细表同时更新数据库和缓存的实现方法
     * @param sysDictItemRequestDto  SysDictItemResponseDto
     * @return
     */
    @Override
    public boolean updateSysDictItem(SysDictItemRequestDto sysDictItemRequestDto){

          if(null ==  sysDictItemRequestDto){
              return false;
          }

        SysDictItem sysDictItem = SysDictItem.build().clone(sysDictItemRequestDto);

        return sysDictItemDao.updateById(sysDictItem);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysDictItemByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysDictItemDao.removeById(idList.get(0));
        }else {
            result =  sysDictItemDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象数据字典明细表实现缓存和数据库更新的方法
     * @param  sysDictItemId
     * @return
     */
    @Override
    public SysDictItemResponseDto getSysDictItemById(long sysDictItemId){
        if(sysDictItemId < 0 ){
            return null;
        }
        SysDictItem sysDictItem =  sysDictItemDao.getById(sysDictItemId);
        if(sysDictItem == null){
            return null;
        }
        SysDictItemResponseDto sysDictItemResponseDto = SysDictItemResponseDto.build().clone(sysDictItem);

        return sysDictItemResponseDto ;

    }

    /**
     * 通过参数limit0,1获取对象数据字典明细表的查询方法
     * @param  queryEnum
     * @return
     */
     @Override
     public   SysDictItemResponseDto getSysDictItemByOne( SysDictItemQueryEnum queryEnum,SysDictItemRequestDto sysDictItemRequestDto){
          if(sysDictItemRequestDto == null ){
              return null;
          }
           QueryWrapper<SysDictItem> queryWrapper = sysDictItemDao.builderQueryEnum( queryEnum, sysDictItemRequestDto);
            //分页对象        PageHelper
           Page<SysDictItem> iPage = new Page<>(0, 1);
           iPage.setSearchCount(false);
           List<SysDictItem>  list = sysDictItemDao.getListByPage(iPage,queryWrapper);
           if(null == list || list.isEmpty()){
                 return null;
           }
           SysDictItem sysDictItem = list.get(0);
           SysDictItemResponseDto sysDictItemResponseDto = SysDictItemResponseDto.build().clone(sysDictItem);

            return sysDictItemResponseDto ;
       }


 /**
   * 通过条件查询SysDictItem信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param paramObject Object
   * @return
   * @author suven
   * @date 2022-02-28 16:10:15
   */
  @Override
  public List<SysDictItemResponseDto> getSysDictItemListByQuery( Object  paramObject, SysDictItemQueryEnum queryEnum){

      QueryWrapper<SysDictItem> queryWrapper = sysDictItemDao.builderQueryEnum( queryEnum, paramObject);

      List<SysDictItem>  list = sysDictItemDao.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<SysDictItemResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictItemResponseDto.class);
      return resDtoList;

  }


    /**
     * 通过分页获取SysDictItem信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:15
     */
    @Override
    public List<SysDictItemResponseDto> getSysDictItemListByPage(Pager page, SysDictItemQueryEnum queryEnum){

        QueryWrapper<SysDictItem> queryWrapper =sysDictItemDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDictItem> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDictItem>  list = sysDictItemDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictItemResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictItemResponseDto.class);
        return resDtoList;

    }



   /**
     * 通过分页获取SysDictItem 数据字典明细表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:15
     */
    @Override
    public ResponseResultPageVo<SysDictItemResponseDto> getSysDictItemByQueryPage(Pager page, SysDictItemQueryEnum queryEnum){

        ResponseResultPageVo<SysDictItemResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysDictItem> queryWrapper = sysDictItemDao.builderQueryEnum(queryEnum,  page.getParamObject());
        //分页对象        PageHelper
        Page<SysDictItem> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(false);
        List<SysDictItem>  list = sysDictItemDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictItemResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictItemResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList);
        return ResponseResultPageVo;
    }

    /**
     * 通过分页获取SysDictItem信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:15
     */
    @Override
    public ResponseResultPageVo<SysDictItemResponseDto> getSysDictItemByNextPage(Pager page, SysDictItemQueryEnum queryEnum){
        ResponseResultPageVo<SysDictItemResponseDto> ResponseResultPageVo = new ResponseResultPageVo();
        QueryWrapper<SysDictItem> queryWrapper = sysDictItemDao.builderQueryEnum(queryEnum,  page.getParamObject());;
        //分页对象        PageHelper
        Page<SysDictItem> iPage = new Page<>(page.getPageNo(), page.getPageSize());
        iPage.setSearchCount(true);
        List<SysDictItem>  list = sysDictItemDao.getListByPage(iPage,queryWrapper);
        if(null == list ){
            list = new ArrayList<>();
        }
        List<SysDictItemResponseDto>  resDtoList =  IterableConvert.convertList(list,SysDictItemResponseDto.class);
        boolean isNext =  page.isNextPage(resDtoList);
        ResponseResultPageVo.toIsNextPage(isNext).toList(resDtoList).toTotal((int)iPage.getTotal());
        return ResponseResultPageVo;

    }



    /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDictItem  setSysDictItem(){
        SysDictItem sysDictItem = new SysDictItem();
        /**
 			//sysDictItem.setDictId (String dictId);
 			//sysDictItem.setItemText (String itemText);
 			//sysDictItem.setItemValue (String itemValue);
 			//sysDictItem.setDescription (String description);
 			//sysDictItem.setSortOrder (int sortOrder);
 			//sysDictItem.setStatus (int status);
 			//sysDictItem.setCreateBy (String createBy);
 			//sysDictItem.setCreateTime (Date createTime);
 			//sysDictItem.setUpdateBy (String updateBy);
 			//sysDictItem.setUpdateTime (Date updateTime);
		**/

        return sysDictItem;
    }

    @Override
    public boolean saveData(InputStream initialStream) {
        return ExcelUtils.readExcel(initialStream,sysDictItemDao, SysDictItem.class,0);
    }


}
