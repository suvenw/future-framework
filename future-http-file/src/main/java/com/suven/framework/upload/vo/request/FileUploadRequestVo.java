package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpFromRequestVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * SaaS平台文件上传请求VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequestVo extends HttpFromRequestVo {

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 1)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 1)
    private String fileBusinessName;

    /** 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否 */
    @ApiDesc(value = "是否需要解释数据", required = 0)
    private int interpretData;

    /** 幂等性标识,用于防止重复上传 */
    @ApiDesc(value = "幂等性标识", required = 0)
    private String idempotent;

    /** 上传文件 */
    @ApiDesc(value = "上传文件", required = 1)
    private MultipartFile file;

    public static FileUploadRequestVo build() {
        return new FileUploadRequestVo();
    }

    public FileUploadRequestVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileUploadRequestVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public FileUploadRequestVo toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public FileUploadRequestVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileUploadRequestVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileUploadRequestVo toInterpretData(int interpretData) {
        this.interpretData = interpretData;
        return this;
    }

    public FileUploadRequestVo toIdempotent(String idempotent) {
        this.idempotent = idempotent;
        return this;
    }
}
