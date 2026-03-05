package com.suven.framework.file.service.impl;

import com.suven.framework.file.client.FileClient;
import com.suven.framework.file.dto.luckysheet.*;
import com.suven.framework.file.service.LuckySheetPreviewService;

import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.util.http.OkHttpClients;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Luckysheet 预览服务实现
 * 
 * 功能：将 Excel 文件转换为 Luckysheet 格式，支持在线预览
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-12
 */
@Slf4j
@Service
public class LuckySheetPreviewServiceImpl implements LuckySheetPreviewService {

    @Autowired(required = false)
    private FileClient fileClient;

    // 支持的 Excel 文件扩展名
    private static final Set<String> EXCEL_EXTENSIONS = new HashSet<>(Arrays.asList(
        ".xlsx", ".xls"
    ));

    // 默认最大行数
    private static final int DEFAULT_MAX_ROWS = 10000;
    
    // 默认最大列数
    private static final int DEFAULT_MAX_COLUMNS = 500;

    @Override
    public LuckySheetPreviewResponseDto preview(LuckySheetPreviewRequestDto requestDto) {
        log.info("开始预览 Excel 文件: fileId={}, fileUrl={}", requestDto.getFileId(), requestDto.getFileUrl());
        
        try {
            // 获取文件输入流
            InputStream inputStream = getFileInputStream(requestDto);
            if (inputStream == null) {
                throw new SystemRuntimeException("无法获取文件输入流");
            }
            
            // 解析 Excel 文件
            List<LuckysheetSheetConfig> sheets = parseExcel(inputStream, requestDto);
            
            // 构建响应
            int totalRows = 0;
            int totalColumns = 0;
            if (sheets != null && !sheets.isEmpty()) {
                LuckysheetSheetConfig firstSheet = sheets.get(0);
                if (firstSheet.getData() != null && !firstSheet.getData().isEmpty()) {
                    totalRows = firstSheet.getData().size();
                    if (!firstSheet.getData().isEmpty()) {
                        totalColumns = firstSheet.getData().get(0).size();
                    }
                }
            }
            
            LuckySheetPreviewResponseDto response = LuckySheetPreviewResponseDto.builder()
                .success(true)
                .message("预览成功")
                .sheets(sheets)
                .totalRows(totalRows)
                .totalColumns(totalColumns)
                .sheetCount(sheets != null ? sheets.size() : 0)
                .build();
            
            inputStream.close();
            return response;
            
        } catch (Exception e) {
            log.error("预览 Excel 文件失败", e);
            return LuckySheetPreviewResponseDto.builder()
                .success(false)
                .message("预览失败: " + e.getMessage())
                .build();
        }
    }

    @Override
    public String previewAsJson(LuckySheetPreviewRequestDto requestDto) {
        LuckySheetPreviewResponseDto response = preview(requestDto);
        if (response.getSuccess() != null && response.getSuccess()) {
            return response.toLuckysheetJsonString();
        }
        return null;
    }

    @Override
    public LuckySheetPreviewResponseDto previewSheet(LuckySheetPreviewRequestDto requestDto, int sheetIndex) {
        log.info("预览 Excel 文件指定 Sheet: fileId={}, sheetIndex={}", requestDto.getFileId(), sheetIndex);
        
        try {
            // 获取文件输入流
            InputStream inputStream = getFileInputStream(requestDto);
            if (inputStream == null) {
                throw new SystemRuntimeException("无法获取文件输入流");
            }
            
            // 解析指定 Sheet
            LuckysheetSheetConfig sheet = parseExcelSheet(inputStream, sheetIndex, requestDto);
            
            List<LuckysheetSheetConfig> sheets = Collections.singletonList(sheet);
            
            int totalRows = 0;
            int totalColumns = 0;
            if (sheet.getData() != null && !sheet.getData().isEmpty()) {
                totalRows = sheet.getData().size();
                if (!sheet.getData().isEmpty()) {
                    totalColumns = sheet.getData().get(0).size();
                }
            }
            
            LuckySheetPreviewResponseDto response = LuckySheetPreviewResponseDto.builder()
                .success(true)
                .message("预览成功")
                .sheets(sheets)
                .totalRows(totalRows)
                .totalColumns(totalColumns)
                .sheetCount(1)
                .build();
            
            inputStream.close();
            return response;
            
        } catch (Exception e) {
            log.error("预览 Excel 文件指定 Sheet 失败", e);
            return LuckySheetPreviewResponseDto.builder()
                .success(false)
                .message("预览失败: " + e.getMessage())
                .build();
        }
    }

