package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传业务流程响应VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
public class FileUploadProcessResponseVo {

    /** 是否成功 */
    @ApiDesc(value = "是否成功", required = 1)
    private boolean success;

    /** 消息 */
    @ApiDesc(value = "消息", required = 0)
    private String message;

    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    /** 业务功能ID */
    @ApiDesc(value = "业务功能ID", required = 0)
    private Long businessFunctionId;

    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码", required = 0)
    private String businessUniqueCode;

    /** 操作记录ID */
    @ApiDesc(value = "操作记录ID", required = 0)
    private Long operationRecordId;

    /** 文件名 */
    @ApiDesc(value = "文件名", required = 0)
    private String fileName;

    /** 总行数 */
    @ApiDesc(value = "总行数", required = 0)
    private int totalRows;

    /** 成功行数 */
    @ApiDesc(value = "成功行数", required = 0)
    private int successRows;

    /** 失败行数 */
    @ApiDesc(value = "失败行数", required = 0)
    private int failRows;

    /** 解释记录数量 */
    @ApiDesc(value = "解释记录数量", required = 0)
    private int interpretRecordCount;

    /** 数据校验错误列表 */
    @ApiDesc(value = "数据校验错误列表", required = 0)
    private List<String> validationErrors;

    /** 回调是否成功 */
    @ApiDesc(value = "回调是否成功", required = 0)
    private Boolean callbackSuccess;

    /** 开始时间 */
    @ApiDesc(value = "开始时间", required = 0)
    private LocalDateTime startTime;

    /** 结束时间 */
    @ApiDesc(value = "结束时间", required = 0)
    private LocalDateTime endTime;

    public FileUploadProcessResponseVo() {
        this.validationErrors = new ArrayList<>();
    }

    public static FileUploadProcessResponseVo build() {
        return new FileUploadProcessResponseVo();
    }

    /**
     * 添加校验错误
     */
    public void addValidationError(String error) {
        if (this.validationErrors == null) {
            this.validationErrors = new ArrayList<>();
        }
        this.validationErrors.add(error);
    }
}
