package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;

public class HttpRequestByUserIdVo extends HttpRequestByIdVo {

    @ApiDesc(value= "用户id")
    private long userId;


    public long getUserId() {
        if(userId == 0){
            userId =  getId();
        }
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
