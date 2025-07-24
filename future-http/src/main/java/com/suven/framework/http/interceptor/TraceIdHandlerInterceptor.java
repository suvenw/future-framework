package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;


import com.google.common.collect.Sets;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.validator.DefaultHandlerValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.*;




/**
 * Title: TraceIdHandlerInterceptor.java
 * @author suven.wang
 * @version V1.0
 * date   2019-10-18 12:35:25
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是实现全局唯一时间id生成,用于业务功能跟踪和业务分析,排序值为1;
 */

public class TraceIdHandlerInterceptor extends AbstractHandlerInterceptorAdapter implements IHandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(TokenHandlerInterceptor.class);
	private final String CHARSET_CONTENT_UTF8 = "UTF-8";
	//删除敏感字段或超长字段,在日志中不作输出
	private final List<String > removeParameterList = Arrays.asList("password","file","files");

	public TraceIdHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
		super(application, handlerSetting);
	}


	/**
	 * Get the order value of this object.
	 * <p>Higher values are interpreted as lower priority. As a consequence,
	 * the object with the lowest value has the highest priority (somewhat
	 * analogous to Servlet {@code load-on-startup} values).
	 * <p>Same order values will result in arbitrary sort positions for the
	 * affected objects.
	 *
	 * @return the order value
	 * @see #HIGHEST_PRECEDENCE
	 * @see #LOWEST_PRECEDENCE
	 */
	@Override
	public int getOrder() {
		return HandlerInterceptorEnum.HANDLER_ORDER_TRACE_ID.getIndex();
	}

	/**
	 * @return
	 */
	@Override
	public String handlerValidatorBeanType() {
		return HandlerInterceptorEnum.HANDLER_ORDER_TRACE_ID.getValue();
	}


	/**
	 * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
	 *
	 * @param request   HttpServletRequest 网络请求对象
	 * @param response  HttpServletResponse 网络返回对象
	 * @param handler   Object handler – 选择要执行的处理程序，用于类型和/或实例评估
	 * @param validator 获取业务的验证实现接口的对应实现类
	 * @return 结果对或错 true/false,为true时,执行下一个验证拦截, 为false 结束运行, 建议抛出异常提示到前端用户
	 * @throws Exception 通过异常反馈到前端
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, HandlerValidator validator) throws Exception {
		log.info(" url request LogbackTraceIdHandlerInterceptor  preHandle ===================order[{}]", getOrder());
    	try {
            request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,Sets.newHashSet(MediaType.APPLICATION_JSON));
			Map<String, String> map = this.getHeadersInfo(request);
			HttpRequestHeaders headers = HttpRequestHeaders.build().of(map);
			headers.setTimes(System.currentTimeMillis());
			//特殊处理兼容性
			GlobalTraceIdThread.setHttpRequestHeaders(headers);
            String[] modules = request.getServletPath().split("/");
			String module = "";
            if(null != modules && modules.length >= 2){
				module = modules[1];
            }
			String logback_trace_id = GlobalTraceIdThread.initTraceId(module);
			log.info("gc server global logback info logback_trace_id [{}]", logback_trace_id);
		}catch (Exception e){}

		return true;

	}

	/**
	 * @return 验证的实现类, 父类默认为抛出异常提示,
	 * 如果不需要自己实现,可以返回默认实现
	 * return DefaultHandlerValidator.build();
	 */
	@Override
	public HandlerValidator handlerValidator() {
		return DefaultHandlerValidator.build();
	}

	/** 方法执行完成执行 **/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info(" url request LogbackTraceIdHandlerInterceptor  afterCompletion ===================");
		try {
			String json =  getJsonBody(request);
			HttpRequestHeaders headers = GlobalTraceIdThread.getHttpRequestHeaders();
			IResultCodeEnum resultCodeEnum = GlobalTraceIdThread.getCodeEnum();
			Map<String,String[]> parameterMap = new HashMap<>(request.getParameterMap());
			removeParameter(parameterMap);
//			GlobalTraceIdThread.printLogForRequestMessage(resultCodeEnum.getCode(),
//					resultCodeEnum.getMsg(),
//					headers,parameterMap,json);
		}catch (Exception e){
			log.warn("LogbackTraceIdHandlerInterceptor",e);
		}finally {
			GlobalTraceIdThread.removeTraceId();
		}


	}

	//删除请求参数的敏感参数信息;,如果需添加,在removeParameterList中添加字段名称
	private boolean removeParameter(Map<String,String[]> parameterMap){
		removeParameterList.forEach(parameterMap::remove);
		return true;
	}

	//获取全部请求头参数,并做好兼容处理,全量将"_""-"替换成"",并且全部转小写,方便面兼容对象反射付值;
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key =  headerNames.nextElement();
			String value = request.getHeader(key);
			key  = key.replace("-", "").replace("_", "").toLowerCase();
			map.put(key, value);
		}
		return map;
	}
	private String getJsonBody(HttpServletRequest request) {
		String json = "";
		boolean isPost = request.getMethod().equals(RequestMethod.POST.name());
		if(!isPost){
			return json;
		}
		try {
			boolean isClosed  = request.getInputStream().available() == 0;
			if (isClosed){
				return json;
			}
			json = IOUtils.toString(request.getInputStream(), this.CHARSET_CONTENT_UTF8);
			return json;
		}catch (Exception e){
			log.info("getJsonBody parameter Exception ", e );
		}
		return json;

	}





}