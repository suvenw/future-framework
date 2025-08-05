//package com.suven.framework.core.db;
//
//
//
//import org.springframework.stereotype.Component;
//import com.suven.framework.common.cat.CatDBSign;
//import com.suven.framework.core.db.ext.DSClassAnnoExplain;
//import com.suven.framework.util.json.JsonUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.*;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.*;
//import java.util.Date;
//
//
///**
// * Title: JdbcBaseDataSource.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) 1.通过JDBC 实现动态数据库连接池指定业务模块的 主-从 功能切换;
// * 2.对jdbcTemplate查询请求方法的封装和返回结果对象化处理;方便调用和实现业务；
// * 3.在执行方法上提供@CatDBSign杯签,方便cat监控通过切面实现数据运行时长采摘和上报业务;
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//@Component
//public class JdbcBaseDataSource  {
//
//	public static Logger log = LoggerFactory.getLogger(JdbcBaseDataSource.class);
//
//	public final int LISTS_PARTITON_SIZE = 100;//Lists.partition
//
//	private JdbcTemplate jdbcTemplate;
//
//	@Resource(name="dataSource")
//	public void setDataSource(DataSource dataSource) {
//		jdbcTemplate = new JdbcTemplate(dataSource);
//	}
//
//	/**通过当前线程安全结合类对应数据库模块标签实现数年组切换,对应的数据组类型为从数据库
//	 * @param tClass
//	 */
//	private  void slaveDataSource(Class<?> tClass){
//		DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(tClass);
//		if(dataSourceGroup == null){
//			return;
//		}
//		dataSourceGroup.setDataType(DataSourceTypeEnum.SLAVE);
//        log.info(" slaveDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
//		DataSourceHolder.putDataSource(dataSourceGroup);
//	}
//
//	/**通过当前线程安全结合类对应数据库模块标签实现数年组切换,对应的数据组类型为主数据库
//	 * @param tClass
//	 */
//	private  void masterDataSource(Class<?> tClass){
//		DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(tClass);
//		if(dataSourceGroup == null){
//			return;
//		}
//		dataSourceGroup.setDataType(DataSourceTypeEnum.MASTER);
//		log.info(" masterDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
//		DataSourceHolder.putDataSource(dataSourceGroup);
//	}
//
//	/**
//	 * 简单查询,PreparedStatement形式
//	 * @param <T>
//	 *
//	 * @param sql
//	 *            查询语句
//	 * @param rowMapper
//	 * @param values
//	 *            查询参数
//	 * @return
//	 * @throws Exception
//	 */
//	@CatDBSign
//	public <T> List<T> query(Class<T> clazz, String sql, final RowMapper<T> rowMapper, Object... values) {
//		log.info("query-sql:[{}],param:{}",sql, values);
//		final List<T> list = new ArrayList<>();
//		slaveDataSource(clazz);
//		jdbcTemplate.query(sql, replaceParamType(values), new ResultSetExtractor<List<T>>() {
//			public List<T> extractData(ResultSet rs) {
//				try {
//					int i = 0;
//					while (rs.next()) {
//						list.add(rowMapper.mapRow(rs, i));
//						i++;
//					}
//				} catch (SQLException e) {
//					log.error("RowMapper-query-sql error", e);
//				}
//				return list;
//			}
//		});
//		DataSourceHolder.clear();
//		return list;
//	}
//	@CatDBSign
//	public <T> List<T> queryByInToList(Class<T> clazz,String sql , final RowMapper<T> rowMapper, Collection<Long>colle){
//		try {
//			slaveDataSource(clazz);
//
//			String ids = StringUtils.join(colle, ",");
//			String inSql = replaceByInToIds(sql, ids);
//
//			log.info("query-sql:[{}],param:{}",inSql,ids);
//			return jdbcTemplate.query(inSql, rowMapper);
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	@CatDBSign
//	public <T> List<T> queryByInToList(Class<T> clazz,String sql , final RowMapper<T> rowMapper, Collection<Long>colle, Object... values){
//		try {
//			slaveDataSource(clazz);
//
//			String ids = StringUtils.join(colle, ",");
//			String inSql = replaceByInToIds(sql, ids);
//
//			if(null == values || values.length == 0){
//				return queryByInToList(clazz,sql,rowMapper,colle);
//			}
//
//			log.info("query-sql:[{}],param ids:{} , param:{}",inSql,ids,values);
//			return jdbcTemplate.query(inSql, rowMapper,replaceParamType(values));
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	@CatDBSign
//	public <T> T queryT(Class<T> clazz,String sql, final RowMapper<T> rowMapper, Object... values) {
//		log.info("query-sql:[{}],param:{}",sql, values);
//		try {
//			slaveDataSource(clazz);
//			List<T> list =  jdbcTemplate.query(sql, rowMapper, replaceParamType(values));
//			if(null != list && !list.isEmpty()){
//				return list.get(0);
//			}return null;
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	@CatDBSign
//	public <T> List<T> queryForList(Class<T> clazz,String sql , Class<T> elementType , Object... args){
//		log.info("query-sql:[{}],param:{}",sql, args);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForList(sql, elementType, replaceParamType(args));
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//
//	@CatDBSign
//	public <T>List<Map<String, Object>> queryList(Class<T> clazz,String sql, Object... args) {
//		log.info("query-sql:[{}],param:{}",sql, args);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForList(sql, replaceParamType(args));
//		} catch(DataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	@CatDBSign
//	public <T>int queryInt(Class<T> clazz,String sql, Object... value) {
//		log.info("query-sql:[{}],param:{}",sql, value);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForObject(sql, replaceParamType(value), Integer.class);
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return 0;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	@CatDBSign
//	public int queryForInt(Class<?> clazz,String sql) {
//		log.info("query-sql:[{}]",sql);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForObject(sql, Integer.class);
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return 0;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//
//	@CatDBSign
//	public Long queryLong(Class<?> clazz,String sql, Object... value) {
//		log.info("query-sql:[{}],param:{}",sql, value);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForObject(sql, replaceParamType(value), Long.class);
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return 0L;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//
//	@CatDBSign
//	public String queryString(Class<?> clazz,String sql, Object... value) {
//		log.info("query-sql:[{}],param:{}",sql, value);
//		try {
//			slaveDataSource(clazz);
//			return jdbcTemplate.queryForObject(sql, replaceParamType(value), String.class);
//		} catch(EmptyResultDataAccessException e) {
//			log.error("query-sql error", e);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//
//	/**
//	 * 执行update / insert 语句
//	 *
//	 * @param sql
//	 *            sql 语句
//	 * @param args
//	 *            参数数组
//	 * @return
//	 * @throws Exception
//	 */
//	@CatDBSign
//	public boolean update(Class<?> clazz,String sql, Object... args) {
//		log.info("update-sql:[{}],param:{}",sql, args);
//		try {
//			masterDataSource(clazz);
//			int c = jdbcTemplate.update(sql, replaceParamType(args));
//			return c > 0;
//		} catch (DuplicateKeyException dke) {
//			log.error("update error", dke);
//		}catch (DataAccessException dae) {
//			log.error("update error", dae);
//		}finally {
//			DataSourceHolder.clear();
//		}
//		return false;
//	}
//
//	/**
//	 * 执行update / insert 语句
//	 *
//	 * @param sql
//	 *            sql 语句
//	 * @param inIds
//	 *            参数数组
//	 * @return
//	 * @throws Exception
//	 */
//	@CatDBSign
//	public int updateByInToList(Class<?> clazz,String sql,  Collection<Long> inIds, Object ... args) {
//		log.info("update-sql:[{}],ids:{}, param:{}",sql, inIds,args);
//		try {
//			masterDataSource(clazz);
//			String ids = StringUtils.join(inIds, ",");
//			String inSql = replaceByInToIds(sql, ids);
//
//			int count = jdbcTemplate.update(inSql, replaceParamType(args));
//			return count;
//		} catch (DuplicateKeyException dke) {
//			log.error("update error", dke);
//		}catch (DataAccessException dae) {
//			log.error("update error", dae);
//		}finally {
//			DataSourceHolder.clear();
//		}
//		return 0;
//	}
//
//
//	/**
//	 *  插入数据,返回自增id
//	 * @param sql
//	 * @param key 主键字段名
//	 * @param args	参数列表
//	 * @return -1-异常
//	 */
//	@CatDBSign
//	public long insert(Class<?> clazz,final String sql, final String key ,final Object... args ){
//		log.info("insert-sql:[{}],param:{}",sql, args);
//		try {
//			masterDataSource(clazz);
//			long result = -1L;
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			PreparedStatementCreator psc  = new PreparedStatementCreator() {
//				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//					final PreparedStatement ps = connection.prepareStatement(sql,new String[] { key });
//					if ( args != null) {
//						for(int i= 0; i<args.length;i++ ){
//							Object param = args[i];
//							if(param instanceof Date){
//								param = new Timestamp(((Date)param).getTime());
//							}
//							ps.setObject(i+1, param);
//						}
//					}
//					return ps;
//				}
//			};
//			result = jdbcTemplate.update(psc, keyHolder);
//			long id = result > 0 ? keyHolder.getKey().longValue() : -1;
//			if(id < 0){
//				log.error("insert-sql:{}, ,param:{}", sql, args);
//			}
//			return id;
//		} catch (DataAccessException e) {
//			log.error("insert error", e);
//		} catch (Exception e) {
//			log.error("insert error", e);
//		}finally {
//			DataSourceHolder.clear();
//		}
//		return -1;
//	}
//
//	/**
//	 * SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
//	 * ParameterizedBeanPropertyRowMapper.newInstance(UserInfo.class)
//	 * String sqlStr="update tableName set filed_name=:name,filed_value=:value where id=:id";
//	 * @param object
//	 * @return
//	 */
//	@CatDBSign
//	public int updateBean(Class<?> clazz,String sql ,Object object) {
//		masterDataSource(clazz);
//		SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
//		return jdbcTemplate.update(sql, beanParam);
//	}
//
//	/**
//	 * SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
//	 * ParameterizedBeanPropertyRowMapper.newInstance(UserInfo.class)
//	 * String sqlStr="update tableName set filed_name=:name,filed_value=:value where id=:id";
//	 * @param list
//	 * @return
//	 */
//	@CatDBSign
//	public <T> int[][]   batchBean(Class<?> clazz,String sql, List<T> list,ParameterizedPreparedStatementSetter setter)
//	{
//		masterDataSource(clazz);
//		int[][]  ids = jdbcTemplate.batchUpdate(sql,list,list.size()-1,setter) ;
//		return ids;
//	}
//
//
//
//
//
//	/**
//	 * 批量执行update / insert 语句
//	 * @param sql
//	 * @return
//	 */
//	@CatDBSign
//	public int[] batchUpdate(Class<?> clazz,String sql,BatchPreparedStatementSetter setter) {
//		log.info("update-sql:[{}],param:{}",sql, setter.toString());
//		try {
//
//			masterDataSource(clazz);
//			int[] ids = jdbcTemplate.batchUpdate(sql,setter);
//			return ids;
//
//		} catch (DataAccessException dae) {
//			log.error("batch update-sql error", dae);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	public <T>int[][] batchUpdate(Class<?> clazz,String sql,Collection<T> batchArgs, ParameterizedPreparedStatementSetter setter){
//		log.info("update-sql:[{}],param:{}",sql, setter.toString());
//		try {
//
//			masterDataSource(clazz);
//			int[][] ids = jdbcTemplate.batchUpdate(sql,batchArgs,batchArgs.size(), setter);
//			return ids;
//
//		} catch (DataAccessException dae) {
//			log.error("batch update-sql error", dae);
//			return null;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//
//	/**
//	 * 批量执行update / insert 语句
//	 * @param sql
//	 * @return
//	 */
//	@CatDBSign
//	public boolean batchUpdate(Class<?> clazz,String sql,List<Object[]> batchArgs) {
//		log.info("update-sql:[{}],param:{}",sql, batchArgs);
//		try {
//			if(!sql.toLowerCase().contains("where")){
//				log.error("update-sql not where condition sql:[{}],param:{}",sql, batchArgs);
//				return false;
//			}
//			masterDataSource(clazz);
//			int[] ids = jdbcTemplate.batchUpdate(sql, batchArgs);
//			return null != ids && ids.length > 0;
//
//		} catch (DataAccessException dae) {
//			log.error("batch update-sql error", dae);
//			return false;
//		}finally {
//			DataSourceHolder.clear();
//		}
//	}
//	/**
//	 * 批量更新
//	 * @author ruan
//	 * @param sql sql语句数组
//	 * @return 每一条sql语句对应影响的行数
//	 */
//	@CatDBSign
//	public boolean batchUpdate(Class<?> clazz,String[] sql){
//		log.info("update-sql:[{}]",String.join(",",sql));
//		try {
//			masterDataSource(clazz);
//			int[] ids =  jdbcTemplate.batchUpdate(sql);
//			return null != ids && ids.length > 0;
//		} catch (DataAccessException e) {
//			log.error("batch update-sql error", e);
//		}finally {
//			DataSourceHolder.clear();
//		}
//		return false;
//	}
//
//
//	private String replaceByInToIds(String sql, String replacement){
//		final String searchString = "?";
//		final String searchIn = " IN ";
//		int beginIndex = sql.toUpperCase().indexOf(searchIn);
//		if( beginIndex > 0 && (sql.indexOf(searchString) > 0)){
//			String s = sql.substring(0,beginIndex) + StringUtils.replace(sql.substring(beginIndex), searchString, replacement,1);
//			return s;
//		}
//		return sql;
//	}
//
//
//	private Object[] replaceParamType(Object... args){
//		return args;
////		return Arrays.stream(args).map(org -> {
////			if(org instanceof Date){
////				return new Timestamp(((Date)org).getTime());
////			}
////			return org;
////		}).collect(Collectors.toList()).toArray();
//	}
//
//
//
//}