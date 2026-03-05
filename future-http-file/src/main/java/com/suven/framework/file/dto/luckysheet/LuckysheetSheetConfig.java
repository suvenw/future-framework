package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Luckysheet Sheet 配置数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckysheetSheetConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Sheet 名称
     */
    @ApiDesc(value = "Sheet名称", required = 1)
    private String name;

    /**
     * Sheet 颜色
     */
    @ApiDesc(value = "Sheet颜色", required = 0)
    private String color;

    /**
     * Sheet 状态: 0=隐藏, 1=显示
     */
    @ApiDesc(value = "Sheet状态", required = 0)
    private Integer status;

    /**
     * Sheet 顺序
     */
    @ApiDesc(value = "Sheet顺序", required = 0)
    private Integer order;

    /**
     * Sheet 索引
     */
    @ApiDesc(value = "Sheet索引", required = 0)
    private Integer index;

    /**
     * 单元格数据 - 二维数组
     */
    @ApiDesc(value = "单元格数据", required = 0)
    private List<List<LuckySheetCellData>> data;

    /**
     * 合并单元格配置
     */
    @ApiDesc(value = "合并单元格", required = 0)
    private Map<String, LuckySheetMergeCell> merge;

    /**
     * 行高配置
     */
    @ApiDesc(value = "行高配置", required = 0)
    private Map<String, Integer> rowlen;

    /**
     * 列宽配置
     */
    @ApiDesc(value = "列宽配置", required = 0)
    private Map<String, Integer> collen;

    /**
     * 隐藏行配置
     */
    @ApiDesc(value = "隐藏行", required = 0)
    private Map<String, Integer> hidden;

    /**
     * 隐藏列配置
     */
    @ApiDesc(value = "隐藏列", required = 0)
    private Map<String, Integer> hiddenColumns;

    /**
     * 筛选配置
     */
    @ApiDesc(value = "筛选配置", required = 0)
    private LuckySheetFilter filter;

    /**
     * 冻结行配置
     */
    @ApiDesc(value = "冻结行", required = 0)
    private LuckySheetFreeze freeze;

    /**
     * 保护配置
     */
    @ApiDesc(value = "保护配置", required = 0)
    private LuckySheetProtect protect;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("name", name);
        
        if (color != null) {
            json.put("color", color);
        }
        if (status != null) {
            json.put("status", status);
        }
        if (order != null) {
            json.put("order", order);
        }
        if (index != null) {
            json.put("index", index);
        }
        
        // 处理单元格数据
        if (data != null) {
            JSONArray dataArray = new JSONArray();
            for (List<LuckySheetCellData> row : data) {
                JSONArray rowArray = new JSONArray();
                if (row != null) {
                    for (LuckySheetCellData cell : row) {
                        if (cell != null) {
                            rowArray.add(cell.toJson());
                        } else {
                            rowArray.add(null);
                        }
                    }
                }
                dataArray.add(rowArray);
            }
            json.put("data", dataArray);
        }
        
        // 处理合并单元格
        if (merge != null && !merge.isEmpty()) {
            JSONObject mergeJson = new JSONObject();
            for (Map.Entry<String, LuckySheetMergeCell> entry : merge.entrySet()) {
                mergeJson.put(entry.getKey(), entry.getValue().toJson());
            }
            json.put("merge", mergeJson);
        } else {
            json.put("merge", new JSONObject());
        }
        
        // 处理行高
        if (rowlen != null && !rowlen.isEmpty()) {
            JSONObject rowlenJson = new JSONObject();
            rowlenJson.putAll(rowlen);
            json.put("rowlen", rowlenJson);
        } else {
            json.put("rowlen", new JSONObject());
        }
        
        // 处理列宽
        if (collen != null && !collen.isEmpty()) {
            JSONObject collenJson = new JSONObject();
            collenJson.putAll(collen);
            json.put("collen", collenJson);
        } else {
            json.put("collen", new JSONObject());
        }
        
        return json;
    }
}
