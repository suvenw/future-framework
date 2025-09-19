package com.suven.framework.http.interceptor;

import com.suven.framework.http.resolver.HttpRequestArgumentParser;
import com.suven.framework.http.validator.DefaultHandlerValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.util.Objects;
import java.util.Optional;


/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-05-11
 * WeeK 星期: 星期二
 * version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  HandlerInterceptorAdapter
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.com
 *
 **/
public abstract class AbstractHandlerInterceptorAdapter extends HttpRequestArgumentParser implements IHandlerInterceptor {

    private ApplicationContext application;
    private InterceptorConfigSetting handlerSetting;

    protected Logger log = LoggerFactory.getLogger(getClass());

    public AbstractHandlerInterceptorAdapter(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
        this.application = application;
        this.handlerSetting = handlerSetting;
    }

    /** 通过配置设置,是否支行状态
     * 1.在白名单中,返回true,在不在排除列表中,返回true **/

    @Override
    public boolean supportsRunStatus() {
        boolean isWhite = this.getHandlerSetting().getHandler().isWhite(getOrder());
        boolean isExclude = this.getHandlerSetting().getHandler().isExclude(getOrder());
        return isWhite || !isExclude;
    }

    public void setApplicationContext(ApplicationContext applicationContext){
        application = applicationContext;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return application;
    }

    /**
     * 配置 IHandlerInterceptor 的相关参数配置
     **/
    @Override
    public InterceptorConfigSetting getHandlerSetting() {
        if (Objects.nonNull(handlerSetting)){
            return handlerSetting;
        }
        handlerSetting = getApplicationContext().getBean(InterceptorConfigSetting.class);
        if (Objects.isNull(handlerSetting)){
            log.info("InterceptorConfigSetting object  is null");
            throw new RuntimeException(ErrorConstants.HANDLER_INTERCEPTOR_CONFIG_NULL);
        }
        return handlerSetting;
    }

    public void setHandlerSetting(InterceptorConfigSetting handlerSetting) {
        this.handlerSetting = handlerSetting;
    }

    public boolean isHeaderToken(){
        return handlerSetting.getService().isHeaderToken();
    }

    /** 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑  **/
//    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception ;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(" url request ChannelHandlerInterceptor  preHandle ===================");
        /** 其中supportsRunStatus()返回如下: 1.在白名单中,返回true,在不在排除列表中,返回true
         * 因此,当不在白名单中,或者在排除中中,将不运行;
         * **/
        if (!supportsRunStatus()){
            return true;
        }
        HandlerValidator validator = handlerValidator();
        if (Objects.isNull(validator) || validator instanceof  DefaultHandlerValidator){
            return true;
        }
        return preHandle(request,response,handler,validator);
    }


    /**
     *
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
    */
    public abstract String handlerValidatorBeanType();
    /**
     * @return 验证的实现类,父类默认为抛出异常提示,
     * 如果不需要自己实现,可以返回默认实现
     * return DefaultHandlerValidator.build();
     */
    @Override
    public HandlerValidator handlerValidator() {
        String validatorBeanType = null;
        try {
            validatorBeanType = handlerValidatorBeanType();
            if (Objects.nonNull(validatorBeanType)){
                Object bean = this.getApplicationContext().getBean(validatorBeanType);
                if (bean instanceof HandlerValidator validator) {
                    return validator;
                }
            }
            return DefaultHandlerValidator.build();
        }catch (NoSuchBeanDefinitionException ex){
            log.debug("IHandlerValidator Bean and impl String:{} ",validatorBeanType);
        } catch (Exception e){
            log.info("IHandlerValidator Bean and Method exception", e);
        }
        return null;
    }
}