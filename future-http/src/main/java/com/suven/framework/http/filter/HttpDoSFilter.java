package com.suven.framework.http.filter;





/**
 *  @Title: ShowDoSFilter.java
 *  @author Joven.wang
 *  @date 2015年2月8日
 *  @version V1.0
 *  Description: TODO(说明)
 */

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.constants.Env;
import com.suven.framework.util.random.RandomUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.ee10.servlets.DoSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 ＊ 拒绝服务过滤器
 *  < P>
 *  这个过滤器对于限制是有用的
 *  暴露于请求洪泛的滥用，无论是恶意的，还是由于
 *  配置错误的客户端。
 * < P>
 * 筛选器跟踪来自连接的请求数量。
 * 其次。如果超过限制，则请求被拒绝、延迟或
 * 节流。
 * < P>
 * 当请求被节流时，它被放置在优先级队列中。优先权是
 * 首先给经过身份验证的用户和具有HTTP会话的用户，然后
 * 可以通过IP地址识别的连接。联系
 * 没有识别它们的方法被给予最低优先级。
 * < P>
 ＊{Link Lang-ExtUsUrSerID（ServestRevestRead Read）}函数应该是
 * 实现，以便唯一地识别已验证的用户。
 * < P>
 * 下面的init参数控制过滤器的行为：
 ＊<dl>
 * <dt> * Max RealStestScSc1</dt>
 * <dd>每个连接的最大请求数
 * 其次。超过此要求的请求被延迟，
 * 然后节流。< /dd>
 ＊<dt> DelayMs </dt>
 * <dd>是在速率限制下对所有请求的延迟，
 * 在它们被考虑之前。1意味着拒绝请求，
 *  0意味着没有延迟，否则就是延迟。</dd>
 * <dt> Max Waistm </dt >
 * <dd>阻塞等待节气门信号的时间有多长。< /dd>
 ＊<dt>节流请求< /dt >
 * <<dd>是速率限制上的请求数量
 * 立即考虑。< /<dd>
 ＊dt>节流器< /dt >
 * <dd>异步等待信号量多长时间？< /dd>
 * <dt> * Max RealestMs</dt>
 * <dd>允许这个请求运行多长时间。</<dd>
 * <dt> MaxIDeLeTrackMS</dt>
 * <dd>跟踪连接请求速率的时间，
 * 在决定用户已经离开，并丢弃它</dd>
 * <dt>插入者< /dt >
 * <dd>如果为true，则将DOSFutter头插入到响应中。默认为true。< /dd>
 * dt>轨迹会话< /dt>
 * <dd>如果为真，会话存在时使用会话跟踪使用率。默认为true。< /dD>
 *  dt>远程端口< /dt>
 * <dd>如果TRUE和会话跟踪未被使用，则通过IP +端口（有效连接）跟踪速率。默认为false .< /dd>
 * <dt> IPWELLIST < /dt >
 * <dd>一个逗号分隔的IP地址列表，将不受速率限制</dd>
 * <dt> 管理ADT< /dt >
 * <dd>如果设置为true，则将该servlet设置为{@链接servlet上下文}属性
 * 筛选器名称作为属性名称。这允许上下文外部机制（如JMX通过{@链接上下文HythHyther-SyMaReDeMy属性}）
 * 管理筛选器的配置。< /<dd>
 ＊<dt> ToMoNoCudio</dt >
 * <dd>如果有太多请求，将发送状态代码。默认为429（请求太多），但503（不可用）是
 * 另一个选项< /dD>
 * </dL>
 * < P>
 * 此过滤器应该配置为{@ Link Debug类型}请求}和{@ Link DeXCurtType<代码> &；异步支持& gt；真和lt；/asyc支持& gt；< />代码>。
 */

public class HttpDoSFilter extends DoSFilter {

	private final static Logger logger = LoggerFactory.getLogger(HttpDoSFilter.class);

	private static  boolean ENV_IS_SERVER = Env.isProd();
	
	/**
	 *  初始化相关配置DosFilter 相关配置参数;
	 *  @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		try {
			
			HttpServletRequest servletRequest =  (HttpServletRequest) request;
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			HttpRequestRemote requestRemote = ParameterMessage.getRequestRemote();
			if(!requestRemote.isIgnoreLogin() || requestRemote.isCdn() || requestRemote.isWhite()){
				  logger.info("Http request's not post type, skin the dos filter. request url is [{}], request ip is [{}]",
						  servletRequest.getRequestURI(), requestRemote.getClientIp());
				  filterChain.doFilter(request, response);
				  return;
			}
			super.doFilter(request, response, filterChain);
			if (servletResponse.getStatus() == HttpServletResponse.SC_SERVICE_UNAVAILABLE) {
				HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
				logger.warn("extractUserId := " + message.getUserId() + ",device:={},token:={},  ServletResponse.SC_SERVICE_CODE:= "+ servletResponse.getStatus()+
						", request url is [{}], request ip is [{}], Platform is [{}], channel is [{}], sysVersion is [{}], version is [{}]",
						message.getDevice(),message.getAccessToken(),servletRequest.getRequestURI(), requestRemote.getClientIp(),
						message.getPlatform(), message.getChannel(), message.getSysVersion(), message.getVersion());
			}
		} catch (Exception e) {
			logger.warn("doFilter catch Exception:=", e);
		}

	}

	/****
	 * 重写实现具体的拦截
	 */
	@Override
	protected String extractUserId(ServletRequest request) {
		HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
		VerifyDosStatus dos = new VerifyDosStatus();
		dos.setClientIp(ParameterMessage.getRequestRemote().getClientIp());
		dos.setDevice(message.getDevice());
		dos.setToken(message.getAccessToken());
		dos.setUserId(message.getUserId());
		dos.setUrl(message.getUri());
		String intercept = checkDos(dos);
		return intercept;
	}

	/**
	 * 拦截类型,1.用户ID,2.token,3到设备码,4.用户IP(不是公司内部);
	 *  
	 *  @return
	 */
	public String checkDos(VerifyDosStatus dos) {
		if(! ENV_IS_SERVER)
			return getRandomNum(dos);
		if(null == dos){
			return getRandomNum();
		}
		if (null != dos) {
			if (dos.getUserId() > 0 ) {
				return "USER_ID:"+dos.getUserId();
			}
			if (ObjectTrue.isNotEmpty(dos.getToken())) {
				return "ACCESS_TOKEN:"+dos.getToken();
			}
			if(ObjectTrue.isNotEmpty(dos.getUrl())){
				return "USER_ID:"+dos.getUserId() +" Url:"+ dos.getUrl();
			}
//			if (StringUtils.isNotBlank(dos.getDevice())) {
//				return "device:"+dos.getDevice();
//			}
			/**  暂时不用检测IP
			if (null != dos.getClientIp()) {// 不是公司内部
				if (dos.getClientIp().startsWith("192.168")
						|| dos.getClientIp().startsWith("10.12")
						|| dos.getClientIp().startsWith("127.0.0.1")) {
					 return null;
				}
				return dos.getClientIp();

			}
			*/
		}
		/**  当返回null走ip检测 */
		logger.info("Request type's not get and Passport,AccessToken is blank, request url is [{}], request ip is [{}]", 
				dos.getUrl(), dos.getClientIp());
		return getRandomNum(dos);
	}
	
	private String getRandomNum(VerifyDosStatus dos){
		String rs = getRandomNum();
		logger.warn("Request type's not get and userId,token,device is blank, random num is [{}], " +
						"request url is [{}], request ip is [{}]",
				rs, dos.getUrl(), dos.getClientIp());
		return rs;
	}

	private String getRandomNum(){
		int baseInt = 1000000;
		String rs =  String.valueOf(RandomUtils.num(baseInt))
				+ RandomUtils.num(baseInt);
		return rs;
	}

	class VerifyDosStatus {

		private long userId;
		private String token;
		private String device;
		private String clientIp;
		private String url;

		public VerifyDosStatus(){}

		public VerifyDosStatus(int userId, String token, String device, String clientIp) {
			super();
			this.userId = userId;
			this.token = token;
			this.device = device;
			this.clientIp = clientIp;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getDevice() {
			return device;
		}
		public void setDevice(String device) {
			this.device = device;
		}
		public String getClientIp() {
			return clientIp;
		}
		public void setClientIp(String clientIp) {
			this.clientIp = clientIp;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}
	}
}
