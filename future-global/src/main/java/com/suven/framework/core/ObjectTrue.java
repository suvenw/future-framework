package com.suven.framework.core;

import java.util.Collection;
import java.util.Map;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-06-29
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  判断对象或值是否为空
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.aiin.vip
 **/


public  class ObjectTrue {

    /*** ============== 业务逻辑判断 是否 为空 判断,返回true 或 false ================= ***/



    /**
     * 判断 object Bean类型对象,是否 为非空
     *
     * @param object Bean类型对象
     * @return {@code true} - 不为空，{@code false} - 为空
     */
    public static boolean isTrue(Boolean object) {
        return  null != object && object == true ;
    }

    /**
     * 判断 object Bean类型对象,是否 为空
     *
     * @param object Bean类型对象
     * @return {@code true} - 为空，{@code false} - 不为空
     */
    public static boolean isEmpty(Boolean object) {
        return null == object ;
    }



    /**
     * 判断 object Number 为数据类型对象,是否 为空
     * @param number Number 类型对象
     * @return {@code true} - 为空，{@code false} - 不为空
     */
    public static boolean isEmpty(Number number) {
        return null == number ;
    }

    /**
     * 判断 object Bean类型对象,是否 为非空
     *
     * @param number Bean类型对象
     * @return {@code true} - 不为空，{@code false} - 为空
     */
    public static boolean isNotEmpty(Number number) {
        return  null != number ;
    }

    /**
     * 判断 object Number 数字类型对象且不为空, 是否 ? 是, 否
     *
     * @param object Number 数字 类型对象
     * @return {@code true} - 不为空，{@code false} - 为空
     */
    public static boolean isNumber(Object object) {
        return  null != object && (object instanceof Number) ;
    }
    /**
     * 判断 str 字符串是否 为空
     *
     * @param str String
     * @return {@code true} - 为空，{@code false} - 不为空
     */
    public static boolean isEmpty(String str) {
        return null == str || str.trim().isEmpty();
    }

    /**
     * 判断 str 字符串是否 为空
     *
     * @param str String
     * @return {@code true} - 非为空，{@code false} - 为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    /**
     * 判断 map 不为空
     *
     * @param collection  Set, List, Map, SortedSet, SortedMap, HashSet, TreeSet, ArrayList,
     *        LinkedList, Vector, Collections, Arrays, AbstractCollection
     * @return {@code true} - 不为空，{@code false} - 为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 判断 聚合collection 为空
     *
     * @param collection Set, List, Map, SortedSet, SortedMap, HashSet, TreeSet, ArrayList,
     *    LinkedList, Vector, Collections, Arrays, AbstractCollection
     * @return {@code true} - 空，{@code false} - 不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null|| collection.isEmpty();
    }



    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    /**
     * 数组是否为非空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (null != array && array.length != 0);
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

    /**
     * 判断 map 不为空
     *
     * @param map map
     * @return {@code true} - 不为空，{@code false} - 空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 判断 map 为空
     *
     * @param map map
     * @return {@code true} - 空，{@code false} - 不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null|| map.isEmpty();
    }


    /**
     * 判断 object Bean类型对象,是否 为空
     *
     * @param object Bean类型对象
     * @return {@code true} - 为空，{@code false} - 不为空
     */
    public static boolean isEmpty(Object object) {
        return null == object ;
    }

    /**
     * 判断 object Bean类型对象,是否 为非空
     *
     * @param object Bean类型对象
     * @return {@code true} - 不为空，{@code false} - 为空
     */
    public static boolean isNotEmpty(Object object) {
        return  null != object ;
    }
    /**
     * 是否包含{@code null}元素
     *
     * @param <T>   数组元素类型
     * @param array 被检查的数组
     * @return 是否包含{@code null}元素
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean hasNull(T... array) {
        if (ObjectTrue.isNotEmpty(array)) {
            for (T element : array) {
                if (null == element) {
                    return true;
                }
            }
        }
        return false;
    }
/*** ============== 业务逻辑判断 是否 为空 判断,返回true 或 false ================= ***/

}
