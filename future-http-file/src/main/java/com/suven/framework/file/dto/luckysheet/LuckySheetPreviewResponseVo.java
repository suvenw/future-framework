package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Luckysheet 预览响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckySheetPreviewResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    @ApiDesc(value = "是否成功", required = 0)
    private Boolean success;

    /**
     * 消息
     */
    @ApiDesc(value = "消息", required = 0)
    private String message;

    /**
     * 文件名称
     */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileName;

    /**
     * Sheet 列表
     */
    @ApiDesc(value = "Sheet列表", required = 0)
    private List<LuckysheetSheetConfig> sheets;

    /**
     * 总行数
     */
    @ApiDesc(value = "总行数", required = 0)
    private Integer totalRows;

    /**
     * 总列数
     */
    @ApiDesc(value = "总列数", required = 0)
    private Integer totalColumns;

    /**
     * Sheet 数量
     */
    @ApiDesc(value = "Sheet数量", required = 0)
    private Integer sheetCount;

    /**
     * Sheet 名称列表（用于获取Sheet列表接口）
     */
    @ApiDesc(value = "Sheet名称列表", required = 0)
    private List<String> sheetNames;

    /**
     * 是否为 Excel 文件（用于文件格式检查接口）
     */
    @ApiDesc(value = "是否为Excel文件", required = 0)
    private Boolean isExcel;

    /**
     * JSON 格式数据（用于返回Luckysheet JSON格式）
     */
    @ApiDesc(value = "JSON格式数据", required = 0)
    private String jsonData;

    /**
     * 转换为 JSON 对象 (Luckysheet 格式)
     */
    public JSONObject toLuckysheetJson() {
        JSONObject json = new JSONObject();
        
        // Luckysheet 基础配置
        JSONObject info = new JSONObject();
        info.put("name", fileName != null ? fileName.replace(".xlsx", "").replace(".xls", "") : "Sheet");
        info.put("creator", "System");
        info.put("lastEditTime", System.currentTimeMillis());
        json.put("info", info);
        
        // Sheet 列表
        JSONArray sheetArray = new JSONArray();
        if (sheets != null) {
            for (LuckysheetSheetConfig sheet : sheets) {
                sheetArray.add(sheet.toJson());
            }
        }
        json.put("sheets", sheetArray);
        
        // 权限配置
        JSONObject permission = new JSONObject();
        json.put("permission", permission);
        
        return json;
    }

    /**
     * 转换为 JSON 字符串
     */
    public String toLuckysheetJsonString() {
        return toLuckysheetJson().toJSONString();
    }
}
