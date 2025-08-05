package com.suven.framework.sys.vo.response;


import com.suven.framework.http.api.ApiDesc;

public class LoginCodeResponseVo {

    @ApiDesc("验证码key")
    private String key;
    @ApiDesc("验证码code")
    private String code;

    @ApiDesc("base64")
    private String base64;


    public static LoginCodeResponseVo build(){
        return new LoginCodeResponseVo();
    }

    public String getBase64() {
        return base64;
    }

    public LoginCodeResponseVo setBase64(String base64) {
        this.base64 = base64;
        return this;
    }

    public String getKey() {
        return key;
    }

    public LoginCodeResponseVo setKey(String key) {
        this.key = key;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LoginCodeResponseVo setCode(String code) {
        this.code = code;
        return this;
    }
}
