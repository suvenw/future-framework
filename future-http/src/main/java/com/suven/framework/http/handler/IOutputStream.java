package com.suven.framework.http.handler;


import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.List;

/**
 * 框架http请求接口接收对象统一实现类
 */
public interface IOutputStream  extends IResponseResultVoHandler{



    /**
     * 按默认格式返回data数据;返回客户端结果/消息。
     * ResponseMessage=[{
     *    "code":0,
     *    "data":"{}",
     *    "msg":"成功"
     * }]
     */
    void writeSuccess();

    /**
     * 按默认格式返回data数据;返回客户端结果/消息。
     * ResponseMessage=[{
     *    "code":0,
     *    "data":"{}",
     *    "msg":"成功"
     * }]
     * @param responseData
     */
    void write(Object responseData, String... errParam);


    /**
     * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
     * @param enumType
     */
    void write(IResultCodeEnum enumType, Object errorToData) ;

    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
    void writeList(List<?> responseDataList, boolean isNextPage);

    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
     void writeList(List responseDataList, boolean isNextPage, long pageIndex, int total);



    /**
     * 按默认格式返回data数据;返回客户端结果/消息。
     * Object 为返回的完整的结构responseResultVo对象
     * 直接将参数对象转换成json字符串再按字节流返回用户端
     * ResponseMessage=[{
     *    "code":0,
     *    "data":"{}",
     *    "msg":"成功"
     * }]
     */
     void writeResult(Object responseResultVo);
    /**
     *
     * 自定义返回结果格式对象,取代或重写ResponseResultVo,按自定义格式返回对象转换成指定对象实现逻辑方法
     * 兼容错误协议封装处理逻辑实现,再将对象转换成json字符串再按字节流返回用户端
     * @param responseData
     */
    void writeResult(IResponseResult responseResultVo, Object responseData, String... errParam) ;

    /**
     * 将被下载文件流,通过文件名下载
     * @param fileName
     * @param data
     */
    void writeStream(String fileName , byte[] data);
}
