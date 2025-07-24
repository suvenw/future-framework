/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.suven.framework.http.proxy;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.openeuler.BGMProvider;

import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HttpSSLCipherSuiteUtil {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SSLCipherSuiteUtil.class);
    private static CloseableHttpClient httpClient;
    private static OkHttpClient okHttpClient;

    public static void createHttpClient(String protocol, HttpClientBuilder httpClient) throws Exception {
        SSLContext sslContext = getSslContext(protocol);
        // create factory
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{protocol}, null, new HttpSSLCipher.TrustAllHostnameVerifier());

        httpClient.setSSLSocketFactory(sslConnectionSocketFactory);
    }
    public static CloseableHttpClient createHttpClient() {
        try {
            String protocol = HttpClientConstants.INTERNATIONAL_PROTOCOL;

            SSLContext sslContext = getSslContext(protocol);
            // create factory
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{protocol}, null, new HttpSSLCipher.TrustAllHostnameVerifier());

            HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory);
            return httpClientBuilder.build();
        }catch (Exception e){
            throw new HttpClientRuntimeException(e);
        }
    }
    public static CloseableHttpAsyncClient createHttpAsyncClient() {
        try {
            String protocol = HttpClientConstants.INTERNATIONAL_PROTOCOL;

            SSLContext sslContext = getSslContext(protocol);


            PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
            CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setConnectionManager(cm)
                    .setSSLHostnameVerifier( new HttpSSLCipher.TrustAllHostnameVerifier())
                    .setSSLContext(sslContext)
                    .build();


            return httpclient;
        }catch (Exception e){
            throw new HttpClientRuntimeException(e);
        }
    }

    public static OkHttpClient createOkHttpClient(String protocol) throws Exception{

        SSLContext sslContext = getSslContext(protocol);
        // Create an ssl socket factory with our all-trusting manager
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, new HttpSSLCipher.TrustAllManager())
                .hostnameVerifier(new HttpSSLCipher.TrustAllHostnameVerifier());
        okHttpClient = builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        return okHttpClient;
    }
    public static OkHttpClient.Builder createOkHttpClientBuilder(String protocol,OkHttpClient.Builder httpClientBuilder)  {
        try {
            SSLContext sslContext = getSslContext(protocol);
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, new HttpSSLCipher.TrustAllManager())
                    .hostnameVerifier(new HttpSSLCipher.TrustAllHostnameVerifier());
            return builder;
        }catch (Exception e){
            throw new HttpClientRuntimeException(e);
        }

    }


    public static HttpURLConnection createHttpsOrHttpURLConnection(URL uUrl, String protocol) throws Exception {
        // initial connection
        if (uUrl.getProtocol().toUpperCase(Locale.getDefault()).equals(HttpClientConstants.HTTPS)) {
            SSLContext sslContext = getSslContext(protocol);
            HttpsURLConnection.setDefaultHostnameVerifier(new HttpSSLCipher.TrustAllHostnameVerifier());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            return (HttpsURLConnection) uUrl.openConnection();
        }
        return (HttpURLConnection) uUrl.openConnection();
    }

    private static SSLContext getSslContext(String protocol) throws
            NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        if (!HttpClientConstants.GM_PROTOCOL.equals(protocol) && !HttpClientConstants.INTERNATIONAL_PROTOCOL.equals(protocol)) {
//            LOGGER.info("Unsupport protocol: {}, Only support GMTLS TLSv1.2", protocol);
            throw new RuntimeException("Unsupport protocol, Only support GMTLS TLSv1.2");
        }
        // Create a trust manager that does not validate certificate chains
        HttpSSLCipher.TrustAllManager[] trust = {new HttpSSLCipher.TrustAllManager()};
        KeyManager[] kms = null;
        SSLContext sslContext;

        sslContext = SSLContext.getInstance(HttpClientConstants.INTERNATIONAL_PROTOCOL, "SunJSSE");

        if (HttpClientConstants.GM_PROTOCOL.equals(protocol)) {
            Security.insertProviderAt(new BGMProvider(), 1);
            sslContext = SSLContext.getInstance(HttpClientConstants.GM_PROTOCOL, "BGMProvider");
        }
        SecureRandom secureRandom = new SecureRandom();
        sslContext.init(kms, trust, secureRandom);
        sslContext.getServerSessionContext().setSessionCacheSize(8192);
        sslContext.getServerSessionContext().setSessionTimeout(3600);
        return sslContext;
    }


}