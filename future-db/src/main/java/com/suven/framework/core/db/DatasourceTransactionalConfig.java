package com.suven.framework.core.db;

import jakarta.annotation.Resource;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: DataSourceHolder.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 动态数据库连接池实现切换，实现多数据源原理,以切面实现数据方法事实类；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
//@Configuration
//@Aspect
public class DatasourceTransactionalConfig {

    private static final int TX_METHOD_TIMEOUT = 300;
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.suven.*.*..dao.*.*(..))";
    private DataSource dataSource;

    @Resource(name="dataSource")
//    @ConditionalOnClass(DataSourceGroupProperties.class)
//    @ConditionalOnMissingBean(DataSourceGroupProperties.class)
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    protected String getAopPath(){
        return AOP_POINTCUT_EXPRESSION;
    }

    @Bean
//    @ConditionalOnClass(DataSourceGroupProperties.class)
//    @ConditionalOnMissingBean(DataSourceGroupProperties.class)
    public TransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("txAdvice")
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();

        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_METHOD_TIMEOUT);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        //查询的方法
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);

        //写的方法
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("del*", requiredTx);

        source.setNameMap( txMap );
        TransactionInterceptor txAdvice = new TransactionInterceptor(txManager(), source);
        return txAdvice;
    }


    /**切面拦截规则 参数会自动从容器中注入*/
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor txAdvice){
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        String aopPath = getAopPath();
        pointcutAdvisor.setAdvice(txAdvice);
        pointcutAdvisor.setExpression(aopPath);
       String name = pointcutAdvisor.getPointcut().getClass().getName();

        return pointcutAdvisor;
    }



//    @Before("txManager()")
//    public void before(JoinPoint point){
//        DSClassAnnoExplain.getDatasourceTransactional(point.getClass());
//    }


}
