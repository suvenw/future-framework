package com.suven.framework.http.message;


import com.suven.framework.http.api.ApiDesc;

/**
 * @Title: HttpRequestGetMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http post 接口公共基础参数实现类;
 */

public class HttpRequestPostMessage extends HttpRequestGetMessage {

	@ApiDesc(value= "客户端申请的appId( 100000001)",required=1 )
	private int appId;     //客户端 XXX登录申请的appid
	@ApiDesc(value= "客户端申请的appId( 100000001)",required=0 )
	private long userId;       //用户Id，pid
	@ApiDesc(value= "令牌,需登录的接口必传,有效期7天,通过refreshToken 更新",required=0 )
	private String accessToken;     //令牌
	@ApiDesc(value= "设备标识",required=1 )
	private String device;   //设备标识
	@ApiDesc(value= "客户端操作系统版本号",required=0 )
	private String sysVersion;//客户端手机系统版本号
	@ApiDesc(value= "时间戳13位1387614995111",required=1 )
	private long times;      //时间戳13位 1387614995111
	@ApiDesc(value= "客户端推广发部渠道编码,见渠道表",required=0 )
	private int channel;      //app第三方渠道号
	@ApiDesc(value= "用户登陆ip",required=0 )
	private String ip;
	private String uri;
	private String brand;

	public HttpRequestPostMessage(){}
	
    public static HttpRequestPostMessage valueOf(HttpRequestGetMessage cmessage){
		if(cmessage ==null){
			return null;
		}		
		HttpRequestPostMessage message = new HttpRequestPostMessage();
		message.setPlatform(cmessage.getPlatform());
		message.setSign(cmessage.getSign());
		message.setJsonSign(cmessage.getJsonSign());
		message.setVersion(cmessage.getVersion());
		return message;
	}


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "RequestMessage{" +
				"userId=" + userId +
				", appId=" + appId +
				", accessToken='" + accessToken + '\'' +
				", device='" + device + '\'' +
				", sysVersion='" + sysVersion + '\'' +
				", times=" + times +
				", channel=" + channel +
				", ip='" + ip + '\'' +
				", uri='" + uri + '\'' +
				'}';
	}




}
