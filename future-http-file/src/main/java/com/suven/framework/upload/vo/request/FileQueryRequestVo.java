package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * SaaS平台文件查询请求VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileQueryRequestVo extends HttpRequestByIdPageVo {

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 0)
    private String fileType;

    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    /** 创建时间-开始 */
    @ApiDesc(value = "创建时间-开始", required = 0)
    private LocalDateTime createDateStart;

    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private LocalDateTime createDateEnd;

    public static FileQueryRequestVo build() {
        return new FileQueryRequestVo();
    }

    public FileQueryRequestVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileQueryRequestVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public FileQueryRequestVo toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public FileQueryRequestVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileQueryRequestVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileQueryRequestVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileQueryRequestVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileQueryRequestVo toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public FileQueryRequestVo toCreateDateStart(LocalDateTime createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public FileQueryRequestVo toCreateDateEnd(LocalDateTime createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }
}
