package com.suven.framework.file.client;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotEmpty;
public abstract class AbstractFileClientConfig implements FileClientConfig{



    /**
     * 自定义域名
     * 1. MinIO：通过 Nginx 配置
     * 2. 阿里云：https://help.aliyun.com/document_detail/31836.html
     * 3. 腾讯云：https://cloud.tencent.com/document/product/436/11142
     * 4. 七牛云：https://developer.qiniu.com/kodo/8556/set-the-custom-source-domain-name
     * 5. 华为云：https://support.huaweicloud.com/usermanual-obs/obs_03_0032.html
     */
    @NotEmpty(message = "domain 不能为空")
    @URL(message = "domain 必须是 URL 格式")
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
