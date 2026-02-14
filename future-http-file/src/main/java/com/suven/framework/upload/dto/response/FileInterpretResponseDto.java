package com.suven.framework.upload.dto.response;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SaaS文件解释记录响应DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileInterpretResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 解释记录ID */
    @ApiDesc(value = "解释记录ID", required = 1)
    private long id;

    /** 操作记录ID */
    @ApiDesc(value = "操作记录ID", required = 1)
    private long operationRecordId;

    /** 解释标识 */
    @ApiDesc(value = "解释标识", required = 1)
    private String interpretKey;

    /** 业务申请唯一码 */
    @ApiDesc(value = "业务申请唯一码", required = 1)
    private String businessUniqueCode;

    /** 业务类型 */
    @ApiDesc(value = "业务类型", required = 0)
    private String businessType;

    /** 业务描述 */
    @ApiDesc(value = "业务描述", required = 0)
    private String businessDescription;

    /** 解释信息JSON */
    @ApiDesc(value = "解释信息JSON", required = 0)
    private String interpretInfo;

    /** 解释状态 */
    @ApiDesc(value = "解释状态", required = 1)
    private String interpretStatus;

    /** 解释进度 */
    @ApiDesc(value = "解释进度", required = 0)
    private int interpretProgress;

    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    /** 数据总条数 */
    @ApiDesc(value = "数据总条数", required = 0)
    private int totalCount;

    /** 成功解释条数 */
    @ApiDesc(value = "成功解释条数", required = 0)
    private int successCount;

    /** 失败解释条数 */
    @ApiDesc(value = "失败解释条数", required = 0)
    private int failCount;

    /** 跳过解释条数 */
    @ApiDesc(value = "跳过解释条数", required = 0)
    private int skipCount;

    /** 回调状态 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;

    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;

    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private LocalDateTime lastCallbackTime;

    /** 业务处理状态 */
    @ApiDesc(value = "业务处理状态", required = 0)
    private String businessProcessStatus;

    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果描述", required = 0)
    private String businessProcessResult;

    /** 业务处理异常信息 */
    @ApiDesc(value = "业务处理异常信息", required = 0)
    private String businessExceptionInfo;

    /** 业务处理时间 */
    @ApiDesc(value = "业务处理时间", required = 0)
    private LocalDateTime businessProcessTime;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private LocalDateTime createDate;
    
    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private LocalDateTime modifyDate;

    public static FileInterpretResponseDto build() {
        return new FileInterpretResponseDto();
    }

    public FileInterpretResponseDto toId(long id) {
        this.id = id;
        return this;
    }

    public FileInterpretResponseDto toOperationRecordId(long operationRecordId) {
        this.operationRecordId = operationRecordId;
        return this;
    }

    public FileInterpretResponseDto toInterpretKey(String interpretKey) {
        this.interpretKey = interpretKey;
        return this;
    }

    public FileInterpretResponseDto toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileInterpretResponseDto toBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public FileInterpretResponseDto toBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
        return this;
    }

    public FileInterpretResponseDto toInterpretInfo(String interpretInfo) {
        this.interpretInfo = interpretInfo;
        return this;
    }

    public FileInterpretResponseDto toInterpretStatus(String interpretStatus) {
        this.interpretStatus = interpretStatus;
        return this;
    }

    public FileInterpretResponseDto toInterpretProgress(int interpretProgress) {
        this.interpretProgress = interpretProgress;
        return this;
    }

    public FileInterpretResponseDto toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public FileInterpretResponseDto toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FileInterpretResponseDto toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public FileInterpretResponseDto toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public FileInterpretResponseDto toSkipCount(int skipCount) {
        this.skipCount = skipCount;
        return this;
    }

    public FileInterpretResponseDto toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public FileInterpretResponseDto toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public FileInterpretResponseDto toLastCallbackTime(Date lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public FileInterpretResponseDto toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public FileInterpretResponseDto toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public FileInterpretResponseDto toBusinessExceptionInfo(String businessExceptionInfo) {
        this.businessExceptionInfo = businessExceptionInfo;
        return this;
    }

    public FileInterpretResponseDto toBusinessProcessTime(Date businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
        return this;
    }

    public FileInterpretResponseDto toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public FileInterpretResponseDto toModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }
}
