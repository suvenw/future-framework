package com.suven.framework.util.zip;


import com.suven.framework.util.crypt.Base64Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip压缩及base64工具类
 * @author summerao
 *
 */
public class GzipBase64Util {
	/**
	 * 先gzip再base64
	 * @param message
	 * @return
	 * @throws IOException
	 */
	public static String GzipBase64(String message) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
  	  	GZIPOutputStream zos = new GZIPOutputStream(bos);
        zos.write(message.getBytes());  
        zos.close();  
        String str = new String(Base64Util.encode(bos.toByteArray()));
        return str;
	}
	/**
	 * 解压
	 * @param encdata
	 * @return
	 * @throws IOException
	 */
	public static String decompressData(String encdata) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(decodeBase64(encdata));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
      return new String(out.toByteArray());
	}
	  public static byte[] decodeBase64(String s) {  
	        if (s == null)  
	            return null;  
	        return Base64Util.decode(s);
	    }  
}
