/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.api.IResponseResultPage;
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.inters.IResultCodeEnum;
import jakarta.servlet.ServletOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:  v1.0.0    修改人： suven  修改日期:  20160110   修改内容: 添加异常信息参数化 
 * </pre>
 */
public abstract class BaseHttpResponseWriteHandlerConverter extends BaseHttpResponseHandlerConverter implements IOutputStream, IResponseHandler {


	private	Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 * @param responseData
	 */
	@Override
	public void write(Object responseData, String... errParam)  {
		IResponseResult vo = this.getResultVo() ;
		this.writeResponseData(vo,responseData,errParam);
		this.writeStream(vo);
		if(vo.code() == SysResultCodeEnum.SYS_SUCCESS.getCode() ||  vo.success()){
			this.printSuccessLog(logger);
		}else {
			this.printErrorLogForRequestMessage(logger,vo.code(),vo.message());
		}
	}

	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 */
	@Override
	public void writeSuccess(){
		write(null);
	}

	/**
	 * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
	 * @param enumType
	 */
	@Override
	public void write(IResultCodeEnum enumType, Object errorToData)  {
		IResponseResult vo = this.getResultVo().of(errorToData) ;
		this.writeResponseData(vo,enumType);
		this.writeStream(vo);
		this.printErrorLogForRequestMessage(logger,vo.code(),vo.message());
	}

	/**
	 * 将List列表数据,返回封装返回,和是否有下一页
	 * @param responseDataList
	 * @param isNextPage
	 */
	@Override
	public void writeList(List<?> responseDataList, boolean isNextPage){
		IResponseResultPage<?> list = new PageResult()
				.toList(responseDataList).toIsNextPage(isNextPage);
		this.write(list);
	}

	/**
	 * 将List列表数据,返回封装返回,和是否有下一页
	 * @param responseDataList
	 * @param isNextPage
	 */
	@Override
	public void writeList(List responseDataList, boolean isNextPage, long pageIndex, int total){
		PageResult list = new PageResult<>();
		list.toList(responseDataList)
				.toIsNextPage(isNextPage)
				.toPageIndex(pageIndex)
				.toTotal(total);
		this.write(list);
	}




	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * Object 为返回的完整的结构responseResultVo对象
	 * 直接将参数对象转换成json字符串再按字节流返回用户端
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 */
	@Override
	public void writeResult(Object responseResultVo) {
		if(responseResultVo == null){
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL);
		}
		this.writeStream(responseResultVo);
		this.printSuccessLog(logger);
	}
	/**
	 *
	 * 自定义返回结果格式对象,取代或重写ResponseResultVo,按自定义格式返回对象转换成指定对象实现逻辑方法
	 * 兼容错误协议封装处理逻辑实现,再将对象转换成json字符串再按字节流返回用户端
	 * @param responseData
	 */
	@Override
	public void writeResult(IResponseResult responseResultVo, Object responseData, String... errParam)  {
		//组合错误信息
		if(responseResultVo == null){
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL);
		}
//		ResponseResultVo vo = responseResultVo.buildResponseResultVo();
		this.writeResponseData(responseResultVo,responseData,errParam);
		this.writeStream(responseResultVo);
		if(responseResultVo.code() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
			this.printSuccessLog(logger);
		}else {
			this.printErrorLogForRequestMessage(logger,responseResultVo.code(), responseResultVo.message());
		}
	}

	/**
	 * 将被下载文件流,通过文件名下载
	 * @param fileName
	 * @param data
	 */
	@Override
	public void writeStream(String fileName , byte[] data){
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename='" + fileName + " '");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");
			ServletOutputStream output = response.getOutputStream();
			if(null == output){
				return ;
			}
			output.write(data);
			output.flush();
			output.close();
		} catch (Exception e1) {
			logger.error("type=Exception,  ResponseError=[{}]", e1);
		}finally {
//			if(!Env.isProd()){
				logger.warn("type=Success, fileName=[{}]", fileName);
//			}
			this.printErrorLogForRequestMessage(logger,0,"");

		}
	}

}
