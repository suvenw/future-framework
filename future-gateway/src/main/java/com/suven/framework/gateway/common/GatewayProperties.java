package com.suven.framework.gateway.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway 配置属性
 */
@Component
@ConfigurationProperties(prefix = "gateway.config")
public class GatewayProperties {

    /** 是否开启 JWT 鉴权 */
    private boolean authEnabled = false;

    /** JWT 密钥 */
    private String jwtSecret = "future-gateway-secret-key";

    /** JWT 过期时间（秒） */
    private long jwtExpiration = 604800;

    /** 白名单路径列表 */
    private List<String> whiteListPaths = new ArrayList<>();

    /** 是否开启请求日志 */
    private boolean logEnabled = true;

    /** 是否开启响应日志 */
    private boolean responseLogEnabled = false;

    /** 是否开启限流 */
    private boolean rateLimitEnabled = false;

    /** 限流容量 */
    private int rateLimitCapacity = GatewayConstant.DEFAULT_RATE_CAPACITY;

    /** 限流速率（每秒） */
    private int rateLimitTokens = GatewayConstant.DEFAULT_RATE_TOKENS;

    public boolean isAuthEnabled() {
        return authEnabled;
    }

    public void setAuthEnabled(boolean authEnabled) {
        this.authEnabled = authEnabled;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }

    public void setJwtExpiration(long jwtExpiration) {
        this.jwtExpiration = jwtExpiration;
    }

    public List<String> getWhiteListPaths() {
        return whiteListPaths;
    }

    public void setWhiteListPaths(List<String> whiteListPaths) {
        this.whiteListPaths = whiteListPaths;
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public boolean isResponseLogEnabled() {
        return responseLogEnabled;
    }

    public void setResponseLogEnabled(boolean responseLogEnabled) {
        this.responseLogEnabled = responseLogEnabled;
    }

    public boolean isRateLimitEnabled() {
        return rateLimitEnabled;
    }

    public void setRateLimitEnabled(boolean rateLimitEnabled) {
        this.rateLimitEnabled = rateLimitEnabled;
    }

    public int getRateLimitCapacity() {
        return rateLimitCapacity;
    }

    public void setRateLimitCapacity(int rateLimitCapacity) {
        this.rateLimitCapacity = rateLimitCapacity;
    }

    public int getRateLimitTokens() {
        return rateLimitTokens;
    }

    public void setRateLimitTokens(int rateLimitTokens) {
        this.rateLimitTokens = rateLimitTokens;
    }
}
