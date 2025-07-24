package com.suven.framework.core.db;


import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.core.db.ext.DSClassAnnoExplain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * <pre>
 *
 * </pre>
 *
 * @version 1.00.00
 * <pre>
 *
 * </pre>
 */
//@Aspect
//@Component
public class DatasourceTransactionalAdvice {

    private static Logger logger = LoggerFactory.getLogger(DatasourceTransactionalAdvice.class);


//    @Around("@annotation(transactional)")
    public Object monitorTransactional(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        try {
            Object[] args = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?>[] params = method.getParameterTypes();

            if (null == params) {
                return joinPoint.proceed();
            }
            for (int i = 0; i < params.length; i++) {
                Class clazz = params[i];
                Object value = args[i];
                if (IBaseApi.class.isAssignableFrom(clazz)) {
                    DSClassAnnoExplain.getDatasourceTransactional(clazz);
                    return joinPoint.proceed();
                }
                if (clazz.isArray() ) {
                    Object[] classValue = (Object[])value;
                    Class moduleClass = classValue[0].getClass();
                    if(classValue[0] instanceof IBaseApi){
                        DSClassAnnoExplain.getDatasourceTransactional(moduleClass);
                        return joinPoint.proceed();
                    }
                }
                if ( Collection.class.isAssignableFrom(clazz)) {
                    Collection classValue = (Collection)value;
                    Object module =  classValue.iterator().next();
                    Class moduleClass =  module.getClass();
                    if(module instanceof IBaseApi){
                        DSClassAnnoExplain.getDatasourceTransactional(moduleClass);
                        return joinPoint.proceed();
                    }
                }
            }
            return joinPoint.proceed();
        } catch (Exception e) {
            logger.warn("monitorTransactional Exception:[{}]",e);
            throw e;
        }

    }


}
