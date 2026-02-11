package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SaaS平台文件字段映射记录表
 * 
 * 功能：记录文件解释的字段英文名、中文名、排编号，关联业务功能配置
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "saas_file_field_mapping")
public class SaaSFileFieldMapping extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 关联信息 ====================
    
    /** 业务功能配置ID，关联saas_company_business_function表 */
    @ApiDesc(value = "业务功能配置ID", required = 0)
    private long businessFunctionId;

    // ==================== 字段信息 ====================
    
    /** 字段英文名称 */
    @ApiDesc(value = "字段英文名称", required = 1)
    private String fieldEnglishName;
    
    /** 字段中文名称 */
    @ApiDesc(value = "字段中文名称", required = 1)
    private String fieldChineseName;
    
    /** 排编号，用于排序和定位 */
    @ApiDesc(value = "排编号", required = 1)
    private int sortOrder;
    
    /** 字段数据类型: STRING-字符串, NUMBER-数字, DATE-日期, BOOLEAN-布尔值 */
    @ApiDesc(value = "字段数据类型", required = 0)
    private String fieldType;
    
    /** 字段长度或精度 */
    @ApiDesc(value = "字段长度或精度", required = 0)
    private int fieldLength;
    
    /** 是否为主键: 0-否, 1-是 */
    @ApiDesc(value = "是否为主键", required = 0)
    private int isPrimaryKey;
    
    /** 是否必填: 0-否, 1-是 */
    @ApiDesc(value = "是否必填", required = 0)
    private int isRequired;
    
    /** 默认值 */
    @ApiDesc(value = "默认值", required = 0)
    private String defaultValue;
    
    /** 字段描述 */
    @ApiDesc(value = "字段描述", required = 0)
    private String fieldDescription;

    // ==================== 验证规则 ====================
    
    /** 字段格式验证规则 */
    @ApiDesc(value = "字段格式验证规则", required = 0)
    private String validateRule;
    
    /** 字段转换规则(正则表达式或转换代码) */
    @ApiDesc(value = "字段转换规则", required = 0)
    private String transformRule;
    
    /** 字段示例值 */
    @ApiDesc(value = "字段示例值", required = 0)
    private String sampleValue;

    // ==================== 统计信息 ====================
    
    /** 字段对应的数据总条数 */
    @ApiDesc(value = "数据总条数", required = 0)
    private int totalCount;
    
    /** 字段非空数据条数 */
    @ApiDesc(value = "非空数据条数", required = 0)
    private int notNullCount;
    
    /** 字段空值数据条数 */
    @ApiDesc(value = "空值数据条数", required = 0)
    private int nullCount;
    
    /** 字段重复值数量 */
    @ApiDesc(value = "重复值数量", required = 0)
    private int duplicateCount;

    // ==================== 状态信息 ====================
    
    /** 状态: ACTIVE-激活, INACTIVE-禁用, DELETED-已删除 */
    @ApiDesc(value = "状态", required = 0)
    private String status;
    
    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    // ==================== 构建辅助方法 ====================

    public static SaaSFileFieldMapping build() {
        return new SaaSFileFieldMapping();
    }

    public SaaSFileFieldMapping toBusinessFunctionId(long businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
        return this;
    }

    public SaaSFileFieldMapping toFieldEnglishName(String fieldEnglishName) {
        this.fieldEnglishName = fieldEnglishName;
        return this;
    }

    public SaaSFileFieldMapping toFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
        return this;
    }

    public SaaSFileFieldMapping toSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public SaaSFileFieldMapping toFieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public SaaSFileFieldMapping toFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
        return this;
    }

    public SaaSFileFieldMapping toIsPrimaryKey(int isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
        return this;
    }

    public SaaSFileFieldMapping toIsRequired(int isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public SaaSFileFieldMapping toDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public SaaSFileFieldMapping toFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
        return this;
    }

    public SaaSFileFieldMapping toValidateRule(String validateRule) {
        this.validateRule = validateRule;
        return this;
    }

    public SaaSFileFieldMapping toTransformRule(String transformRule) {
        this.transformRule = transformRule;
        return this;
    }

    public SaaSFileFieldMapping toSampleValue(String sampleValue) {
        this.sampleValue = sampleValue;
        return this;
    }

    public SaaSFileFieldMapping toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public SaaSFileFieldMapping toNotNullCount(int notNullCount) {
        this.notNullCount = notNullCount;
        return this;
    }

    public SaaSFileFieldMapping toNullCount(int nullCount) {
        this.nullCount = nullCount;
        return this;
    }

    public SaaSFileFieldMapping toDuplicateCount(int duplicateCount) {
        this.duplicateCount = duplicateCount;
        return this;
    }

    public SaaSFileFieldMapping toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSFileFieldMapping toRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
