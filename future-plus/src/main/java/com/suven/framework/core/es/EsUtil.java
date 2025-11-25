//package com.suven.framework.core.es;
//
//import com.suven.framework.http.api.IBaseApi;
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import com.suven.framework.util.json.JsonUtils;
//import org.elasticsearch.core.TimeValue;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.Requests;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//
//@Component
//@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_ES_ENABLED,  matchIfMissing = false)
//public class EsUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(EsUtil.class);
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//
//    /**
//     * @PostContruct是spring框架的注解 spring容器初始化的时候执行该方法
//     */
//
//
//    private static RestHighLevelClient esClient;
//
//    @PostConstruct
//    private void init() {
//        esClient = restHighLevelClient;
//    }
//
//
//    private static Settings settingsBuilder() {
//        return Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2).build();
//    }
//
//    /**
//     * 创建索引
//     *
//     * @param index
//     * @throws IOException
//     */
//    public static boolean createIndex(String index, Settings settingsBuilder) throws IOException {
//        try {
//            CreateIndexRequest request = new CreateIndexRequest(index);
//            if (settingsBuilder == null) {
//                settingsBuilder = settingsBuilder();
//            }
//            request.settings(settingsBuilder);
//            CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
//            if (null == createIndexResponse) {
//                return false;
//            }
//            logger.info("执行建立成功:[{}]", JsonUtils.toJson(createIndexResponse));
//            return createIndexResponse.isAcknowledged();
//        } catch (Exception e) {
//            logger.error("es index 创建出现异常 ", e);
//            return false;
//        }
//    }
//
//
//    /**
//     * 判断索引是否存在
//     *
//     * @param index
//     * @return
//     * @throws IOException
//     */
//    public static boolean isIndexExist(String index) throws IOException {
//        if (StringUtils.isBlank(index))
//            return false;
//        try {
//            GetIndexRequest request = new GetIndexRequest(index);
//            request.local(false).includeDefaults(false).humanReadable(true);
//            boolean exists = esClient.indices().exists(request, RequestOptions.DEFAULT);
//            logger.info("es exists Index::[{}]", index);
//            return exists;
//        } catch (Exception e) {
//            logger.error("indexExist error", e);
//        }
//        return false;
//    }
//
//    /**
//     * 传入index和id 判断文档是否存在
//     *
//     * @param index
//     * @param id
//     * @return
//     */
//    private boolean documentExist(String index, String id) {
//        if (StringUtils.isBlank(index) || StringUtils.isBlank(id))
//            return false;
//        try {
//            GetRequest getRequest = Requests.getRequest(index).id(id);
//            getRequest.fetchSourceContext(new FetchSourceContext(false));
//            getRequest.storedFields("_none_");
//            return esClient.exists(getRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            logger.error("documentExist error", e);
//        }
//        return false;
//    }
//
//
//    /**
//     * 删除索引
//     *
//     * @param index
//     * @return
//     */
//    public static boolean deleteIndex(String index) throws IOException {
//        if (!isIndexExist(index)) {
//            logger.info("Index[{}] : is not exits!", index);
//            return false;
//        }
//        DeleteIndexRequest request = Requests.deleteIndexRequest(index);
//        AcknowledgedResponse dResponse = esClient.indices().delete(request, RequestOptions.DEFAULT);
//        if (dResponse.isAcknowledged()) {
//            logger.info("delete index index :[{}]  successfully!", index);
//        } else {
//            logger.info("Fail to delete index :[{}]", index);
//        }
//        return dResponse.isAcknowledged();
//    }
//
//    /**
//     * 数据添加，正定ID
//     *
//     * @param entity 要增加的数据
//     * @param index  索引，类似数据库
//     * @return
//     * @throws IOException
//     */
//    public static String save(String index, IBaseApi entity) throws IOException {
//        if (StringUtils.isBlank(index) || null == entity){
//            return null;
//        }
//        IndexRequest indexRequest = Requests.indexRequest(index);//new IndexRequest(index,type);
//        indexRequest.id(entity.getEsId());
//        indexRequest.source(JsonUtils.toJson(entity), XContentType.JSON);
//        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
//        if (null == indexResponse) {
//            return null;
//        }
//        logger.info("addData response status:[{}],id:[{}]", indexResponse.status().getStatus(), indexResponse.getId());
//        return indexResponse.getId();
//
//
//    }
//
//
//    /**
//     * 批量保存
//     *
//     * @param index index索引名称
//     * @param list  要保存的list 对象
//     */
//    public boolean saveBatch(String index, List<IBaseApi> list) {
//        if (StringUtils.isBlank(index) || (list == null || list.isEmpty())){
//
//            return false;
//        }
//        try {
//            BulkRequest request = Requests.bulkRequest();
//            list.forEach(item -> request.add(Requests.indexRequest(index).id(item.getEsId())
//                    .source(JsonUtils.toJson(item), XContentType.JSON)));
//
//            esClient.bulk(request, RequestOptions.DEFAULT);
//            return true;
//        } catch (Exception e) {
//            logger.error("EsUtil saveBatch error :[{}]", e);
//        }
//        return false;
//    }
//
//
//    /**
//     * 通过ID 更新数据
//     *
//     * @param index  index索引名称
//     * @param entity 更新的对象
//     * @param index  索引，类似数据库
//     * @return
//     */
//    public static boolean updateById(String index, IBaseApi entity) throws IOException {
//        if (StringUtils.isBlank(index) || (entity == null || entity.getId() < 0)) {
//            return false;
//        }
//        try {
//
//            UpdateRequest updateRequest = new UpdateRequest();
//
//            updateRequest.index(index).id(entity.getEsId()).doc(JsonUtils.toJson(entity), XContentType.JSON);
//
//            esClient.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            logger.error("EsUtil updateById error :[{}]", e);
//        }
//        return false;
//    }
//
//    /**
//     * 根据id 删除
//     *
//     * @param index
//     * @param entity
//     * @return
//     */
//    public static boolean deleteById(String index, IBaseApi entity) {
//        if (StringUtils.isBlank(index) || null == entity || entity.getId() < 0){
//            return false;
//        }
//        try {
//            DeleteRequest request = Requests.deleteRequest(index).id(entity.getEsId());
//
//            DeleteResponse deleteResponse = esClient.delete(request, RequestOptions.DEFAULT);
//            if (null != deleteResponse && deleteResponse.status() == RestStatus.OK) {
//                logger.info(" EsUtil deleteById successfully！ by id: [{}]", entity.getId());
//                return true;
//            }
//        } catch (Exception e) {
//            logger.error("EsUtil deleteById error [{}]", e);
//        }
//        return false;
//    }
//
//
//    /**
//     * 通过ID删除数据
//     *
//     * @param index 索引，类似数据库
//     * @param id    数据ID
//     */
//    public static boolean delete(String index, long id) throws Exception {
//        if (StringUtils.isBlank(index) || id < 0)
//            return false;
//        try {
//            DeleteRequest request = Requests.deleteRequest(index).id(String.valueOf(id));
//            DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
//            if (null != response && response.status() == RestStatus.OK) {
//                logger.info(" EsUtil deleteById successfully！ by id: [{}]", id);
//                return true;
//            }
//        } catch (Exception e) {
//            logger.error("EsUtil deleteById error [{}]", e);
//        }
//        return false;
//    }
//
//    /**
//     * 批量删除
//     *
//     * @param index
//     * @param idList 要删除的id 列表
//     */
//    public static boolean deleteBatch(String index, Collection<IBaseApi> idList) {
//        if (StringUtils.isBlank(index) || (idList == null || idList.isEmpty()))
//            return false;
//        try {
//            BulkRequest request = Requests.bulkRequest();
//
//            idList.forEach(entity -> request.add(Requests.deleteRequest(index).id(entity.getEsId())));
//
//            esClient.bulk(request, RequestOptions.DEFAULT);
//            return true;
//        } catch (Exception e) {
//            logger.error(" EsUtil delete Batch error: [{}]", e);
//        }
//        return false;
//    }
//
//    /**
//     * 根据id 获取对象数据  get方式
//     *
//     * @param index
//     * @param id
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T getById(String index, long id, Class<T> clazz) {
//        if (null == index || id < 0 || null == clazz) {
//            return null;
//        }
//        try {
//            GetRequest getRequest = Requests.getRequest(index).id(String.valueOf(id));
//            GetResponse response = esClient.get(getRequest, RequestOptions.DEFAULT);
//            if (null != response && response.isExists()) {
//                String source = response.getSourceAsString();
//                T t = JsonUtils.parseObject(source, clazz);
//                return t;
//            }
//        } catch (Exception e) {
//            logger.error("EsUtil getById error : [{}]", e);
//        }
//        return null;
//
//    }
//
//    /**
//     * 根据搜索条件 查找数据，返回封装好的 list （对象为 c参数
//     *
//     * @param index
//     * @param builder 搜索对象构建
//     * @param clazz   接收的 对象
//     * @param <T>
//     * @return
//     */
////    public <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> clazz) {
////        if (StringUtils.isBlank(index))
////            return null;
////        try {
////            SearchRequest request = Requests.searchRequest(index).source(builder);
////
////            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
////
////            SearchHit[] hits = response.getHits().getHits();
////
////            List<T> res = new ArrayList<>(hits.length);
////
////            for (SearchHit hit : hits) {
////                res.add(JsonUtils.parseObject(hit.getSourceAsString(), clazz));
////            }
////            return res;
////        } catch (Exception e) {
////            logger.error("search error", e);
////        }
////        return null;
////    }
//
//
//    /**
//     * 根据搜索条件 直接返回 SearchResponse对象
//     *
//     * @param index
//     * @param builder
//     * @return
//     */
//    public static SearchResponse search(String index, SearchSourceBuilder builder) {
//        if (StringUtils.isBlank(index)) {
//            return null;
//        }
//        try {
//            SearchRequest request = Requests.searchRequest(index).source(builder);
//
//            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
//
//            return response;
//        } catch (Exception e) {
//            logger.error("search error", e);
//        }
//        return null;
//    }
//
//    /**
//     * 查找数据  不分页
//     *
//     * @param index
//     * @param query
//     * @return
//     * @throws IOException
//     */
//    public static EsPage searchDataPage(String index, QueryBuilder query) throws IOException {
//        return searchDataPage(index, 0, 100, query);
//    }
//
//    /**
//     * 查找数据  支持分页
//     *
//     * @param index
//     * @param startPage
//     * @param pageSize
//     * @param query
//     * @return
//     * @throws IOException
//     */
//    public static EsPage searchDataPage(String index, int startPage, int pageSize, QueryBuilder query) throws IOException {
//        return searchDataPage(index, startPage, pageSize, query, null, null, null, null);
//    }
//
//    /**
//     * 查找数据 支持分页，支持过滤显示字段，支持字段排序，支持高亮
//     *
//     * @param index          索引名称
//     * @param startPage      起始页
//     * @param pageSize       页大小
//     * @param query          查询条件
//     * @param fields         需要显示的字段，默认为全部字段
//     * @param sortField      排序字段
//     * @param orderType      排序类型
//     * @param highlightField 高亮字段
//     * @return
//     * @throws IOException
//     */
//    public static EsPage searchDataPage(String index, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, SortOrder orderType, String highlightField) throws IOException {
//        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
//
//        // 需要显示的字段，逗号分隔（缺省为全部字段）
//        if (StringUtils.isNotBlank(fields)) {
//            searchRequestBuilder.fetchSource(fields.split(","), null);
//        }
//        //排序字段
//        if (StringUtils.isNotBlank(sortField)) {
//            searchRequestBuilder.sort(sortField, orderType);
//        }
//        setHighlightField(searchRequestBuilder, highlightField);
//        searchRequestBuilder.query(QueryBuilders.matchAllQuery());
//        searchRequestBuilder.query(query);
//
//        // 分页应用
//        searchRequestBuilder.from(startPage).size(pageSize);
//        // 设置是否按查询匹配度排序
//        searchRequestBuilder.explain(true);
//        //打印的内容
//        logger.info("{}", searchRequestBuilder);
//        // 执行搜索,返回搜索响应信息
//        SearchResponse searchResponse = search(index, searchRequestBuilder);
//
//        shards(searchResponse);
//
//        //数据记录
//        SearchHits hits = searchResponse.getHits();
//        long totalHits = hits.getTotalHits().value;
//        long length = hits.getHits().length;
//        float maxScore = hits.getMaxScore();
//        logger.info("共查询到[{}]条数据,处理数据条数[{}],最高分数[{}]", totalHits, length, maxScore);
//
//        //搜索结果状态信息
//        RestStatus status = searchResponse.status();
//        TimeValue took = searchResponse.getTook();
//        logger.info("查询成功！ 用时{}毫秒", took.millis());
//        if (status == RestStatus.OK) {
//            // 解析对象
//            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);
//            //构建分页对象 并返回
//            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
//        }
//        return null;
//    }
//
//
//    /**
//     * 分片搜索情况
//     *
//     * @param searchResponse
//     */
//    private static void shards(SearchResponse searchResponse) {
//        int totalShards = searchResponse.getTotalShards();
//        int successfulShards = searchResponse.getSuccessfulShards();
//        int failedShards = searchResponse.getFailedShards();
//        logger.info("totalShards[{}],successfulShards[{}],failedShards[{}]", totalShards, successfulShards, failedShards);
//    }
//
//    /**
//     * 高亮设置
//     *
//     * @param searchRequestBuilder 搜索构建对象
//     * @param highlightField       高亮字段
//     */
//    private static void setHighlightField(SearchSourceBuilder searchRequestBuilder, String highlightField) {
//        //高亮设置
//        if (StringUtils.isNotBlank(highlightField)) {
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            highlightBuilder.preTags("<span style='color:red' >");
//            highlightBuilder.postTags("</span>");
//            highlightBuilder.field(highlightField);
//            searchRequestBuilder.highlighter(highlightBuilder);
//        }
//    }
//
//    /**
//     * 高亮结果集 特殊处理
//     *
//     * @param searchResponse 结果对象
//     * @param highlightField 高亮字段
//     */
//    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
//        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
//        StringBuffer stringBuffer = new StringBuffer();
//
//        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//            searchHit.getSourceAsMap().put("id", searchHit.getId());
//            if (StringUtils.isNotBlank(highlightField)) {
//                logger.info("遍历 高亮结果集，覆盖 正常结果集{}", searchHit.getSourceAsMap());
//                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();
//
//                if (text != null) {
//                    for (Text str : text) {
//                        stringBuffer.append(str.string());
//                    }
//                    //遍历 高亮结果集，覆盖 正常结果集
//                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
//                }
//            }
//            sourceList.add(searchHit.getSourceAsMap());
//        }
//        return sourceList;
//    }
//}
