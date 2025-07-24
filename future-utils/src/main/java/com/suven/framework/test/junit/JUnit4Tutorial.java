package com.suven.framework.test.junit;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit 4代码示例
 *
 * @author	Edward Lee
 * @version 2013-3-3
 */
@RunWith(value = Parameterized.class)
public class JUnit4Tutorial {
	@BeforeClass
	public static void oneTimeSetUp() {
		out.println("@BeforeClass - oneTimeSetUp");
	}
	
	/**
	 * Exception Test
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void indexOutOfBoundsException() {
		List<Object> list = new ArrayList<Object>(0);
		assertNull(list.get(0));
	}
	
	/**
	 * Ignore Test
	 */
	@Ignore("ok")
	@Test
	public void ignoreTest() {
		assertEquals(0, 1); // fail
	}
	
	/**
	 * Time Test
	 */
	@Test(timeout = 1000)
	public void timeoutTest() {
		// HttpRequest
	}
	
	/*
	 * Parameterized Test
	 * 
	 * 缺点：该类中的所有测试方法都会被执行若干次。
	 */
	private int number;
	
	public JUnit4Tutorial(int number) {
		super();
		this.number = number;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 1 }, { 2 }, { 3 } };
		return Arrays.asList(data);
	}
	
	@Test
	public void parameter() {
		out.println("Parameterized Number is : " + this.number);
	}
}
