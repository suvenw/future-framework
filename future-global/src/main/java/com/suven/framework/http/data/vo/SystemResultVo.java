package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.common.enums.SystemMsgCodeEnum;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IResponseResult;

import java.io.Serializable;

/**
 * @Title: ResponseResultVo.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口返回拉口的统一封装对象类;
 */
@ApiDesc(value= "接口返回对象")
public class SystemResultVo implements IResponseResult,Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	@ApiDesc(value= "成功标志 ")
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	@ApiDesc(value= "返回处理消息 ")
	private String message = "";

	/**
	 * 返回代码
	 */
	@ApiDesc(value= "返回代码 ")
	private Integer code = 0;
	
	/**
	 * 返回数据对象 data
	 */
	@ApiDesc(value= "返回数据对象 ")
	private Object result = new JSONObject();    //Json 内容
	
	/**
	 * 时间戳
	 */
	@ApiDesc(value= "时间戳 ")
	private long timestamp = System.currentTimeMillis();


	public static SystemResultVo build() {
		SystemResultVo r = new SystemResultVo();
		r.setSuccess(true);
		r.setCode(SystemMsgCodeEnum.SYS_SC_OK_200.getCode());
		return r;
	}


	@Override
	public int code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public boolean success() {
		return success;
	}

	@Override
	public IResponseResult of(int code, String message) {
		this.success = false;
		this.code = code;
		this.message = message;
		return this;
	}

	@Override
	public IResponseResult of(boolean success, int code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.success = success;
		this.result = initData(result);
		return this;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


}