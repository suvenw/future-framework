//package com.suven.framework.core.es;
//
//import com.suven.framework.common.annotation.EsIk;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.elasticsearch.common.Strings;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.*;
//import java.util.Date;
//import java.util.List;
//
//public class EsMappingParse {
//
//    Logger logger = LoggerFactory.getLogger(EsMappingParse.class);
//
//
//
//    private final String PROPERTIES = "properties";
//
//    public XContentBuilder esMappingBuild(Class entityClazz ) throws Exception{
//
//
//        XContentBuilder mapping = XContentFactory.jsonBuilder();
//        mapping.startObject() // start root
//                .field("dynamic", "false")
//                .startObject(PROPERTIES);
//
//        //组合类实现
//        converterMappingBean(entityClazz,mapping);
//
//        mapping.endObject()
//                .endObject();
//        //创建mapping约束字段
//        String json = Strings.toString(mapping);
//        System.out.println(json);
//
//        return mapping;
//    }
//
//
//
////设置标题
////               .field("type", "string")
////                .field("store", "no")
////                .field("index", "analyzed")
////                .field("analyzer", "ik_smart")
//
//    /** 单个类实现 XContentBuilder 对象 **/
//    protected void converterMappingBean(Class entityClazz, XContentBuilder mapping )throws Exception{
//
//        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
//        for (Field field : fieldList) {
//            EsIk esIk = field.getAnnotation(EsIk.class);
//            Class<?> fieldType = field.getType();
//            String title =  field.getName();
//            boolean isEnum = field.getType().isEnum();
//            boolean isFinal = isFinal(field);
//            if (isEnum || isFinal){
//                logger.warn("Class[{}] to Elasticsearch  fieldType [{}] isEnum or isFinal, do not support, please to class object ",entityClazz,fieldType );
//                continue;
//            }
//            boolean isPrimitiveExt = isSimpleType(fieldType);
//            if(isPrimitiveExt){//数据类型的实现
//
//                String esType =  getElasticSearchMappingType(field);
//                mapping.startObject(title);
//                if(null != esIk){
//                    mapping.field("type", esIk.type());
//                    if(!"".equals(esIk.index())){
//                        mapping .field("index", esIk.index());
//                    }
//                    if(!"".equals(esIk.analyzer()) ){
//                        mapping.field("analyzer", esIk.analyzer());
//                    }
//                    if(!"".equals(esIk.search_analyzer())){
//                        mapping.field("search_analyzer", esIk.search_analyzer());
//                    }
//                    if("true".equals(esIk.store())){
//                        mapping .field("store", esIk.store());
//                    }
//                }else {
//                    mapping.field("type",esType);
//                }
//                mapping.endObject();
//                continue;
//            }
//            if(fieldType == Date.class){
//                mapping.startObject(title);
//                if(null != esIk){
//                    mapping.field("type", esIk.type());
//                    mapping.field("format", esIk.format());
//                    if(!"".equals(esIk.index())){
//                        mapping .field("index", esIk.index());
//                    }
//                    if("true".equals(esIk.store())){
//                        mapping .field("store", esIk.store());
//                    }
//                }else {
//                    mapping.field("type", "date");
//                }
//                mapping.endObject();
//                continue;
//            }
//
//           //如果是java 类处理,继承指定的接口类 IEsIndex
//            boolean isResolver = IEsIndex.class.isAssignableFrom(fieldType);
//            if(isResolver){
//                fieldBeanMapping(fieldType, title,mapping);
//            }//未知数据统一处理
//            else{
//                mapping.startObject(title);
//                String esType =  getElasticSearchMappingType(field);
//                mapping.field("type",esType);
//                mapping.endObject();
//            }
//        }
//        //结束当前类属性
//        System.out.println("-----------" );
//
//    }
//
//    /**
//     * 排除 泛型,扩展性,或静态,或final类型
//     * @param property
//     * @return
//     */
//    private  boolean isTypeClass(Field property) {
//        boolean isStatic = Modifier.isStatic(property.getModifiers());
//        boolean isFinal = Modifier.isFinal(property.getModifiers());
//        return isStatic || isFinal;
//    }
//
//
//    /**
//     * 排除 泛型,扩展性,或静态,或final类型
//     * @param property
//     * @return
//     */
//    private  boolean isFinal(Field property) {
//        boolean isFinal = Modifier.isFinal(property.getModifiers());
//        return  isFinal;
//    }
//
//
//
//    private  boolean isSimpleType(Class<?> fieldType) {
//        return ClassUtils.isPrimitiveOrWrapper(fieldType)
//                || fieldType == String.class;
//    }
//    /**
//     * java类型与ElasticSearch类型映射
//     * integer,long,short,byte double,float
//     * @return
//     */
//    private  String getElasticSearchMappingType(Field field) {
//        String varType = field.getType().getSimpleName().toLowerCase();
//        String es;
//        switch (varType) {
//
//            case "byte":
//                es = "byte";
//                break;
//            case "char":
//                es = "short";
//                break;
//            case "short":
//                es = "short";
//                break;
//            case "int":
//                es = "integer";
//                break;
//            case "integer":
//                es = "integer";
//                break;
//            case "long":
//                es = "long";
//                break;
//            case "float":
//                es = "float";
//                break;
//            case "double":
//                es = "double";
//                break;
//            case "date":
//                es = "date";
//                break;
//            case "string":
//                es = "text";
//                break;
//            default:
//                es = "text";
//                break;
//        }
//        return es;
//
//    }
//
//    private void fieldBeanMapping(Class entityClazz, String fieldName , XContentBuilder mapping ) throws Exception{
//        mapping.startObject(fieldName).startObject(PROPERTIES);
//        converterMappingBean(entityClazz,mapping);
//        mapping.endObject().endObject();
//    }
//
//
//
//    /**
//     * 设置分片
//     *
//     * @param request
//     * @return void
//     * @throws
//     * @author WCNGS@QQ.COM
//     * @See
//     * @date 2019/10/17 19:27
//     * @since
//     */
//    public void buildSetting(CreateIndexRequest request) {
//        request.settings(Settings.builder().put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2));
//    }
//
//
////
//}
