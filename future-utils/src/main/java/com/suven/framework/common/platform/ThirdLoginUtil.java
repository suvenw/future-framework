package com.suven.framework.common.platform;

import com.suven.framework.util.json.JsonUtils;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 
 * ClassName: WeixinUtil
 * Description: 公众平台通用接口工具类
 * @author tluo
 * date 2015年12月16日 下午7:16:35
 */
public class ThirdLoginUtil {
	private static Logger log = LoggerFactory.getLogger(ThirdLoginUtil.class);
	
	private static int timeout = 3000;

	/**
	 * 校验qq登录时MD5值是否正确
	 * @param:qqinfo
	 * @param:MD5Secret
	 * @param:MD5qqinfo
	 * @return
	 */
	public static Boolean checkLoginParm(String parm, String MD5Secret,String MD5Parm) {
		String parmSign = DigestUtils.md5DigestAsHex(StringUtils.getBytesUtf8(parm+MD5Secret));
		log.info("parmSign="+parmSign+",MD5Parm="+MD5Parm+",MD5Secret="+MD5Secret);
        return MD5Parm.equals(parmSign);		
    }

	/**
	 * 获取微信登录的AccessToken
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 */
	public static WeixinAccessToken getAccessToken(String appid, String appsecret,String code) {
		String requestUrl = ThirdLoginTypeUtil.WEIXIN_ACCESS_TOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		log.info("getAccessToken requestUrl="+requestUrl);
		JSONObject jsonObject = ThirdLoginUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		WeixinAccessToken weixinAccessToken = null;
		if (null != jsonObject) {
			log.info("code={},accessTokenJson={}",code,jsonObject.toString());
			if (jsonObject.containsKey("errcode")) {
				log.error("code={},get weixinAccessToken fail！,errcode={}",code,jsonObject.getString("errcode"));
				return weixinAccessToken;
			}
			try {
				String token = jsonObject.getString("access_token");
				String refreshToken = jsonObject.getString("refresh_token");
				String openid = jsonObject.getString("openid");
				String scope = jsonObject.getString("scope");
				int expires = jsonObject.getInteger("expires_in");
				weixinAccessToken = new WeixinAccessToken();
				weixinAccessToken.setAccessToken(token);
				weixinAccessToken.setExpires_in(expires+"");
				weixinAccessToken.setOpenid(openid);
				weixinAccessToken.setScope(scope);
				weixinAccessToken.setRefresh_token(refreshToken);

				log.info("get weixinAccessToken success, code={},token={}", code, token);
			} catch (Exception e) {
				// 获取token失败
				log.error("code="+code+",analyze weixinAccessToken fail,"+e.getMessage(),e);
			}
		}else{
			log.info("get weixinAccessToken is blank");
		}
		return weixinAccessToken;
	}


	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			httpUrlConn.setConnectTimeout(timeout);
			httpUrlConn.setReadTimeout(timeout);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JsonUtils.parseObject(buffer.toString(),JSONObject.class);
		} catch (ConnectException ce) {
			log.error("connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 微信第三方登录获取用户信息
	 * Title: getUserInfo
	 * Description: 获取用户的信息
	 * @param @param openId
	 * @param @param accessToken
	 * @param @return 设定文件
	 * @return WXUser 返回类型
	 */
	public static ThirdLoginUser getWeiXinUser(String openId, String accessToken) {
		ThirdLoginUser thirdLoginUser = null;
		String requestUrl = ThirdLoginTypeUtil.WEIXIN_GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		log.info("getWeiXinUser requestUrl="+requestUrl);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				log.info("accessToken={},weixinUserInfoJson={}",accessToken,jsonObject.toString());
				if (jsonObject.containsKey("errcode")) {
					log.error("get weixinUserInfo fail,errcode=" + jsonObject.getString("errcode"));
					return thirdLoginUser;
				}
				String openid = jsonObject.getString("openid");
				String headimgurl = jsonObject.getString("headimgurl");
				String unionid = jsonObject.getString("unionid");
				thirdLoginUser = new ThirdLoginUser();
				thirdLoginUser.setOpenid(openid);
				thirdLoginUser.setHeadimgurl(headimgurl);
				thirdLoginUser.setUnionid(unionid);

				log.info("get weixinUserSuccess, openid={},headimgurl={}", openid, headimgurl);
			} catch (Exception e) {
				log.error("accessToken="+accessToken+",analyze weixinUserInfo fail,"+e.getMessage(),e);
			}
		}
		return thirdLoginUser;
	}

	/**
	 * 微博第三方登录获取用户信息
	 * Title: getUserInfo
	 * Description: 获取用户的信息
	 * @param @param openId
	 * @param @param accessToken
	 * @param @return 设定文件
	 * @return WXUser 返回类型
	 */
	public static ThirdLoginUser getWeiboUser(String weiboUid, String weiboTocken) {
		ThirdLoginUser thirdLoginUser = null;
		String requestUrl = ThirdLoginTypeUtil.WEIBO_GET_USERINFO_URL.replace("ACCESS_TOKEN", weiboTocken).replace("UID", weiboUid);
		log.info("getWeiboUser requestUrl="+requestUrl);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				log.info("weiboUid={},weiboUserInfoJson={}",weiboUid,jsonObject.toString());
				if (jsonObject.containsKey("error_code")) {
					log.error("get weiboUser fail,error_code=" + jsonObject.getString("error_code"));
					return thirdLoginUser;
				}
				String headimgurl = jsonObject.getString("profile_image_url");
				thirdLoginUser = new ThirdLoginUser();
				thirdLoginUser.setOpenid(weiboUid);
				thirdLoginUser.setHeadimgurl(headimgurl);

			} catch (Exception e) {
				log.error("weiboUid="+weiboUid+",analyze weiboUserInfo fail,"+e.getMessage(),e);
			}

		}
		return thirdLoginUser;
	}

	/**
	 * qq第三方登录获取用户信息
	 * Title: getUserInfo
	 * Description: 获取用户的信息
	 * @param @param openId
	 * @param @param accessToken
	 * @param @return 设定文件
	 * @return WXUser 返回类型
	 */
	public static ThirdLoginUser getQQUser(String openid, String qqTocken,String qqAppid) {
		ThirdLoginUser thirdLoginUser = null;
		String requestUrl = ThirdLoginTypeUtil.QQ_GET_USERINFO_URL.replace("ACCESS_TOKEN", qqTocken).replace("APPID", qqAppid).replace("OPENID", openid);
		log.info("getQQUser requestUrl="+requestUrl);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				log.info("qqAppid={},qqUserInfoJson=",qqAppid,jsonObject.toString());
				if (jsonObject.containsKey("ret")) {
					log.info("ret="+jsonObject.getString("ret"));
					if(!"0".equals(jsonObject.getString("ret"))){
						return thirdLoginUser;
					}
				}
				String headimgurl = jsonObject.getString("figureurl_qq_2");
				thirdLoginUser = new ThirdLoginUser();
				thirdLoginUser.setOpenid(openid);
				thirdLoginUser.setHeadimgurl(headimgurl);

			} catch (Exception e) {
				log.error("qqAppid="+qqAppid+",analyze qqUserInfo fail,"+e.getMessage(),e);
			}
		}
		return thirdLoginUser;
	}
}




