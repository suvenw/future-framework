package com.suven.framework.http.interceptor;

//import com.suven.framework.http.message.TenantContext;

import com.suven.framework.http.message.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Objects;


/**
 * Title: TenantHandlerInterceptor.java
 * @author suven.wang
 * @version V1.0
 * date   2019-10-18 12:35:25
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是实现全局唯一租户信息,排序值为1;
 */
public class TenantHandlerInterceptor  extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(TenantHandlerInterceptor.class);

    public TenantHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
        super(application, handlerSetting);
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return HandlerInterceptorEnum.HANDLER_ORDER_TENANT.getIndex();
    }

    /**
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
     */
    @Override
    public String handlerValidatorBeanType() {
        return  HandlerInterceptorEnum.HANDLER_ORDER_TENANT.getValue();
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
      String tenantId = request.getHeader(TenantContext.TENANT__ID);
        if (Objects.isNull(tenantId)){
            tenantId = request.getHeader(TenantContext.tenantId);
        }
        if (Objects.isNull(tenantId)){
            tenantId = request.getHeader(TenantContext.TENANT_ID);
        }
        TenantContext.setCurrentTenant(tenantId);
        log.info("Current tenantId in fromTenant method: {}", TenantContext.getCurrentTenant());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.clear();
    }
}
