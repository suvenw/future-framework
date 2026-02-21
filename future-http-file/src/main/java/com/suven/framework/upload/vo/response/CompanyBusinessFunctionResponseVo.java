package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公司业务功能申请响应VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
public class CompanyBusinessFunctionResponseVo {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiDesc(value = "ID", required = 0)
    private Long id;

    // ==================== 公司/租户信息 ====================

    /** 公司ID */
    @ApiDesc(value = "公司ID", required = 1)
    private String companyId;

    /** 公司名称 */
    @ApiDesc(value = "公司名称", required = 0)
    private String companyName;

    // ==================== 业务配置信息 ====================

    /** 平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口 */
    @ApiDesc(value = "平台类型", required = 1)
    private String platformType;

    /** 业务编码 */
    @ApiDesc(value = "业务编码", required = 1)
    private String businessCode;

    /** 业务名称 */
    @ApiDesc(value = "业务名称", required = 1)
    private String businessName;

    /** 功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载 */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;

    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码", required = 1)
    private String businessUniqueCode;

    /** 业务回调地址 */
    @ApiDesc(value = "业务回调地址", required = 0)
    private String callbackUrl;

    /** 业务查询地址 */
    @ApiDesc(value = "业务查询地址", required = 0)
    private String queryUrl;

    /** 访问方式: GET, POST, PUT, DELETE */
    @ApiDesc(value = "访问方式", required = 0)
    private String accessMethod;

    /** 状态: ACTIVE-启用, INACTIVE-停用, PENDING-待激活 */
    @ApiDesc(value = "状态", required = 0)
    private String status;

    /** 备注信息 */
    @ApiDesc(value = "备注信息", required = 0)
    private String remark;

    // ==================== 字段映射列表 ====================

    /** 字段映射列表 */
    @ApiDesc(value = "字段映射列表", required = 0)
    private List<FileFieldResponseVo> fieldMappings;

    // ==================== 时间信息 ====================

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 0)
    private LocalDateTime createDate;

    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private LocalDateTime modifyDate;

    public static CompanyBusinessFunctionResponseVo build() {
        return new CompanyBusinessFunctionResponseVo();
    }

    public CompanyBusinessFunctionResponseVo clone(Object source) {
        if (source == null) {
            return this;
        }
        // 使用BeanUtils或其他工具进行属性复制
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, this);
        } catch (Exception e) {
            // 忽略复制错误
        }
        return this;
    }
}
