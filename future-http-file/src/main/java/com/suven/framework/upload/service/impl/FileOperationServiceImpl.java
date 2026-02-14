package com.suven.framework.upload.service.impl;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.upload.dto.request.SaaSFileInterpretRequestDto;
import com.suven.framework.upload.dto.request.SaaSFileOperationRequestDto;
import com.suven.framework.upload.dto.response.FileFieldResponseDto;
import com.suven.framework.upload.dto.response.FileInterpretResponseDto;
import com.suven.framework.upload.dto.response.FileOperationResponseDto;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.entity.SaaSFileInterpretRecord;
import com.suven.framework.upload.entity.FileOperationRecord;
import com.suven.framework.upload.mapper.SaaSFileInterpretRecordMapper;
import com.suven.framework.upload.mapper.FileOperationRecordMapper;
import com.suven.framework.upload.repository.SaaSFileFieldMappingRepository;
import com.suven.framework.upload.repository.SaaSFileInterpretRecordRepository;
import com.suven.framework.upload.repository.FileOperationRecordRepository;
import com.suven.framework.upload.service.SaaSFileOperationService;
import com.suven.framework.upload.vo.request.SaaSFileCallbackRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileInterpretPageRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

/**
 * SaaS文件业务操作服务实现
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class FileOperationServiceImpl implements SaaSFileOperationService {

    @Autowired
    private FileOperationRecordRepository operationRecordRepository;

    @Autowired
    private SaaSFileInterpretRecordRepository interpretRecordRepository;

    @Autowired
    private FileOperationRecordMapper operationRecordMapper;

    @Autowired
    private SaaSFileFieldMappingRepository fieldMappingRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileOperationResponseDto createOperationRecord(SaaSFileOperationRequestDto requestDto) {
        log.info("创建文件操作记录开始, AppId: {}, FileName: {}", requestDto.getAppId(), requestDto.getFileSourceName());
        
        try {
            FileOperationRecord record = new FileOperationRecord();
            record.setAppId(requestDto.getAppId());
            record.setClientId(requestDto.getClientId());
            record.setUseBusinessId(requestDto.getUseBusinessId());
            record.setCompanyId(requestDto.getCompanyId());
            record.setCompanyName(requestDto.getCompanyName());
            record.setUploadUserId(requestDto.getUploadUserId());
            record.setUploadUserName(requestDto.getUploadUserName());
            record.setFileProductName(requestDto.getFileProductName());
            record.setFileBusinessName(requestDto.getFileBusinessName());
            record.setFunctionType(requestDto.getFunctionType());
            record.setPlatformType(requestDto.getPlatformType());
            record.setFileUploadStorageId(requestDto.getFileUploadStorageId());
            record.setFileSourceName(requestDto.getFileSourceName());
            record.setFileType(requestDto.getFileType());
            record.setNeedCallback(requestDto.getNeedCallback());
            record.setCallbackUrl(requestDto.getCallbackUrl());
            record.setStatus("PENDING");
            record.setProgressPercent(0);
            record.setCallbackStatus("PENDING");
            record.setCreateDate(LocalDateTime.now());
            record.setModifyDate(LocalDateTime.now());
            
            FileOperationRecord savedRecord = operationRecordRepository.saveId(record);
            
            // 保存字段映射
            if (ObjectTrue.isNotEmpty(requestDto.getFieldMappings())) {
                saveFieldMappings(savedRecord.getId(), requestDto.getFieldMappings());
            }
            
            FileOperationResponseDto responseDto = buildOperationResponseDto(savedRecord);
            log.info("创建文件操作记录成功, ID: {}", savedRecord.getId());
            return responseDto;
        } catch (Exception e) {
            log.error("创建文件操作记录失败", e);
            throw new RuntimeException("创建文件操作记录失败: " + e.getMessage());
        }
    }

    @Override
    public FileOperationResponseDto getOperationDetail(long id) {
        log.info("查询操作记录详情, ID: {}", id);
        
        FileOperationRecord record = operationRecordRepository.getById(id);
        if (record == null) {
            throw new RuntimeException("操作记录不存在");
        }
        
        FileOperationResponseDto responseDto = buildOperationResponseDto(record);
        
        // 查询字段映射，统一由 Repository 封装查询条件
        List<SaaSFileFieldMapping> fieldMappings = fieldMappingRepository.getByOperationRecordId(id);
        responseDto.setFieldMappings(buildFieldResponseList(fieldMappings));
        
        // 查询解释记录
        List<SaaSFileInterpretRecord> interpretRecords = interpretRecordRepository.getByOperationRecordId(id);
        responseDto.setInterpretRecords(buildInterpretResponseList(interpretRecords));
        
        return responseDto;
    }

    @Override
    public PageResult<FileOperationResponseDto> queryOperationPage(SaaSFileOperationRequestDto requestDto, Pager pager) {
        log.info("分页查询操作记录, AppId: {}, PageNo: {}, PageSize: {}", 
            requestDto.getAppId(), pager.getPageNo(), pager.getPageSize());
        
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<FileOperationRecord> queryWrapper =
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        
        if (ObjectTrue.isNotEmpty(requestDto.getAppId())) {
            queryWrapper.eq(FileOperationRecord::getAppId, requestDto.getAppId());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getClientId())) {
            queryWrapper.eq(FileOperationRecord::getClientId, Long.parseLong(requestDto.getClientId()));
        }
        if (ObjectTrue.isNotEmpty(requestDto.getFileProductName())) {
            queryWrapper.eq(FileOperationRecord::getFileProductName, requestDto.getFileProductName());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getFileBusinessName())) {
            queryWrapper.eq(FileOperationRecord::getFileBusinessName, requestDto.getFileBusinessName());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getStatus())) {
            queryWrapper.eq(FileOperationRecord::getStatus, requestDto.getStatus());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getFileSourceName())) {
            queryWrapper.like(FileOperationRecord::getFileSourceName, requestDto.getFileSourceName());
        }
        queryWrapper.eq(FileOperationRecord::getDeleted, 0);
        queryWrapper.orderByDesc(FileOperationRecord::getId);
        
        PageResult<FileOperationRecord> pageResult = operationRecordMapper.selectPage(pager, queryWrapper);
        
        PageResult<FileOperationResponseDto> result = new PageResult<>();
        result.setTotal(pageResult.getTotal());
        result.setList(pageResult.getList().stream()
            .map(this::buildOperationResponseDto)
            .toList());
        
        return result;
    }

    @Override
    public boolean updateOperationStatus(long id, String status, int progressPercent, String message) {
        log.info("更新操作记录状态, ID: {}, Status: {}, Progress: {}", id, status, progressPercent);
        
        FileOperationRecord record = operationRecordRepository.getById(id);
        if (record == null) {
            return false;
        }
        
        record.setStatus(status);
        record.setProgressPercent(progressPercent);
        if (ObjectTrue.isNotEmpty(message)) {
            record.setMessage(message);
        }
        record.setModifyDate(LocalDateTime.now());
        
        return operationRecordRepository.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFieldMappings(long operationId, List<SaaSFileInterpretRequestDto> fieldMappings) {
        log.info("添加字段映射, OperationId: {}, Count: {}", operationId, fieldMappings.size());
        
        try {
            return saveFieldMappings(operationId, fieldMappings);
        } catch (Exception e) {
            log.error("添加字段映射失败", e);
            throw new RuntimeException("添加字段映射失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInterpretResponseDto createInterpretRecord(SaaSFileInterpretRequestDto requestDto) {
        log.info("创建解释记录, OperationId: {}, Key: {}", requestDto.getOperationRecordId(), requestDto.getInterpretKey());
        
        try {
            SaaSFileInterpretRecord record = new SaaSFileInterpretRecord();
            record.setOperationRecordId(requestDto.getOperationRecordId());
            record.setInterpretKey(requestDto.getInterpretKey());
            record.setBusinessUniqueCode(requestDto.getBusinessUniqueCode());
            record.setBusinessType(requestDto.getBusinessType());
            record.setBusinessDescription(requestDto.getBusinessDescription());
            record.setInterpretInfo(requestDto.getInterpretInfo());
            record.setInterpretStatus(requestDto.getInterpretStatus() != null ? requestDto.getInterpretStatus() : "PENDING");
            record.setTotalCount(requestDto.getTotalCount());
            record.setNeedCallback(requestDto.getNeedCallback());
            record.setCallbackUrl(requestDto.getCallbackUrl());
            record.setCallbackMethod(requestDto.getCallbackMethod() != null ? requestDto.getCallbackMethod() : "POST");
            record.setCallbackStatus("PENDING");
            record.setBusinessProcessStatus("PENDING");
            record.setCreateDate(LocalDateTime.now());
            record.setModifyDate(LocalDateTime.now());
            
            SaaSFileInterpretRecord savedRecord = interpretRecordRepository.saveId(record);
            
            return buildInterpretResponseDto(savedRecord);
        } catch (Exception e) {
            log.error("创建解释记录失败", e);
            throw new RuntimeException("创建解释记录失败: " + e.getMessage());
        }
    }

    @Override
    public FileInterpretResponseDto getInterpretDetail(long id) {
        log.info("查询解释记录详情, ID: {}", id);
        
        SaaSFileInterpretRecord record = interpretRecordRepository.getById(id);
        if (record == null) {
            throw new RuntimeException("解释记录不存在");
        }
        
        return buildInterpretResponseDto(record);
    }

    @Override
    public PageResult<FileInterpretResponseDto> queryInterpretPage(long operationId, Pager pager) {
        log.info("分页查询解释记录, OperationId: {}", operationId);
        
        List<SaaSFileInterpretRecord> records = interpretRecordRepository.getByOperationRecordId(operationId);
        
        PageResult<FileInterpretResponseDto> result = new PageResult<>();
        result.setTotal(records.size());
        result.setList(records.stream()
            .map(this::buildInterpretResponseDto)
            .toList());
        
        return result;
    }

    @Override
    public boolean updateInterpretRecord(SaaSFileInterpretRequestDto requestDto) {
        log.info("更新解释记录, ID: {}", requestDto.getId());
        
        SaaSFileInterpretRecord record = interpretRecordRepository.getById(requestDto.getId());
        if (record == null) {
            return false;
        }
        
        if (ObjectTrue.isNotEmpty(requestDto.getInterpretInfo())) {
            record.setInterpretInfo(requestDto.getInterpretInfo());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getInterpretStatus())) {
            record.setInterpretStatus(requestDto.getInterpretStatus());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getBusinessProcessStatus())) {
            record.setBusinessProcessStatus(requestDto.getBusinessProcessStatus());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getBusinessProcessResult())) {
            record.setBusinessProcessResult(requestDto.getBusinessProcessResult());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getBusinessExceptionInfo())) {
            record.setBusinessExceptionInfo(requestDto.getBusinessExceptionInfo());
        }
        record.setModifyDate(LocalDateTime.now());
        
        return interpretRecordRepository.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleBusinessCallback(SaaSFileCallbackRequestVo callbackRequest) {
        log.info("处理业务回调, InterpretId: {}, Status: {}", 
            callbackRequest.getInterpretRecordId(), callbackRequest.getBusinessProcessStatus());
        
        try {
            SaaSFileInterpretRecord record = interpretRecordRepository.getById(callbackRequest.getInterpretRecordId());
            if (record == null) {
                log.warn("解释记录不存在, ID: {}", callbackRequest.getInterpretRecordId());
                return false;
            }
            
            // 验证业务唯一码
            if (!record.getBusinessUniqueCode().equals(callbackRequest.getBusinessUniqueCode())) {
                log.warn("业务唯一码不匹配, Expected: {}, Actual: {}", 
                    record.getBusinessUniqueCode(), callbackRequest.getBusinessUniqueCode());
                return false;
            }
            
            // 更新解释记录
            record.setBusinessProcessStatus(callbackRequest.getBusinessProcessStatus());
            if (ObjectTrue.isNotEmpty(callbackRequest.getBusinessProcessResult())) {
                record.setBusinessProcessResult(callbackRequest.getBusinessProcessResult());
            }
            if (ObjectTrue.isNotEmpty(callbackRequest.getBusinessExceptionInfo())) {
                record.setBusinessExceptionInfo(callbackRequest.getBusinessExceptionInfo());
            }
            record.setBusinessProcessTime(LocalDateTime.now());
            record.setModifyDate(LocalDateTime.now());
            
            boolean result = interpretRecordRepository.updateById(record);
            
            // 如果处理成功，更新操作记录的成功/失败计数
            if ("SUCCESS".equals(callbackRequest.getBusinessProcessStatus())) {
                updateOperationRecordCounts(record.getOperationRecordId());
            }
            
            return result;
        } catch (Exception e) {
            log.error("处理业务回调失败", e);
            throw new RuntimeException("处理业务回调失败: " + e.getMessage());
        }
    }

    @Override
    public PageResult<FileInterpretResponseDto> queryPendingInterpretRecords(long operationId, String status, Pager pager) {
        log.info("查询待处理的解释记录, OperationId: {}, Status: {}", operationId, status);
        
        List<SaaSFileInterpretRecord> records = interpretRecordRepository.getByOperationRecordIdAndStatus(
            operationId, status, pager);
        
        PageResult<FileInterpretResponseDto> result = new PageResult<>();
        result.setTotal(pager.getTotal());
        result.setList(records.stream()
            .map(this::buildInterpretResponseDto)
            .toList());
        
        return result;
    }

    @Override
    public List<FileInterpretResponseDto> getInterpretRecordsByBusinessCode(String businessUniqueCode) {
        log.info("根据业务唯一码查询解释记录, Code: {}", businessUniqueCode);
        
        List<SaaSFileInterpretRecord> records = interpretRecordRepository.getByBusinessUniqueCode(businessUniqueCode);
        
        return records.stream()
            .map(this::buildInterpretResponseDto)
            .toList();
    }

    @Override
    public PageResult<FileInterpretResponseDto> pageQueryInterpretByBusiness(SaaSFileInterpretPageRequestVo requestVo) {
        log.info("按业务唯一码分页查询解释记录, businessUniqueCode: {}, pageNo: {}, pageSize: {}",
                requestVo.getBusinessUniqueCode(), requestVo.getPageNo(), requestVo.getPageSize());

        String businessUniqueCode = requestVo.getBusinessUniqueCode();

        List<SaaSFileInterpretRecord> records = interpretRecordRepository.getByBusinessUniqueCode(businessUniqueCode);
        if (records == null) {
            records = new ArrayList<>();
        }

        int total = records.size();
        int pageNo = requestVo.getPageNo() <= 0 ? 1 : requestVo.getPageNo();
        int pageSize = requestVo.getPageSize() <= 0 ? 20 : requestVo.getPageSize();

        int fromIndex = (pageNo - 1) * pageSize;
        if (fromIndex >= total) {
            fromIndex = 0;
        }
        int toIndex = Math.min(fromIndex + pageSize, total);

        List<SaaSFileInterpretRecord> pageList = records.subList(fromIndex, toIndex);

        PageResult<FileInterpretResponseDto> result = new PageResult<>();
        result.setTotal(total);
        result.setList(pageList.stream()
                .map(this::buildInterpretResponseDto)
                .toList());

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOperationRecord(long id) {
        log.info("删除操作记录, ID: {}", id);
        
        FileOperationRecord record = operationRecordRepository.getById(id);
        if (record == null) {
            return false;
        }
        
        record.setDeleted(1);
        record.setModifyDate(LocalDateTime.now());
        
        return operationRecordRepository.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteInterpretRecord(long id) {
        log.info("删除解释记录, ID: {}", id);
        
        SaaSFileInterpretRecord record = interpretRecordRepository.getById(id);
        if (record == null) {
            return false;
        }
        
        record.setDeleted(1);
        record.setModifyDate(LocalDateTime.now());
        
        return interpretRecordRepository.updateById(record);
    }

    // ==================== 私有方法 ====================

    private boolean saveFieldMappings(long operationId, List<SaaSFileInterpretRequestDto> fieldMappings) {
        int sortOrder = 1;
        for (SaaSFileInterpretRequestDto dto : fieldMappings) {
            SaaSFileFieldMapping fieldMapping = new SaaSFileFieldMapping();
            fieldMapping.setOperationRecordId(operationId);
            fieldMapping.setFieldEnglishName(dto.getFieldEnglishName());
            fieldMapping.setFieldChineseName(dto.getFieldChineseName());
            fieldMapping.setSortOrder(sortOrder++);
            fieldMapping.setFieldType(dto.getFieldType());
            fieldMapping.setIsPrimaryKey(dto.getIsPrimaryKey());
            fieldMapping.setIsRequired(dto.getIsRequired());
            fieldMapping.setDefaultValue(dto.getDefaultValue());
            fieldMapping.setFieldDescription(dto.getFieldDescription());
            fieldMapping.setValidateRule(dto.getValidateRule());
            fieldMapping.setTransformRule(dto.getTransformRule());
            fieldMapping.setRemark(dto.getRemark());
            fieldMapping.setStatus("ACTIVE");
            fieldMapping.setCreateDate(LocalDateTime.now());
            fieldMapping.setModifyDate(LocalDateTime.now());
            
            fieldMappingMapper.insert(fieldMapping);
        }
        return true;
    }

    private void updateOperationRecordCounts(long operationId) {
        List<SaaSFileInterpretRecord> records = interpretRecordRepository.getByOperationRecordId(operationId);
        
        int successCount = 0;
        int failCount = 0;
        
        for (SaaSFileInterpretRecord record : records) {
            if ("SUCCESS".equals(record.getBusinessProcessStatus())) {
                successCount++;
            } else if ("FAILED".equals(record.getBusinessProcessStatus())) {
                failCount++;
            }
        }
        
        FileOperationRecord operationRecord = operationRecordRepository.getById(operationId);
        if (operationRecord != null) {
            operationRecord.setSuccessCount(successCount);
            operationRecord.setFailCount(failCount);
            operationRecord.setProgressPercent(records.isEmpty() ? 100 : (successCount + failCount) * 100 / records.size());
            if (successCount + failCount >= records.size()) {
                operationRecord.setStatus("COMPLETED");
            }
            operationRecord.setModifyDate(LocalDateTime.now());
            operationRecordRepository.updateById(operationRecord);
        }
    }

    private FileOperationResponseDto buildOperationResponseDto(FileOperationRecord record) {
        return FileOperationResponseDto.build().clone(record);
    }

    private FileInterpretResponseDto buildInterpretResponseDto(SaaSFileInterpretRecord record) {
        return FileInterpretResponseDto.build().clone(record);
    }

    private List<FileFieldResponseDto> buildFieldResponseList(List<SaaSFileFieldMapping> fieldMappings) {
        if (fieldMappings == null) {
            return new ArrayList<>();
        }
        return fieldMappings.stream()
            .map(this::buildFieldResponseDto)
            .toList();
    }

    private FileFieldResponseDto buildFieldResponseDto(SaaSFileFieldMapping fieldMapping) {
        return FileFieldResponseDto.build().clone(fieldMapping);
    }

    private List<FileInterpretResponseDto> buildInterpretResponseList(List<SaaSFileInterpretRecord> records) {
        if (records == null) {
            return new ArrayList<>();
        }
        return records.stream()
            .map(this::buildInterpretResponseDto)
            .toList();
    }
}
