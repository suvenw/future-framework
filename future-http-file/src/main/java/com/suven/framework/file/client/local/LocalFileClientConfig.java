package com.suven.framework.file.client.local;

import com.suven.framework.file.client.AbstractFileClientConfig;
import com.suven.framework.file.client.FileClientConfig;

import jakarta.validation.constraints.NotEmpty;

/**
 * 本地文件客户端的配置类
 */
public class LocalFileClientConfig extends AbstractFileClientConfig  implements FileClientConfig {

    /**
     * 基础路径
     */
    @NotEmpty(message = "基础路径不能为空")
    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
