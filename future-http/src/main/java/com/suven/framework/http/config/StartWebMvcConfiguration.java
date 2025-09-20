package com.suven.framework.http.config;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.http.handler.AbstractHandlerArgumentResolver;
import com.suven.framework.http.handler.IHandlerMethodArgumentResolver;
import com.suven.framework.http.handler.JacksonHttpMessageConverter;
import com.suven.framework.http.interceptor.AbstractHandlerInterceptorAdapter;
import com.suven.framework.http.interceptor.IHandlerInterceptor;
import com.suven.framework.http.interceptor.InterceptorConfigSetting;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 所有应用
 */
@Configuration
@EnableWebMvc
public class StartWebMvcConfiguration implements WebMvcConfigurer {
    //WebMvcConfigurerAdapter WebMvcConfigurationSupport WebSecurityConfigurerAdapter


    @Autowired(required = false)
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private InterceptorConfigSetting configSetting;
    @Autowired(required = false)
    private TimeFormatProperties timeFormatProperties;


    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};



    private static final String[] EXCLUDE_PATH ={
            "/webjars/**","swagger-resources/**","/*/api-docs"
    };
    private static final String[] EXCLUDE_PATH_PATTERNS ={
            "/css/**",
            "/js/**",
            "/layer/**",
            "/error/**",
            "/images/**",
            "/**.js",
            "/**.css",
            "/**.html"

    };




    @Override
    public void addInterceptors( InterceptorRegistry registry) {

        // 注册拦截器

        String pathPattern = "/**";// /top/**
        String jvmPathPattern = "/jvm/**";//exclude

        List<String> pathPatternList = new ArrayList<>();//
        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add(jvmPathPattern);
        excludePathPatterns.addAll(Arrays.asList(EXCLUDE_PATH_PATTERNS));
        excludePathPatterns.addAll(Arrays.asList(EXCLUDE_PATH));
        pathPatternList.add(pathPattern);
        List<IHandlerInterceptor> interceptorsExtList = addInterceptorsScan();
        for (IHandlerInterceptor interceptor : interceptorsExtList){

            List<String> pathList = interceptor.getHandlerSetting().getHandler().pathList();
            List<String> exList = interceptor.getHandlerSetting().getHandler().excludePathList();

            pathPatternList.addAll(pathList);
            excludePathPatterns.addAll(exList);

            registry.addInterceptor(interceptor)
                    .addPathPatterns(pathPatternList)
                    .excludePathPatterns(excludePathPatterns);
        }

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //与spring的参数验证有冲突，相当于所有参数自己解析处理
        List<HandlerMethodArgumentResolver> argumentResolverList = addArgumentResolversScan();
        argumentResolvers.addAll(argumentResolverList);

//        argumentResolvers.add(new RequestFormHandlerArgumentResolver());
//        argumentResolvers.add(new RequestHeaderHandlerArgumentResolver());
//        argumentResolvers.add(new RequestJsonHandlerArgumentResolver());
    }


    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//        super.addReturnValueHandlers(returnValueHandlers);
