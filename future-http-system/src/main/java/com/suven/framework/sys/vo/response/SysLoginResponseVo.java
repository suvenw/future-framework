package com.suven.framework.sys.vo.response;


import com.suven.framework.http.api.ApiDesc;

import java.util.List;

public class SysLoginResponseVo {


    @ApiDesc(value = "用户信息", required = 0)
    private SysUserResponseVo userInfo;
    @ApiDesc(value = "登陆token", required = 0)
    private String token;

    @ApiDesc(value = "部门类型:0,1,2", required = 0)
    private int multi_depart;
    @ApiDesc(value = "部门信息", required = 0)
    private List<SysDepartResponseVo> departs;

    public static  SysLoginResponseVo build() {
        return new SysLoginResponseVo();
    }


    public int getMulti_depart() {
        return multi_depart;
    }

    public SysLoginResponseVo setMulti_depart(int multi_depart) {
        this.multi_depart = multi_depart;
        return this;
    }

    public SysUserResponseVo getUserInfo() {
        return userInfo;
    }

    public SysLoginResponseVo setUserInfo(SysUserResponseVo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SysLoginResponseVo setToken(String token) {
        this.token = token;
        return this;
    }

    public List<SysDepartResponseVo> getDeparts() {
        return departs;
    }

    public SysLoginResponseVo setDeparts(List<SysDepartResponseVo> departs) {
        this.departs = departs;
        return this;
    }
}
