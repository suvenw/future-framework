package com.suven.framework.http.resolver;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.JsonParse;
import com.suven.framework.http.api.HttpFromRequest;
import com.suven.framework.http.api.HttpHeaderRequest;
import com.suven.framework.http.api.HttpJsonRequest;
import com.suven.framework.http.api.HttpRequestTypeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Objects;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @ description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2024-01-02
 **/
public class HttpRequestArgumentParser extends HttpRequestArgumentResolver implements IHttpRequestArgumentParser {

    /**
     * 通过 Http header http头部格式传输信息
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param headerClass         接收请求头信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收请求头信息参数对象
     */
    @Override
    public <T> T parseHeaderData(HttpServletRequest request, HttpServletResponse response, Class<T> headerClass, HttpRequestTypeEnum httpRequestTypeEnum) {
        if (Objects.isNull(request) || Objects.isNull(response) || Objects.isNull(headerClass)) {
            return null;
        }
        boolean isType = Objects.equals(HttpRequestTypeEnum.TYPE_HEADER,httpRequestTypeEnum);
        boolean isRequest = HttpHeaderRequest.class.isAssignableFrom(headerClass);
        if (!isRequest || !isType) {
            String errorMessage = "receive client request Object header Class :[" + headerClass + "] not  implements HttpHeaderRequest ";
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_HEADER_ERROR.format(errorMessage));

        }
        try {
            T t = null;
            Map<String, String> headersMap = this.getRequestHeadersBody(request,true);
            if (Objects.isNull(headersMap)) {
                return t;
            }
            t = this.parseHeader(headersMap, headerClass);
            return t;

        } catch (Exception e) {
            log.info("HttpRequestArgumentParser parseHeaderData parameter Exception ", e );
            if (e instanceof SystemRuntimeException) {
                throw (SystemRuntimeException) e;
            } else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_HEADER_ERROR.format(e.getCause().getMessage()));
            }
        }

    }

    /**
     * 通过 Http FORM 表单格式请求
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param fromClass           接收表单请求信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收表单请求信息参数对象
     */
    @Override
    public <T> T parseFromData(HttpServletRequest request, HttpServletResponse response, Class<T> fromClass, HttpRequestTypeEnum httpRequestTypeEnum) {
        if (Objects.isNull(request) || Objects.isNull(response) || Objects.isNull(fromClass)) {
            return null;
        }
        boolean isType = Objects.equals(HttpRequestTypeEnum.TYPE_FORM,httpRequestTypeEnum);
        boolean isRequest = HttpFromRequest.class.isAssignableFrom(fromClass);
        if (!isRequest || !isType) {
            String errorMessage = "receive client request Object from Class :[" + fromClass + "] not implements HttpFromRequest ";
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_FORM_ERROR).format(errorMessage);
        }
        try {
            T t = null;
            Map<String, Object> formMap = this.getRequestFormBody(request);
            if (Objects.isNull(formMap)) {
                return t;
            }
            t = this.parseFrom(formMap, fromClass);
            return t;

        } catch (Exception e) {
            log.info("HttpRequestArgumentParser parseFromData parameter Exception ", e );
            if (e instanceof SystemRuntimeException) {
                throw (SystemRuntimeException) e;
            } else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_FORM_ERROR.format(e.getCause().getMessage()));
            }
        }
    }

    /**
     * 通过 Http JSON 格式数据请求
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param jsonClass           接收JSON请求信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收JSON请求信息参数对象
     */
    @Override
    public <T> T parseJsonData(HttpServletRequest request, HttpServletResponse response, Class<T> jsonClass, HttpRequestTypeEnum httpRequestTypeEnum) {
        if (Objects.isNull(request) || Objects.isNull(response) || Objects.isNull(jsonClass)) {
            return null;
        }
        boolean isType = Objects.equals(HttpRequestTypeEnum.TYPE_JSON,httpRequestTypeEnum);
        boolean isRequest = HttpJsonRequest.class.isAssignableFrom(jsonClass);
        if (!isRequest || !isType) {
            String errorMessage = "receive client request Object from Class :[" + jsonClass + "] not implements HttpJsonRequest ";
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_FORM_ERROR).format(errorMessage);
        }
        try {
            T t = null;
            String jsonBody = this.getRequestJsonBody(request);
            if (Objects.isNull(jsonBody)) {
                return t;
            }
            t = this.parseJson(jsonBody, jsonClass);
            return t;

        } catch (Exception e) {
            log.info("HttpRequestArgumentParser parseJsonData parameter Exception ", e );
            if (e instanceof SystemRuntimeException) {
                throw (SystemRuntimeException) e;
            } else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_FORM_ERROR.format(e.getCause().getMessage()));
            }
        }
    }


    /**
     * http 表单提交实现java反射类对象;
     *
     * @param map Map<String, Object> map 表单提交的数据
     * @param fromClass 表单提供参数 转换成对象类信息
     * @return T 返回转换对象
     */
    @Override
    public <T> T parseFrom(Map<String, Object> map, Class<T> fromClass) throws Exception {
        T t = JsonParse.parseFrom(map, fromClass);
        return t;
    }


    /**
     * http 提交参数头部实现java反射类对象;
     *
     * @param headersMap  http头的聚合信息
     * @param headerClass 反射接收类对象
     * @return T 返回转换对象
     */
    @Override
    public <T> T parseHeader(Map<String, String> headersMap, Class<T> headerClass, boolean isCompatible) {
        T t = JsonParse.parseHeaders(headersMap, headerClass, isCompatible);
        return t;
    }

    /**
     * http json提交参数实现java反射类对象;
     *
     * @param jsonString 反射字符串内容
     * @param jsonClass  反射接收类对象
     * @return T 返回转换对象
     */
    @Override
    public <T> T parseJson(String jsonString, Class<T> jsonClass) {
        T t = JsonParse.parseJson(jsonString, jsonClass);
        return t;
    }
}
