package com.suven.framework.sys.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByUserIdVo;

public class TokenRequestVo  extends HttpRequestByUserIdVo {

    @ApiDesc(value= "用户token " )
    private String token;

    public String getToken() {
        return token;
    }

    public TokenRequestVo setToken(String token) {
        this.token = token;
        return this;
    }
}
