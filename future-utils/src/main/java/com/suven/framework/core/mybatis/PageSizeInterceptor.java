//package com.suven.framework.core.mybatis;
//
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//
//import java.util.Map;
//import java.util.Properties;
//
//public class PageSizeInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        // 获取原始的MappedStatement对象
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//
//        // 获取原始的BoundSql对象
//        BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
//
//        // 修改分页参数
//        Object parameterObject = boundSql.getParameterObject();
//        if (parameterObject instanceof Map) {
//            Map<String, Object> paramMap = (Map<String, Object>) parameterObject;
//            Integer offset = (Integer) paramMap.get("offset");
//            Integer limit = (Integer) paramMap.get("limit");
//
//            if (offset != null && limit != null) {
//                String originalSql = boundSql.getSql();
//                String modifiedSql = originalSql + " LIMIT " + offset + ", " + limit;
//                BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), modifiedSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
//                MappedStatement newMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
//                invocation.getArgs()[0] = newMappedStatement;
//            }
//        }else {
//            if (parameterObject instanceof IPager) {
//
//            }
//        }
//
//        // 调用原始方法
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        // 创建拦截代理对象
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // 读取和设置拦截器的属性
//    }
//
//    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
//        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        builder.keyProperty(String.join(",",ms.getKeyProperties()));
//        builder.timeout(ms.getTimeout());
//        builder.cache(ms.getCache());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//    private static class BoundSqlSqlSource implements SqlSource {
//        private final BoundSql boundSql;
//
//        public BoundSqlSqlSource(BoundSql boundSql) {
//            this.boundSql = boundSql;
//        }
//
//        @Override
//        public BoundSql getBoundSql(Object parameterObject) {
//            return boundSql;
//        }
//    }
//}