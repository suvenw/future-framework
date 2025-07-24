package com.suven.framework.common.enums;

/**
 * @Auther: suven.wang
 * @Date: 2018/8/21 12:01
 * Description: 短信验证类型属性值枚举类, 包括:1.注册验证码 ,2.绑定手机, 3.重置密码, 4.解绑邮箱, 5.绑定设备
 */
public enum SmsValidEnum {
    SIGN_UP(1, "注册验证码"),
    BIND_USER_PHONE(2, "绑定手机"),
    RESET(3, "重置密码"),
    UN_BIND_EMAIL(4, "解绑邮箱"),
    BIND_PAD(5, "绑定设备");
    private int id;
    private String desc;

    SmsValidEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }


    public String getDesc() {
        return desc;
    }

}
