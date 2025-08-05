package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;


import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParameterMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;



/**
 * Title: BlackHandlerInterceptor.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是实现黑名单方面业务类,排序值为6;
 */

public class BlackHandlerInterceptor extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {

	public BlackHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
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
		return HandlerInterceptorEnum.HANDLER_ORDER_BLACK.getIndex();
	}

	/**
	 * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
	 * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
	 */
	@Override
	public String handlerValidatorBeanType() {
		return HandlerInterceptorEnum.HANDLER_ORDER_BLACK.getValue();
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
		HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
		boolean validatorData = validator.validatorFromData(true,message);
		return validatorData;
	}



}
