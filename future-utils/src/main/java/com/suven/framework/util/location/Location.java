package com.suven.framework.util.location;


/**
 * @Title: Location.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 经纬度实现Bean类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


public class Location {
	
	//经度 Longitude 简写Lng
	 private double longitude; 
	//纬度 Latitude 简写Lat
    private double latitude;  
    
    private double distance;  
    
  
    public Location(double latitude, double longitude) {  
        this.latitude = format(latitude);  
        this.longitude = format(longitude);  
    }  
    public Location(double latitude, double longitude,double distance) {  
        this.latitude = format(latitude);  
        this.longitude = format(longitude); 
        this.distance = format(distance);
    }  
    
    private double format(double doubles){
    	return LatitudeLontitudeUtil.formatDouble(doubles);
    }
  
    public double getLatitude() {  
        return latitude;  
    }  
  
    public void setLatitude(double latitude) {  
        this.latitude = format(latitude);  
    }  
  
    public double getLongitude() {  
        return longitude;  
    }  
  
    public void setLongitude(double longitude) {  
        this.longitude = format(longitude);  
    }

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}  
    
  
}  