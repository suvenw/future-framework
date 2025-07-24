package com.suven.framework.util.constants;


import com.suven.framework.util.PropertiesUtil;

/**   
 * @Title: PropertiesParam.java 
 * @author Joven.wang   
 * @date   2017年2月4日
 * @version V1.0  
 * Description: TODO(说明)  
 */

public class PropertiesParam {

	
	
	private final static boolean sys_redis_cluster_server_write_default = true;
    private final static String sys_redis_cluster_server_write = "top.sys.redis.cluster.server.write";

    private final static String sys_redis_cluster_server_read = "top.sys.redis.cluster.server.write";
	private final static boolean sys_redis_cluster_server_read_default = true;

    private final static String sys_dao_is_open_redis_cache = "top.sys.redis.is.open.cache";
	private final static boolean sys_dao_is_open_redis_cache_default = true;

    private final static String sys_file_upload_path = "top.sys.file.upload.path";
    private final static String sys_file_upload_path_default = "D://upload";


    private final static String sys_file_upload_wifi_size = "top.sys.file.upload.wifi.size";
    private final static long sys_file_upload_wifi_size_default = 6L * 1024 * 1024;

    private final static String sys_file_upload_g2g3_size = "top.sys.file.upload.g2g3.size";
    private final static long sys_file_upload_g2g3_size_default = 6L * 1024 * 1024;

    private final static String sys_file_upload_byte_size = "top.sys.file.upload.byte.size";
    private final static long sys_file_upload_byte_size_default = 6L * 1024 * 1024;


    private final static String sys_server_servlet_context_path = "server.servlet.context-path";
    private final static String sys_server_servlet_context_path_default = "top";

    private final static String sys_server_dubbo_registry_address = "dubbo.registry.address";
    private final static String sys_server_dubbo_registry_address_default = "nacos://192.168.25.200:8848";




    //上传URL
    /** 缓存验证码方式， true 为vm内存，false 为redis缓存,分钟  **/
    public final static String fileUploadPath(){
        return  PropertiesUtil.getString(sys_file_upload_path,sys_file_upload_path_default);
    }
    //单块上传文件大小限制
    /** 缓存验证码方式， true 为vm内存，false 为redis缓存,分钟  **/
    public final static long fileUploadWifiSize(){
        return  PropertiesUtil.getLong(sys_file_upload_wifi_size, sys_file_upload_wifi_size_default);
    }
    /** 缓存验证码方式， true 为vm内存，false 为redis缓存,分钟  **/
    public final static long fileUploadG2g3Size(){
        return  PropertiesUtil.getLong(sys_file_upload_g2g3_size, sys_file_upload_g2g3_size_default);
    }

    //上传文件总大小限制
    /** 缓存验证码方式， true 为vm内存，false 为redis缓存,分钟  **/
    public final static long fileUploadByteSize(){
        return  PropertiesUtil.getLong(sys_file_upload_byte_size, sys_file_upload_byte_size_default);
    }



    /** 缓存验证码方式， true 为vm内存，false 为redis缓存,分钟  **/
    public final static String  sysServerDubboRegistryAddress(){
        return  PropertiesUtil.getString(sys_server_dubbo_registry_address, sys_server_dubbo_registry_address_default);
    }




}
