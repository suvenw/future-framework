package com.suven.framework.util.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TopTools {
	private static final Logger log = LoggerFactory.getLogger(TopTools.class);

	private TopTools() {

	}

	public static final String DELIMITER_INNER_ITEM = "_";
	public static final String DELIMITER_INNER_ITEM1 = ":";
	public static final String DELIMITER_INNER_ITEM2 = ",";
	public static final String DELIMITER_BETWEEN_ITEMS = "|";
	public static final String DELIMITER_BETWEEN_ITEMS2 = "#";
	public static final String ARGS_DELIMITER = " ";
	public static final String ARGS_ITEMS_DELIMITER = "\\|";





	/** 随机数对象 */
	private static final Random RANDOM = new Random((41 * 0x5DEECE66DL + 0xBL) << 5);
	
	/**
	 * 取得随机数
	 * 
	 * @param maxValue
	 *            随机的最大值
	 * @return 随机的值
	 */
	public static int getRandomInteger(int maxValue) {
		int value = 0;
		if (maxValue > 0) {
			//value = RandomUtil.nextInt(maxValue);
			value = RANDOM.nextInt(maxValue);
		}
		return value;
	}
	
	/**
	 * 把以|_分割的字符串分解成数组
	 * 
	 * @param delimiterString
	 * @return List<String[]>
	 */
	public static List<String[]> delimiterString2Array(String delimiterString) {
		if (delimiterString == null || delimiterString.trim().length() == 0) {
			return null;
		}

		String[] ss = delimiterString.trim().split(TopTools.ARGS_ITEMS_DELIMITER);
		if (ss != null && ss.length > 0) {
			List<String[]> list = new ArrayList<String[]>();
			for (int i = 0; i < ss.length; i++) {
				list.add(ss[i].split(TopTools.DELIMITER_INNER_ITEM));
			}

			return list;
		}
		return null;
	}

	/**
	 * 把以|_分割的字符串分解成HashMap,键位为字符串的0下标数值
	 * 
	 * @param delimiterString
	 * @return Map<String,String[]>
	 */
	public static Map<String, String[]> delimiterString2Map(
			String delimiterString) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		if (delimiterString == null || delimiterString.trim().length() == 0) {
			return map;
		}

		String[] ss = delimiterString.trim().split(TopTools.ARGS_ITEMS_DELIMITER);
		if (ss != null && ss.length > 0) {

			for (int i = 0; i < ss.length; i++) {
				String[] str = ss[i].split(TopTools.DELIMITER_INNER_ITEM);
				if (str.length > 0) {
					map.put(str[0], str);
				}
			}
			return map;
		}
		return map;
	}

	/**
	 * 把子项集合转换成以|_分割的字符串
	 * 
	 * @return Map<String,String[]>
	 */
	public static String delimiterCollection2String(Collection<String[]> collection) {
		if (collection == null || collection.isEmpty()) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();
		for (String[] strings : collection) {
			if (strings == null || strings.length == 0) {
				continue;
			}

			for (int i = 0; i < strings.length; i++) {
				if (i == strings.length - 1) {
					subContent.append(strings[i]).append(
							TopTools.DELIMITER_BETWEEN_ITEMS);
					continue;
				}

				subContent.append(strings[i])
						.append(TopTools.DELIMITER_INNER_ITEM);
			}
		}
		return subContent.toString();
	}
	
	/**
	 * 把子项数组转换成以|_分割的字符串
	 * 
	 * @param subArray
	 *            子项数组
	 * @return String
	 */
	public static String array2DelimiterString(String[] subArray) {
		if (subArray == null || subArray.length == 0) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();

		for (int i = 0; i < subArray.length; i++) {
			subContent.append(subArray[i]).append(TopTools.DELIMITER_INNER_ITEM);
		}

		String tmp = subContent.toString().substring(0,
				subContent.lastIndexOf(TopTools.DELIMITER_INNER_ITEM));

		return tmp + TopTools.DELIMITER_BETWEEN_ITEMS;
	}

	/**
	 * 把List中的子项数组转换成以|_分割的字符串
	 * 
	 * @param subArrayList
	 *            , 子项数组集合
	 * @return String
	 */
	public static String listArray2DelimiterString(List<String[]> subArrayList) {
		if (subArrayList == null || subArrayList.isEmpty()) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();
		for (String[] strings : subArrayList) {
			if (strings == null || strings.length == 0) {
				continue;
			}

			for (int i = 0; i < strings.length; i++) {
				if (i == strings.length - 1) {
					subContent.append(strings[i]).append(
							TopTools.DELIMITER_BETWEEN_ITEMS);
					continue;
				}

				subContent.append(strings[i])
						.append(TopTools.DELIMITER_INNER_ITEM);
			}
		}
		return subContent.toString();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
	}

	public static double roundDown(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, RoundingMode.HALF_DOWN).doubleValue();
	}

	public static double roundUp(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
	}

	public static double divideAndRoundUp(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);

		return bd1.divide(bd2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	public static double divideAndRoundDown(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);

		return bd1.divide(bd2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 计算time2距离time1时间
	 * 
	 * @param time1
	 * @param time2
	 * @return 差距
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			logger.error("", e);
		}
		return quot;
	}
	
	/**
	 * 内存列表分页
	 * @param <T>
	 * @param list
	 * @param startIndex
	 * @param fetchCount
	 * @return
	 */
	public static <T> List<T> pageResult(List<T> list, int startIndex, int fetchCount){
		if(list != null && !list.isEmpty()){
			if(startIndex >= list.size()){
				return null;
			}
			startIndex = Math.max(startIndex, 0);
			if(fetchCount <= 0){
				return list.subList(startIndex, list.size());
			}
			int toIndex = Math.min(startIndex + fetchCount, list.size());
			return list.subList(startIndex, toIndex);
		}
		
		return null;
	}
	
	
	/**
	 * 将制定的键值放入map (其中 Map<?, List<?>>)
	 * @param map  Map<?, List<?>>
	 * @param key
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void add2MapList(Map map, Object key, Object value){
		if(map != null && key != null){
			List list = (List)map.get(key);
			if(list == null){
				list = new ArrayList<>();
				map.put(key, list);
			}
			list.add(value);
		}
	}

	/**
	 * 获取列表的子集的拷贝
	 *
	 * @param <T>
	 * @param source 			源列表
	 * @param start 			开始位置
	 * @param count 			获取记录数量
	 * @return {@link List}		返回结果是另一个 ArrayList 实例
	 */
	public static <T> List<T> subListCopy(List<T> source, int start, int count) {
		if (source == null || source.isEmpty()) {
			return new ArrayList<T>(0);
		}

		int fromIndex = Math.max(start, 0);
		if (start > source.size()) {
			fromIndex = source.size();
		}

		count = Math.max(count, 0);	//增加了边界处理
		int endIndex = fromIndex + count;
		if (endIndex > source.size()) {
			endIndex = source.size();
		}
		return new ArrayList<T>(source.subList(fromIndex, endIndex));
	}

	/**
	 * 获取列表的子集的拷贝
	 *
	 * @param <T>
	 * @param  source 			源列表
	 * @param  startIndex 		开始位置
	 * @param  stopIndex 		结束位置
	 * @return {@link List}		返回结果是另一个 ArrayList 实例
	 */
	public static <T> List<T> subList(List<T> source, int startIndex, int stopIndex) {
		if (source == null || source.size() == 0) {
			return new ArrayList<T>(0);
		}

		int fromIndex = Math.max(startIndex, 0);
		if (startIndex > source.size()) {
			fromIndex = source.size();
		}

		stopIndex = Math.max(stopIndex, 0);//增加了边界处理
		stopIndex = Math.max(stopIndex, startIndex);
		if (stopIndex > source.size()) {
			stopIndex = source.size();
		}
		return new ArrayList<T>(source.subList(fromIndex, stopIndex));
	}

	/**
	 * 当列表元素不存在时，进行添加
	 *
	 * @param list 		列表
	 * @param idx 		插入位置
	 * @param element 	插入的元素
	 */
	public static <T> void addNotExist(List<T> list, int idx, T element) {
		if (!list.contains(element)) {
			list.add(idx, element);
		}
	}

	/**
	 *
	 * 根据map的value升序排序
	 *
	 * @param map
	 * @return 返回Map的key数组
	 */
	@SuppressWarnings("unchecked")
	public static <T, S> List<T> sortMapKeyByScoreValue(Map<T, S> map){

		List<Map.Entry<T, S>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<T, S>>() {
			public int compare(Map.Entry<T, S> o2, Map.Entry<T, S> o1) {
				return ((Comparable<S>) o1.getValue())
						.compareTo(o2.getValue());
			}
		});
		List<T> result = new ArrayList<T>();
		for (Map.Entry<T, S> key : list) {
			result.add(key.getKey());
		}

		return result;
	}

	/**
	 * 合并两个value 有序Map
	 * 返回list
	 * @param sortMap1
	 * @param sortMap2
	 * @param limit
	 * @return
	 */
	public static <T> List<T> mergeList(Map<T, Long> sortMap1, Map<T, Long> sortMap2, int limit) {

		sortMap1 = (sortMap1 == null ? new LinkedHashMap<T, Long>() :
				sortMap1);
		sortMap2 = (sortMap2 == null ? new LinkedHashMap<T, Long>() :
				sortMap2);

		Iterator<Map.Entry<T, Long>> iterator1 = sortMap1.entrySet().iterator();
		Iterator<Map.Entry<T, Long>> iterator2 = sortMap2.entrySet().iterator();
		List<T> list = new ArrayList<>();

		Map.Entry<T, Long> tmp1 = null;
		Map.Entry<T, Long> tmp2 = null;

		do {

			if(list.size() == limit){
				return list;
			}

			if(tmp1 == null && iterator1.hasNext()){
				tmp1 = iterator1.next();
			}

			if(tmp2 == null && iterator2.hasNext()){
				tmp2 = iterator2.next();
			}

			if(tmp1 == null || tmp2 == null){

				if(tmp1 == null && tmp2 == null){
					tmp1 = null;
					tmp2 = null;
					continue;
				}
				list.add(tmp1 != null ? tmp1.getKey() : tmp2.getKey());
				tmp1 = null;
				tmp2 = null;
				continue;
			}

			if(tmp1.getValue() < tmp2.getValue()){
				list.add(tmp2.getKey());
				tmp2 = null;
				continue;
			}

			list.add(tmp1.getKey());
			tmp1 = null;

		} while (iterator1.hasNext() || iterator2.hasNext());

		return list;

	}
	public static boolean isEmpty(Collection collection){
        return collection != null && !collection.isEmpty();
    }

	public static boolean isEmptyStr(String string){
        return string != null && !"".equals(string);
    }
	
	
	static Logger logger = LoggerFactory.getLogger(TopTools.class);
