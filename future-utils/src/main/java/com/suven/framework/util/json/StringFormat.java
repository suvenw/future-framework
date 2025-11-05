package com.suven.framework.util.json;


import java.util.Collection;
import java.util.Iterator;

/**
 * Title: StringFormat.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串,HelloWorld->hello_world
 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
 * 例如：HELLO_WORLD->HelloWorld
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class StringFormat {

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
	 * 例如：HelloWorld->hello_world
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线小写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && !name.isEmpty()) {
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (Character.isUpperCase(c)) {
					if (i > 0) {
						result.append("_");
					}
					result.append(Character.toLowerCase(c));
				} else {
					result.append(c);
				}
			}

		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String[] camels = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.isEmpty()) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将属性类型集合转换成字符串数组;
	 * @param collec
	 * @return
	 */
	public static String[] toArray(Collection<?> collec) {
		if (null == collec || collec.isEmpty()) {
			return null;
		}
		String[] r = new String[collec.size()];
		int i = 0 ;
        for (Object o : collec) {
            r[i] = String.valueOf(o);
            i++;
        }
		return r;
	}
	/**
	 * 将属性类型集合转换成字符串数组;
	 * @param collec
	 * @return
	 */
	public static String toJdbcString(Collection<?> collec) {
		if (null == collec || collec.isEmpty()) {
			return null;
		}
		String r = "";
        for (Object o : collec) {
            r += "," + o;
        }
		return r.substring(1);
	}

	//首字母转小写
	public static String toLowerCaseFirstOne(String name)
	{
        char[] cs = name.toCharArray();
        if(cs[0]<65 || cs[0]>90){
            return name;
        }
        cs[0]+=32;
        return String.valueOf(cs);

	}
	//首字母转大写
	public static String toUpperCaseFirstOne(String name)
	{
        char[] cs = name.toCharArray();
        if(cs[0]<97 || cs[0]>122){
            return name;
        }
        cs[0]-=32;
        return String.valueOf(cs);
	}


	
//	public static void main(String[] args) {
//		Collection<Page> list = new HashSet<Page>();
//
//		list.add(new Page(10));
//		list.add(new Page(11));
//		list.add(new Page(12));
//		list.add(new Page(13));
//		list.toString();
//		String str = new DecimalFormat("###.###").format(123456);//.format("%d.%02d.%03d", 123456);
//		System.out.println(toLowerCaseFirstOne("ABC"));
//        System.out.println(toUpperCaseFirstOne("abcC"));
//	}
	
}
