//package com.suven.framework.core.es;
//
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//
//
//
///**
// * es java客户端 RestHighLevelClient对象构建
// * <p>
// * Java REST Client 有两种风格：
// * <p>
// * Java Low Level REST Client ：用于Elasticsearch的官方低级客户端。它允许通过http与Elasticsearch集群通信。将请求编排和响应反编排留给用户自己处理。它兼容所有的Elasticsearch版本。
// * Java High Level REST Client ：用于Elasticsearch的官方高级客户端。它是基于低级客户端的，它提供很多API，并负责请求的编排与响应的反编排。
// * （PS：所谓低级与高级，我觉得一个很形象的比喻是，面向过程编程与面向对象编程）
// * <p>
// * 在 Elasticsearch 7.0 中不建议使用TransportClient，并且在8.0中会完全删除TransportClient。
// * <p>
// * 因此，官方更建议我们用Java High Level REST Client，它执行HTTP请求，而不是序列号的Java请求。既然如此，这里就直接用高级了。
// *
// * @Author dongxie
// * @Description //TODO
// * @CreateDate 2019-09-11  14:59
// **/
//
///**
// *  配置文件 配置属性
// * top.es.nodes=47.107.116.210:9200,47.107.116.210:9200
// * top.es.schema=http
// *
// * top.es.max-connect-total=50
// * top.es.max-connect-per-route=10
// * top.es.connection-request-timeout-millis=500
// * top.es.socket-timeout-millis=30000
// * top.es.connect-timeout-millis=1000
// */
//@Configuration
//@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_ES_ENABLED,  matchIfMissing = false)
//@ConfigurationProperties(value = GlobalConfigConstants.TOP_SERVER_ES)
//public class EsConfigSetting {
//
//    private static final Logger logger = LoggerFactory.getLogger(EsConfigSetting.class);
//
//    /**  连接的集群地址  IP+端口 如有多个用,隔开 */
//    private String nodes;// 集群地址，多个用,隔开 eg:top.es.nodes=47.107.116.210:9200,47.107.116.210:9200
//
//    /**  索引名称 */
//    private String indexName = "index";// 使用的协议
//
//    /**  分片数据 */
//    private int numberOfShards = 3;
//    /**  复本数据  */
//    private int numberOfReplicas = 2;
//    /**  协议 http */
//    private String schema = "http";// 使用的协议
//    /** 最大连接数 */
//    private  int maxConnectTotal = 100; // 最大连接数
//    /** 最大路由连接数 */
//    private  int maxConnectPerRoute = 100; // 最大路由连接数
//
//    /** 连接超时时间  （默认为1秒）*/
//    private  int connectTimeoutMillis = 1000; // 连接超时时间
//    /** 连接超时时间  （默认为30秒） */
//    private  int socketTimeoutMillis = 30000; // 连接超时时间
//    /** 获取连接的超时时间 */
//    private  int connectionRequestTimeoutMillis = 500; // 获取连接的超时时间
//
//
//    public String getIndexName() {
//        return indexName;
//    }
//
//    public void setIndexName(String indexName) {
//        this.indexName = indexName;
//    }
//
//    public int getNumberOfShards() {
//        return numberOfShards;
//    }
//
//    public void setNumberOfShards(int numberOfShards) {
//        this.numberOfShards = numberOfShards;
//    }
//
//    public int getNumberOfReplicas() {
//        return numberOfReplicas;
//    }
//
//    public void setNumberOfReplicas(int numberOfReplicas) {
//        this.numberOfReplicas = numberOfReplicas;
//    }
//
//    public String getNodes() {
//        return nodes;
//    }
//
//    public void setNodes(String nodes) {
//        this.nodes = nodes;
//    }
//
//    public String getSchema() {
//        return schema;
//    }
//
//    public void setSchema(String schema) {
//        this.schema = schema;
//    }
//
//
//    public int getMaxConnectTotal() {
//        return maxConnectTotal;
//    }
//
//    public void setMaxConnectTotal(int maxConnectTotal) {
//        this.maxConnectTotal = maxConnectTotal;
//    }
//
//    public int getMaxConnectPerRoute() {
//        return maxConnectPerRoute;
//    }
//
//    public void setMaxConnectPerRoute(int maxConnectPerRoute) {
//        this.maxConnectPerRoute = maxConnectPerRoute;
//    }
//
//    public int getConnectTimeoutMillis() {
//        return connectTimeoutMillis;
//    }
//
//    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
//        this.connectTimeoutMillis = connectTimeoutMillis;
//    }
//
//    public int getSocketTimeoutMillis() {
//        return socketTimeoutMillis;
//    }
//
//    public void setSocketTimeoutMillis(int socketTimeoutMillis) {
//        this.socketTimeoutMillis = socketTimeoutMillis;
//    }
//
//    public int getConnectionRequestTimeoutMillis() {
//        return connectionRequestTimeoutMillis;
//    }
//
//    public void setConnectionRequestTimeoutMillis(int connectionRequestTimeoutMillis) {
//        this.connectionRequestTimeoutMillis = connectionRequestTimeoutMillis;
//    }
//}
