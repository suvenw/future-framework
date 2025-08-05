package com.suven.framework.common.enums;

/**
 * @author Joven.wang
 * @version V1.0
 * Title: GlobalServiceInfo.java
 * date 2019年2月20日
 * Description: TODO(说明)
 * 账号是否可用的属性值枚举类
 */
public enum AccountEnum {

    ALLOW(0, "正常"),
    WARN(1, "警告"),
    BANNED(2, "禁言"),
    STATUS(3, "封禁"),//封禁多少小时类的
    BLACK(4, "黑名单"),//对于手机设置封禁使用
    FORBID(5, "永久封号"),
    ;

    private int value;
    private String name;

    AccountEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
