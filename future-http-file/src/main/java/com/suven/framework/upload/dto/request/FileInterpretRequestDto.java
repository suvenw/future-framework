package com.suven.framework.upload.dto.request;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * SaaS文件解释记录请求DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileInterpretRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 解释记录ID (查询时使用) */
    @ApiDesc(value = "解释记录ID", required = 0)
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

    /** 解释状态: PENDING, PROCESSING, COMPLETED, FAILED */
    @ApiDesc(value = "解释状态", required = 1)
    private String interpretStatus;

    /** 数据总条数 */
    @ApiDesc(value = "数据总条数", required = 0)
    private int totalCount;

    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调", required = 0)
    private int needCallback;

    /** 回调URL */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;

    /** 回调HTTP方法: GET, POST */
    @ApiDesc(value = "回调HTTP方法", required = 0)
    private String callbackMethod;

    /** 业务处理状态: PENDING, PROCESSING, SUCCESS, FAILED */
    @ApiDesc(value = "业务处理状态", required = 0)
    private String businessProcessStatus;

    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果描述", required = 0)
    private String businessProcessResult;

    /** 业务处理异常信息 */
    @ApiDesc(value = "业务处理异常信息", required = 0)
    private String businessExceptionInfo;

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

    public static FileInterpretRequestDto build() {
        return new FileInterpretRequestDto();
    }

    public FileInterpretRequestDto toId(long id) {
        this.id = id;
        return this;
    }

    public FileInterpretRequestDto toOperationRecordId(long operationRecordId) {
        this.operationRecordId = operationRecordId;
        return this;
    }

    public FileInterpretRequestDto toInterpretKey(String interpretKey) {
        this.interpretKey = interpretKey;
        return this;
    }

    public FileInterpretRequestDto toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileInterpretRequestDto toBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public FileInterpretRequestDto toBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
        return this;
    }

    public FileInterpretRequestDto toInterpretInfo(String interpretInfo) {
        this.interpretInfo = interpretInfo;
        return this;
    }

    public FileInterpretRequestDto toInterpretStatus(String interpretStatus) {
        this.interpretStatus = interpretStatus;
        return this;
    }

    public FileInterpretRequestDto toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FileInterpretRequestDto toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public FileInterpretRequestDto toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public FileInterpretRequestDto toCallbackMethod(String callbackMethod) {
        this.callbackMethod = callbackMethod;
        return this;
    }

    public FileInterpretRequestDto toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public FileInterpretRequestDto toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public FileInterpretRequestDto toBusinessExceptionInfo(String businessExceptionInfo) {
        this.businessExceptionInfo = businessExceptionInfo;
        return this;
    }

    public FileInterpretRequestDto toExtField1(String extField1) {
        this.extField1 = extField1;
        return this;
    }

    public FileInterpretRequestDto toExtField2(String extField2) {
        this.extField2 = extField2;
        return this;
    }

    public FileInterpretRequestDto toExtField3(String extField3) {
        this.extField3 = extField3;
        return this;
    }

    public FileInterpretRequestDto toRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
