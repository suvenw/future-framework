package com.suven.framework.http.validator;

import com.suven.framework.http.interceptor.HandlerValidator;
import com.suven.framework.http.interceptor.InterceptorOrderValue;
import org.springframework.stereotype.Component;


/**
 * @Title: BlackHandlerValidator.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 黑名单实现类
 */


@Component(InterceptorOrderValue.HANDLER_VALIDATOR_BLACK)
public class BlackHandlerValidator  implements HandlerValidator {


    /**
     * 验证结果数据,成功返回御前, 失败返回false
     * @param googleCache
     * @param parameter
     * @return
     */
    @Override
    public boolean validatorFromData(boolean googleCache, Object parameter) {
        return true;
    }
}
