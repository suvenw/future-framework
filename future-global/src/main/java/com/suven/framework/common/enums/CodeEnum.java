package com.suven.framework.common.enums;

import com.suven.framework.http.inters.IResultCodeEnum;

public  enum CodeEnum implements IResultCodeEnum {
    /** 系统返回错误码 10000~10099*/
    /** 行为类型　０１－２０**/
    SYS_UNKOWNN_FAIL(11003001, "操作失败!"),
    SYS_TOKEN_NULL(1100002, "请重新登录"),
    SYS_PROJECT_MAINTAIN(1100003,"服务维护中！"),
    SYS_LOGIN_CODE_FAIL(1100005, "验证码失效。"),
    SYS_RESPONSE_RESULT_IS_NULL(1100006, "系统规范模板类不能为空。"),
    SYS_RESPONSE_QUERY_IS_NULL(1100007, "查询条件对象不能为空。"),
    SYS_NOT_HAVE_PERMISSION(1100008, "无权限操作！%s"),

    DATA_NOT_FOUND(110030010,"11111"),
    FILE_IS_EMPTY(11003012,"文件为空"),
    PARAM_IS_INVALID(11003013, "SaaS文件删除参数错误, ID列表为空");

    private int code;
    private String message;


    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 系统响应码
     *
     * @return code
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 默认系统响应提示，code=0时此处为空
     *
     * @return msg
     */
    @Override
    public String getMsg() {
        return message;
    }
}
