package com.suven.framework.file.dto.luckysheet;

import com.alibaba.fastjson2.JSONObject;
import com.suven.framework.http.api.ApiDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Luckysheet 保护配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckySheetProtect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否启用保护
     */
    @ApiDesc(value = "是否启用保护", required = 0)
    private Boolean enable;

    /**
     * 保护密码
     */
    @ApiDesc(value = "保护密码", required = 0)
    private String password;

    /**
     * 允许的操作列表
     */
    @ApiDesc(value = "允许的操作", required = 0)
    private List<String> allow;

    /**
     * 转换为 JSON 对象
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (enable != null) {
            json.put("enable", enable);
        }
        if (password != null) {
            json.put("password", password);
        }
        if (allow != null) {
            json.put("allow", allow);
        }
        return json;
    }
}
