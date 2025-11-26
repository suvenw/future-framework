package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.suven.framework.core.ObjectTrue;
import org.apache.ibatis.executor.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public class MybatisPageInnerInterceptor extends PaginationInnerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisPageInnerInterceptor.class);

    /**
     * 默认最大分页限制：防止单次查询数据量过大
     */
    private static final long DEFAULT_MAX_LIMIT = 1000L;

    /**
     * 构造函数：设置默认最大限制为 1000 条
     * 
     * <p>说明：此限制仅用于限制分页参数的最大值，不会自动添加分页参数。
     * 开发者必须明确指定分页参数，否则查询将不会进行分页处理。</p>
     */
    public MybatisPageInnerInterceptor() {
        super();
        // 设置默认最大分页限制为 1000 条（仅用于限制分页参数的最大值）
        this.setMaxLimit(DEFAULT_MAX_LIMIT);
    }

    /**
     * 处理超出分页条数限制,默认归为限制数
     *
     * @param page IPage
     */
    protected void handlerLimit(IPage<?> page, Long limit) {
        final long size = page.getSize();
        if (limit != null && limit > 0 && (size > limit || size < 0)) {
            page.setSize(limit);
        }
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
        
        // 如果没有分页参数，直接返回，不进行分页处理
        // 强制开发者必须明确指定分页参数，避免依赖自动保护机制
        if (page == null) {
            // 记录警告日志，提醒开发者必须明确指定分页参数
            logger.warn("查询方法 {} 未指定分页参数，将执行全表查询。请明确指定 IPage 参数以避免性能问题。", 
                        ms.getId());
            // 直接返回，不进行分页处理
            return;
        }

        // 处理 orderBy 拼接
        boolean addOrdered = false;
        String buildSql = boundSql.getSql();
        List<OrderItem> orders = page.orders();
        if (ObjectTrue.isNotEmpty(orders)) {
            addOrdered = true;
            buildSql = this.concatOrderBy(buildSql, orders);
        }

        // size 小于 0 且不限制返回值则不构造分页sql
        Long _limit = page.maxLimit() != null ? page.maxLimit() : maxLimit;
        if (page.getSize() < 0 && null == _limit) {
            if (addOrdered) {
                PluginUtils.mpBoundSql(boundSql).sql(buildSql);
            }
            return;
        }

        handlerLimit(page, _limit);
        IDialect dialect = findIDialect(executor);

        final Configuration configuration = ms.getConfiguration();

        long pageSize = getPageSizePlus(page);

        DialectModel model = dialect.buildPaginationSql(buildSql, page.offset(), pageSize);
        PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);

        List<ParameterMapping> mappings = mpBoundSql.parameterMappings();
        Map<String, Object> additionalParameter = mpBoundSql.additionalParameters();
        model.consumers(mappings, configuration, additionalParameter);
        mpBoundSql.sql(model.getDialectSql());
        mpBoundSql.parameterMappings(mappings);
    }


    /**
     * 获取分页大小，支持扩展的下一次逻辑
     * 
     * @param page 分页对象
     * @return 实际使用的分页大小
     */
    public long getPageSizePlus(IPage<?> page) {
        long pageSize = page.getSize();
        // 优化后（JDK 21 模式匹配）
        if (page instanceof IPager<?> pager && pager.isNextPage()) {
            pageSize = pager.getNextPageSize();
        }
        return pageSize;
    }
}
