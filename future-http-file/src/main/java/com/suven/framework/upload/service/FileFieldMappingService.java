package com.suven.framework.upload.service;

import com.suven.framework.upload.dto.request.FileFieldRequestDto;
import com.suven.framework.upload.entity.FileFieldMapping;

import java.util.List;

/**
 * 文件字段映射服务接口
 * 
 * 负责业务字段映射配置的增删改查管理
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
public interface FileFieldMappingService {

    /**
     * 根据ID获取字段映射
     *
     * @param id 字段映射ID
     * @return FileFieldMapping 字段映射记录
     */
    FileFieldMapping getById(long id);

    /**
     * 根据业务功能ID获取字段映射列表
     *
     * @param businessFunctionId 业务功能ID
     * @return List<FileFieldMapping> 字段映射列表
     */
    List<FileFieldMapping> getByBusinessFunctionId(long businessFunctionId);

    /**
     * 保存字段映射
     *
     * @param requestDto 字段映射请求DTO
     * @return FileFieldMapping 保存后的字段映射
     */
    FileFieldMapping saveFieldMapping(FileFieldRequestDto requestDto);

    /**
     * 更新字段映射
     *
     * @param requestDto 字段映射请求DTO
     * @return FileFieldMapping 更新后的字段映射
     */
    FileFieldMapping updateFieldMapping(FileFieldRequestDto requestDto);

    /**
     * 删除字段映射
     *
     * @param id 字段映射ID
     * @return boolean 是否成功
     */
    boolean deleteFieldMapping(long id);

    /**
     * 批量保存字段映射
     * 会先删除该业务功能下的所有字段映射，再保存新的
     *
     * @param businessFunctionId 业务功能ID
     * @param requestDtoList 字段映射请求DTO列表
     * @return boolean 是否成功
     */
    boolean batchSaveFieldMappings(long businessFunctionId, List<FileFieldRequestDto> requestDtoList);

    /**
     * 批量删除字段映射
     *
     * @param idList 字段映射ID列表
     * @return int 删除数量
     */
    int batchDeleteFieldMappings(List<Long> idList);

    /**
     * 检查字段英文名是否已存在
     *
     * @param businessFunctionId 业务功能ID
     * @param fieldEnglishName 字段英文名
     * @return boolean true-已存在, false-不存在
     */
    boolean existsByFieldEnglishName(long businessFunctionId, String fieldEnglishName);

    /**
     * 根据业务功能ID删除所有字段映射
     *
     * @param businessFunctionId 业务功能ID
     * @return boolean 是否成功
     */
    boolean deleteByBusinessFunctionId(long businessFunctionId);
}
