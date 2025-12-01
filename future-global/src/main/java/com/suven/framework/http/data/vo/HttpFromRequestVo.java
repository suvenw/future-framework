package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.HttpFromRequest;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;

/**
 * Title: HttpFromRequestVo.java http 表单请求,参数继承对象
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
public  class HttpFromRequestVo implements HttpFromRequest, IBeanClone,Serializable {

    public static HttpFromRequestVo build(){
        return new HttpFromRequestVo();
    }

}

