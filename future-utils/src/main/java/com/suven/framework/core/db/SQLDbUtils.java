package com.suven.framework.core.db;

import com.suven.framework.common.enums.SQLFormatEnum;
import com.suven.framework.common.enums.SqlTypeEnum;
import com.suven.framework.common.enums.TableTypeEnum;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.util.json.StringFormat;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: Dbutils.java
 * @author Joven.wang
 * date 2016年1月21日
 * @version V1.0
 * Description: TODO(说明)
 */

public class SQLDbUtils {

	private static Logger logger = LoggerFactory.getLogger(SQLDbUtils.class);
	/**
	 * 保存对象,只会保存生成对应数据值不为null的sql,返回插入数据sql和排序好的对象集合数组;
	 * @param obj
	 * @return
	 */
	public static SQLData saveSQLData(Object obj) {
		return camelSQL( obj, SQLFormatEnum.INSERT_INTO, false);
	}

	/**
	 * 保存或更新对象,只会保存生成对应数据值不为null的sql,返回插入或更新数据sql和排序好的对象集合数组;
	 * @param obj
	 * @return
	 */
	public static SQLData saveOrUpdateSQLData(Object obj){
		return camelSQL( obj, SQLFormatEnum.REPLACE_INTO,false);
	}


	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @param tableName :自定义的表名称;
	 * @return 创建表的sql语句
	 */
	public static String creatTableSQL(Class<?> obj,String tableName) throws Exception{
		return creatTableServer(obj.newInstance(), SQLFormatEnum.CREATE_TABLE, tableName);
	}

	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String creatTableSQL(Object  obj){
		return creatTableServer(obj,  SQLFormatEnum.CREATE_TABLE, null);
	}

	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String creatTableIdSQL(Object  obj){
		return creatTableServer(obj,  SQLFormatEnum.CREATE_TABLE_ID, null);
	}

	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String creatTableIdSQLClass(Class<?>  obj) throws Exception{
		return creatTableServer(obj.newInstance(),  SQLFormatEnum.CREATE_TABLE_ID, null);
	}
	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String creatTableSQL(Object  obj, SQLFormatEnum formatType, String tableName ){
		return creatTableServer(obj, formatType, tableName);
	}

	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String queryListSQL(Object obj){
		return querySQLServer(obj, SQLFormatEnum.SERLECT_LIST, null);
	}
	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @return 创建表的sql语句
	 */
	public static String queryTSQL(Object obj){
		return querySQLServer(obj, SQLFormatEnum.SERLECT_OBJECT, null);
	}
	/**
	 * 通过sql 格式实现
	 * @param obj 有值的要保存对象;
	 * @param valueIsNull : true时,生成全量字段的sql脚本; false时,生成只有值的字段的sql脚本;
	 * @return
	 */
	private static SQLData camelSQL(Object obj, SQLFormatEnum sqlFormatType, boolean valueIsNull){
		if(null == obj){
			 throw new RuntimeException("Object java.lang.Class obj is null");
		}
		String sql = "";
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			String tableName = StringFormat.underscoreName(obj.getClass().getSimpleName());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();


			StringBuilder insert = new StringBuilder();
			StringBuilder values = new StringBuilder();
			List<Object> list = new ArrayList<>();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (null != value || valueIsNull) {
						insert.append(",").append("`").append(StringFormat.underscoreName(key)).append("`");
						values.append(", ? ");
						if (value instanceof Boolean) {
							list.add(value.equals(true) ? 1 : 0);
						} else {
							list.add(value);
						}

					}

				}

			}

			if(insert.length() == 0 || values.length() == 0){
				return null;
			}
			sql = String.format(sqlFormatType.getValue(), tableName,
					insert.deleteCharAt(0), values.deleteCharAt(0));
			return new SQLData(sql, list.toArray());
		} catch (Exception e) {
			logger.error("SQLDbUtils camelSQL sql:{} ", sql);
			logger.error("SQLDbUtils camelSQL Exception :{} ", e);

		}
		return null;

	}


	/**
	 * 通过对象实现创建表的sql 格式语句
	 * @param obj 有值的要保存对象;
	 * @param sqlFormatType 保存或更新
	 * @param tableNames : true时,生成全量字段的sql脚本; false时,生成只有值的字段的sql脚本;
	 * @return
	 */
	private static String creatTableServer(Object obj, SQLFormatEnum sqlFormatType, String tableNames){
		if(null == obj){
			 throw new RuntimeException("Object java.lang.Class obj is null");
		}
		String sql = "";
		try {
			Class<?> entityClazz = obj.getClass();

			String tableName = StringFormat.underscoreName(entityClazz.getSimpleName());
			
			List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
			

			StringBuilder insert = new StringBuilder();
			
//			Field[] fds = claxx.getDeclaredFields();
//			if (claxx.getGenericSuperclass() != null) { //如果存在父类;
//		        Class<?> superClass = claxx.getSuperclass();// 父类
//		        Field[] supfds = superClass.getDeclaredFields();
//		        for (Field property : supfds) {
//		        	String key = property.getName();
//					String type = property.getType().getSimpleName();
//					// 过滤class属性
//					if (!key.equals("class")) {
//						// 得到property对应的getter方法
//						insert.append("`").append(StringFormat.underscoreName(key)).append("`")
//						.append(EnumsTableType.getKey(type.toUpperCase())).append(",");
//					}
//				}
//			}

			for (Field property : fieldList) {
				String key = property.getName();
				String type = property.getType().getSimpleName();
				boolean isEnum = property.getType().isEnum();
				if(isEnum){
					type = String.class.getSimpleName();
				}
				boolean isType = isTypeClass(property);
				// 过滤class属性
				if (!key.equals("class") && !isType ) {
					// 得到property对应的getter方法
					String clumn = StringFormat.underscoreName(key);
					insert.append("`").append(clumn).append("`");
					insert.append(TableTypeEnum.getKey(type.toUpperCase()));
					if(clumn.equals("id")){
						insert.append(" NOT NULL AUTO_INCREMENT");
					}
					if(clumn.endsWith("_date")){
						insert.append(" NOT NULL ");
					}
//					if(clumn.equals("global_id")){
//						insert.append(" DATETIME 0 ");
//					}
					ApiDesc apiDesc = property.getAnnotation(ApiDesc.class);
					if(null == apiDesc){
						insert.append(" COMMENT '',\n\t");

					}else {
						insert.append(" COMMENT '").append(apiDesc.value()).append("'").append(",\n\t ");
					}

				}

			}
			if(null != tableNames && !"".equals(tableNames)){
				tableName = tableNames;
			}
			sql = String.format(sqlFormatType.getValue(), tableName,
					insert.deleteCharAt(insert.length() -1 ));
//			System.out.println("creat Table "+tableName+" sql =:  \n " + sql);
//			System.out.println(" \n " + sql);
			return sql;
		} catch (Exception e) {
			logger.error("SQLDbUtils camelSQL sql:{} ", sql);
			logger.error("SQLDbUtils camelSQL Exception :{} ", e);

		}
		return null;

	}


	 /**
	  * 排除 泛型,扩展性,或静态,或final类型
	  * @param property
	  * @return
	  */
	 private static boolean isTypeClass(Field property) {
			boolean isStatic = Modifier.isStatic(property.getModifiers());
			boolean isFinal = Modifier.isFinal(property.getModifiers());
//			boolean isTyep = RedisSetEnum.isContains(property.getType().getSimpleName());
         return isStatic || isFinal;
     }

	/**
	 * 通过对象实现查询表的sql 格式语句
	 * @param obj 需要查询的对象;
	 * @param sqlFormatType 查询规范语句
	 * @param tableNames : null 或""时,生成默认对象表sql脚本; 否则按指定表名称生成查询SQL语句;
	 * @return
	 */
	private static String querySQLServer(Object obj, SQLFormatEnum sqlFormatType, String tableNames){
		if(null == obj){
			 throw new RuntimeException("Object java.lang.Class obj is null");
		}
		String sql = "";
		try {
			String tableName = StringFormat.underscoreName(obj.getClass().getSimpleName());
//			Field[] fds = obj.getClass().getDeclaredFields();
            List<Field> fds = FieldUtils.getAllFieldsList(obj.getClass());
			StringBuilder select = new StringBuilder();

//			if (obj.getClass().getGenericSuperclass() != null) { //如果存在父类;
//		        Class<?> superClass = obj.getClass().getSuperclass();// 父类
//		        Field[] supfds = superClass.getDeclaredFields();
//		        for (Field property : supfds) {
//					String key = property.getName();
////					String type = property.getType().getSimpleName();
//					// 过滤class属性
//					if (!key.equals("class")) {
//						// 得到property对应的getter方法
//						select.append("`").append(StringFormat.underscoreName(key)).append("`").append(",");
//					}
//
//				}
//			}

			for (Field property : fds) {
				String key = property.getName();
//				String type = property.getType().getSimpleName();
				boolean isType = isTypeClass(property);
				// 过滤class属性
				if (!key.equals("class") && !isType ) {
					// 得到property对应的getter方法
					select.append("`").append(StringFormat.underscoreName(key)).append("`").append(",");
				}

			}
			if(null != tableNames && "".equals(tableNames)){
				tableName = tableNames;
			}
			sql = String.format(sqlFormatType.getValue(),
					select.deleteCharAt(select.length() -1 ),tableName);

			return sql;
		} catch (Exception e) {
			logger.error("SQLDbUtils camelSQL sql:{} ", sql);
			logger.error("SQLDbUtils camelSQL Exception :{} ", e);

		}
		return null;

	}

	/**
	 * 生成jdbc映射对象类实现方法;
	 * @param klass 返回对象类
	 * @return
	 */
	public static <T>RowMapper<T> creatRowMapper(Class<T> klass){
		RowMapper<T> tMapper = new RowMapper<T>() {
			@Override
			public T mapRow(ResultSet rs, int rowNum) {
				T clazz = null;
				try {
					clazz = klass.newInstance();
					Field[] fds = klass.getDeclaredFields();
					setFields(rs, clazz, fds);
					if (klass.getGenericSuperclass() != null) { //如果存在父类;
				        Class<?> superClass = klass.getSuperclass();// 父类
				        Field[] supfds = superClass.getDeclaredFields();
				        setFields(rs, clazz, supfds);
					}


				} catch (Exception e) {
					logger.error("SQLDbUtils camelSQL Exception :{} ", e);

				}
				return clazz;
			}

			private void setFields(ResultSet rs, T clazz, Field[] fds)
					throws IllegalAccessException, SQLException {
				for (Field fd : fds) {
					if (!fd.isAccessible()) {
						fd.setAccessible(true);
					}
					String key = StringFormat.underscoreName(fd.getName());//转换成数据库表字段
					Class<?> classType = fd.getType();//getSimpleName();
					boolean isType = isTypeClass(fd);
					boolean isEnum = fd.getType().isEnum();
					// 过滤class属性
					if (!key.equals("class") && !isType ) {
						if(isEnum){
							Enum<?> enums = clsToEnum(classType, SqlTypeEnum.getEnumKey(rs, key));
							if(null != enums){
								fd.set(classType.getEnumConstants(), enums);
							}
						}else{
							fd.set(clazz, SqlTypeEnum.getKey(rs,classType, key));
						}
					}
				}
			}
		};
		return tMapper;

	}

	/**
     * 加载每个枚举对象数据
     * */
	
    private static Enum<?> clsToEnum(Class<?> cls,String name ){
        Method method;
		try {
			method = cls.getMethod("values");
			 Enum<?>[] inter = (Enum[]) method.invoke(cls.newInstance());
	        for (Enum<?> enums : inter) {
	        	if(enums.name().equals("name")){
	        		return enums;
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}

        return null;
    }




}
