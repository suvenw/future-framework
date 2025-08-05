package com.suven.framework.util.ips;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;


/**
 * Title: InetUtil.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) ip查询工具实现类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class Ip2regionDbSearch{
	 private static volatile DbSearcher dbSearcher;  
	 private static String dbPath;
	      
	 private Ip2regionDbSearch(){
	    	
	 }

	public static  DbSearcher getDbSearcher(String path) throws Exception{
	 	dbPath = path;
		if(dbSearcher == null){
			synchronized(Ip2regionDbSearch.class){
				if(dbSearcher == null){
					dbSearcher = new DbSearcher(new DbConfig(),dbPath);
				}
			}
		}
		return dbSearcher;
	}

	 public static  DbSearcher getDbSearcher() throws Exception{  
		 if(dbSearcher == null){  
			synchronized(Ip2regionDbSearch.class){  
				 if(dbSearcher == null){  
					 dbSearcher = new DbSearcher(new DbConfig(),dbPath);  
				 }  
			 }  
		 }  
		 return dbSearcher;  
	}

	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		Ip2regionDbSearch.dbPath = dbPath;
	}
}