//	public static void main(String[] args) {
		// String test = "nishizhu";
		//    	
		// String encode = new BASE64Encoder().encode(test.getBytes());
		//    	
		// System.out.println("orign: " + test);
		// System.out.println("encode: " + encode );
		//    	
		// byte[] decode = new BASE64Decoder().decodeBuffer(encode);
		//    	
		// System.out.println("decode: " + new String(decode));

		// @SuppressWarnings("unused")
		// List<String[]> st =
		// delimiterString2Array("0_1_1060_10|0_2_1061_10|0_3_1062_10|");
//		List<String[]> arrayList = new ArrayList<String[]>();
//		arrayList.add(new String[] { "1", "2", "3" });
//		arrayList.add(new String[] { "2", "2", "3" });
//		arrayList.add(new String[] { "3", "2", "3" });
//		arrayList.add(new String[] { "4", "2", "3" });
//		arrayList.add(new String[] { "5", "2", "3" });
//		arrayList.add(new String[] { "6", "2", "3" });
//		String str = listArray2DelimiterString(arrayList);
//		logger.debug("str:  {}" , str);
	//
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 10000000; i++) {
//			SecureRandom.getInstance("SHA-256").nextInt(1000);
//		}
//		long end = System.currentTimeMillis();
//		System.err.println("SecureRandom getInstance: "+ (end - start));
//
//		long start = System.currentTimeMillis();
////		for (int i = 0; i < 10000000; i++) {
////			new SecureRandom().nextInt(1000);
////		}
//		long end = System.currentTimeMillis();
//		System.err.println("SecureRandom: "+ (end - start));
//
//		start = System.currentTimeMillis();
//		for (int i = 0; i < 100; i++) {
//			System.err.println(RANDOM.nextInt(1000));
//		}
//		end = System.currentTimeMillis();
//		System.err.println("RANDOM:"+ (end - start));
//		System.out.println(TopTools.roundUp(100.222, 0));
//		System.out.println(TopTools.roundUp(100.777, 0));
//	}

}
