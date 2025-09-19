package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.api.IResponseResultPage;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
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

public class ResponseCovertResultVo{


	private static IResponseResult build(){
		return ResponseResultVo.build();
	}

	/**
	 *  在此处对响应体进行处理,用于拦截器,统一处理封包逻辑
	 *  可以修改、包装、添加额外的数据等
	 *  返回最终的响应体
	 *  示例：在响应体中添加额外的数据
	 *  异常类型
	 * @param body
	 * @param isNextPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object convertData(Object body, boolean isNextPage) {
		//.1.标准规范对象接口
		if (body instanceof IResponseResult) {
			 return body;
		 }//2.错误类型对象接口
		if (body instanceof IResultCodeEnum codeEnum) {
			IResponseResult returnVo = build().of(codeEnum.getCode(), codeEnum.getMsg());
			return returnVo;

		}//3.自定义接口继承类
		else if (body instanceof IBaseApi || body instanceof IResponseResultPage) {
			IResponseResult returnVo = build().of(body);
			return returnVo;
		}//4.常量类型转换 JSONObject 类对象
		if (ClassUtils.isPrimitiveOrWrapper(body.getClass())) {
			JSONObject data = new JSONObject();
			data.put("result", body);
			if (body instanceof Boolean) {
				int value = (Boolean)body ? 1 : 0;
				data.put("result", value);
			}
			IResponseResult returnVo = build().of(data);
			return returnVo;

		}//5.字符串类型转换
		else if (body instanceof String) {
			IResponseResult returnVo = build().of(body);
			return returnVo;
		}//6.数组类型转换  PageResult
		else if (body.getClass().isArray()) {
			List<Object> data = Arrays.asList(body);
			PageResult<Object> pageVo = new PageResult<>();
			pageVo.setList(data);
			return pageVo;
		} else {//未知类型
			return body;
		}

	}


}
