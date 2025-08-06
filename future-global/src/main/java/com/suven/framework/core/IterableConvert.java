package com.suven.framework.core;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IDApi;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @author 作者 : suven
 * date 创建时间: 2023-11-30
 * @version 版本: v1.0.0
 * <pre>
 *
 *  description (说明):

 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 **/
public class IterableConvert {


    private final static Logger log = LoggerFactory.getLogger(IterableConvert.class);

    /**
     * 将 object 和 newElements 合并成一个数组
     *
     * @param object 对象
     * @param newElements 数组
     * @param <T> 泛型
     * @return 结果数组
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Consumer<T>[] append(Consumer<T> object, Consumer<T>... newElements) {
        if (object == null) {
            return newElements;
        }
        Consumer<T>[] result = (Consumer<T>[]) Array.newInstance(Consumer.class, 1 + newElements.length);
        result[0] = object;
        System.arraycopy(newElements, 0, result, 1, newElements.length);
        return result;
    }

        /**
     * 向集合中添加元素，如果元素不为null的话
     *
     * @param coll 要添加元素的集合
     * @param item 要添加的元素
     * @param <T> 集合和元素的类型参数
     */
    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        // 如果元素为null，则直接返回不执行添加操作
        if (item == null) {
            return;
        }
        coll.add(item);
    }




    /**
     * 判断集合中是否存在满足条件的元素。
     * long spuId = 1L, 商品supId
     * List<Item> itemList = null
     * anyMatch(itemList, item -> ObjectUtil.equal(item.getSpuId(), spuId))
     * @param from      源集合
     * @param predicate 条件判断函数
     * @return 如果集合中存在满足条件的元素，则返回 true；否则返回 false。
     */
    public static <T> boolean anyMatch(Collection<T> from, Predicate<T> predicate) {
        return from.stream().anyMatch(predicate);
    }





    /**
     * 将List集合转换为以ID为键的Map集合
     *
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return 返回以实体ID为键，实体对象为值的Map集合；如果输入列表为空，则返回空Map
     */
    public static < T extends IBaseApi> Map<Long, T> convertMap(List<T> list){
        // 如果列表为空，返回空Map
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        // 使用Stream API将List转换为Map，以实体ID作为键，实体对象作为值
        return list.stream().collect(Collectors.toMap(T::getId, entity -> entity));
    }



    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return 返回以实体ID为键，实体对象为值的Map集合；如果输入列表为空，则返回空Map
     */
    public static  < I extends Serializable, T extends IDApi<I>,V extends IBeanClone>  Map<I,V > convertMap(Collection<T> list, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(T::getId, entity ->{
            try {
                return  clazz.getDeclaredConstructor().newInstance().clone(entity);
            } catch (Exception e) {
               log.info("convertMap Exception ", e);
            }
            return null;
        }));

    }

    /**
     * T extends Serializable 为系列化对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性,但不能有重复key
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     * <p> 备注:但不能有重复key <p/>
     *  1.使用例子:
     *   List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(2l));
     *   Map<Long,BaseStatusEntity> map =  converterMap(list,BaseEntity::getId, BaseStatusEntity.class);
     *   Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *   And the following produces a Map mapping a unique identifier to students:
     *   Map<String, Student> studentIdToStudent
     *   students.stream().collect(toMap(Student::getId,Functions.identity());
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     *
     * @return 返回以实体ID为键，实体对象为值的Map集合；如果输入列表为空，则返回空Map
     */
    public static <T,K, V extends IBeanClone> Map<K, V> convertMap(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, entity ->{
            try {
                V  value =  clazz.getDeclaredConstructor().newInstance().clone(entity);
                return value;
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
            return null;
        }));

    }

    /**
     * T extends Serializable 为系列化对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性,但有重复key,后面的值 value2 会覆盖前端的value1值
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     * <p> 备注:但有重复key,后面的值 value2 会覆盖前端的value1值<p/>
     *  1.使用例子:
     *   List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(2l));
     *   Map<Long,BaseStatusEntity> map =  converterMap(list,BaseEntity::getId, BaseStatusEntity.class);
     *   Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *   And the following produces a Map mapping a unique identifier to students:
     *   Map<String, Student> studentIdToStudent
     *   students.stream().collect(toMap(Student::getId,Functions.identity());
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     *
     * @return 存储结果map集合
     */
    @SuppressWarnings("unchecked")
    public static <T,K, V extends IBeanClone> Map<K, V> convertMapSame(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz) {
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<K,V> map = list.stream().collect(Collectors.toMap(keyMapper, entity -> {
            try {
                return clazz.getDeclaredConstructor().newInstance().clone(entity);
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
            return null;
        },(value1, value2) ->{
            value1= null;
            return value2;
        }));
        return map;

    }



    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象主键ID属性值;存储在map集合的Key属性
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     * List 以keyMapper属性分组 Map<K,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseEntity> map =  convertMapGroup(list,BaseEntity::getId);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return Map 存储结果map集合
     */
    public static <T,K>  Map<K,List<T>> convertMapGroup(List<T> list,Function<T,K> keyMapper){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().filter(e -> ObjectTrue.isNotEmpty(keyMapper.apply(e))).collect(Collectors.groupingBy(keyMapper));
    }


    /**
     * 将map<K,V> 集合转换成List<V>集合实现方法
     * Map<Long,T> map --> list<T>
     * @param map 待转换的Map集合，键为Serializable类型，值为泛型T
     * @return 转换后的List集合，包含原Map的所有值；如果输入为null则返回空列表
     */
    public static <T>  List<T> convertMapToList(Map<Serializable,T> map){
        // 处理空值情况，避免空指针异常
        if(null ==map){
            return Collections.emptyList();
        }
        // 使用Stream API将Map的values转换为List
        List<T> result = map.values().stream().collect(Collectors.toList());
        return result;
    }



    /** List<A> list --> list<B>
     * T extends Serializable 为系列化对象或Object对象
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     * List 以为V为对象基于T做克隆属性后的对象的集合;
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<BaseStatusEntity> resultList =  convertList(list, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return   List<B> 存储结果list集合
     */
    public static < T, V extends IBeanClone>  List<V> convertList(Collection<T> list, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyList();
        }
        List<V> result =  list.stream().map(entity -> {
              try {
                  V clone =  clazz.getDeclaredConstructor().newInstance().clone(entity);
                  return clone;
              } catch (Exception e) {
                    log.info("convertMap Exception ", e);
              }
              return null;
          }).collect(Collectors.toList());
        return result;
    }

    /** List<A> list --> list<B>
     * T extends Serializable 为系列化对象或Object对象
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     * List 以为V为对象基于T做克隆属性后的对象的集合;
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<BaseStatusEntity> resultList =  convertList(list, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return   List<B> 存储结果list集合
     */
    public static < T, K,V extends IBeanClone>  List<V> convertList(Collection<T> list, Class<V> clazz, Function<V,K> function){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyList();
        }
        List<V> result =  list.stream().map(entity -> {
            try {
                V clone = clazz.getDeclaredConstructor().newInstance().clone(entity);
                if(ObjectTrue.isNotEmpty(function)){
                    function.apply(clone);
                }
                return clone;
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
            return null;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象主键ID属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertLinkedHashMap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return Map 存储结果map集合
     */
    public  static <I extends Serializable,T extends IDApi<I>,V extends IBeanClone> Map<I, V> convertLinkedHashMap(Collection<T> list, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<I,V > map =   new LinkedHashMap<>();
        list.forEach(entity ->{
            try {
                I key = entity.getId();
                V vo =  clazz.getDeclaredConstructor().newInstance().clone(entity);
                map.put(key,vo);
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
        });
        return map;
    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertLinkedHashMap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return Map 存储结果map集合
     */
    public  static <T,K,V extends IBeanClone> Map<K, V> convertLinkedHashMap(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<K,V > map =   new LinkedHashMap<>();
        list.forEach(entity ->{
            try {
                K key = keyMapper.apply(entity);
                V vo =  clazz.getDeclaredConstructor().newInstance().clone(entity);
                map.put(key,vo);
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
        });
        return map;
    }


    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertGroupMap(list, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return Map 存储结果map集合
     */
    @SuppressWarnings("unchecked")
    public  static <K extends IBaseApi,V extends IBeanClone>   Map<Long, Collection<V>> convertGroupMap(Collection<K> list, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyMap();
        }
        Multimap<Long,V > map =   ArrayListMultimap.create();
        list.forEach(entity ->{
            try {
                V vo = Objects.requireNonNull(newInstance(clazz)).clone(entity);
                map.put(entity.groupId() ,vo);
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
        });
        Map<Long,Collection<V>> mapList =  map.asMap();
        return mapList;

    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Multimap<Serializable,BaseStatusEntity> map =  convertMultimap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list 待转换的实体对象列表，列表中的对象必须实现IBaseApi接口
     * @return Multimap 存储结果map集合
     */
    public  static <T,K , V extends IBeanClone> Multimap<K, V> convertMultimap(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz){

        Multimap<K,V > map =   ArrayListMultimap.create();
        if(ObjectTrue.isEmpty(list)){
            return map;
        }
        list.forEach(entity ->{
            try {
                V vo =  Objects.requireNonNull(newInstance(clazz)).clone(entity);
                K key =  keyMapper.apply(entity);
                map.put(key,vo);
            } catch (Exception e) {
                  log.info("convertMap Exception ", e);
            }
        });
        return map;

    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * V 为T(Object)对象的指定属性的类型,返回指定属性的类型的值
     * List 以keyMapper属性的值的集合
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<Long> resultList =  convertFieldsList(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list 结果集合
     * @return 集合结果
     */
    public  static <T,V> List<V> convertFieldsList(Collection<T> list, Function<T,V> keyMapper){

        if(ObjectTrue.isEmpty(list)){
            return new ArrayList<>();
        }
        List<V> resultList =  list.stream().map(keyMapper).collect(Collectors.toList());
        return resultList;

    }
    /**
     * T extends Serializable 为系列化对象或Object对象
     * V 为T(Object)对象的指定属性的类型,返回指定属性的类型的值
     * List 以keyMapper属性的值的集合
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Set<Long> resultSet =  convertFieldsSet(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list 结果集合
     * @return 去重集合结果
     */
    public  static <T,V> Set<V> convertFieldsSet(Collection<T> list, Function<T,V> keyMapper){

        if(ObjectTrue.isEmpty(list)){
            return new HashSet<>();
        }
        Set<V> resultSet =  list.stream().map(keyMapper).collect(Collectors.toSet());
        return resultSet;

    }


    /**
     * 将数组转换为目标类型的列表。
     *  T[] item
     *  convertList(item -> Item::getId)
     *
     * @param from 源数组
     * @param func 转换函数
     * @return 转换后的结果列表
     */
    public static <T, U> List<U> convertList(T[] from, Function<T, U> func) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return convertList(Arrays.asList(from), func);
    }

    /**
     * 对集合进行转换操作，返回转换后的结果列表。
     *  Collection<Item> item
     *  convertList(item -> Item::getId)
     * @param from 源集合
     * @param func 转换函数
     * @return 转换后的结果列表
     */
    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 对集合进行转换操作，并根据条件进行过滤，返回转换后的结果列表。
     *  List<String> originalList = Arrays.asList("apple", "banana", "cherry", "date");
     * Predicate<String> filter = str -> str.length() > 5; // 过滤长度大于5的字符串
     * Function<String, Integer> func = String::length; // 将字符串转换为其长度
     * List<Integer> resultList = convertList(originalList, func, filter);
     *
     * @param from   源集合
     * @param func   转换函数
     * @param filter 过滤条件函数
     * @return 转换并过滤后的结果列表
     */
    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Convert and filter a collection, keeping the original elements.
     * 对集合进行转换操作，并根据条件进行过滤，返回转换后的结果列表。
     *  List<String> originalList = Arrays.asList("apple", "banana", "cherry", "date");
     * Predicate<String> filter = str -> str.length() > 5; // 过滤长度大于5的字符串
     * Function<String, Integer> func = String::length; // 将字符串转换为其长度
     * List<Integer> resultList = convertList(originalList, func, filter);
     *
     * @param from   源集合
     * @param filter 过滤条件函数
     * @return 转换并过滤后的结果列
     */
    public static <T> List<T> convertFilterList(Collection<T> from, Predicate<T> filter) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream()
                .filter(filter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 将集合转换为Set集合，并过滤掉null元素
     *
     * @param <T> 集合元素类型
     * @param from 源集合
     * @return 转换后的Set集合，不包含null元素
     */
    public static <T> Set<T> convertSet(Collection<T> from){
        return from.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }


    /**
     * 对集合进行转换操作，返回转换后的结果集合。
     *  Collection<Item> item
     *  convertList(item -> Item::getId)
     *
     * @param from 源集合
     * @param func 转换函数
     * @return 转换后的结果集合
     */
    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    /**
     * 对集合进行转换操作，并根据条件进行过滤，返回转换后的结果集合。
     *  List<String> originalList = Arrays.asList("apple", "banana", "cherry", "date");
     * Predicate<String> filter = str -> str.length() > 5; // 过滤长度大于5的字符串
     * Function<String, Integer> func = String::length; // 将字符串转换为其长度
     * Set<Integer> resultList = convertSet(originalList, func, filter);
     *
     * @param from   源集合
     * @param func   转换函数
     * @param filter 过滤条件函数
     * @return 转换并过滤后的结果集合
     */
    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    /**
     * 对集合进行转换操作，并根据条件进行过滤，返回按键映射关系的结果集合。
     * List<String> originalList = Arrays.asList("apple", "banana", "cherry", "date");
     * Predicate<String> filter = str -> str.length() > 5; // 过滤长度大于5的字符串
     * Function<String, Integer> keyFunc = String::length; // 将字符串长度作为键
     * Map<Integer, String> resultMap = convertMapByFilter(originalList, filter, keyFunc);
     *
     * @param from    源集合
     * @param filter  过滤条件函数
     * @param keyFunc 键提取函数
     * @return 转换并过滤后的按键映射关系的结果集合
     */
    public static <T, K> Map<K, T> convertMapByFilter(Collection<T> from, Predicate<T> filter, Function<T, K> keyFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().filter(filter).collect(Collectors.toMap(keyFunc, Function.identity(), (v1, v2) -> v1));
    }

    /**
     * 对集合进行转换操作，并根据键提取函数过滤，转换并过滤后的按键映射关系的结果集合。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @return 转换并过滤后的按键映射关系的结果集合
     */
    public static <T, K> Map<K, T> convertMap(T[] from, Function<T, K> keyFunc) {
        if (Objects.isNull(from)) {
            return new HashMap<>();
        }

        return convertMap(Arrays.asList(from), keyFunc, Function.identity());
    }

    /**
     * 对集合进行转换操作，并根据键提取函数过滤，转换并过滤后的按键映射关系的结果集合。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @return 转换并过滤后的按键映射关系的结果集合
     */
    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, Function.identity());
    }

    /**
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from     源集合
     * @param keyFunc  键提取函数
     * @param supplier Map 实例提供者
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
        if (ObjectTrue.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, Function.identity(), supplier);
    }


    /**
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from     源集合
     * @param keyFunc  键提取函数
     * @param valueFunc Function 键提取函数
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    /**
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from          源集合
     * @param keyFunc       键提取函数
     * @param valueFunc     值提取函数
     * @param mergeFunction 合并函数（用于处理键冲突时的值合并）
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, mergeFunction, HashMap::new);
    }

    /**
     *  集合转Map
     * List -> Map
     * 需要注意的是：
     * toMap 如果集合对象有重复的key，会报错Duplicate key ....
     *  apple1,apple12的id都为1。
     *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
     *     List<T> list =  new ArrayList<>();
     *     Map<Long,T> map = list.stream().collect(Collectors.toMap(T::getId, a -> a,(k1, k2)->k1));
     *  List 以ID分组 Map<Integer,List<Apple>>
     *  Map<Integer, List<Apple>> groupBy = appleList.stream().collect(Collectors.groupingBy(Apple::getId));
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from          源集合
     * @param keyFunc       键提取函数
     * @param valueFunc     值提取函数
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (ObjectTrue.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    /**
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from          源集合
     * @param keyFunc       键提取函数
     * @param valueFunc     值提取函数
     * @param mergeFunction 合并函数（用于处理键冲突时的值合并）
     * @param supplier      Map 实例提供者
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }

    /**
     * 对集合进行转换操作并生成映射关系，返回结果集合。
     *
     * @param from          源集合
     * @param filter        过滤条件函数
     * @param keyFunc       键提取函数
     * @param valueFunc     值提取函数
     * @param mergeFunction 合并函数（用于处理键冲突时的值合并）
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Predicate<T> filter, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().filter(filter).collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction));
    }

    /**
     * 将集合按照键进行分组，生成键与对应元素列表的多重映射关系。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @param <T>     元素类型
     * @param <K>     键类型
     * @return 键与对应元素列表的多重映射关系
     */
    public static <T, K> Map<K, List<T>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(t -> t, Collectors.toList())));
    }
    /**
     * 将集合按照键进行分组，生成键与对应元素列表的多重映射关系。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @param <T>     元素类型
     * @param <K>     键类型
     * @return 键与对应元素列表的多重映射关系
     */
    public static <T, K, V> Map<K, List<V>> convertMultiListMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream()
                .collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toList())));
    }
    /**
     * 将集合按照键进行分组，生成键与对应元素列表的多重映射关系。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @param <T>     元素类型
     * @param <K>     键类型
     * @return 键与对应元素列表的多重映射关系
     */
    // 暂时没想好名字，先以 2 结尾噶
    public static <T, K, V> Map<K, Set<V>> convertMultiSetMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toSet())));
    }


    /**
     * 对集合进行扁平化转换操作，将每个元素转换为一个流，并将所有流合并为一个结果列表。
     *
     * @param from 源集合
     * @param func 元素转换函数，将每个元素转换为一个流
     * @return 扁平化转换后的结果列表
     */
    public static <T, U> List<U> convertListByFlatMap(Collection<T> from,
                                                      Function<T, ? extends Stream<? extends U>> func) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().flatMap(func).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 对集合进行扁平化转换操作，将每个元素转换为一个流，并将所有流合并为一个结果集合。
     *
     * @param from 源集合
     * @param func 元素转换函数，将每个元素转换为一个流
     * @return 扁平化转换后的结果集合
     */
    public static <T, U> Set<U> convertSetByFlatMap(Collection<T> from,
                                                    Function<T, ? extends Stream<? extends U>> func) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().flatMap(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    /**
     * 将集合按照键进行分组，生成键与对应元素列表的多重映射关系。
     *
     * @param from    源集合
     * @param keyFunc 键提取函数
     * @param <T>     元素类型
     * @param <K>     键类型
     * @return 键与对应元素列表的多重映射关系
     */
    public static <T, K> Map<K, T> convertImmutableMap(Collection<T> from, Function<T, K> keyFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return Collections.emptyMap();
        }
        ImmutableMap.Builder<K, T> builder = ImmutableMap.builder();
        from.forEach(item -> builder.put(keyFunc.apply(item), item));
        return builder.build();
    }


    /**
     * 验证目标列表中,是否包括源对象
     * @param source
     * @param targets
     * @return
     */
    public static boolean containsAny(Object source, Object... targets) {
        return asList(targets).contains(source);
    }


    /**
     * 检查源集合是否包含候选集合中的任何元素
     *
     * @param source 源集合
     * @param candidates 候选集合，用于检查其元素是否存在于源集合中
     * @return 如果源集合包含候选集合中的任何元素，则返回true；否则返回false
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return CollectionUtils.containsAny(source, candidates);
    }

    /**
     * 对集合进行去重操作
     *
     * @param collection 待去重的集合
     * @param keyExtractor 用于提取去重依据的函数
     * @param <T> 集合元素类型
     * @return 去重后的集合
     */
    public static <T> List<T> deduplicateCollection(Collection<T> collection, Function<? super T, String> keyExtractor) {
        if (ObjectTrue.isEmpty(collection)){
            return Collections.emptyList();
        }
        return collection.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(keyExtractor, Comparator.nullsFirst(Comparator.naturalOrder())))),
                        ArrayList::new));
    }

    /**
     * 对集合进行去重操作，返回去重后的结果列表。
     * List<Item> item
     * item -> anyMatch(item.getIds(), id -> ObjectUtil.equal(id, configId))
     * @param from      源集合
     * @return 去重后的结果列表
     */
    public static <T> List<T> distinctList(Collection<T> from){
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(ObjectTrue::isNotEmpty).distinct().collect(Collectors.toList());
    }

    /**
     * 对集合进行去重操作和按大小分组返回去重后的结果分组列表。
     * List<Item> item
     * item -> from.stream().filter(ObjectTrue::isNotEmpty).distinct().collect(Collectors.toList());
     * @param from      源集合
     * @return 去重后的结果列表
     */
    public static <T>  List<List<T>> listPartition(Collection<T> from, int batchSize){
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        List<T>  list =  from.stream().filter(ObjectTrue::isNotEmpty).distinct().collect(Collectors.toList());
        List<List<T>> groupList = Lists.partition(list, batchSize);
        return groupList;
    }


    /**
     * 对集合进行过滤后按大小分组返回去重后的结果分组列表。
     * List<Item> item
     * item -> from.stream().filter(predicate).distinct().collect(Collectors.toList());
     * @param from      源集合
     * @return 结果列表
     */
    public static <T>  List<List<T>> listPartition(Collection<T> from, int batchSize, Predicate<T> predicate){
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        List<T>  list =  from.stream().filter(predicate).distinct().collect(Collectors.toList());
        return Lists.partition(list, batchSize);
    }


    /**
     * 对集合进行去重操作和按大小分组返回去重后的结果分组列表。
     * List<Item> item
     * item -> from.stream().filter(ObjectTrue::isNotEmpty).distinct().collect(Collectors.toList());
     * @param from      源集合
     * @return 去重后的结果列表
     */
    @SuppressWarnings("unchecked")
    public static  <T, R>  List<List<R>> listPartition(Collection<T> from, Function<T, R> keyMapper,int batchSize){
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        List<R>  list =  from.stream()
                .filter(obj -> ObjectTrue.isNotEmpty(keyMapper.apply(obj)) )
                .map(keyMapper).distinct().collect(Collectors.toList());
        List<List<R>> groupList = Lists.partition(list, batchSize);
        return groupList;
    }


    /**
     * 对集合进行去重操作，返回去重后的结果列表。
     * List<Item> item
     * item -> anyMatch(item.getIds(), id -> ObjectUtil.equal(id, configId))
     * @param from      源集合
     * @param keyMapper 用于提取去重依据的函数
     * @return 去重后的结果列表
     */
    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return distinct(from, keyMapper, (t1, t2) -> t1);
    }

    /**
     * 对集合进行去重操作，返回去重后的结果列表。
     *
     * @param from      源集合
     * @param keyMapper 用于提取去重依据的函数
     * @param cover     当出现重复元素时的处理函数，用于确定保留哪一个元素
     * @return 去重后的结果列表
     */
    public static <T, R> List<T> distinct(Collection<T> from, Function<T, R> keyMapper, BinaryOperator<T> cover) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(convertMap(from, keyMapper, Function.identity(), cover).values());
    }

    /**
     * 对比老、新两个列表，找出新增、修改、删除的数据
     *
     * @param oldList  老列表
     * @param newList  新列表
     * @param sameFunc 对比函数，返回 true 表示相同，返回 false 表示不同
     *                 注意，same 是通过每个元素的“标识”，判断它们是不是同一个数据
     * @return [新增列表、修改列表、删除列表]
     */
    public static <T> List<List<T>> diffList(Collection<T> oldList, Collection<T> newList,
                                             BiFunction<T, T, Boolean> sameFunc) {
        List<T> createList = new LinkedList<>(newList); // 默认都认为是新增的，后续会进行移除
        List<T> updateList = new ArrayList<>();
        List<T> deleteList = new ArrayList<>();

        // 通过以 oldList 为主遍历，找出 updateList 和 deleteList
        for (T oldObj : oldList) {
            // 1. 寻找是否有匹配的
            T foundObj = null;
            for (Iterator<T> iterator = createList.iterator(); iterator.hasNext(); ) {
                T newObj = iterator.next();
                // 1.1 不匹配，则直接跳过
                if (!sameFunc.apply(oldObj, newObj)) {
                    continue;
                }
                // 1.2 匹配，则移除，并结束寻找
                iterator.remove();
                foundObj = newObj;
                break;
            }
            // 2. 匹配添加到 updateList；不匹配则添加到 deleteList 中
            if (foundObj != null) {
                updateList.add(foundObj);
            } else {
                deleteList.add(oldObj);
            }
        }
        return asList(createList, updateList, deleteList);
    }


    /**
     * 过滤集合中满足条件的元素，返回过滤后的结果列表。
     * List<Item> item
     * String version = "1.0.0"
     * IterableConvert.filterList(item, item -> version.equals(item.getVersion()));
     * @param from      源集合
     * @param predicate 过滤条件函数
     * @return 过滤后的结果列表
     */
    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }
        /**
     * 过滤并转换集合中的元素
     *
     * @param <T> 输入集合元素类型
     * @param <U> 输出集合元素类型
     * @param from 待处理的输入集合
     * @param predicate 用于过滤元素的谓词条件
     * @param func 用于转换元素的函数
     * @return 过滤并转换后的新列表
     */
    public static <T, U> List<U> filterConvertList(Collection<T> from, Predicate<T> predicate,Function<T, U> func) {
        // 如果输入集合为空，则返回空列表
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        // 使用Stream API进行过滤和转换操作
        return from.stream().filter(predicate).map(func).collect(Collectors.toList());
    }


        /**
     * 根据指定的谓词条件过滤集合中的元素，返回符合条件的元素列表
     *
     * @param <T> 集合元素的类型
     * @param from 要过滤的原始集合
     * @param predicate 一个或多个过滤条件谓词
     * @return 包含所有符合条件元素的新列表
     */
    @SafeVarargs
    public static <T> List<T> filterList(Collection<T> from, Predicate<T>...predicate) {
        // 如果输入集合为空，直接返回空列表
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }

        // 使用Stream API逐个应用过滤条件
        Stream<T> stream = from.stream();
        for (Predicate<T> tPredicate : predicate) {
            stream.filter(tPredicate);
        }

        // 收集过滤后的结果并返回
        return stream.collect(Collectors.toList());
    }

        /**
     * 过滤并转换集合中的元素到新的列表
     *
     * @param <T> 输入集合元素类型
     * @param <U> 输出列表元素类型
     * @param from 源集合，用于过滤和转换的原始数据
     * @param func 转换函数，将类型T的元素转换为类型U
     * @param predicate 一个或多个过滤条件，只有满足所有条件的元素才会被转换
     * @return 包含经过过滤和转换后元素的新列表
     */
    @SafeVarargs
    public static <T, U> List<U> filterConvertList(Collection<T> from, Function<T, U> func, Predicate<T>... predicate) {
        if (ObjectTrue.isEmpty(from)) {
            return new ArrayList<>();
        }
        // 构建流处理管道，应用所有过滤条件
        Stream<T> stream = from.stream();
        for (Predicate<T> tPredicate : predicate) {
            stream.filter(tPredicate);
        }
        // 应用转换函数并收集结果到列表
        return stream.map(func).collect(Collectors.toList());
    }


        /**
     * 在列表中查找第一个满足条件的元素
     *
     * @param <T> 元素类型
     * @param from 要搜索的列表
     * @param predicate 判断元素是否满足条件的谓词函数
     * @return 返回第一个满足条件的元素，如果没有找到则返回null
     */
    public static <T> T findFirst(List<T> from, Predicate<T> predicate) {
        return findFirst(from, predicate, Function.identity());
    }


        /**
     * 在列表中查找第一个满足条件的元素，并将其转换为指定类型返回
     *
     * @param <T> 输入列表元素的类型
     * @param <U> 转换后返回值的类型
     * @param from 要搜索的列表
     * @param predicate 用于过滤元素的条件谓词
     * @param func 用于转换匹配元素的函数
     * @return 找到的第一个满足条件的元素经过转换后的结果，如果未找到则返回null
     */
    public static <T, U> U findFirst(List<T> from, Predicate<T> predicate, Function<T, U> func) {
        // 检查输入列表是否为空
        if (ObjectTrue.isEmpty(from)) {
            return null;
        }
        // 使用Stream API过滤元素，找到第一个匹配的元素并进行转换
        return from.stream().filter(predicate).findFirst().map(func).orElse(null);
    }

    /**
     * 获取数据的指定索引的对象
     * @param array 数组
     * @param index 获取数组的索引值
     * @return 返回结果对象
     * @param <T> 返回结果对象
     */
    public static <T> T get(T[] array, int index) {
        if (null == array || index >= array.length) {
            return null;
        }
        return array[index];
    }


        /**
     * 获取列表中的第一个元素
     *
     * @param <T> 列表元素的类型
     * @param from 要获取元素的列表
     * @return 如果列表不为空，返回列表的第一个元素；否则返回null
     */
    public static <T> T getFirst(List<T> from) {
        return !ObjectTrue.isEmpty(from) ? from.get(0) : null;
    }


        /**
     * 从集合中获取最大值
     *
     * @param <T> 集合元素类型
     * @param <V> 值类型，必须实现Comparable接口
     * @param from 源集合
     * @param valueFunc 用于提取比较值的函数
     * @return 集合中元素对应的最大值，如果集合为空则返回null
     */
    public static <T, V extends Comparable<? super V>> V getMaxValue(Collection<T> from, Function<T, V> valueFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return null;
        }
        // 使用Stream API找出集合中valueFunc对应的值最大的元素
        Optional<T> optionals = from.stream().max(Comparator.comparing(valueFunc));
        if (optionals.isPresent()) {
            T t = optionals.get();
            return valueFunc.apply(t);
        }
        return null;

    }


    /**
     * 从列表中获取最小值
     *
     * @param <T> 列表元素的类型
     * @param <V> 值类型，必须实现Comparable接口
     * @param from 要查找的元素列表
     * @param valueFunc 用于从元素中提取可比较值的函数
     * @return 列表中元素的最小值，如果列表为空则返回null
     */
    public static <T, V extends Comparable<? super V>> V getMinValue(List<T> from, Function<T, V> valueFunc) {
        // 检查列表是否为空
        if (ObjectTrue.isEmpty(from)) {
            return null;
        }

        // 使用Stream API查找具有最小值的元素
        Optional<T> optionals = from.stream().min(Comparator.comparing(valueFunc));

        // 如果找到元素，则应用函数获取对应的值
        if (optionals.isPresent()) {
            T t = optionals.get();
            return valueFunc.apply(t);
        }

        return null;
    }


        /**
     * 计算列表中元素经过函数转换后的累积值
     *
     * @param <T> 列表元素的类型
     * @param <V> 转换后值的类型，必须实现Comparable接口
     * @param from 要处理的元素列表
     * @param valueFunc 将元素T转换为值V的函数
     * @param accumulator 用于累积两个值V的二元操作符
     * @return 累积计算的结果，如果列表为空则返回null
     */
    public static <T, V extends Comparable<? super V>> V getSumValue(List<T> from, Function<T, V> valueFunc,
                                                                     BinaryOperator<V> accumulator) {
        // 检查输入列表是否为空
        if (ObjectTrue.isEmpty(from)) {
            return null;
        }
        // 对列表元素进行转换并累积计算
        Optional<V> optionals = from.stream().map(valueFunc).reduce(accumulator);
        return optionals.orElse(null);
    }


        /**
     * 计算列表中满足条件的元素的值的总和
     *
     * @param <T> 列表元素的类型
     * @param <V> 值的类型，必须实现Comparable接口
     * @param from 要处理的元素列表
     * @param predicate 用于过滤元素的条件谓词
     * @param valueFunc 从元素中提取值的函数
     * @param accumulator 用于累加值的二元操作符
     * @return 满足条件的元素值的总和，如果列表为空或没有满足条件的元素则返回null
     */
    public static <T, V extends Comparable<? super V>> V getSumValue(List<T> from, Predicate<T> predicate,
                                                                     Function<T, V> valueFunc, BinaryOperator<V> accumulator) {
        if (ObjectTrue.isEmpty(from)) {
            return null;
        }
        // 过滤满足条件的元素，提取值并进行累加
        Optional<V> optionals = from.stream().filter(predicate).map(valueFunc).reduce(accumulator);
        return optionals.orElse(null);
    }



        /**
     * 检查多个集合中是否至少有一个为空集合
     *
     * @param collections 可变参数，传入一个或多个集合对象
     * @return 如果传入的集合中至少有一个为空，则返回true；如果所有集合都不为空，则返回false
     */
    public static boolean isAnyEmpty(Collection<?>... collections) {
        // 使用Stream API检查是否至少有一个集合为空
        return Arrays.stream(collections).anyMatch(ObjectTrue::isEmpty);
    }


    /**
     * 合并多个具有相同键的值列表，并返回合并后的结果列表。
     *
     * @param map 包含值列表的映射表
     * @return 合并后的结果列表
     */
    public static <T, V> List<V> mergeValuesFromMap(Map<T, List<V>> map) {
        return map.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }



        /**
     * 创建一个包含单个元素的集合，如果输入为null则返回空集合
     *
     * @param <T> 集合元素的类型
     * @param deptId 要包含在集合中的元素，可以为null
     * @return 当deptId不为null时，返回包含该元素的单例集合；当deptId为null时，返回空集合
     */
    public static <T> Collection<T> singleton(T deptId) {
        // 如果deptId为null，返回空集合；否则返回包含deptId的单例集合
        return deptId == null ? Collections.emptyList() : Collections.singleton(deptId);
    }



    /**
     * 将集合对象列表转换成数组集合组数组
     * @param objects 对象列表信息
     * @return 集合列表组数组
     */
    public static  Map<String,List<Object>>  convertObjectToGroupMap(List<?> objects){
        Map<String,List<Object>> map = new LinkedHashMap<>();
        if (ObjectTrue.isEmpty(objects)){
            return map;
        }
        List<Field> fields = FieldUtils.getAllFieldsList(objects.get(0).getClass());
        for (Object object : objects) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    String key = field.getName();
                    String valueString = ObjectTrue.isEmpty(value) ? "" : value.toString() ;
                    List<Object> list = map.getOrDefault(key,new ArrayList<>());
                    list.add(valueString);
                    map.put(key,list);
                } catch (IllegalAccessException e) {
                    log.info("convertObjectToGroupMap List<Object> objects to Map<String,List<Object>> IllegalAccessException" ,e);
                }
            }
        }
        return map;
    }



    /**
     * 将集合对象按对象属性转换成列表转换成数组集合组数组
     * @param object 对象信息转换成数组列表
     * @return 集合列表组数组
     */
    public static List<String> convertObjectValueToList(Object object){
        List<String> propertyValues = new ArrayList<>();
        List<Field> fields = FieldUtils.getAllFieldsList( object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                String valueString = ObjectTrue.isEmpty(value) ? "" : value.toString() ;
                propertyValues.add(valueString);
            } catch (IllegalAccessException e) {
                log.info("convertObjectValueToList Object object to List<String> IllegalAccessException" ,e);
            }
        }
        return propertyValues;
    }
    /**
     * 将对象按属性转换成map对象
     * @param object 对象信息
     * @return 反回map对象
     */
    public static Map<String,Object> convertObjectToMap(Object object){
        Map<String,Object>  result = new LinkedHashMap<>();
        List<Field> fields = FieldUtils.getAllFieldsList( object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                String valueString = ObjectTrue.isNotEmpty(value) ? value.toString() : null;
                result.put(field.getName(),valueString);
            } catch (IllegalAccessException e) {
                log.info("convertObjectToMap Object object to Map<String,Object> IllegalAccessException" ,e);
            }
        }


        return result;
    }

    /**
     * 转换统计由总数按页大小，获取得到分页数；
     * @param pageSize 页大小
     * @param total 总数
     * @return 分页数
     */
    public static long getPages(int pageSize, long total) {
        if (pageSize == 0) {
            return 0L;
        }
        long pages = total /pageSize;
        if (total % pageSize != 0) {
            pages++;
        }
        return pages;
    }


    /**
     * 对集合进行过滤转换操作并生成映射关系，返回结果集合。
     *
     * @param from      源集合
     * @param predicate 键提取函数
     * @param keyFunc   键提取函数
     * @param valueFunc Function 键提取函数
     * @return 转换生成的映射关系结果集合
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Predicate<T> predicate, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (ObjectTrue.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toMap(keyFunc, valueFunc, (v1, v2) -> v1));
    }

    /** 将两个 List 集合 A 和 B 转换成 Map<A, B> 类型的集合可以通过多种方式实现。以下是几种常见的方法：
        List<String> listA = Arrays.asList("a", "b", "c");
        List<String> listB = Arrays.asList("1", "2", "3");
        Map<String, String> map = convertListsToMap(listA, listB);
        System.out.println(map); // 输出: {a=1, b=2, c=3}
     **/
    public static <K, V> Map<K, V> convertListsToMap(List<K> keys, List<V> values) {
        if (keys == null || values == null) {
            return new HashMap<>();
        }
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Lists must have the same size");
        }
        Map<K, V> map = new HashMap<>(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }


    /**
     * 获取两个列表的交集
     *
     * @param list1 第一个列表
     * @param list2 第二个列表
     * @return 两个列表的交集
     */
    public static <T> List<T> getIntersection(List<T> list1, List<T> list2) {
        if (ObjectTrue.isEmpty(list1) || ObjectTrue.isEmpty(list2)){
            return new ArrayList<>();
        }
        List<T> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        return intersection;
    }

    /**
     * 根据统计条件计算指定字段的整型汇总值
     *
     * @param from      数据源列表
     * @param predicate 过滤条件
     * @param func      映射函数，用于提取需要统计的字段
     * @return 返回一个 Map，包含字段及其对应的整型汇总值
     */
    public static <T, K> Map<K, Integer> calculateIntegerSummary(List<T> from, Predicate<T> predicate, Function<T, K> func) {
        if (from == null || from.isEmpty()) {
            return new HashMap<>();
        }
        return from.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .collect(Collectors.toMap(func, e -> 1, Integer::sum));
    }

    /**
     * 根据统计条件计算指定字段的长整型汇总值
     *
     * @param from      数据源列表
     * @param func      映射函数，用于提取需要统计的字段
     * @return 返回一个 Map，包含字段及其对应的长整型汇总值
     */
    public static <T, K> Map<K, Integer> calculateIntegerSummary(List<T> from, Function<T, K> func) {
        // 参数非空校验
        if (from == null || from.isEmpty()) {
            return new HashMap<>();
        }
        return from.stream().filter(Objects::nonNull).collect(Collectors.toMap(func, e -> 1, Integer::sum));
    }

    /** List<A> list --> list<B>
     * T extends Serializable 为系列化对象或Object对象
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     * List 以为V为对象基于T做克隆属性后的对象的集合;
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<BaseStatusEntity> resultList =  convertList(list, BaseStatusEntity.class);
     * @param list 源集合
     * @return   List<B> 转换后的集合
     */
    @SuppressWarnings("unchecked")
    public static < T, V extends IBeanClone>  List<V> convertBySpringList(Collection<T> list, Class<V> clazz){
        if(ObjectTrue.isEmpty(list)){
            return Collections.emptyList();
        }
        List<V> result =  list.stream().map(entity -> {
            try {
                V target = (V) ReflectUtils.newInstance(clazz);
                BeanUtils.copyProperties(entity, target);
                return  target;
            } catch (Exception e) {
                log.info("cloneBeanBySpring Exception", e);
            }
            return null;
        }).collect(Collectors.toList());
        return result;
    }

        /**
     * 创建指定类的新实例
     *
     * @param clazz 要创建实例的类对象，必须实现IBeanClone接口
     * @return 成功时返回新创建的实例，失败时返回null
     */
    @SuppressWarnings("unchecked")
    public static <T extends IBeanClone>T newInstance(Class<T> clazz ){
        try {
            // 通过反射调用无参构造函数创建新实例
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.info("newInstance Exception", e);
        }
        return null;
    }


}
