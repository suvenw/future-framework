package com.suven.framework.file.service;

import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewRequestDto;
import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewResponseDto;

/**
 * Luckysheet 预览服务接口
 * 
 * 功能：提供 Excel 文件转换为 Luckysheet 格式的在线预览能力
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-12
 */
public interface LuckySheetPreviewService {

    /**
     * 预览 Excel 文件
     * 
     * @param requestDto 预览请求参数
     * @return 预览响应结果
     */
    LuckySheetPreviewResponseDto preview(LuckySheetPreviewRequestDto requestDto);

    /**
     * 预览 Excel 文件并返回 Luckysheet JSON 格式
     * 
     * @param requestDto 预览请求参数
     * @return Luckysheet JSON 字符串
     */
    String previewAsJson(LuckySheetPreviewRequestDto requestDto);

    /**
     * 预览指定 Sheet
     * 
     * @param requestDto 预览请求参数
     * @param sheetIndex Sheet 索引 (从0开始)
     * @return 预览响应结果
     */
    LuckySheetPreviewResponseDto previewSheet(LuckySheetPreviewRequestDto requestDto, int sheetIndex);

    /**
     * 检查文件是否为 Excel 文件
     * 
     * @param fileName 文件名
     * @return 是否为 Excel 文件
     */
    boolean isExcelFile(String fileName);

    /**
     * 获取文件支持的 Sheet 列表
     * 
     * @param requestDto 预览请求参数
     * @return Sheet 名称列表
     */
    java.util.List<String> getSheetNames(LuckySheetPreviewRequestDto requestDto);
}
