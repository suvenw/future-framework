package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class OkHttp3FutureCallback   extends CompletableFuture<Response> implements Callback, FutureCallbackProxy<Response,Callback> {


    public static OkHttp3FutureCallback build(){
        return new OkHttp3FutureCallback();
    }
    private Response response;
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        super.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.complete(response);
        this.response = response;
    }


    @Override
    public Future<Response> getFuture() {
        return this;
    }


    @Override
    public Callback getFutureCallbackProxy() {
        return this;
    }

//    /**
//     * 通过 void completed(FutureResponse futureResponse) 线程方法,初始化返回架构对象到当前类的属性,再返回对应的属性
//     * 通过调用线程,Future.get(),或 Future get(long timeout, TimeUnit unit)的方法实现结果,再转换
//     * @param responseBodyHandler 网络返回结果处理对象
//     **/
//    @Override
//    public HttpClientResponse getHttpClientResponse(HttpResponseBodyHandler responseBodyHandler) {
//
//        try {
//            Response httpResponse = getFutureResponse();
//            if(null == httpResponse){
//                return null;
//            }
//            int code = this.getStatusCode(httpResponse);
//            boolean successful = this.isSuccess(httpResponse);
//            Map<String, List<String>> headers = getHeaders(httpResponse);
//            String body = responseBodyHandler.handleResponseBody(httpResponse,responseBodyHandler.getContentType());
//            HttpClientResponse result = HttpClientResponse.build(successful,code,headers,body,null);
//            return result;
//
//        }catch (Exception e){
//            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
//        }
//    }




//    @Override
    public void completed(Response response) {
        this.response = response;
    }

    @Override
    public Response getFutureResponse() {
        return response;
    }

    @Override
    public boolean isSuccess(Response response) {
        if (response == null) {
            return false;
        }
        return response.code() >= 200 && response.code() < 300;
    }


    @Override
    public int getStatusCode(Response response) {
        if(null == response){
            return HttpClientConstants.SC_BAD_REQUEST;
        }
        int statusCode =  response.code();
        return statusCode;
    }



    @Override
    public Map<String, List<String>> getHeaders(Response response) {
        Map<String, List<String>> headers = response.headers().toMultimap();
        return headers;
    }

    public InputStream getContent(Response response) {
        if(null == response){
            return null;
        }
        try (InputStream is = response.body().byteStream()){
            return is;
        }catch (Exception e) {
            throw new HttpClientRuntimeException(e);
        }

    }



}
