package com.suven.framework.http.api;


import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.ClassUtils;

import java.io.Serializable;

/**
 * @author 作者 : suven.wang
 * CreateDate创建时间: 2021-06-29
 * @WeeK 星期: 星期二
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  返回前端结构实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/

public interface IResponseResult extends Serializable {



    /*** 返回指定规范对象 **/
    int code();
    String message();
    boolean success();


    /**
     *  失败时返回对象
     * @param code 失败错误码
     * @param message 失败错误提示内容信息
     * @return
     */
    IResponseResult of(int code, String message);

    /**
     * 返回自定义对象,
     * 默认成功返回的结果数据
     * @param success
     * @param code
     * @param message
     * @param result
     * @return
     */
    IResponseResult of(boolean success, int code, String message, Object result);


    /**
     * 默认成功返回的结果数据
     * @param result 返回的数据对象
     * @return
     */
    default IResponseResult of( Object result){
        return of(true,0,"成功",result);
    }

    /**
     * 成功返回的结果数据,success默认值为true
     * @param code 返回的编码
     * @param message 返回的提示内容信息
     * @param result 返回的结果对象信息;
     * @return
     */
    default IResponseResult of(int code, String message,Object result){
        return of(true,code,message,result);
    }

    /**
     * 用于处理 包体,data 逻辑
     * @param data
     * @return
     */
    default Object initData(Object data) {
        if(data == null){
            return null;
        }
        if( data instanceof String ){
            return data;
        }
        if(data != null && !ClassUtils.isPrimitiveOrWrapper(data.getClass())){
            return data;
        }
        if(data instanceof  Boolean){
            JSONObject object = new JSONObject();
            int value = (Boolean)data ? 1 : 0;
            object.put("result",value);
            return object;
        }else {
            JSONObject object = new JSONObject();
            object.put("result",data);
            return object;
        }

    }

}
