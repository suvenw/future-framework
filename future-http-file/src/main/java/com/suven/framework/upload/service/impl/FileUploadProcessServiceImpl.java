package com.suven.framework.upload.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.HttpClientUtil;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.proxy.HttpProxyDefaultRequest;
import com.suven.framework.http.proxy.HttpProxyRequest;
import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.*;
import com.suven.framework.upload.repository.FileInterpretRecordRepository;
import com.suven.framework.upload.repository.FileOperationRecordRepository;
import com.suven.framework.upload.service.*;
import com.suven.framework.upload.vo.request.FileUploadRequestVo;
import com.suven.framework.upload.vo.response.FileUploadProcessResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 文件上传业务流程服务实现
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class FileUploadProcessServiceImpl implements FileUploadProcessService {

    @Autowired
    private CompanyBusinessFunctionService businessFunctionService;

    @Autowired
    private FileFieldMappingService fieldMappingService;

    @Autowired
    private FileOperationService fileOperationService;

    @Autowired
    private FileParseService fileParseService;

    @Autowired
    private FileOperationRecordRepository operationRecordRepository;

    @Autowired
    private FileInterpretRecordRepository interpretRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileUploadProcessResponseVo processUpload(
            FileUploadRequestVo requestVo,
            InputStream inputStream,
            String fileName) {
        
        log.info("开始处理文件上传业务: businessUniqueCode={}, fileName={}", 
                requestVo.getBusinessUniqueCode(), fileName);

        FileUploadProcessResponseVo responseVo = new FileUploadProcessResponseVo();
        responseVo.setFileName(fileName);
        responseVo.setStartTime(LocalDateTime.now());

        try {
            // 步骤1: 校验业务功能配置
            log.debug("步骤1: 校验业务功能配置");
            CompanyBusinessFunction businessFunction = validateAndGetBusinessFunction(
                    requestVo.getBusinessUniqueCode());
            responseVo.setBusinessFunctionId(businessFunction.getId());
            responseVo.setBusinessUniqueCode(businessFunction.getBusinessUniqueCode());

            // 步骤2: 创建操作记录
            log.debug("步骤2: 创建操作记录");
            FileOperationRecord operationRecord = createOperationRecord(requestVo, businessFunction, fileName);
            responseVo.setOperationRecordId(operationRecord.getId());

            // 步骤3: 解析文件
            log.debug("步骤3: 解析文件");
            updateOperationStatus(operationRecord.getId(), "PARSING", 20, "正在解析文件");
            FileParseResultDto parseResult = parseFile(inputStream, fileName, businessFunction.getId());
            
            if (!parseResult.isSuccess()) {
                log.error("文件解析失败: {}", parseResult.getErrorMessage());
                updateOperationStatus(operationRecord.getId(), "FAILED", 100, 
                        "文件解析失败: " + parseResult.getErrorMessage());
                responseVo.setSuccess(false);
                responseVo.setErrorMessage(parseResult.getErrorMessage());
                responseVo.setEndTime(LocalDateTime.now());
                return responseVo;
            }
            
            responseVo.setTotalRows(parseResult.getTotalRows());
            responseVo.setSuccessRows(parseResult.getSuccessRows());
            responseVo.setFailRows(parseResult.getFailRows());

            // 步骤4: 数据校验（如果配置了需要校验）
            log.debug("步骤4: 数据校验");
            updateOperationStatus(operationRecord.getId(), "VALIDATING", 40, "正在校验数据");
            List<String> validationErrors = validateData(parseResult.getDataRows(), businessFunction.getId());
            
            if (!validationErrors.isEmpty()) {
                log.warn("数据校验发现错误: count={}", validationErrors.size());
                responseVo.setValidationErrors(validationErrors);
                // 继续处理，但记录错误
            }

            // 步骤5: 保存解释记录
            log.debug("步骤5: 保存解释记录");
            updateOperationStatus(operationRecord.getId(), "SAVING", 60, "正在保存数据");
            List<FileInterpretRecord> interpretRecords = saveInterpretRecords(
                    operationRecord.getId(), parseResult, businessFunction.getBusinessUniqueCode());
            responseVo.setInterpretRecordCount(interpretRecords.size());

            // 步骤6: 回调业务服务（如果需要）
            log.debug("步骤6: 回调业务服务");
            updateOperationStatus(operationRecord.getId(), "CALLBACK", 80, "正在通知业务服务");
            
            boolean callbackSuccess = true;
            if (businessFunction.getCallbackUrl() != null && !businessFunction.getCallbackUrl().isEmpty()) {
                if (requestVo.getNeedCallback() != null && requestVo.getNeedCallback() == 1) {
                    callbackSuccess = callbackBusinessService(
                            operationRecord.getId(), interpretRecords, businessFunction.getCallbackUrl());
                    
                    if (!callbackSuccess) {
                        // 异步重试回调
                        asyncCallbackBusinessService(operationRecord.getId(), businessFunction.getCallbackUrl());
                    }
                }
            }

            // 更新最终状态
            log.debug("更新最终状态");
            String finalStatus = callbackSuccess ? "COMPLETED" : "CALLBACK_PENDING";
            String finalMessage = callbackSuccess ? "处理完成" : "等待回调确认";
            updateOperationStatus(operationRecord.getId(), finalStatus, 100, finalMessage);

            // 更新操作记录的统计信息
            updateOperationRecordStats(operationRecord.getId(), parseResult, interpretRecords);

            responseVo.setSuccess(true);
            responseVo.setCallbackSuccess(callbackSuccess);
            responseVo.setMessage(finalMessage);

        } catch (Exception e) {
            log.error("处理文件上传业务异常", e);
            if (responseVo.getOperationRecordId() != null) {
                updateOperationStatus(responseVo.getOperationRecordId(), "FAILED", 100, 
                        "处理异常: " + e.getMessage());
            }
            responseVo.setSuccess(false);
            responseVo.setErrorMessage("处理异常: " + e.getMessage());
        }

        responseVo.setEndTime(LocalDateTime.now());
        log.info("文件上传业务处理完成: success={}, message={}", 
                responseVo.isSuccess(), responseVo.getMessage());
        
        return responseVo;
    }

    @Override
    public boolean validateBusinessFunction(String businessUniqueCode) {
        CompanyBusinessFunction function = businessFunctionService.getByBusinessUniqueCode(businessUniqueCode);
        
        if (function == null) {
            log.error("业务功能配置不存在: businessUniqueCode={}", businessUniqueCode);
            return false;
        }
        
        if (!"ACTIVE".equals(function.getStatus())) {
            log.error("业务功能未激活: businessUniqueCode={}, status={}", 
                    businessUniqueCode, function.getStatus());
            return false;
        }
        
        return true;
    }

    @Override
    public FileParseResultDto parseFile(
            InputStream inputStream,
            String fileName,
            long businessFunctionId) {
        
        log.info("解析文件: fileName={}, businessFunctionId={}", fileName, businessFunctionId);

        // 获取文件扩展名
        String fileExtension = getFileExtension(fileName);
        
        // 获取字段映射
        List<FileFieldMapping> fieldMappings = fieldMappingService.getByBusinessFunctionId(businessFunctionId);
        
        // 解析文件
        FileParseResultDto parseResult;
        if (ObjectTrue.isNotEmpty(fieldMappings)) {
            parseResult = fileParseService.parseWithMapping(inputStream, fileName, fileExtension, fieldMappings);
        } else {
            parseResult = fileParseService.parse(inputStream, fileName, fileExtension);
        }

        return parseResult;
    }

    @Override
    public List<String> validateData(
            List<Map<String, Object>> dataRows,
            long businessFunctionId) {
        
        log.info("校验数据: rows={}, businessFunctionId={}", dataRows.size(), businessFunctionId);
        
        List<String> errors = new ArrayList<>();
        
        // 获取字段映射
        List<FileFieldMapping> fieldMappings = fieldMappingService.getByBusinessFunctionId(businessFunctionId);
        if (ObjectTrue.isEmpty(fieldMappings)) {
            log.warn("未配置字段映射，跳过数据校验");
            return errors;
        }

        // 构建字段映射Map
        Map<String, FileFieldMapping> fieldMap = fieldMappings.stream()
                .collect(Collectors.toMap(FileFieldMapping::getFieldEnglishName, f -> f));

        // 逐行校验
        for (int i = 0; i < dataRows.size(); i++) {
            Map<String, Object> row = dataRows.get(i);
            int rowNum = i + 1;
            
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                FileFieldMapping mapping = fieldMap.get(fieldName);
                
                if (mapping == null) {
                    continue; // 未配置的字段跳过
                }

                // 校验必填
                if (mapping.getIsRequired() == 1) {
                    if (fieldValue == null || StringUtils.isBlank(fieldValue.toString())) {
                        errors.add(String.format("第%d行: %s(%s)为必填项", 
                                rowNum, mapping.getFieldChineseName(), fieldName));
                    }
                }

                // 校验数据类型
                if (fieldValue != null && StringUtils.isNotBlank(fieldValue.toString())) {
                    String typeError = validateFieldType(fieldValue.toString(), mapping, rowNum);
                    if (typeError != null) {
                        errors.add(typeError);
                    }
                }

                // 校验正则规则
                if (fieldValue != null && StringUtils.isNotBlank(mapping.getValidateRule())) {
                    String regexError = validateFieldRegex(fieldValue.toString(), mapping, rowNum);
                    if (regexError != null) {
                        errors.add(regexError);
                    }
                }
            }
        }

        return errors;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<FileInterpretRecord> saveInterpretRecords(
            long operationRecordId,
            FileParseResultDto parseResult,
            String businessUniqueCode) {
        
        log.info("保存解释记录: operationRecordId={}, rows={}", 
                operationRecordId, parseResult.getDataRows().size());

        List<FileInterpretRecord> records = new ArrayList<>();
        List<Map<String, Object>> dataRows = parseResult.getDataRows();

        for (int i = 0; i < dataRows.size(); i++) {
            Map<String, Object> rowData = dataRows.get(i);
            
            FileInterpretRecord record = new FileInterpretRecord();
            record.setFileUploadId(operationRecordId);
            record.setBusinessUniqueCode(businessUniqueCode);
            record.setRowIndex(i + 1);
            record.setInterpretInfo(JSON.toJSONString(rowData));
            record.setBusinessStatus("PENDING"); // 待业务处理
            record.setCreateDate(LocalDateTime.now());
            record.setModifyDate(LocalDateTime.now());
            record.setDeleted(0);
            
            interpretRecordRepository.save(record);
            records.add(record);
        }

        return records;
    }

    @Override
    public boolean callbackBusinessService(
            long operationRecordId,
            List<FileInterpretRecord> interpretRecords,
            String callbackUrl) {
        
        log.info("回调业务服务: operationRecordId={}, records={}, callbackUrl={}", 
                operationRecordId, interpretRecords.size(), callbackUrl);

        try {
            // 构建回调数据
            Map<String, Object> callbackData = new HashMap<>();
            callbackData.put("operationRecordId", operationRecordId);
            callbackData.put("recordCount", interpretRecords.size());
            callbackData.put("records", interpretRecords.stream()
                    .map(r -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("interpretId", r.getId());
                        map.put("businessUniqueCode", r.getBusinessUniqueCode());
                        map.put("rowIndex", r.getRowIndex());
                        map.put("interpretInfo", r.getInterpretInfo());
                        return map;
                    })
                    .collect(Collectors.toList()));
            callbackData.put("callbackTime", LocalDateTime.now().toString());

            // 发送回调请求
            HttpProxyRequest proxyRequest = new HttpProxyDefaultRequest();
            HttpClientResponse response = HttpClientUtil.post(callbackUrl, callbackData, proxyRequest);

            if (response != null && response.isSuccess()) {
                log.info("回调业务服务成功: operationRecordId={}", operationRecordId);
                
                // 更新操作记录的回调状态
                FileOperationRecord operationRecord = operationRecordRepository.getById(operationRecordId);
                if (operationRecord != null) {
                    operationRecord.setCallbackStatus("SUCCESS");
                    operationRecord.setCallbackTime(LocalDateTime.now());
                    operationRecord.setModifyDate(LocalDateTime.now());
                    operationRecordRepository.updateById(operationRecord);
                }
                
                return true;
            } else {
                log.warn("回调业务服务失败: operationRecordId={}, response={}", 
                        operationRecordId, response != null ? response.getBody() : "null");
                return false;
            }

        } catch (Exception e) {
            log.error("回调业务服务异常: operationRecordId={}", operationRecordId, e);
            return false;
        }
    }

    @Override
    @Async("fileUploadExecutor")
    public void asyncCallbackBusinessService(long operationRecordId, String callbackUrl) {
        log.info("异步回调业务服务: operationRecordId={}, callbackUrl={}", operationRecordId, callbackUrl);

        int maxRetries = 3;
        int retryCount = 0;
        boolean success = false;

        while (retryCount < maxRetries && !success) {
            try {
                Thread.sleep(5000 * (retryCount + 1)); // 递增延迟

                // 获取最新的解释记录
                List<FileInterpretRecord> records = interpretRecordRepository.getByFileUploadId(operationRecordId);
                success = callbackBusinessService(operationRecordId, records, callbackUrl);

                if (!success) {
                    retryCount++;
                    log.warn("异步回调重试: operationRecordId={}, retry={}", operationRecordId, retryCount);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("异步回调被中断: operationRecordId={}", operationRecordId);
                break;
            } catch (Exception e) {
                retryCount++;
                log.error("异步回调异常: operationRecordId={}, retry={}", operationRecordId, retryCount, e);
            }
        }

        if (!success) {
            log.error("异步回调最终失败: operationRecordId={}", operationRecordId);
            
            // 更新操作记录状态
            FileOperationRecord operationRecord = operationRecordRepository.getById(operationRecordId);
            if (operationRecord != null) {
                operationRecord.setCallbackStatus("FAILED");
                operationRecord.setMessage("回调失败，已达到最大重试次数");
                operationRecord.setModifyDate(LocalDateTime.now());
                operationRecordRepository.updateById(operationRecord);
            }
        }
    }

    @Override
    public Map<String, Object> getProcessProgress(long operationRecordId) {
        log.debug("获取处理进度: operationRecordId={}", operationRecordId);

        FileOperationRecord record = operationRecordRepository.getById(operationRecordId);
        if (record == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "操作记录不存在");
        }

        Map<String, Object> progress = new HashMap<>();
        progress.put("operationRecordId", operationRecordId);
        progress.put("status", record.getStatus());
        progress.put("progressPercent", record.getProgressPercent());
        progress.put("message", record.getMessage());
        progress.put("callbackStatus", record.getCallbackStatus());
        progress.put("totalCount", record.getTotalCount());
        progress.put("successCount", record.getSuccessCount());
        progress.put("failCount", record.getFailCount());

        return progress;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileUploadProcessResponseVo retryFailedRecords(long operationRecordId) {
        log.info("重新处理失败记录: operationRecordId={}", operationRecordId);

        FileOperationRecord operationRecord = operationRecordRepository.getById(operationRecordId);
        if (operationRecord == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "操作记录不存在");
        }

        // 获取失败的解释记录
        List<FileInterpretRecord> failedRecords = interpretRecordRepository.getByFileUploadId(operationRecordId)
                .stream()
                .filter(r -> !"SUCCESS".equals(r.getBusinessStatus()))
                .collect(Collectors.toList());

        FileUploadProcessResponseVo responseVo = new FileUploadProcessResponseVo();
        responseVo.setOperationRecordId(operationRecordId);
        responseVo.setStartTime(LocalDateTime.now());

        if (failedRecords.isEmpty()) {
            responseVo.setSuccess(true);
            responseVo.setMessage("没有需要重试的记录");
            responseVo.setEndTime(LocalDateTime.now());
            return responseVo;
        }

        // 回调业务服务
        boolean callbackSuccess = callbackBusinessService(
                operationRecordId, failedRecords, operationRecord.getCallbackUrl());

        responseVo.setSuccess(callbackSuccess);
        responseVo.setCallbackSuccess(callbackSuccess);
        responseVo.setMessage(callbackSuccess ? "重试成功" : "重试失败");
        responseVo.setInterpretRecordCount(failedRecords.size());
        responseVo.setEndTime(LocalDateTime.now());

        return responseVo;
    }

    @Override
    public PageResult<FileOperationRecord> queryOperationRecords(
            String businessUniqueCode,
            Pager<FileOperationRecord> pager) {
        
        log.debug("查询操作记录: businessUniqueCode={}, pageNo={}, pageSize={}", 
                businessUniqueCode, pager.getPageNo(), pager.getPageSize());

        // 这里可以根据需要实现更复杂的查询逻辑
        // 暂时返回空结果
        return new PageResult<>();
    }

    @Override
    public FileOperationRecord getOperationRecordDetail(long operationRecordId) {
        log.debug("获取操作记录详情: operationRecordId={}", operationRecordId);
        return operationRecordRepository.getById(operationRecordId);
    }

    // ==================== 私有方法 ====================

    private CompanyBusinessFunction validateAndGetBusinessFunction(String businessUniqueCode) {
        CompanyBusinessFunction function = businessFunctionService.getByBusinessUniqueCode(businessUniqueCode);
        
        if (function == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, 
                    "业务功能配置不存在: " + businessUniqueCode);
        }
        
        if (!"ACTIVE".equals(function.getStatus())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, 
                    "业务功能未激活: " + businessUniqueCode);
        }
        
        return function;
    }

    private FileOperationRecord createOperationRecord(
            FileUploadRequestVo requestVo,
            CompanyBusinessFunction businessFunction,
            String fileName) {
        
        FileOperationRecord record = new FileOperationRecord();
        record.setAppId(requestVo.getAppId());
        record.setClientId(requestVo.getClientId());
        record.setUseBusinessId(businessFunction.getId());
        record.setCompanyId(businessFunction.getCompanyId());
        record.setCompanyName(businessFunction.getCompanyName());
        record.setUploadUserId(requestVo.getUploadUserId());
        record.setUploadUserName(requestVo.getUploadUserName());
        record.setFileProductName(businessFunction.getBusinessName());
        record.setFileBusinessName(businessFunction.getBusinessCode());
        record.setFunctionType(businessFunction.getFunctionType());
        record.setPlatformType(businessFunction.getPlatformType());
        record.setFileSourceName(fileName);
        record.setFileType(getFileExtension(fileName));
        record.setNeedCallback(requestVo.getNeedCallback() != null ? requestVo.getNeedCallback() : 0);
        record.setCallbackUrl(businessFunction.getCallbackUrl());
        record.setStatus("PENDING");
        record.setProgressPercent(0);
        record.setCallbackStatus("PENDING");
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());
        
        operationRecordRepository.save(record);
        
        return record;
    }

    private void updateOperationStatus(long operationRecordId, String status, int progressPercent, String message) {
        fileOperationService.updateOperationStatus(operationRecordId, status, progressPercent, message);
    }

    private void updateOperationRecordStats(
            long operationRecordId,
            FileParseResultDto parseResult,
            List<FileInterpretRecord> interpretRecords) {
        
        FileOperationRecord record = operationRecordRepository.getById(operationRecordId);
        if (record == null) {
            return;
        }

        record.setTotalCount(parseResult.getTotalRows());
        record.setSuccessCount(parseResult.getSuccessRows());
        record.setFailCount(parseResult.getFailRows());
        record.setModifyDate(LocalDateTime.now());
        
        operationRecordRepository.updateById(record);
    }

    private String getFileExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    private String validateFieldType(String value, FileFieldMapping mapping, int rowNum) {
        String fieldType = mapping.getFieldType();
        if (StringUtils.isBlank(fieldType)) {
            return null;
        }

        try {
            switch (fieldType.toUpperCase()) {
                case "NUMBER":
                    new java.math.BigDecimal(value);
                    break;
                case "INTEGER":
                    Integer.parseInt(value);
                    break;
                case "LONG":
                    Long.parseLong(value);
                    break;
                case "DATE":
                    // 尝试解析日期
                    java.time.LocalDate.parse(value);
                    break;
                case "DATETIME":
                    java.time.LocalDateTime.parse(value.replace(" ", "T"));
                    break;
                case "BOOLEAN":
                    if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)
                            && !"1".equals(value) && !"0".equals(value)) {
                        return String.format("第%d行: %s(%s)布尔值格式错误", 
                                rowNum, mapping.getFieldChineseName(), mapping.getFieldEnglishName());
                    }
                    break;
                default:
                    // STRING类型不校验
                    break;
            }
        } catch (Exception e) {
            return String.format("第%d行: %s(%s)数据类型错误，期望%s", 
                    rowNum, mapping.getFieldChineseName(), mapping.getFieldEnglishName(), fieldType);
        }

        return null;
    }

    private String validateFieldRegex(String value, FileFieldMapping mapping, int rowNum) {
        String validateRule = mapping.getValidateRule();
        if (StringUtils.isBlank(validateRule)) {
            return null;
        }

        try {
            Pattern pattern = Pattern.compile(validateRule);
            if (!pattern.matcher(value).matches()) {
                return String.format("第%d行: %s(%s)格式不符合规则", 
                        rowNum, mapping.getFieldChineseName(), mapping.getFieldEnglishName());
            }
        } catch (Exception e) {
            log.warn("正则表达式验证异常: rule={}", validateRule, e);
        }

        return null;
    }
}
