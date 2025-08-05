package com.suven.framework.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suven.framework.util.io.FileToByteArrayUtils;

import java.io.File;
import java.io.IOException;

public class FileUploadHelper {
	
	private static Logger logger = LoggerFactory.getLogger(FileUploadHelper.class);
	

	/**
	 * 得到缓存位置
	 * 
	 * @return
	 */
	public static File getFileConfigPath(String rootPath) {
		File path = new File(rootPath);
		if(!path.exists()){
			boolean bool = path.mkdirs();
			logger.info("create path:{}, success:{}", path.getAbsolutePath(), bool);
		}
		return path;
	}
	public static void mkDirs(String destPath){
		try {
			File file = new File(destPath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}catch (Exception e){}
	}
	
	/**
	 * 删除缓存文件
	 * 
	 * @param urlPath
	 */
	public static void deleteCacheFile(String rootPath,String urlPath){
		File file = new File(getFileConfigPath(rootPath), urlPath);
		if(file.exists()){
			file.deleteOnExit();
			logger.info("delete new file:{}", file.getAbsolutePath());
		}
	}
	
	/**
	 * 得到缓存的文件
	 * 
	 * @param customPath
	 * @return
	 */
	public  static File getCacheFile(String rootPath,String customPath){
		File file = new File(getFileConfigPath(rootPath), customPath);
		if(!file.getParentFile().exists()){
			try {
				file.getParentFile().mkdirs();
			} catch (Exception e) {
				logger.info("Create new Parent File failed:{}" ,file.getParentFile() );
			}
		}
		if(!file.exists()){
			try {
				file.createNewFile();
				logger.info("Create new file:{}", file.getAbsolutePath());
			} catch (IOException e) {
				logger.info("Create new file failed:{}" , customPath);
			}
		}
		return file;
	}
	
	/**
	 * 追加缓存文件
	 * 返回当前位子
	 * @param urlPath
	 * @param filePart
	 * @param position
	 */
	public static long cachePositionFilePart(String rootPath,String urlPath , byte[] filePart, long position){
		File file = new File(getFileConfigPath(rootPath), urlPath);
		return FileToByteArrayUtils.toAccessFilePosition(file,filePart,position);
	}
	


}
