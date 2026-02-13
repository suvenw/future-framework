package com.suven.framework.upload.service;

import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.FileFieldMapping;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件解析服务接口
 * 
 * 功能：定义文件解析的通用接口，支持XLS、CSV等格式的文件解析
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
public interface FileParseService {

    /**
     * 支持的文件类型
     */
    String SUPPORTED_TYPES = "xls,xlsx,csv";

    /**
     * 解析文件
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return FileParseResultDto 解析结果
     */
    FileParseResultDto parse(InputStream inputStream, String fileName, String fileType);

    /**
     * 解析文件并映射字段
     * 
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param fieldMappings 字段映射列表
     * @return FileParseResultDto 解析结果
     */
    FileParseResultDto parseWithMapping(
            InputStream inputStream, 
            String fileName, 
            String fileType, 
            List<FileFieldMapping> fieldMappings);

    /**
     * 验证文件格式
     * 
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return boolean 是否支持
     */
    boolean isSupported(String fileName, String fileType);

    /**
     * 获取文件扩展名
     * 
     * @param fileName 文件名称
     * @return 扩展名
     */
    String getFileExtension(String fileName);

    /**
     * 根据字段映射转换数据
     * 
     * @param rawData 原始数据行
     * @param fieldMappings 字段映射列表
     * @return 转换后的数据Map
     */
    Map<String, Object> convertDataRow(
            List<String> rawData, 
            List<FileFieldMapping> fieldMappings);

    /**
     * 转换单个值
     * 
     * @param value 原始值
     * @param fieldType 目标字段类型
     * @return 转换后的值
     */
    Object convertValue(String value, String fieldType);

    /**
     * 验证数据行
     * 
     * @param dataRow 数据行
     * @param fieldMappings 字段映射列表
     * @return 验证结果 (true: 验证通过, false: 验证失败)
     */
    boolean validateDataRow(
            Map<String, Object> dataRow, 
            List<FileFieldMapping> fieldMappings);
}
