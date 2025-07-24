/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.util.location;

/**
 * <pre>
 * 有关经纬度计算的工具。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */

public class LonLatUtil 
{
	public static final double R = 6371.004;
	/**
	 * 根据给定的两个经纬度计算两地之间的距离，单位km
	 * @param lon1	经度1
	 * @param lat1	纬度1
	 * @param lon2	经度2
	 * @param lat2	纬度2
	 * @return	两地距离
	 */
	public static double getDistance(double lon1, double lat1, double lon2, double lat2)
	{
		double x = changeToRad(lon1);
		double y = changeToRad(lat1);
		double a = changeToRad(lon2);
		double b = changeToRad(lat2);
		double rad = Math.acos(Math.cos(y) * Math.cos(b) * Math.cos(x - a) + Math.sin(y) * Math.sin(b));
		if (rad > Math.PI)
			rad = Math.PI * 2 - rad;
		return R * rad;
	}
	
	/**
	 * 将角度转化为弧度
	 * @param angle	角度
	 * @return	弧度
	 */
	public static double changeToRad(double angle)
	{
		return angle / 180 * Math.PI;
	}
	

}
