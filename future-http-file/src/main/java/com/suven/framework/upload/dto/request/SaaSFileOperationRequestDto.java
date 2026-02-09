package com.suven.framework.upload.dto.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * SaaS文件操作记录请求DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileOperationRequestDto extends BaseTenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 操作记录ID (查询时使用) */
    @ApiDesc(value = "操作记录ID", required = 0)
    private long id;

    /** 业务公司id */
    @ApiDesc(value = "业务公司id", required = 0)
    private String companyId;

    /** 业务公司名称 */
    @ApiDesc(value = "业务公司名称", required = 0)
    private String companyName;

    /** 上传人员ID */
    @ApiDesc(value = "上传人员ID", required = 0)
    private long uploadUserId;

    /** 上传人员名称 */
    @ApiDesc(value = "上传人员名称", required = 0)
    private String uploadUserName;

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 1)
    private long useBusinessId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 1)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 1)
    private String fileBusinessName;

    /** 功能类型: IMPORT, EXPORT, UPLOAD, DOWNLOAD */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;

    /** 平台类型: WEB, APP, MINI, API */
    @ApiDesc(value = "平台类型", required = 0)
    private String platformType;

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 1)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 1)
    private String fileType;

    /** 操作状态: PENDING, PROCESSING, COMPLETED, FAILED */
    @ApiDesc(value = "操作状态", required = 0)
    private String status;

    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调", required = 0)
    private int needCallback;

    /** 回调URL */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;

    /** 创建时间-开始 */
    @ApiDesc(value = "创建时间-开始", required = 0)
    private Date createDateStart;

    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private Date createDateEnd;

    /** 字段映射列表 */
    @ApiDesc(value = "字段映射列表", required = 0)
    private List<SaaSFileFieldRequestDto> fieldMappings;

    public static SaaSFileOperationRequestDto build() {
        return new SaaSFileOperationRequestDto();
    }

    public SaaSFileOperationRequestDto toId(long id) {
        this.id = id;
        return this;
    }

    public SaaSFileOperationRequestDto toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public SaaSFileOperationRequestDto toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SaaSFileOperationRequestDto toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public SaaSFileOperationRequestDto toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public SaaSFileOperationRequestDto toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileOperationRequestDto toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileOperationRequestDto toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileOperationRequestDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileOperationRequestDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileOperationRequestDto toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public SaaSFileOperationRequestDto toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public SaaSFileOperationRequestDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileOperationRequestDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileOperationRequestDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileOperationRequestDto toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSFileOperationRequestDto toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public SaaSFileOperationRequestDto toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public SaaSFileOperationRequestDto toCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public SaaSFileOperationRequestDto toCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }

    public SaaSFileOperationRequestDto toFieldMappings(List<SaaSFileFieldRequestDto> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }
}
