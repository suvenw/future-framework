package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class ApacheFutureCallback   implements FutureCallback<HttpResponse> , FutureCallbackProxy<HttpResponse,ApacheFutureCallback> {
    private HttpResponse httpResponse;
    final CompletableFuture<HttpResponse> future = new CompletableFuture<>();


    public static ApacheFutureCallback build(){
        return new ApacheFutureCallback();
    }

    @Override
    public HttpResponse getFutureResponse() {
        return httpResponse;
    }

    @Override
    public ApacheFutureCallback getFutureCallbackProxy() {
        return this;
    }



    @Override
    public Future<HttpResponse> getFuture() {
        return  future;
    }


//
//    /**
//     * 通过 void completed(FutureResponse futureResponse) 线程方法,初始化返回架构对象到当前类的属性,再返回对应的属性
//     * 通过调用线程,Future.get(),或 Future get(long timeout, TimeUnit unit)的方法实现结果,再转换
//     *
//     * @param responseBodyHandler 网络返回结果处理器
//     **/
//    @Override
//    public HttpClientResponse getHttpClientResponse(HttpResponseBodyHandler responseBodyHandler) {
//        HttpResponse httpResponse = getFutureResponse();
//        int code = this.getStatusCode(httpResponse);
//        try {
//            boolean successful = this.isSuccess(httpResponse);
//            Map<String, List<String>> headers = getHeaders(httpResponse);
//            //通过统一的返回结果拦截器，对返回结果进行统一处理，实现
//            String body = responseBodyHandler.handleResponseBody(httpResponse,responseBodyHandler.getContentType());
//            HttpClientResponse result = successfulResponse(successful,code,headers,body);
//            return result;
//
//        }catch (Exception e){
//            InputStream error = getContent(httpResponse);
//            this.createHttpException(code,error);
//            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
//        }
//    }


    @Override
    public int getStatusCode(HttpResponse response) {
        if(null == response){
            return HttpClientConstants.SC_BAD_REQUEST;
        }
        int statusCode =  response.getStatusLine().getStatusCode();
        return statusCode;
    }

    @Override
    public boolean isSuccess(HttpResponse response) {
        this.httpResponse = response;
        if (response == null) {
            return false;
        }
        if (response.getStatusLine() == null) {
            return false;
        }
        return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
    }



    @Override
    public Map<String, List<String>> getHeaders(HttpResponse httpResponse) {
        Map<String, List<String>> headers = Arrays.stream(httpResponse.getAllHeaders())
                .collect(Collectors.toMap(Header::getName, (value) -> {
                    ArrayList<String> headerValue = new ArrayList<>();
                    headerValue.add(value.getValue());
                    return headerValue;
                }, (oldValue, newValue) -> newValue));
        return headers;
    }






    public InputStream getContent(HttpResponse response) {
        if(null == response){
            return null;
        }
        try {
            InputStream is = response.getEntity().getContent();
            return is;
        }catch (Exception e){
            throw new HttpClientRuntimeException(e);
        }

    }
    /** FutureCallback 相关的业务接口 **/
    @Override
    public void completed(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        future.complete(httpResponse);
    }

    @Override
    public void cancelled() {
        future.cancel(false);
    }

    @Override
    public void failed(Exception ex) {
        future.completeExceptionally(ex);
    }
}
