//package com.suven.framework.http;
//
//
//import com.suven.framework.common.api.IRequestVo;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Arrays;
//import java.util.Set;
//
//
///**
// * Title: ParameterHandlerInterceptor.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) http 接口拦截器,主要是实现请求接口参数业务校验业务,排序值为7;
// */
//
//
//
//public class HttpParameterValidator {
//
//
//    //换行符
//    private static String lineSeparator = System.lineSeparator();
//
//    public static void checkValidatorParameter(IRequestVo iRequestVo  ) throws Exception{
//
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        //获取validator实例
//        Validator validator = validatorFactory.getValidator();
//        //调用调用，得到校验结果信息 Set
//        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(iRequestVo);
//        //StringBuilder组装异常信息
//        StringBuilder builder = new StringBuilder();
//        //遍历拼装
//        constraintViolationSet.forEach(violationInfo -> {
//            builder.append(  violationInfo.getPropertyPath()+" : "+violationInfo.getMessage()+lineSeparator  );
//        });
//        if (builder.length() > 0){
//            String error = builder.substring(0,builder.length()-1);
//            throw new RuntimeException(formatMsg(error));
//
//        }
//    }
//
//    private static String formatMsg(String... error){
//        String format = "参数请求错误，%s！";
//        String msg =  String.format(format, new Object[] { error });
//        return msg;
//    }
//
//}
