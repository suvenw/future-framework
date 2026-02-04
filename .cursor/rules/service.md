## service 接口类
package ${packageName}.${moduleName}.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import com.baomidou.mybatisplus.extension.service.IService;





/**
 * @ClassName: ${className}Service.java
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


public interface ${className}Service {



    /**
     * 保存${description}更新数据库和缓存的实现方法
     * @param ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return   ${className}ResponseVo  返回${className}ResponseVo
     */
    ${className}ResponseVo save${className}(${className}AddRequestVo ${paramName}AddRequestVo);


     /**
     * 保存${description}同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return   ${className}ResponseVo  返回${className}ResponseVo
     */
    ${className}ResponseVo saveId${className}(${className}AddRequestVo ${paramName}AddRequestVo);


     /**
     * 保存${description}同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return  boolean true-成功, false-失败
     */
    boolean saveBatchId${className}(Collection<${className}AddRequestVo> entityList);

    /**
     * 保存${description}更新数据库和缓存的实现方法
     * @return  boolean true-成功, false-失败
     */
    boolean saveBatch${className}(Collection<${className}AddRequestVo> entityList, int batchSize);

    /**
     * 保存${description}更新数据库和缓存的实现方法
     * @return  boolean true-成功, false-失败
     */
    boolean saveOrUpdateBatch${className}(Collection<${className}AddRequestVo> entityList, int batchSize);



    /**
     * 更新${description}同时更新数据库和缓存的实现方法
     * @param ${paramName}AddRequestVo  ${className}AddRequestVo
     * @return  boolean true-成功, false-失败
     */
    boolean update${className} (${className}AddRequestVo ${paramName}AddRequestVo);

    /**
     * 更新${description}同时更新数据库和缓存的实现方法
     * @return  boolean true-成功, false-失败
     */
    boolean updateBatchById(Collection<${className}AddRequestVo> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  id 业务 id
     * @return int 大于0 则代表删除成功
     */
    int del${className}ByIds(long id);

    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  ${paramName}Ids  List<Long> 业务ID列表
     * @return int 大于0 则代表删除成功
     */
    int del${className}ByIds(List<Long>  ${paramName}Ids);


    /**
     * 通过主键ID更新对象${description}实现缓存和数据库更新的方法
     * @param  ${paramName}Id 业务 id
     * @return ${className}ResponseVo  返回${className}ResponseVo
     */
        ${className}ResponseVo get${className}ById(long ${paramName}Id);

    /**
     * 通过参数limit0,1获取对象${description}的查询方法
     * @param  queryEnum ${className}QueryEnum 查询条件枚举
     * @return ${className}ResponseVo  返回${className}ResponseVo
     */
    ${className}ResponseVo get${className}ByOne(${className}RequestVo ${paramName}RequestVo, ${className}QueryEnum queryEnum);


    /**
    * 通过分页和枚举条件获取${className}信息实现查找缓存和数据库的方法
    * @param ${paramName}RequestVo ${className}RequestVo
    * @return List<${className}ResponseVo> 列表集合对象
    * @author ${author}
    * @date ${datetime}
    */
    List<${className}ResponseVo> get${className}ListByQuery(${className}RequestVo  ${paramName}RequestVo, ${className}QueryEnum queryEnum);


    /**
     * 通过分页获取${className}信息实现查找缓存和数据库的方法
     * @param pager Pager<${className}RequestVo>
     * @return List<${className}ResponseVo> 列表集合对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    List<${className}ResponseVo> get${className}ListByPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum);




    /**
     * 通过分页获取${className} ${description}信息实现查找缓存和数据库的方法,包括查总页数
     * @param pager Pager<${className}RequestVo>
     * @return PageResult<${className}ResponseVo>  返回分页信息对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    PageResult<${className}ResponseVo> get${className}ByNextPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum);

    /**
     * 通过分页获取${className} ${description}信息实现查找缓存和数据库的方法,不查总页数
     * @param pager Pager<${className}RequestVo>
     * @return PageResult<${className}ResponseVo>  返回分页信息对象
     * @author ${author}  作者
     * date ${datetime} 创建时间
     */
    PageResult<${className}ResponseVo> get${className}ByQueryPage(Pager<${className}RequestVo> pager,${className}QueryEnum queryEnum);



}