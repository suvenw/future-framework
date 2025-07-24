/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.message.ParameterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: OutputResponse.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class OutputAllResponse extends BaseHttpResponseWrite implements IResponseHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());



	/**
	 * 统一出口,写流和cdn信息
	 */
	@Override
	protected void writeStream(Object responseResultVo) {

		if(null == responseResultVo){
			super.writeSuccess();
			return;
		}
		IResponseResult iResponseResult = getResultVo();
		int dataType = ParameterMessage.getRequestMessage().getDataType();
		switch (dataType){
			case 1:
				/*** ----------将返回结果进行缓存到redis中---------- ***/
				this.cacheDataResultVo(responseResultVo);
				break;
			case 2:
				/*** ----------将返回结果进行aes加密处理---------- ***/
				this.aesDateResultVo(iResponseResult,responseResultVo);
				break;
			case 3:
				/*** ----------将返回结果进行aes加密处理---------- ***/
				this.aesDateResultVo(iResponseResult,responseResultVo);
				/*** ----------将返回结果进行缓存到redis中---------- ***/
				this.cacheDataResultVo(responseResultVo);
				break;
		}
		super.writeStream(responseResultVo);
	}








	@Override
	public String toString() {
		long exeTime = System.currentTimeMillis() - ParameterMessage.getRequestMessage().getTimes();
		String outLogger =  "OutputCacheResponse{" +
				"" + ParameterMessage.getRequestMessage().toString() +
				" [ code = "+ errorCodeEnum.getCode() +
				", msg = " + errorCodeEnum.getMsg() +
				"] "+
				"responseEndTime = " + exeTime
				+"}";
		logger.warn(outLogger);
		return outLogger;
	}



}
