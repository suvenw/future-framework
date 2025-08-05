/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultVo;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Title: BaseHttpResponseWrite.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class BaseHttpResponseWrite extends BaseHttpResponseWriteHandlerConverter implements IResponseHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 快速构造输入类实现类对象
     * @param response
     * @return
     */
    public static BaseHttpResponseWrite build(HttpServletResponse response) {
        BaseHttpResponseWrite OutputResponse = new BaseHttpResponseWrite();
        OutputResponse.response = response;
        return OutputResponse ;
    }


    /** 通过类来初始化实现类时,通过此方法,初始返回 HttpServletResponse 对象**/
    @Override
    public IResponseHandler initResponse(HttpServletResponse httpResponse) {
        this.response = httpResponse;
        return this;
    }

    /**
     * 按默认格式返回data数据;返回客户端结果/消息。对象结构体,可以通过重构该方法实现新的数据结构体
     * IResponseResult=[{
     *    "code":0,
     *    "data":"{}",
     *    "msg":"成功"
     * }]
     */
    @Override
    public IResponseResult getResultVo() {
        return ResponseResultVo.build();
    }

}
