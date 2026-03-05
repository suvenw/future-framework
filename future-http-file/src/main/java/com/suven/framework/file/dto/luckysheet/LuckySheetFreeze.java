package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson2.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Luckysheet 冻结配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckySheetFreeze implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 冻结类型: "row"=冻结行, "col"=冻结列, "rangeRowCol"=冻结行列, "rangeRow"=冻结行范围, "rangeCol"=冻结列范围
     */
    @ApiDesc(value = "冻结类型", required = 0)
    private String type;

    /**
     * 冻结起始行
     */
    @ApiDesc(value = "起始行", required = 0)
    private Integer row;

    /**
     * 冻结起始列
     */
    @ApiDesc(value = "起始列", required = 0)
    private Integer column;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (type != null) {
            json.put("type", type);
        }
        if (row != null) {
            json.put("row", row);
        }
        if (column != null) {
            json.put("column", column);
        }
        return json;
    }
}
