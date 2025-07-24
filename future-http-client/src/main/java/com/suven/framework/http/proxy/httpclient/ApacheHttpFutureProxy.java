//package com.suven.framework.http.proxy.httpclient;
//
//import com.suven.framework.http.constants.HttpClientConstants;
//import com.suven.framework.http.exception.HttpClientRuntimeException;
//import com.suven.framework.http.proxy.AbstractFutureProxy3;
//import com.suven.framework.http.proxy.FutureCallbackProxy;
//import com.suven.framework.http.proxy.HttpClientResponse;
//import org.apache.http.Header;
//import org.apache.http.HttpResponse;
//import org.apache.http.util.EntityUtils;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class ApacheHttpFutureProxy extends AbstractFutureProxy3<HttpResponse,ApacheFutureCallback>
//{
//
//    public static ApacheHttpFutureProxy build(){
//        ApacheFutureCallback future =  new  ApacheFutureCallback();
//        FutureCallbackProxy  futureCallbackProxy = new ApacheHttpFutureProxy(future);
//        return new ApacheHttpFutureProxy(future,futureCallbackProxy);
//    }
//
//
//    public ApacheHttpFutureProxy( FutureCallbackProxy futureCallbackProxy ){
//        super(future,futureCallbackProxy);
//    }a
//
//    @Override
//    public int getStatusCode(HttpResponse response) {
//        if(null == response){
//            return HttpClientConstants.SC_BAD_REQUEST;
//        }
//       int statusCode =  response.getStatusLine().getStatusCode();
//        return statusCode;
//    }
//
//    @Override
//    public InputStream getContent(HttpResponse response) {
//        if(null == response){
//            return null;
//        }
//        try {
//            InputStream is = response.getEntity().getContent();
//            return is;
//        }catch (Exception e){
//            throw new HttpClientRuntimeException(e);
//        }
//
//    }
//
//    @Override
//    public boolean isSuccess(HttpResponse response) {
//        if (response == null) {
//            return false;
//        }
//        if (response.getStatusLine() == null) {
//            return false;
//        }
//        return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
//    }
//
//
//    @Override
//    public HttpClientResponse completeResult(HttpResponse response){
//        try {
//            StringBuffer body = new StringBuffer();
//            if (response.getEntity() != null) {
//                body.append(EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING));
//            }
//            int code = response.getStatusLine().getStatusCode();
//            boolean successful = isSuccess(response);
//            Map<String, List<String>> headers = Arrays.stream(response.getAllHeaders())
//                    .collect(Collectors.toMap(Header::getName, (value) -> {
//                        ArrayList<String> headerValue = new ArrayList<>();
//                        headerValue.add(value.getValue());
//                        return headerValue;
//                    }, (oldValue, newValue) -> newValue));
//            return  HttpClientResponse.build(successful, code, headers, body.toString(), null);
//        }catch (Exception e){
//            this.completeExceptionally(response);
//            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
//        }
//
//    }
//
//
//    @Override
//    public void completed(HttpResponse result) {
//        super.completed(result);
//    }
//
//    @Override
//    public ApacheFutureCallback getFutureCallbackProxy() {
//        return (ApacheFutureCallback)futureCallbackProxy.getFutureCallbackProxy();
//    }
//}