//        returnValueHandlers.add(new ResponseBodyHandlerArgumentResolver());
//        returnValueHandlers.add(new EncryptBodyRturnValueHandler());

    }


    /**
     * 配置消息转换器
     * 移除默认的 MappingJackson2HttpMessageConverter，使用自定义的转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除默认的 MappingJackson2HttpMessageConverter
        converters.removeIf(converter ->
                converter instanceof MappingJackson2HttpMessageConverter);

        // 添加自定义的 JacksonHttpMessageConverter
        converters.add(defaultJacksonConverter());
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//        super.addResourceHandlers(registry);


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                .allowedOrigins("*")//设置允许跨域请求的域名
                .allowCredentials(false)//是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                .maxAge(3600000);//跨域允许时间
    }



    private List<IHandlerInterceptor> addInterceptorsScan() {
        Set<Class<? extends IHandlerInterceptor>> classList = ReflectionsScan.reflections.getSubTypesOf(IHandlerInterceptor.class);
        if(null == classList || classList.isEmpty()){
            return new ArrayList<>();
        }

        TreeMap<Integer,IHandlerInterceptor > handlerTreeMap = new TreeMap();
        List<IHandlerInterceptor> list = new ArrayList<>();

        for (Class<?> clazz :classList){
            try {
                boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
                if(isAbstract){continue;}
                /**
                 * 强制继续实现方法转换类增加到拦截器中;
                 */
                boolean isResolver = IHandlerInterceptor.class.isAssignableFrom(clazz) && HandlerInterceptor.class.isAssignableFrom(clazz);
                if(isResolver){
                    // 获取构造器
                    Constructor<?> constructor = clazz.getConstructor(ApplicationContext.class,InterceptorConfigSetting.class);
                    // 创建实例对象
                    AbstractHandlerInterceptorAdapter handlerInterceptor = (AbstractHandlerInterceptorAdapter)constructor.newInstance(applicationContext,configSetting);
//                    AbstractHandlerInterceptorAdapter handlerInterceptor =  (AbstractHandlerInterceptorAdapter)clazz.newInstance();
                    handlerTreeMap.put(handlerInterceptor.getOrder(),handlerInterceptor);
                }

            }catch (Exception e){

            }
        }
        list.addAll(0,handlerTreeMap.values());
        //        return new ArrayList<>(handlerTreeMap.values());
        return list;

    }
    private List<HandlerMethodArgumentResolver> addArgumentResolversScan() {
        Reflections reflections   = new Reflections(GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES);
        Set<Class<? extends IHandlerMethodArgumentResolver>> classList = reflections.getSubTypesOf(IHandlerMethodArgumentResolver.class);
        List<HandlerMethodArgumentResolver> list = new ArrayList<>();
        if(null == classList || classList.isEmpty()){
            return list;
        }
        for (Class<?> clazz : classList){
            try {
                boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
                if(isAbstract){continue;}
                /**
                 * 强制继续实现方法转换类增加到拦截器中;
                 */
                boolean isResolver = AbstractHandlerArgumentResolver.class.isAssignableFrom(clazz);
                if (isResolver){
                    list.add((IHandlerMethodArgumentResolver)clazz.newInstance());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }



    /**
     * 扩展消息转换器配置
     * 可以添加额外的转换器
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 如果需要，可以在这里添加额外的转换器配置
    }

    /**
     * 默认的 Jackson 消息转换器
     * 使用 JacksonUtil 的配置，确保统一的业务 JSON 转换
     */
    @Bean
    @Primary
    public JacksonHttpMessageConverter defaultJacksonConverter() {
        JacksonHttpMessageConverter.TimeFormat format = JacksonHttpMessageConverter.TimeFormat.TIMESTAMP ;

        // 如果配置了时间格式属性，使用配置的格式
        if (timeFormatProperties != null && timeFormatProperties.isEnabled()) {
            format = timeFormatProperties.getFormat();
        }
        return new JacksonHttpMessageConverter(format);
    }

//    private List<HandlerMethodArgumentResolver> addArgumentResolversExt() {
//        Map<String,IHandlerMethodArgumentResolver> handlerMap = applicationContext.getBeansOfType(IHandlerMethodArgumentResolver.class);
//        List<HandlerMethodArgumentResolver> list = new ArrayList<>();
//        if(Objects.isNull(handlerMap)){
//            return list;
//        }
//        for (IHandlerMethodArgumentResolver argument : handlerMap.values()){
//            try {
//                boolean isAbstract = Modifier.isAbstract(argument.getClass().getModifiers());
//                boolean isInterface = IHandlerMethodArgumentResolver.class.isAssignableFrom(argument.getClass());
//                if(isAbstract || !isInterface){continue;}
//                list.add(argument);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
//private List<IHandlerInterceptor> addInterceptorsExt() {
//    boolean isExclude = false;
//    Map<String,IHandlerInterceptor> handlerMap = applicationContext.getBeansOfType(IHandlerInterceptor.class);
//    if(null == handlerMap || handlerMap.isEmpty()){
//        return new ArrayList<>();
//    }
//    TreeMap<Integer,IHandlerInterceptor > handlerTreeMap = new TreeMap();
//    List<IHandlerInterceptor> list = new ArrayList<>();
//    for (IHandlerInterceptor handler :handlerMap.values()){
//        try {
//            boolean isAbstract = Modifier.isAbstract( handler.getClass().getModifiers());
////                不是实现类排除,或没有实现指定接口的类
//            if(isAbstract || isExclude){
//                continue;
//            }
//            boolean isResolver = AbstractHandlerInterceptorAdapter.class.isAssignableFrom(handler.getClass());
//            if (isResolver){
//                AbstractHandlerInterceptorAdapter adapter = (AbstractHandlerInterceptorAdapter) handler;
//                adapter.setApplicationContext(applicationContext);
//
//                handlerTreeMap.put(handler.getOrder(),adapter);
//            }else {
//                list.add(handler);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    list.addAll(0,handlerTreeMap.values());
//    return list;
//
//}


}
