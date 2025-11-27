package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MyBatisTenantLineInnerInterceptor extends TenantLineInnerInterceptor {
 
    public MyBatisTenantLineInnerInterceptor() {
        super();
    }
 
    public MyBatisTenantLineInnerInterceptor(TenantLineHandler tenantLineHandler) {
        super(tenantLineHandler);
    }
    
    public MyBatisTenantLineInnerInterceptor(TenantLineHandler tenantLineHandler, Collection<String> ignoreStatements) {
        super(tenantLineHandler);
        addIgnoreStatements(ignoreStatements);
    }
 
    private final Set<String> ignoreStatementNames = new CopyOnWriteArraySet<>();

    public Set<String> getIgnoreStatementNames() {
        return Collections.unmodifiableSet(ignoreStatementNames);
    }

    public void addIgnoreStatement(String statementId) {
        if (statementId != null && !statementId.isEmpty()) {
            ignoreStatementNames.add(statementId);
        }
    }

    public void addIgnoreStatements(Collection<String> statementIds) {
        if (statementIds == null) {
            return;
        }
        statementIds.stream()
                .filter(id -> id != null && !id.isEmpty())
                .forEach(ignoreStatementNames::add);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 如果statementid存在，则忽略方法
        if (!ignoreStatementNames.isEmpty() && ignoreStatementNames.contains(ms.getId())) {
            return;
        }
        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
}