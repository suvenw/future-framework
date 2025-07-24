//package com.suven.framework.core.db;
//
//import com.google.common.collect.Lists;
//import com.suven.framework.core.IterableConvert;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
//import org.springframework.jdbc.core.RowMapper;
//import com.suven.framework.common.api.IBaseApi;
//import com.suven.framework.common.enums.QueryTypeEnum;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.*;
//
///**
// * @Title: JdbcBaseEntityDao.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明)通过 JDBC 映射关系的数据查询的基础实现类,该类是直接查找数据库的,需要实现数据缓存,可以使用 JdbcBaseCacheDao;
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//public abstract class JdbcBaseEntityDao<T extends IBaseApi>  {
//
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private JdbcBaseDataSource dataSource;
//
//	public JdbcBaseDataSource getDataSource(){
//		return dataSource;
//	}
//
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity 类说明
//	 */
//	protected T saveEntity(T entity,String sql, List<Object> dbList ){
//		if(null == entity){
//			return  null;
//		}
//
//		long id = dataSource.insert(entity.getClass(),sql, "id", dbList.toArray());
//		if(id > 0){
//			entity.setId(id);
//			return entity;
//		}
//		return null;
//
//	}
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity 对象实体
//	 */
//	protected T saveEntity(T entity,String sql,String idName, List<Object> dbList ){
//		if(null == entity) {
//			return null;
//		}
//		long id = dataSource.insert(entity.getClass(),sql, idName, dbList.toArray());
//		if(id > 0){
//			entity.setId(id);
//			return entity;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 保存创建UserAlias,并且保存到缓存中
//	 * @param entity 对象实体
//	 */
//	protected boolean saveIdEntity(T entity,String sql, List<Object> dbList ){
//		if(null == entity || null == sql || null == dbList) {
//			return false;
//		}
//
//		if(entity.getId() <= 0){
//			logger.error("Entity entity by id Must be greater than zero(0)");
//			throw new RuntimeException("Entity entity by id Must be greater than zero(0)");
//		}
//		boolean id = dataSource.update(entity.getClass(),sql, dbList.toArray());
//		return id;
//
//
//	}
//
//	/**
//	 * 更新userAlias信息,where条件为userAliasId,更新userAlias
//	 * @param sql sql语句
//	 */
//	public boolean updateEntity(T entity,String sql, List<Object> dbList){
//		boolean flg = false ;
//		if(null == sql || dbList.isEmpty()){
//			return  false;
//		}
//
//		flg = dataSource.update(entity.getClass(),sql,dbList.toArray());
//		return flg;
//
//	}
//
//	/**
//	 * 通过主键userAliasId删除userAlias
//	 *  @param entityIdList 对象实体主键Id集体
//	 */
//	public int  updateStatusByIdList(Class<T> clazz, String sql ,List<Long> entityIdList,Object ... args){
//		if(entityIdList == null || entityIdList.isEmpty()){
//			return 0;
//		}
//
//		return dataSource.updateByInToList(clazz,sql,entityIdList,args);
//
//	}
//
//	/**
//	 * 通过主键userAliasId删除userAlias
//	 *  @param entityId 对象实体主键Id
//	 */
//	public boolean  delEntityById(Class<T> clazz , String sql ,long entityId){
//		if(entityId == 0){
//			return false;
//		}
//		return dataSource.update(clazz,sql,entityId);
//
//	}
//
//	/**
//	 * 通过主键userAliasId删除userAlias
//	 *  @param entityIdList 对象实体主键Id集合
//	 */
//	public int  delEntityByIdList(Class<T> clazz , String sql ,List<Long> entityIdList){
//		if(entityIdList == null || entityIdList.isEmpty()) {
//			return 0;
//		}
//		return dataSource.updateByInToList(clazz,sql,entityIdList);
//
//	}
//
//	/**
//	 * 通过主键userAlias删除userAlias
//	 *  @param entity 对象实体
//	 */
//	public boolean  delEntity(T entity,String sql){
//		if(null == entity || entity.getId() < 0) {
//			return false;
//		}
//		return dataSource.update(entity.getClass(),sql,entity.getId());
//
//	}
//	/**
//	 * 通过主键atyAnnualReportId查找AtyAnnualReport
//	 * @param entityId 对象实体主键Id
//	 */
//	public T getEntityById(Class<T> clazz , RowMapper<T> rowMapper, String sql , long entityId){
//		if(entityId <= 0) {
//			return null;
//		}
//
//		T entity = dataSource.queryT(clazz,sql, rowMapper, entityId);
//		return  entity;
//	}
//
//	/**
//	 * 通过名字 videoName查找Video
//	 * @param findFileName 搜索名称
//	 */
//	public T getEntityByName(Class<T> clazz , RowMapper<T> rowMapper, String sql , String findFileName){
//		if(findFileName == null) {
//			return null;
//		}
//
//		T entity = dataSource.queryT(clazz,sql, rowMapper, findFileName);
//		if(null == entity){
//			return null;
//		}
//		return  entity;
//	}
//
//	/**
//	 * 通过主键id集合批量查找AtyAnnualReport Collection<Long>
//	 * @param colle entityIdList
//	 */
//	public List<T>  getEntityListByIdList(Class<T> clazz , RowMapper<T> rowMapper, String sql , Collection<Long> colle, Object... values){
//		if(null == colle || colle.isEmpty()) {
//		   return  null;
//		}
//		List<T> list = new ArrayList<>();
//		//查找从缓存中没有查找到的AtyAnnualReport实现;
//		if(colle.size() > dataSource.LISTS_PARTITON_SIZE){
//			/*** 如果大于100条,则查用分页查询;返回结果值; */
//			List<List<Long>> partition = Lists.partition(new ArrayList<>(colle),  dataSource.LISTS_PARTITON_SIZE);
//			if(null !=partition && !partition.isEmpty()){
//				for(List<Long> colles : partition ){
//					List<T>  dbList = dataSource.queryByInToList(clazz,sql, rowMapper, colles,values);
//					if(null != dbList && !dbList.isEmpty()){
//						list.addAll(dbList);
//					}
//				}
//			}
//		}else{
//			List<T>  dbList = dataSource.queryByInToList(clazz,sql, rowMapper, colle);
//			if(null != dbList && !dbList.isEmpty()){
//				list.addAll(dbList);
//			}
//		}
//		return  list;
//	}
//
//	/**
//	 * 通过主键id集合批量查找AtyAnnualReport
//	 * @param colle 集合
//	 */
//	public Map<Long,T>  getEntityMapByIdList(Class<T> clazz , RowMapper<T> rowMapper, String sql , Collection<Long> colle, Object... values){
//
//		List<T> list = getEntityListByIdList(clazz,rowMapper,sql,colle,values);
//		if(null == list ){
//			return new HashMap<>(1);
//		}
//		//从db中批量查找作品信息;
//		Map<Long,T> map = IterableConvert.convertMap(list);
//		return map;
//	}
//
//
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
//		List<T> dbList = dataSource.query(clazz,sql, rowMapper ,paramList.toArray());
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
//		List<T> dbList = dataSource.query(clazz,sql, rowMapper ,paramList.toArray());
//		return  dbList;
//
//	}
//
//	public boolean batchUpdate(Class<T> clazz ,String sql ,final List<T> list) {
//		if(null == list || list.isEmpty() ) {
//			return false;
//		}
//
//		int[][] ids = dataSource.batchUpdate(clazz,sql, list, new BatchSetter());
//		if(null != ids && ids.length > 0){
//			return true;
//		}return false;
//	}
//
//
//
//
//	/**
//	 *
//	 * @param index 索引下标值，以1开始
//	 * @param preparedStatement 数据库参数对象
//	 * @param entity 查询结果对象
//	 * @Exceptin SQLException 异常处理
//	 *  preparedStatement.setLong(1, users.get(idx).getId());
//	 *  preparedStatement.setString(2, users.get(idx).getUsername());
//	 *  preparedStatement.setString(3, users.get(idx).getPassword());
//	 */
//	protected abstract void setSetterValues(int index ,PreparedStatement preparedStatement, T entity) throws SQLException;
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
//	/**
//	 * 通过分页Pager查找UserAlias
//	 * @param paramList
//	 * @Return 统计数
//	 */
//	public int getEntityByPageCount(Class<T> clazz , String sql , List<Object> paramList){
//
//		int total = dataSource.queryInt(clazz,sql ,paramList.toArray());
//		return  total;
//
//	}
//
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
//}
