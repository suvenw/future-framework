package com.suven.framework.http.exception;


import com.suven.framework.http.inters.IResultCodeEnum;

/**
 * @ Description   :  异常工厂实现
 * @ Author        :  suven
 * @ CreateDate    :  2023-12-21
 * @ Version       :  1.0
 */
public class ExceptionFactory {


    public static SystemRuntimeException sysException(IResultCodeEnum errorEnum) {
        return new SystemRuntimeException(errorEnum);
    }
    public static SystemRuntimeException sysException(IResultCodeEnum errorEnum, String... errorMessage) {
        return new SystemRuntimeException(errorEnum,errorMessage);
    }


    public static BusinessLogicException bizException(IResultCodeEnum errorEnum) {
        return new BusinessLogicException(errorEnum);
    }
    public static BusinessLogicException bizException(IResultCodeEnum errorEnum, String... errorMessage) {
        return new BusinessLogicException(errorEnum,errorMessage);
    }


}
