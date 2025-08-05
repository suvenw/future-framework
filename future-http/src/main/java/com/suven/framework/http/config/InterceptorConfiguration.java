//package com.suven.framework.http.config;
//
//import com.suven.framework.common.constants.ReflectionsScan;
//import com.suven.framework.core.jetty.settings.JettyServerSettings;
//import com.suven.framework.http.handler.AbstractHandlerArgumentResolver;
//import com.suven.framework.http.interceptor.IHandlerInterceptor;
//import com.suven.framework.http.interceptor.InterceptorOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.*;
//
//import java.lang.reflect.Modifier;
//import java.util.*;
//
///**
// * 所有应用
// */
//@Configuration
//@EnableWebMvc
//@SuppressWarnings("unchecked")
//public class InterceptorConfiguration implements WebMvcConfigurer {
//    //WebMvcConfigurerAdapter WebMvcConfigurationSupport WebSecurityConfigurerAdapter
//
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//
//    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//            "classpath:/META-INF/resources/", "classpath:/resources/",
//            "classpath:/static/", "classpath:/public/"};
//
//
//
//    private static final String[] EXCLUDE_PATH ={
//            "/webjars/**","/*/api-docs"
//    };
//    private static final String[] EXCLUDE_PATH_PATTERNS ={
//            "/css/**",
//            "/js/**",
//            "/layer/**",
//            "/error/**",
//            "/images/**",
//            "/**.js",
//            "/**.css",
//            "/**.html"
//
//    };
//
//
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 注册拦截器
//
//        String PATH_PATTERNS = "/**";// /top/**
//        String JVM_PATH_PATTERNS = "/jvm/**";//exclude
////        final HandlerInterceptor[] interceptors = {
////                new LogbackTraceIdHandlerInterceptor(),
////                new UrlHandlerInterceptor(),
////                new JsonHandlerInterceptorAdapter(),
////                new VersionHandlerInterceptor(), /** 所有post请求，版本提示强制升级返回 **/
////                new WhiteHandlerInterceptor(),/**  所有post请求，用户在白名单中，直接返回 **/
////                new BlackHandlerInterceptor(),/**  所有post请求，用户在黑名单或设备在黑名单中，直接返回 **/
////                new ParameterHandlerInterceptor(),/**  参数校验，公共参数检查 **/
////                new TokenHandlerInterceptor() /** 结合白名单和登录验证 **/
////
////        };
//        /**   还可以在这里注册其它的拦截器 **/
//        /**   url下架直接返回 **/
////        for (HandlerInterceptor interceptor : interceptors){
////            registry.addInterceptor(interceptor)
////                    .addPathPatterns(PATH_PATTERNS)
////                    .excludePathPatterns(EXCLUDE_PATH_PATTERNS);
////        }
//        JettyServerSettings configSetting = applicationContext.getBean("jettyServerSettings",JettyServerSettings.class);
//        List<String> exclude_path_patterns = new ArrayList(Arrays.asList(EXCLUDE_PATH_PATTERNS));
//        exclude_path_patterns.addAll(Arrays.asList(EXCLUDE_PATH));
//        List<String> path_patterns = new ArrayList<>();
//        if(null != configSetting && null != configSetting.getInter()){
//            path_patterns.addAll(configSetting.getInter().getPaths()) ;
//            exclude_path_patterns.addAll( configSetting.getInter().getExcludePaths());
//        }
//        if(path_patterns.isEmpty()){
//            path_patterns.add(PATH_PATTERNS);
//        }
//        List<HandlerInterceptor> interceptorsExtList = new ArrayList<>();//addInterceptorsExt();
//        for (HandlerInterceptor interceptor : interceptorsExtList){
//            registry.addInterceptor(interceptor)
//                    .addPathPatterns(path_patterns)
//                    .excludePathPatterns(exclude_path_patterns);
//        }
////        super.addInterceptors(registry);
//
//    }
//    private List<HandlerInterceptor> addInterceptorsExt() {
//        Set<Class<? extends BaseHandlerInterceptorAdapter>> classList = ReflectionsScan.reflections.getSubTypesOf(BaseHandlerInterceptorAdapter.class);
//        if(null == classList || classList.isEmpty()){
//            return new ArrayList<>();
//        }
//
//        TreeMap<Integer,HandlerInterceptor > handlerTreeMap = new TreeMap();
//        List<HandlerInterceptor> list = new ArrayList<>();
//
//        JettyServerSettings jettyServer = applicationContext.getBean("jettyServerSettings",JettyServerSettings.class);
//        JettyServerSettings.InterServerConfigSetting inter  = null;
//        if(null != jettyServer){
//            inter = jettyServer.getInter();
//        }
//        for (Class<?> clazz :classList){
//            try {
//                if(IHandlerInterceptor.class.isAssignableFrom(clazz) && HandlerInterceptor.class.isAssignableFrom(clazz)){
//                    InterceptorOrder interceptor = AnnotationUtils.findAnnotation(clazz, InterceptorOrder.class);
//
//                    //没有排序编号;随机排序;
//                    if(null == interceptor){
//                        list.add((HandlerInterceptor)clazz.newInstance());
//                        continue;
//                    }
//                    //如果不在白名单中
//                    boolean isWhite = inter.isWhite(interceptor.order());
//                    boolean isBlack = inter.isExclude(interceptor.order());
//                    boolean isRun = interceptor.isRun();
//                    //如果不在白名单中且, 在黑名单或不运行的情况下跳过;
//                    if(!isWhite && (isBlack || !isRun)){
//                        //过滤器按编号排序
//                        continue;
//                    }
//
//                    //过滤器按编号排序
//                    IHandlerInterceptor handlerInterceptor =  (IHandlerInterceptor)clazz.newInstance();
//                    handlerInterceptor.setApplicationContext(applicationContext);
//                    handlerTreeMap.put(interceptor.order(),(HandlerInterceptor)handlerInterceptor);
//
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        list.addAll(0,handlerTreeMap.values());
//        //        return new ArrayList<>(handlerTreeMap.values());
//        return list;
//
//    }
//    private List<HandlerMethodArgumentResolver> addArgumentResolversExt() {
//        Set<Class<? extends HandlerMethodArgumentResolver>> classList = ReflectionsScan.reflections.getSubTypesOf(HandlerMethodArgumentResolver.class);
//        List<HandlerMethodArgumentResolver> list = new ArrayList<>();
//        if(null == classList || classList.isEmpty()){
//            return list;
//        }
//        for (Class<?> clazz : classList){
//            try {
//                boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
//                if(isAbstract){continue;}
//                /**
//                 * 强制继续实现方法转换类增加到拦截器中;
//                 */
//                boolean isResolver = AbstractHandlerArgumentResolver.class.isAssignableFrom(clazz);
//                if (isResolver){
//                    list.add((HandlerMethodArgumentResolver)clazz.newInstance());
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
////        super.addArgumentResolvers(argumentResolvers);
//        //与spring的参数验证有冲突，相当于所有参数自己解析处理
//        List<HandlerMethodArgumentResolver> argumentResolverList = addArgumentResolversExt();
//        argumentResolvers.addAll(argumentResolverList);
//
////        argumentResolvers.add(new JsonRequestHandlerArgumentResolver());
////        argumentResolvers.add(new JsonReturnHandlerArgumentResolver());
////        argumentResolvers.add(new JsonResponseHandlerArgumentResolver());
//    }
//
//
//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
////        super.addReturnValueHandlers(returnValueHandlers);
////        returnValueHandlers.add(new ResponseBodyHandlerArgumentResolver());
////        returnValueHandlers.add(new EncryptBodyRturnValueHandler());
//
//    }
//
//
//
//
//
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (!registry.hasMappingForPattern("/webjars/**")) {
//            registry.addResourceHandler("/webjars/**")
//                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }
//        registry.addResourceHandler("/**")
//                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
////        super.addResourceHandlers(registry);
//
//
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")//设置允许跨域的路径
//                .allowedOrigins("*")//设置允许跨域请求的域名
//                .allowCredentials(false)//是否允许证书 不再默认开启
//                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
//                .maxAge(360000);//跨域允许时间
//    }
//
//}
