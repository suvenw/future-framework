package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * SaaS文件业务回调请求VO
 * 
 * 业务方调用此接口通知数据已解释完成，或者回写处理结果
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileCallbackRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 解释记录ID */
    @ApiDesc(value = "解释记录ID", required = 1)
    private long interpretRecordId;

    /** 业务申请唯一码 */
    @ApiDesc(value = "业务申请唯一码", required = 1)
    private String businessUniqueCode;

    /** 业务处理状态: SUCCESS, FAILED */
    @ApiDesc(value = "业务处理状态", required = 1)
    private String businessProcessStatus;

    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果描述", required = 0)
    private String businessProcessResult;

    /** 业务处理异常信息 */
    @ApiDesc(value = "业务处理异常信息", required = 0)
    private String businessExceptionInfo;

    /** 处理的业务数据ID（可选） */
    @ApiDesc(value = "处理的业务数据ID", required = 0)
    private String businessDataId;

    /** 扩展字段1 */
    @ApiDesc(value = "扩展字段1", required = 0)
    private String extField1;

    /** 扩展字段2 */
    @ApiDesc(value = "扩展字段2", required = 0)
    private String extField2;

    /** 扩展字段3 */
    @ApiDesc(value = "扩展字段3", required = 0)
    private String extField3;

    /** 回调签名（用于验证回调来源） */
    @ApiDesc(value = "回调签名", required = 0)
    private String signature;

    /** 回调时间戳 */
    @ApiDesc(value = "回调时间戳", required = 0)
    private long timestamp;

    public static SaaSFileCallbackRequestVo build() {
        return new SaaSFileCallbackRequestVo();
    }

    public SaaSFileCallbackRequestVo toInterpretRecordId(long interpretRecordId) {
        this.interpretRecordId = interpretRecordId;
        return this;
    }

    public SaaSFileCallbackRequestVo toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public SaaSFileCallbackRequestVo toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public SaaSFileCallbackRequestVo toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public SaaSFileCallbackRequestVo toBusinessExceptionInfo(String businessExceptionInfo) {
        this.businessExceptionInfo = businessExceptionInfo;
        return this;
    }

    public SaaSFileCallbackRequestVo toBusinessDataId(String businessDataId) {
        this.businessDataId = businessDataId;
        return this;
    }

    public SaaSFileCallbackRequestVo toExtField1(String extField1) {
        this.extField1 = extField1;
        return this;
    }

    public SaaSFileCallbackRequestVo toExtField2(String extField2) {
        this.extField2 = extField2;
        return this;
    }

    public SaaSFileCallbackRequestVo toExtField3(String extField3) {
        this.extField3 = extField3;
        return this;
    }

    public SaaSFileCallbackRequestVo toSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public SaaSFileCallbackRequestVo toTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
