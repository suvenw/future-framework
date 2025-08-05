/*
 * Copyright (c) 2011-2023, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.suven.framework.common.cat.CatDBSign;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.DataSourceGroup;
import com.suven.framework.core.db.DataSourceHolder;
import com.suven.framework.core.db.DataSourceTypeEnum;
import com.suven.framework.core.db.ext.DSClassAnnoExplain;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ）
 *
 * @author hubin
 * @since 2018-06-23
 */
@SuppressWarnings("unchecked")
public abstract class AbstractMyBatisDao<M extends BaseMapper<T>, T extends IBaseApi>extends ServiceImpl<M,T> implements IService<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final int BATCH_SIZE  = 1000;
    @Override
    public Class<T> getEntityClass() {
        return currentModelClass();
    }

    public Class<M> getMapperClass() {
        return currentMapperClass();
    }

    public M getSlaveMapper() {
        slaveDataSource();
        return this.baseMapper;
    }
    @Override
    public M getBaseMapper() {
        masterDataSource();
        return this.baseMapper;
    }

    protected  void masterDataSource(){
        Class<T> entityClass =  getEntityClass();
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(entityClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.MASTER);
        logger.info(" masterDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }

    protected   void slaveDataSource(){
        Class<T> entityClass =  getEntityClass();
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(entityClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.SLAVE);
        logger.info(" slaveDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }


    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数，可能为 null
     * @return boolean，成功返回 true，失败返回 false
     * @deprecated 3.3.1
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean returnBool(Long result) {
        // 默认 null 视为操作失败
        return SqlUtil.retBool(result);
    }


    @CatDBSign
    @Override
    public boolean save(T entity) {
        this.masterDataSource();
        if(Objects.isNull(entity.getId())){
            entity.initId();
        }
        return super.save(entity);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @CatDBSign
    @Override
    public boolean saveBatch(Collection<T> entityList) {
        this.masterDataSource();
        return super.saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }




    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    @CatDBSign
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        this.masterDataSource();
        return super.saveBatch(entityList,batchSize);
    }

    /**
     * 获取mapperStatementId
     *
     * @param sqlMethod 方法名
     * @return 命名id
     * @since 3.4.0
     */
    protected String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(this.currentMapperClass(), sqlMethod);
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @CatDBSign
    @Override
    public boolean saveOrUpdate(T entity) {
        this.masterDataSource();
        if (null != entity) {
            super.saveOrUpdate(entity);
        }
        return false;
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    @CatDBSign
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        this.masterDataSource();
        return super.saveOrUpdateBatch(entityList,batchSize);
    }
    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    @CatDBSign
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        this.masterDataSource();
        if (Objects.isNull(entityList)){
            return false;
        }
       return super.updateBatchById(entityList,batchSize);
    }

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @return {@link Optional} 返回一个Optional对象
     */

    @CatDBSign
    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        //增加保护，免于过量数据查询
        if (queryWrapper instanceof QueryWrapper){
            ((QueryWrapper)queryWrapper).last("LIMIT 2");
        }
        if (throwEx) {
            return this.getSlaveMapper().selectOne(queryWrapper);
        }
        return SqlHelper.getObject(log, this.getSlaveMapper().selectList(queryWrapper));
    }

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @return {@link Optional} 返回一个Optional对象
     */
    @CatDBSign
    @Override
    public Optional<T> getOneOpt(Wrapper<T> queryWrapper, boolean throwEx) {
       T value =  this.getOne(queryWrapper,throwEx);
        return Optional.ofNullable(value);
    }

    @CatDBSign
    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(log, this.getSlaveMapper().selectMaps(queryWrapper));
    }
    @CatDBSign
    @Override
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return this.getSlaveMapper().selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }
    @CatDBSign
    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }



    @CatDBSign
    @Override
    public boolean removeById(Serializable id) {
        this.masterDataSource();
       return super.removeById(id);
    }

    @CatDBSign
    @Override
    public boolean removeByIds(Collection<?> list) {
        if (Objects.isNull(list)) {
            return false;
        }
        this.masterDataSource();
        return super.removeByIds(list);
    }

    @CatDBSign
    @Override
    public boolean removeById(Serializable id, boolean useFill) {
        this.masterDataSource();
        return super.removeById(id,useFill);

    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, int batchSize) {
        this.masterDataSource();
        return super.removeBatchByIds(list, batchSize);
    }

    @CatDBSign
    @Override
    public boolean removeBatchByIds(Collection<?> list, int batchSize, boolean useFill) {
        this.masterDataSource();
        return super.removeBatchByIds(list,batchSize,useFill);
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */

    @CatDBSign
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        this.masterDataSource();
        return super.saveOrUpdateBatch(entityList);
    }

    /**
     * 根据实体(ID)删除
     *
     * @param entity 实体
     * @since 3.4.4
     */
    @CatDBSign
    @Override
    public boolean removeById(T entity) {
        return super.removeById(entity);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    @CatDBSign
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public boolean remove(Wrapper<T> queryWrapper) {
        return super.remove(queryWrapper);
    }

    /**
     * 批量删除
     *
     * @param list    主键ID或实体列表
     * @param useFill 是否填充(为true的情况,会将入参转换实体进行delete删除)
     * @return 删除结果
     * @since 3.5.0
     */
    @Override
    @CatDBSign
    public boolean removeByIds(Collection<?> list, boolean useFill) {
        this.masterDataSource();
        return super.removeByIds(list, useFill);
    }

    /**
     * 批量删除(jdbc批量提交)
     *
     * @param list 主键ID或实体列表(主键ID类型必须与实体类型字段保持一致)
     * @return 删除结果
     * @since 3.5.0
     */
    @Override
    @CatDBSign
    public boolean removeBatchByIds(Collection<?> list) {
        this.masterDataSource();
        return super.removeBatchByIds(list);
    }

    /**
     * 批量删除(jdbc批量提交)
     *
     * @param list    主键ID或实体列表(主键ID类型必须与实体类型字段保持一致)
     * @param useFill 是否启用填充(为true的情况,会将入参转换实体进行delete删除)
     * @return 删除结果
     * @since 3.5.0
     */
    @Override
    @CatDBSign
    public boolean removeBatchByIds(Collection<?> list, boolean useFill) {
        this.masterDataSource();
        return super.removeBatchByIds(list, useFill);
    }

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    @Override
    @CatDBSign
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param updateWrapper 实体对象封装操作类 {@link UpdateWrapper}
     */
    @Override
    @CatDBSign
    public boolean update(Wrapper<T> updateWrapper) {
        return super.update(updateWrapper);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link UpdateWrapper}
     */
    @Override
    @CatDBSign
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Override
    @CatDBSign
    public boolean updateBatchById(Collection<T> entityList) {
        this.masterDataSource();
        return super.updateBatchById(entityList);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
//    @Override
    @CatDBSign
    public T getById(Long id) {
        return this.getSlaveMapper().selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return 返回主键与 对象 T 的 map<id,T> 信息聚合
     */
    @CatDBSign
    public Map<Serializable,T> getMapByIds(List<Serializable> idList) {
        Map<Serializable,T>  map = new LinkedHashMap<>();
        List<T> list = getListByIds(idList);
        if (ObjectTrue.isEmpty(list)){
            return map;
        }
        list.forEach(api -> map.put(api.getId(),api) );
        return map;
    }

    /**
     * 查询（根据ID 批量查询），当 idList 数量大于 1000时，会执行分批操作再把结果汇总返回 List
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    @CatDBSign
    public List<T> getListByIds(List<? extends Serializable> idList) {

        List<T> list = new ArrayList<>();
        /** 如果批量id 少于指定值时100条,直接查询**/
        if(idList.size() < BATCH_SIZE && !idList.isEmpty()){
            list = super.listByIds(idList);
            return list;
        }
        /*** 如果大于100条,则查用分页查询;返回结果值; */
        List<? extends List<? extends Serializable>> partition = Lists.partition(idList, BATCH_SIZE);
        if(ObjectTrue.isEmpty(partition) ){
            return list;
        }
        for(List<? extends Serializable> ids : partition ){
            List<T> dbList =  list = super.listByIds(ids);
            if(ObjectTrue.isNotEmpty(dbList)){
                list.addAll(dbList);
            }
        }
        return list;

    }

    /**
     * 根据 ID 查询，返回一个Option对象
     *
     * @param id 主键ID
     * @return {@link Optional}
     */
    @Override
    @CatDBSign
    public Optional<T> getOptById(Serializable id) {
        return Optional.ofNullable(getSlaveMapper().selectById(id));
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    @Override
    @CatDBSign
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        List<T> list = new ArrayList<>();
        if (ObjectTrue.isEmpty(idList)){
            return list;
        }
        slaveDataSource();
        /** 如果批量id 少于指定值时100条,直接查询**/
        if(idList.size() < this.DEFAULT_BATCH_SIZE) {
            list = this.getSlaveMapper().selectBatchIds(idList);
          return list;
        }
        /*** 如果大于1000条,则查用分页查询;返回结果值; */
        List<List<Serializable>> partition = Lists.partition(new ArrayList<>(idList), DEFAULT_BATCH_SIZE);
        if(partition.isEmpty()){
            return list;
        }
        for(List<Serializable> ids : partition ){
            Collection<T> dbList =  list =  this.getSlaveMapper().selectBatchIds(ids);
            if(ObjectTrue.isNotEmpty(dbList)){
                list.addAll(dbList);
            }
        }
      return list;
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    @Override
    @CatDBSign
    public List<T> listByMap(Map<String, Object> columnMap) {
        return getSlaveMapper().selectByMap(columnMap);
    }




    /**
     * 查询指定条件是否存在数据
     *
     * @param queryWrapper
     */
    @Override
    @CatDBSign
    public boolean exists(Wrapper<T> queryWrapper) {
        return super.exists(queryWrapper);
    }

    /**
     * 查询总记录数
     *
     */
    @Override
    @CatDBSign
    public long count() {
        return super.count();
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public long count(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(getSlaveMapper().selectCount(queryWrapper));
    }


    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public List<T> list(Wrapper<T> queryWrapper) {
        return getSlaveMapper().selectList(queryWrapper);
    }

    /**
     * 查询列表
     *
     * @param page         分页条件
     * @param queryWrapper queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @return 列表数据
     * @since 3.5.3.2
     */
    @Override
    @CatDBSign
    public List<T> list(IPage<T> page, Wrapper<T> queryWrapper) {
        return getSlaveMapper().selectList(page, queryWrapper);
    }




    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return getSlaveMapper().selectMaps(queryWrapper);
    }

    /**
     * 查询列表
     *
     * @param page         分页条件
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @return 列表数据
     * @since 3.5.3.2
     */
    @Override
    @CatDBSign
    public List<Map<String, Object>> listMaps(IPage<? extends Map<String, Object>> page, Wrapper<T> queryWrapper) {
        return getSlaveMapper().selectMaps(page, queryWrapper);
    }





    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public <E> List<E> listObjs(Wrapper<T> queryWrapper) {
        this.slaveDataSource();
        return super.listObjs(queryWrapper);
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    @CatDBSign
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<T> queryWrapper) {
        this.slaveDataSource();
        return super.pageMaps(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     */
    @Override
    @CatDBSign
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        this.slaveDataSource();
        return super.pageMaps(page);
    }


    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    @CatDBSign
    public QueryWrapper<T> queryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    public LambdaQueryWrapper<T> queryLambda() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @param entity 实体对象
     * @return LambdaQueryWrapper 的包装类
     */
    public LambdaQueryWrapper<T> queryLambda(T entity) {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 链式查询 lambda 式
     * kotlin 使用
     *
     * @return KtQueryWrapper 的包装类
     */
    public KtQueryChainWrapper<T> queryKt() {
        return new KtQueryChainWrapper<>(getEntityClass());
    }


}
