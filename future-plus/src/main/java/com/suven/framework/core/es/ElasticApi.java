//package com.suven.framework.core.es;
//
//import com.alibaba.fastjson.JSON;
//import com.suven.framework.core.es.config.EsConfigSetting;
//import com.suven.framework.util.json.JsonUtils;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.elasticsearch.action.support.IndicesOptions;
//
//import javax.swing.*;
//import java.io.IOException;
//
///**
// * @author leien
// * @date 2020 7-19
// */
//@Component
//public class ElasticApi extends EsMappingParse {
//
//
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    @Autowired(required = false)
//    private EsConfigSetting esConfigSetting;
//
//    private Logger log = LoggerFactory.getLogger(ElasticApi.class);
//
//
//
//    protected RestHighLevelClient getEsClient(){
//        return restHighLevelClient;
//    }
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
//        request.settings(Settings.builder().put("index.number_of_shards", esConfigSetting.getNumberOfShards())
//                .put("index.number_of_replicas", esConfigSetting.getNumberOfReplicas()));
//    }
//    /**
//     * @param idxName 索引名称
//     * @param mapping 索引描述 {"dynamic":false,"properties":{"location_id":{"type":"long"},"flag":{"type":"text","index":true},"local_code":{"type":"text","index":true},"local_name":{"type":"text","index":true,"analyzer":"ik_max_word"},"lv":{"type":"long"},"sup_local_code":{"type":"text","index":true},"url":{"type":"text","index":true}}}
//     * @return void
//     * @throws
//     * @since
//     */
//    public void createIndex(String idxName, XContentBuilder mapping) {
//        try {
//
//            CreateIndexRequest request = new CreateIndexRequest(idxName);
//            buildSetting(request);
//            request.mapping(mapping);
//            CreateIndexResponse res = this.getEsClient().indices().create(request, RequestOptions.DEFAULT);
//            if (!res.isAcknowledged()) {
//                throw new RuntimeException("初始化失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
//
//    /**
//     * @param idxName 索引名称
//     * @param mappingJson 索引描述 {"dynamic":false,"properties":{"location_id":{"type":"long"},"flag":{"type":"text","index":true},"local_code":{"type":"text","index":true},"local_name":{"type":"text","index":true,"analyzer":"ik_max_word"},"lv":{"type":"long"},"sup_local_code":{"type":"text","index":true},"url":{"type":"text","index":true}}}
//     * @return void
//     * @throws
//     * @since
//     */
//    public void createIndex(String idxName, String mappingJson) {
//        try {
//
//            CreateIndexRequest request = new CreateIndexRequest(idxName);
//            buildSetting(request);
//
//            request.mapping(mappingJson, XContentType.JSON);
////            request.settings() 手工指定Setting
//            CreateIndexResponse res = this.getEsClient().indices().create(request, RequestOptions.DEFAULT);
//            if (!res.isAcknowledged()) {
//                throw new RuntimeException("初始化失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
//
//
//
//
//    /**
//     * 制定配置项的判断索引是否存在，注意与 isExistsIndex 区别
//     *
//     * @param idxName index名
//     * @return boolean
//     * @throws
//     * @since
//     */
//    public boolean indexExist(String idxName) throws Exception {
//        GetIndexRequest request = new GetIndexRequest(idxName);
//        //TRUE-返回本地信息检索状态，FALSE-还是从主节点检索状态
//        request.local(false);
//        //是否适应被人可读的格式返回
//        request.humanReadable(true);
//        //是否为每个索引返回所有默认设置
//        request.includeDefaults(false);
//        //控制如何解决不可用的索引以及如何扩展通配符表达式,忽略不可用索引的索引选项，仅将通配符扩展为开放索引，并且不允许从通配符表达式解析任何索引
//        request.indicesOptions(IndicesOptions.lenientExpandOpen());
//        return this.getEsClient().indices().exists(request, RequestOptions.DEFAULT);
//    }
//
//    /**
//     * 断某个index是否存在
//     *
//     * @param idxName index名
//     * @return boolean
//     * @throws
//     * @since
//     */
//    public boolean isExistsIndex(String idxName) throws Exception {
//        return this.getEsClient().indices().exists(new GetIndexRequest(idxName), RequestOptions.DEFAULT);
//    }
//
//
//
//    /**
//     * @param idxName index
//     * @param entity  对象
//     * @return void
//     * @throws
//     * @since
//     */
//    public  <T extends IBaseApi>void insertOrUpdateOne(String idxName, T entity) {
//
//        try {
//            IndexRequest request = new IndexRequest(idxName);
//
//            request.id(entity.getEsId());
//            request.source(JSON.toJSONString(entity), XContentType.JSON);
//            this.getEsClient().index(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            log.error("Data : id={},entity={}", entity.getId(), JSON.toJSONString(entity));
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    /**
//     * @param idxName index
//     * @param entity  对象
//     * @return void
//     * @throws
//     * @since
//     */
//    public  <T extends IBaseApi> boolean deleteOne(String idxName, T entity) {
//
//        try {DeleteRequest request = new DeleteRequest(idxName);
//            request.id(entity.getEsId());
//            DeleteResponse response = this.getEsClient().delete(request, RequestOptions.DEFAULT);
//            if(response != null){
//                return true;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
//
//    /**
//     * 批量插入数据
//     *
//     * @param idxName index
//     * @param list    带插入列表
//     * @return void
//     * @throws
//     * @since
//     */
//    public <T extends IBaseApi>void insertBatch(String idxName, List<T> list) {
//
//        try {
//            BulkRequest request = new BulkRequest();
//            list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getEsId())
//                .source(JSON.toJSONString(item), XContentType.JSON)));
//
//            this.getEsClient().bulk(request, RequestOptions.DEFAULT);
//
//        } catch (Exception e) {
//            System.err.println("数据写入es成功:"+list.size());
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 批量插入数据
//     *
//     * @param idxName index
//     * @param list    带插入列表
//     * @return void
//     * @throws
//     * @since
//     */
//    public <T extends IBaseApi>void insertBatchTrueObj(String idxName, List<T> list) {
//
//        try {
//            BulkRequest request = new BulkRequest();
//            list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getEsId())
//                .source(item, XContentType.JSON)));
//
//            this.getEsClient().bulk(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 批量删除
//     *
//     * @param idxName index
//     * @param idList  待删除列表
//     * @return void
//     * @throws
//     * @since
//     */
//    public <T> void deleteBatch(String idxName, Collection<T> idList) {
//        try {
//            BulkRequest request = new BulkRequest();
//            idList.forEach(item -> request.add(new DeleteRequest(idxName, item.toString())));
//
//            this.getEsClient().bulk(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 删除index
//     *
//     * @param idxName
//     * @return void
//     * @throws
//     * @since
//     */
//    public void deleteIndex(String idxName) {
//        try {
//            if (!this.indexExist(idxName)) {
//                log.error(" idxName={} 已经存在", idxName);
//                return;
//            }
//            this.getEsClient().indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    /**
//     * @param idxName
//     * @param builder
//     * @return void
//     * @throws
//     * @since
//     */
//    public void deleteByQuery(String idxName, QueryBuilder builder) {
//        try {
//
//            DeleteByQueryRequest request = new DeleteByQueryRequest(idxName);
//            request.setQuery(builder);
//            //设置批量操作数量,最大为10000
//            request.setBatchSize(10000);
//            request.setConflicts("proceed");
//
//            this.getEsClient().deleteByQuery(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @param idxName index
//     * @param builder 查询参数
//     * @param c       结果类对象
//     * @return java.util.List<T>
//     * @throws
//     * @since
//     */
//    public <T> List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) {
//
//        try {
//            SearchRequest request = new SearchRequest(idxName);
//            request.source(builder);
//            SearchResponse response = this.getEsClient().search(request, RequestOptions.DEFAULT);
//            SearchHit[] hits = response.getHits().getHits();
//            List<T> res = new ArrayList<>(hits.length);
//            for (SearchHit hit : hits) {
//                res.add(JSON.parseObject(hit.getSourceAsString(), c));
//            }
//            return res;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public EsSearchResult searchByPage(String idxName, SearchSourceBuilder builder, Class c) {
//        SearchRequest request = new SearchRequest(idxName);
//        EsSearchResult esSearchResult = EsSearchResult.build();
//        request.source(builder);
//        try {
//            SearchResponse response = this.getEsClient().search(request, RequestOptions.DEFAULT);
//            SearchHit[] hits = response.getHits().getHits();
//            List res = new ArrayList(hits.length);
//            for (SearchHit hit : hits) {
//                res.add(JSON.parseObject(hit.getSourceAsString(), c));
//            }
//            esSearchResult.toTotal(response.getHits().getTotalHits().value)
//            .toResult(res);
//
//
//            return esSearchResult;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /*  根据id 获取对象数据  get方式
//     *
//     * @param index
//     * @param id
//     * @param clazz
//     * @param <T>
//     * @return
//     * */
//    public <T> T getById(String index, long id, Class<T> clazz) {
//        if (null == index || id < 0 || null == clazz) {
//            return null;
//        }
//        try {
//
//            GetRequest getRequest = Requests.getRequest(index).id(String.valueOf(id));
//            GetResponse response = this.getEsClient().get(getRequest, RequestOptions.DEFAULT);
//            if (null != response && response.isExists()) {
//                String source = response.getSourceAsString();
//                T t = JsonUtils.parseObject(source, clazz);
//                return t;
//            }
//        } catch (Exception e) {
//            log.error("EsUtil getById error : [{}]", e);
//        }
//        return null;
//    }
//
//    /*
//     *
//     * 查找数据  不分页
//     *
//     * @param index
//     * @param query
//     * @return
//     * @throws IOException
//     */
//
//
//    public  EsSearchResult searchDataPage(String index, QueryBuilder query) throws IOException {
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
//
//
//    public  EsSearchResult searchDataPage(String index, int startPage, int pageSize, QueryBuilder query) throws IOException {
//        return searchDataPage(index, startPage, pageSize, query, null, null, null, null);
//    }
//
//    /*
//     *
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
//     * @throws IOException*/
//
//    public EsSearchResult searchDataPage(String index, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, SortOrder orderType, String highlightField) throws IOException {
//        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
//
//        // 需要显示的字段，逗号分隔（缺省为全部字段）
//        if (Objectt.isNotBlank(fields)) {
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
//        log.info("{}", searchRequestBuilder);
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
//        log.info("共查询到[{}]条数据,处理数据条数[{}],最高分数[{}]", totalHits, length, maxScore);
//
//        //搜索结果状态信息
//        RestStatus status = searchResponse.status();
//        TimeValue took = searchResponse.getTook();
//        log.info("查询成功！ 用时{}毫秒", took.millis());
//        if (status == RestStatus.OK) {
//            // 解析对象
//            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);
//            //构建分页对象 并返回
//            EsSearchResult  result = EsSearchResult.build().toCurrentPage(startPage)
//                    .toPageSize(pageSize).toRecordCount( (int)totalHits).toMaxScore(maxScore)
//            .toResultList(sourceList);
//            return result;
//        }
//        return null;
//    }
//
//    /*   *
//     * 根据搜索条件 直接返回 SearchResponse对象
//     *
//     * @param index
//     * @param builder
//     * @return*/
//
//
//    public SearchResponse search(String index, SearchSourceBuilder builder) {
//        if (StringUtils.isBlank(index)) {
//            return null;
//        }
//        try {
//            SearchRequest request = Requests.searchRequest(index).source(builder);
//
//            SearchResponse response = this.getEsClient().search(request, RequestOptions.DEFAULT);
//
//            return response;
//        } catch (Exception e) {
//            log.error("search error", e);
//        }
//        return null;
//    }
//
//    /**
//     * 分片搜索情况
//     *
//     * @param searchResponse
//     */
//    private void shards(SearchResponse searchResponse) {
//        int totalShards = searchResponse.getTotalShards();
//        int successfulShards = searchResponse.getSuccessfulShards();
//        int failedShards = searchResponse.getFailedShards();
//        log.info("totalShards[{}],successfulShards[{}],failedShards[{}]", totalShards, successfulShards, failedShards);
//    }
//
//    /*
//     *
//     * 高亮设置
//     *
//     * @param searchRequestBuilder 搜索构建对象
//     * @param highlightField       高亮字段
//     */
//
//
//    private void setHighlightField(SearchSourceBuilder searchRequestBuilder, String highlightField) {
//        //高亮设置
//        if (StringUtils.isNotBlank(highlightField)) {
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            highlightBuilder.preTags("<span style='color:red' >");
//            highlightBuilder.postTags("</span>");
//            highlightBuilder.field(highlightField);
//            searchRequestBuilder.highlighter(highlightBuilder);
//        }
//    }
//    /*
//     *
//     * 高亮结果集 特殊处理
//     *
//     * @param searchResponse 结果对象
//     * @param highlightField 高亮字段*/
//
//
//    private  List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
//        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
//        StringBuffer stringBuffer = new StringBuffer();
//
//        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//            searchHit.getSourceAsMap().put("id", searchHit.getId());
//            if (StringUtils.isNotBlank(highlightField)) {
//                log.info("遍历 高亮结果集，覆盖 正常结果集{}", searchHit.getSourceAsMap());
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
//
//    /**
//     * 分词
//     */
//    public String wordAnalyze(String txt) {
//
//       /* StringBuffer stringBuffer = new StringBuffer(txt);
//        stringBuffer.insert(1," ");*/
//        String text = "";
//        try {
//            AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer("ik_max_word",txt);
//            AnalyzeResponse response = null;
//            response = this.getEsClient().indices().analyze(request, RequestOptions.DEFAULT);
//            List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
//            if(tokens == null){
//                return null;
//            }
//            for(AnalyzeResponse.AnalyzeToken to : tokens){
//                text = text + to.getTerm().trim() + ",";
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return text;
//
//    }
//
//    public  <T extends IBaseApi> void  update(String idxName,T entity){
//        try {
//            UpdateRequest request = new UpdateRequest();
//            log.info("修改操作Data : id={},entity={}", entity.getId(), JSON.toJSONString(entity));
//            request.id(entity.getEsId());
//            request.doc(JSON.toJSONString(entity), XContentType.JSON);
//            request.index(idxName);
//            UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
//            if (update.status() == RestStatus.OK) {
//                log.info("修改成功！EsBaseEntity: {}", JSON.toJSONString(entity));
//                return ;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//
//        }
//    }
//}
