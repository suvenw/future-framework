package com.suven.framework.file.service;

import com.suven.framework.file.dto.luckysheet.*;
import com.suven.framework.file.service.impl.LuckysheetPreviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Luckysheet 预览服务测试
 * ===============================
 *
 * 测试 Excel 在线预览功能：
 * 1. Luckysheet 数据模型构建
 * 2. 单元格数据转换
 * 3. 样式配置测试
 * 4. Sheet 配置测试
 * 5. JSON 格式输出
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
class LuckysheetPreviewServiceTest {

    @InjectMocks
    @Spy
    private LuckysheetPreviewServiceImpl luckysheetPreviewService;

    private LuckysheetPreviewResponseDto responseDto;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        responseDto = createSampleResponseDto();
    }

    @Test
    @DisplayName("测试：创建单元格数据")
    void testCreateCellData() {
        // given
        LuckysheetCellData cellData = LuckysheetCellData.builder()
                .v("Hello")
                .m("Hello")
                .t("s")
                .build();

        // then
        assertNotNull(cellData);
        assertEquals("Hello", cellData.getV());
        assertEquals("Hello", cellData.getM());
        assertEquals("s", cellData.getT());
    }

    @Test
    @DisplayName("测试：创建带样式的单元格")
    void testCreateCellDataWithStyle() {
        // given
        LuckysheetCellStyle style = LuckysheetCellStyle.builder()
                .bg("#FF0000")
                .fc("#FFFFFF")
                .fs(14)
                .ff("Arial")
                .bl(true)
                .build();

        LuckysheetCellData cellData = LuckysheetCellData.builder()
                .v("Styled Text")
                .m("Styled Text")
                .t("s")
                .style(style)
                .build();

        // then
        assertNotNull(cellData);
        assertNotNull(cellData.getStyle());
        assertEquals("#FF0000", cellData.getStyle().getBg());
        assertEquals("#FFFFFF", cellData.getStyle().getFc());
        assertEquals(14, cellData.getStyle().getFs());
        assertTrue(cellData.getStyle().getBl());
    }

    @Test
    @DisplayName("测试：创建合并单元格")
    void testCreateMergeCell() {
        // given
        LuckysheetMergeCell mergeCell = LuckysheetMergeCell.builder()
                .r(0)
                .c(0)
                .rs(2)
                .cs(2)
                .build();

        // then
        assertNotNull(mergeCell);
        assertEquals(0, mergeCell.getR());
        assertEquals(0, mergeCell.getC());
        assertEquals(2, mergeCell.getRs());
        assertEquals(2, mergeCell.getCs());
    }

    @Test
    @DisplayName("测试：创建 Sheet 配置")
    void testCreateSheetConfig() {
        // given
        List<LuckysheetCellData> cells = new ArrayList<>();
        cells.add(createCellData("A1", "Name"));
        cells.add(createCellData("B1", "Age"));
        cells.add(createCellData("A2", "John"));
        cells.add(createCellData("B2", "25"));

        LuckysheetSheetConfig sheetConfig = LuckysheetSheetConfig.builder()
                .name("Test Sheet")
                .color("#009900")
                .status(1)
                .index("sheet_001")
                .data(cells)
                .build();

        // then
        assertNotNull(sheetConfig);
        assertEquals("Test Sheet", sheetConfig.getName());
        assertEquals("#009900", sheetConfig.getColor());
        assertEquals(1, sheetConfig.getStatus());
        assertEquals("sheet_001", sheetConfig.getIndex());
        assertEquals(4, sheetConfig.getData().size());
    }

    @Test
    @DisplayName("测试：预览响应 DTO 构建")
    void testPreviewResponseDto() {
        // given
        List<LuckysheetSheetConfig> sheets = new ArrayList<>();
        sheets.add(createSampleSheetConfig());

        LuckysheetPreviewResponseDto response = LuckysheetPreviewResponseDto.builder()
                .success(true)
                .message("预览生成成功")
                .fileName("test.xlsx")
                .sheets(sheets)
                .totalRows(100)
                .totalColumns(10)
                .sheetCount(1)
                .build();

        // then
        assertTrue(response.isSuccess());
        assertEquals("预览生成成功", response.getMessage());
        assertEquals("test.xlsx", response.getFileName());
        assertEquals(1, response.getSheets().size());
        assertEquals(100, response.getTotalRows());
        assertEquals(10, response.getTotalColumns());
        assertEquals(1, response.getSheetCount());
    }

    @Test
    @DisplayName("测试：Luckysheet JSON 格式输出")
    void testLuckysheetJsonOutput() {
        // given
        List<LuckysheetSheetConfig> sheets = new ArrayList<>();
        sheets.add(createSampleSheetConfig());

        LuckysheetPreviewResponseDto response = LuckysheetPreviewResponseDto.builder()
                .success(true)
                .fileName("test.xlsx")
                .sheets(sheets)
                .totalRows(10)
                .totalColumns(5)
                .sheetCount(1)
                .build();

        // when
        String json = response.toLuckysheetJsonString();

        // then
        assertNotNull(json);
        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"data\""));
        assertTrue(json.contains("\"Test Sheet\""));
    }

    @Test
    @DisplayName("测试：单元格 JSON 格式输出")
    void testCellDataJsonOutput() {
        // given
        LuckysheetCellData cellData = LuckysheetCellData.builder()
                .v("Test Value")
                .m("Test Value")
                .t("s")
                .build();

        // when
        String json = cellData.toJson();

        // then
        assertNotNull(json);
        assertTrue(json.contains("\"v\""));
        assertTrue(json.contains("\"m\""));
        assertTrue(json.contains("\"t\""));
        assertTrue(json.contains("Test Value"));
    }

    @Test
    @DisplayName("测试：创建冻结窗格配置")
    void testFreezeConfig() {
        // given
        LuckysheetFreeze freeze = LuckysheetFreeze.builder()
                .type("rowCol")
                .row(1)
                .column(1)
                .build();

        // then
        assertNotNull(freeze);
        assertEquals("rowCol", freeze.getType());
        assertEquals(1, freeze.getRow());
        assertEquals(1, freeze.getColumn());
    }

    @Test
    @DisplayName("测试：创建筛选器配置")
    void testFilterConfig() {
        // given
        LuckysheetFilter filter = LuckysheetFilter.builder()
                .row(0)
                .column(0)
                .rowCount(1)
                .columnCount(5)
                .build();

        // then
        assertNotNull(filter);
        assertEquals(0, filter.getRow());
        assertEquals(0, filter.getColumn());
        assertEquals(1, filter.getRowCount());
        assertEquals(5, filter.getColumnCount());
    }

    @Test
    @DisplayName("测试：创建保护配置")
    void testProtectConfig() {
        // given
        LuckysheetProtect protect = LuckysheetProtect.builder()
                .enable(true)
                .password("123456")
                .allow("1,2,3")
                .build();

        // then
        assertNotNull(protect);
        assertTrue(protect.getEnable());
        assertEquals("123456", protect.getPassword());
        assertEquals("1,2,3", protect.getAllow());
    }

    @Test
    @DisplayName("测试：预览请求 DTO")
    void testPreviewRequestDto() {
        // given
        LuckysheetPreviewRequestDto request = LuckysheetPreviewRequestDto.builder()
                .fileId("file_123")
                .fileUrl("https://example.com/test.xlsx")
                .sheetIndex(0)
                .maxRows(1000)
                .maxColumns(100)
                .includeStyle(true)
                .includeFormula(true)
                .tenantId(1L)
                .build();

        // then
        assertNotNull(request);
        assertEquals("file_123", request.getFileId());
        assertEquals("https://example.com/test.xlsx", request.getFileUrl());
        assertEquals(0, request.getSheetIndex());
        assertEquals(1000, request.getMaxRows());
        assertEquals(100, request.getMaxColumns());
        assertTrue(request.getIncludeStyle());
        assertTrue(request.getIncludeFormula());
        assertEquals(1L, request.getTenantId());
    }

    // ========== 测试数据工厂方法 ==========

    private LuckysheetCellData createCellData(String value, String displayValue) {
        return LuckysheetCellData.builder()
                .v(displayValue)
                .m(value)
                .t("s")
                .build();
    }

    private LuckysheetSheetConfig createSampleSheetConfig() {
        List<LuckysheetCellData> cells = new ArrayList<>();
        cells.add(createCellData("A1", "Header1"));
        cells.add(createCellData("B1", "Header2"));

        return LuckysheetSheetConfig.builder()
                .name("Sample Sheet")
                .color("#009900")
                .status(1)
                .index("sheet_sample")
                .data(cells)
                .build();
    }

    private LuckysheetPreviewResponseDto createSampleResponseDto() {
        List<LuckysheetSheetConfig> sheets = new ArrayList<>();
        sheets.add(createSampleSheetConfig());

        return LuckysheetPreviewResponseDto.builder()
                .success(true)
                .message("预览生成成功")
                .fileName("test.xlsx")
                .sheets(sheets)
                .totalRows(100)
                .totalColumns(10)
                .sheetCount(1)
                .build();
    }
}
