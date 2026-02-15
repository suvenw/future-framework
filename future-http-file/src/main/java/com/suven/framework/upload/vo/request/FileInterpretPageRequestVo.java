package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SaaS 文件解释结果分页查询请求VO
 *
 * 统一封装业务唯一码、回调批次号等业务标识和分页参数，
 * 便于基于同一个对象进行多业务场景的扩展。
 *
 * 继承 {@link HttpRequestByIdPageVo}，内置 pageNo/pageSize 等分页字段。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileInterpretPageRequestVo extends HttpRequestByIdPageVo {

    /** 业务申请唯一码，用于关联业务功能配置与解释记录 */
    @ApiDesc(value = "业务申请唯一码", required = 1)
    private String businessUniqueCode;

    /** 回调批次号（可选，用于精确查询某次回调对应的数据） */
    @ApiDesc(value = "回调批次号", required = 0)
    private String callbackBatchId;

    /** 业务公司ID（可选，用于多租户/多公司过滤） */
    @ApiDesc(value = "业务公司ID", required = 0)
    private String companyId;

    /** 平台类型（可选，如 WEB/APP/MINI/API 等） */
    @ApiDesc(value = "平台类型", required = 0)
    private String platformType;

    public static FileInterpretPageRequestVo build() {
        return new FileInterpretPageRequestVo();
    }

    public FileInterpretPageRequestVo toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileInterpretPageRequestVo toCallbackBatchId(String callbackBatchId) {
        this.callbackBatchId = callbackBatchId;
        return this;
    }

    public FileInterpretPageRequestVo toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public FileInterpretPageRequestVo toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }
}

