package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import lombok.Data;

import java.util.Map;

/**
 * 文件上传请求VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
public class FileUploadRequestVo {

    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码", required = 1)
    private String businessUniqueCode;

    /** 应用ID */
    @ApiDesc(value = "应用ID", required = 0)
    private String appId;

    /** 客户端ID */
    @ApiDesc(value = "客户端ID", required = 0)
    private String clientId;

    /** 上传用户ID */
    @ApiDesc(value = "上传用户ID", required = 0)
    private Long uploadUserId;

    /** 上传用户名 */
    @ApiDesc(value = "上传用户名", required = 0)
    private String uploadUserName;

    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调: 0-否, 1-是", required = 0)
    private Integer needCallback;

    /** 回调URL（可选，如果不传则使用业务配置中的回调URL） */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;

    /** 额外参数（会传递给回调接口） */
    @ApiDesc(value = "额外参数", required = 0)
    private Map<String, Object> extraParams;

    public static FileUploadRequestVo build() {
        return new FileUploadRequestVo();
    }
}
