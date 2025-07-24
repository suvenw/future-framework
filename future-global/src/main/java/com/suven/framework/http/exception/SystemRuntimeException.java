package com.suven.framework.http.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.function.Supplier;


/**
 * @author vincentdeng
 *
 */
public class SystemRuntimeException extends RuntimeException implements Supplier<SystemRuntimeException> {

	private static Logger logger = LoggerFactory.getLogger(SystemRuntimeException.class);


	/**
	 *
	 */
	private static final long serialVersionUID = -1349660295724046112L;

	private int errorCode;

	private String errorMessage;

	private Object response;


	private IResultCodeEnum error;


	public SystemRuntimeException() {
		super();
	}


	public SystemRuntimeException(IResultCodeEnum error)  {
		super(error.getMsg());
		this.errorCode =  error.getCode();
		this.errorMessage = error.getMsg();
		this.error = error;
	}

    public SystemRuntimeException(IResultCodeEnum error, String... errParam)  {
		super(null == errParam ? error.getMsg() : String.format(error.getMsg(), new Object[] { errParam }));
		this.errorCode =  error.getCode();
		this.errorMessage = super.getMessage();
		this.error = error;

    }



	public SystemRuntimeException setResponse(Object response) {
		this.response = response;
		return this;
	}
	public SystemRuntimeException format(Object... errParam)  {
		this.errorMessage = String.format(this.errorMessage, errParam);
		return this;

	}

	public SystemRuntimeException(String message, Throwable cause,
                                  boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemRuntimeException(String message) {
		super(message);
	}

	public SystemRuntimeException(Throwable cause) {
		super(cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResponse() {
		return response;
	}


	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	@Override
	public SystemRuntimeException get() {
		return this;
	}

	/**
	 * @return 返回 error。
	 */

	public IResultCodeEnum getError() {

		return error;
	}

}
