/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: OutputResponse.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class OutputResponse extends BaseHttpResponseWrite  implements IResponseHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /** 重新返回结果结构体对象,否则返回默认值:ResponseResultVo 对象结构 **/
    @Override
    public IResponseResult getResultVo() {
        return ResponseResultVo.build();
    }
    /**
     * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
     * 兼容历史版本,不建议使用
     * @param enumType
     */
    @Deprecated
    public void writeAuth(IResultCodeEnum enumType, Object errorToData)  {
        this.write(enumType,errorToData);
    }





//    @Override
//    public String toString() {
//        long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
//        String outLogger =  "OutputResponse{" +
//                "" + ParamMessage.getRequestMessage()+
//                " [ code = "+errorCodeEnum.getCode() +
//                ", msg = " +errorCodeEnum.getMsg() +
//                "] "+
//                "responseEndTime = " + exeTime
//                +"}";
//        logger.warn(outLogger);
//        return outLogger;
//    }



}
