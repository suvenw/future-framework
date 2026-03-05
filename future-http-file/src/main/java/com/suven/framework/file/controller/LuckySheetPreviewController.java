package com.suven.framework.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewRequestDto;
import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewResponseDto;
import com.suven.framework.file.service.LuckySheetPreviewService;
import com.suven.framework.http.api.ApiDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Luckysheet 在线预览 Controller
 * 
 * 功能：提供 Excel 文件在线预览 API，返回 Luckysheet 格式的 JSON 数据
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-12
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/file/luckysheet")
public class LuckySheetPreviewController {

    @Autowired
    private LuckySheetPreviewService LuckySheetPreviewService;

    /**
     * 预览 Excel 文件
     * 
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param filePath 文件本地路径
     * @param sheetIndex Sheet索引 (-1表示所有Sheet)
     * @param maxRows 最大行数限制
     * @param maxColumns 最大列数限制
     * @param includeStyle 是否包含样式
     * @param includeFormula 是否包含公式
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件在线预览API")
    @RequestMapping(value = "/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public Object preview(@RequestParam(required = false) Long fileId,
                        @RequestParam(required = false) String fileUrl,
                        @RequestParam(required = false) String filePath,
                        @RequestParam(required = false, defaultValue = "-1") Integer sheetIndex,
                        @RequestParam(required = false, defaultValue = "0") Integer maxRows,
                        @RequestParam(required = false, defaultValue = "0") Integer maxColumns,
                        @RequestParam(required = false, defaultValue = "true") Boolean includeStyle,
                        @RequestParam(required = false, defaultValue = "true") Boolean includeFormula) {
        log.info("Luckysheet预览请求: fileId={}, fileUrl={}, sheetIndex={}", fileId, fileUrl, sheetIndex);
        
        try {
            // 构建请求参数
            LuckySheetPreviewRequestDto requestDto = LuckySheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .sheetIndex(sheetIndex >= 0 ? sheetIndex : null)
                .maxRows(maxRows > 0 ? maxRows : null)
                .maxColumns(maxColumns > 0 ? maxColumns : null)
                .includeStyle(includeStyle)
                .includeFormula(includeFormula)
                .build();
            
            // 执行预览
            LuckySheetPreviewResponseDto response = LuckySheetPreviewService.preview(requestDto);
            return response;
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            return LuckySheetPreviewResponseDto.builder().success(false)
                .message("预览失败: " + e.getMessage())
                .build();
        }
    }

    /**
     * 预览 Excel 文件 (返回 Luckysheet JSON 格式)
     * 
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param filePath 文件本地路径
     * @param sheetIndex Sheet索引
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件在线预览API - 返回Luckysheet格式")
    @RequestMapping(value = "/preview/json", method = {RequestMethod.GET, RequestMethod.POST})
    public Object previewJson(@RequestParam(required = false) Long fileId,
                            @RequestParam(required = false) String fileUrl,
                            @RequestParam(required = false) String filePath,
                            @RequestParam(required = false, defaultValue = "0") Integer sheetIndex,
                            @RequestParam(required = false, defaultValue = "0") Integer maxRows,
                            @RequestParam(required = false, defaultValue = "0") Integer maxColumns,
                            @RequestParam(required = false, defaultValue = "true") Boolean includeStyle,
                            @RequestParam(required = false, defaultValue = "true") Boolean includeFormula) {
        log.info("Luckysheet预览请求(JSON格式): fileId={}, fileUrl={}", fileId, fileUrl);
        
        try {
            // 构建请求参数
            LuckySheetPreviewRequestDto requestDto = LuckySheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .sheetIndex(sheetIndex > 0 ? sheetIndex : null)
                .maxRows(maxRows > 0 ? maxRows : null)
                .maxColumns(maxColumns > 0 ? maxColumns : null)
                .includeStyle(includeStyle)
                .includeFormula(includeFormula)
                .build();
            
            // 执行预览并返回 JSON 格式
            String json = LuckySheetPreviewService.previewAsJson(requestDto);
            
            if (json != null) {
                return json;
            } else {
                return "{\"success\":false,\"message\":\"预览失败\"}";
            }
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            return LuckySheetPreviewResponseDto.builder().success(false)
                    .message("预览失败: " + e.getMessage())
                    .build();
        }
    }

    /**
     * 预览指定 Sheet
     * 
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param sheetIndex Sheet索引
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件指定Sheet预览API")
    @RequestMapping(value = "/preview/{sheetIndex}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object previewSheet(@RequestParam(required = false) Long fileId,
                             @RequestParam(required = false) String fileUrl,
                             @RequestParam(required = false) String filePath,
                             @PathVariable Integer sheetIndex,
                             @RequestParam(required = false, defaultValue = "0") Integer maxRows,
                             @RequestParam(required = false, defaultValue = "0") Integer maxColumns,
                             @RequestParam(required = false, defaultValue = "true") Boolean includeStyle,
                             @RequestParam(required = false, defaultValue = "true") Boolean includeFormula) {
        log.info("Luckysheet预览指定Sheet: fileId={}, sheetIndex={}", fileId, sheetIndex);
        
        try {
            // 构建请求参数
            LuckySheetPreviewRequestDto requestDto = LuckySheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .maxRows(maxRows > 0 ? maxRows : null)
                .maxColumns(maxColumns > 0 ? maxColumns : null)
                .includeStyle(includeStyle)
                .includeFormula(includeFormula)
                .build();
            
            // 执行预览指定 Sheet
            LuckySheetPreviewResponseDto response = LuckySheetPreviewService.previewSheet(requestDto, sheetIndex);
            return response;
            
        } catch (Exception e) {
            log.error("Luckysheet预览指定Sheet失败", e);
            return LuckySheetPreviewResponseDto.builder().success(false)
                .message("预览失败: " + e.getMessage())
                .build();
        }
    }

    /**
     * 获取 Sheet 列表
     * 
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param filePath 文件本地路径
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "获取Excel文件Sheet列表API")
    @RequestMapping(value = "/sheets", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getSheetNames(@RequestParam(required = false) Long fileId,
                              @RequestParam(required = false) String fileUrl,
                              @RequestParam(required = false) String filePath) {
        log.info("获取Sheet列表: fileId={}, fileUrl={}", fileId, fileUrl);
        
        try {
            // 构建请求参数
            LuckySheetPreviewRequestDto requestDto = LuckySheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .build();
            
            // 获取 Sheet 名称列表
            List<String> sheetNames = LuckySheetPreviewService.getSheetNames(requestDto);
            
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("sheetNames", sheetNames);
            result.put("sheetCount", sheetNames.size());
            
            return result;
            
        } catch (Exception e) {
            log.error("获取Sheet列表失败", e);
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("message", "获取Sheet列表失败: " + e.getMessage());
            return result;
        }
    }

    /**
     * 检查文件是否为 Excel 文件
     * 
     * @param fileName 文件名
     * @return 是否为 Excel 文件
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "检查文件是否为Excel格式")
    @GetMapping(value = "/check")
    public Object checkExcelFile(@RequestParam String fileName) {
        log.info("检查文件格式: fileName={}", fileName);
        
        boolean isExcel = LuckySheetPreviewService.isExcelFile(fileName);
        
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("isExcel", isExcel);
        result.put("fileName", fileName);
        
        return result;
    }
}
