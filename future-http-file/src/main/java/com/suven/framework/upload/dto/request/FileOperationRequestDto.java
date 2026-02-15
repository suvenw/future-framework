package com.suven.framework.upload.dto.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class FileOperationRequestDto extends BaseTenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;


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
    private LocalDateTime createDateStart;
    
    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private LocalDateTime createDateEnd;

    /** 字段映射列表 */
    @ApiDesc(value = "字段映射列表", required = 0)
    private List<FileFieldRequestDto> fieldMappings;

    public static FileOperationRequestDto build() {
        return new FileOperationRequestDto();
    }



    public FileOperationRequestDto toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public FileOperationRequestDto toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public FileOperationRequestDto toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public FileOperationRequestDto toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public FileOperationRequestDto toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileOperationRequestDto toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public FileOperationRequestDto toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public FileOperationRequestDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileOperationRequestDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileOperationRequestDto toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public FileOperationRequestDto toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public FileOperationRequestDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public FileOperationRequestDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileOperationRequestDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileOperationRequestDto toStatus(String status) {
        this.status = status;
        return this;
    }

    public FileOperationRequestDto toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public FileOperationRequestDto toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public FileOperationRequestDto toCreateDateStart(LocalDateTime createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public FileOperationRequestDto toCreateDateEnd(LocalDateTime createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }

    public FileOperationRequestDto toFieldMappings(List<FileFieldRequestDto> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }
}
