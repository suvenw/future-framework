package com.suven.framework.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.upload.dto.response.SaaSFileParseResultDto;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.entity.SaaSFileInterpretRecord;
import com.suven.framework.upload.entity.SaaSFileUpload;
import com.suven.framework.upload.repository.SaaSFileFieldMappingRepository;
import com.suven.framework.upload.repository.SaaSFileInterpretRecordRepository;
import com.suven.framework.upload.repository.SaaSFileUploadRepository;
import com.suven.framework.upload.service.SaaSFileParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * SaaS文件解析服务实现
 * 
 * 功能：集成XLS和CSV解析器，提供统一的文件解析接口，支持字段映射、数据转换、解释记录保存等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class SaaSFileParseServiceImpl implements SaaSFileParseService {

    @Autowired
    private SaaSFileParseService xlsFileParser;

    @Autowired
    private SaaSFileParseService csvFileParser;

    @Autowired
    private SaaSFileUploadRepository fileUploadRepository;

    @Autowired
    private SaaSFileInterpretRecordRepository interpretRecordRepository;

    @Autowired
    private SaaSFileFieldMappingRepository fieldMappingRepository;

    /**
     * 解析文件
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return SaaSFileParseResultDto 解析结果
     */
    @Override
    public SaaSFileParseResultDto parse(InputStream inputStream, String fileName, String fileType) {
        log.info("开始解析文件: {}, 类型: {}", fileName, fileType);
        
        SaaSFileParseService parser = getParser(fileName, fileType);
        if (parser == null) {
            log.error("不支持的文件类型: {}", fileType);
            return SaaSFileParseResultDto.fail(fileType, fileName, "不支持的文件类型: " + fileType);
        }

        return parser.parse(inputStream, fileName, fileType);
    }

    /**
     * 解析文件并映射字段
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param fieldMappings 字段映射列表
     * @return SaaSFileParseResultDto 解析结果
     */
    @Override
    public SaaSFileParseResultDto parseWithMapping(
            InputStream inputStream, 
            String fileName, 
            String fileType, 
            List<SaaSFileFieldMapping> fieldMappings) {
        log.info("开始解析文件(带字段映射): {}, 类型: {}, 字段数: {}", 
                fileName, fileType, ObjectTrue.isEmpty(fieldMappings) ? 0 : fieldMappings.size());
        
        SaaSFileParseService parser = getParser(fileName, fileType);
        if (parser == null) {
            log.error("不支持的文件类型: {}", fileType);
            return SaaSFileParseResultDto.fail(fileType, fileName, "不支持的文件类型: " + fileType);
        }

        return parser.parseWithMapping(inputStream, fileName, fileType, fieldMappings);
    }

    /**
     * 解析文件并保存解释记录
     * 
     * @param fileUploadId 文件上传记录ID
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param businessUniqueCode 业务唯一码
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return SaaSFileInterpretRecord 解释记录
     */
    public SaaSFileInterpretRecord parseAndSaveInterpretRecord(
            long fileUploadId,
            InputStream inputStream, 
            String fileName, 
            String fileType,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl) {
        log.info("解析文件并保存解释记录: fileUploadId={}, fileName={}, businessUniqueCode={}", 
                fileUploadId, fileName, businessUniqueCode);

        try {
            // 获取字段映射
            List<SaaSFileFieldMapping> fieldMappings = getFieldMappingsByUploadId(fileUploadId);
            
            // 解析文件
            SaaSFileParseResultDto parseResult;
            if (ObjectTrue.isNotEmpty(fieldMappings)) {
                parseResult = parseWithMapping(inputStream, fileName, fileType, fieldMappings);
            } else {
                parseResult = parse(inputStream, fileName, fileType);
            }

            if (!parseResult.isSuccess()) {
                log.error("文件解析失败: {}", parseResult.getErrorMessage());
                return null;
            }

            // 生成解释标识
            String interpretKey = generateInterpretKey(fileUploadId);

            // 保存解释记录
            SaaSFileInterpretRecord interpretRecord = saveInterpretRecord(
                    fileUploadId,
                    interpretKey,
                    businessUniqueCode,
                    parseResult,
                    needCallback,
                    callbackUrl
            );

            // 更新文件上传记录状态
            updateFileUploadStatus(fileUploadId, interpretKey, parseResult);

            log.info("解析记录保存成功: interpretId={}", interpretRecord.getId());
            return interpretRecord;

        } catch (Exception e) {
            log.error("解析并保存解释记录失败: fileUploadId={}", fileUploadId, e);
            return null;
        }
    }

    /**
     * 批量保存解释记录明细
     * 
     * @param fileUploadId 文件上传记录ID
     * @param parseResult 解析结果
     * @param interpretKey 解释标识
     * @param businessUniqueCode 业务唯一码
     * @return 保存的记录数
     */
    public int batchSaveInterpretDetails(
            long fileUploadId,
            SaaSFileParseResultDto parseResult,
            String interpretKey,
            String businessUniqueCode) {
        log.info("批量保存解释记录明细: fileUploadId={}, interpretKey={}, dataRows={}", 
                fileUploadId, interpretKey, parseResult.getDataRows().size());

        int savedCount = 0;
        List<Map<String, Object>> dataRows = parseResult.getDataRows();
        
        for (int i = 0; i < dataRows.size(); i++) {
            try {
                Map<String, Object> rowData = dataRows.get(i);
                String interpretInfo = JSON.toJSONString(rowData);

                SaaSFileInterpretRecord detailRecord = new SaaSFileInterpretRecord();
                detailRecord.setFileUploadId(fileUploadId);
                detailRecord.setInterpretKey(interpretKey);
                detailRecord.setBusinessUniqueCode(businessUniqueCode);
                detailRecord.setInterpretInfo(interpretInfo);
                detailRecord.setInterpretStatus("COMPLETED");
                detailRecord.setSuccessCount(1);
                detailRecord.setNeedCallback(0);
                detailRecord.setCreateDate(LocalDateTime.now());
                detailRecord.setModifyDate(LocalDateTime.now());

                interpretRecordRepository.saveId(detailRecord);
                savedCount++;

            } catch (Exception e) {
                log.warn("保存解释记录明细失败: rowIndex={}", i, e);
            }
        }

        return savedCount;
    }

    @Override
    public boolean isSupported(String fileName, String fileType) {
        return getParser(fileName, fileType) != null;
    }

    @Override
    public String getFileExtension(String fileName) {
        if (ObjectTrue.isEmpty(fileName)) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    @Override
    public Map<String, Object> convertDataRow(
            List<String> rawData, 
            List<SaaSFileFieldMapping> fieldMappings) {
        // 委托给CSV解析器处理
        return csvFileParser.convertDataRow(rawData, fieldMappings);
    }

    @Override
    public Object convertValue(String value, String fieldType) {
        // 委托给CSV解析器处理
        return csvFileParser.convertValue(value, fieldType);
    }

    @Override
    public boolean validateDataRow(
            Map<String, Object> dataRow, 
            List<SaaSFileFieldMapping> fieldMappings) {
        // 委托给CSV解析器处理
        return csvFileParser.validateDataRow(dataRow, fieldMappings);
    }

    // ==================== 私有方法 ====================

    /**
     * 获取合适的解析器
     * 
     * @param fileName 文件名
     * @param fileType 文件类型
     * @return 解析器实例
     */
    private SaaSFileParseService getParser(String fileName, String fileType) {
        if (xlsFileParser.isSupported(fileName, fileType)) {
            return xlsFileParser;
        }
        if (csvFileParser.isSupported(fileName, fileType)) {
            return csvFileParser;
        }
        return null;
    }

    /**
     * 根据文件上传ID获取字段映射
     * 
     * @param fileUploadId 文件上传ID
     * @return 字段映射列表
     */
    private List<SaaSFileFieldMapping> getFieldMappingsByUploadId(long fileUploadId) {
        try {
            SaaSFileUpload fileUpload = fileUploadRepository.getById(fileUploadId);
            if (fileUpload == null || fileUpload.getFieldMappingId() <= 0) {
                return new ArrayList<>();
            }
            return fieldMappingRepository.getByBusinessFunctionId(fileUpload.getBusinessFunctionId());
        } catch (Exception e) {
            log.warn("获取字段映射失败: fileUploadId={}", fileUploadId, e);
            return new ArrayList<>();
        }
    }

    /**
     * 生成解释标识
     * 
     * @param fileUploadId 文件上传ID
     * @return 解释标识
     */
    private String generateInterpretKey(long fileUploadId) {
        return "INTERPRET_" + fileUploadId + "_" + System.currentTimeMillis();
    }

    /**
     * 保存解释记录
     * 
     * @param fileUploadId 文件上传ID
     * @param interpretKey 解释标识
     * @param businessUniqueCode 业务唯一码
     * @param parseResult 解析结果
     * @param needCallback 是否需要回调
     * @param callbackUrl 回调URL
     * @return 解释记录
     */
    private SaaSFileInterpretRecord saveInterpretRecord(
            long fileUploadId,
            String interpretKey,
            String businessUniqueCode,
            SaaSFileParseResultDto parseResult,
            int needCallback,
            String callbackUrl) {
        
        SaaSFileInterpretRecord record = new SaaSFileInterpretRecord();
        record.setFileUploadId(fileUploadId);
        record.setInterpretKey(interpretKey);
        record.setBusinessUniqueCode(businessUniqueCode);
        record.setInterpretStatus("COMPLETED");
        record.setInterpretProgress(100);
        record.setTotalCount(parseResult.getTotalRows());
        record.setSuccessCount(parseResult.getSuccessRows());
        record.setFailCount(parseResult.getFailRows());
        record.setSkipCount(parseResult.getSkipRows());
        record.setNeedCallback(needCallback);
        record.setCallbackUrl(callbackUrl);
        record.setCallbackStatus(needCallback == 1 ? "PENDING" : null);
        record.setBusinessProcessStatus("PENDING");
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());

        return interpretRecordRepository.saveId(record);
    }

    /**
     * 更新文件上传记录状态
     * 
     * @param fileUploadId 文件上传ID
     * @param interpretKey 解释标识
     * @param parseResult 解析结果
     */
    private void updateFileUploadStatus(
            long fileUploadId,
            String interpretKey,
            SaaSFileParseResultDto parseResult) {
        
        try {
            SaaSFileUpload fileUpload = fileUploadRepository.getById(fileUploadId);
            if (fileUpload == null) {
                log.warn("文件上传记录不存在: fileUploadId={}", fileUploadId);
                return;
            }

            fileUpload.setInterpretFlag(1);
            fileUpload.setInterpretStatus("COMPLETED");
            fileUpload.setInterpretKey(interpretKey);
            fileUpload.setStatus("COMPLETED");
            fileUpload.setProgressPercent(100);
            fileUpload.setTotalCount(parseResult.getTotalRows());
            fileUpload.setSuccessCount(parseResult.getSuccessRows());
            fileUpload.setFailCount(parseResult.getFailRows());
            fileUpload.setMessage(parseResult.getMessage());
            fileUpload.setModifyDate(LocalDateTime.now());

            fileUploadRepository.updateById(fileUpload);

        } catch (Exception e) {
            log.warn("更新文件上传状态失败: fileUploadId={}", fileUploadId, e);
        }
    }
}
