package com.suven.framework.upload.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.service.FileParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * XLS/XLSX文件解析器
 * 
 * 功能：解析Excel格式文件（.xls, .xlsx），支持字段映射、数据验证等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service("xlsFileParser")
public class XLSFileParser implements FileParseService {

    /**
     * 默认跳过行数（表头）
     */
    private static final int DEFAULT_SKIP_ROWS = 1;

    @Override
    public FileParseResultDto parse(InputStream inputStream, String fileName, String fileType) {
        long startTime = System.currentTimeMillis();
        FileParseResultDto.FileParseResultDtoBuilder resultBuilder = FileParseResultDto.builder();
        resultBuilder.fileName(fileName);
        resultBuilder.fileType(fileType);

        try {
            // 使用EasyExcel读取文件
            List<Map<Integer, String>> allData = readExcelData(inputStream);
            
            if (ObjectTrue.isEmpty(allData)) {
                return resultBuilder
                        .success(false)
                        .errorMessage("文件为空或读取失败")
                        .message("解析失败")
                        .parseTimeMs(System.currentTimeMillis() - startTime)
                        .build();
            }

            // 解析每一行数据
            for (Map<Integer, String> row : allData) {
                Map<String, Object> dataRow = convertRowData(row);
                if (dataRow != null && !dataRow.isEmpty()) {
                    resultBuilder.rawDataRows().addDataRow(dataRow);
                } else {
                    resultBuilder.skipRows(1);
                }
            }

            return resultBuilder
                    .success(true)
                    .message("解析成功")
                    .parseTimeMs(System.currentTimeMillis() - startTime)
                    .build();

        } catch (Exception e) {
            log.error("XLS文件解析失败: {}", fileName, e);
            return resultBuilder
                    .success(false)
                    .errorMessage("解析异常: " + e.getMessage())
                    .message("解析失败")
                    .parseTimeMs(System.currentTimeMillis() - startTime)
                    .build();
        }
    }

    @Override
    public FileParseResultDto parseWithMapping(
            InputStream inputStream, 
            String fileName, 
            String fileType, 
            List<FileFieldMapping> fieldMappings) {
        long startTime = System.currentTimeMillis();
        FileParseResultDto.FileParseResultDtoBuilder resultBuilder = FileParseResultDto.builder();
        resultBuilder.fileName(fileName);
        resultBuilder.fileType(fileType);

        if (ObjectTrue.isEmpty(fieldMappings)) {
            return parse(inputStream, fileName, fileType);
        }

        try {
            // 使用EasyExcel读取文件
            List<Map<Integer, String>> allData = readExcelData(inputStream);
            
            if (ObjectTrue.isEmpty(allData)) {
                return resultBuilder
                        .success(false)
                        .errorMessage("文件为空或读取失败")
                        .message("解析失败")
                        .parseTimeMs(System.currentTimeMillis() - startTime)
                        .build();
            }

            // 跳过表头行
            int skipRows = DEFAULT_SKIP_ROWS;
            if (allData.size() <= skipRows) {
                return resultBuilder
                        .success(false)
                        .errorMessage("文件数据行数不足")
                        .message("解析失败")
                        .parseTimeMs(System.currentTimeMillis() - startTime)
                        .build();
            }

            // 解析数据行
            for (int i = skipRows; i < allData.size(); i++) {
                Map<Integer, String> row = allData.get(i);

                try {
                    Map<String, Object> dataRow = convertDataRowWithMapping(row, fieldMappings);
                    if (dataRow != null && !dataRow.isEmpty()) {
                        // 验证数据
                        if (validateDataRow(dataRow, fieldMappings)) {
                            resultBuilder.addDataRow(dataRow);
                        } else {
                            resultBuilder.skipRow("数据验证失败");
                        }
                    } else {
                        resultBuilder.skipRow("空行或无效数据");
                    }
                } catch (Exception e) {
                    log.warn("数据行转换失败，行号: {}, 错误: {}", i + 1, e.getMessage());
                    resultBuilder.markFailed("数据行转换异常: " + e.getMessage());
                }
            }

            return resultBuilder
                    .success(true)
                    .message("解析成功，共" + resultBuilder() + "行")
                    .parseTimeMs(System.currentTimeMillis() - startTime)
                    .build();

        } catch (Exception e) {
            log.error("XLS文件解析失败: {}", fileName, e);
            return resultBuilder
                    .success(false)
                    .errorMessage("解析异常: " + e.getMessage())
                    .message("解析失败")
                    .parseTimeMs(System.currentTimeMillis() - startTime)
                    .build();
        }
    }

