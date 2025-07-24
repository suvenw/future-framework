//package top.suven.base.test.rule.util;
//
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;
//
///**
// * @author alex
// */
//public class MiscUtil {
//
//    public static final String CLASS_SUFFIX = "__ProxyTest__";
//
//    private static boolean useSpring = false;
//
//    /**
//     * 是否在测试类上面写了ContextConfiguration和WebAppConfiguration这两个注解
//     * @param testClass 测试类的class
//     * @return 写了就返回true, 否则返回false
//     */
//    public static boolean hasWebMvc(Class<?> testClass) {
//        return AnnotationUtils.findAnnotation(testClass, ContextConfiguration.class) != null
//                && AnnotationUtils.findAnnotation(testClass, WebAppConfiguration.class) != null;
//    }
//
//    /**
//     * 判断是否是有Spring-Test启动
//     * @return 由Spring-Test启动就返回true, 否则返回false
//     */
//    public static boolean hasSpring() {
//        return useSpring;
//    }
//
//    /**
//     * 设置状态是由Spring-Test启动
//     */
//    public static void useSpring() {
//        useSpring = true;
//    }
//
//    public static String testMethodName() {
//        String command = System.getProperty("sun.java.command");
//        if (command != null && (command.contains(",") || command.contains(":"))) {
//            String[] args = command.split("\\s*(,|:)\\s*");
//            return args[args.length - 1];
//        }
//        return "ALL";
//    }
//
//    /**
//     * 此次单元测试是但单方法测试还是其他(类或者testsuit)
//     * @return
//     * 如果是单方法返回true, 否则false
//     */
//    public static boolean isSingleTest() {
//        return !"ALL".equals(testMethodName());
//    }
//}
