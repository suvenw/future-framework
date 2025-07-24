package com.suven.framework.common.excel;

//import com.suven.framework.util.bean.BeanUtil;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;

public class ExportUtils {

//    /**
//     * 导出
//     * @param outputStream 输出流
//     * @param dataList 数据
//     * @param columnBox 列定义
//     */
//    public static void write(OutputStream outputStream, List dataList, ColumnBox columnBox)
//            throws IOException {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("Sheet1");
//        int rowIndex = 0;
//        // 设定标题行样式
//        HSSFCellStyle cellStyle = ExportUtils.getTitleHSSFCellStyle(workbook);
//        // 建立标题行
//        final HSSFRow titleRow = sheet.createRow(rowIndex);
//        titleRow.setHeight((short) (25 * 20));
//        columnBox.forEach(column -> {
//            HSSFCell cell = titleRow.createCell(column.getIndex(), Cell.CELL_TYPE_STRING);
//            cell.setCellStyle(cellStyle);
//            cell.setCellValue(column.getTitle());
//        });
//        // 数据样式
//        HSSFCellStyle contentHSSFCellStyle = ExportUtils.getContentHSSFCellStyle(workbook);
//        // 填充每行数据
//        for (Object data : dataList) {
//            Map<String, Object> map;
//            if(data instanceof Map){
//                map = (Map<String, Object>) data;
//            }else{
//                map = BeanUtil.buildMap(data);
//            }
//
//            rowIndex++;
//            final HSSFRow row = sheet.createRow(rowIndex);
//            row.setHeight((short) (25 * 20));
//            columnBox.forEach(column -> {
//                HSSFCell cell = row.createCell(column.getIndex(), Cell.CELL_TYPE_STRING);
//                cell.setCellStyle(contentHSSFCellStyle);
//                // 设置表格值
//                cell.setCellValue(column.getFormater().format(map.get(column.getKey())));
//            });
//        }
//        columnBox.forEach(column -> sheet.autoSizeColumn(column.getIndex(), true));
//        workbook.write(outputStream);
//    }
//
//    /**
//     * 导出
//     * @param fileName 文件名
//     * @param response 响应
//     * @param dataList 数据
//     * @param columnBox 列定义
//     */
//    public static void write(String fileName, HttpServletResponse response
//            , List dataList, ColumnBox columnBox)
//            throws IOException {
//        // 如果文件名有中文，必须URL编码
//        fileName = URLEncoder.encode(fileName, "UTF-8");
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        write(response.getOutputStream(), dataList, columnBox);
//    }
//
//    /**
//     * 标题表格样式
//     * @param workbook
//     * @return
//     */
//    private static HSSFCellStyle getTitleHSSFCellStyle(HSSFWorkbook workbook){
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setWrapText(true);
//        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 边框
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        // 标题字体
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short) 10);
//        font.setColor(HSSFColor.BLACK.index);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        cellStyle.setFont(font);
//        return cellStyle;
//    }
//
//    /**
//     * 标题内容样式
//     * @param workbook
//     * @return
//     */
//    private static HSSFCellStyle getContentHSSFCellStyle(HSSFWorkbook workbook){
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 边框
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        return cellStyle;
//    }

}
