## 数据查询层接口实现类
package ${packageName}.${moduleName}.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;




/**
 * @author 作者 : ${author}
 * @version 版本: v1.0.0
 *  date 创建时间: ${datetime}
 * <pre>
 *
 *  Description: ${description} 的数据库查询逻辑实现类
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

@Repository("${classname}Repository")
@Slf4j
public class ${className}RepositoryImpl extends AbstractMyBatisRepository<${className}Mapper, ${className}> implements ${className}Repository{





    /**
     * 保存创建${className},并且保存到缓存中
     * @param ${paramName} 表对象
     * @author ${author} 作者
     * date ${datetime} 创建时间
     */

    public ${className} saveId(${className} ${paramName}){
        if(null == ${paramName}){
            return  null;
        }
       if ( ${paramName}.getId() == null  ){
          boolean success = this.save(address);
          if(success){
            return address;
          }
       }else {
          long id = this.getBaseMapper().saveToId(${paramName});
         if (returnResult(id)){
             return ${paramName};
         }
       }
        return null;
    }


    /**
     * 保存创建${className},并且保存到缓存中
     * @param ${paramName} 表参数对象
     * @author ${author} 作者
     * date ${datetime} 创建时间
     */

        public ${className} saveToId(${className} ${paramName}){
        if(null == ${paramName}){
            return  null;
        }

        long id = this.getBaseMapper().saveToId(${paramName});
        if (returnResult(id)){
            return ${paramName};
        }
        return null;


    }

    /**
     * 批量保存创建${className},并且保存到缓存中
     * @param ${paramName}List 批量保存表对象列表
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public boolean saveBatchId(List<${className}> ${paramName}List){
        if(null == ${paramName}List){
            return  false;
        }
        long id =  this.getBaseMapper().saveBatch(${paramName}List);
        return returnResult(id);

    }



    /**
     * 通过分页获取${className}信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public PageResult<${className}> getListByPage(Pager<${className}> pager, Wrapper<${className}> queryWrapper ){

        PageResult<${className}> pageVo = new PageResult<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        Page<${className}> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<${className}> page = super.page(iPage, queryWrapper);
        if(ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())){
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
    * 通过分页获取SysDataLog信息实现查找缓存和数据库的方法
    * @param queryWrapper BasePage
    * @return
    * @author suven
    * date 2022-02-28 16:10:02
    */
    public List<${className}> getListByPage(IPage<${className}> iPage, Wrapper<${className}> queryWrapper ){

        List<${className}> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }

        IPage<${className}> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<${className}>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        return list;
    }
    /**
     * 通过分页获取${className}信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public List<${className}> getListByQuery( Wrapper<${className}> queryWrapper ){
        List<${className}> resDtoList = new ArrayList<>();
        if(ObjectTrue.isEmpty(queryWrapper)){
            queryWrapper = new QueryWrapper<>();
        }
        List<${className}> list = super.list(queryWrapper);
        if(ObjectTrue.isEmpty(list)){
            return resDtoList;
        }
        return list;
    }



    /**
     * 通过枚举实现${className}不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public QueryWrapper<${className}> builderQueryEnum(${className}QueryEnum queryEnum,  Object queryObject){
        QueryWrapper<${className}> queryWrapper = new QueryWrapper<>();
        if(ObjectTrue.isEmpty(queryEnum)){
            AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if(ObjectTrue.isEmpty(queryObject)){
            AssertEx.error( new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        ${className}  ${paramName} = ${className}.build().clone(queryObject);
        switch (queryEnum){
            case DESC_ID :{
                queryWrapper.orderByDesc("id");
                break;
            }
            default:
                break;
        }
        return queryWrapper;
    }
}