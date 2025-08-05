package com.suven.framework.util.location;



/**
 * Title: LocationPoint.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 经纬度实现Bean类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


public class LocationPoint {

    /** 左上角 */  
    private Location leftTop = null;
    /** 右上角 */  
    private Location rightTop = null;  
    /** 左下角 */  
    private Location leftBottom = null; 
    /** 右下角 */  
    private Location rightBottom = null;
    
    public LocationPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocationPoint(Location leftTop, Location rightTop,
			Location leftBottom, Location rightBottom) {
		super();
		this.leftTop = leftTop;
		this.rightTop = rightTop;
		this.leftBottom = leftBottom;
		this.rightBottom = rightBottom;
	}
	
	public Location getLeftTop() {
		return leftTop;
	}
	public void setLeftTop(Location leftTop) {
		this.leftTop = leftTop;
	}
	public Location getRightTop() {
		return rightTop;
	}
	public void setRightTop(Location rightTop) {
		this.rightTop = rightTop;
	}
	public Location getLeftBottom() {
		return leftBottom;
	}
	public void setLeftBottom(Location leftBottom) {
		this.leftBottom = leftBottom;
	}
	public Location getRightBottom() {
		return rightBottom;
	}
	public void setRightBottom(Location rightBottom) {
		this.rightBottom = rightBottom;
	}  
    
    
}
