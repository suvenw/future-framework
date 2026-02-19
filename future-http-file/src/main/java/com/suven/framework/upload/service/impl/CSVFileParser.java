package com.suven.framework.upload.service.impl;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.upload.dto.response.FileParseResultDto;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.service.FileParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

/**
 * CSV文件解析器
 * 
 * 功能：解析CSV格式文件，支持分隔符配置、字符编码转换、字段映射等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service("csvFileParser")
public class CSVFileParser implements FileParseService {

    /**
     * 默认分隔符
     */
    private static final char DEFAULT_DELIMITER = ',';

    /**
     * 默认字符编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 默认跳过行数（表头）
     */
    private static final int DEFAULT_SKIP_ROWS = 1;

    /**
     * 数字正则
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    /**
     * 日期正则
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}(\\s+\\d{2}:\\d{2}:\\d{2})?$");

    @Override
    public FileParseResultDto parse(InputStream inputStream, String fileName, String fileType) {
        long startTime = System.currentTimeMillis();

        try {
            // 读取CSV文件
            String errorMessage = "文件为空或读取失败";
            List<List<String>> allRows = readCSVFile(inputStream);
            if (ObjectTrue.isEmpty(allRows)) {
                FileParseResultDto fail = FileParseResultDto.fail(fileType, fileName, errorMessage);
                fail.setParseTimeMs(System.currentTimeMillis() - startTime);
                return fail;
            }

            FileParseResultDto resultDto = FileParseResultDto.build().success(fileType, fileName);
            
            // 解析每一行数据（跳过表头）
            int skipRows = DEFAULT_SKIP_ROWS;
            for (int i = skipRows; i < allRows.size(); i++) {
                List<String> row = allRows.get(i);
                resultDto.addRawDataRow(row);
                
                // 将原始行数据转换为Map（使用索引作为key）
                Map<String, Object> dataRow = convertRawRowToMap(row);
                if (dataRow != null && !dataRow.isEmpty()) {
                    resultDto.addDataRow(dataRow);
                } else {
                    resultDto.skipRow("空行或无效数据");
                }
            }

            // 设置解析耗时（只在最后设置一次）
            resultDto.setParseTimeMs(System.currentTimeMillis() - startTime);
            resultDto.setMessage("解析成功，共" + resultDto.getTotalRows() + "行");
            return resultDto;

        } catch (Exception e) {
            log.error("CSV文件解析失败: {}", fileName, e);
            String errorMessage = "解析异常: " + e.getMessage();
            FileParseResultDto fail = FileParseResultDto.fail(fileType, fileName, errorMessage);
            fail.setParseTimeMs(System.currentTimeMillis() - startTime);
            return fail;
        }
    }

    @Override
    public FileParseResultDto parseWithMapping(
            InputStream inputStream, 
            String fileName, 
            String fileType, 
            List<FileFieldMapping> fieldMappings) {
        long startTime = System.currentTimeMillis();

        if (ObjectTrue.isEmpty(fieldMappings)) {
            return parse(inputStream, fileName, fileType);
        }

        try {
            // 读取CSV文件
            List<List<String>> allRows = readCSVFile(inputStream);
            if (ObjectTrue.isEmpty(allRows)) {
                FileParseResultDto fail = FileParseResultDto.fail(fileType, fileName, "文件为空或读取失败");
                fail.setParseTimeMs(System.currentTimeMillis() - startTime);
                return fail;
            }

            FileParseResultDto resultDto = FileParseResultDto.build().success(fileType, fileName);

            // 跳过表头行
            int skipRows = DEFAULT_SKIP_ROWS;
            if (allRows.size() <= skipRows) {
                FileParseResultDto fail = FileParseResultDto.fail(fileType, fileName, "文件数据行数不足");
                fail.setParseTimeMs(System.currentTimeMillis() - startTime);
                return fail;
            }

            // 解析数据行
            for (int i = skipRows; i < allRows.size(); i++) {
                List<String> row = allRows.get(i);
                resultDto.addRawDataRow(row);

                try {
                    Map<String, Object> dataRow = convertDataRow(row, fieldMappings);
                    if (dataRow != null && !dataRow.isEmpty()) {
                        // 验证数据
                        if (validateDataRow(dataRow, fieldMappings)) {
                            resultDto.addDataRow(dataRow);
                        } else {
                            resultDto.skipRow("数据验证失败");
                        }
                    } else {
                        resultDto.skipRow("空行或无效数据");
                    }
                } catch (Exception e) {
                    log.warn("数据行转换失败，行号: {}, 错误: {}", i + 1, e.getMessage());
                    resultDto.markFailed("数据行转换异常: " + e.getMessage());
                }
            }

            // 设置解析耗时和消息（只在最后设置一次）
            resultDto.setParseTimeMs(System.currentTimeMillis() - startTime);
            resultDto.setMessage("解析成功，共" + resultDto.getTotalRows() + "行");
            return resultDto;

        } catch (Exception e) {
            log.error("CSV文件解析失败: {}", fileName, e);
            FileParseResultDto fail = FileParseResultDto.fail(fileType, fileName, "解析异常: " + e.getMessage());
            fail.setParseTimeMs(System.currentTimeMillis() - startTime);
            return fail;
        }
    }

    @Override
    public boolean isSupported(String fileName, String fileType) {
        if (ObjectTrue.isEmpty(fileName)) {
            return false;
        }
        String extension = getFileExtension(fileName).toLowerCase();
        if (ObjectTrue.isNotEmpty(fileType)) {
            return fileType.equalsIgnoreCase("csv") || extension.equals("csv");
        }
        return "csv".equalsIgnoreCase(extension);
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
            if (value != null) {
                value = value.trim();
            } else {
                value = "";
            }

            String fieldName = mapping.getFieldEnglishName();
            if (ObjectTrue.isEmpty(fieldName)) {
                // 如果字段名为空，使用索引作为key
                fieldName = "field_" + index;
            }

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
                // 日期格式保持字符串，由业务层进一步处理
                return value;
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
            if (ObjectTrue.isEmpty(fieldName)) {
                continue; // 跳过没有字段名的映射
            }

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
                try {
                if (!validateByRule(value.toString(), validateRule)) {
                        log.warn("字段格式验证失败: {}, 值: {}, 规则: {}", fieldName, value, validateRule);
                        return false;
                    }
                } catch (Exception e) {
                    log.warn("字段格式验证异常: {}, 值: {}, 规则: {}, 错误: {}", 
                            fieldName, value, validateRule, e.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 读取CSV文件
     * 
     * @param inputStream 输入流
     * @return 数据行列表
     * @throws IOException IO异常
     */
    private List<List<String>> readCSVFile(InputStream inputStream) throws IOException {
        List<List<String>> allRows = new ArrayList<>();
        
        // 使用 BufferedInputStream 支持 mark/reset，用于编码检测
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        
        // 检测字符编码
        Charset charset = detectCharset(bufferedInputStream);
        log.info("检测到CSV文件编码: {}", charset.name());

        // 使用 try-with-resources 自动关闭资源
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(bufferedInputStream, charset))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // 跳过空行
                if (ObjectTrue.isEmpty(line) || line.trim().isEmpty()) {
                    continue;
                }
                
                List<String> row = parseCSVLine(line);
                if (!row.isEmpty()) {
                    allRows.add(row);
                }
            }
        }

        return allRows;
    }

    /**
     * 检测文件字符编码
     * 
     * @param inputStream 输入流（必须是 BufferedInputStream 或支持 mark/reset 的流）
     * @return 字符编码
     */
    private Charset detectCharset(InputStream inputStream) throws IOException {
        // 确保支持 mark/reset
        if (!inputStream.markSupported()) {
            log.warn("输入流不支持 mark/reset，使用默认编码 UTF-8");
            return Charset.forName(DEFAULT_CHARSET);
        }

        // 标记当前位置，以便后续 reset
        inputStream.mark(4096);
        
        try {
        byte[] buffer = new byte[4096];
        int bytesRead = inputStream.read(buffer);
            
            // 重置流到标记位置
        inputStream.reset();

            if (bytesRead <= 0) {
                return Charset.forName(DEFAULT_CHARSET);
            }

            // 检测 UTF-8 BOM (EF BB BF)
        if (bytesRead >= 3 && buffer[0] == (byte) 0xEF && buffer[1] == (byte) 0xBB && buffer[2] == (byte) 0xBF) {
            log.info("检测到UTF-8 BOM");
            return Charset.forName("UTF-8");
        }

            // 检测 UTF-16LE BOM (FF FE)
        if (bytesRead >= 2 && buffer[0] == (byte) 0xFF && buffer[1] == (byte) 0xFE) {
                log.info("检测到UTF-16LE BOM");
            return Charset.forName("UTF-16LE");
        }

            // 检测 UTF-16BE BOM (FE FF)
            if (bytesRead >= 2 && buffer[0] == (byte) 0xFE && buffer[1] == (byte) 0xFF) {
                log.info("检测到UTF-16BE BOM");
                return Charset.forName("UTF-16BE");
            }

            // 默认使用 UTF-8
            return Charset.forName(DEFAULT_CHARSET);
        } catch (Exception e) {
            log.warn("编码检测失败，使用默认编码 UTF-8: {}", e.getMessage());
            return Charset.forName(DEFAULT_CHARSET);
        }
    }

    /**
     * 解析CSV行
     * 处理引号包围的字段和转义字符
     * 
     * @param line CSV行
     * @return 字段列表
     */
    private List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        if (ObjectTrue.isEmpty(line)) {
            return fields;
        }

        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        char delimiter = DEFAULT_DELIMITER;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // 转义引号（双引号表示一个引号字符）
                    currentField.append('"');
                    i++; // 跳过下一个引号
                } else {
                    // 切换引号状态
                    inQuotes = !inQuotes;
                }
            } else if (c == delimiter && !inQuotes) {
                // 分隔符，保存字段
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        // 添加最后一个字段
        fields.add(currentField.toString().trim());

        // 如果行尾有未闭合的引号，记录警告
        if (inQuotes) {
            log.warn("CSV行包含未闭合的引号: {}", line);
        }

        return fields;
    }

    /**
     * 将原始行数据转换为Map（用于无字段映射的解析）
     * 
     * @param rawRow 原始行数据
     * @return 转换后的Map，key为字段索引（field_0, field_1...）
     */
    private Map<String, Object> convertRawRowToMap(List<String> rawRow) {
        Map<String, Object> result = new HashMap<>();
        if (ObjectTrue.isEmpty(rawRow)) {
            return result;
        }

        for (int i = 0; i < rawRow.size(); i++) {
            String value = rawRow.get(i);
            if (value != null) {
                value = value.trim();
            }
            // 使用索引作为key
            result.put("field_" + i, value);
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
        if (NUMBER_PATTERN.matcher(value).matches()) {
            try {
                if (value.contains(".")) {
                    return Double.parseDouble(value);
                }
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                // 不是数字
            }
        }

        // 检查布尔值
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }

        // 检查日期
        if (DATE_PATTERN.matcher(value).matches()) {
            return value;
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
            // 支持正则表达式验证
            return Pattern.matches(validateRule, value);
        } catch (Exception e) {
            log.warn("验证规则执行失败: {}", validateRule, e);
            return true; // 验证规则异常时默认通过
        }
    }
}
