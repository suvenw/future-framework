package com.suven.framework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 对象转换器
 */
@Deprecated
public class ObjectConverter {
	private static final Log log = LogFactory.getLog(ObjectConverter.class);
	
	/**
	 * ASObject对象转换成字节数组
	 * @param obj ASObject对象
	 * @return  byte[]
	 */

	/**
	 * 对象转换成字节数组
	 * 
	 * @param obj 		对象
	 * @return byte[]
	 */
	public static byte[] object2ByteArray(Object obj) {
		if (obj == null) {
			return null;
		}

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			(new ObjectOutputStream(bos)).writeObject(obj);

			return bos.toByteArray();
		} catch (IOException ex) {
			log.error("failed to serialize obj", ex);

			return null;
		}

	}

	/**
	 * 字节数组转换成对象	 * 
	 * @param buffer
	 * @return Object
	 */
	public static Object byteArray2Object(byte[] buffer) {
		if (buffer == null || buffer.length == 0) {
			return null;
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception ex) {
			log.error("failed to deserialize obj", ex);
			return null;
		} finally{
			try{
				if(ois != null){
					ois.close();
				}
			} catch (Exception e){
				
			}
			
			try {
				bais.close();
			} catch (Exception e) {
				
			}
		}
		
	}
	
	
	 public static Map<String,Object> convertBean(Object obj) {
		 Map<String, Object> map = new HashMap<String, Object>();  
	        // System.out.println(obj.getClass());  
	        // 获取f对象对应类中的所有属性域
	        Field[] fields = obj.getClass().getDeclaredFields();
	        for (int i = 0, len = fields.length; i < len; i++) {  
	            String varName = fields[i].getName();  
	            try {  
	                // 获取原来的访问控制权限  
	                boolean accessFlag = fields[i].isAccessible();  
	                // 修改访问控制权限  
	                fields[i].setAccessible(true);  
	                // 获取在对象f中属性fields[i]对应的对象中的变量  
	                Object o = fields[i].get(obj);  
	                if (o != null)  
	                    map.put(varName, o.toString());  
	                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);  
	                // 恢复访问控制权限  
	                fields[i].setAccessible(accessFlag);  
	            } catch (IllegalArgumentException ex) {  
	                ex.printStackTrace();  
	            } catch (IllegalAccessException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	        return map;  
	 }
	 
}
