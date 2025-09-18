package com.suven.framework.http.interceptor;

import com.suven.framework.http.NetworkUtil;
import com.suven.framework.http.api.HttpRequestTypeEnum;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.json.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;



/**
 * Title: JsonHandlerInterceptorAdapter.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是实现数据反映成对象Bean方面业务类,包括头部数据封装,排序值为3;
 */

public class HeadersHandlerInterceptor extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(BlackHandlerInterceptor.class);

    public HeadersHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
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
        return HandlerInterceptorEnum.HANDLER_ORDER_HEADERS.getIndex();
    }

    /**
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
     */
    @Override
    public String handlerValidatorBeanType() {
        return HandlerInterceptorEnum.HANDLER_ORDER_HEADERS.getValue();
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
        logger.info(" url request JsonHandlerInterceptorAdapter  preHandle ===================");
        //判断是get还是post
        boolean isPostReq = this.isPostRequestMethod(request);
        boolean isJson = false;
        Class<HttpRequestPostMessage> clazz = HttpRequestPostMessage.class;
        HttpRequestPostMessage message = this.parseHeaderData(request,response,clazz,HttpRequestTypeEnum.TYPE_HEADER);
        String url = request.getServletPath();
        long netTime = message.getTimes();
        long sysTime = System.currentTimeMillis();

        message.setIp(NetworkUtil.getIpAddress(request));
        message.setUri(request.getRequestURI());
        message.setTimes(sysTime);
        //初始请求数据到当前线程安全中,实现业务解耦,
        //收集来自请求接口的代理属性信息
        HttpRequestRemote remote = new HttpRequestRemote();

        remote.setJsonRequest(isJson);
        remote.setUrl(url);
        remote.setClientIp(message.getIp());
        remote.setDataType(message.getDataType());
        remote.setCliMd5Sign(message.getSign());
        remote.setCliJsonSign(message.getJsonSign());
        remote.setPostRequest(isPostReq);
        remote.setNetTime(sysTime - netTime);

        ParameterMessage.setRequestRemote(remote);

        logger.info("receive client request url=[{}],mode=[{}] ,param=[{}] ", url, request.getMethod(), JsonUtils.toJson(message));
        return true;
    }




    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug("JsonHandlerInterceptorAdapter afterCompletion method 3 ...." );
        ParameterMessage.clear();

    }




}
