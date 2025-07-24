package com.suven.framework.util.location;

import java.math.BigDecimal;


/**
 * @Title: LatitudeLontitudeUtil.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 经纬度算法工具实现类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class LatitudeLontitudeUtil {  
  
    /** 地球半径 */  
    private static final double EARTH_RADIUS = 6371000;  
    /** 范围距离 */  
    private double distance;  

  
    private LatitudeLontitudeUtil(double distance) {  
        this.distance = distance;  
    }  
  
    private LocationPoint getRectangle4Point(double lat, double lng) {
  
        // float dlng = 2 * asin(sin(distance / (2 * EARTH_RADIUS)) / cos(lat));  
        // float dlng = degrees(dlng) // 弧度转换成角度  
        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(lat));  
        dlng = Math.toDegrees(dlng);  
  
        // dlat = distance / EARTH_RADIUS  
        // dlng = degrees(dlat) # 弧度转换成角度  
        double dlat = distance / EARTH_RADIUS;  
        dlat = Math.toDegrees(dlat); // # 弧度转换成角度  
  
        // left-top : (lat + dlat, lng - dlng)  
        // right-top : (lat + dlat, lng + dlng)  
        // left-bottom : (lat - dlat, lng - dlng)  
        // right-bottom: (lat - dlat, lng + dlng)  
        Location left_top = new Location(lat + dlat, lng - dlng);
        Location  right_top = new Location(lat + dlat, lng + dlng);  
        Location  left_bottom = new Location(lat - dlat, lng - dlng);  
        Location  right_bottom = new Location(lat - dlat, lng + dlng);  
        LocationPoint point = new LocationPoint(left_top,right_top,left_bottom,right_bottom);
        return point;
  
    }  
  
    public static double hav(double theta) {  
        double s = Math.sin(theta / 2);  
        return s * s;  
    }  
  
    /**
     * 结算两个经纬度的距离
     * @param lat0 纬度0
     * @param lng0 经度0
     * @param lat1 纬度1
     * @param lng1 经度1
     * @return
     */
    public static double getDistance(double lat0, double lng0, double lat1,  double lng1) {  
        // from math import sin, asin, cos, radians, fabs, sqrt  
  
        // def hav(theta):  
        // s = sin(theta / 2)  
        // return s * s  
  
        // def get_distance_hav(lat0, lng0, lat1, lng1):  
        // "用haversine公式计算球面两点间的距离。"  
        // # 经纬度转换成弧度  
        // lat0 = radians(lat0)  
        // lat1 = radians(lat1)  
        // lng0 = radians(lng0)  
        // lng1 = radians(lng1)  
  
        // dlng = fabs(lng0 - lng1)  
        // dlat = fabs(lat0 - lat1)  
        // h = hav(dlat) + cos(lat0) * cos(lat1) * hav(dlng)  
        // distance = 2 * EARTH_RADIUS * asin(sqrt(h))  
  
        // return distance  
        lat0 = Math.toRadians(lat0);  
        lat1 = Math.toRadians(lat1);  
        lng0 = Math.toRadians(lng0);  
        lng1 = Math.toRadians(lng1);  
  
        double dlng = Math.abs(lng0 - lng1);  
        double dlat = Math.abs(lat0 - lat1);  
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);  
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));  
        distance =  formatDouble(distance);
        return distance;  
    }  
  
    public static LocationPoint getRectangle4Point(double lat, double lng, double distance) {  
        LatitudeLontitudeUtil llu = new LatitudeLontitudeUtil(distance);  
        LocationPoint point =  llu.getRectangle4Point(lat, lng);  
        return point;  
    }  
  
    /**
     * 获取经纬度与距离的结算sql
     * @param latitude 纬度
     * @param longitude 经度
     * @param distance 距离
     * @return
     */
    public static String getLocationPointSQLWhere(double latitude, double longitude, double distance){
    	LocationPoint point = getRectangle4Point(latitude, longitude, distance);
    	String lat = " latitude > "  +  point.getLeftBottom().getLatitude() 
    			+ " AND latitude < " +  point.getLeftTop().getLatitude() ;
    	if(point.getLeftBottom().getLatitude() > point.getLeftTop().getLatitude() ){
    		lat =  " latitude < "  +  point.getLeftBottom().getLatitude() 
    		+ " AND latitude > " +  point.getLeftTop().getLatitude() ;
    	}
    	
    	String lon = " longitude > "  +  point.getLeftTop().getLongitude()
    			+ " AND longitude < "  +  point.getRightTop().getLongitude(); 
    	if(point.getLeftTop().getLongitude() > point.getRightTop().getLongitude() ){
    		lon =  " longitude < "  +  point.getLeftTop().getLongitude() 
    		+ " AND longitude > " +  point.getRightTop().getLongitude() ;
    	}
    	String where = " WHERE " + lat + " AND " +lon;
    	return where;
    }
    public static double formatDouble(double doubles){//格式化数据，保留6个小数
        BigDecimal bg = new BigDecimal(doubles);  
        double f1 = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();  
        return f1;
    }

    
//    public static void main(String[] args) {
//        double lat = -30.5001567;
//        double lng = -120.500;
//        double distance = 500d;
//        LocationPoint point = LatitudeLontitudeUtil.getRectangle4Point(lat, lng, distance);
//        String sql = "SELECT * FROM place WHERE latitude > "
//                +  point.getLeftBottom().getLatitude() + " AND latitude < "
//                +  point.getLeftTop().getLatitude() + " AND longitude > "
//                +  point.getLeftTop().getLongitude() + " AND longitude < "
//                +  point.getRightTop().getLongitude();
//        System.out.println(sql);
//        String str = LatitudeLontitudeUtil.getLocationPointSQLWhere(lat, lng, distance);
//        System.out.println(str);
//
//        double lat1 = -30.495503391970406;
//        double lng1 = -120.49261708577215;
//        double d = LatitudeLontitudeUtil.getDistance(lat, lng, lat1, lng1);
//        System.out.println(d);
//        System.out.println(formatDouble(lat1));
//
//    }
   
}