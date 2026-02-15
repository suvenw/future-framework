package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SaaS文件下载查询请求VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Setter@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadQueryRequestVo extends HttpRequestByIdPageVo {

    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码", required = 0)
    private String businessUniqueCode;

    /** 业务功能ID */
    @ApiDesc(value = "业务功能ID", required = 0)
    private Long businessFunctionId;

    /** 生成状态 */
    @ApiDesc(value = "生成状态", required = 0)
    private String generateStatus;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 0)
    private String fileType;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileName;

    /** 开始时间 */
    @ApiDesc(value = "开始时间", required = 0)
    private String startTime;

    /** 结束时间 */
    @ApiDesc(value = "结束时间", required = 0)
    private String endTime;


}
