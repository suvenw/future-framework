package com.suven.framework.common.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * HttpClient客户端服务类
 * @author Kuanghom Cheng 
 * @version [V1.0, 2018年1月24日 上午9:27:27]
 */
public class HttpClientService {
	
	/** 定义可关闭的HttpClient客户端对象 */
	private CloseableHttpClient httpClient;
	
	private static final String CHARSET = "UTF-8";
	
	public final Logger logger = (Logger) LoggerFactory.getLogger(HttpClientService.class);
	
    private RequestConfig requestConfig;
    
	public HttpResponseEntity sendGet(String url, Map<String,String> params){
		return this.sendGet(url, params, CHARSET);
	}

	/**
	 * 利用HttpClient发送get请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应数据
	 */
	public HttpResponseEntity sendGet(String url, Map<String,String> params, String encode){
		if(httpClient == null){
			httpClient = HttpClients.createDefault();
		}
		
		/** 定义可关闭的响应对象 */
		CloseableHttpResponse response = null;
		try{
			/** 构建URI */
			URIBuilder uriBuilder = new URIBuilder(url);
			/** 判断是否有请求参数 */
			if (params != null && params.size() > 0){
				for (Map.Entry<String, String> entry : params.entrySet()){
					/** 添加请求参数 */
					uriBuilder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			/** 创建 HttpGet请求对象 */
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			/** 设置请求和传输超时时间 */
			httpGet.setConfig(requestConfig);
			/** 执行请求，得到响应对象 */
			response = httpClient.execute(httpGet);
			if(null == response){
				throw new RuntimeException("HttpResponseEntity sendGet RuntimeException");
			}
			/** 判断response，获取响应数据 */
			String content =  (response != null && response.getEntity() != null) 
					? EntityUtils.toString(response.getEntity(), StringUtils.isBlank(encode) ? CHARSET : encode) : null;
			if(logger.isDebugEnabled() && null != response){
				logger.debug("url={}, params={}, statusCode={}, content={}", new Object[]{url, params, response.getStatusLine().getStatusCode(), content});
			}
			/** 返回数据 */
			return new HttpResponseEntity(response.getStatusLine().getStatusCode(), content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			try{
				if (response != null){
					response.close();
				}
			}catch(Exception ex){
				logger.error("close CloseableHttpResponse failed.", ex);
			}
		}
	}
	public HttpResponseEntity sendPost(String url){
		return this.sendPost(url, null, CHARSET);
	}
	
	public HttpResponseEntity sendPost(String url, Map<String,String> params){
		return this.sendPost(url, params, CHARSET);
	}
	
	/**
	 * 利用HttpClient发送post请求 
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应数据
	 */
	public HttpResponseEntity sendPost(String url, Map<String,String> params, String encode){
		if(httpClient == null){
			httpClient = HttpClients.createDefault();
		}
		
		/** 定义可关闭的响应对象 */
		CloseableHttpResponse response = null;
		try{
			/** 创建 HttpPost请求对象 */
			HttpPost httpPost = new HttpPost(url);
			/** 判断是否有请求参数 */
			if (params != null && params.size() > 0){
				/** 定义List集合封装请求参数 */
				List<NameValuePair> nvPair = new ArrayList<>();
				/** 迭代Map集合 */
				for (Map.Entry<String, String> entry : params.entrySet()){
					/** 添加请求参数 */
					nvPair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				/** 设置请求参数 */
				httpPost.setEntity(new UrlEncodedFormEntity(nvPair, StringUtils.isNotBlank(encode) ? encode : CHARSET));
			}
			/** 设置超时配置*/
			httpPost.setConfig(requestConfig);
			/** 执行请求，得到响应对象 */
			response = httpClient.execute(httpPost);
			if(null == response){
				throw new RuntimeException("HttpResponseEntity sendPost RuntimeException");
			}
			/** 判断response，获取响应数据 */
			String content =  (response != null && response.getEntity() != null) 
					? EntityUtils.toString(response.getEntity(), StringUtils.isNotBlank(encode) ? encode : CHARSET) : null;
			if(logger.isDebugEnabled()){
				logger.debug("url={}, params={}, statusCode={}, content={}", new Object[]{url, params, response.getStatusLine().getStatusCode(), content});
			}
			/** 返回数据 */
			return new HttpResponseEntity(response.getStatusLine().getStatusCode(), content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			try{
				if (response != null){
					response.close();
				}
			}catch(Exception ex){
				logger.error("close CloseableHttpResponse failed.", ex);
			}
		}
	}
	/**
	 * 利用HttpClient发送post请求 
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应数据
	 */
	public HttpResponseEntity sendPost(String url, String requestContent){
		/** 定义可关闭的响应对象 */
		CloseableHttpResponse response = null;
		try{
			/** 创建 HttpPost请求对象 */
			HttpPost httpPost = new HttpPost(url);
			/** 判断是否有请求参数 */
			if (StringUtils.isNotBlank(requestContent)){
				httpPost.setEntity(new StringEntity(requestContent, ContentType.APPLICATION_JSON));
			}
			/** 执行请求，得到响应对象 */
			response = httpClient.execute(httpPost);
			if(null == response){
				throw new RuntimeException("HttpResponseEntity sendPost RuntimeException");
			}
			/** 判断response，获取响应数据 */
			String content =  (response != null && response.getEntity() != null) 
					? EntityUtils.toString(response.getEntity(), Consts.UTF_8) : null;
			if(logger.isDebugEnabled()){
				logger.debug("url={}, requestContent={}, statusCode={}, content={}", new Object[]{url, requestContent, response.getStatusLine().getStatusCode(), content});
			}
			/** 返回数据 */
			return new HttpResponseEntity(response.getStatusLine().getStatusCode(), content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			try{
				if (response != null){
					response.close();
				}
			}catch(Exception ex){
				logger.error("close CloseableHttpResponse failed.", ex);
			}
		}
	}
	/**
	 * 利用HttpClient发送put请求 
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应数据
	 */
	public HttpResponseEntity sendPut(String url, Map<String,String> params){
		if(httpClient == null){
			httpClient = HttpClients.createDefault();
		}
		/** 定义可关闭的响应对象 */
		CloseableHttpResponse response = null;
		try{
			/** 创建 HttpPut请求对象 */
			HttpPut httpPut = new HttpPut(url);
			/** 判断是否有请求参数 */
			if (params != null && params.size() > 0){
				/** 定义List集合封装请求参数 */
				List<NameValuePair> nvPair = new ArrayList<>();
				/** 迭代Map集合 */
				for (Map.Entry<String, String> entry : params.entrySet()){
					/** 添加请求参数 */
					nvPair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				/** 设置请求参数 */
				httpPut.setEntity(new UrlEncodedFormEntity(nvPair, Consts.UTF_8));
			}
			httpPut.setConfig(requestConfig);
			/** 执行请求，得到响应对象 */
			response = httpClient.execute(httpPut);
			if(null == response){
				throw new RuntimeException("HttpResponseEntity sendPostsendPut RuntimeException");
			}
			/** 判断response，获取响应数据 */
			String content =  (response != null && response.getEntity() != null) 
					? EntityUtils.toString(response.getEntity(), Consts.UTF_8) : null;
			if(logger.isDebugEnabled()){
				logger.debug("url={}, params={}, statusCode={}, content={}", new Object[]{url, params, response.getStatusLine().getStatusCode(), content});
			}
			/** 返回数据 */
			return new HttpResponseEntity(response.getStatusLine().getStatusCode(), content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}finally{
			try{
				if (response != null){
					response.close();
				}
			}catch(Exception ex){
				logger.error("close CloseableHttpResponse failed.", ex);
			}
		}
	}
	
	/**
	 * 利用HttpClient发送delete请求 
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应数据
	 */
	public HttpResponseEntity sendDelete(String url, Map<String,String> params){
		/** 判断Map集合 */
		if (params == null){
			params = new HashMap<>();
		}
		params.put("_method", "delete");
		return sendPost(url, params);
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

}