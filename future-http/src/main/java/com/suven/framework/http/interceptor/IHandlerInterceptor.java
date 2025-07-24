package com.suven.framework.http.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.AsyncHandlerInterceptor;



/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-05-11
 * WeeK 星期: 星期二
 * version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  IHandlerInterceptor 拦截处理器的自定义接口实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.com
 *
 **/
public interface IHandlerInterceptor extends AsyncHandlerInterceptor, Ordered {


    /** ApplicationContext 信息,用于 bean 管理实现 **/
    ApplicationContext getApplicationContext();

    /** 配置 IHandlerInterceptor 的相关参数配置 **/
    InterceptorConfigSetting getHandlerSetting();

    /** 通过配置设置,是否支行状态 **/
    boolean supportsRunStatus();

    /** 获取对应业务的验证实现方法,
     * 1.如何为空时,结束验证,返回true
     * 2.如果不空时,执行 下面 preHandle 方法,实现业务逻辑,
     * 3.抽像父类,默认抛出运行异常**/
    HandlerValidator handlerValidator();

    /**
     * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
     * @param request HttpServletRequest 网络请求对象
     * @param response HttpServletResponse 网络返回对象
     * @param handler Object handler – 选择要执行的处理程序，用于类型和/或实例评估
     * @param validator 获取业务的验证实现接口的对应实现类
     * @return 结果对或错 true/false,为true时,执行下一个验证拦截, 为false 结束运行, 建议抛出异常提示到前端用户
     * @throws Exception 通过异常反馈到前端
     */
     boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, HandlerValidator validator) throws Exception ;



}