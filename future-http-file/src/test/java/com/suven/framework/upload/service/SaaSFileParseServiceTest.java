package com.suven.framework.upload.service;

import com.suven.framework.upload.AbstractUnitTest;
import com.suven.framework.upload.dto.response.SaaSFileParseResultDto;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.service.impl.SaaSFileParseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SaaS 文件解析服务测试
 * ===============================
 *
 * 测试文件解析服务的核心功能：
 * 1. XLS 文件解析
 * 2. CSV 文件解析
 * 3. 字段映射转换
 * 4. 数据类型转换
 * 5. 数据校验
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class SaaSFileParseServiceTest extends AbstractUnitTest {

    @InjectMocks
    @Spy
    private SaaSFileParseServiceImpl fileParseService;

    @TempDir
    Path tempDir;

    private List<SaaSFileFieldMapping> fieldMappings;

    @BeforeEach
    void setUp() {
        logTestStart("SaaSFileParseServiceTest");
        // 初始化字段映射配置
        fieldMappings = createFieldMappings();
    }

    @Test
    @DisplayName("测试：支持的文件类型识别")
    void testIsSupported() {
        logStep("测试文件类型识别功能");

        // given
        String xlsxFile = "test.xlsx";
        String xlsFile = "test.xls";
        String csvFile = "test.csv";
        String txtFile = "test.txt";

        // when & then
        assertTrue(fileParseService.isSupported(xlsxFile, "xlsx"));
        assertTrue(fileParseService.isSupported(xlsFile, "xls"));
        assertTrue(fileParseService.isSupported(csvFile, "csv"));
        assertFalse(fileParseService.isSupported(txtFile, "txt"));

        logResult("文件类型识别", "通过");
    }

    @Test
    @DisplayName("测试：获取文件扩展名")
    void testGetFileExtension() {
        logStep("测试获取文件扩展名");

        // given & when & then
        assertEquals("xlsx", fileParseService.getFileExtension("test.xlsx"));
        assertEquals("xls", fileParseService.getFileExtension("test.xls"));
        assertEquals("csv", fileParseService.getFileExtension("test.csv"));
        assertEquals("", fileParseService.getFileExtension("test"));

        logResult("扩展名获取", "通过");
    }

    @Test
    @DisplayName("测试：CSV 数据行转换 - 英文字段转中文字段")
    void testConvertDataRow() {
        logStep("测试数据行字段映射转换");

        // given
        List<String> rawData = Arrays.asList("John", "25", "true", "2024-01-01");
        List<SaaSFileFieldMapping> mappings = createFieldMappings();

        // when
        Map<String, Object> result = fileParseService.convertDataRow(rawData, mappings);

        // then
        assertNotNull(result);
        assertEquals("John", result.get("姓名"));
        assertEquals(25, result.get("年龄"));
        assertEquals(true, result.get("是否激活"));
        assertEquals("2024-01-01", result.get("注册日期"));

        logData("转换结果", result);
    }

    @Test
    @DisplayName("测试：数据类型转换 - String 转 Number/Boolean")
    void testConvertValue() {
        logStep("测试数据类型转换");

        // when & then
        assertEquals(25, fileParseService.convertValue("25", "java.lang.Integer"));
        assertEquals(25L, fileParseService.convertValue("25", "java.lang.Long"));
        assertEquals(25.5, fileParseService.convertValue("25.5", "java.lang.Double"));
        assertEquals(true, fileParseService.convertValue("true", "java.lang.Boolean"));
        assertEquals(false, fileParseService.convertValue("false", "java.lang.Boolean"));
        assertEquals("test", fileParseService.convertValue("test", "java.lang.String"));

        logResult("类型转换", "通过");
    }

    @Test
    @DisplayName("测试：数据行校验 - 必填字段校验")
    void testValidateDataRow() {
        logStep("测试数据行校验功能");

        // given
        List<SaaSFileFieldMapping> mappings = createFieldMappingsWithRequired();

        // 必填字段不为空 - 通过校验
        Map<String, Object> validData = new HashMap<>();
        validData.put("姓名", "John");
        validData.put("年龄", 25);
        validData.put("是否激活", true);
        validData.put("注册日期", "2024-01-01");

        // when & then
        assertTrue(fileParseService.validateDataRow(validData, mappings));

        // 必填字段为空 - 校验失败
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("姓名", "");
        invalidData.put("年龄", 25);
        invalidData.put("是否激活", true);
        invalidData.put("注册日期", "2024-01-01");

        assertFalse(fileParseService.validateDataRow(invalidData, mappings));

        logResult("数据校验", "通过");
    }

    @Test
    @DisplayName("测试：CSV 文件解析 - 完整流程")
    void testParseCsvFile() throws IOException {
        logStep("测试 CSV 文件完整解析流程");

        // given
        String csvContent = "name,age,active,date\nJohn,25,true,2024-01-01\nJane,30,false,2024-01-02\n";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        // when
        SaaSFileParseResultDto result = fileParseService.parse(inputStream, "test.csv", "csv");

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(2, result.getTotalRows());
        assertEquals(2, result.getSuccessRows());
        assertEquals(0, result.getFailRows());
        assertEquals(2, result.getDataRows().size());

        logData("解析结果", result);
    }

    @Test
    @DisplayName("测试：带字段映射的解析 - 英文转中文")
    void testParseWithMapping() throws IOException {
        logStep("测试带字段映射的解析功能");

        // given
        String csvContent = "name,age\nJohn,25\n";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        // when
        SaaSFileParseResultDto result = fileParseService.parseWithMapping(
                inputStream,
                "test.csv",
                "csv",
                fieldMappings
        );

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(1, result.getDataRows().size());

        Map<String, Object> dataRow = result.getDataRows().get(0);
        assertTrue(dataRow.containsKey("姓名"));
        assertTrue(dataRow.containsKey("年龄"));

        logData("带映射解析结果", result);
    }

    @Test
    @DisplayName("测试：解析结果统计")
    void testParseResultStatistics() throws IOException {
        logStep("测试解析结果统计功能");

        // given
        String csvContent = "name,age\nJohn,25\nJane,30\n";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        // when
        SaaSFileParseResultDto result = fileParseService.parse(inputStream, "test.csv", "csv");

        // then
        assertEquals(2, result.getTotalRows());
        assertEquals(2, result.getSuccessRows());
        assertEquals(0, result.getFailRows());
        assertEquals(0, result.getSkipRows());
        assertTrue(result.getParseTimeMs() >= 0);

        logResult("统计结果", "通过");
    }

    // ========== 测试数据工厂方法 ==========

    /**
     * 创建字段映射配置
     */
    private List<SaaSFileFieldMapping> createFieldMappings() {
        List<SaaSFileFieldMapping> mappings = new ArrayList<>();

        mappings.add(createFieldMapping("name", "姓名", 1));
        mappings.add(createFieldMapping("age", "年龄", 2));
        mappings.add(createFieldMapping("active", "是否激活", 3));
        mappings.add(createFieldMapping("date", "注册日期", 4));

        return mappings;
    }

    /**
     * 创建带必填校验的字段映射
     */
    private List<SaaSFileFieldMapping> createFieldMappingsWithRequired() {
        List<SaaSFileFieldMapping> mappings = new ArrayList<>();

        SaaSFileFieldMapping nameMapping = createFieldMapping("name", "姓名", 1);
        nameMapping.setRequired(true);
        mappings.add(nameMapping);

        mappings.add(createFieldMapping("age", "年龄", 2));
        mappings.add(createFieldMapping("active", "是否激活", 3));
        mappings.add(createFieldMapping("date", "注册日期", 4));

        return mappings;
    }

    /**
     * 创建单个字段映射
     */
    private SaaSFileFieldMapping createFieldMapping(String englishName, String chineseName, int sortOrder) {
        return SaaSFileFieldMapping.builder()
                .englishName(englishName)
                .chineseName(chineseName)
                .sortOrder(sortOrder)
                .fieldType("java.lang.String")
                .required(false)
                .build();
    }
}
