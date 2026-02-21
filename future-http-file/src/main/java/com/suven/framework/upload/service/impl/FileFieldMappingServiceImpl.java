package com.suven.framework.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.upload.dto.request.FileFieldRequestDto;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.repository.FileFieldMappingRepository;
import com.suven.framework.upload.service.FileFieldMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件字段映射服务实现
 *
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class FileFieldMappingServiceImpl implements FileFieldMappingService {

    @Autowired
    private FileFieldMappingRepository fieldMappingRepository;

    @Override
    public FileFieldMapping getById(long id) {
        log.debug("根据ID查询字段映射: id={}", id);
        return fieldMappingRepository.getById(id);
    }

    @Override
    public List<FileFieldMapping> getByBusinessFunctionId(long businessFunctionId) {
        log.debug("根据业务功能ID查询字段映射: businessFunctionId={}", businessFunctionId);
        return fieldMappingRepository.getByBusinessFunctionId(businessFunctionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileFieldMapping saveFieldMapping(FileFieldRequestDto requestDto) {
        log.info("保存字段映射: businessFunctionId={}, fieldEnglishName={}", 
                requestDto.getBusinessFunctionId(), requestDto.getFieldEnglishName());

        // 检查字段英文名是否已存在
        if (existsByFieldEnglishName(requestDto.getBusinessFunctionId(), requestDto.getFieldEnglishName())) {
            throw new RuntimeException("字段英文名已存在: " + requestDto.getFieldEnglishName());
        }

        FileFieldMapping mapping = new FileFieldMapping();
        mapping.setBusinessFunctionId(requestDto.getBusinessFunctionId());
        mapping.setFieldEnglishName(requestDto.getFieldEnglishName());
        mapping.setFieldChineseName(requestDto.getFieldChineseName());
        mapping.setSortOrder(requestDto.getSortOrder());
        mapping.setFieldType(requestDto.getFieldType());
        mapping.setFieldLength(requestDto.getFieldLength() != null ? requestDto.getFieldLength() : 0);
        mapping.setIsPrimaryKey(requestDto.getIsPrimaryKey() != null ? requestDto.getIsPrimaryKey() : 0);
        mapping.setIsRequired(requestDto.getIsRequired() != null ? requestDto.getIsRequired() : 0);
        mapping.setDefaultValue(requestDto.getDefaultValue());
        mapping.setFieldDescription(requestDto.getFieldDescription());
        mapping.setValidateRule(requestDto.getValidateRule());
        mapping.setTransformRule(requestDto.getTransformRule());
        mapping.setSampleValue(requestDto.getSampleValue());
        mapping.setStatus("ACTIVE");
        mapping.setRemark(requestDto.getRemark());
        mapping.setCreateDate(LocalDateTime.now());
        mapping.setModifyDate(LocalDateTime.now());
        mapping.setDeleted(0);

        fieldMappingRepository.save(mapping);
        
        log.info("保存字段映射成功: id={}", mapping.getId());
        return mapping;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileFieldMapping updateFieldMapping(FileFieldRequestDto requestDto) {
        log.info("更新字段映射: id={}", requestDto.getId());

        FileFieldMapping mapping = fieldMappingRepository.getById(requestDto.getId());
        if (mapping == null) {
            throw new RuntimeException("字段映射不存在: " + requestDto.getId());
        }

        // 如果修改了字段英文名，检查是否与其他字段冲突
        if (ObjectTrue.isNotEmpty(requestDto.getFieldEnglishName()) 
                && !requestDto.getFieldEnglishName().equals(mapping.getFieldEnglishName())) {
            if (existsByFieldEnglishName(mapping.getBusinessFunctionId(), requestDto.getFieldEnglishName())) {
                throw new RuntimeException("字段英文名已存在: " + requestDto.getFieldEnglishName());
            }
            mapping.setFieldEnglishName(requestDto.getFieldEnglishName());
        }

        if (ObjectTrue.isNotEmpty(requestDto.getFieldChineseName())) {
            mapping.setFieldChineseName(requestDto.getFieldChineseName());
        }
        if (requestDto.getSortOrder() != null) {
            mapping.setSortOrder(requestDto.getSortOrder());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getFieldType())) {
            mapping.setFieldType(requestDto.getFieldType());
        }
        if (requestDto.getFieldLength() != null) {
            mapping.setFieldLength(requestDto.getFieldLength());
        }
        if (requestDto.getIsPrimaryKey() != null) {
            mapping.setIsPrimaryKey(requestDto.getIsPrimaryKey());
        }
        if (requestDto.getIsRequired() != null) {
            mapping.setIsRequired(requestDto.getIsRequired());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getDefaultValue())) {
            mapping.setDefaultValue(requestDto.getDefaultValue());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getFieldDescription())) {
            mapping.setFieldDescription(requestDto.getFieldDescription());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getValidateRule())) {
            mapping.setValidateRule(requestDto.getValidateRule());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getTransformRule())) {
            mapping.setTransformRule(requestDto.getTransformRule());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getSampleValue())) {
            mapping.setSampleValue(requestDto.getSampleValue());
        }
        if (ObjectTrue.isNotEmpty(requestDto.getRemark())) {
            mapping.setRemark(requestDto.getRemark());
        }

        mapping.setModifyDate(LocalDateTime.now());
        fieldMappingRepository.updateById(mapping);
        
        log.info("更新字段映射成功: id={}", mapping.getId());
        return mapping;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFieldMapping(long id) {
        log.info("删除字段映射: id={}", id);

        FileFieldMapping mapping = fieldMappingRepository.getById(id);
        if (mapping == null) {
            log.warn("字段映射不存在: id={}", id);
            return false;
        }

        mapping.setDeleted(1);
        mapping.setStatus("DELETED");
        mapping.setModifyDate(LocalDateTime.now());
        
        return fieldMappingRepository.updateById(mapping);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSaveFieldMappings(long businessFunctionId, List<FileFieldRequestDto> requestDtoList) {
        log.info("批量保存字段映射: businessFunctionId={}, count={}", businessFunctionId, requestDtoList.size());

        // 先删除该业务功能下的所有字段映射
        deleteByBusinessFunctionId(businessFunctionId);

        // 保存新的字段映射
        int sortOrder = 1;
        for (FileFieldRequestDto requestDto : requestDtoList) {
            requestDto.setBusinessFunctionId(businessFunctionId);
            if (requestDto.getSortOrder() == null) {
                requestDto.setSortOrder(sortOrder++);
            }
            saveFieldMapping(requestDto);
        }

        log.info("批量保存字段映射成功");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteFieldMappings(List<Long> idList) {
        log.info("批量删除字段映射: count={}", idList.size());

        int count = 0;
        for (Long id : idList) {
            if (deleteFieldMapping(id)) {
                count++;
            }
        }
        
        return count;
    }

    @Override
    public boolean existsByFieldEnglishName(long businessFunctionId, String fieldEnglishName) {
        LambdaQueryWrapper<FileFieldMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileFieldMapping::getBusinessFunctionId, businessFunctionId);
        queryWrapper.eq(FileFieldMapping::getFieldEnglishName, fieldEnglishName);
        queryWrapper.eq(FileFieldMapping::getDeleted, 0);
        
        return fieldMappingRepository.count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByBusinessFunctionId(long businessFunctionId) {
        log.info("根据业务功能ID删除字段映射: businessFunctionId={}", businessFunctionId);

        List<FileFieldMapping> mappings = getByBusinessFunctionId(businessFunctionId);
        for (FileFieldMapping mapping : mappings) {
            mapping.setDeleted(1);
            mapping.setStatus("DELETED");
            mapping.setModifyDate(LocalDateTime.now());
            fieldMappingRepository.updateById(mapping);
        }

        return true;
    }
}