    @Override
    public boolean isSupported(String fileName, String fileType) {
        if (ObjectTrue.isEmpty(fileName)) {
            return false;
        }
        String extension = getFileExtension(fileName).toLowerCase();
        if (ObjectTrue.isNotEmpty(fileType)) {
            return fileType.equalsIgnoreCase("xls") || 
                   fileType.equalsIgnoreCase("xlsx") ||
                   extension.equals("xls") || 
                   extension.equals("xlsx");
        }
        return "xls".equalsIgnoreCase(extension) || "xlsx".equalsIgnoreCase(extension);
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
            List<FileFieldMapping> fieldMappings) {
        if (ObjectTrue.isEmpty(rawData) || ObjectTrue.isEmpty(fieldMappings)) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();
        int index = 0;

        for (FileFieldMapping mapping : fieldMappings) {
            if (index >= rawData.size()) {
                break;
            }

            String value = rawData.get(index);
            String fieldName = mapping.getFieldEnglishName();
            String fieldType = mapping.getFieldType();

            // 转换值
            Object convertedValue = convertValue(value, fieldType);

            // 如果有默认值且值为空，使用默认值
            if (convertedValue == null || "".equals(convertedValue)) {
                String defaultValue = mapping.getDefaultValue();
                if (ObjectTrue.isNotEmpty(defaultValue)) {
                    convertedValue = convertValue(defaultValue, fieldType);
                }
            }

            result.put(fieldName, convertedValue);
            index++;
        }

        return result;
    }

