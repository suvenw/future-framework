package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SaaS文件下载查询请求VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaaSFileDownloadQueryRequestVo {

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

    /** 页码 */
    @ApiDesc(value = "页码", required = 0)
    private int pageNo = 1;

    /** 每页数量 */
    @ApiDesc(value = "每页数量", required = 0)
    private int pageSize = 20;
}
