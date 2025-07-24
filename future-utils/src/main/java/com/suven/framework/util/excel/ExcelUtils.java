package com.suven.framework.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.suven.framework.http.api.IBaseExcelData;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 *   阿里 EasyExcel 文件导入导出 工具类
 * @Author dongxie
 * @Description //TODO
 * @CreateDate 2019-11-15  17:15
 **/
public class ExcelUtils {


    /**
     *  文件导入
     * @param is 输入流
     * @param baseDAO 数据库业务操作对象
     * @param clazzData 实体对象
     * @param sheetNo sheet
     * @return
     */
    public static Boolean readExcel(InputStream is, IBaseExcelData baseDAO, Class clazzData, int sheetNo){
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(is);
            // 解析每行结果在listener中处理
            ExcelListener listener = ExcelListener.build(baseDAO);
            ExcelReader excelReader = EasyExcel.read(bis, clazzData, listener).build();
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
            excelReader.read(readSheet);
            excelReader.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     *
     *  文件导出
     * @param response 输出流
     * @param clazzData 实体对象
     * @param data 导出的list数据
     * @param sheetName  sheetName名称
     * @return
     */
    public static Boolean writeExcel(HttpServletResponse response, String fileChinaName, Class clazzData, List data, String sheetName) throws Exception{
        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileChinaName.getBytes("gbk"), "iso8859-1"));
        response.setContentType("application/octet-stream; charset=UTF-8");
        return writeExcel(response.getOutputStream(),clazzData,data,sheetName);

    }

    /**
     *
     *  文件导出
     * @param response 输出流
     * @param clazzData 实体对象
     * @param data 导出的list数据
     * @param sheetName  sheetName名称
     * @return
     */
    public static Boolean writeExcel(HttpServletResponse response, String fileName ,Charset charset, Class clazzData, List data, String sheetName) throws Exception{
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName.getBytes(charset) );
        response.setContentType("application/octet-stream; charset=UTF-8");
        return writeExcel(response.getOutputStream(),clazzData,data,sheetName);

    }
    /**
     *
     *  文件导出
     * @param os 输出流
     * @param clazzData 实体对象
     * @param data 导出的list数据
     * @param sheetName  sheetName名称
     * @return
     */
    public static Boolean writeExcel(OutputStream os, Class clazzData, List data,String sheetName){
        BufferedOutputStream bos= null;
        if(StringUtils.isBlank(sheetName)){
            sheetName="导出";
        }
        try {
            bos = new BufferedOutputStream(os);
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            ExcelWriter excelWriter = EasyExcel.write(bos, clazzData).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            excelWriter.write(data, writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