    @Override
    public Object convertValue(String value, String fieldType) {
        if (ObjectTrue.isEmpty(value)) {
            return null;
        }

        if (ObjectTrue.isEmpty(fieldType)) {
            // 自动推断类型
            return inferType(value);
        }

        switch (fieldType.toUpperCase()) {
            case "STRING":
                return value;
            case "NUMBER":
            case "INTEGER":
            case "LONG":
                try {
                    if (value.contains(".")) {
                        return Double.parseDouble(value);
                    }
                    return Long.parseLong(value);
                } catch (NumberFormatException e) {
                    return value;
                }
            case "DOUBLE":
            case "FLOAT":
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    return value;
                }
            case "BOOLEAN":
                return "true".equalsIgnoreCase(value) || "1".equals(value) || "yes".equalsIgnoreCase(value);
            case "DATE":
            case "DATETIME":
                return value;
            default:
                return inferType(value);
        }
    }

    @Override
    public boolean validateDataRow(
            Map<String, Object> dataRow, 
            List<FileFieldMapping> fieldMappings) {
        if (ObjectTrue.isEmpty(dataRow) || ObjectTrue.isEmpty(fieldMappings)) {
            return false;
        }

        for (FileFieldMapping mapping : fieldMappings) {
            String fieldName = mapping.getFieldEnglishName();
            Object value = dataRow.get(fieldName);

            // 必填验证
            if (mapping.getIsRequired() == 1 && (value == null || "".equals(value))) {
                log.warn("字段验证失败: {} 为必填字段", fieldName);
                return false;
            }

            // 如果值为空且不是必填，跳过后续验证
            if (value == null || "".equals(value)) {
                continue;
            }

            // 格式验证
            String validateRule = mapping.getValidateRule();
            if (ObjectTrue.isNotEmpty(validateRule)) {
                if (!validateByRule(value.toString(), validateRule)) {
                    log.warn("字段格式验证失败: {}, 值: {}", fieldName, value);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 使用EasyExcel读取Excel数据
     * 
     * @param inputStream 输入流
     * @return 数据行列表
     */
    private List<Map<Integer, String>> readExcelData(InputStream inputStream) {
        List<Map<Integer, String>> allData = new ArrayList<>();
        
        try {
            EasyExcel.read(inputStream, new ReadListener<Map<Integer, String>>() {
                @Override
                public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
                    // 过滤空行
                    boolean hasData = false;
                    for (String value : rowData.values()) {
                        if (ObjectTrue.isNotEmpty(value) && !value.trim().isEmpty()) {
                            hasData = true;
                            break;
                        }
                    }
                    if (hasData) {
                        allData.add(rowData);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    log.info("Excel解析完成，共读取 {} 行数据", allData.size());
                }

                @Override
                public void onException(Exception exception, AnalysisContext context) {
                    log.error("Excel解析异常", exception);
                }
            })
            .extraRead(CellExtraTypeEnum.COMMENT) // 读取批注
            .extraRead(CellExtraTypeEnum.HYPERLINK) // 读取超链接
            .headRowNumber(0) // 不跳过表头行，在业务层处理
            .sheet()
            .doRead();
                
        } catch (Exception e) {
            log.error("读取Excel文件失败", e);
        }
        
        return allData;
    }

    /**
     * 转换行数据（无字段映射）
     * 
     * @param row 行数据
     * @return 转换后的数据Map
     */
    private Map<String, Object> convertRowData(Map<Integer, String> row) {
        if (ObjectTrue.isEmpty(row)) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();
        int index = 0;
        for (Map.Entry<Integer, String> entry : row.entrySet()) {
            result.put("column_" + index++, entry.getValue());
        }
        return result;
    }

    /**
     * 根据字段映射转换行数据
     * 
     * @param row 行数据
     * @param fieldMappings 字段映射列表
     * @return 转换后的数据Map
     */
    private Map<String, Object> convertDataRowWithMapping(
            Map<Integer, String> row, 
            List<FileFieldMapping> fieldMappings) {
        if (ObjectTrue.isEmpty(row) || ObjectTrue.isEmpty(fieldMappings)) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();
        
        for (FileFieldMapping mapping : fieldMappings) {
            int columnIndex = mapping.getSortOrder() - 1; // 排编号从1开始，索引从0开始
            String value = row.get(columnIndex);
            String fieldName = mapping.getFieldEnglishName();
            String fieldType = mapping.getFieldType();

            // 转换值
            Object convertedValue = convertValue(value, fieldType);

            // 如果有默认值且值为空，使用默认值
            if ((convertedValue == null || "".equals(convertedValue)) && ObjectTrue.isNotEmpty(mapping.getDefaultValue())) {
                convertedValue = convertValue(mapping.getDefaultValue(), fieldType);
            }

            result.put(fieldName, convertedValue);
        }

        return result;
    }

    /**
     * 自动推断类型
     * 
     * @param value 原始值
     * @return 推断后的值
     */
    private Object inferType(String value) {
        if (ObjectTrue.isEmpty(value)) {
            return null;
        }

        // 检查数字
        try {
            if (value.contains(".")) {
                Double.parseDouble(value);
                return Double.parseDouble(value);
            } else {
                Long.parseLong(value);
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            // 不是数字
        }

        // 检查布尔值
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }

        return value;
    }

    /**
     * 根据规则验证值
     * 
     * @param value 值
     * @param validateRule 验证规则
     * @return 是否通过验证
     */
    private boolean validateByRule(String value, String validateRule) {
        try {
            return value.matches(validateRule);
        } catch (Exception e) {
            log.warn("验证规则执行失败: {}", validateRule, e);
            return true; // 验证规则异常时默认通过
        }
    }
}
