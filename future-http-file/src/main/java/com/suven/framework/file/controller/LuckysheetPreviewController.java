package com.suven.framework.file.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suven.framework.file.dto.luckysheet.LuckysheetPreviewRequestDto;
import com.suven.framework.file.dto.luckysheet.LuckysheetPreviewResponseDto;
import com.suven.framework.file.service.LuckysheetPreviewService;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.handler.OutputResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Luckysheet 在线预览 Controller
 * 
 * 功能：提供 Excel 文件在线预览 API，返回 Luckysheet 格式的 JSON 数据
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-12
 */
@Slf4j
@Controller
@RequestMapping("/api/file/luckysheet")
public class LuckysheetPreviewController {

    @Autowired
    private LuckysheetPreviewService luckysheetPreviewService;

    /**
     * 预览 Excel 文件
     * 
     * @param output 输出响应
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
    public void preview(OutputResponse output,
                        @RequestParam(required = false) Long fileId,
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
            LuckysheetPreviewRequestDto requestDto = LuckysheetPreviewRequestDto.builder()
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
            LuckysheetPreviewResponseDto response = luckysheetPreviewService.preview(requestDto);
            output.write(response);
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            output.write(LuckysheetPreviewResponseDto.builder()
                .success(false)
                .message("预览失败: " + e.getMessage())
                .build());
        }
    }

    /**
     * 预览 Excel 文件 (返回 Luckysheet JSON 格式)
     * 
     * @param output 输出响应
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param filePath 文件本地路径
     * @param sheetIndex Sheet索引
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件在线预览API - 返回Luckysheet格式")
    @RequestMapping(value = "/preview/json", method = {RequestMethod.GET, RequestMethod.POST})
    public void previewJson(OutputResponse output,
                            @RequestParam(required = false) Long fileId,
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
            LuckysheetPreviewRequestDto requestDto = LuckysheetPreviewRequestDto.builder()
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
            String json = luckysheetPreviewService.previewAsJson(requestDto);
            
            if (json != null) {
                output.write(json);
            } else {
                output.write("{\"success\":false,\"message\":\"预览失败\"}");
            }
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            output.write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * 预览指定 Sheet
     * 
     * @param output 输出响应
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param sheetIndex Sheet索引
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件指定Sheet预览API")
    @RequestMapping(value = "/preview/{sheetIndex}", method = {RequestMethod.GET, RequestMethod.POST})
    public void previewSheet(OutputResponse output,
                             @RequestParam(required = false) Long fileId,
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
            LuckysheetPreviewRequestDto requestDto = LuckysheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .maxRows(maxRows > 0 ? maxRows : null)
                .maxColumns(maxColumns > 0 ? maxColumns : null)
                .includeStyle(includeStyle)
                .includeFormula(includeFormula)
                .build();
            
            // 执行预览指定 Sheet
            LuckysheetPreviewResponseDto response = luckysheetPreviewService.previewSheet(requestDto, sheetIndex);
            output.write(response);
            
        } catch (Exception e) {
            log.error("Luckysheet预览指定Sheet失败", e);
            output.write(LuckysheetPreviewResponseDto.builder()
                .success(false)
                .message("预览失败: " + e.getMessage())
                .build());
        }
    }

    /**
     * 获取 Sheet 列表
     * 
     * @param output 输出响应
     * @param fileId 文件ID
     * @param fileUrl 文件访问URL
     * @param filePath 文件本地路径
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "获取Excel文件Sheet列表API")
    @RequestMapping(value = "/sheets", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSheetNames(OutputResponse output,
                              @RequestParam(required = false) Long fileId,
                              @RequestParam(required = false) String fileUrl,
                              @RequestParam(required = false) String filePath) {
        log.info("获取Sheet列表: fileId={}, fileUrl={}", fileId, fileUrl);
        
        try {
            // 构建请求参数
            LuckysheetPreviewRequestDto requestDto = LuckysheetPreviewRequestDto.builder()
                .fileId(fileId)
                .fileUrl(fileUrl)
                .filePath(filePath)
                .build();
            
            // 获取 Sheet 名称列表
            List<String> sheetNames = luckysheetPreviewService.getSheetNames(requestDto);
            
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("sheetNames", sheetNames);
            result.put("sheetCount", sheetNames.size());
            
            output.write(result);
            
        } catch (Exception e) {
            log.error("获取Sheet列表失败", e);
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("message", "获取Sheet列表失败: " + e.getMessage());
            output.write(result);
        }
    }

    /**
     * 检查文件是否为 Excel 文件
     * 
     * @param fileName 文件名
     * @return 是否为 Excel 文件
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "检查文件是否为Excel格式")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public void checkExcelFile(OutputResponse output,
                               @RequestParam String fileName) {
        log.info("检查文件格式: fileName={}", fileName);
        
        boolean isExcel = luckysheetPreviewService.isExcelFile(fileName);
        
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("isExcel", isExcel);
        result.put("fileName", fileName);
        
        output.write(result);
    }
}
