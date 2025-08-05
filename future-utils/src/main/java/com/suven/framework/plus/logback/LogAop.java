//package top.suven.base.common.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName:
// * Description:
// * @Author lixiangling
// * date 2018/6/22 11:08
// * Copyright: (c) 2018 gc by https://www.suven.top
// * @version : 1.0.0
// * --------------------------------------------------------
// * modifyer    modifyTime                 comment
// *
// * --------------------------------------------------------
// */
//@Aspect
//@Component
//public class LogAop {
//    private Logger logger = LoggerFactory.getLogger(LogAop.class);
//
//    @Pointcut("execution(* top.suven.base..*(..))")
//    public void hszCut(){};
//
//    @Before("hszCut()")
//    public void before(JoinPoint point){
//        /**
//         * Signature 包含了方法名、申明类型以及地址等信息
//         */
//        String method_name = point.getSignature().getName();
//        //重新定义日志
//        logger = LoggerFactory.getLogger(point.getTarget().getClass());
//        logger.info("send server method_name = {}",method_name);
//    }
//}
