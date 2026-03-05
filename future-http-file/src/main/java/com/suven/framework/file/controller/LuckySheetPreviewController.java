package com.suven.framework.file.controller;

import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewRequestVo;
import com.suven.framework.file.dto.luckysheet.LuckySheetPreviewResponseVo;
import com.suven.framework.file.service.LuckySheetPreviewService;
import com.suven.framework.http.api.ApiDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

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
    private LuckySheetPreviewService luckySheetPreviewService;

    /**
     * 预览 Excel 文件
     * 
     * @param requestDto 预览请求参数对象
     * @return 预览响应结果
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件在线预览API")
    @RequestMapping(value = "/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public LuckySheetPreviewResponseVo preview(@ModelAttribute LuckySheetPreviewRequestVo requestDto) {
        log.info("Luckysheet预览请求: fileId={}, fileUrl={}, sheetIndex={}", 
                requestDto.getFileId(), requestDto.getFileUrl(), requestDto.getSheetIndex());
        
        try {
            // 处理参数默认值
            processDefaultParams(requestDto);
            
            // 执行预览
            return luckySheetPreviewService.preview(requestDto);
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            return buildErrorResponse("预览失败: " + e.getMessage());
        }
    }

    /**
     * 预览 Excel 文件 (返回 Luckysheet JSON 格式字符串)
     * 
     * @param requestDto 预览请求参数对象
     * @return 预览响应结果（data字段包含JSON字符串）
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件在线预览API - 返回Luckysheet格式")
    @RequestMapping(value = "/preview/json", method = {RequestMethod.GET, RequestMethod.POST})
    public LuckySheetPreviewResponseVo previewJson(@ModelAttribute LuckySheetPreviewRequestVo requestDto) {
        log.info("Luckysheet预览请求(JSON格式): fileId={}, fileUrl={}", 
                requestDto.getFileId(), requestDto.getFileUrl());
        
        try {
            // 处理参数默认值
            processDefaultParams(requestDto);
            
            // 执行预览并返回 JSON 格式
            String json = luckySheetPreviewService.previewAsJson(requestDto);
            
            if (json != null) {
                LuckySheetPreviewResponseVo response = new LuckySheetPreviewResponseVo();
                response.setSuccess(true);
                response.setMessage("预览成功");
                response.setJsonData(json);
                return response;
            } else {
                return buildErrorResponse("预览失败");
            }
            
        } catch (Exception e) {
            log.error("Luckysheet预览失败", e);
            return buildErrorResponse("预览失败: " + e.getMessage());
        }
    }

    /**
     * 预览指定 Sheet
     * 
     * @param sheetIndex Sheet索引
     * @param requestDto 预览请求参数对象
     * @return 预览响应结果
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "Excel文件指定Sheet预览API")
    @RequestMapping(value = "/preview/{sheetIndex}", method = {RequestMethod.GET, RequestMethod.POST})
    public LuckySheetPreviewResponseVo previewSheet(@PathVariable Integer sheetIndex,
                                                    @ModelAttribute LuckySheetPreviewRequestVo requestDto) {
        log.info("Luckysheet预览指定Sheet: fileId={}, sheetIndex={}", 
                requestDto.getFileId(), sheetIndex);
        
        try {
            // 处理参数默认值
            processDefaultParams(requestDto);
            
            // 执行预览指定 Sheet
            return luckySheetPreviewService.previewSheet(requestDto, sheetIndex);
            
        } catch (Exception e) {
            log.error("Luckysheet预览指定Sheet失败", e);
            return buildErrorResponse("预览失败: " + e.getMessage());
        }
    }

    /**
     * 获取 Sheet 列表
     * 
     * @param requestDto 预览请求参数对象
     * @return 包含Sheet列表的响应结果
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "获取Excel文件Sheet列表API")
    @RequestMapping(value = "/sheets", method = {RequestMethod.GET, RequestMethod.POST})
    public LuckySheetPreviewResponseVo getSheetNames(@ModelAttribute LuckySheetPreviewRequestVo requestDto) {
        log.info("获取Sheet列表: fileId={}, fileUrl={}", 
                requestDto.getFileId(), requestDto.getFileUrl());
        
        try {
            // 获取 Sheet 名称列表
            List<String> sheetNames = luckySheetPreviewService.getSheetNames(requestDto);
            
            LuckySheetPreviewResponseVo response = new LuckySheetPreviewResponseVo();
            response.setSuccess(true);
            response.setMessage("获取成功");
            response.setSheetNames(sheetNames);
            response.setSheetCount(sheetNames.size());
            
            return response;
            
        } catch (Exception e) {
            log.error("获取Sheet列表失败", e);
            return buildErrorResponse("获取Sheet列表失败: " + e.getMessage());
        }
    }

    /**
     * 检查文件是否为 Excel 文件
     * 
     * @param requestDto 包含fileName的请求参数对象
     * @return 检查结果响应
     */
    @ApiDoc(module = "Luckysheet在线预览", group = "file-group", groupDesc = "检查文件是否为Excel格式")
    @RequestMapping(value = "/check", method = {RequestMethod.GET, RequestMethod.POST})
    public LuckySheetPreviewResponseVo checkExcelFile(@ModelAttribute LuckySheetPreviewRequestVo requestDto) {
        String fileName = requestDto.getFileName();
        log.info("检查文件格式: fileName={}", fileName);
        
        boolean isExcel = luckySheetPreviewService.isExcelFile(fileName);
        
        LuckySheetPreviewResponseVo response = new LuckySheetPreviewResponseVo();
        response.setSuccess(true);
        response.setMessage("检查完成");
        response.setIsExcel(isExcel);
        response.setFileName(fileName);
        
        return response;
    }

    /**
     * 处理请求参数默认值
     */
    private void processDefaultParams(LuckySheetPreviewRequestVo requestDto) {
        // 处理 sheetIndex: -1 表示所有 Sheet
        if (requestDto.getSheetIndex() != null && requestDto.getSheetIndex() < 0) {
            requestDto.setSheetIndex(null);
        }
        // 处理 maxRows: 0 表示不限制
        if (requestDto.getMaxRows() != null && requestDto.getMaxRows() <= 0) {
            requestDto.setMaxRows(null);
        }
        // 处理 maxColumns: 0 表示不限制
        if (requestDto.getMaxColumns() != null && requestDto.getMaxColumns() <= 0) {
            requestDto.setMaxColumns(null);
        }
        // 处理 includeStyle 默认值
        if (requestDto.getIncludeStyle() == null) {
            requestDto.setIncludeStyle(true);
        }
        // 处理 includeFormula 默认值
        if (requestDto.getIncludeFormula() == null) {
            requestDto.setIncludeFormula(true);
        }
    }

    /**
     * 构建错误响应
     */
    private LuckySheetPreviewResponseVo buildErrorResponse(String message) {
        LuckySheetPreviewResponseVo response = new LuckySheetPreviewResponseVo();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}
