package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 文件解释记录明细表
 * 
 * 功能：记录文件解析后的每行数据详情，关联业务功能、字段映射和文件上传
 * 
 * 关联关系：
 * ├─ 关联公司业务功能（N:1）- businessFunctionId -> CompanyBusinessFunction.id
 * ├─ 关联字段映射（N:1）- fieldMappingId -> FileFieldMapping.id
 * ├─ 关联文件上传管理（N:1）- fileUploadId -> FileUpload.id
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "saas_file_interpret_record")
public class FileInterpretRecord extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 关联关系 ====================
    
    /** 公司业务功能ID，关联 saas_company_business_function 表 */
    @ApiDesc(value = "公司业务功能ID", required = 1)
    private long businessFunctionId;
    
    /** 字段映射ID，关联 saas_file_field_mapping 表 */
    @ApiDesc(value = "字段映射ID", required = 0)
    private long fieldMappingId;
    
    /** 文件上传管理ID，关联 saas_file_upload 表 */
    @ApiDesc(value = "文件上传管理ID", required = 1)
    private long fileUploadId;

    // ==================== 行号记录 ====================
    
    /** 行号记录，当前数据在文件中的行号 */
    @ApiDesc(value = "行号", required = 1)
    private int rowNumber;
    
    /** 行序号，用于排序展示 */
    @ApiDesc(value = "行序号", required = 0)
    private int rowIndex;

    // ==================== 数据存储 ====================

    /** 原始数据 JSON 存储，未转换的原始数据 */
    @ApiDesc(value = "原始数据JSON", required = 0)
    private String rawDataJson;

    /** 解析数据 JSON 存储，转换后的数据（英文字段名 -> 值） */
    @ApiDesc(value = "解析数据JSON", required = 0)
    private String dataJson;
    
    /** 解析后的中文数据 JSON（中文字段名 -> 值），用于业务处理 */
    @ApiDesc(value = "中文数据JSON", required = 0)
    private String chineseDataJson;

    // ==================== 校验状态 ====================
    
    /** 校验状态: PENDING-待校验, VALID-校验通过, INVALID-校验失败, WARNING-警告 */
    @ApiDesc(value = "校验状态", required = 0)
    private String checkStatus;
    
    /** 校验详情 JSON，存储各字段校验结果 */
    @ApiDesc(value = "校验详情JSON", required = 0)
    private String checkDetailJson;
    
    /** 校验通过数 */
    @ApiDesc(value = "校验通过数", required = 0)
    private int validCount;
    
    /** 校验失败数 */
    @ApiDesc(value = "校验失败数", required = 0)
    private int invalidCount;
    
    /** 警告数 */
    @ApiDesc(value = "警告数", required = 0)
    private int warningCount;

    // ==================== 异常信息记录 ====================
    
    /** 错误代码 */
    @ApiDesc(value = "错误代码", required = 0)
    private String errorCode;
    
    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;
    
    /** 异常堆栈信息 */
    @ApiDesc(value = "异常堆栈", required = 0)
    private String exceptionStack;
    
    /** 重试次数 */
    @ApiDesc(value = "重试次数", required = 0)
    private int retryCount;

    // ==================== 处理状态 ====================
    
    /** 处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败, SKIPPED-已跳过 */
    @ApiDesc(value = "处理状态", required = 0)
    private String processStatus;
    
    /** 处理进度 */
    @ApiDesc(value = "处理进度", required = 0)
    private int progressPercent;
    
    /** 处理消息 */
    @ApiDesc(value = "处理消息", required = 0)
    private String processMessage;

    // ==================== 业务处理结果 ====================
    
    /** 业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败 */
    @ApiDesc(value = "业务处理状态", required = 0)
    private String businessStatus;
    
    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果", required = 0)
    private String businessResult;
    
    /** 业务处理异常信息 */
    @ApiDesc(value = "业务异常信息", required = 0)
    private String businessErrorMessage;
    
    /** 业务处理时间 */
    @ApiDesc(value = "业务处理时间", required = 0)
    private LocalDateTime businessProcessTime;
    
    /** 业务处理完成时间 */
    @ApiDesc(value = "业务处理完成时间", required = 0)
    private LocalDateTime businessFinishTime;

    
    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码", required = 0)
    private String businessUniqueCode;
    
    /** 扩展字段1 */
    @ApiDesc(value = "扩展字段1", required = 0)
    private String extField1;
    
    /** 扩展字段2 */
    @ApiDesc(value = "扩展字段2", required = 0)
    private String extField2;
    
    /** 扩展字段3 */
    @ApiDesc(value = "扩展字段3", required = 0)
    private String extField3;
    
    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    // ==================== 构建辅助方法 ====================

    public static FileInterpretRecord build() {
        return new FileInterpretRecord();
    }

    public FileInterpretRecord toBusinessFunctionId(long businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
        return this;
    }

    public FileInterpretRecord toFieldMappingId(long fieldMappingId) {
        this.fieldMappingId = fieldMappingId;
        return this;
    }

    public FileInterpretRecord toFileUploadId(long fileUploadId) {
        this.fileUploadId = fileUploadId;
        return this;
    }

    public FileInterpretRecord toRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public FileInterpretRecord toRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public FileInterpretRecord toDataJson(String dataJson) {
        this.dataJson = dataJson;
        return this;
    }

    public FileInterpretRecord toRawDataJson(String rawDataJson) {
        this.rawDataJson = rawDataJson;
        return this;
    }

    public FileInterpretRecord toChineseDataJson(String chineseDataJson) {
        this.chineseDataJson = chineseDataJson;
        return this;
    }

    public FileInterpretRecord toCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
        return this;
    }

    public FileInterpretRecord toCheckDetailJson(String checkDetailJson) {
        this.checkDetailJson = checkDetailJson;
        return this;
    }

    public FileInterpretRecord toValidCount(int validCount) {
        this.validCount = validCount;
        return this;
    }

    public FileInterpretRecord toInvalidCount(int invalidCount) {
        this.invalidCount = invalidCount;
        return this;
    }

    public FileInterpretRecord toWarningCount(int warningCount) {
        this.warningCount = warningCount;
        return this;
    }

    public FileInterpretRecord toErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public FileInterpretRecord toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public FileInterpretRecord toExceptionStack(String exceptionStack) {
        this.exceptionStack = exceptionStack;
        return this;
    }

    public FileInterpretRecord toRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public FileInterpretRecord toProcessStatus(String processStatus) {
        this.processStatus = processStatus;
        return this;
    }

    public FileInterpretRecord toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public FileInterpretRecord toProcessMessage(String processMessage) {
        this.processMessage = processMessage;
        return this;
    }

    public FileInterpretRecord toBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
        return this;
    }

    public FileInterpretRecord toBusinessResult(String businessResult) {
        this.businessResult = businessResult;
        return this;
    }

    public FileInterpretRecord toBusinessErrorMessage(String businessErrorMessage) {
        this.businessErrorMessage = businessErrorMessage;
        return this;
    }

    public FileInterpretRecord toBusinessProcessTime(LocalDateTime businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
        return this;
    }

    public FileInterpretRecord toBusinessFinishTime(LocalDateTime businessFinishTime) {
        this.businessFinishTime = businessFinishTime;
        return this;
    }

    public FileInterpretRecord toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public FileInterpretRecord toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public FileInterpretRecord toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public FileInterpretRecord toLastCallbackTime(LocalDateTime lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public FileInterpretRecord toCallbackResponse(String callbackResponse) {
        this.callbackResponse = callbackResponse;
        return this;
    }

    public FileInterpretRecord toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileInterpretRecord toExtField1(String extField1) {
        this.extField1 = extField1;
        return this;
    }

    public FileInterpretRecord toExtField2(String extField2) {
        this.extField2 = extField2;
        return this;
    }

    public FileInterpretRecord toExtField3(String extField3) {
        this.extField3 = extField3;
        return this;
    }

    public FileInterpretRecord toRemark(String remark) {
        this.remark = remark;
        return this;
    }

    // ==================== 业务方法 ====================

    /**
     * 标记校验通过
     */
    public void markValid() {
        this.checkStatus = "VALID";
        this.validCount = 1;
        this.invalidCount = 0;
    }

    /**
     * 标记校验失败
     * @param errorMessage 错误信息
     */
    public void markInvalid(String errorMessage) {
        this.checkStatus = "INVALID";
        this.validCount = 0;
        this.invalidCount = 1;
        this.errorMessage = errorMessage;
    }

    /**
     * 标记业务处理成功
     * @param businessResult 业务结果
     */
    public void markBusinessSuccess(String businessResult) {
        this.businessStatus = "SUCCESS";
        this.businessResult = businessResult;
        this.businessProcessTime = LocalDateTime.now();
    }

    /**
     * 标记业务处理失败
     * @param errorMessage 错误信息
     */
    public void markBusinessFailed(String errorMessage) {
        this.businessStatus = "FAILED";
        this.businessErrorMessage = errorMessage;
        this.businessProcessTime = LocalDateTime.now();
    }

    /**
     * 标记处理中
     * @param message 处理消息
     */
    public void markProcessing(String message) {
        this.processStatus = "PROCESSING";
        this.processMessage = message;
    }

    /**
     * 增加重试次数
     */
    public void incrementRetry() {
        this.retryCount++;
    }

    /**
     * 是否可以重试
     * @param maxRetry 最大重试次数
     * @return 是否可以重试
     */
    public boolean canRetry(int maxRetry) {
        return this.retryCount < maxRetry;
    }
}
