package com.suven.framework.gateway.config;

import com.suven.framework.gateway.filter.CustomGlobalFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

//    @Bean
//    public CustomGlobalFilter customGlobalFilter() {
//        return new CustomGlobalFilter();
//    }
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        RouteLocatorBuilder.Builder route = builderRoute(builder);
//        return route.build();
//
//    }
//
//    private  RouteLocatorBuilder.Builder builderRoute(RouteLocatorBuilder routes){
//        RouteLocatorBuilder.Builder builder =  routes.routes().route("example_route", r -> r.path("/example")
//                .uri("http://example.com"));
//
//       return builder;
//    }

}