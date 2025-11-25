package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;


import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.http.processor.url.UrlExplain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

import static com.suven.framework.common.enums.SysResultCodeEnum.SYS_PARAM_CHECK;


/**
 * Title: WhiteHandlerInterceptor.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是校验接口是否白名单列表接口业务,排序值为4;
 */



public class ParameterSignHandlerInterceptor extends AbstractHandlerInterceptorAdapter implements IHandlerInterceptor {

    private static Logger log= LoggerFactory.getLogger(ParameterSignHandlerInterceptor.class);

    public ParameterSignHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
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
        return HandlerInterceptorEnum.HANDLER_ORDER_PARAMETER_SIGN.getIndex();
    }

    /**
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
     */
    @Override
    public String handlerValidatorBeanType() {
        return HandlerInterceptorEnum.HANDLER_ORDER_PARAMETER_SIGN.getValue();
    }

    /**
     * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
     *
     * @param request
     * @param response
     * @param handler
     * @param validator
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response , Object handler, HandlerValidator validator ) throws Exception {
        HttpRequestRemote remote = ParameterMessage.getRequestRemote();
        if(Objects.isNull(remote)){
            log.warn(" ParameterSignHandlerInterceptor RequestRemote is null remote: [{}] ", remote );
            return true;
        }//接口是否做加密处理,默认是加密的,配置为非加密,跳过
        if(!UrlExplain.isParamSign(remote.getUrl())){
            return true;
        }
        //通过配置文件,配置系统级别是否验证加密;
        if(!this.getHandlerSetting().getService().isParamSign()){//参数窜改验证
            return true;
        }
        if (Objects.nonNull(remote.getSrvMd5Sign()) && !Objects.equals(remote.getCliMd5Sign(),remote.getSrvMd5Sign())){
            log.warn("非法请求client_sign={},server_sign={}",remote.getCliMd5Sign(),remote.getSrvMd5Sign());
            throw new SystemRuntimeException(SYS_PARAM_CHECK);
        }
        if (Objects.nonNull(remote.getSrvJsonSign()) && !Objects.equals(remote.getCliJsonSign(),remote.getSrvJsonSign())){
            log.warn("非法请求client_json_sign={},server_json_sign={}",remote.getCliJsonSign(),remote.getSrvJsonSign());
            throw new SystemRuntimeException(SYS_PARAM_CHECK);
        }
        return true;

    }



}
