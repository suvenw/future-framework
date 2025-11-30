package com.suven.framework.http.resolver;

import lombok.Data;

@Data
public class HttpRequestArgumentUserHeader {
    private Long userId;
    private String  accessToken;
}
