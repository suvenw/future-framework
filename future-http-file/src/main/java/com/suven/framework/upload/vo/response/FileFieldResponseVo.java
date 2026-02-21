package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件字段映射响应VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
public class FileFieldResponseVo {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiDesc(value = "ID", required = 0)
    private Long id;

    /** 业务功能配置ID */
    @ApiDesc(value = "业务功能配置ID", required = 0)
    private Long businessFunctionId;

    /** 字段英文名称 */
    @ApiDesc(value = "字段英文名称", required = 1)
    private String fieldEnglishName;

    /** 字段中文名称 */
    @ApiDesc(value = "字段中文名称", required = 1)
    private String fieldChineseName;

    /** 排编号，用于排序和定位 */
    @ApiDesc(value = "排编号", required = 1)
    private Integer sortOrder;

    /** 字段数据类型: STRING-字符串, NUMBER-数字, DATE-日期, BOOLEAN-布尔值 */
    @ApiDesc(value = "字段数据类型", required = 0)
    private String fieldType;

    /** 字段长度或精度 */
    @ApiDesc(value = "字段长度或精度", required = 0)
    private Integer fieldLength;

    /** 是否为主键: 0-否, 1-是 */
    @ApiDesc(value = "是否为主键", required = 0)
    private Integer isPrimaryKey;

    /** 是否必填: 0-否, 1-是 */
    @ApiDesc(value = "是否必填", required = 0)
    private Integer isRequired;

    /** 默认值 */
    @ApiDesc(value = "默认值", required = 0)
    private String defaultValue;

    /** 字段描述 */
    @ApiDesc(value = "字段描述", required = 0)
    private String fieldDescription;

    /** 字段格式验证规则 */
    @ApiDesc(value = "字段格式验证规则", required = 0)
    private String validateRule;

    /** 字段转换规则(正则表达式或转换代码) */
    @ApiDesc(value = "字段转换规则", required = 0)
    private String transformRule;

    /** 字段示例值 */
    @ApiDesc(value = "字段示例值", required = 0)
    private String sampleValue;

    /** 状态: ACTIVE-激活, INACTIVE-禁用 */
    @ApiDesc(value = "状态", required = 0)
    private String status;

    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 0)
    private LocalDateTime createDate;

    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private LocalDateTime modifyDate;

    public static FileFieldResponseVo build() {
        return new FileFieldResponseVo();
    }

    public FileFieldResponseVo clone(Object source) {
        if (source == null) {
            return this;
        }
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, this);
        } catch (Exception e) {
            // 忽略复制错误
        }
        return this;
    }
}
