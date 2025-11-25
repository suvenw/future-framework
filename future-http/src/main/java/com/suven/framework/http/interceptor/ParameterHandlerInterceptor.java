package com.suven.framework.http.interceptor;

import com.suven.framework.http.JsonParse;
import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.handler.BaseHttpResponseHandlerConverter;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.http.validator.ParamValidatorConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.suven.framework.common.enums.SysResultCodeEnum.*;

/**
 * Title: ParameterHandlerInterceptor.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口拦截器,主要是实现请求接口参数业务校验业务,排序值为7;
 */



public class ParameterHandlerInterceptor extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(BlackHandlerInterceptor.class);

    public ParameterHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
        super(application, handlerSetting);
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return HandlerInterceptorEnum.HANDLER_ORDER_PARAMETER.getIndex();
    }

    /**
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
     */
    @Override
    public String handlerValidatorBeanType() {
        return HandlerInterceptorEnum.HANDLER_ORDER_PARAMETER.getValue();
    }

    /**
     * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
     *
     * @param request
     * @param response
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, HandlerValidator validator) throws Exception {
        HttpRequestRemote remote = ParameterMessage.getRequestRemote();
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        if(remote == null){
            log.warn(" TokenHandlerInterceptor RequestRemote is null remote: [{}] ", remote );
            return false;
        }//接口是否做加密处理,默认是加密的,配置为非加密,跳过

        this.checkRequestParam(message);//基本请求参数验证
        if(remote.isPostRequest()){ //post或get 基础参数验证;
            checkPostParam(message,remote);
        }else{
            checkGetParam(message);
        }
        //具体业务参数验证;
        if( this.getHandlerSetting().getService().isCheckBody()){
            this.checkOnlyParameter((HandlerMethod) handler);
        }
        return true;
    }


    /**
     * Title: 公共参数校验
     * Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkRequestParam(HttpRequestPostMessage message)throws SystemRuntimeException {
        IResultCodeEnum sysMsgEnumType =  SYS_PARAM_ERROR;
        if(message.getAppId() <= 0){
            throw new SystemRuntimeException(sysMsgEnumType, ParamValidatorConstant.System.CLIENT_IS_NOT_NULL);
        } else if(message.getVersion() == 0){
            logger.warn("非法请求version={}",message.getVersion());
            throw new SystemRuntimeException(SYS_INVALID_REQUEST);
        }

    }
    /**
     * Title: GET请求参数校验
     * Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkGetParam(HttpRequestPostMessage message){

    }
    /**
     * Title: POST请求参数校验
     * Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkPostParam(HttpRequestPostMessage message, HttpRequestRemote remote)throws SystemRuntimeException{
        IResultCodeEnum sysMsgEnumType =  SYS_PARAM_ERROR;
        //post 接口验证用户登陆参数
        if(!remote.isWhite()){
            if(message.getUserId() <= 0 || Objects.isNull(message.getAccessToken()) ){
                logger.warn("login faild userId={},accessToken={}",message.getUserId(),message.getAccessToken());
                throw new SystemRuntimeException(SYS_TOKEN_NULL);
            }
        }
        if(message.getTimes() == 0) {
            throw new SystemRuntimeException(sysMsgEnumType).format(ParamValidatorConstant.System.TIMES_IS_NOT_NULL);
        }

    }

    /**
     * 具体实现业务参数验证;
     * 原始类型：一般意义上的java类，由class类实现
     * 参数化类型：ParameterizedType接口的实现类
     * 数组类型：GenericArrayType接口的实现类
     * 类型变量：TypeVariable接口的实现类
     * 基本类型：int，float等java基本类型，其实也是class
     * @param handlerMethod
     * @throws Exception
     */
    private void checkOnlyParameter(HandlerMethod handlerMethod) throws Exception{
        Type[] parameterTypes = handlerMethod.getMethod().getGenericParameterTypes();
        if(parameterTypes == null || parameterTypes.length <1){
            return;
        }
        for (Type parameterType : parameterTypes){
            if(parameterType == OutputResponse.class || parameterType == HttpServletRequest.class
                    || parameterType == HttpServletResponse.class || parameterType == HttpSession.class){
                continue;
            }
            if(parameterType instanceof BaseHttpResponseHandlerConverter){
                continue;
            }
            Class genericClazz = null;
            if (parameterType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) parameterType;
                Type rawType =  pt.getRawType();
                if(rawType == java.util.List.class || rawType == Set.class || rawType == java.util.Arrays.class
                        || rawType == java.util.Queue.class || rawType == java.util.Iterator.class ){
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                }else if(rawType == Map.class){
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[1];
                }else {
                    genericClazz = (Class)rawType;
                }
            } else if (parameterType instanceof TypeVariable) {
                TypeVariable tType = (TypeVariable) parameterType;
                genericClazz = tType.getGenericDeclaration().getClass();
            }
            else {
                genericClazz = (Class) parameterType;
            }
            if(genericClazz == null){
                return;
            }
            if(IRequestVo.class.isAssignableFrom(genericClazz) |  JsonParse.class.isAssignableFrom(genericClazz)){
                checkValidatorParameter(genericClazz);
            }

        }
    }

    //换行符
    private static String lineSeparator = System.lineSeparator();

        /**
     * 检查验证器参数
     * @param clazz 需要验证的类对象
     * @throws Exception 当参数验证失败时抛出异常
     */
    public void checkValidatorParameter( Class  clazz ) throws Exception{
        // 获取JSON解析映射表
        Map body =  ParameterMessage.getJsonParseMap();
        // 通过反射获取parseFrom方法
        Method method = clazz.getMethod("parseFrom", Map.class, Class.class);
        // 调用parseFrom方法创建对象实例
        Object  object =  method.invoke(clazz.newInstance(), body, clazz);
//        checkValidatoParameter(object);
        // 构建默认验证器工厂
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        //获取validator实例
        Validator validator = validatorFactory.getValidator();
        //调用调用，得到校验结果信息 Set
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(object);
        //StringBuilder组装异常信息
        StringBuilder builder = new StringBuilder();
        //遍历拼装
        constraintViolationSet.forEach(violationInfo -> {
            builder.append( lineSeparator + violationInfo.getPropertyPath()+" : "+violationInfo.getMessage() );
        });
        // 如果存在验证错误，则抛出系统运行时异常
        if (builder.length() > 0){
            throw new SystemRuntimeException(SYS_PARAM_ERROR.format(builder.toString()));

        }
    }



}
