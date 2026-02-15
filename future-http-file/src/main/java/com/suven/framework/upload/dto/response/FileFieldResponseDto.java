package com.suven.framework.upload.dto.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * SaaS文件字段映射响应DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileFieldResponseDto extends BaseTenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 字段数据类型 */
    @ApiDesc(value = "字段数据类型", required = 0)
    private String fieldType;

    /** 是否为主键 */
    @ApiDesc(value = "是否为主键", required = 0)
    private int isPrimaryKey;

    /** 是否必填 */
    @ApiDesc(value = "是否必填", required = 0)
    private int isRequired;

    /** 默认值 */
    @ApiDesc(value = "默认值", required = 0)
    private String defaultValue;

    /** 字段描述 */
    @ApiDesc(value = "字段描述", required = 0)
    private String fieldDescription;

    /** 数据总条数 */
    @ApiDesc(value = "数据总条数", required = 0)
    private int totalCount;

    /** 非空数据条数 */
    @ApiDesc(value = "非空数据条数", required = 0)
    private int notNullCount;

    /** 空值数据条数 */
    @ApiDesc(value = "空值数据条数", required = 0)
    private int nullCount;

    /** 重复值数量 */
    @ApiDesc(value = "重复值数量", required = 0)
    private int duplicateCount;

    /** 字段示例值 */
    @ApiDesc(value = "字段示例值", required = 0)
    private String sampleValue;

    /** 状态 */
    @ApiDesc(value = "状态", required = 0)
    private String status;

    public static FileFieldResponseDto build() {
        return new FileFieldResponseDto();
    }


    public FileFieldResponseDto toOperationRecordId(long operationRecordId) {
        this.operationRecordId = operationRecordId;
        return this;
    }

    public FileFieldResponseDto toInterpretRecordId(long interpretRecordId) {
        this.interpretRecordId = interpretRecordId;
        return this;
    }

    public FileFieldResponseDto toFieldEnglishName(String fieldEnglishName) {
        this.fieldEnglishName = fieldEnglishName;
        return this;
    }

    public FileFieldResponseDto toFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
        return this;
    }

    public FileFieldResponseDto toSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public FileFieldResponseDto toFieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public FileFieldResponseDto toIsPrimaryKey(int isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
        return this;
    }

    public FileFieldResponseDto toIsRequired(int isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public FileFieldResponseDto toDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public FileFieldResponseDto toFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
        return this;
    }

    public FileFieldResponseDto toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FileFieldResponseDto toNotNullCount(int notNullCount) {
        this.notNullCount = notNullCount;
        return this;
    }

    public FileFieldResponseDto toNullCount(int nullCount) {
        this.nullCount = nullCount;
        return this;
    }

    public FileFieldResponseDto toDuplicateCount(int duplicateCount) {
        this.duplicateCount = duplicateCount;
        return this;
    }

    public FileFieldResponseDto toSampleValue(String sampleValue) {
        this.sampleValue = sampleValue;
        return this;
    }

    public FileFieldResponseDto toStatus(String status) {
        this.status = status;
        return this;
    }
}
