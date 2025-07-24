//package com.suven.framework.core.es;
//
//import com.suven.framework.core.es.model.UserTest;
//import com.suven.framework.core.es.repository.ElasticsearchRepository;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.Avg;
//import org.elasticsearch.search.aggregations.metrics.Stats;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.ScoreSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author xxx.xxx
// * Description: es公共api代码例子，类似于操作数据库的 DAO类
// * @CreateDate 2019-09-12  15:06
// **/
//@Component
//public class UserTestRepository extends ElasticsearchRepository<UserTest> {
//
//
//    private Logger logger = LoggerFactory.getLogger(UserTestRepository.class);
//
//
//    /**
//     * 保存
//     *
//     * @param userTest userTest
//     */
//    public boolean saveUserTest(UserTest userTest) {
//        if (null == userTest){
//            return false;
//        }
//
//        EsBaseEntity<UserTest> entity = new EsBaseEntity<>(userTest.getId(), userTest);
//        return this.save(INDEX_NAME, entity);
//    }
//
//    /**
//     * 批量保存
//     *
//     * @param users users
//     */
//    public boolean saveUserTestList(List<UserTest> users) {
//        if (users == null || users.isEmpty()){
//            return false;
//        }
//        List<EsBaseEntity> list = new ArrayList<>();
//        users.forEach(item -> list.add(new EsBaseEntity<>(item.getId(), item)));
//        return this.saveBatch(INDEX_NAME, list);
//    }
//
//
//    /**
//     * 更新数据
//     *
//     * @param userTest
//     * @return
//     */
//    public boolean updateUserTest(UserTest userTest) {
//        if (null == userTest)
//            return false;
//        EsBaseEntity<UserTest> entity = new EsBaseEntity<>(userTest.getId(), userTest);
//        return this.update(INDEX_NAME, entity);
//    }
//
//    /**
//     * 根据id 删除数据
//     *
//     * @param id
//     * @return
//     */
//    public boolean deleteUserTestById(String id) {
//        return this.deleteById(INDEX_NAME, id);
//    }
//
//    /**
//     * 根据id 获取对象数据
//     *
//     * @param id
//     * @return
//     */
//    public UserTest getUserTestById(String id) {
//        return this.getById(INDEX_NAME, id, UserTest.class);
//    }
//
//
//    /**
//     * 批量删除
//     *
//     * @param list list
//     */
//    public boolean deleteBatch(List<String> list) {
//        if (list == null || list.isEmpty())
//            return false;
//        return this.deleteBatch(INDEX_NAME, list);
//    }
//
//    /**
//     * 根据关键词搜索 ， 支持分页， 设置执行超时时间
//     *
//     * @param
//     */
//    public List<UserTest> getUserTestListByPage(EsPage page, String name, String context, String userId, String sex, Integer gte, Integer lte) {
//        if (null == page) {
//            return null;
//        }
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //构建查询参数对象
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//
//        if (StringUtils.isNotBlank(name)) {
//            //模糊查询
//            boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
//        }
//        if (StringUtils.isNotBlank(context)) {
//            //模糊查询
//            boolQueryBuilder.must(QueryBuilders.matchQuery("context", context));
//        }
//        if (StringUtils.isNotBlank(userId)) {
//            //精确查询
//            boolQueryBuilder.must(QueryBuilders.termQuery("userId", userId));
//        }
//        if (StringUtils.isNotBlank(sex)) {
//            //精确查询
//            boolQueryBuilder.must(QueryBuilders.termQuery("sex", sex));
//        }
//
//        //范围的过滤条件 比如 年龄 18-60岁
//        if (gte != null || lte != null) {
//            RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
//            if (gte != null) {
//                rangeQueryBuilder.gte(gte);
//            }
//            if (lte != null) {
//                rangeQueryBuilder.lte(lte);
//            }
//            boolQueryBuilder.filter().add(rangeQueryBuilder);
//        }
//
//
//        builder.from(page.getStart()).size(page.getPageSize());
//        builder.query(boolQueryBuilder);
//        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        return this.search(INDEX_NAME, builder, UserTest.class);
//
//    }
//
//
//    /**
//     * 执行全文检索 Multi Match Query
//     *
//     * @param query
//     * @return
//     */
//    public List<UserTest> multiBatchQuery(EsPage page, String query) {
//        //查询参数
//        MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(query);
//
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //分页
//        builder.from(page.getStart()).size(page.getPageSize());
//        builder.query(queryBuilder);
//        return this.search(INDEX_NAME, builder, UserTest.class);
//
//    }
//
//
//    /**
//     * 同一个值 进行在多字段检索 (Multi-field Search)
//     *
//     * @param query
//     * @return
//     */
//    public List<UserTest> multiFieldQuery(EsPage page, String query) {
//        MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(query).field("name").field("content");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //分页
//        builder.from(page.getStart()).size(page.getPageSize());
//        builder.query(queryBuilder);
//        return this.search(INDEX_NAME, builder, UserTest.class);
//    }
//
//
//    /**
//     * Wildcard Query 通配符检索
//     * 要查找具有以 "t" 字母开头的作者的所有记录
//     */
//    public List<UserTest> wildcardQuery(EsPage page, String fieldName, String pattern) {
//        WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder(fieldName, pattern);
//
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //分页
//        builder.from(page.getStart()).size(page.getPageSize());
//        builder.query(wildcardQueryBuilder);
//        return this.search(INDEX_NAME, builder, UserTest.class);
//    }
//
//    /**
//     * 正则表达式检索( Regexp Query)
//     * = "t[a-z]*y";regexp
//     *
//     * @param fieldName
//     * @param regexp
//     * @return
//     */
//    public List<UserTest> regexpQuery(EsPage page, String fieldName, String regexp) {
//        RegexpQueryBuilder queryBuilder = new RegexpQueryBuilder(fieldName, regexp);
//
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //分页
//        builder.from(page.getStart()).size(page.getPageSize());
//        builder.query(queryBuilder);
//        return this.search(INDEX_NAME, builder, UserTest.class);
//    }
//
//
//    /**
//     * 获取平均值聚合示例，最大值、最小值、求和类似
//     *
//     * @return
//     */
//    public void aggregation() {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex")
//                .field("sex.keyword");  //若不加keyword，在text类型上进行聚合操作时会报错
//        aggregation.subAggregation(AggregationBuilders.avg("avg_age")
//                .field("age"));  //avg_age 为子聚合名称，名称可随意
//        searchSourceBuilder.aggregation(aggregation);
//
//        SearchResponse searchResponse = this.search(INDEX_NAME, searchSourceBuilder);
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms bySexAggregation = aggregations.get("by_sex");
//        Terms.Bucket elasticBucket = bySexAggregation.getBucketByKey("女");
//        Avg averageAge = elasticBucket.getAggregations().get("avg_age");
//        double avg = averageAge.getValue();
//        logger.info("女性平均年龄：{}", avg);
//
//
//    }
//
//    /**
//     * Stats统计
//     */
//    public void stats() {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex")
//                .field("sex.keyword");  //by_sex为聚合名称，名称可随意
//        aggregation.subAggregation(AggregationBuilders.stats("stat_age")
//                .field("age"));  //avg_age 为子聚合名称，名称可随意
//        searchSourceBuilder.aggregation(aggregation);
//
//        SearchResponse searchResponse = this.search(INDEX_NAME, searchSourceBuilder);
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms bySexAggregation = aggregations.get("by_sex");
//        List<? extends Terms.Bucket> buckets = bySexAggregation.getBuckets();
//        for (Terms.Bucket bucket : buckets) {
//            Stats statAge = bucket.getAggregations().get("stat_age");
//            logger.info("分组名称：{}", bucket.getKey()); //获取分组名称
//            logger.info("平均值：{}", statAge.getAvg());
//            logger.info("总数：{}", statAge.getSum());
//            logger.info("最大值：{}", statAge.getMaxAsString());
//            logger.info("最小值：{}", statAge.getMin());
//        }
//    }
//
//    /**
//     * 比较完整的例子：      没有实现的功能  （查询建议 词项建议 和 自动补全，根据用户的输入联想到可能的词或者短语）
//     * 根据关键词搜索
//     * 1 模糊匹配 查询条件
//     * 2 分页支持
//     * 3 排序
//     * 4 过滤需要返回哪些字段
//     * 5 排除哪些字段
//     * 6 高亮显示
//     * 7 超时时间设置
//     * 8 查询 总共用时 时间
//     * 8 SearchHit对象 使用汇总 获取各种数据
//     * <p>
//     * termQuery: term为不使用分词器查找，类似精确查找。
//     * matchQuery: mactch为使用分词器进行查找，会查询到一些近似匹配的内容。
//     *
//     * @param
//     */
//    public List<UserTest> getByPage(EsPage page, String query) {
//        if (null == page) {
//            return null;
//        }
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//
//        //对名称进行模糊搜素，并启用分析器
//        boolQueryBuilder.must(QueryBuilders.matchQuery("name", query));
//
//        //查询条件
//        builder.query(boolQueryBuilder);
//
//        //分页
//        builder.from(page.getStart()).size(page.getPageSize());
//
//        //按分数（即匹配度）排序  //通过指定字段来排序
//        builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        builder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
//
//
//        //需要返回哪些字段
//        String[] includeFields = new String[]{"name", "context", "innerObject.*"};
//        //排除哪些字段
//        String[] excludeFields = new String[]{"_type"};
//        builder.fetchSource(includeFields, excludeFields);
//
//
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("name");
//        highlightTitle.highlighterType("unified"); //字段高亮显示类型，默认用标签包裹高亮字词
//        highlightBuilder.field(highlightTitle);
//        builder.highlighter(highlightBuilder);
//
//
//        //设置超时时间
//        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        //搜素对象
//        SearchRequest request = new SearchRequest(INDEX_NAME);
//        request.source(builder);
//        SearchResponse searchResponse = this.search(request);
//
//        SearchHits hits = searchResponse.getHits();
//        //查询命中总数
//        long totalHits = hits.getTotalHits().value;
//        //查询命中的最高分数
//        float maxScore = hits.getMaxScore();
//        //用时
//        TimeValue took = searchResponse.getTook();
//        logger.info("查询成功！");
//        logger.info("查询命中总数：{}", totalHits);
//        logger.info("查询命中的最高分数：{}", maxScore);
//        logger.info("请求参数: {}, 用时{}毫秒", request.source().toString(), took.millis());
//
//        for (SearchHit hit : hits.getHits()) {
//
//            //通过SearchHit还可以获取返回数据的索引、docId和得分等基本信息
//            String index = hit.getIndex();
//            String id = hit.getId();
//            float score = hit.getScore();
//            //hit还可以以Json字符串或Map的形式返回数据
//            String sourceAsString = hit.getSourceAsString();
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//
//            String name = (String) sourceAsMap.get("name");
//            Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
//
//            logger.info("index : {}", index);
//            logger.info("id : {}", id);
//            logger.info("score : {}", score);
//            logger.info("sourceAsString : {}", sourceAsString);
//            logger.info("sourceAsMap : {}", sourceAsMap);
//            logger.info("name : {}", name);
//            logger.info("innerObject : {}", innerObject);
//
//            //取高亮结果
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            HighlightField highlight = highlightFields.get("name");
//            if (highlight != null) {
//                Text[] fragments = highlight.fragments();  //多值的字段会有多个值
//                if (fragments != null) {
//                    String fragmentString = fragments[0].string();
//                    logger.info("title highlight : {}", fragmentString);
//                    //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
//                    //sourceAsMap.put("title", fragmentString);
//                }
//            }
//
//        }
//        return null;
//    }
//
////    /**
////     * es 集群信息
////     */
////    public void clusterInfo() {
////        MainResponse response = this.info();
////        //返回集群的各种信息
////        String clusterName = response.getClusterName(); //集群名称
////        String clusterUuid = response.getClusterUuid(); //群集的唯一标识符
////        String nodeName = response.getNodeName(); //已执行请求的节点的名称
////        MainResponse.Version version = response.getVersion(); //已执行请求的节点的版本
////        logger.info("clusterName: {}", clusterName);
////        logger.info("clusterUuid: {}", clusterUuid);
////        logger.info("nodeName: {}", nodeName);
////        logger.info("version: {}", version);
////    }
//
//
//}
