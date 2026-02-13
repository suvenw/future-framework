package com.suven.framework.upload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件解析结果DTO
 * 
 * 功能：封装文件解析的中间结果，包括解析状态、解析数据、错误信息等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileParseResultDto {

    /**
     * 是否解析成功
     */
    private boolean success;

    /**
     * 总行数
     */
    private int totalRows;

    /**
     * 成功解析行数
     */
    private int successRows;

    /**
     * 失败行数
     */
    private int failRows;

    /**
     * 跳过的行数
     */
    private int skipRows;

    /**
     * 解析消息
     */
    private String message;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 解析的数据行，每行是一个Map，key为字段英文名，value为字段值
     */
    @Builder.Default
    private List<Map<String, Object>> dataRows = new ArrayList<>();

    /**
     * 原始数据行（未转换的）
     */
    @Builder.Default
    private List<List<String>> rawDataRows = new ArrayList<>();

    /**
     * 解析耗时（毫秒）
     */
    private long parseTimeMs;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 添加一行解析数据
     * 
     * @param rowData 行数据
     */
    public void addDataRow(Map<String, Object> rowData) {
        if (this.dataRows == null) {
            this.dataRows = new ArrayList<>();
        }
        this.dataRows.add(rowData);
        this.successRows++;
        this.totalRows++;
    }

    /**
     * 添加一行原始数据
     * 
     * @param rawRow 原始行数据
     */
    public void addRawDataRow(List<String> rawRow) {
        if (this.rawDataRows == null) {
            this.rawDataRows = new ArrayList<>();
        }
        this.rawDataRows.add(rawRow);
    }

    /**
     * 标记解析失败
     * 
     * @param errorMessage 错误信息
     */
    public void markFailed(String errorMessage) {
        this.success = false;
        this.failRows++;
        this.totalRows++;
        this.errorMessage = errorMessage;
    }

    /**
     * 跳过一行
     * 
     * @param reason 跳过原因
     */
    public void skipRow(String reason) {
        this.skipRows++;
        this.totalRows++;
    }

    /**
     * 创建成功结果
     * 
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @return FileParseResultDto
     */
    public static FileParseResultDto success(String fileType, String fileName) {
        return FileParseResultDto.builder()
                .success(true)
                .fileType(fileType)
                .fileName(fileName)
                .message("解析成功")
                .build();
    }

    /**
     * 创建失败结果
     * 
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @param errorMessage 错误信息
     * @return FileParseResultDto
     */
    public static FileParseResultDto fail(String fileType, String fileName, String errorMessage) {
        return FileParseResultDto.builder()
                .success(false)
                .fileType(fileType)
                .fileName(fileName)
                .errorMessage(errorMessage)
                .message("解析失败")
                .build();
    }
}