    @Override
    public boolean isExcelFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String lowerName = fileName.toLowerCase();
        for (String ext : EXCEL_EXTENSIONS) {
            if (lowerName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getSheetNames(LuckySheetPreviewRequestDto requestDto) {
        log.info("获取 Sheet 列表: fileId={}", requestDto.getFileId());
        
        try {
            InputStream inputStream = getFileInputStream(requestDto);
            if (inputStream == null) {
                throw new SystemRuntimeException("无法获取文件输入流");
            }
            
            List<String> sheetNames = new ArrayList<>();
            Workbook workbook = createWorkbook(inputStream, requestDto.getFilePath());
            
            if (workbook != null) {
                int numSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numSheets; i++) {
                    sheetNames.add(workbook.getSheetAt(i).getSheetName());
                }
                workbook.close();
            }
            
            inputStream.close();
            return sheetNames;
            
        } catch (Exception e) {
            log.error("获取 Sheet 列表失败", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取文件输入流
     */
    private InputStream getFileInputStream(LuckySheetPreviewRequestDto requestDto) throws IOException {
        // 1. 先尝试从文件路径读取
        if (requestDto.getFilePath() != null && !requestDto.getFilePath().isEmpty()) {
            File file = new File(requestDto.getFilePath());
            if (file.exists()) {
                return new FileInputStream(file);
            }
        }
        
        // 2. 尝试从 URL 下载
        if (requestDto.getFileUrl() != null && !requestDto.getFileUrl().isEmpty()) {
            return OkHttpClients.getHttpInputStream(requestDto.getFileUrl(), null);
        }
        
        // 3. 尝试从文件存储服务读取
        if (requestDto.getFileId() != null && requestDto.getFileId() > 0) {
            if (fileClient != null) {
                // 从文件存储服务获取文件内容
                log.info("从文件存储服务获取文件: fileId={}", requestDto.getFileId());
                // 这里需要根据实际实现获取文件
                // byte[] content = fileClient.getContent(...);
                // return new ByteArrayInputStream(content);
            }
        }
        
        return null;
    }

    /**
     * 创建 Workbook
     */
    private Workbook createWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName != null && fileName.toLowerCase().endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else {
            return new HSSFWorkbook(inputStream);
        }
    }

    /**
     * 解析整个 Excel 文件
     */
    private List<LuckysheetSheetConfig> parseExcel(InputStream inputStream, LuckySheetPreviewRequestDto requestDto) throws IOException {
        List<LuckysheetSheetConfig> sheets = new ArrayList<>();
        
        Workbook workbook = createWorkbook(inputStream, requestDto.getFilePath());
        int numSheets = workbook.getNumberOfSheets();
        
        // 如果指定了 sheetIndex，只解析该 Sheet
        if (requestDto.getSheetIndex() != null && requestDto.getSheetIndex() >= 0) {
            if (requestDto.getSheetIndex() < numSheets) {
                sheets.add(parseSheet(workbook.getSheetAt(requestDto.getSheetIndex()), requestDto));
            }
        } else {
            // 解析所有 Sheet
            for (int i = 0; i < numSheets; i++) {
                sheets.add(parseSheet(workbook.getSheetAt(i), requestDto));
            }
        }
        
        workbook.close();
        return sheets;
    }

    /**
     * 解析指定 Sheet
     */
    private LuckysheetSheetConfig parseExcelSheet(InputStream inputStream, int sheetIndex, LuckySheetPreviewRequestDto requestDto) throws IOException {
        Workbook workbook = createWorkbook(inputStream, requestDto.getFilePath());
        LuckysheetSheetConfig sheet = null;
        
        if (sheetIndex >= 0 && sheetIndex < workbook.getNumberOfSheets()) {
            sheet = parseSheet(workbook.getSheetAt(sheetIndex), requestDto);
        }
        
        workbook.close();
        return sheet;
    }

    /**
     * 解析单个 Sheet
     */
    private LuckysheetSheetConfig parseSheet(Sheet sheet, LuckySheetPreviewRequestDto requestDto) {
        String sheetName = sheet.getSheetName();
        int sheetIndex = getSheetIndex(sheet.getWorkbook(), sheet);
        
        // 获取配置参数
        int maxRows = requestDto.getMaxRows() != null && requestDto.getMaxRows() > 0 
            ? requestDto.getMaxRows() 
            : DEFAULT_MAX_ROWS;
        int maxColumns = requestDto.getMaxColumns() != null && requestDto.getMaxColumns() > 0 
            ? requestDto.getMaxColumns() 
            : DEFAULT_MAX_COLUMNS;
        boolean includeStyle = requestDto.getIncludeStyle() == null || requestDto.getIncludeStyle();
        boolean includeFormula = requestDto.getIncludeFormula() == null || requestDto.getIncludeFormula();
        
        // 读取数据
        List<List<LuckySheetCellData>> data = new ArrayList<>();
        Map<String, LuckySheetMergeCell> mergeCells = new HashMap<>();
        
        // 获取最后一行和最后一列
        int lastRowNum = (int) sheet.getLastRowNum();
        lastRowNum = Math.min(lastRowNum, maxRows - 1);
        
        for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                data.add(new ArrayList<>());
                continue;
            }
            
            List<LuckySheetCellData> rowData = new ArrayList<>();
            int lastCellNum = (int) row.getLastCellNum();
            lastCellNum = Math.min(lastCellNum, maxColumns);
            
            for (int colIndex = 0; colIndex < lastCellNum; colIndex++) {
                Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                LuckySheetCellData cellData = parseCell(cell, includeStyle, includeFormula);
                rowData.add(cellData);
                
                // 处理合并单元格
                if (cellData.getRs() != null || cellData.getCs() != null) {
                    String key = rowIndex + "_" + colIndex;
                    LuckySheetMergeCell mergeCell = LuckySheetMergeCell.builder()
                        .r(rowIndex)
                        .c(colIndex)
                        .rs(cellData.getRs())
                        .cs(cellData.getCs())
                        .build();
                    mergeCells.put(key, mergeCell);
                }
            }
            
            data.add(rowData);
        }
        
        // 构建 Sheet 配置
        return LuckysheetSheetConfig.builder()
            .name(sheetName)
            .color("")
            .status(1)
            .order(sheetIndex)
            .index(sheetIndex)
            .data(data)
            .merge(mergeCells.isEmpty() ? null : mergeCells)
            .build();
    }

    /**
     * 解析单个单元格
     */
    private LuckySheetCellData parseCell(Cell cell, boolean includeStyle, boolean includeFormula) {
        LuckySheetCellData.LuckysheetCellDataBuilder builder = LuckySheetCellData.builder();
        
        CellType cellType = cell.getCellType();
        
        // 处理公式单元格
        if (cellType == CellType.FORMULA) {
            if (includeFormula) {
                builder.f(cell.getCellFormula());
            }
            // 获取公式计算结果类型
            CellType cachedValueType = cell.getCachedFormulaResultType();
            builder.t(getCellType(cachedValueType));
        } else {
            builder.t(getCellType(cellType));
        }
        
        // 根据单元格类型处理值
        switch (cellType) {
            case STRING:
                String stringValue = cell.getStringCellValue();
                builder.v(stringValue);
                builder.m(stringValue);
                break;
                
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    java.util.Date dateValue = cell.getDateCellValue();
                    builder.v(dateValue.toString());
                    builder.m(cell.getLocalDateTimeCellValue().toString());
                    builder.t("d");
                } else {
                    double numericValue = cell.getNumericCellValue();
                    builder.v(String.valueOf(numericValue));
                    builder.m(String.valueOf(numericValue));
                    builder.t("n");
                }
                break;
                
            case BOOLEAN:
                boolean booleanValue = cell.getBooleanCellValue();
                builder.v(String.valueOf(booleanValue));
                builder.m(String.valueOf(booleanValue));
                builder.t("b");
                break;
                
            case BLANK:
                builder.v("");
                builder.m("");
                builder.t("s");
                break;
                
            case FORMULA:
                // 公式结果已经在上面处理
                if (cell.getCachedFormulaResultType() == CellType.STRING) {
                    builder.v(cell.getStringCellValue());
                } else if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                    double numericValue = cell.getNumericCellValue();
                    builder.v(String.valueOf(numericValue));
                }
                break;
                
            default:
                builder.v("");
                builder.m("");
                builder.t("s");
        }
        
