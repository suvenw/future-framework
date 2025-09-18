
package com.suven.framework.core.db.ext;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if(params.get(MenuPage.PAGE.getValue()) != null){
            curPage = Long.parseLong((String)params.get(MenuPage.PAGE.getValue()));
        }
        if(params.get(MenuPage.LIMIT.getValue()) != null){
            limit = Long.parseLong((String)params.get(MenuPage.LIMIT.getValue()));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(MenuPage.PAGE.getValue(), page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = sqlInject((String)params.get(MenuPage.ORDER_FIELD.getValue()));
        String order = (String)params.get(MenuPage.ORDER.getValue());


        //前端字段排序
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(MenuPage.ASC.getValue().equalsIgnoreCase(order)) {
                return  page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if(StringUtils.isBlank(defaultOrderField)){
            return page;
        }

        //默认排序
        if(isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        }else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RuntimeException("包含非法字符");
            }
        }

        return str;
    }
    /*
	 *分页类型
      */
    public enum MenuPage {
        /**
         * 当前页码
         */
        PAGE("page"),
        /**
         * 每页显示记录数
         */
        LIMIT("limit"),
        /**
         * 排序字段
         */
        ORDER_FIELD("sidx"),
        /**
         * 排序方式
         */
        ORDER("order"),
        /**
         *  升序
         */
        ASC("asc");

        private String value;

        MenuPage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
