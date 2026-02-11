package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SaaS平台文件下载记录表
 * 
 * 功能：记录每一次文件生成和下载操作，包含业务功能、数据查询、文件生成等信息
 *       用于追踪批量大数据导出功能的使用情况
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
@TableName(value = "saas_file_download_record")
public class SaaSFileDownloadRecord extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 关联信息 ====================
    
    /** 业务功能配置ID，关联saas_company_business_function表 */
    @ApiDesc(value = "业务功能配置ID", required = 0)
    private long businessFunctionId;
    
    /** 字段映射ID，关联saas_file_field_mapping表 */
    @ApiDesc(value = "字段映射ID", required = 0)
    private long fieldMappingId;

    // ==================== 业务信息 ====================
    
    /** 业务唯一码，关联业务功能配置 */
    @ApiDesc(value = "业务唯一码", required = 1)
    private String businessUniqueCode;
    
    /** 业务类型 */
    @ApiDesc(value = "业务类型", required = 0)
    private String businessType;
    
    /** 业务描述 */
    @ApiDesc(value = "业务描述", required = 0)
    private String businessDescription;

    // ==================== 下载用户信息 ====================
    
    /** 下载人员ID */
    @ApiDesc(value = "下载人员ID", required = 0)
    private long downloadUserId;
    
    /** 下载人员名称 */
    @ApiDesc(value = "下载人员名称", required = 0)
    private String downloadUserName;
    
    /** 下载人员部门ID */
    @ApiDesc(value = "下载人员部门ID", required = 0)
    private long downloadDeptId;
    
    /** 下载人员部门名称 */
    @ApiDesc(value = "下载人员部门名称", required = 0)
    private String downloadDeptName;

    // ==================== 查询条件 ====================
    
    /** 查询条件JSON，存储业务方提供的查询参数 */
    @ApiDesc(value = "查询条件JSON", required = 0)
    private String queryCondition;
    
    /** 查询结果总数据量 */
    @ApiDesc(value = "查询总数据量", required = 0)
    private long totalQueryCount;
    
    /** 查询耗时（毫秒） */
    @ApiDesc(value = "查询耗时", required = 0)
    private long queryTimeMs;

    // ==================== 文件信息 ====================
    
    /** 生成文件名称 */
    @ApiDesc(value = "生成文件名称", required = 1)
    private String fileName;
    
    /** 生成文件类型: XLS, XLSX, CSV */
    @ApiDesc(value = "文件类型", required = 1)
    private String fileType;
    
    /** 生成文件大小（字节） */
    @ApiDesc(value = "文件大小", required = 0)
    private long fileSize;
    
    /** 文件存储路径 */
    @ApiDesc(value = "文件存储路径", required = 0)
    private String filePath;
    
    /** 文件MD5值 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    // ==================== 下载状态 ====================
    
    /** 生成状态: PENDING-待生成, PROCESSING-生成中, COMPLETED-已完成, FAILED-失败 */
    @ApiDesc(value = "生成状态", required = 1)
    private String generateStatus;
    
    /** 生成进度百分比 0-100 */
    @ApiDesc(value = "生成进度", required = 0)
    private int progressPercent;
    
    /** 生成消息 */
    @ApiDesc(value = "生成消息", required = 0)
    private String message;
    
    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    // ==================== 下载信息 ====================
    
    /** 下载次数 */
    @ApiDesc(value = "下载次数", required = 0)
    private int downloadCount;
    
    /** 最后下载时间 */
    @ApiDesc(value = "最后下载时间", required = 0)
    private LocalDateTime lastDownloadTime;
    
    /** 文件访问URL */
    @ApiDesc(value = "文件访问URL", required = 0)
    private String fileAccessUrl;
    
    /** 文件访问过期时间 */
    @ApiDesc(value = "文件访问过期时间", required = 0)
    private LocalDateTime accessExpireTime;

    // ==================== 统计信息 ====================
    
    /** 导出数据总条数 */
    @ApiDesc(value = "导出数据条数", required = 0)
    private long exportCount;
    
    /** 导出成功条数 */
    @ApiDesc(value = "导出成功条数", required = 0)
    private long successCount;
    
    /** 导出失败条数 */
    @ApiDesc(value = "导出失败条数", required = 0)
    private long failCount;

    // ==================== 时间信息 ====================
    
    /** 文件生成开始时间 */
    @ApiDesc(value = "生成开始时间", required = 0)
    private LocalDateTime generateStartTime;
    
    /** 文件生成结束时间 */
    @ApiDesc(value = "生成结束时间", required = 0)
    private LocalDateTime generateEndTime;
    
    /** 文件生成耗时（毫秒） */
    @ApiDesc(value = "生成耗时", required = 0)
    private long generateTimeMs;

    // ==================== 构建辅助方法 ====================

    public static SaaSFileDownloadRecord build() {
        return new SaaSFileDownloadRecord();
    }

    public SaaSFileDownloadRecord toBusinessFunctionId(long businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
        return this;
    }

    public SaaSFileDownloadRecord toFieldMappingId(long fieldMappingId) {
        this.fieldMappingId = fieldMappingId;
        return this;
    }

    public SaaSFileDownloadRecord toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public SaaSFileDownloadRecord toBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public SaaSFileDownloadRecord toBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
        return this;
    }

    public SaaSFileDownloadRecord toDownloadUserId(long downloadUserId) {
        this.downloadUserId = downloadUserId;
        return this;
    }

    public SaaSFileDownloadRecord toDownloadUserName(String downloadUserName) {
        this.downloadUserName = downloadUserName;
        return this;
    }

    public SaaSFileDownloadRecord toDownloadDeptId(long downloadDeptId) {
        this.downloadDeptId = downloadDeptId;
        return this;
    }

    public SaaSFileDownloadRecord toDownloadDeptName(String downloadDeptName) {
        this.downloadDeptName = downloadDeptName;
        return this;
    }

    public SaaSFileDownloadRecord toQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
        return this;
    }

    public SaaSFileDownloadRecord toTotalQueryCount(long totalQueryCount) {
        this.totalQueryCount = totalQueryCount;
        return this;
    }

    public SaaSFileDownloadRecord toQueryTimeMs(long queryTimeMs) {
        this.queryTimeMs = queryTimeMs;
        return this;
    }

    public SaaSFileDownloadRecord toFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public SaaSFileDownloadRecord toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileDownloadRecord toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileDownloadRecord toFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public SaaSFileDownloadRecord toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileDownloadRecord toGenerateStatus(String generateStatus) {
        this.generateStatus = generateStatus;
        return this;
    }

    public SaaSFileDownloadRecord toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileDownloadRecord toMessage(String message) {
        this.message = message;
        return this;
    }

    public SaaSFileDownloadRecord toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileDownloadRecord toDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public SaaSFileDownloadRecord toLastDownloadTime(LocalDateTime lastDownloadTime) {
        this.lastDownloadTime = lastDownloadTime;
        return this;
    }

    public SaaSFileDownloadRecord toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public SaaSFileDownloadRecord toAccessExpireTime(LocalDateTime accessExpireTime) {
        this.accessExpireTime = accessExpireTime;
        return this;
    }

    public SaaSFileDownloadRecord toExportCount(long exportCount) {
        this.exportCount = exportCount;
        return this;
    }

    public SaaSFileDownloadRecord toSuccessCount(long successCount) {
        this.successCount = successCount;
        return this;
    }

    public SaaSFileDownloadRecord toFailCount(long failCount) {
        this.failCount = failCount;
        return this;
    }

    public SaaSFileDownloadRecord toGenerateStartTime(LocalDateTime generateStartTime) {
        this.generateStartTime = generateStartTime;
        return this;
    }

    public SaaSFileDownloadRecord toGenerateEndTime(LocalDateTime generateEndTime) {
        this.generateEndTime = generateEndTime;
        return this;
    }

    public SaaSFileDownloadRecord toGenerateTimeMs(long generateTimeMs) {
        this.generateTimeMs = generateTimeMs;
        return this;
    }
}
