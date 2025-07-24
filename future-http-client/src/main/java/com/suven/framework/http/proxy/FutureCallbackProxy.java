package com.suven.framework.http.proxy;


import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 线程代理接口实现;
 * @param <FutureResponse>
 * @param <FutureCallback>
 */
public interface FutureCallbackProxy<FutureResponse,FutureCallback>  {

    /**1.获取对应 各业务方自定义的实现逻辑的线程**/
    FutureCallback getFutureCallbackProxy();

//
//    /** 2.最终统一的想法结果, 各业务方架构返回的业务对象转换成现在规范统一聚合的请求对象**/
//    /** 通过 void completed(FutureResponse futureResponse) 线程方法,初始化返回架构对象到当前类的属性,再返回对应的属性
//     *  通过调用线程,Future.get(),或 Future get(long timeout, TimeUnit unit)的方法实现结果,再转换
//     *
//     * **/
//    HttpClientResponse getHttpClientResponse(HttpResponseBodyHandler responseBodyHandler);

//    /** 3.返回业务的结果对象,实现自己需要实现的逻辑**/
//     void initCompleted(FutureResponse futureResponse);


     /** 返回异步处理线程 **/
    Future<FutureResponse> getFuture();

    /** 4.获取各业务方自定义的实现逻辑的线程返回的结果对象**/
    FutureResponse getFutureResponse();
    /**
     *  5.返回业务的结果对象,实现自己需要实现的逻辑
     * 通过proxy统一转换成统一的返回结果实现类
     * 结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,
     * @param httpResponse 网络请求框架返回结果对象
     * @return 网络请求框架返回结果成功、失败状态
     */
     boolean isSuccess(FutureResponse httpResponse) ;
    /**
     * 6.结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,通过proxy统一转换成统一的返回结果实现类
     * @param httpResponse 网络请求框架返回结果对象
     * @return 网络请求框架返回结果状态码
     */
    int getStatusCode(FutureResponse httpResponse) ;

    /**
     *  5.返回业务的结果对象,实现自己需要实现的逻辑
     * 通过proxy统一转换成统一的返回结果实现类
     * 结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,
     * @param httpResponse 网络请求框架返回结果对象
     * @return 返回网络的请求头信息集合
     */
    Map<String, List<String>> getHeaders(FutureResponse httpResponse) ;

//    /**
//     * 6.结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,通过proxy统一转换成统一的返回结果实现类
//     *
//     * @param httpResponse 网络请求框架返回结果对象
//     * @param contentType 请求网络请求数据类型
//     * @return 返回 contentType 对应的结果内容信息
//     */
//    String handleResponseBody(FutureResponse httpResponse, int contentType) throws Exception;

    /**
     * 返回统一正常结果实现
     * @param successful
     * @param code
     * @param headers
     * @param body
     * @return
     */
    default HttpClientResponse successfulResponse( boolean successful, int code ,
                                                     Map<String, List<String>> headers, String body){
        return  HttpClientResponse.build(successful, code, headers, body, null);
    }


    /**
     * 返回统一异常结果实现
     * @param exception
     * @return
     */
    default HttpClientResponse errorResponse(Exception exception){
        return  HttpClientResponse.build(false, 500, null, null, exception.getMessage());
    }



    /**}
     * Create the appropriate exception for the given HTTP response.
     *
     * @param response HTTP response
     * @return {@link HttpClientRuntimeException} instance
     */
    default  HttpClientRuntimeException createHttpException(int statusCode, InputStream inputContent) {
        if (statusCode == HttpClientConstants.SC_UNAUTHORIZED) {
            return new HttpClientRuntimeException(
                    "Salt user does not have sufficient permissions");
        } else {
            String content = "";
            try {
                content = new BufferedReader(new InputStreamReader(inputContent))
                        .lines().parallel().collect(Collectors.joining("\n"));
            } catch (Exception e) {
                // error trying to get the response body, nothing to do...
            }finally {
                try {
                    if(null != inputContent){
                        inputContent.close();
                    }
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
            return new HttpClientRuntimeException("Response code: " + statusCode + ". Response body:\n" + content);
        }
    }



}
