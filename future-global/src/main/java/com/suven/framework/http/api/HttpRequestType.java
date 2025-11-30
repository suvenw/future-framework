package com.suven.framework.http.api;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2024-01-02
 **/
public interface HttpRequestType {
    /**
     * 返回值为 HttpRequestTypeEnum code
     * */
    int parseType();

   default  boolean checkValidator(){
       return false;
   }
}
