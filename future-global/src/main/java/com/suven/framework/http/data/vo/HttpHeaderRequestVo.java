package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.HttpHeaderRequest;

import java.io.Serializable;

/**
 * Title: HttpHeaderRequestVo.java http 请求头参数继承对象
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http请求头参数VO类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class HttpHeaderRequestVo implements HttpHeaderRequest,Serializable {


    public static HttpHeaderRequestVo build(){
        return new HttpHeaderRequestVo();
    }



}

