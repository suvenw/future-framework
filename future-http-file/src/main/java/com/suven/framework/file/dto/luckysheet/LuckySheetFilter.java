package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Luckysheet 筛选配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckySheetFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 筛选范围 - 起始行
     */
    @ApiDesc(value = "起始行", required = 0)
    private Integer row;

    /**
     * 筛选范围 - 起始列
     */
    @ApiDesc(value = "起始列", required = 0)
    private Integer column;

    /**
     * 筛选范围 - 行数
     */
    @ApiDesc(value = "行数", required = 0)
    private Integer rowCount;

    /**
     * 筛选范围 - 列数
     */
    @ApiDesc(value = "列数", required = 0)
    private Integer columnCount;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (row != null) {
            json.put("row", row);
        }
        if (column != null) {
            json.put("column", column);
        }
        if (rowCount != null) {
            json.put("row_count", rowCount);
        }
        if (columnCount != null) {
            json.put("column_count", columnCount);
        }
        return json;
    }
}
