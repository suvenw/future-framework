package com.suven.framework.util.supplier;

import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.function.Supplier;

/**
 * @author Created by 陈淦彬
 * @date 2022/8/3
 */
public class SystemRuntimeSupplier implements Supplier<SystemRuntimeException> {
    SystemRuntimeException exception;

    public SystemRuntimeSupplier(int errorCode, String errorMessage) {
        exception = new SystemRuntimeException(errorMessage);
        exception.setErrorCode(errorCode);
        exception.setErrorMessage(errorMessage);
    }

    public SystemRuntimeSupplier(String errorMessage) {
        exception = new SystemRuntimeException(errorMessage);
        exception.setErrorMessage(errorMessage);
    }

    public SystemRuntimeSupplier(IResultCodeEnum resultCodeEnum) {
        exception = new SystemRuntimeException(resultCodeEnum);
    }

    public SystemRuntimeSupplier(IResultCodeEnum resultCodeEnum, String... arg) {
        exception = new SystemRuntimeException(resultCodeEnum, arg);
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public SystemRuntimeException get() {
        return exception;
    }
}
