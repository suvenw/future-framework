package com.suven.framework.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.DataSourceModuleName;

import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.entity.FileInterpretRecord;
import com.suven.framework.upload.entity.FileUpload;
import com.suven.framework.upload.mapper.FileUploadMapper;
import com.suven.framework.upload.repository.FileFieldMappingRepository;
import com.suven.framework.upload.repository.FileInterpretRecordRepository;
import com.suven.framework.upload.repository.FileUploadRepository;
import com.suven.framework.upload.service.FileParseService;
import com.suven.framework.upload.service.FileUploadParseService;
import com.suven.framework.upload.vo.request.FileInterpretPageRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  文件上传与解析服务实现
 * 
 * 功能：实现文件上传、解析、保存的完整业务流程
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class FileUploadParseServiceImpl implements FileUploadParseService {

    @Autowired
    private FileParseService fileParseService;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private FileInterpretRecordRepository interpretRecordRepository;

    @Autowired
    private FileFieldMappingRepository fieldMappingRepository;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    private static final String UPLOAD_BATCH_NO_PREFIX = "UPLOAD_";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileUpload uploadAndParse(
            MultipartFile file,
            String appId,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl) {
        log.info("开始上传并解析文件: fileName={}, appId={}, businessUniqueCode={}", 
                file.getOriginalFilename(), appId, businessUniqueCode);

        // 验证文件
        if (file.isEmpty()) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "上传文件不能为空");
        }

        try {
            // 保存文件上传记录
            FileUpload fileUpload = createFileUploadRecord(
                    file, appId, businessUniqueCode, needCallback, callbackUrl);

            // 解析文件
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String fileType = getFileExtension(fileName);

             FileInterpretRecord interpretRecord = parseAndSave(
                    inputStream, fileName, fileType,
                    fileUpload.getId(), businessUniqueCode,
                    needCallback, callbackUrl);

            if (interpretRecord != null) {
                fileUpload.setInterpretFlag(1);
                fileUploadRepository.updateById(fileUpload);
            }

            log.info("文件上传并解析完成: fileUploadId={}", fileUpload.getId());
            return fileUpload;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "文件上传失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileUpload uploadAndParseWithMapping(
            MultipartFile file,
            String appId,
            String businessUniqueCode,
            List<FileFieldMapping> fieldMappings,
            int needCallback,
            String callbackUrl) {
        log.info("开始上传并解析文件(带字段映射): fileName={}, appId={}, fieldMappings={}", 
                file.getOriginalFilename(), appId, 
                ObjectTrue.isEmpty(fieldMappings) ? 0 : fieldMappings.size());

        // 验证文件
        if (file.isEmpty()) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "上传文件不能为空");
        }

        try {
            // 保存文件上传记录
            FileUpload fileUpload = createFileUploadRecord(
                    file, appId, businessUniqueCode, needCallback, callbackUrl);

            // 解析文件（带字段映射）
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String fileType = getFileExtension(fileName);

            // 如果有字段映射，先保存映射
            if (ObjectTrue.isNotEmpty(fieldMappings)) {
                saveFieldMappings(fileUpload.getId(), fieldMappings);
            }

            // 解析并保存
             FileInterpretRecord interpretRecord = parseAndSaveWithMapping(
                    inputStream, fileName, fileType,
                    fileUpload.getId(), businessUniqueCode,
                    fieldMappings, needCallback, callbackUrl);

            if (interpretRecord != null) {
                fileUpload.setInterpretFlag(1);
                fileUploadRepository.updateById(fileUpload);
            }

            log.info("文件上传并解析完成: fileUploadId={}", fileUpload.getId());
            return fileUpload;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public  FileInterpretRecord parseUploadedFile(long fileUploadId, int needCallback, String callbackUrl) {
        log.info("解析已上传的文件: fileUploadId={}", fileUploadId);

        FileUpload fileUpload = fileUploadRepository.getById(fileUploadId);
        if (fileUpload == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "文件上传记录不存在");
        }

        return null; // 需要从文件存储中获取文件流
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  FileInterpretRecord parseAndSave(
            InputStream inputStream,
            String fileName,
            String fileType,
            long fileUploadId,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl) {
        log.info("解析并保存记录: fileName={}, fileUploadId={}", fileName, fileUploadId);

        try {
            // 解析文件
             FileParseResultDto parseResult = fileParseService.parse(inputStream, fileName, fileType);
            if (!parseResult.isSuccess()) {
                log.error("文件解析失败: {}", parseResult.getErrorMessage());
                return null;
            }

            // 生成解释标识
            String interpretKey = generateInterpretKey(fileUploadId);

            // 保存解释记录
             FileInterpretRecord interpretRecord = saveInterpretRecord(
                    fileUploadId, interpretKey, businessUniqueCode, parseResult, needCallback, callbackUrl);

            // 批量保存解释明细
            int savedCount = batchSaveInterpretDetails(
                    fileUploadId, parseResult, interpretKey, businessUniqueCode);

            log.info("解析并保存完成: interpretId={}, savedCount={}", interpretRecord.getId(), savedCount);
            return interpretRecord;

        } catch (Exception e) {
            log.error("解析并保存失败: fileName={}", fileName, e);
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "解析失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  FileInterpretRecord parseAndSaveWithMapping(
            InputStream inputStream,
            String fileName,
            String fileType,
            long fileUploadId,
            String businessUniqueCode,
            List<FileFieldMapping> fieldMappings,
            int needCallback,
            String callbackUrl) {
        log.info("解析并保存记录(带字段映射): fileName={}, fileUploadId={}", fileName, fileUploadId);

        try {
            // 解析文件（带字段映射）
             FileParseResultDto parseResult = fileParseService.parseWithMapping(
                    inputStream, fileName, fileType, fieldMappings);
            if (!parseResult.isSuccess()) {
                log.error("文件解析失败: {}", parseResult.getErrorMessage());
                return null;
            }

            // 生成解释标识
            String interpretKey = generateInterpretKey(fileUploadId);

            // 保存解释记录
             FileInterpretRecord interpretRecord = saveInterpretRecord(
                    fileUploadId, interpretKey, businessUniqueCode, parseResult, needCallback, callbackUrl);

            // 批量保存解释明细
            int savedCount = batchSaveInterpretDetails(
                    fileUploadId, parseResult, interpretKey, businessUniqueCode);

            log.info("解析并保存完成: interpretId={}, savedCount={}", interpretRecord.getId(), savedCount);
            return interpretRecord;

        } catch (Exception e) {
            log.error("解析并保存失败: fileName={}", fileName, e);
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "解析失败: " + e.getMessage());
        }
    }

    @Override
    public FileUpload getFileUpload(long id) {
        return fileUploadRepository.getById(id);
    }

    /**
     * 分页查询文件上传记录
     *
     * @param requestDto 查询条件
     * @param pager      分页参数
     * @return PageResult<FileUpload>
     */

    @Override
    public PageResult<FileUpload> pageQueryFileUpload(FileUpload requestDto, Pager<FileInterpretRecord> pager) {
        log.info("分页查询文件上传记录: pageNo={}, pageSize={}", pager.getPageNo(), pager.getPageSize());

        LambdaQueryWrapper<FileUpload> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(requestDto.getBusinessUniqueCode())) {
            queryWrapper.eq(FileUpload::getBusinessUniqueCode, requestDto.getBusinessUniqueCode());
        }
        if (StringUtils.isNotBlank(requestDto.getStatus())) {
            queryWrapper.eq(FileUpload::getStatus, requestDto.getStatus());
        }
        if (StringUtils.isNotBlank(requestDto.getFileSourceName())) {
            queryWrapper.like(FileUpload::getFileSourceName, requestDto.getFileSourceName());
        }
        queryWrapper.eq(FileUpload::getDeleted, 0);
        queryWrapper.orderByDesc(FileUpload::getId);

        Page<FileUpload> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileUpload> pageResult = fileUploadMapper.selectPage(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());

        PageResult<FileUpload> result = new PageResult<>();
        result.setTotal(pageResult.getTotal());
        result.setList(pageResult.getRecords());

        return result;
    }

    @Override
    public  FileInterpretRecord getInterpretRecord(long id) {
        return interpretRecordRepository.getById(id);
    }

    @Override
    public List< FileInterpretRecord> getInterpretRecordsByFileUploadId(long fileUploadId) {
        return interpretRecordRepository.getByFileUploadId(fileUploadId);
    }

    @Override
    public PageResult< FileInterpretRecord> pageQueryInterpretByBusiness(FileInterpretPageRequestVo requestVo) {
        log.info("按业务唯一码分页查询解释记录: businessUniqueCode={}", requestVo.getBusinessUniqueCode());

        String businessUniqueCode = requestVo.getBusinessUniqueCode();
        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        List< FileInterpretRecord> records = interpretRecordRepository.getByBusinessUniqueCode(businessUniqueCode);

        PageResult< FileInterpretRecord> result = new PageResult<>();
        result.setTotal(records.size());

        int pageNo = requestVo.getPageNo() <= 0 ? 1 : requestVo.getPageNo();
        int pageSize = requestVo.getPageSize() <= 0 ? 20 : requestVo.getPageSize();
        int fromIndex = (pageNo - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, records.size());

        if (fromIndex < records.size()) {
            result.setList(records.subList(fromIndex, toIndex));
        } else {
            result.setList(new ArrayList<>());
        }

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean writeBackProcessResult(
            long interpretRecordId,
            String businessUniqueCode,
            String processStatus,
            String processResult,
            String exceptionInfo) {
        log.info("回写处理结果: interpretRecordId={}, processStatus={}", interpretRecordId, processStatus);

         FileInterpretRecord record = interpretRecordRepository.getById(interpretRecordId);
        if (record == null) {
            log.warn("解释记录不存在: interpretRecordId={}", interpretRecordId);
            return false;
        }

        // 验证业务唯一码
        if (!record.getBusinessUniqueCode().equals(businessUniqueCode)) {
            log.warn("业务唯一码不匹配");
            return false;
        }

//        record.setBusinessProcessStatus(processStatus);
//        record.setBusinessProcessResult(processResult);
//        record.setBusinessExceptionInfo(exceptionInfo);
        record.setBusinessProcessTime(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());

        return interpretRecordRepository.updateById(record);
    }

    @Override
    public boolean isParseSupported(String fileName, String fileType) {
        return fileParseService.isSupported(fileName, fileType);
    }

    // ==================== 私有方法 ====================

    /**
     * 创建文件上传记录
     */
    private FileUpload createFileUploadRecord(
            MultipartFile file,
            String appId,
            String businessUniqueCode,
            int needCallback,
            String callbackUrl) {

        String fileName = file.getOriginalFilename();
        String fileType = getFileExtension(fileName);

        FileUpload fileUpload = new FileUpload();
        fileUpload.setBusinessUniqueCode(businessUniqueCode);
        fileUpload.setUploadBatchNo(UPLOAD_BATCH_NO_PREFIX + System.currentTimeMillis());
        fileUpload.setAppId(appId);
        fileUpload.setFileSourceName(fileName);
        fileUpload.setFileType(fileType);
        fileUpload.setFileSize(file.getSize());
        fileUpload.setInterpretFlag(0);
        fileUpload.setInterpretStatus("PENDING");
        fileUpload.setStatus("PROCESSING");
        fileUpload.setProgressPercent(0);
        fileUpload.setNeedCallback(needCallback);
        fileUpload.setCallbackUrl(callbackUrl);
        fileUpload.setCallbackStatus(needCallback == 1 ? "PENDING" : null);
        fileUpload.setBusinessProcessStatus("PENDING");
        fileUpload.setCreateDate(LocalDateTime.now());
        fileUpload.setModifyDate(LocalDateTime.now());
        fileUploadRepository.save(fileUpload);
        return fileUpload;
    }

    /**
     * 保存字段映射
     */
    private void saveFieldMappings(long fileUploadId, List< FileFieldMapping> fieldMappings) {
        int sortOrder = 1;
        for ( FileFieldMapping mapping : fieldMappings) {
             FileFieldMapping newMapping = new  FileFieldMapping();
            newMapping.setBusinessFunctionId(fileUploadId);
            newMapping.setFieldEnglishName(mapping.getFieldEnglishName());
            newMapping.setFieldChineseName(mapping.getFieldChineseName());
            newMapping.setSortOrder(sortOrder++);
            newMapping.setFieldType(mapping.getFieldType());
            newMapping.setIsPrimaryKey(mapping.getIsPrimaryKey());
            newMapping.setIsRequired(mapping.getIsRequired());
            newMapping.setDefaultValue(mapping.getDefaultValue());
            newMapping.setFieldDescription(mapping.getFieldDescription());
            newMapping.setValidateRule(mapping.getValidateRule());
            newMapping.setTransformRule(mapping.getTransformRule());
            newMapping.setStatus("ACTIVE");
            newMapping.setCreateDate(LocalDateTime.now());
            newMapping.setModifyDate(LocalDateTime.now());

            fieldMappingRepository.save(newMapping);
        }
    }

    /**
     * 生成解释标识
     */
    private String generateInterpretKey(long fileUploadId) {
        return "INTERPRET_" + fileUploadId + "_" + System.currentTimeMillis();
    }

    /**
     * 保存解释记录
     */
    private  FileInterpretRecord saveInterpretRecord(
            long fileUploadId,
            String interpretKey,
            String businessUniqueCode,
             FileParseResultDto parseResult,
            int needCallback,
            String callbackUrl) {

         FileInterpretRecord record = new  FileInterpretRecord();
        record.setFileUploadId(fileUploadId);
        record.setBusinessUniqueCode(businessUniqueCode);
//        record.setInterpretKey(interpretKey);
//        record.setInterpretStatus(parseResult.isSuccess() ? "COMPLETED" : "FAILED");
//        record.setInterpretProgress(100);
//        record.setTotalCount(parseResult.getTotalRows());
//        record.setSuccessCount(parseResult.getSuccessRows());
//        record.setFailCount(parseResult.getFailRows());
//        record.setSkipCount(parseResult.getSkipRows());
//        record.setNeedCallback(needCallback);
//        record.setCallbackUrl(callbackUrl);
//        record.setCallbackStatus(needCallback == 1 ? "PENDING" : null);
//        record.setBusinessProcessStatus("PENDING");
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());
        interpretRecordRepository.save(record);
        return record;
    }

    /**
     * 批量保存解释明细
     */
    private int batchSaveInterpretDetails(
            long fileUploadId,
             FileParseResultDto parseResult,
            String interpretKey,
            String businessUniqueCode) {

        int savedCount = 0;
        List<Map<String, Object>> dataRows = parseResult.getDataRows();

        for (int i = 0; i < dataRows.size(); i++) {
            try {
                Map<String, Object> rowData = dataRows.get(i);
                String interpretInfo = JSON.toJSONString(rowData);

                 FileInterpretRecord detailRecord = new  FileInterpretRecord();
                detailRecord.setFileUploadId(fileUploadId);

                detailRecord.setBusinessUniqueCode(businessUniqueCode);
//                detailRecord.setInterpretKey(interpretKey);
//                detailRecord.setInterpretInfo(interpretInfo);
//                detailRecord.setInterpretStatus("COMPLETED");
//                detailRecord.setSuccessCount(1);
//                detailRecord.setNeedCallback(0);
                detailRecord.setCreateDate(LocalDateTime.now());
                detailRecord.setModifyDate(LocalDateTime.now());

                interpretRecordRepository.save(detailRecord);
                savedCount++;

            } catch (Exception e) {
                log.warn("保存解释记录明细失败: rowIndex={}", i, e);
            }
        }

        return savedCount;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (ObjectTrue.isEmpty(fileName)) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
}
