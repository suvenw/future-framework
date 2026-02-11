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
 * SaaS平台文件解释记录表
 * 
 * 功能：记录文件解释的详细信息，包含解释信息JSON、业务申请唯一码、回调状态等
 *       关联业务文件上传表，记录每行数据的解释结果
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
public class SaaSFileInterpretRecord extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 关联信息 ====================
    
    /** 文件上传ID，关联saas_file_upload表 */
    @ApiDesc(value = "文件上传ID", required = 1)
    private long fileUploadId;
    
    /** 解释标识，用于关联字段映射 */
    @ApiDesc(value = "解释标识", required = 0)
    private String interpretKey;

    // ==================== 业务信息 ====================
    
    /** 业务申请唯一码，用于数据新增或修改 */
    @ApiDesc(value = "业务申请唯一码", required = 0)
    private String businessUniqueCode;
    
    /** 业务类型 */
    @ApiDesc(value = "业务类型", required = 0)
    private String businessType;
    
    /** 业务描述 */
    @ApiDesc(value = "业务描述", required = 0)
    private String businessDescription;

    // ==================== 解释信息 ====================
    
    /** 解释信息JSON，存储字段与值的映射关系 */
    @ApiDesc(value = "解释信息JSON", required = 0)
    private String interpretInfo;
    
    /** 解释状态: PENDING-待解释, PROCESSING-解释中, COMPLETED-已解释, FAILED-解释失败 */
    @ApiDesc(value = "解释状态", required = 0)
    private String interpretStatus;
    
    /** 解释进度 0-100 */
    @ApiDesc(value = "解释进度", required = 0)
    private int interpretProgress;
    
    /** 解释失败次数 */
    @ApiDesc(value = "解释失败次数", required = 0)
    private int interpretFailCount;
    
    /** 最后解释时间 */
    @ApiDesc(value = "最后解释时间", required = 0)
    private LocalDateTime lastInterpretTime;
    
    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    // ==================== 统计信息 ====================
    
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

    // ==================== 回调信息 ====================
    
    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调", required = 0)
    private int needCallback;
    
    /** 回调URL */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;
    
    /** 回调HTTP方法: GET, POST */
    @ApiDesc(value = "回调HTTP方法", required = 0)
    private String callbackMethod;
    
    /** 回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;
    
    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;
    
    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private LocalDateTime lastCallbackTime;
    
    /** 回调请求内容 */
    @ApiDesc(value = "回调请求内容", required = 0)
    private String callbackRequest;
    
    /** 回调响应内容 */
    @ApiDesc(value = "回调响应内容", required = 0)
    private String callbackResponse;

    // ==================== 业务处理结果 ====================
    
    /** 业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败 */
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

    // ==================== 扩展信息 ====================
    
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

    public static SaaSFileInterpretRecord build() {
        return new SaaSFileInterpretRecord();
    }

    public SaaSFileInterpretRecord toFileUploadId(long fileUploadId) {
        this.fileUploadId = fileUploadId;
        return this;
    }

    public SaaSFileInterpretRecord toInterpretKey(String interpretKey) {
        this.interpretKey = interpretKey;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
        return this;
    }

    public SaaSFileInterpretRecord toInterpretInfo(String interpretInfo) {
        this.interpretInfo = interpretInfo;
        return this;
    }

    public SaaSFileInterpretRecord toInterpretStatus(String interpretStatus) {
        this.interpretStatus = interpretStatus;
        return this;
    }

    public SaaSFileInterpretRecord toInterpretProgress(int interpretProgress) {
        this.interpretProgress = interpretProgress;
        return this;
    }

    public SaaSFileInterpretRecord toInterpretFailCount(int interpretFailCount) {
        this.interpretFailCount = interpretFailCount;
        return this;
    }

    public SaaSFileInterpretRecord toLastInterpretTime(LocalDateTime lastInterpretTime) {
        this.lastInterpretTime = lastInterpretTime;
        return this;
    }

    public SaaSFileInterpretRecord toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileInterpretRecord toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public SaaSFileInterpretRecord toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public SaaSFileInterpretRecord toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public SaaSFileInterpretRecord toSkipCount(int skipCount) {
        this.skipCount = skipCount;
        return this;
    }

    public SaaSFileInterpretRecord toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackMethod(String callbackMethod) {
        this.callbackMethod = callbackMethod;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public SaaSFileInterpretRecord toLastCallbackTime(LocalDateTime lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackRequest(String callbackRequest) {
        this.callbackRequest = callbackRequest;
        return this;
    }

    public SaaSFileInterpretRecord toCallbackResponse(String callbackResponse) {
        this.callbackResponse = callbackResponse;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessExceptionInfo(String businessExceptionInfo) {
        this.businessExceptionInfo = businessExceptionInfo;
        return this;
    }

    public SaaSFileInterpretRecord toBusinessProcessTime(LocalDateTime businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
        return this;
    }

    public SaaSFileInterpretRecord toExtField1(String extField1) {
        this.extField1 = extField1;
        return this;
    }

    public SaaSFileInterpretRecord toExtField2(String extField2) {
        this.extField2 = extField2;
        return this;
    }

    public SaaSFileInterpretRecord toExtField3(String extField3) {
        this.extField3 = extField3;
        return this;
    }

    public SaaSFileInterpretRecord toRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
