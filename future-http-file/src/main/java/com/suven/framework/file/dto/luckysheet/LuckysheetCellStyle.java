package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Luckysheet 单元格样式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckysheetCellStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 背景颜色
     */
    @ApiDesc(value = "背景颜色", required = 0)
    private String bg;

    /**
     * 字体颜色
     */
    @ApiDesc(value = "字体颜色", required = 0)
    private String fc;

    /**
     * 字体大小
     */
    @ApiDesc(value = "字体大小", required = 0)
    private Integer fs;

    /**
     * 字体名称
     */
    @ApiDesc(value = "字体名称", required = 0)
    private String ff;

    /**
     * 是否粗体: 1=粗体, 0=正常
     */
    @ApiDesc(value = "是否粗体", required = 0)
    private Integer bl;

    /**
     * 是否斜体: 1=斜体, 0=正常
     */
    @ApiDesc(value = "是否斜体", required = 0)
    private Integer it;

    /**
     * 下划线: 0=无, 1=下划线, 2=双下划线
     */
    @ApiDesc(value = "下划线", required = 0)
    private Integer un;

    /**
     * 删除线: 0=无, 1=删除线
     */
    @ApiDesc(value = "删除线", required = 0)
    private Integer cl;

    /**
     * 水平对齐: 0=左, 1=中, 2=右
     */
    @ApiDesc(value = "水平对齐", required = 0)
    private Integer ht;

    /**
     * 垂直对齐: 0=上, 1=中, 2=下
     */
    @ApiDesc(value = "垂直对齐", required = 0)
    private Integer vt;

    /**
     * 边框颜色
     */
    @ApiDesc(value = "边框颜色", required = 0)
    private String bc;

    /**
     * 边框宽度
     */
    @ApiDesc(value = "边框宽度", required = 0)
    private Integer bw;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        if (bg != null) {
            json.put("bg", bg);
        }
        if (fc != null) {
            json.put("fc", fc);
        }
        if (fs != null) {
            json.put("fs", fs);
        }
        if (ff != null) {
            json.put("ff", ff);
        }
        if (bl != null) {
            json.put("bl", bl);
        }
        if (it != null) {
            json.put("it", it);
        }
        if (un != null) {
            json.put("un", un);
        }
        if (cl != null) {
            json.put("cl", cl);
        }
        if (ht != null) {
            json.put("ht", ht);
        }
        if (vt != null) {
            json.put("vt", vt);
        }
        if (bc != null) {
            json.put("bc", bc);
        }
        if (bw != null) {
            json.put("bw", bw);
        }
        
        return json;
    }
}
