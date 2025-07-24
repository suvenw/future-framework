//package top.suven.base.test.rule.runner;
//
//import top.suven.base.test.rule.util.MiscUtil;
//import top.suven.base.util.utilize.TestUtils;
//import FastBoot;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.NotFoundException;
//import javassist.bytecode.AnnotationsAttribute;
//import javassist.bytecode.ClassFile;
//import javassist.bytecode.annotation.Annotation;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.junit.runner.notification.RunNotifier;
//import org.junit.runners.BlockJUnit4ClassRunner;
//import org.junit.runners.model.*;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 代替之前的SpringFeedJunitRunner <br/>
// * DTest这个类的好处在于 <br/>
// * 1.当不需要Spring提供的功能(依赖注入,数据库回滚,Mock测试等等)的时候, 启动速度和普通JUnit一样快 <br/>
// * 2.当需要Spring的功能的时候, 不需要写ContextConfiguration和WebAppConfiguration 这两个注解, 直接写@RunWith(DTest.class)就好 <br/>
// * 3.和SpringFeedJunitRunner完全兼容, 也就是可以直接把@RunWith(SpringFeedJunitRunner.class)
// * 换成@RunWith(DTest.class)就可以运行 <br/>
// *
// * 缺陷: <br/>
// * 动态修改字节码方式在Intellij 的JUnit插件上面有问题(不知道Eclipse上面会不会也是这样), <br/>
// * 做了一个艰难的决定, 修改了JUnit的源码 这样子Intellij就可以了  <br/>
// * Created by Alex on 2014/7/15.
// */
//public class DTest extends BlockJUnit4ClassRunner {
//
//
//    protected BlockJUnit4ClassRunner delegateRunner;
//
//    public DTest(Class<?> klass) throws InitializationError {
//        super(modifyTestClass(klass));
//    }
//
//    public Object invoke(Object o, String methodName, Object... args) {
//        try {
//            Method method = findMethod(o.getClass(), methodName);
//            if (method != null)
//                return method.invoke(o, args);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    protected static Map<Class, Map<String, Method>> classMap = new HashMap<>();
//    protected static Method findMethod(Class klass, String methodName) {
//        Map<String, Method> methodMap = classMap.get(klass);
//        if (methodMap == null) {
//            methodMap = initMethodSet(klass);
//        }
//        return methodMap.get(methodName);
//    }
//
//
//
//    private static Map<String, Method> initMethodSet(Class klass) {
//        HashMap<String, Method> methodMap = new HashMap<>();
//        for (Class c = klass; c != null; c = c.getSuperclass()) {
//            for (Method method : c.getDeclaredMethods()) {
//                if (method.getName().equals("test01")) {
//                    System.out.println(c);
//                }
//                if (!methodMap.containsKey(method.getName())) {
//                    method.setAccessible(true);
//                    methodMap.put(method.getName(), method);
//                }
//            }
//        }
//        classMap.put(klass, methodMap);
//        return methodMap;
//    }
//
//    @Override
//    public String testName(FrameworkMethod method) {
//        return (String) invoke(delegateRunner, "testName", method);
//    }
//
//    @Override
//    public void setScheduler(RunnerScheduler scheduler) {
//        delegateRunner.setScheduler(scheduler);
//    }
//
//    @Override
//    public Object createTest() {
//        return invoke(delegateRunner, "createTest");
//    }
//
//    protected static Class<?> modifyTestClass(Class<?> testClass)  {
//        try {
//            ClassPool classPool = ClassPool.getDefault();
//            CtClass srcCt =classPool.getCtClass(testClass.getName());
//            CtClass destCt =classPool.makeClass(testClass.getName() + MiscUtil.CLASS_SUFFIX);
//            destCt.setSuperclass(srcCt);
//            boolean shouldAdd;
//
//            if (MiscUtil.isSingleTest()) {
//                Method method = findMethod(testClass, MiscUtil.testMethodName());
//                shouldAdd = AnnotationUtils.getAnnotation(method, FastBoot.class) == null;
//            } else {
//                shouldAdd = true;
//            }
//            if (MiscUtil.hasWebMvc(testClass)) { //如果已经写了Spring test的配置, 就不要自己加了
//                shouldAdd = false;
//            }
//            if (shouldAdd) {
//                addAnnotation(classPool, destCt);
//            }
//            return destCt.toClass();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//    private static void addAnnotation(ClassPool classPool, CtClass destCt) throws NotFoundException {
//        ClassFile classFile = destCt.getClassFile();
//        AnnotationsAttribute attribute = new AnnotationsAttribute(classFile.getConstPool(), AnnotationsAttribute.visibleTag);
//        Annotation ant = new Annotation(classFile.getConstPool(), destCt);
//
//        attribute.addAnnotation(ant);
//        classFile.addAttribute(attribute);
//    }
//
//    @Override
//    public void validateTestMethods(List<Throwable> errors) {
//        Exception e = null;
//        try {
//            TestClass fTestClass = (TestClass) FieldUtils.readField(this, "fTestClass", true);
//            Class klass = fTestClass.getJavaClass();
//            String testMethodName = MiscUtil.testMethodName();
//            if (MiscUtil.isSingleTest()) {
//                Method method = findMethod(klass, testMethodName);
//                FastBoot fastBoot = AnnotationUtils.findAnnotation(method, FastBoot.class);
//                if (fastBoot != null) {
////                    delegateRunner = new Feeder(klass);
//                } else {
////                    delegateRunner = new SpringFeed4jJUnitRunner(klass);
//                    MiscUtil.useSpring();
//                }
//            } else if (TestUtils.isJenkinsJunit()){
////            	delegateRunner = new Feeder(klass);
//            } else {
////                delegateRunner = new SpringFeed4jJUnitRunner(klass);
//            }
//        } catch (Exception ee) {
//            e = ee;
//        }
//        if (delegateRunner == null) throw new RuntimeException("发生了神马情况, 居然委托不了???", e);
//        invoke(delegateRunner, "validateTestMethods", errors);
//    }
//
//    /**
//     * 过滤掉不想要的测试方法
//     * @return 此次要跑的测试方法
//     */
//    @Override
//    protected List<FrameworkMethod> computeTestMethods() {
//        List<FrameworkMethod> testMethods = (List<FrameworkMethod>) invoke(delegateRunner, "computeTestMethods");
//        String targetMethod = MiscUtil.testMethodName();
//        if (MiscUtil.isSingleTest()) {
//            for (FrameworkMethod testMethod : testMethods) {
//                if (targetMethod.equals(testMethod.getName())) {
//                    try {
//                        if (testMethods.size() != 1) {
//                            testMethods.clear();
//                            testMethods.add(testMethod);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//        return testMethods;
//    }
//
//    @Override
//    public Statement childrenInvoker(RunNotifier notifier) {
//        return (Statement) invoke(delegateRunner, "childrenInvoker", notifier);
//    }
//
//    @Override
//    public void runChild(FrameworkMethod method, RunNotifier notifier) {
//        invoke(delegateRunner, "runChild", method, notifier);
//    }
//
//}
