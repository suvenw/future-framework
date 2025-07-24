//package com.suven.framework.core.mybatis;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
//import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.*;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.RowBounds;
//
//import java.util.Properties;
//
//@Intercepts({
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, org.apache.ibatis.session.RowBounds.class, org.apache.ibatis.session.ResultHandler.class})
//})
//public class CustomPaginationInterceptor implements Interceptor {
//
//    private static final String DIALECT_KEY = "dialect";
//    private static final String DIALECT_MYBATIS_PLUS = "mybatis-plus";
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//        Object parameter = args[1];
//        RowBounds rowBounds = (RowBounds) args[2];
//
//        if (ms.getSqlCommandType().name().startsWith("SELECT") ) {
//            String originalSql = ms.getBoundSql(parameter).getSql();
//            String dialect = getDialect(ms.getConfiguration().getVariables());
//            String paginationSql = getPaginationSql(originalSql, dialect, rowBounds.getOffset(), rowBounds.getLimit());
//            MappedStatement newMs = copyFromMappedStatement(ms, new SqlSource(ms.getBoundSql(parameter), paginationSql));
//            invocation.getArgs()[0] = newMs;
//        }
//
//        return invocation.proceed();
//    }
//
//    private DialectModel getPaginationSql(String originalSql, String dialect, long offset, long limit) {
//        IDialect innerDialect;
//        if ("mysql".equalsIgnoreCase(dialect)) {
//            innerDialect = new MySqlDialect();
//        } else if ("oracle".equalsIgnoreCase(dialect)) {
//            innerDialect = new OracleDialect();
//        } else if ("sqlserver".equalsIgnoreCase(dialect)) {
//            innerDialect = new SQLServerDialect();
//        } else if ("postgresql".equalsIgnoreCase(dialect)) {
//            innerDialect = new PostgreDialect();
//        } else {
//            throw new UnsupportedOperationException("The database dialect is not supported.");
//        }
//
//        return innerDialect.buildPaginationSql(originalSql, offset, limit);
//    }
//
//    private String getDialect(Properties properties) {
//        String dialect = properties.getProperty(DIALECT_KEY);
//        if (dialect != null && !dialect.isEmpty()) {
//            return dialect;
//        } else {
//            return DIALECT_MYBATIS_PLUS;
//        }
//    }
//
//    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
//        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.resultSetType(ms.getResultSetType());
//        builder.cache(ms.getCache());
//        builder.flushCacheRequired(ms.isFlushCacheRequired());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        if (target instanceof Executor) {
//            return Plugin.wrap(target, this);
//        } else {
//            return target;
//        }
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // No additional properties needed
//    }
//}