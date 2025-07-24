package com.suven.framework.http.validator;

import com.suven.framework.http.interceptor.HandlerValidator;


/**
 * Title: DefaultHandlerValidator.java
 * @author Joven.wang
 * @version V1.0
 * date   2019-10-18 12:35:25
 *
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 缺省时验证实现,使用单例类实现类
 */


public class DefaultHandlerValidator implements HandlerValidator {

    private static class SingletonHandlerValidator {
        private static final DefaultHandlerValidator INSTANCE = new DefaultHandlerValidator();

    }
    private DefaultHandlerValidator(){}

    public static DefaultHandlerValidator build(){
        return SingletonHandlerValidator.INSTANCE;
    }
    /**
     * 验证结果数据,成功返回御前, 失败返回false
     *
     * @param googleCache
     * @param parameter
     * @return
     */
    @Override
    public boolean validatorFromData(boolean googleCache, Object parameter) {
        return true;
    }
}
