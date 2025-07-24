package com.suven.framework.core.db.druid;

import com.suven.framework.core.db.*;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.random.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import java.util.*;


/**
 * 获取数据源
 *
 * @author joven
 * @version 2018/6/15 13:18
 */

/**
 * 将 DataSourceGroupNameEnum 数据源组 生成bean对象注入到spring管理容器中；
 * 通过 datasource_druid_enabled 对应模块数据库配置总开关，默认值为true
 * 通过 datasource_druid_slave_enabled 对应模块从数据库配置总开关，默认值为true
 * 将模块初始化后数据源信息，并将结果生成spring bean 名称缓存到DataSourceGroupNameEnum对象中
 * 将所有模块通过initDataByGroup 初始化后的数据库的聚群，初始化到目标的动态数据池子里；
 * 再根据模块名称字符串 作为模块key 从targetDataSources 的Map中获取指定模块的数据库的连接池；
 * 从而通过动态桥接的设计模式来达到数据源连接池动态切换原理；
 */
public class DruidDynamicDataSource extends AbstractRoutingDataSource {

	private final Logger logger = LoggerFactory.getLogger(getClass());



	private boolean isDefaultTargetDataSource = true;

	private Map<String, DruidDatasourceGroup> map = new LinkedHashMap<>();

	private Map<Object, Object> targetDataSources = new LinkedHashMap<>();


	public Map<Object, Object> getTargetDataSources(){
		return targetDataSources;
	}

	public Object getTargetDataSources(String dataSourcesKey){
		return targetDataSources.get(dataSourcesKey);
	}

	/**注入到spring bean的名称生成规则；（模块文称+ master）*/
	public Object putTargetDataSources(String dataSourceKey, Object dataSourceValue){
		return targetDataSources.putIfAbsent(dataSourceKey,dataSourceValue);
	}

	public void initDataSourceGroup(String modelName ,DruidDatasourceGroup  group){
		map.put(modelName,group);
	}


	public DruidDatasourceGroup getDruidDatasourceGroup(String modelName){
		return map.get(modelName);
	}
	/**优点：避免了线程不安全，延迟加载，效率高。
	 静态内部类的方式利用了类装载机制来保证线程安全，只有在第一次调用getInstance方法时，
	 才会装载SingletonInstance内部类，完成Singleton的实例化，所以也有懒加载的效果。 **/
	private DruidDynamicDataSource() {}

	private static class DynamicDataSourceInstance {
		private static final DruidDynamicDataSource INSTANCE = new DruidDynamicDataSource();
	}

	public static DruidDynamicDataSource getInstance() {
		return DynamicDataSourceInstance.INSTANCE;
	}

	/**
	 * 将所有模块通过initDataByGroup 初始化后的数据库的聚群，初始化到目标的动态数据池子里；
	 */
	public void setTargetDataSources(){
		super.setTargetDataSources(targetDataSources);
	}



	/**
	 * 获取数据源名称,采用轮询的方式实现
	 * 1.如果没有设置 数据库模块切换值,则按初始化时默认的数据库处理;
	 * 2.如果有设置
	 *  2.1.数据库模块切换值如果是主数据库, 即返回当前模块的主数据对应的key
	 *  2.2.数据库模块切换值如果是从数据库, 先判断从数据集合是否包括从数据key,如果没有则返回主数据库key, 否则轮询返回从数据库key
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		try{
			DataSourceGroup ds = DataSourceHolder.getDataSource();
			/**
			 * 如果没有设置 数据库模块切换值,则按初始化时默认的数据库处理;
			 */
			if (null == ds) {
				logger.warn("Property 'determineCurrentLookupKey' is DataSourceHolder.getDataSource() get DataSourceGroup is null .... ");
				return null;
			}
			String dataSourceKey = this.getDatasourceMap(ds.getGroupName(),ds.getDataType());

//			logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum  DataSourceGroup [{}]" ,dataSourceKey);
			logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum  " );
			return dataSourceKey;
		}catch (Exception e){
			e.printStackTrace();
			logger.warn("AbstractRoutingDataSource determineCurrentLookupKey  DataSourceGroup exception :[{}]", e);
		}
		return null;


	}



	private String getDatasourceMap(String moduleName, DataSourceTypeEnum dataSourceTypeEnum){
		if(null == dataSourceTypeEnum){
			dataSourceTypeEnum = DataSourceTypeEnum.MASTER;
		}
		String datasourceMasterName = DataSourceTypeEnum.MASTER.name().toLowerCase();
		String dataSourceSlaveName = DataSourceTypeEnum.SLAVE.name().toLowerCase();
		/**
		 * 注入到spring bean的名称生成规则；（模块文称+ master）
		 */
		String datasourceMasterBeanName = StringUtils.join(Arrays.asList(moduleName, datasourceMasterName), "_");
		if(dataSourceTypeEnum.equals(DataSourceTypeEnum.MASTER)){
			return datasourceMasterBeanName;
		}else {
			DruidDatasourceGroup dataSourceGroup =  map.get(moduleName);
			List<DataSourceConnectionInfo>  slaveList = dataSourceGroup.getSlave();
			if (null == slaveList || slaveList.isEmpty()) {
//				logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE from MASTER DataSourceGroup [{}]" , JsonUtils.toJson(dataSourceGroup));
				logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE from MASTER " );
				return datasourceMasterBeanName;
			}
			int size = slaveList.size();
			//随机获取;
			int index = size == 1 ? 0 :RandomUtils.num(size);
			String datasourceSlaveBeanName = builderString(moduleName, dataSourceSlaveName,  index);
			return datasourceSlaveBeanName;
		}
	}

	public static String builderString(Object ... params){
		String datasourceSlaveBeanName = StringUtils.join(Arrays.asList(params), "_");
		return datasourceSlaveBeanName;
	}



}
