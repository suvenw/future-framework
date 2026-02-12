package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson.JSON;
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
 * Luckysheet 单元格数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckysheetCellData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单元格文本值
     */
    @ApiDesc(value = "单元格文本值", required = 0)
    private String v;

    /**
     * 单元格格式化前的原始值
     */
    @ApiDesc(value = "原始值", required = 0)
    private String m;

    /**
     * 单元格类型: "t"=文本, "n"=数字, "b"=布尔值, "d"=日期, "s"=富文本
     */
    @ApiDesc(value = "单元格类型", required = 0)
    private String t;

    /**
     * 单元格样式
     */
    @ApiDesc(value = "单元格样式", required = 0)
    private LuckysheetCellStyle style;

    /**
     * 公式
     */
    @ApiDesc(value = "公式", required = 0)
    private String f;

    /**
     * 跨列合并信息
     */
    @ApiDesc(value = "跨列", required = 0)
    private Integer cs;

    /**
     * 跨行合并信息
     */
    @ApiDesc(value = "跨行", required = 0)
    private Integer rs;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        if (v != null) {
            json.put("v", v);
        }
        if (m != null) {
            json.put("m", m);
        }
        if (t != null) {
            json.put("t", t);
        }
        if (f != null) {
            json.put("f", f);
        }
        if (cs != null) {
            json.put("cs", cs);
        }
        if (rs != null) {
            json.put("rs", rs);
        }
        if (style != null) {
            json.put("style", style.toJson());
        }
        
        return json;
    }
}
