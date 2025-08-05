package com.suven.framework.core.mybatis;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.suven.framework.common.cat.CatCacheSign;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.redis.RedisClientConvertEntity;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBaseExcelData;
import com.suven.framework.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Title: MyBatisBaseCacheDao.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) MyBatis dao 查询缓存redis或数据库的 实现类,（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
@SuppressWarnings("unchecked")
@Component
public abstract class AbstractMyBatisCacheDao<M extends BaseMapper<T>, T extends IBaseApi> extends AbstractMyBatisDao<M,T> implements IService<T>, IBaseExcelData {



    @Autowired
    private RedisClientConvertEntity<T> redisClientServer;


    public RedisClientConvertEntity getRedis(){
        return redisClientServer;
    }




    @CatCacheSign
    public boolean save(T entity) {
        masterDataSource();
       boolean result = super.save(entity);
       if(result){
           this.getRedis().addCache(entity);
       }
       return result;
    }

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize ignore
     * @return ignore
     */
   @CatCacheSign
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if(null == entityList ||batchSize <= 0 ){
            return  false;
        }
        masterDataSource();
        boolean result=  super.saveBatch(entityList,batchSize);
        if(result){
            this.addCacheByList(entityList);
        }
        return result;
    }



    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
   @CatCacheSign
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
          boolean result = super.saveOrUpdate(entity);
          if(result){
              this.getRedis().addCache(entity);
          }
          return result;
        }
        return false;
    }
   @CatCacheSign
   public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        masterDataSource();
        super.saveOrUpdateBatch(entityList,batchSize);
        this.addCacheByList(entityList);
        return true;
    }


   @CatCacheSign
    public T getById(long id) {
      IBaseApi  entity = this.getRedis().findCacheById(getEntityClass(),id);
       if(null != entity){
           return (T)entity;
       }
        entity = super.getById(id);
        this.getRedis().addCache(entity);
        return (T)entity;
    }


   @CatCacheSign
    public List<T> getListByIds(Collection<Long> idList) {
        if(ObjectTrue.isEmpty(idList)){
            return new ArrayList<>();
        }
        Map<Long,T> map =  getMapByIds(idList);
        if (null == map || map.isEmpty()){
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());

    }

    @CatCacheSign
    public Map<Long,T> getMapByIds(Collection<Long> idList) {
        if(ObjectTrue.isEmpty(idList)){
            return new HashMap<>();
        }
        Map<Long,T> map = this.getRedis().findMapCache(getEntityClass(), idList);
        if(null != map && map.size() == idList.size() ){
            return map;
        }

        Set<Long> removeKeys = new HashSet<>(); //存储删除已查到的对象信息;
        //查找从缓存中没有查找到的OpusInfo实现;
        if(null != map && !map.isEmpty()){
            Set<Long> mapKeys = map.keySet();
            idList.removeAll(mapKeys);//删除已查到的对象信息;
            removeKeys.addAll(mapKeys);
        }
        if(map == null){
            map = new HashMap<>();
        }
        List<T> list = new ArrayList<>();

        // 如果批量id 少于指定值时100条,直接查询
        if(idList.size() < BATCH_SIZE && !idList.isEmpty()){
            list =  super.listByIds(idList);
        }else{
            ///*** 如果大于100条,则查用分页查询;返回结果值; */
            List<List<Long>> partition = Lists.partition(new ArrayList<>(idList), BATCH_SIZE);
            if(!partition.isEmpty()){
                for(List<Long> ids : partition ){
                    List<T> dbList =  list = super.listByIds(ids);
                    if(null != dbList && !dbList.isEmpty()){
                        list.addAll(dbList);
                    }
                }
            }

        }
        //从db中批量查找作品信息;
        if(ObjectTrue.isNotEmpty(list)){
            for (T entity : list) {
                map.put(entity.getId(), entity);
            }
            this.getRedis().addCacheList(getEntityClass(), list);

        }
        idList.addAll(removeKeys); //重新赋值已经删除的key 保证传递与返回 colle 一致
        return map;
    }

   @CatCacheSign
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        String key = this.getRedis().getPrefixKey(getEntityClass()) +  JsonUtils.toJson(queryWrapper);
        T result = (T) this.getRedis().findCacheByKey(key, getEntityClass());
        if(result != null){
            return result;
        }
        if (throwEx) {
            result = super.getOne(queryWrapper);
            this.getRedis().addCacheByKey(key,result);
            return result;
        }
        result = SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
        this.getRedis().addCacheByKey(key,result);
        return result;
    }


    @Override
    public void saveData(List<Object> list) {

    }

    protected boolean addCacheByList(Collection<T> entityList){
        try {
            return this.getRedis().addCacheList(getEntityClass(),new ArrayList<>(entityList));
        }catch (Exception e){
            log.error("updateBatchById to  this.getRedis().addCacheList exception:[{}]",e);
        }
        return true;
    }
}
