## serviceImpl 业务接口实现类
package ${packageName}.${moduleName}.service.impl;


${importServicePackage}
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;








/**
 * @ClassName: ${className}ServiceImpl.java
 *
 * @Author 作者 : ${author}
 * @CreateDate 创建时间: ${datetime}
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: ${description} RPC业务接口逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by <a href="https://www.suven.top">suven</a>
 **/

@Service
@Slf4j
public class ${className}ServiceImpl  implements ${className}Service {


    @Autowired
    private ${className}Repository  ${paramName}Repository;



    /**
     * 保存${description}同时更新数据库和缓存的实现方法
     * @param ${paramName}AddRequestVo ${className}AddRequestVo
     * @return  ${className}ResponseVo  返回${className}ResponseVo
     */
    @Override
    public ${className}ResponseVo save${className}(${className}AddRequestVo ${paramName}AddRequestVo){
        if(${paramName}AddRequestVo== null){
            return null;
        }
        ${className} ${paramName} = ${className}.build().clone(${paramName}AddRequestVo);
        boolean result = ${paramName}Repository.save(${paramName});
        if(!result){
            return null;
        }
        ${className}ResponseVo ${paramName}ResponseVo = ${className}ResponseVo.build().clone(${paramName});
        return ${paramName}ResponseVo;


    }

