package com.suven.framework.upload.dto.request;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * SaaS文件字段映射请求DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileFieldRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 字段ID (查询时使用) */
    @ApiDesc(value = "字段ID", required = 0)
    private long id;

    /** 操作记录ID */
    @ApiDesc(value = "操作记录ID", required = 1)
    private long operationRecordId;

    /** 解释记录ID */
    @ApiDesc(value = "解释记录ID", required = 0)
    private long interpretRecordId;

    /** 字段英文名称 */
    @ApiDesc(value = "字段英文名称", required = 1)
    private String fieldEnglishName;

    /** 字段中文名称 */
    @ApiDesc(value = "字段中文名称", required = 1)
    private String fieldChineseName;

    /** 排编号 */
    @ApiDesc(value = "排编号", required = 1)
    private int sortOrder;

    /** 字段数据类型: STRING, NUMBER, DATE, BOOLEAN */
    @ApiDesc(value = "字段数据类型", required = 0)
    private String fieldType;

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

    /** 字段格式验证规则 */
    @ApiDesc(value = "字段格式验证规则", required = 0)
    private String validateRule;

    /** 字段转换规则 */
    @ApiDesc(value = "字段转换规则", required = 0)
    private String transformRule;

    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    public static FileFieldRequestDto build() {
        return new FileFieldRequestDto();
    }

    public FileFieldRequestDto toId(long id) {
        this.id = id;
        return this;
    }

    public FileFieldRequestDto toOperationRecordId(long operationRecordId) {
        this.operationRecordId = operationRecordId;
        return this;
    }

    public FileFieldRequestDto toInterpretRecordId(long interpretRecordId) {
        this.interpretRecordId = interpretRecordId;
        return this;
    }

    public FileFieldRequestDto toFieldEnglishName(String fieldEnglishName) {
        this.fieldEnglishName = fieldEnglishName;
        return this;
    }

    public FileFieldRequestDto toFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
        return this;
    }

    public FileFieldRequestDto toSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public FileFieldRequestDto toFieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public FileFieldRequestDto toIsPrimaryKey(int isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
        return this;
    }

    public FileFieldRequestDto toIsRequired(int isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public FileFieldRequestDto toDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public FileFieldRequestDto toFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
        return this;
    }

    public FileFieldRequestDto toValidateRule(String validateRule) {
        this.validateRule = validateRule;
        return this;
    }

    public FileFieldRequestDto toTransformRule(String transformRule) {
        this.transformRule = transformRule;
        return this;
    }

    public FileFieldRequestDto toRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
