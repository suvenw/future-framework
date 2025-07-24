package com.suven.framework.test.junit;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration("serverPort")
@ConfigurationProperties(prefix = GlobalConfigConstants.JETTY_SERVER_PORT_SETTINGS)
public class JettyServerPortSettings {
    private HttpServerPort http = new HttpServerPort();
    private RpcServerPort rpc = new RpcServerPort();



    public int getPort(String key) {
       int port = 0;
        try{
            port = (Integer)PropertyUtils.getProperty(http, key);
        }catch (Exception e){ }
        return port;
    }
    public int getRpcPort(String key) {
        int port = 0;
        try{
            port = (Integer)PropertyUtils.getProperty(rpc, key);
        }catch (Exception e){ }
        return port;
    }

    public HttpServerPort getHttp() {
        return http;
    }

    public void setHttp(HttpServerPort http) {
        this.http = http;
    }

    public RpcServerPort getRpc() {
        return rpc;
    }

    public void setRpc(RpcServerPort rpc) {
        this.rpc = rpc;
    }

    public static class  HttpServerPort{
        private int user = 9010;
        private int oauth  = 9020;
        private int resource  = 9030;
        private int media = 9180;
        private int statistic = 9120;
        private int assets  = 9050;
        private int video  = 9060;
        private int order  = 9070;
        private int pay  = 9080;
        private int config  = 9090;

        public int getUser() {
            return user;
        }

        public HttpServerPort setUser(int user) {
            this.user = user;
            return this;
        }

        public int getOauth() {
            return oauth;
        }

        public HttpServerPort setOauth(int oauth) {
            this.oauth = oauth;
            return this;
        }

        public int getResource() {
            return resource;
        }

        public HttpServerPort setResource(int resource) {
            this.resource = resource;
            return this;
        }

        public int getAssets() {
            return assets;
        }

        public HttpServerPort setAssets(int assets) {
            this.assets = assets;
            return this;
        }

        public int getVideo() {
            return video;
        }

        public HttpServerPort setVideo(int video) {
            this.video = video;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public HttpServerPort setOrder(int order) {
            this.order = order;
            return this;
        }

        public int getPay() {
            return pay;
        }

        public HttpServerPort setPay(int pay) {
            this.pay = pay;
            return this;
        }

        public int getConfig() {
            return config;
        }

        public HttpServerPort setConfig(int config) {
            this.config = config;
            return this;
        }

        public int getMedia() {
            return media;
        }

        public HttpServerPort setMedia(int media) {
            this.media = media;
            return this;
        }

        public int getStatistic() {
            return statistic;
        }

        public void setStatistic(int statistic) {
            this.statistic = statistic;
        }
    }

    public class  RpcServerPort{
        private int user = 29010;
        private int oauth  = 29020;
        private int resource  = 29030;
        private int media = 29180;
        private int statistic = 29190;

        private int assets  = 29050;
        private int video  = 29060;
        private int order  = 29070;
        private int pay  = 29080;
        private int config  = 29090;
        private int log  = 29150;


        public int getUser() {
            return user;
        }

        public RpcServerPort setUser(int user) {
            this.user = user;
            return this;
        }

        public int getOauth() {
            return oauth;
        }

        public RpcServerPort setOauth(int oauth) {
            this.oauth = oauth;
            return this;
        }

        public int getResource() {
            return resource;
        }

        public RpcServerPort setResource(int resource) {
            this.resource = resource;
            return this;
        }

        public int getAssets() {
            return assets;
        }

        public RpcServerPort setAssets(int assets) {
            this.assets = assets;
            return this;
        }

        public int getVideo() {
            return video;
        }

        public RpcServerPort setVideo(int video) {
            this.video = video;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public RpcServerPort setOrder(int order) {
            this.order = order;
            return this;
        }

        public int getPay() {
            return pay;
        }

        public RpcServerPort setPay(int pay) {
            this.pay = pay;
            return this;
        }

        public int getConfig() {
            return config;
        }

        public RpcServerPort setConfig(int config) {
            this.config = config;
            return this;
        }

        public int getLog() {
            return log;
        }

        public RpcServerPort setLog(int log) {
            this.log = log;
            return this;
        }

        public int getMedia() {
            return media;
        }

        public RpcServerPort setMedia(int media) {
            this.media = media;
            return this;
        }

        public int getStatistic() {
            return statistic;
        }

        public RpcServerPort setStatistic(int statistic) {
            this.statistic = statistic;
            return this;
        }
    }






}
