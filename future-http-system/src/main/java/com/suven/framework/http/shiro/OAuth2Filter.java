//package com.suven.framework.http.shiro;
//
///**
// * @program: st-software-service
// * Description: 过滤器
// * @author: xuegao
// * @create: 2019-08-10 15:04
// **/
//
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.common.util.Constant;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import com.suven.framework.http.inters.IResultCodeEnum;
//import com.suven.framework.http.processor.url.UrlExplain;
//import com.suven.framework.util.json.JsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpStatus;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//
//public class OAuth2Filter extends AuthenticatingFilter {
//
//	Logger logger = LoggerFactory.getLogger(OAuth2Filter.class);
//	@Override
//	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
//		//获取请求token
//		String token = getRequestToken((HttpServletRequest)request);
//
//		if (StringUtils.isBlank(token)) {
//			return null;
//		}
//
//		return new OAuth2Token(token);
//	}
//
//	@Override
//	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//		//没有权限访问
////		if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())) {
////			return true;
////		}
//
//		//url在白明单中，不需要验证
////		HttpRequestRemote remote = ParamMessage.getRequestRemote();
//		String url = ((HttpServletRequest)request).getServletPath();
//		if(UrlExplain.isWhite(url)){
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//		//获取请求token，如果token不存在，直接返回401
//		String token = getRequestToken((HttpServletRequest)request);
//		if (StringUtils.isBlank(token)) {
//			HttpServletResponse httpResponse = (HttpServletResponse)response;
//			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//
//			IResultCodeEnum codeEnum = OAuth2FilterCodeEnum.build().cloneMsg("token is null");
//			String json = JsonUtils.toJson(codeEnum);
//			httpResponse.getWriter().print(json);
//			return false;
//		}
//
//		return executeLogin(request, response);
//	}
//
//	@Override
//	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
//	                                 ServletResponse response) {
//		try {
//			HttpServletResponse httpResponse = (HttpServletResponse)response;
//			httpResponse.setContentType("application/json;charset=utf-8");
//			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//
//			//处理登录失败的异常
////			Throwable throwable = e.getCause() == null ? e : e.getCause();
////			Map<String,Object> map =new HashMap<>();
////			map.put("errorCode",HttpStatus.SC_UNAUTHORIZED);
////			map.put("errorMessage",throwable.getMessage());
////			httpResponse.getWriter().print(JsonUtils.map2String(map));
//			Throwable throwable = e.getCause() == null ? e : e.getCause();
//			IResultCodeEnum codeEnum = OAuth2FilterCodeEnum.build().cloneMsg(throwable.getMessage());
//			String json = JsonUtils.toJson(codeEnum);
//			httpResponse.getWriter().print(json);
//
//		} catch (RuntimeException e1) {
//			throw  new SystemRuntimeException(SysResultCodeEnum.SYS_AUTH_ACCESS_TOKEN_FAIL);
//		}catch (Exception ex){
//			ex.printStackTrace();
//			logger.error("onLoginFailure",e);
//		}
//
//		return false;
//	}
//
//	public static class OAuth2FilterCodeEnum  implements IResultCodeEnum {
//
//		public static OAuth2FilterCodeEnum build(){
//			return new OAuth2FilterCodeEnum();
//		}
//		String msg;
//		@Override
//		public int getCode() {
//			return HttpStatus.SC_UNAUTHORIZED;
//		}
//
//		@Override
//		public String getMsg() {
//			return msg;
//		}
//
//
//		@Override
//		public IResultCodeEnum cloneMsg(String msg) {
//			IResultCodeEnum msgEnum  = MsgEnumType.clone(this,msg);
//			return msgEnum;
//		}
//	}
//	/**
//	 * 获取请求的token
//	 */
//	private String getRequestToken(HttpServletRequest httpRequest) {
//		//从header中获取token
//		//TOKEN KEY
//		String token = httpRequest.getHeader(Constant.ACCESS_TOKEN);
//		if(StringUtils.isNotBlank(token)){
//			return token;
//		}
//		//如果header中不存在token，则从参数中获取token
//		token = httpRequest.getParameter(Constant.ACCESS_TOKEN);
//		if (StringUtils.isNotBlank(token)) {
//			return token;
//		}
//		token = httpRequest.getHeader(Constant.X_ACCESS_TOKEN);
//		if(StringUtils.isNotBlank(token)){
//			return token;
//		}
//		//如果header中不存在token，则从参数中获取token
//		token = httpRequest.getParameter(Constant.X_ACCESS_TOKEN);
//		if (StringUtils.isNotBlank(token)) {
//			return token;
//		}
//		return token;
//	}
//
//
////	public static HttpServletRequest getHttpServletRequest() {
////		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
////	}
////
////	public static String getDomain() {
////		HttpServletRequest request = getHttpServletRequest();
////		StringBuffer url = request.getRequestURL();
////		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
////	}
//
//
//}
