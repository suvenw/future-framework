package com.suven.framework.file.dto.luckysheet;

import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Luckysheet 预览请求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckySheetPreviewRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @ApiDesc(value = "文件ID", required = 0)
    private Long fileId;

    /**
     * 文件访问URL
     */
    @ApiDesc(value = "文件访问URL", required = 0)
    private String fileUrl;

    /**
     * 文件本地路径
     */
    @ApiDesc(value = "文件本地路径", required = 0)
    private String filePath;

    /**
     * Sheet 索引 (从0开始), -1表示所有Sheet
     */
    @ApiDesc(value = "Sheet索引, -1表示所有Sheet", required = 0)
    private Integer sheetIndex;

    /**
     * 最大行数限制 (0表示不限制)
     */
    @ApiDesc(value = "最大行数限制", required = 0)
    private Integer maxRows;

    /**
     * 最大列数限制 (0表示不限制)
     */
    @ApiDesc(value = "最大列数限制", required = 0)
    private Integer maxColumns;

    /**
     * 是否包含样式
     */
    @ApiDesc(value = "是否包含样式", required = 0)
    private Boolean includeStyle;

    /**
     * 是否包含公式
     */
    @ApiDesc(value = "是否包含公式", required = 0)
    private Boolean includeFormula;

    /**
     * 租户ID
     */
    @ApiDesc(value = "租户ID", required = 0)
    private Long tenantId;

    /**
     * 文件名（用于文件格式检查）
     */
    @ApiDesc(value = "文件名", required = 0)
    private String fileName;
}
