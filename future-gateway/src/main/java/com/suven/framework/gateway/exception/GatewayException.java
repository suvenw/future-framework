package com.suven.framework.gateway.exception;

import com.suven.framework.gateway.result.ResultCode;

/**
 * Gateway 业务异常
 */
public class GatewayException extends RuntimeException {

    private final int code;

    public GatewayException(String message) {
        super(message);
        this.code = ResultCode.INTERNAL_ERROR.getCode();
    }

    public GatewayException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GatewayException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public GatewayException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