    /**
     * 保存${description}同时更新数据库和缓存的实现方法,自定义初始化id值到对象中
     * @param ${paramName}AddRequestVo ${className}AddRequestVo
     * @return  ${className}ResponseVo  返回${className}ResponseVo
     */
    @Override
    public ${className}ResponseVo saveId${className}(${className}AddRequestVo ${paramName}AddRequestVo){
        if(${paramName}AddRequestVo== null){
            return null;
        }
        ${className} ${paramName} = ${className}.build().clone(${paramName}AddRequestVo);
        ${paramName} = ${paramName}Repository.saveToId(${paramName});
        if(null == ${paramName}){
            return null;
        }
        ${className}ResponseVo ${paramName}ResponseVo = ${className}ResponseVo.build().clone(${paramName});
        return ${paramName}ResponseVo;


    }
    /**
     * 保存${description}同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param entityList ${className}RequestVo集合
     * @return boolean true-成功, false-失败
     */
    @Override
    public boolean saveBatchId${className}(Collection<${className}AddRequestVo> entityList) {
        if(null == entityList ){
            return false;
        }
        List<${className}>  list =  IterableConvert.convertList(entityList,${className}.class);
        boolean result = ${paramName}Repository.saveBatchId(list);
        return result;
    }
    /**
     * 批量保存${description}同时更新数据库和缓存的实现方法
     * @param entityList ${className}AddRequestVo
     * @return boolean true-成功, false-失败
     */
    @Override
    public boolean saveBatch${className}(Collection<${className}AddRequestVo> entityList, int batchSize) {
        if(null == entityList ||batchSize <= 0){
            return false;
        }
        List<${className}>  list =  IterableConvert.convertList(entityList,${className}.class);
        boolean result = ${paramName}Repository.saveBatch(list,batchSize);
        return result;
    }
    /**
     * 更新${description}同时更新数据库和缓存的实现方法
     * @param entityList ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return  boolean true-成功, false-失败
     */
    @Override
    public boolean saveOrUpdateBatch${className}(Collection<${className}AddRequestVo> entityList, int batchSize) {
        if(null == entityList ||batchSize <= 0 ){
            return false;
        }
        List<${className}>  list =  IterableConvert.convertList(entityList,${className}.class);
        boolean result = ${paramName}Repository.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    /**
     * 更新${description}同时更新数据库和缓存的实现方法
     * @param entityList ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return  boolean true-成功, false-失败
     */
    @Override
    public boolean updateBatchById(Collection<${className}AddRequestVo> entityList, int batchSize) {
        if(null == entityList  || batchSize <= 0){
            return false;
        }
        List<${className}>  list =  IterableConvert.convertList(entityList,${className}.class);
        boolean result =  ${paramName}Repository.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新${description}同时更新数据库和缓存的实现方法
     * @param ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return boolean true-成功, false-失败
     */
    @Override
    public boolean update${className}(${className}AddRequestVo ${paramName}AddRequestVo){

          if(null ==  ${paramName}AddRequestVo){
              return false;
          }

        ${className} ${paramName} = ${className}.build().clone(${paramName}AddRequestVo);

        return ${paramName}Repository.updateById(${paramName});


    }





 /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  id 业务表对象 id
     * @return 删除数量
     */
    @Override
    public int del${className}ByIds(long id){
         boolean result =  ${paramName}Repository.removeById(id);
         if(result){
             return ResultEnum.SUCCESS.id();
         }
         return ResultEnum.FAIL.id();
    }

    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList 列表
     * @return 删除数量
     */
    @Override
    public int del${className}ByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = ${paramName}Repository.removeById(idList.get(0));
        }else {
            result =  ${paramName}Repository.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象${description}实现缓存和数据库更新的方法
     * @param  ${paramName}Id 业务表对象 id
     * @return ${className}ResponseVo 业务对象
     */
    @Override
    public ${className}ResponseVo get${className}ById(long ${paramName}Id){
        if(${paramName}Id < 0 ){
            return null;
        }
        ${className} ${paramName} =  ${paramName}Repository.getById(${paramName}Id);
        if(${paramName} == null){
            return null;
        }
        ${className}ResponseVo ${paramName}ResponseVo = ${className}ResponseVo.build().clone(${paramName});

        return ${paramName}ResponseVo ;

    }

    /**
     * 通过参数limit0,1获取对象${description}的查询方法
     * @param  queryEnum ${className}QueryEnum
     * @return ${className}ResponseVo 业务对象
     */
     @Override
     public   ${className}ResponseVo get${className}ByOne( ${className}RequestVo ${paramName}RequestVo, ${className}QueryEnum queryEnum){
          if(${paramName}RequestVo == null ){
              return null;
          }
         Wrapper<${className}> queryWrapper = ${paramName}Repository.builderQueryEnum( queryEnum, ${paramName}RequestVo);

         Page<${className}> page = new Page<>(0, 1);
         page.setSearchCount(false);

         List<${className}>  list = ${paramName}Repository.getListByPage(page,queryWrapper);
           if(null ==  list || list.isEmpty()){
                 return null;
           }
         ${className} ${paramName} = list.get(0);
         return ${className}ResponseVo.build().clone( ${paramName});

       }


 /**
   * 通过条件查询${className}信息列表,实现查找缓存和数据库的方法,但不统计总页数
   * @param  ${paramName}RequestVo ${className}RequestVo
   * @return List<${className}ResponseVo> 分页列表集合对象
   * @author ${author}
   * @date ${datetime}
   */
  @Override
  public List<${className}ResponseVo> get${className}ListByQuery( ${className}RequestVo  ${paramName}RequestVo, ${className}QueryEnum queryEnum){

      Wrapper<${className}> queryWrapper = ${paramName}Repository.builderQueryEnum( queryEnum, ${paramName}RequestVo);

      List<${className}>  list = ${paramName}Repository.getListByQuery(queryWrapper);
      if(null == list ){
          list = new ArrayList<>();
      }
      List<${className}ResponseVo>  resDtoList =  IterableConvert.convertList(list,${className}ResponseVo.class);
      return resDtoList;

  }


    /**
     * 通过分页获取${className}信息列表,实现查找缓存和数据库的方法,但不统计总页数
     * @param pager BasePage
     * @return List<${className}ResponseVo> 分页列表集合对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    @Override
    public List<${className}ResponseVo> get${className}ListByPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum){

        Wrapper<${className}> queryWrapper = ${paramName}Repository.builderQueryEnum(queryEnum,  pager.getParamObject());
        //分页对象        PageHelper
        Pager<${className}>  page =  new Pager<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(false);

        PageResult<${className}>  pageResult = ${paramName}Repository.getListByPage(page,queryWrapper);
        if(ObjectTrue.isEmpty(pageResult) || ObjectTrue.isEmpty(pageResult.getList())){
            return new ArrayList<>();
        }
        List<${className}ResponseVo>  resDtoList =  IterableConvert.convertList(pageResult.getList(),${className}ResponseVo.class);
        return resDtoList;

    }



   /**
     * 通过分页获取${className} ${description}信息实现查找缓存和数据库的方法,不查总页数
     * @param pager BasePage
     * @return PageResult<${className}ResponseVo> 分页列表集合对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    @Override
    public PageResult<${className}ResponseVo> get${className}ByQueryPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum){

        Wrapper<${className}> queryWrapper = ${paramName}Repository.builderQueryEnum(queryEnum,  pager.getParamObject());;
        //分页对象        PageHelper
        Pager<${className}>  page =  new Pager<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(false);

        PageResult<${className}>  pageResult = ${paramName}Repository.getListByPage(page,queryWrapper);
        if(ObjectTrue.isEmpty(pageResult) || ObjectTrue.isEmpty(pageResult.getList())){
            return  new PageResult<>();
        }
        PageResult<${className}ResponseVo> pageResultVo = pageResult.convertBuild(${className}ResponseVo.class);
        return pageResultVo;
    }

    /**
     * 通过分页获取${className}信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param pager BasePage
     * @return PageResult<${className}ResponseVo> 分页列表集合对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    @Override
    public PageResult<${className}ResponseVo> get${className}ByNextPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum){
        Wrapper<${className}> queryWrapper = ${paramName}Repository.builderQueryEnum(queryEnum,  pager.getParamObject());;
        //分页对象        PageHelper
        Pager<${className}>  page =  new Pager<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(true);

        PageResult<${className}>  pageResult = ${paramName}Repository.getListByPage(page,queryWrapper);
        if(ObjectTrue.isEmpty(pageResult) || ObjectTrue.isEmpty(pageResult.getList())){
            return  new PageResult<>();
        }
        PageResult<${className}ResponseVo> pageResultVo = pageResult.convertBuild(${className}ResponseVo.class);

        return pageResultVo;

    }




}
