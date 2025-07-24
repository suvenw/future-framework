/*
 * com.toone.web.goldfinger.dto  2015-4-30
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.suven.framework.common.platform;

/** 
 * @ClassName: FingerJMSMessage 
 * Description: TODO(这里用一句话描述这个类的作用)
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-4-30 下午2:50:16 
 *  
 */
public class FingerJMSMessage {
	private long userId;
	private String sessionId;
	private String padSign;
	
	/** 
	 * @return userId 
	 */
	public long getUserId() {
		return userId;
	}
	/** 
	 * @param userId 要设置的 userId 
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/** 
	 * @return sessionId 
	 */
	public String getSessionId() {
		return sessionId;
	}
	/** 
	 * @param sessionId 要设置的 sessionId 
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/** 
	 * @return padSign 
	 */
	public String getPadSign() {
		return padSign;
	}
	/** 
	 * @param padSign 要设置的 padSign 
	 */
	public void setPadSign(String padSign) {
		this.padSign = padSign;
	}
}
