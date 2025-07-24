//package com.suven.framework.http.proxy.okhttp3;
//
//import com.suven.framework.http.constants.HttpClientConstants;
//import com.suven.framework.http.exception.HttpClientRuntimeException;
//import com.suven.framework.http.proxy.AbstractFutureProxy3;
//import com.suven.framework.http.proxy.FutureCallbackProxy;
//import com.suven.framework.http.proxy.HttpClientResponse;
//import okhttp3.Callback;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//
//import java.io.InputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//public class OkHttp3FutureProxy extends AbstractFutureProxy3<Response,OkHttp3FutureCallback> {
//
//    public static OkHttp3FutureProxy build(){
//        CompletableFuture future =  new  CompletableFuture();
//        FutureCallbackProxy  futureCallbackProxy = new OkHttp3FutureCallback(future);
//        return new OkHttp3FutureProxy(future,futureCallbackProxy);
//    }
//
//
//    public OkHttp3FutureProxy(CompletableFuture<HttpClientResponse> future, FutureCallbackProxy<Response,Callback> futureCallbackProxy ){
//        super(future,futureCallbackProxy);
//    }
//
//    @Override
//    public int getStatusCode(Response response) {
//        if(null == response){
//            return HttpClientConstants.SC_BAD_REQUEST;
//        }
//       int statusCode =  response.code();
//        return statusCode;
//    }
//
//    @Override
//    public InputStream getContent(Response response) {
//        if(null == response){
//            return null;
//        }
//        try {
//            InputStream is = response.body().byteStream();
//            return is;
//        }catch (Exception e){
//            throw new HttpClientRuntimeException(e);
//        }
//
//    }
//
//    @Override
//    public boolean isSuccess(Response response) {
//        if (response == null) {
//            return false;
//        }
//        return response.code() >= 200 && response.code() < 300;
//    }
//
//
//    @Override
//    public HttpClientResponse completeResult(Response response){
//        try {
//            int code = response.code();
//            boolean successful = response.isSuccessful();
//            Map<String, List<String>> headers = response.headers().toMultimap();
//            ResponseBody responseBody = response.body();
//            String body = null == responseBody ? null : responseBody.string();
//            return HttpClientResponse.build(successful, code, headers, body, null);
//
//        }catch (Exception e){
//            this.completeExceptionally(response);
//            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
//        }
//
//    }
//
//
//    @Override
//    public OkHttp3FutureCallback getFutureCallbackProxy() {
//        return (OkHttp3FutureCallback)futureCallbackProxy.getFutureCallbackProxy();
//    }
//
//    @Override
//    public void completed(Response result) {
//        super.completed(result);
//    }
//}
