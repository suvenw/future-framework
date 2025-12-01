package com.suven.framework.http.handler;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.HttpRequestTypeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.ParameterMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;



/**
 * @ Title: JsonReturnHandlerArgumentResolver.java
 * @author Joven.wang
 * @ date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口统一方法请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */

public class ResponseJsonHandlerArgumentResolver extends AbstractHandlerArgumentResolver<IResponseHandler> {


    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory) throws Exception{

        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpRequestTypeEnum httpRequestTypeEnum = HttpRequestTypeEnum.TYPE_JSON;
        /** **/
        Class<?> responseClass = parameter.getParameterType();
        return parserDate(request, response,responseClass, httpRequestTypeEnum);
    }

    @Override
    public Object parserDate(HttpServletRequest webRequest, HttpServletResponse response, Class<?> responseClass, HttpRequestTypeEnum httpRequestTypeEnum) {

        boolean isExtendsRequestVo =  IResponseHandler.class.isAssignableFrom(responseClass);
        if(!isExtendsRequestVo){
            String errorMessage = "receive client IResponseVo Object class :["+responseClass+"] not extends BaseHttpResponseWriteHandlerConverter or implements IResponseVo ";
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(errorMessage);
        }
        try {
            IResponseHandler responseVo = (IResponseHandler) responseClass.getDeclaredConstructor().newInstance();
            responseVo.initResponse(response);
            /** 用于异常处理,返回统一规范对象 **/
            ParameterMessage.setResponseResult(responseVo.getResultVo());

            return responseVo;
        }catch (Exception e){
            log.info("JsonResponseHandlerArgumentResolver parserDate method Exception " ,e);
            if(e instanceof SystemRuntimeException){
                throw  (SystemRuntimeException)e;
            }else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR.format(e.getCause().getMessage()));
            }
        }

    }


}