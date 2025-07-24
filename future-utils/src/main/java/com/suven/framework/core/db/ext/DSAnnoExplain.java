//package top.suven.future.core.db.ext;
//
//import com.suven.framework.common.constants.ReflectionsScan;
//import com.suven.framework.core.db.DataSourceGroup;
//import com.suven.framework.core.db.DataSourceGroupEnumInterface;
//import com.suven.framework.core.db.DataSourceHolder;
//import com.suven.framework.core.db.DatasourceEnumManager;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import javax.annotation.PostConstruct;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Set;
//
//
///**
// *
// * <pre>
// *
// * </pre>
// *
// * @author suven.wang
// * @version 1.00.00
// *
// * <pre>
// *
// * </pre>
// */
//public class DSAnnoExplain {
//	private static Map<String, Class<?>> dsMap = new LinkedHashMap<>();
//	private static Logger logger = LoggerFactory.getLogger(DSAnnoExplain.class);
//
//
//	@PostConstruct
//	public static void init() {
//		Class<DS> clazz = DS.class;
//		Set<Class<?>> classSet = ReflectionsScan.reflections.getTypesAnnotatedWith(clazz);
//		for (Class<?> cls : classSet) {
//			annoHandler(cls,clazz);
//		}
//
//
//	}
//
//	public static void initDatasource(){
//		try {
//			if(dsMap.isEmpty())return;
//			String str = dsMap.keySet().iterator().next();
//			if(str == null)return;
//			Class claxx = dsMap.get(str);
//			Object o = MethodUtils.invokeExactMethod(claxx, "initDataSourceGroupName");
//			if (o == null){
//			    return;
//            }
//			DataSourceGroupEnumInterface groupName = (DataSourceGroupEnumInterface)o;
//            DataSourceGroup dataSourceGroup =   DatasourceEnumManager.getDataSourceGroupByEnumName(groupName.name());
//			DataSourceHolder.putDataSource(dataSourceGroup);
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			logger.error("DatasourceTransactionalConfig initDatasource Exception[{}]", e.getMessage());
//			e.printStackTrace();
//		}
//
//
//	}
//
//
//	/**
//	 * 组装每个项目key
//	 *
//	 * @param cls
//	 * @return
//	 */
//	private static void annoHandler( Class<?> cls,Class<DS> clazz) {
//		DS ds = AnnotationUtils.findAnnotation(cls,clazz);
//		if (ds == null) {
//			return ;
//		}
//		build(ds.dataSource(),dsMap,cls);
//
//	}
//	private static void build(String cmd, Map map,Class<?> cls){
//		if ( cmd == null) {
//			return;
//		}
//		if(map.keySet().contains(cmd)){
//			throw  new RuntimeException("TioHandler command  is Already existed, value["+cmd+"]" );
//		}
//		map.put(cmd,cls);
//
//	}
//
//}
