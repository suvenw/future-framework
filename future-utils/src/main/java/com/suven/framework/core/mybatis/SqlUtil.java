package com.suven.framework.core.mybatis;

/**
 * SQL 工具类
 *
 * 提供一些 SQL 相关的工具方法，用于替代已经过时的方法
 */
public class SqlUtil {

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数，可能为 null
     * @return boolean，成功返回 true，失败返回 false
     */
    public static boolean retBool(Long result) {
        // 默认 null 视为操作失败
        return result != null && result > 0;
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数，可能为 null
     * @return boolean，成功返回 true，失败返回 false
     */
    public static boolean retBool(Integer result) {
        // 默认 null 视为操作失败
        return result != null && result > 0;
    }
}
