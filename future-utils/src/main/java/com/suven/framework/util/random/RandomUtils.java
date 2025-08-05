/*
 * com.toone.framework.utils  2015-4-14
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.suven.framework.util.random;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/** 
 * ClassName: RandomUtils
 * Description: TODO(这里用一句话描述这个类的作用) 
 * date 2015-4-14 下午2:27:21
 *  
 */
public class RandomUtils {

	private static final Random random = new Random();
	/**
	 * 
	 * Title: randomUUID 
	 * Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String randomUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
//	public static void main(String[] args) {
//		System.out.println("ABCDEFGHJKLMNPQRSTUVWXYZ0123456789".length());
//		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
//		Random random = new Random();
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < 6; i++) {
//			int num = random.nextInt(34);
//			buf.append(str.charAt(num));
//		}
//		System.out.println(buf.toString());
//
//		System.out.println(randomString(4));
//	}
	
	/** 产生一个随机的字符串，排除难分辨的I/l、O/0 */
	public static String randomString2(int length) {
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(str.length());
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的字符串,排除难分辨的I/l、O/0 j/1*/
	public static String randomString(int length) {
		String str = "abcdefghkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(str.length());
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的数字*/
	public static String randomStringOnlyNumber(int length) {
		String str = "0123456789";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(10);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的字符串，适用于JDK 1.7 */
	public static String randomString17(int length) {
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
		}
		return builder.toString();
	}

	/**
	 * 产生两个数之间的随机数
	 * @param min 小数
	 * @param max 比min大的数
	 * @return int 随机数字
	 */
	public static int num(int min, int max)
	{
		return min + random.nextInt(max - min);
	}

	/**
	 * 产生0--num的随机数,不包括num
	 * @param num 数字
	 * @return int 随机数字
	 */
	public static int num(int num)
	{
		return random.nextInt(num);
	}

	/**
	 * 生成指定位数据的随机码,生成数类型;-1.随机数字+大写+小写;  0:全部数字;1.全部大写;2.全部小写
	 * @param numLength 长度,即生成数据的位数
	 * @param randomType 生成数类型;-1.随机数字+大写+小写;  0:全部数字;1.全部大写;2.全部小写
	 * @return
	 */
	public static String verifyCode(int numLength, int randomType) {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < numLength; i++){
			if(randomType == -1){
				randomType= random.nextInt(3);
			}
			switch (randomType){
				case 0://数据
					int code1 = random.nextInt(10);
					str += code1;
					break;
				case 1://大写
					char code2 = (char)(random.nextInt(26)+65);
					str += code2;
					break;
				case 2://小写
					char code3 = (char)(random.nextInt(26)+97);
					str += code3;
					break;
			}
		}
		return str;
	}

	public static String randomShortNum(){
		int  maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
				'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		while(count < 8){
			i = Math.abs(random.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count ++;
			}
		}
		return pwd.toString();
	}
}
