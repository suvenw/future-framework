package com.suven.framework.http.handler;


import com.suven.framework.http.api.IResponseResult;

/**
 * @author 作者 : suven.wang
 * CreateDate创建时间: 2021-12-30
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  结果对象返回业务处理逻辑实现接口类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public interface IResponseResultVoHandler {


     /**
      * 按默认格式返回data数据;返回客户端结果/消息。对象结构体
      * IResponseResult=[{
      *    "code":0,
      *    "data":"{}",
      *    "msg":"成功"
      * }]
      */
     IResponseResult getResultVo();

}
