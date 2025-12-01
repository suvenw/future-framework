package com.suven.framework.http.api;

import com.suven.framework.http.data.vo.HttpRequestByPageVo;

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

    /**
     * 方法用于做参数校验
     * 子类可以重写此方法返回子类类型
     * @return 返回 true 为验证通过,抛异常或返回 false 为验证失败
     */
   default  boolean checkValidator(){
       return false;
   }
    /**
     * 返回当前实例，子类可以重写此方法以返回更具体的类型
     * 用于支持链式调用时返回正确的类型
     * @return 当前实例
     */
    @SuppressWarnings("unchecked")
    default <T extends HttpRequestType> T self() {
        return (T) this;
    }
}
