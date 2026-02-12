package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Luckysheet 合并单元格配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckysheetMergeCell implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 起始行
     */
    @ApiDesc(value = "起始行", required = 1)
    private Integer r;

    /**
     * 起始列
     */
    @ApiDesc(value = "起始列", required = 1)
    private Integer c;

    /**
     * 跨行数
     */
    @ApiDesc(value = "跨行数", required = 0)
    private Integer rs;

    /**
     * 跨列数
     */
    @ApiDesc(value = "跨列数", required = 0)
    private Integer cs;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("r", r);
        json.put("c", c);
        if (rs != null) {
            json.put("rs", rs);
        }
        if (cs != null) {
            json.put("cs", cs);
        }
        return json;
    }
}
