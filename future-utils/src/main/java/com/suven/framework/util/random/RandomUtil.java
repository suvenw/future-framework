package com.suven.framework.util.random;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机数工具类
 */
@Deprecated
public class RandomUtil{
	private static ThreadLocal<Random> threadLocal = new ThreadLocal<Random>();
	
	public static void setRandomProvider(Random random) {
		threadLocal.set(random);
	}
	
	private static Random getRandomProvider() {
		Random random = threadLocal.get();
		if(random == null) {
			String algorithm = "SHA-256";
			try {
				random = SecureRandom.getInstance(algorithm);
			} catch (NoSuchAlgorithmException e) {
				random = new SecureRandom();
			}
			threadLocal.set(random);
		}
		return random;
	}
	
	public static boolean nextBoolean() {
		return getRandomProvider().nextBoolean();	
	}
	
	public static double nextDouble() {
		return getRandomProvider().nextDouble();
	}
	
	public static float nextFloat() {
		return getRandomProvider().nextFloat();
	}
	
	public static int nextInt() {
		return getRandomProvider().nextInt();
	}
	
	public static int nextInt(int n) {
		return getRandomProvider().nextInt(n);
	}
	
	public static long nextLong() {
		return getRandomProvider().nextLong();
	}
	
	public static final int rand(int min, int max) {
		int tmp = max - min;
		if (tmp < 0) {
			throw new Error("min can mot larger then max!");
		} else if (tmp == 0) {
			return min;
		} else {
			return nextInt(tmp + 1) + min;
		}
	}
	
	public static final boolean isLuck(double chance) {
		return rand(1, 10000) <= ((int) chance * 100);
	}
}
