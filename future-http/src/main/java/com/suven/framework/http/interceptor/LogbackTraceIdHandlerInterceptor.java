//package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;
//
//
//import com.google.common.collect.Sets;
//import com.suven.framework.common.GlobalLogbackThread;
//import com.suven.framework.core.db.DataSourceHolder;
//import com.suven.framework.http.message.ParameterMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerMapping;
//import com.suven.framework.http.exception.BusinessLogicException;
//
//import javax.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
///**
// * @Title: LogbackTraceIdHandlerInterceptor.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) http 接口拦截器,主要是实现全局唯一时间id生成,用于业务功能跟踪和业务分析,排序值为1;
// */
//
//@Component
//@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_LOGBACK)
//public class LogbackTraceIdHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor {
//	private static Logger logger = LoggerFactory.getLogger(LogbackTraceIdHandlerInterceptor.class);
//
//    public LogbackTraceIdHandlerInterceptor(){}
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) {
//
//	}
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BusinessLogicException {
//		logger.info(" url request LogbackTraceIdHandlerInterceptor  preHandle ===================");
//    	try {
//            request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,Sets.newHashSet(MediaType.APPLICATION_JSON));
//            String[] module = request.getServletPath().split("/");
//            if(null != module && module.length >= 2){
//                String logback_trace_id = GlobalLogbackThread.initGlobalLogbackTraceId(module[1]);
//                logger.info("gc server global logback info logback_trace_id [{}]", logback_trace_id);
//            }
//
//		}catch (Exception e){}
//
//		return true;
//
//	}
//
//	@Override
//	public void afterCompletion(
//			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		logger.info(" url request LogbackTraceIdHandlerInterceptor  afterCompletion ===================");
//		GlobalLogbackThread.removeTraceId();
//		ParameterMessage.clear();
//		DataSourceHolder.remove();
//
//	}
//
//
//}
