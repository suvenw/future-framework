package com.suven.framework.http.interceptor;

public interface HandlerValidator {

    /**
     * 验证结果数据,成功返回御前, 失败返回false
     * @return
     */
    boolean validatorFromData(boolean googleCache,Object parameter);


}
