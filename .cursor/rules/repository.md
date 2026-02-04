## 数据层查询接口类
package ${packageName}.${moduleName}.repository;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;






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

public interface  ${className}Repository extends IService<${className}>{





    /**
     * 保存创建${className},并且保存到缓存中
     * @param ${paramName} 表对象
     * @author ${author} 作者
     * date ${datetime} 创建时间
     */

    public ${className} saveId(${className} ${paramName});



    /**
     * 批量保存创建${className},并且保存到缓存中
     * @param ${paramName}List 批量保存表对象列表
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public boolean saveBatchId(List<${className}> ${paramName}List);


    /**
     * 通过分页获取${className}信息实现查找缓存和数据库的方法
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public PageResult<${className}> getListByPage(Pager<${className}> pager, Wrapper<${className}> queryWrapper );

    /**
    * 通过分页获取SysDataLog信息实现查找缓存和数据库的方法
    * @param queryWrapper BasePage
    * @return  ${className}List 对象列表
    * @author suven
    * date 2022-02-28 16:10:02
    */
    public List<${className}> getListByPage(IPage<${className}> iPage, Wrapper<${className}> queryWrapper );

    /**
     * 通过分页获取${className}信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public List<${className}> getListByQuery( Wrapper<${className}> queryWrapper );


    /**
     * 通过枚举实现${className}不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return 返架查询条件对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    public Wrapper<${className}> builderQueryEnum(${className}QueryEnum queryEnum,  Object queryObject);
}