        // 处理合并单元格
        CellRangeAddress mergedRegion = getMergedRegion(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
        if (mergedRegion != null) {
            if (mergedRegion.getFirstRow() == cell.getRowIndex() && mergedRegion.getFirstColumn() == cell.getColumnIndex()) {
                // 左上角单元格，设置合并范围
                int rs = mergedRegion.getLastRow() - mergedRegion.getFirstRow() + 1;
                int cs = mergedRegion.getLastColumn() - mergedRegion.getFirstColumn() + 1;
                builder.rs(rs > 1 ? rs : null);
                builder.cs(cs > 1 ? cs : null);
            } else {
                // 非左上角单元格，不输出数据
                return null;
            }
        }
        
        // 处理样式
        if (includeStyle) {
            LuckySheetCellStyle style = parseCellStyle(cell.getCellStyle());
            if (style != null) {
                builder.style(style);
            }
        }
        
        return builder.build();
    }

    /**
     * 获取单元格类型字符串
     */
    private String getCellType(CellType cellType) {
        switch (cellType) {
            case STRING:
                return "s";
            case NUMERIC:
                return "n";
            case BOOLEAN:
                return "b";
            case BLANK:
                return "s";
            case FORMULA:
                return "n";
            default:
                return "s";
        }
    }

    /**
     * 解析单元格样式
     */
    private LuckySheetCellStyle parseCellStyle(CellStyle cellStyle) {
        if (cellStyle == null) {
            return null;
        }
        
        LuckySheetCellStyle.LuckySheetCellStyleBuilder builder = LuckySheetCellStyle.builder();
        
        // 背景颜色
        short bgColor = cellStyle.getFillForegroundColor();
        if (bgColor > 0) {
            builder.bg(getColorString(bgColor, cellStyle.getFillBackgroundColorColor()));
        }
        
        // 字体颜色
        Font font = new XSSFFont();
        if (font != null) {
            short fontColor = font.getColor();
            builder.fc(getColorString(fontColor, null));
            builder.fs((int)font.getFontHeightInPoints());
            builder.ff(font.getFontName());
            
            // 粗体
            if (font.getBold()) {
                builder.bl(1);
            }
            
            // 斜体
            if (font.getItalic()) {
                builder.it(1);
            }
            
            // 下划线
            if (font.getUnderline() > 0) {
                builder.un(1);
            }
        }
        cellStyle.setFont(font);
        // 边框
        short borderColor = cellStyle.getBottomBorderColor();
        if (borderColor > 0) {
            builder.bc(getColorString(borderColor, null));
            builder.bw((int) cellStyle.getBorderBottom().getCode());
        }
        
        // 对齐方式
        switch (cellStyle.getAlignment()) {
            case LEFT:
                builder.ht(0);
                break;
            case CENTER:
                builder.ht(1);
                break;
            case RIGHT:
                builder.ht(2);
                break;
            default:
                builder.ht(0);
        }
        
        switch (cellStyle.getVerticalAlignment()) {
            case TOP:
                builder.vt(0);
                break;
            case CENTER:
                builder.vt(1);
                break;
            case BOTTOM:
                builder.vt(2);
                break;
            default:
                builder.vt(0);
        }
        
        return builder.build();
    }

    /**
     * 获取颜色字符串
     */
    private String getColorString(short indexedColor, Color rgbColor) {
        if (indexedColor > 0 && rgbColor != null) {
            if (rgbColor instanceof XSSFColor) {
                XSSFColor xssfColor = (XSSFColor) rgbColor;
                byte[] rgb = xssfColor.getRGB();
                if (rgb != null) {
                    return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                }
            } else if (rgbColor instanceof HSSFColor) {
                HSSFColor hssfColor = (HSSFColor) rgbColor;
                short[] rgb = hssfColor.getTriplet();
                if (rgb != null) {
                    return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
                }
            }
        }
        return null;
    }

    /**
     * 获取合并单元格信息
     */
    private CellRangeAddress getMergedRegion(Sheet sheet, int row, int column) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress region : mergedRegions) {
            if (region.isInRange(row, column)) {
                return region;
            }
        }
        return null;
    }

    /**
     * 获取 Workbook 中 Sheet 的索引
     */
    private int getSheetIndex(Workbook workbook, Sheet sheet) {
        // 由于 Sheet 对象不直接提供索引，需要通过 Workbook 获取
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (workbook.getSheetAt(i) == sheet) {
                return i;
            }
        }
        return 0;
    }
}
