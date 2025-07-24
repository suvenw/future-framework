package com.suven.framework.http.filter;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.suven.framework.core.jetty.settings.JettyServerSettings;

import java.util.Map;


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


@Configuration
public class HttpDoSFilterConfig {

    private static Logger logger = LoggerFactory.getLogger(HttpDoSFilterConfig.class);


    @Autowired
    private JettyServerSettings jettyServer;
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = GlobalConfigConstants.JETTY_SERVER_FILTER_DOS_ENABLED, havingValue = "true", matchIfMissing = false)
    public FilterRegistrationBean<HttpDoSFilter> dosFilter(){
        FilterRegistrationBean<HttpDoSFilter> filterRegistrationBean = new FilterRegistrationBean<>(new HttpDoSFilter());
        JettyServerSettings.HttpDosFilterSettings config  = jettyServer.getDos();
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns(config.getUrlPatterns());
        //添加不需要忽略的格式信息.
        Map<String, String>   param = config.toMap();
        logger.warn("HttpDoSFilterConfig FilterRegistrationBean [{}]", param.toString());
        filterRegistrationBean.setInitParameters(param);
        return filterRegistrationBean;
    }





}
