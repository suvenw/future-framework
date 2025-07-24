package com.suven.framework.common.platform;

/**
 * 
 * @ClassName:
 * Description: 第三方登录信息类
 * @author tluo
 * @date 2015年12月17日 下午1:56:40
 */
public class ThirdLoginUser {
	
	private String openid;
	private String headimgurl;//头像地址
	private String unionid;
	
	
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	
	
}
