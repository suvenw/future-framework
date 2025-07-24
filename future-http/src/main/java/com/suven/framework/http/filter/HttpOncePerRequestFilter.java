package com.suven.framework.http.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter是Spring框架提供的一个过滤器基类，它确保每个请求只会被过滤一次，无论请求的资源是什么。这个基类实现了javax.servlet.Filter接口，并提供了一些钩子方法，允许您在请求被过滤前后执行自定义逻辑。
 * 以下是一个使用OncePerRequestFilter的示例及介绍：
 * 在上面的示例中，我们创建了一个名为MyFilter的自定义过滤器，继承自OncePerRequestFilter。我们重写了doFilterInternal方法，在该方法中可以编写自定义的过滤逻辑。
 * 在doFilterInternal方法中，我们首先执行在请求被过滤之前需要处理的逻辑，例如打印日志、验证请求等。然后，我们调用filterChain.doFilter(request, response)方法将请求传递给下一个过滤器或处理程序。最后，我们执行在请求被过滤之后需要处理的逻辑，例如处理响应、记录日志等。
 * 要将自定义过滤器添加到Spring应用程序中，您可以通过配置FilterRegistrationBean或使用@Bean注解注册过滤器。以下是一个注册过滤器的示例：
 * import org.springframework.boot.web.servlet.FilterRegistrationBean;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * @Configuration
 * public class MyFilterConfig {
 *
 *     @Bean
 *     public FilterRegistrationBean<MyFilter> myFilterRegistrationBean() {
 *         FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
 *         registrationBean.setFilter(new MyFilter());
 *         registrationBean.addUrlPatterns("/*"); // 设置过滤的URL模式
 *         registrationBean.setOrder(1); // 设置过滤器的执行顺序
 *         return registrationBean;
 *     }
 * }
 * 在上面的示例中，我们创建了一个FilterRegistrationBean实例，并将自定义过滤器MyFilter设置为其过滤器。然后，我们使用addUrlPatterns方法设置过滤的URL模式，这里使用"/*"表示匹配所有的请求。最后，我们可以使用setOrder方法设置过滤器的执行顺序，数字越小优先级越高。
 * 通过配置FilterRegistrationBean并将其声明为@Bean，Spring框架会自动将过滤器添加到应用程序的过滤器链中。
 * 请注意，OncePerRequestFilter适用于需要在每个请求上执行的逻辑，无论请求的资源是什么。如果您只需要对特定URL模式的请求执行过滤逻辑，您可以考虑使用GenericFilterBean或AbstractRequestLoggingFilter等其他过滤器基类。
 */
public class HttpOncePerRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 在请求被过滤之前执行的逻辑
        System.out.println("Before filtering...");

        // 执行过滤链，将请求传递给下一个过滤器或处理程序
        filterChain.doFilter(request, response);

        // 在请求被过滤之后执行的逻辑
        System.out.println("After filtering...");
    }
}