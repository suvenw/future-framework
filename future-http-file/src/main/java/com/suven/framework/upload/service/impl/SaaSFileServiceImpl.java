package com.suven.framework.upload.service.impl;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.upload.dto.request.SaaSFileRequestDto;
import com.suven.framework.upload.dto.response.SaaSFileResponseDto;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.entity.FileUploadStorage;
import com.suven.framework.upload.mapper.FileUploadStorageMapper;
import com.suven.framework.upload.service.FileUploadStorageService;
import com.suven.framework.upload.service.SaaSFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * SaaS平台文件上传下载服务实现
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class SaaSFileServiceImpl implements SaaSFileService {

    @Autowired
    private FileUploadStorageService fileUploadStorageService;

    @Autowired
    private FileUploadStorageMapper fileUploadStorageMapper;

    /**
     * 上传文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaaSFileResponseDto uploadFile(SaaSFileRequestDto requestDto, MultipartFile file) {
        log.info("SaaS文件上传开始, 文件名: {}, 大小: {}", file.getOriginalFilename(), file.getSize());
        
        try {
            // 构建FileUploadStorage实体
            FileUploadStorage fileUploadStorage = buildFileUploadStorageFromRequest(requestDto, file);
            
            // 调用文件存储服务上传文件
            FileUploadStorage savedStorage = fileUploadStorageService.uploadFile(fileUploadStorage, file.getInputStream());
            
            // 构建响应
            SaaSFileResponseDto responseDto = buildResponseFromStorage(savedStorage);
            responseDto.setDuplicateUpload(false);
            
            log.info("SaaS文件上传成功, 文件ID: {}", savedStorage.getId());
            return responseDto;
        } catch (Exception e) {
            log.error("SaaS文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    @Override
    public SaaSFileResponseDto downloadFile(SaaSFileRequestDto requestDto) {
        log.info("SaaS文件下载开始, 文件ID: {}", requestDto.getFileUploadStorageId());
        
        FileUploadStorage storage = fileUploadStorageService.getFileUploadStorageById(requestDto.getFileUploadStorageId());
        if (storage == null) {
            throw new RuntimeException("文件不存在");
        }
        
        SaaSFileResponseDto responseDto = buildResponseFromStorage(storage);
        
        // 如果需要生成临时URL
        if (requestDto.getGenerateTempUrl() == 1) {
            String tempUrl = generateTempUrl(requestDto.getFileUploadStorageId(), requestDto.getExpirationTime());
            responseDto.setTempAccessUrl(tempUrl);
        }
        
        log.info("SaaS文件下载成功, 文件ID: {}", requestDto.getFileUploadStorageId());
        return responseDto;
    }

    /**
     * 生成大数据文件(调用第三方接口)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaaSFileResponseDto generateFile(SaaSFileRequestDto requestDto) {
        log.info("SaaS大数据文件生成开始, API地址: {}", requestDto.getThirdPartyApiUrl());
        
        try {
            // 调用第三方接口获取数据并生成文件
            FileUploadStorage storage = fileUploadStorageService.generateFileFromThirdParty(
                requestDto.getThirdPartyApiUrl(),
                requestDto.getThirdPartyApiMethod(),
                requestDto.getThirdPartyApiHeaders(),
                requestDto.getThirdPartyApiParams(),
                requestDto.getFileTypeForGenerate(),
                requestDto.getFileNamePrefix(),
                requestDto.getInterpretData()
            );
            
            SaaSFileResponseDto responseDto = buildResponseFromStorage(storage);
            responseDto.setGenerateStatus(1); // 生成成功
            responseDto.setProgressPercent(100);
            
            log.info("SaaS大数据文件生成成功, 文件ID: {}", storage.getId());
            return responseDto;
        } catch (Exception e) {
            log.error("SaaS大数据文件生成失败", e);
            SaaSFileResponseDto responseDto = new SaaSFileResponseDto();
            responseDto.setGenerateStatus(2); // 生成失败
            responseDto.setErrorMessage(e.getMessage());
            return responseDto;
        }
    }

    /**
     * 查询文件列表
     */
    @Override
    public List<SaaSFileResponseDto> queryFileList(SaaSFileRequestDto requestDto) {
        log.info("SaaS文件列表查询开始, AppID: {}", requestDto.getAppId());
        
        List<FileUploadStorage> storageList = fileUploadStorageService.getFileUploadStorageListByQuery(
            null, // queryEnum
            requestDto
        );
        
        return storageList.stream()
            .map(this::buildResponseFromStorage)
            .toList();
    }

    /**
     * 分页查询文件列表
     */
    @Override
    public PageResult<SaaSFileResponseDto> queryFilePage(SaaSFileRequestDto requestDto, Pager pager) {
        log.info("SaaS文件分页查询开始, AppID: {}, 页码: {}, 页大小: {}", 
            requestDto.getAppId(), pager.getPageNo(), pager.getPageSize());
        
        PageResult<FileUploadStorage> pageResult = fileUploadStorageService.getFileUploadStorageByNextPage(null, pager);
        
        PageResult<SaaSFileResponseDto> result = new PageResult<>();
        result.setTotal(pageResult.getTotal());
        result.setList(pageResult.getList().stream()
            .map(this::buildResponseFromStorage)
            .toList());
        
        return result;
    }

    /**
     * 获取文件详情
     */
    @Override
    public SaaSFileResponseDto getFileDetail(long fileUploadStorageId) {
        log.info("SaaS文件详情查询开始, 文件ID: {}", fileUploadStorageId);
        
        FileUploadStorage storage = fileUploadStorageService.getFileUploadStorageById(fileUploadStorageId);
        if (storage == null) {
            throw new RuntimeException("文件不存在");
        }
        
        return buildResponseFromStorage(storage);
    }

    /**
     * 删除文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFile(long fileUploadStorageId) {
        log.info("SaaS文件删除开始, 文件ID: {}", fileUploadStorageId);
        
        int result = fileUploadStorageService.delFileUploadStorageByIds(List.of(fileUploadStorageId));
        
        log.info("SaaS文件删除完成, 文件ID: {}, 结果: {}", fileUploadStorageId, result > 0);
        return result > 0;
    }

    /**
     * 批量删除文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteFiles(List<Long> fileUploadStorageIds) {
        log.info("SaaS文件批量删除开始, 文件数量: {}", fileUploadStorageIds.size());
        
        int result = fileUploadStorageService.delFileUploadStorageByIds(fileUploadStorageIds);
        
        log.info("SaaS文件批量删除完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 根据ID列表获取文件列表
     */
    @Override
    public List<SaaSFileResponseDto> getFileListByIdList(List<Long> idList) {
        log.info("SaaS文件列表查询开始, ID数量: {}", idList.size());
        
        return fileUploadStorageMapper.selectBatchIds(idList).stream()
            .map(this::buildResponseFromStorage)
            .toList();
    }

    /**
     * 生成临时访问URL
     */
    @Override
    public String generateTempUrl(long fileUploadStorageId, int expirationTime) {
        log.info("SaaS临时URL生成开始, 文件ID: {}, 有效期: {}秒", fileUploadStorageId, expirationTime);
        
        return fileUploadStorageService.buildTempAccessUrl(fileUploadStorageId, expirationTime);
    }

    /**
     * 构建FileUploadStorage实体
     */
    private FileUploadStorage buildFileUploadStorageFromRequest(SaaSFileRequestDto requestDto, MultipartFile file) {
        FileUploadStorage storage = new FileUploadStorage();
        storage.setAppId(requestDto.getAppId());
        storage.setClientId(Long.parseLong(requestDto.getClientId()));
        storage.setUseBusinessId(requestDto.getUseBusinessId());
        storage.setFileSourceName(file.getOriginalFilename());
        storage.setFileType(getFileExtension(file.getOriginalFilename()));
        storage.setFileSize(file.getSize());
        storage.setInterpretData(requestDto.getInterpretData());
        
        if (StringUtils.isNotBlank(requestDto.getIdempotent())) {
            storage.setIdempotent(Long.parseLong(requestDto.getIdempotent()));
        }
        
        return storage;
    }

    /**
     * 构建响应DTO
     */
    private SaaSFileResponseDto buildResponseFromStorage(FileUploadStorage storage) {
        SaaSFileResponseDto responseDto = new SaaSFileResponseDto();
        responseDto.setFileUploadStorageId(storage.getId());
        responseDto.setFileSourceName(storage.getFileSourceName());
        responseDto.setFileType(storage.getFileType());
        responseDto.setFileSize(storage.getFileSize());
        responseDto.setFileMd5(storage.getFileMd5());
        responseDto.setFileAccessUrl(storage.getFileAccessUrl());
        responseDto.setDuplicateUpload(storage.getIdempotent() > 0);
        responseDto.setCreateDate(storage.getCreateDate());
        return responseDto;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
