package com.suven.framework.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;



@Component
public class CustomFilterFactory extends AbstractGatewayFilterFactory<CustomFilterFactory.Config> {

    public CustomFilterFactory() {
        super(Config.class);
    }
    private Logger logger = LoggerFactory.getLogger(CustomFilterFactory.class);

    @Override
    public GatewayFilter apply(Config config) {
        // create and return a filter based on the config

        return null;
    }

    public static class Config {
        // define properties for the filter
    }
}