package com.suven.framework.upload.facade;

import com.suven.framework.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SaaS平台文件上传下载外观类
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Component
public class SaaSFileFacade {

    @Autowired
    private FileService saaSFileService;

    public FileService getSaaSFileService() {
        return saaSFileService;
    }
}
