//package com.suven.framework.core.mybatis;
//
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.enums.SqlMethod;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.metadata.TableInfo;
//import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
//import com.baomidou.mybatisplus.core.toolkit.*;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
//import com.google.common.collect.Lists;
//import com.suven.framework.common.api.IBaseApi;
//import com.suven.framework.common.api.IBaseExcelData;
//import com.suven.framework.common.cat.CatDBSign;
//import com.suven.framework.util.json.JsonUtils;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.logging.Log;
//import org.apache.ibatis.logging.LogFactory;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.*;
//
//
//
///**
// * @Title: MyBatisBaseCacheDao.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) MyBatis dao 查询缓存redis或数据库的 实现类,（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//@SuppressWarnings("unchecked")
//public class MyBatisBaseCacheDao<M extends BaseMapper<T>, T extends IBaseApi> extends AbstractMyBatisDao<M,T> implements IService<T>, IBaseExcelData {
//
//    protected Log log = LogFactory.getLog(getClass());
//
//    private final int LISTS_PARTITON_SIZE = 100;//Lists.partition
//
//
//    @Autowired
//    private MyBatisBaseRedisClient myBatisRedisClient;
//
//
//    public MyBatisBaseRedisClient getRedis(){
//        return myBatisRedisClient;
//    }
//
//    public boolean returnBool(Long result) {
//        return null != result && result >= 0;
//    }
//
//    public Class<T> getEntityClass(){
//        return  currentModelClass();
//    }
//
//
//    @CatDBSign
//    public boolean save(T entity) {
//        masterDataSource();
//       boolean result = super.save(entity);
//       if(result){
//           myBatisRedisClient.addCache(entity);
//       }
//       return result;
//    }
//
//    /**
//     * 批量插入
//     *
//     * @param entityList ignore
//     * @param batchSize ignore
//     * @return ignore
//     */
//    @CatDBSign
//    public boolean saveBatch(Collection<T> entityList, int batchSize) {
//        if(null == entityList ||batchSize <= 0 ){
//            return  false;
//        }
//        masterDataSource();
//        boolean result=  super.saveBatch(entityList,batchSize);
//        if(true){
//            this.addCacheByList(entityList);
//        }
//        return result;
//    }
//
//
//
//    /**
//     * TableId 注解存在更新记录，否插入一条记录
//     *
//     * @param entity 实体对象
//     * @return boolean
//     */
//    @CatDBSign
//    public boolean saveOrUpdate(T entity) {
//        if (null != entity) {
//          boolean result = super.saveOrUpdate(entity);
//          if(result){
//              myBatisRedisClient.addCache(entity);
//          }
//          return result;
//        }
//        return false;
//    }
//    @CatDBSign
//    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
//        Assert.notEmpty(entityList, "error: entityList must not be empty");
//        masterDataSource();
//        saveOrUpdateBatchTr(entityList,batchSize);
//        this.addCacheByList(entityList);
//        return true;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    protected boolean saveOrUpdateBatchTr(Collection<T> entityList, int batchSize) {
//
//        Class<?> cls = getEntityClass();
//        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
//        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
//        String keyProperty = tableInfo.getKeyProperty();
//        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
//        try (SqlSession batchSqlSession = sqlSessionBatch()) {
//            int i = 0;
//            for (T entity : entityList) {
//                Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
//                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
//                    batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
//                } else {
//                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
//                    param.put(Constants.ENTITY, entity);
//                    batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
//                }
//                // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
//                if (i >= 1 && i % batchSize == 0) {
//                    batchSqlSession.flushStatements();
//                }
//                i++;
//            }
//            batchSqlSession.flushStatements();
//        }
//        return true;
//    }
//
//    @CatDBSign
//    public boolean removeById(long id) {
//        myBatisRedisClient.delCache(getEntityClass(),id);
//        return super.removeById(id);
//
//    }
//
//    @CatDBSign
//    public boolean removeByIds(Class<T> clazz ,List<Long> idList) {
//        masterDataSource();
//        boolean result =  super.removeByIds(idList);
//        if(result){
//            myBatisRedisClient.delCache(clazz,idList);
//        }
//        return result;
//    }
//
//    @CatDBSign
//    public boolean updateById(T entity) {
//        masterDataSource();
//        boolean result =  super.updateById(entity);
//        if(result){
//            myBatisRedisClient.addCache(entity);
//        }
//        return result;
//    }
//
//    @CatDBSign
//    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
//        Assert.notEmpty(entityList, "error: entityList must not be empty");
//        masterDataSource();
//        updateBatchTrById(entityList,batchSize);
//        this.addCacheByList(entityList);
//        return true;
//
//    }
//
//    protected boolean addCacheByList(Collection<T> entityList){
//        try {
//            myBatisRedisClient.addCacheList(getEntityClass(),new ArrayList(entityList));
//        }catch (Exception e){
//            log.error("updateBatchById to  myBatisRedisClient.addCacheList exception:[{}]",e);
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    protected boolean updateBatchTrById(Collection<T> entityList, int batchSize) {
//        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
//        try (SqlSession batchSqlSession = sqlSessionBatch()) {
//            int i = 0;
//
//            for (T anEntityList : entityList) {
//                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
//                param.put(Constants.ENTITY, anEntityList);
//                batchSqlSession.update(sqlStatement, param);
//                if (i >= 1 && i % batchSize == 0) {
//                    batchSqlSession.flushStatements();
//                }
//                i++;
//            }
//            batchSqlSession.flushStatements();
//        }
//        return true;
//    }
//
//    @CatDBSign
//    public T getById(long id) {
//      IBaseApi  entity = myBatisRedisClient.findCacheById(getEntityClass(),id);
//       if(null != entity){
//           return (T)entity;
//       }
//        entity = super.getById(id);
//        myBatisRedisClient.addCache(entity);
//        return (T)entity;
//    }
//
//
//    @CatDBSign
//    public List<T> getListByIds(Collection<Long> idList) {
//        if(CollectionUtils.isEmpty(idList)){
//            return new ArrayList<>();
//        }
//        Map<Long,T> map =  getMapByIds(idList);
//        if (null == map || map.isEmpty()){
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(map.values());
//
//    }
//
//    @CatDBSign
//    public Map<Long,T> getMapByIds(Collection<Long> idList) {
//        if(CollectionUtils.isEmpty(idList)){
//            return new HashMap<>();
//        }
//        Map<Long,T> map = myBatisRedisClient.findMapCache(getEntityClass(), idList);
//        if(null != map && map.size() == idList.size() ){
//            return map;
//        }
//
//        Set<Long> removeKeys = new HashSet<>(); //存储删除已查到的对象信息;
//        //查找从缓存中没有查找到的OpusInfo实现;
//        if(null != map && !map.isEmpty()){
//            Set<Long> mapKeys = map.keySet();
//            idList.removeAll(mapKeys);//删除已查到的对象信息;
//            removeKeys.addAll(mapKeys);
//        }
//        if(map == null){
//            map = new HashMap<>();
//        }
//        List<T> list = new ArrayList<>();
//
//        /** 如果批量id 少于指定值时100条,直接查询**/
//        if(idList.size() < LISTS_PARTITON_SIZE && !idList.isEmpty()){
//            list = (List<T>) super.listByIds(idList);
//        }else{
//            /*** 如果大于100条,则查用分页查询;返回结果值; */
//            List<List<Long>> partition = Lists.partition(new ArrayList<>(idList), LISTS_PARTITON_SIZE);
//            if(null !=partition && !partition.isEmpty()){
//                for(List<Long> ids : partition ){
//                    List<T> dbList =  list =(List<T>)  super.listByIds(ids);
//                    if(null != dbList && !dbList.isEmpty()){
//                        list.addAll(dbList);
//                    }
//                }
//            }
//
//        }
//        //从db中批量查找作品信息;
//        if(CollectionUtils.isNotEmpty(list)){
//            for (T entity : list) {
//                map.put(entity.getId(), entity);
//            }
//            myBatisRedisClient.addCacheList(getEntityClass(), list);
//
//        }
//        idList.addAll(removeKeys); //重新赋值已经删除的key 保证传递与返回 colle 一致
//        return map;
//    }
//
//    @CatDBSign
//    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
//        String key = myBatisRedisClient.getPrefixKey(getEntityClass()) +  JsonUtils.toJson(queryWrapper);
//        T result = (T) myBatisRedisClient.findCacheByKey(key, getEntityClass());
//        if(result != null){
//            return result;
//        }
//        if (throwEx) {
//            result = super.getOne(queryWrapper);
//            myBatisRedisClient.addCacheByKey(key,result);
//            return result;
//        }
//        result = SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
//        myBatisRedisClient.addCacheByKey(key,result);
//        return result;
//    }
//
//
//    @Override
//    public void saveData(List<Object> list) {
//
//    }
//}
