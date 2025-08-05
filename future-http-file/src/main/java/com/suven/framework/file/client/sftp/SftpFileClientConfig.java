package com.suven.framework.file.client.sftp;

import com.suven.framework.file.client.AbstractFileClientConfig;
import com.suven.framework.file.client.FileClientConfig;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Sftp 文件客户端的配置类
 */
public class SftpFileClientConfig extends AbstractFileClientConfig implements FileClientConfig {

    /**
     * 基础路径
     */
    @NotEmpty(message = "基础路径不能为空")
    private String basePath;

    /**
     * 主机地址
     */
    @NotEmpty(message = "host 不能为空")
    private String host;
    /**
     * 主机端口
     */
    @NotNull(message = "port 不能为空")
    private Integer port;
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
