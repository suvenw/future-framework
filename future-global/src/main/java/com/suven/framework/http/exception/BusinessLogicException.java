package com.suven.framework.http.exception;

import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.function.Supplier;

/**
 * Created by Alex on 2014/9/3
 */
public class BusinessLogicException extends Exception implements Supplier<BusinessLogicException> {

    private IResultCodeEnum error;

    private int errorCode;

    private String errorMessage;


    public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

    }

    public BusinessLogicException(IResultCodeEnum error) {
        super(error.getMsg());
        this.error = error;
        this.errorCode = error.getCode();
        this.errorMessage = error.getMsg();
    }

    public BusinessLogicException(IResultCodeEnum error, String... errParam) {
        super(error.getMsg());
        this.error = error;
        this.errorCode = error.getCode();
        this.errorMessage = String.format(error.getMsg(), new Object[] { errParam });
    }


    /**
     * @return 返回 error。
     */
    public IResultCodeEnum getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public BusinessLogicException get() {
        return this;
    }
}