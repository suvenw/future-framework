package com.suven.framework.sys.utils;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2022-02-28
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  错误提示中文信息类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public interface SystemParamConstant {
    interface Sys{
        final String SYS_CAPTCHA_CODE_ERROR = "验证码错误";
        final String USER_NAME_IS_NOT_NULL = "用戶号码或者邮箱不能为空";
        final String SIGN_IS_NOT_NULL = "密钥不能为空";
        final String VERSION_IS_NOT_NULL = "版本不能为空";
        final String CLIENT_IS_NOT_NULL = "客户端平台不能为空";
        final String TIMESTAMP_IS_NOT_NULL = "时间戳不能为空";
        final String CHECK_S_IS_NOT_NULL = "校验参数不能为空";
        final String ENCRYPT_PARM_IS_NOT_NULL = "加密参数不能为空";
        final String CUID_IS_NOT_NULL = "用户识别号不能为空";

    }
}
