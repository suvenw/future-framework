package com.suven.framework.sys.vo.response;

import com.suven.framework.http.api.ApiDesc;

public class UserRoleVo {
    @ApiDesc("id")
    private long id;
    @ApiDesc("label")
    private String label;

    public static UserRoleVo build(){
        return new UserRoleVo();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
