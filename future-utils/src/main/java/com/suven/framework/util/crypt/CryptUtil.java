package com.suven.framework.util.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Random;


/**
 * @Title: CryptUtil.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 加密解密工具
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class CryptUtil {
	private static Logger logger = LoggerFactory.getLogger(CryptUtil.class);
	private static final String PASSWORD_CRYPT_KEY = "base_64Pass!live";
	private final static String DES_CRYPT = "DES";
	private final static String AES_CRYPT = "AES";
	private final static String AES_ECB_CRYPT = "AES/ECB/PKCS5Padding";
	private final static String RSA_CRYPT = "RSA";
	private final static String MD5_CRYPT = "MD5";
	private static final String URF_8 = "UTF-8";


	/**
	 * 随机数据来源
	 */
	@SuppressWarnings("unused")
	private final static String[] UPPERCASE = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "0", "Q", "W", "E", "R", "T", "Y", "U", "I", "O",
			"P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C",
			"V", "B", "N", "M" };

	/**
	 * 随机数据来源
	 */
	@SuppressWarnings("unused")
	private final static String[] LOWERCASE = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "0", "q", "w", "e", "r", "t", "y", "u", "i", "o",
			"p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c",
			"v", "b", "n", "m" };



	/**
	 * MD5 摘要计算(String).
	 * @param  src
	 * @throws Exception
	 * @return {@link String}
	 */
	public static String md5(String src){
		try {
			logger.debug("请求参数加密时的原始值："+src);
			MessageDigest alg = MessageDigest.getInstance(MD5_CRYPT);
			byte[] md5Byte =  alg.digest(src.getBytes(URF_8));
			return parseByte2Hex(md5Byte);
		} catch (Exception e) {}
		return "";
	}

	/**
	 * MD5 摘要计算(String).
	 * @param  bytes
	 * @throws Exception
	 * @return {@link String}
	 */
	public static String md5(byte[] bytes){
		try {
			logger.debug("请求参数加密时的原始值："+ String.valueOf(bytes));
			MessageDigest alg = MessageDigest.getInstance(MD5_CRYPT);
			byte[] md5Byte =  alg.digest(bytes);
			return parseByte2Hex(md5Byte);
		} catch (Exception e) {}
		return "";
	}
	/**
	 * 加密
	 *
	 * @param  content 			数据源
	 * @param  password 			密钥，长度必须是8的倍数
	 * @return String		返回加密后的数据
	 * @throws Exception
	 */
	private static String encrypt(String content, String password) throws Exception {
		byte[] contentBytes = content.getBytes(URF_8);
		byte[] passwordBytes = password.getBytes(URF_8);
		byte[] encryptedBytes = getDESCipher(contentBytes, passwordBytes, Cipher.ENCRYPT_MODE);
		return parseByte2Hex(encryptedBytes);
	}

	/**
	 * 解密
	 *
	 * @param content 		数据源
	 * @param password 		密钥，长度必须是8的倍数
	 * @return byte[]	返回解密后的原始数据
	 * @throws Exception
	 */
	public static String decrypt(String content, String password) throws Exception {
		byte[] contentBytes  = parseHex2Byte(content);
		byte[] passwordBytes = password.getBytes(URF_8);
		byte[] decryptedBytes = getDESCipher(contentBytes, passwordBytes, Cipher.DECRYPT_MODE);
		return new String(decryptedBytes, URF_8);
	}

	private static  byte[] getDESCipher(byte[] contentBytes, byte[] passwordBytes, int cipherMode )throws Exception{
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(passwordBytes);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_CRYPT);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES_CRYPT);
		cipher.init(cipherMode, securekey, sr);
		return cipher.doFinal(contentBytes);
	}




	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHex2Byte(String hexStr) {
		if (hexStr.length() < 1){
			return null;
		}
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * @param bytes
	 * @return
	 */
	public static String parseByte2Hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * AES密码解密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String aesDecryptPassword(String data) {
		if (data == null){
			return null;
		}
		try {
			return aesDecrypt(data, PASSWORD_CRYPT_KEY);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 密码解密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decryptPassword(String data) {
		if (data == null){
			return null;
		}
		try {
			return decrypt(data, PASSWORD_CRYPT_KEY);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 密码加密
	 *
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final static String encryptPassword(String password) {
		if (password != null) {
			try {
				return encrypt(password, PASSWORD_CRYPT_KEY);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return null;
	}



	/**
	 * AES加密字符串
	 *
	 * @param content 需要被加密的字符串
	 * @param password  加密需要的密码
	 * @return 密文
	 */
	public static String aesPassEncrypt(String content, String password){
		try {
			byte[] contentBytes = content.getBytes(URF_8);
			byte[] passwordBytes = password.getBytes(URF_8);
			byte[] encryptedBytes = getAESCipherInstance(contentBytes, passwordBytes, Cipher.ENCRYPT_MODE);
			return parseByte2Hex(encryptedBytes);
		}catch ( Exception e){

		}
		return null;
	}


	/**
	 * AES加密字符串
	 *
	 * @param content 需要被加密的字符串
	 * @param password  加密需要的密码
	 * @return 密文
	 */
	public static String aesEncrypt(String content, String password) throws Exception{
		byte[] contentBytes = content.getBytes(URF_8);
		byte[] passwordBytes = password.getBytes(URF_8);
		byte[] encryptedBytes = getAESCipherInstance(contentBytes, passwordBytes, Cipher.ENCRYPT_MODE);
		return parseByte2Hex(encryptedBytes);
	}

	/**
	 * 解密AES加密过的字符串
	 * @param content 需要被加密的字符串
	 * @param password  加密需要的密码
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String content, String password)throws Exception {
		byte[] contentBytes  = parseHex2Byte(content);
		byte[] passwordBytes = password.getBytes(URF_8);
		byte[] decryptedBytes = getAESCipherInstance(contentBytes, passwordBytes, Cipher.DECRYPT_MODE);
		return new String(decryptedBytes, URF_8);
	}


	private static byte[] getAESCipherInstance(byte[] contentBytes, byte[] passwordBytes, int cipherMode) throws Exception{
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(passwordBytes);
		KeyGenerator kgen = KeyGenerator.getInstance(AES_CRYPT);
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
		byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥


		SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_CRYPT);// 转换为AES专用密钥
		Cipher cipher = Cipher.getInstance(AES_ECB_CRYPT);// 创建密码器
		cipher.init(cipherMode, key);// 初始化为解密模式的密码器

		byte[] result = cipher.doFinal(contentBytes);
		return result; // 明文

	}

	/**
	 * 异或加密和解密
	 * @param origin
	 * @param signKeyBytes
	 * @return
	 */
	public static byte[] xorCoding(byte[] origin,byte[] signKeyBytes) {
		int signKeyIndex = signKeyBytes.length;
		byte[] master = new byte[origin.length];
		for (int i = 0, len = origin.length; i < len; i++) {
			master[i] = (byte) (origin[i] ^ signKeyBytes[i % signKeyIndex]);
		}
		return master;
	}

	/**
	 * 异或加密和解密
	 * @param content
	 * @param signKey
	 * @return
	 */
	public static String xorCoding(String content,String signKey) {
		byte[] signKeyBytes = signKey.getBytes();
		byte[] origin = content.getBytes();
		byte[] master =  xorCoding(origin,signKeyBytes);
		return new String( master);
	}

	/**
	 * TODO.生成MD5加密的随机盐
	 *
	 * @param length
	 * @return
	 */
	public static String generateSalt(int length) {

		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			result.append(UPPERCASE[random.nextInt(UPPERCASE.length - 1)]);
		}
		return result.toString();
	}


	private static void aesTest(){
		try {
			String ss="H@s0zRed!fiNger8";
			String key = "1234567890abcdef";
			String de = aesEncrypt(ss, key);
			System.out.println("aes encrypt : "+de);
			String en = aesDecrypt(de, key);
			System.out.println("aes encrypt : "+en);

			String spass=    aesEncrypt("qwe123", ss)      ;
			System.out.println("spass aes encrypt : "+spass);
			String spass2=    aesEncrypt("qwe123", ss)      ;
			System.out.println("spass aes encrypt : "+spass2);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void desTest(){
		try {
			String strs ="1234567890qwe";
			String ss = CryptUtil.encryptPassword(strs);
			System.out.println("des encrypt : "+ ss);
			ss = CryptUtil.decryptPassword(ss);
			System.out.println("des decrypt : "+ ss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
