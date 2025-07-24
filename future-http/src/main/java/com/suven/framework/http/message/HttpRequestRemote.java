package com.suven.framework.http.message;

import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.http.processor.url.UrlExplain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Title: HttpRequestRemote.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http get/post 接口对应参数解释,统一采摘方便后面展开业务实现;
 */


public class HttpRequestRemote implements IRequestRemote {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 是否要进行登录。
	 */
	private boolean ignoreLogin = false;

	private boolean isCdn;

	private boolean isWhite;
	/**
	 * 是否进行md5密文校验(默认需求检测)。
	 */
	private boolean isMd5 = true;
	/**
	 * 来自客户端的md5校验密文。
	 */
	private String cliMd5Sign;
	private String cliJsonSign;
	/**
	 * 服务端自行加工的md5校验密文。
	 */
	private String srvMd5Sign;
	private String srvJsonSign;
	/**
	 * 是否需要检测token
	 */
	private boolean isPostRequest;

	/**
	 * 是否Json请求
	 */
	private boolean isJsonRequest;
	/**
	 * 是否要进行版本升级检测。
	 */
	private boolean isChkUpgrade = true;
	
	private String url;
	private String clientIp;

	private long netTime;
	/**
	 * 模块名称
	 */
	private String module;

	private int dataType;


	public static HttpRequestRemote build(){
		return new HttpRequestRemote();
	}

	private boolean isToken(String token){
		return false;
	}
	
	
	/**
	 * @return 返回 isMd5。
	 */
	public boolean isMd5() {
		return isMd5;
	}

	/**
	 * @param isMd5 设置 isMd5。
	 */
	public void setMd5(boolean isMd5) {
		this.isMd5 = isMd5;
	}

	/**
	 * @return 返回 cliMd5Sign。
	 */
	public String getCliMd5Sign() {
		return cliMd5Sign;
	}

	/**
	 * @param cliMd5Sign 设置 cliMd5Sign。
	 */
	public void setCliMd5Sign(String cliMd5Sign) {
		this.cliMd5Sign = cliMd5Sign;
	}

	/**
	 * @return 返回 srvMd5Sign。
	 */
	public String getSrvMd5Sign() {
		return srvMd5Sign;
	}

	/**
	 * @param srvMd5Sign 设置 srvMd5Sign。
	 */
	public void setSrvMd5Sign(String srvMd5Sign) {
		this.srvMd5Sign = srvMd5Sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.setWhite(UrlExplain.isWhite(url));
		this.setCdn(Cdn.isCdn(url));
		this.setIgnoreLogin();
		this.setmodule();

	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return 返回 ignoreLogin。
	 */
	public boolean isIgnoreLogin() {
		return ignoreLogin;
	}

	/**
	 *  ignoreLogin 设置 ignoreLogin。
	 */
	public void setIgnoreLogin() {
		if(!isCdn && !isWhite && isPostRequest){
			this.ignoreLogin = true;
		}

	}

    public void setmodule() {
        if(null == url ){
            return;
        }
        this.module = url;
        String[] urls =  this.url.split("/");
        if(null != urls && urls.length > 1){
            this.module = this.url.split("/")[1];
        }


    }

	/**
	 * @return 返回 isChkUpgrade。
	 */
	public boolean isChkUpgrade() {
		return isChkUpgrade;
	}

	/**
	 * @param isChkUpgrade 设置 isChkUpgrade。
	 */
	public void setChkUpgrade(boolean isChkUpgrade) {
		this.isChkUpgrade = isChkUpgrade;
	}

	/**
	 * @return 返回 isPostRequest。
	 */
	public boolean isPostRequest() {
		return isPostRequest;
	}

	/**
	 * @param isPostRequest 设置 isPostReq。
	 */
	public void setPostRequest(boolean isPostRequest) {
		this.isPostRequest = isPostRequest;
	}

	public boolean isCdn() {
		return isCdn;
	}

	public void setCdn(boolean cdn) {
		isCdn = cdn;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean white) {
		isWhite = white;
	}

	public long getNetTime() {
		return netTime;
	}

	public void setNetTime(long netTime) {
		this.netTime = netTime;
	}
	public String getModule() {
		return module;
	}

	public boolean isJsonRequest() {
		return isJsonRequest;
	}

	public void setJsonRequest(boolean jsonRequest) {
		isJsonRequest = jsonRequest;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getCliJsonSign() {
		return cliJsonSign;
	}

	public void setCliJsonSign(String cliJsonSign) {
		this.cliJsonSign = cliJsonSign;
	}

	public String getSrvJsonSign() {
		return srvJsonSign;
	}

	public void setSrvJsonSign(String srvJsonSign) {
		this.srvJsonSign = srvJsonSign;
	}
}
