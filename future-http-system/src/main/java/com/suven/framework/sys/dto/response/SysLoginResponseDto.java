package com.suven.framework.sys.dto.response;



import com.suven.framework.http.data.entity.BaseTimeEntity;

import java.util.List;

public class SysLoginResponseDto extends BaseTimeEntity {

    private int multi_depart;
    private SysUserResponseDto userInfo;

    private String token;

    private List<SysDepartResponseDto> departs;


    public static SysLoginResponseDto build(){
        return new SysLoginResponseDto();
    }

    public int getMulti_depart() {
        return multi_depart;
    }

    public void setMulti_depart(int multi_depart) {
        this.multi_depart = multi_depart;
    }

    public SysUserResponseDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SysUserResponseDto userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SysDepartResponseDto> getDeparts() {
        return departs;
    }

    public void setDeparts(List<SysDepartResponseDto> departs) {
        this.departs = departs;
    }
}
