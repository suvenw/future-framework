//package java.net.http;
//
//import java.io.IOException;
//import java.net.ProxySelector;
//import java.time.Duration;
//import java.util.concurrent.Future;
//
///**
// * For the Java 8 compatibility when compiled with JDK 11+.
// *
// * @author L.cm
// */
//public abstract class HttpClient {
//
//	public static Builder newBuilder() {
//		return null;
//	}
//
//	public abstract <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException;
//
//	public abstract <T> Future<HttpResponse<T>> sendAsync (HttpRequest request, HttpResponse.BodyHandler<T>  responseBodyHandler)throws IOException, InterruptedException;;
//
//	public interface Builder {
//		Builder connectTimeout(Duration duration);
//
//		Builder proxy(ProxySelector proxySelector);
//
//		HttpClient build();
//	}
//}
