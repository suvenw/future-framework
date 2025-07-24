//package com.suven.framework.core.es;
//
//import org.apache.lucene.search.TotalHits;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//    public class TestService {
//        @Autowired
//        RestHighLevelClient highLevelClient;
//
//        private void search(RestHighLevelClient highLevelClient) throws IOException {
//
//            SearchRequest searchRequest = new SearchRequest();
//            searchRequest.indices("customer");
//            searchRequest.types("_doc");
//
//            // 条件=
//            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("city", "北京");
//            TermQueryBuilder termQuery = QueryBuilders.termQuery("province", "福建");
//            // 范围查询
//            RangeQueryBuilder timeFilter = QueryBuilders.rangeQuery("log_time").gt(12345).lt(343750);
//            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//            QueryBuilder totalFilter = QueryBuilders.boolQuery()
//                    .filter(matchQuery)
//                    .filter(timeFilter)
//                    .mustNot(termQuery);
//
//            int size = 200;
//            int from = 0;
//            TotalHits total = null;
//            do {
//                try {
//                    sourceBuilder.query(totalFilter).from(from).size(size);
////                    sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//                    searchRequest.source(sourceBuilder);
//
//                    SearchResponse response = highLevelClient.search(searchRequest,null);
//                    SearchHit[] hits = response.getHits().getHits();
//                    for (SearchHit hit : hits) {
//                        System.out.println(hit.getSourceAsString());
//                    }
//
//                    total = response.getHits().getTotalHits();
//
//                    System.out.println("测试:[" + total + "][" + from + "-" + (from + hits.length) + ")");
//
//                    from += hits.length;
//
//                    // from + size must be less than or equal to: [10000]
//                    if (from >= 10000) {
//                      System.out.println("测试:超过10000条直接中断");
//                      break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } while (from < total.value);
//        }
//    }