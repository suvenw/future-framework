package com.suven.framework.core.db.ext;

import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.core.db.DataSourceGroup;
import com.suven.framework.core.db.DataSourceHolder;
import com.suven.framework.core.db.DataSourceTypeEnum;
import com.suven.framework.core.db.druid.DruidDatasourceGroup;
import com.suven.framework.core.db.druid.DruidDynamicDataSource;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author suven.wang
 * @version 1.00.00
 * 
 * <pre>
 * 
 * </pre>
 */
@Component
public class DSClassAnnoExplain {
	private static Map<String, String> dsMap = new LinkedHashMap<>();
	private static Logger logger = LoggerFactory.getLogger(DSClassAnnoExplain.class);


	@PostConstruct
	public static void init() {
		Class<DS> clazz = DS.class;
		Set<Class<?>> classSet = ReflectionsScan.reflections.getTypesAnnotatedWith(clazz);
		for (Class<?> cls : classSet) {
			annotatedClass(cls,clazz);
		}


	}
//
//	public static void dataSource(String dsKey){
//		dsMap.get(dsKey);
//	}

	/**
	 * 通过java 类上传的@DS标注,查找到对应的数据库组的实现方法
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T extends IBaseApi> void getDatasourceTransactional(Class<?> clazz){
		DataSourceGroup dataSourceGroup = getDataSourceGroupByClass(clazz);
		if(dataSourceGroup == null){
			return;
		}
		dataSourceGroup.setDataType(DataSourceTypeEnum.MASTER);
		DataSourceHolder.putDataSource(dataSourceGroup);

	}


	/**
	 * 通过java 类上传的@DS标注,查找到对应的数据库组的实现方法
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T extends IBaseApi> DataSourceGroup getDataSourceGroupByClass(Class<?> clazz){
		if(null == clazz || dsMap.isEmpty()){
			logger.warn("Please fill in the @DS tag on the class ["+ clazz +"],which must Module Class<T> extends IBaseApi ,AbstractRoutingDataSource use determineCurrentLookupKey is null  ");
			return null;
		}
		String className  = clazz.getName();
		String dataSourceGroupModuleKey = dsMap.get(className);
		if( null == dataSourceGroupModuleKey ){
			logger.warn("Please fill in the @DS tag on the class ["+ clazz +"],which must Module Class<T> extends IBaseApi ,AbstractRoutingDataSource use determineCurrentLookupKey is null ");
			return null;
		}
		DruidDatasourceGroup druidDatasourceGroup = DruidDynamicDataSource.getInstance().getDruidDatasourceGroup(dataSourceGroupModuleKey);
		if(druidDatasourceGroup != null){
			return DataSourceGroup.build(druidDatasourceGroup);
		}
		//兼容旧版本
		return  null;
	}


	/**
	 * 组装每个项目key
	 * 
	 * @param clazz
	 * @return
	 */
	private static void annotatedClass( Class<?> clazz,Class<DS> annotationClass) {
		DS ds = AnnotationUtils.findAnnotation(clazz,annotationClass);
		if (ds == null || null == ds.dataSource() || "".equals(ds.dataSource())) {
			return ;
		}
		String className = clazz.getName();
		if(dsMap.keySet().contains(className)){
			throw  new RuntimeException("TioHandler command  is Already existed, value["+className+"]" );
		}
		dsMap.put(className,ds.dataSource());


	}


}
