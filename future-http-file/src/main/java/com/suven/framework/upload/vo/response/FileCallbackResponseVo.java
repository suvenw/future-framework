package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SaaS文件业务回调响应VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileCallbackResponseVo extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 解释记录ID */
    @ApiDesc(value = "解释记录ID", required = 1)
    private long interpretRecordId;

    /** 业务申请唯一码 */
    @ApiDesc(value = "业务申请唯一码", required = 1)
    private String businessUniqueCode;

    /** 解释标识 */
    @ApiDesc(value = "解释标识", required = 1)
    private String interpretKey;

    /** 解释信息JSON */
    @ApiDesc(value = "解释信息JSON", required = 0)
    private String interpretInfo;

    /** 解释状态 */
    @ApiDesc(value = "解释状态", required = 1)
    private String interpretStatus;

    /** 数据总条数 */
    @ApiDesc(value = "数据总条数", required = 0)
    private int totalCount;

    /** 成功解释条数 */
    @ApiDesc(value = "成功解释条数", required = 0)
    private int successCount;

    /** 失败解释条数 */
    @ApiDesc(value = "失败解释条数", required = 0)
    private int failCount;

    /** 业务处理状态 */
    @ApiDesc(value = "业务处理状态", required = 0)
    private String businessProcessStatus;

    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果描述", required = 0)
    private String businessProcessResult;

    /** 回调状态 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;

    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;

    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private LocalDateTime lastCallbackTime;
    
    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private LocalDateTime createDate;
    
    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private LocalDateTime modifyDate;

    public static FileCallbackResponseVo build() {
        return new FileCallbackResponseVo();
    }

    public FileCallbackResponseVo toInterpretRecordId(long interpretRecordId) {
        this.interpretRecordId = interpretRecordId;
        return this;
    }

    public FileCallbackResponseVo toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileCallbackResponseVo toInterpretKey(String interpretKey) {
        this.interpretKey = interpretKey;
        return this;
    }

    public FileCallbackResponseVo toInterpretInfo(String interpretInfo) {
        this.interpretInfo = interpretInfo;
        return this;
    }

    public FileCallbackResponseVo toInterpretStatus(String interpretStatus) {
        this.interpretStatus = interpretStatus;
        return this;
    }

    public FileCallbackResponseVo toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FileCallbackResponseVo toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public FileCallbackResponseVo toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public FileCallbackResponseVo toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public FileCallbackResponseVo toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public FileCallbackResponseVo toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public FileCallbackResponseVo toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public FileCallbackResponseVo toLastCallbackTime(LocalDateTime lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }


}
