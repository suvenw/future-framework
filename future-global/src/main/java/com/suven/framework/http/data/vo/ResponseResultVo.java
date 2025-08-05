package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.api.IResponseResultPage;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.apache.commons.lang3.ClassUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Title: ResponseResultVo.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口返回拉口的统一封装对象类;
 */

public class ResponseResultVo implements IResponseResult, Serializable {

	private int code = 0;     //状态码
	private String msg = "";     //状态描述
	private long times = System.currentTimeMillis();        //时间戳 13位
	private Object data = new JSONObject();    //Json 内容

	public ResponseResultVo(){

	}

	public static IResponseResult build(){
		return new ResponseResultVo();
	}


	@Override
	public int code() {
		return code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String message() {
		return msg;
	}

	@Override
	public boolean success() {
		return true;
	}

	@Override
	public IResponseResult of(int code, String message) {
		this.code = code;
		this.msg = message;
		return this;
	}

	@Override
	public IResponseResult of(boolean success, int code, String message, Object result) {
		this.code = code;
		this.msg = message;
		this.data = this.initData(result);
		return this;
	}


	public Object getData() {
		return data;
	}



	/**
	 *  在此处对响应体进行处理,用于拦截器,统一处理封包逻辑
	 *  可以修改、包装、添加额外的数据等
	 *  返回最终的响应体
	 *  示例：在响应体中添加额外的数据
	 *  异常类型
	 * @param body
	 * @return  Object 结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object initData( Object body ){
		if(body instanceof IResultCodeEnum){
			IResultCodeEnum codeEnum = (IResultCodeEnum) body;
			IResponseResult returnVo =   build().of(codeEnum.getCode(),codeEnum.getMsg());
			return returnVo;
		}//标准规范的对象接口
		if (body instanceof IResponseResult) {
			return body ;
		}
		//自定义接口继承类
		if(body instanceof IBaseApi || body instanceof IResponseResultPage){
			IResponseResult returnVo =   build().of(body);
			return returnVo;
		}//常量类型
		if(ClassUtils.isPrimitiveOrWrapper(body.getClass())){
			JSONObject data = new JSONObject();
			data.put("result",body);
			if(body instanceof  Boolean) {
				int value = (Boolean) body ? 1 : 0;
				data.put("result", value);
			}
			IResponseResult returnVo =   build().of(data);
			return returnVo;
		}
		if(body instanceof String){
			IResponseResult returnVo =  build().of(body);
			return returnVo;
		}
		if(body.getClass().isArray()){
			List data = Arrays.asList((Object[]) body);
			ResponseResultPageVo<?> pageVo = new ResponseResultPageVo<>().of(data,data.size());
			return pageVo;
		}
		return body;
	}

}
