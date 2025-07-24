//package com.suven.framework.core.es;
//
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.List;
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
// * @author xxx.xxx
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
//@ConditionalOnClass({EsConfigSetting.class})
//public class EsBeanAutoConfiguration {
//
//    private static final Logger logger = LoggerFactory.getLogger(EsBeanAutoConfiguration.class);
//
//    /**  连接的集群地址  IP+端口 如有多个用,隔开 */
//
//    @Autowired(required = false)
//    private EsConfigSetting esConfigSetting;
//
//    List<HttpHost>  hosts = null;
//
//    @PostConstruct
//    @ConditionalOnBean({EsConfigSetting.class})
//    public void init(){
//        hosts = new ArrayList<>();
//        if(esConfigSetting == null || esConfigSetting.getNodes() == null){
//            throw new RuntimeException(" please Initialize the ES configuration property ,because nodes is not null");
//        }
//        String nodes = esConfigSetting.getNodes();
//        String[] ipPorts = nodes.split(",|;");
//        for (String ipPort : ipPorts) {
//            try {
//                String[] hostPort =  ipPort.split(":",2);
//                hosts.add(new HttpHost(hostPort[0],Integer.parseInt(hostPort[1]) , esConfigSetting.getSchema()));
//            }catch (Exception ex){
//                logger.error("Invalid ES nodes  : [{}] property, Exception :[{}]", nodes, ex);
//                throw new IllegalStateException("Invalid ES nodes property "+ nodes +" Exception :" + ex);
//            }
//
//        }
//
//    }
//
//    @Bean(name = "restHighLevelClient")
//    @ConditionalOnBean({EsConfigSetting.class})
//    public RestHighLevelClient esClient() {
//
//        if(null == hosts || hosts.isEmpty()){
//            throw new RuntimeException(" please Initialize the ES configuration property, hosts is empty ");
//        }
//        RestClientBuilder builder = RestClient.builder(hosts.toArray(new HttpHost[0]));
//        // 异步httpclient连接延时配置
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(esConfigSetting.getConnectTimeoutMillis());
//            requestConfigBuilder.setSocketTimeout(esConfigSetting.getSocketTimeoutMillis());
//            requestConfigBuilder.setConnectionRequestTimeout(esConfigSetting.getConnectionRequestTimeoutMillis());
//            return requestConfigBuilder;
//        });
//        // 异步httpclient连接数配置
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//                httpClientBuilder.setMaxConnTotal(esConfigSetting.getMaxConnectTotal());
//                httpClientBuilder.setMaxConnPerRoute(esConfigSetting.getMaxConnectPerRoute());
//                return httpClientBuilder;
//        });
//        RestHighLevelClient client = new RestHighLevelClient(builder);
//        return client;
//    }
//
//
//}
