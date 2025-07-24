package com.suven.framework.http.proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-02-07
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class HttpSSLCipher {
    // 不校验域名
    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    // 不校验服务端证书
    public static class TrustAllManager implements X509TrustManager {
        private X509Certificate[] issuers;

        public TrustAllManager() {
            this.issuers = new X509Certificate[0];
        }

        public X509Certificate[] getAcceptedIssuers() {
            return issuers;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }
}
