package com.suven.framework.core.db;//package com.suven.framework.core.db;
//
//import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.suven.framework.common.api.IBaseApi;
//import com.suven.framework.common.api.IBaseExcelData;
//import com.suven.framework.common.enums.QueryTypeEnum;
//import com.suven.framework.core.redis.BaseRedisClient;
//import com.google.common.collect.Lists;
//import org.apache.commons.collections.ObjectTrues;
//import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.*;
//
///**
// * Title: JdbcBaseCacheDao.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明)通过 JDBC 映射关系的数据查询的基础实现类,包括数据缓存到redis中和从redis缓存中查询的实现类;
// * 需要直接查找数据库的,可以使用基础 JdbcBaseEntityDao;
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//public abstract class JdbcBaseCacheDao<T extends IBaseApi> extends BaseRedisClient<T> implements IBaseExcelData {
//
//
//	@Autowired
//	private JdbcBaseDataSource dataSource;
//
//
//	public JdbcBaseDataSource getDataSource(){
//		return dataSource;
//	}
//
//	public Class<T> getEntityClass(){
//		Class<?>  clazz = ReflectionKit.getSuperClassGenericType(getClass(), 1);
//		if(clazz == null || ! IBaseApi.class.isAssignableFrom(clazz) ){
//			throw new RuntimeException("Please fill in the JdbcBaseDataSource on the class [" +clazz +"],which must Module Class<T> extends IBaseApi ") ;
//		}
//		return (Class<T>)clazz;
//	}
//
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity
//	 */
//	protected T saveEntity(T entity,String sql, List<Object> dbList ){
//		if(null == entity)
//			return  null;
//
//		long id = dataSource.insert(entity.getClass(),sql, "id", dbList.toArray());
//		if(id > 0){
//			entity.setId(id);
//			this.addCache(entity);//增加到缓存
//			return entity;
//		}
//		return null;
//
//	}
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity
//	 */
//	protected T saveEntity(T entity,String sql,String idName, List<Object> dbList ){
//		if(null == entity)
//			return  null;
//
//		long id = dataSource.insert(entity.getClass(),sql, idName, dbList.toArray());
//		if(id > 0){
//			entity.setId(id);
//			this.addCache(entity);//增加到缓存
//			return entity;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity
//	 */
//	protected boolean saveIdEntity(T entity,String sql, List<Object> dbList ){
//		if(null == entity || null == sql || null == dbList)
//			return  false;
//
//		if(entity.getId() <= 0){
//			logger.error("Entity entity by id Must be greater than zero(0)");
//			throw new RuntimeException("Entity entity by id Must be greater than zero(0)");
//		}
//		boolean id = dataSource.update(entity.getClass(),sql, dbList.toArray());
//		if(id){
//			this.addCache(entity);//增加到缓存
//			return true;
//		}
//		return false;
//
//	}
//
//	/**
//	 * 更新userAlias信息,where条件为userAliasId,更新userAlias
//	 * @param sql
//	 */
//	public boolean updateEntity(T entity,String sql, List<Object> dbList){
//		boolean flg = false ;
//		if(null == sql || dbList.isEmpty())
//			return  flg;
//
//		flg = dataSource.update(entity.getClass(),sql,dbList.toArray());
//		if(flg){
//			this.addCache( entity); //更新到缓存;
//		}return flg;
//
//	}
//
//	/**
//	 * 通过主键userAliasId删除userAlias
//	 *  @param entityId
//	 */
//	public boolean  delEntityById(Class<T> clazz , String sql ,long entityId){
//		if(entityId == 0)
//			return false;
//		this.delCache(clazz,entityId);//删除缓存
//		return dataSource.update(clazz,sql,entityId);
//
//	}
//
//	/**
//	 * 通过主键userAliasId删除userAlias
//	 *  @param entityIdList
//	 */
//	public int  delEntityByIdList(Class<T> clazz , String sql ,List<Long> entityIdList){
//		if(entityIdList == null || entityIdList.isEmpty())
//			return 0;
//		this.delCache(clazz,entityIdList);//删除缓存
//		return dataSource.updateByInToList(clazz,sql,entityIdList);
//
//	}
//
//	/**
//	 * 通过主键userAlias删除userAlias
//	 *  @param entity
//	 */
//	public boolean  delEntity(T entity,String sql){
//		if(null == entity || entity.getId() < 0)
//			return false;
//		this.delCache(entity);//删除缓存
//		return dataSource.update(entity.getClass(),sql,entity.getId());
//
//	}
//	/**
//	 * 通过主键atyAnnualReportId查找AtyAnnualReport
//	 * @param entityId
//	 */
//	public T getEntityById(Class<T> clazz , RowMapper<T> rowMapper, String sql , long entityId){
//		if(entityId <= 0)
//			return  null;
//		T entity = this.findCacheById(clazz, entityId );//从缓存中查找
//		if(null != entity){
//			return entity;
//		}
//
//		entity = dataSource.queryT(clazz,sql, rowMapper, entityId);
//		if(null == entity){
//			return null;
//		}
//		this.addCache(entity);//增加到缓存;
//		return  entity;
//	}
//
//	/**
//	 * 通过名字 videoName查找Video
//	 * @param videoFileName
//	 */
//	public T getEntityByName(Class<T> clazz , RowMapper<T> rowMapper, String sql , String videoFileName){
//		if(videoFileName == null)
//			return  null;
//		T entity = this.findCacheById(clazz, videoFileName );//从缓存中查找
//		if(null != entity){
//			return entity;
//		}
//
//		entity = dataSource.queryT(clazz,sql, rowMapper, videoFileName);
//		if(null == entity){
//			return null;
//		}
//		this.addCache(entity);//增加到缓存;
//		return  entity;
//	}
//
//	/**
//	 * 通过主键id集合批量查找AtyAnnualReport Collection<Long>
//	 * @param colle entityIdList
//	 */
//	public List<T>  getEntityListByIdList(Class<T> clazz , RowMapper<T> rowMapper, String sql , Collection<Long> colle, Object... values){
//		if(null == colle || colle.isEmpty()) {
//			return  null;
//		}
//		List<T> list = new ArrayList<>();
//		//查找从缓存中没有查找到的AtyAnnualReport实现;
//		Map<Long,T> map = this.findMapCache(clazz, colle );//从缓存中查找
//		if(null != map ){
//			list = new ArrayList<>( map.values());
//			if( map.size() == colle.size()){
//				return list;
//			}
//		}
//		//查找从缓存中没有查找到的OpusInfo实现;
//		Set<Long> removeKeys = new HashSet<>();
//		if(null != map && !map.isEmpty()){
//			Set<Long> mapKeys = map.keySet();
//			colle.removeAll(mapKeys);//删除已查到的对象信息;
//			removeKeys.addAll(mapKeys);
//		}
//		//删除之后为空集合,返回上面结果;
//		if(colle.isEmpty()){
//			return list;
//		}
//
//
//		if(colle.size() > dataSource.LISTS_PARTITON_SIZE){
//			/*** 如果大于100条,则查用分页查询;返回结果值; */
//			List<List<Long>> partition = Lists.partition(new ArrayList<>(colle),  dataSource.LISTS_PARTITON_SIZE);
//			if(ObjectTrues.isNotEmpty(partition)){
//				for(List<Long> colles : partition ){
//					List<T>  dbList = dataSource.queryByInToList(clazz,sql, rowMapper, colles);
//					if(null != dbList && !dbList.isEmpty()){
//						this.addCacheList(clazz, dbList);
//						list.addAll(dbList);
//					}
//				}
//			}
//		}else{
//
//			List<T>  dbList = dataSource.queryByInToList(clazz,sql, rowMapper, colle);
//			if(null != dbList && !dbList.isEmpty()){
//				this.addCacheList(clazz, dbList);
//				list.addAll(dbList);
//			}
//		}
//		colle.addAll(removeKeys);
//		return  list;
//	}
//
//	/**
//	 * 通过主键id集合批量查找AtyAnnualReport
//	 * @param colle
//	 */
//	public Map<Long,T>  getEntityMapByIdList(Class<T> clazz , RowMapper<T> rowMapper, String sql , Collection<Long> colle){
//		if(ObjectTrues.isEmpty(colle)){
//			return new HashMap<Long, T>();
//		}
//		Map<Long,T> map = this.findMapCache(clazz, colle);
//		if(null != map && map.size() == colle.size() ){
//			return map;
//		}
//
//		Set<Long> removeKeys = new HashSet<>(); //存储删除已查到的对象信息;
//		//查找从缓存中没有查找到的OpusInfo实现;
//		if(null != map && !map.isEmpty()){
//			Set<Long> mapKeys = map.keySet();
//			colle.removeAll(mapKeys);//删除已查到的对象信息;
//			removeKeys.addAll(mapKeys);
//		}
//		if(map == null){
//			map = new HashMap<>();
//		}
//		List<T> list = new ArrayList<>();
//
//		/** 如果批量id 少于指定值时100条,直接查询**/
//		if(colle.size() < dataSource.LISTS_PARTITON_SIZE && !colle.isEmpty()){
//			list = dataSource.queryByInToList(clazz,sql, rowMapper, colle);
//		}else{
//			/*** 如果大于100条,则查用分页查询;返回结果值; */
//			List<List<Long>> partition = Lists.partition(new ArrayList<>(colle), dataSource.LISTS_PARTITON_SIZE);
//			if(ObjectTrues.isNotEmpty(partition)){
//				for(List<Long> ids : partition ){
//					List<T> dbList = dataSource.queryByInToList(clazz,sql,rowMapper, ids);
//					if(null != dbList && !dbList.isEmpty()){
//						list.addAll(dbList);
//					}
//				}
//			}
//
//		}
//		//从db中批量查找作品信息;
//		if(ObjectTrues.isNotEmpty(list)){
//			for (T entity : list) {
//				map.put(entity.getId(), entity);
//			}
//			this.addCacheList(clazz, list);
//
//		}
//		colle.addAll(removeKeys); //重新赋值已经删除的key 保证传递与返回 colle 一致
//		return map;
//	}
//
//
//
//
//	/**
//	 * 通过分页Pager查找UserAlias
//	 * @param paramList
//	 */
//	public List<T>  getEntityByPage(Class<T> clazz , RowMapper<T> rowMapper, String sql , List<Object> paramList){
//
//		String redisKey = this.getListForRedisKey("",clazz,
//				paramList.toArray());
//
//		List<T> list  = this.findResultByCache(redisKey,clazz);//从缓存中查找
//		if(null != list && !list.isEmpty()){
//			return list;
//		}
//		List<T> dbList = dataSource.query(clazz,sql, rowMapper ,paramList.toArray());
//		if(null != dbList && !dbList.isEmpty()){
//			this.addResultToCache(redisKey,dbList);
//		}
//		return  dbList;
//
//	}
//
//	/**
//	 * 通过分页Pager查找UserAlias 取消缓存
//	 * @param paramList
//	 */
//	public List<T>  getEntityByPageNoCache(Class<T> clazz , RowMapper<T> rowMapper, String sql , List<Object> paramList){
//
//		String redisKey = this.getListForRedisKey("",clazz,
//				paramList.toArray());
//
//		List<T> dbList = dataSource.query(clazz,sql, rowMapper ,paramList.toArray());
//		if(null != dbList && !dbList.isEmpty()){
//			this.addResultToCache(redisKey,dbList);
//		}
//		return  dbList;
//
//	}
//
//	public boolean batchUpdate(Class<T> clazz ,String sql ,final List<T> list) {
//		boolean flg = false ;
//		if(null == list || list.isEmpty() )
//			return  flg;
//
//		int[][] ids = dataSource.batchUpdate(clazz,sql, list, new BatchSetter());
//		if(null != ids && ids.length > 0){
//			flg = true;
//			this.addCacheList(clazz,list);
//		}return flg;
//	}
//
//
//	/**
//	 *
//	 * @param index
//	 * @param preparedStatement
//	 * @param entity
//	 *  preparedStatement.setLong(1, users.get(idx).getId());
//	 *  preparedStatement.setString(2, users.get(idx).getUsername());
//	 *  preparedStatement.setString(3, users.get(idx).getPassword());
//	 */
//	protected abstract void setSetterValues(int index ,PreparedStatement preparedStatement, T entity);
//
//	/**
//	 * 批量更新的实现转换实现
//	 */
//
//	protected class  BatchSetter implements  ParameterizedPreparedStatementSetter<T>{
//
//		@Override
//		public void setValues(PreparedStatement statement, T entity) throws SQLException {
//			int index  = 1;
//			setSetterValues(index,statement,entity);
////            statement.setLong(index++,10);
////            statement.setString(index++,"name");
//		}
//	}
//
//
//	/**
//	 * 通过分页Pager查找UserAlias
//	 * @param paramList
//	 */
//	public int getEntityByPageCount(Class<T> clazz , String sql , List<Object> paramList){
//
////		String redisKey = this.("",clazz,
////				paramList.toArray());
////
////		List<T> list  = this.findResultByCache(redisKey,clazz);//从缓存中查找
////		if(null != list && !list.isEmpty()){
////			return list;
////		}
//		int total = dataSource.queryInt(clazz,sql ,paramList.toArray());
////			this.addResultToCache(redisKey,total);
//		return  total;
//
//	}
//
//	/**
//	 *  增加 拼装 查询sql语句的查询条件 及 赋值
//	 * @param sql sql语句 查询条件拼装
//	 * @param name 查询条件 数据库字段名
//	 * @param queryType 查询条件类型
//	 * @param list  查询条件 赋值的列表
//	 * @param value  查询条件的值
//	 */
//	protected void addEasyQuery(StringBuilder sql, String name, QueryTypeEnum queryType, List<Object> list, Object value) {
//		if (value == null || queryType == null || list==null) {
//			return;
//		}
//
//		sql.append(" and ");
//		sql.append(name);
//		sql.append(queryType.getJdbcKey());
//		list.add(value);
//	}
//
//
//}
