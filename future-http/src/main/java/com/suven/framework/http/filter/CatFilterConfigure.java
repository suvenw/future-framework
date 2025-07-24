//package top.suven.future.http.filter;
//
//import com.dianping.cat.Cat;
//import com.dianping.cat.servlet.CatFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CatFilterConfigure {
//
////    @Bean
//    public FilterRegistrationBean catFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        CatFilter filter = new CatFilter();
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
////        registration.addUrlPatterns("/base/*","/server/*");
//        registration.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        registration.setName("cat-filter");
//        registration.setOrder(2);
//        Cat.getManager().setTraceMode(true);
//        return registration;
//    }
//
////    @Bean
////    public FilterRegistrationBean catFilter() {
////        FilterRegistrationBean registration = new FilterRegistrationBean(new CatContextFilter());
////        registration.addUrlPatterns("/base/*","/server/*");
////        registration.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
////        registration.setName("cat-filter");
////        registration.setOrder(2);
////        Cat.getManager().setTraceMode(true);
////        return registration;
////    }
//
////    /**
////     * 注册一个：filterRegistrationBean
////     * @return
////     */
////    @Bean
////    public FilterRegistrationBean catContextFilter(){
////        FilterRegistrationBean registration = new FilterRegistrationBean(new CatContextFilter());
////        //添加过滤规则.
////        registration.addUrlPatterns("/top/suven/base/*","/server/*");
////        //添加不需要忽略的格式信息.
////        registration.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
////        registration.setName("cat-filter");
////        registration.setOrder(2);
////        Cat.getManager().setTraceMode(true);
////        return registration;
////    }
//}