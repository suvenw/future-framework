//package top.suven.base.test.rule.runner;
//
//import MockitoInit;
//import org.databene.benerator.Generator;
//import org.databene.benerator.anno.AnnotationMapper;
//import org.databene.benerator.anno.DefaultPathResolver;
//import org.databene.benerator.anno.PathResolver;
//import org.databene.benerator.anno.ThreadPoolSize;
//import org.databene.benerator.engine.BeneratorContext;
//import org.databene.benerator.engine.DefaultBeneratorContext;
//import org.databene.benerator.factory.EquivalenceGeneratorFactory;
//import org.databene.benerator.wrapper.ProductWrapper;
//import org.databene.commons.ConfigurationError;
//import org.databene.commons.IOUtil;
//import org.databene.commons.Period;
//import org.databene.commons.StringUtil;
//import org.databene.commons.converter.AnyConverter;
//import org.databene.feed4junit.ChildRunner;
//import org.databene.feed4junit.Feeder;
//import org.databene.feed4junit.FrameworkMethodWithParameters;
//import org.databene.feed4junit.Scheduler;
//import org.databene.feed4junit.scheduler.DefaultFeedScheduler;
//import org.databene.model.data.DataModel;
//import org.databene.platform.java.BeanDescriptorProvider;
//import org.databene.platform.java.Entity2JavaConverter;
//import org.databene.script.DatabeneScriptParser;
//import org.databene.script.Expression;
//import org.junit.Test;
//import org.junit.rules.MethodRule;
//import org.junit.runner.notification.RunNotifier;
//import org.junit.runners.model.*;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
// * 此类不再推荐使用, 请使用DTest这个测试类, 详情见{@link DTest} <br/>
// * Created by Alex on 2014/5/10
// */
//@Deprecated
//public class SpringFeed4jJUnitRunner extends SpringJUnit4ClassRunner {
//
//    private BeneratorContext context;
//    private PathResolver pathResolver;
//    private AnnotationMapper annotationMapper;
//    private List<FrameworkMethod> children;
//    private RunnerScheduler scheduler;
////    private Class<?> clazz
//
//    public SpringFeed4jJUnitRunner(Class<?> clazz) throws InitializationError {
//        super(clazz);
//        this.children = null;
//    }
//
//    public static final String CONFIG_FILENAME_PROPERTY = "feed4junit.properties";
//    private static final String DEFAULT_CONFIG_FILENAME = "feed4junit.properties";
//    private static final String FEED4JUNIT_BASE_PATH = "feed4junit.basepath";
//
//    private static final long DEFAULT_TIMEOUT = Period.WEEK.getMillis();
//
//    static {
//        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
//    }
//
//
//    @Override
//    protected String testName(FrameworkMethod method) {
//        return (method instanceof FrameworkMethodWithParameters ? method.toString() : super.testName(method));
//    }
//
//    @Override
//    public void setScheduler(RunnerScheduler scheduler) {
//        this.scheduler = scheduler;
//        super.setScheduler(scheduler);
//    }
//
//    /**
//     * Instantiates a test class and initializes attributes
//     * which have been marked with a @Source annotation.
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @Override
//    protected Object createTest() throws Exception {
//        Object testObject = super.createTest();
//        for (FrameworkField attribute : getTestClass().getAnnotatedFields(org.databene.benerator.anno.Source.class)) {
//            if ((attribute.getField().getModifiers() & Modifier.PUBLIC) == 0)
//                throw new ConfigurationError("Attribute '" + attribute.getField().getName() + "' must be public");
//            Generator<?> generator = getAnnotationMapper().createAndInitAttributeGenerator(attribute.getField(), getContext());
//            if (generator != null) {
//                ProductWrapper wrapper = new ProductWrapper();
//                wrapper = generator.generate(wrapper);
//                if (wrapper != null)
//                    attribute.getField().set(testObject, wrapper.unwrap());
//            }
//        }
//        return testObject;
//    }
//    private String filterMethod() {
//        String command = System.getProperty("sun.java.command");
//        if (command != null && (command.contains(",") || command.contains(":"))) {
//            String[] args = command.split("\\s*(,|:)\\s*");
//            return args[args.length-1];
//        }
//        return "ALL";
//    }
//    @Override
//    protected List<FrameworkMethod> computeTestMethods() {
//        if (children == null) {
//            children = new ArrayList<>();
//            TestClass testClass = getTestClass();
//            BeneratorContext context = getContext();
//            context.setGeneratorFactory(new EquivalenceGeneratorFactory());
//            getAnnotationMapper().parseClassAnnotations(testClass.getAnnotations(), context);
//            String filter = filterMethod();
//            boolean skip = !filter.equals("ALL");
//            for (FrameworkMethod method : testClass.getAnnotatedMethods(Test.class)) {
//                if (skip && !filter.equals(method.getName())) {
//                    continue;
//                }
//                if (method.getMethod().getParameterTypes().length == 0) {
//                    // standard JUnit test method
//                    children.add(method);
//                } else {
//                    // parameterized Feed4JUnit test method
//                    List<? extends FrameworkMethod> parameterizedTestMethods;
//                    parameterizedTestMethods = computeParameterizedTestMethods(method.getMethod(), context);
//                    children.addAll(parameterizedTestMethods);
//                }
//            }
//        }
//        return children;
//    }
//
//    @Override
//    protected void validateTestMethods(List<Throwable> errors) {
//        validatePublicVoidMethods(Test.class, false, errors);
//    }
//
//    // test execution --------------------------------------------------------------------------------------------------
//
//    protected Statement childrenInvoker(final RunNotifier notifier) {
//        return new Statement() {
//            @Override
//            public void evaluate() {
//                runChildren(notifier);
//            }
//        };
//    }
//
//    private void runChildren(final RunNotifier notifier)  {
//        try {
//			RunnerScheduler scheduler = getScheduler();
//
//			for (FrameworkMethod method : getChildren()){
//				 Feeder feeder = new Feeder(this.getTestClass().getJavaClass());
//			    scheduler.schedule(new ChildRunner(feeder, method, notifier));
//			}
//			scheduler.finished();
//		} catch (InitializationError e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
//    public RunnerScheduler getScheduler() {
//        if (scheduler == null)
//            scheduler = createDefaultScheduler();
//        return scheduler;
//    }
//
//    protected RunnerScheduler createDefaultScheduler() {
//        TestClass testClass = getTestClass();
//        Scheduler annotation = testClass.getJavaClass().getAnnotation(Scheduler.class);
//        if (annotation != null) {
//            String spec = annotation.value();
//            Expression<?> bean = DatabeneScriptParser.parseBeanSpec(spec);
//            return (RunnerScheduler) bean.evaluate(null);
//        } else {
//            return new DefaultFeedScheduler(1, DEFAULT_TIMEOUT);
//        }
//    }
//
//    @Override
//    public void runChild(FrameworkMethod method, RunNotifier notifier) {
//        super.runChild(method, notifier);
//    }
//
//    // helpers ---------------------------------------------------------------------------------------------------------
//
//    private PathResolver configuredPathResolver() {
//        if (pathResolver != null)
//            return pathResolver;
//        String configuredConfigFileName = System.getProperty(CONFIG_FILENAME_PROPERTY);
//        String configFileName = configuredConfigFileName;
//        if (StringUtil.isEmpty(configFileName))
//            configFileName = DEFAULT_CONFIG_FILENAME;
//        if (IOUtil.isURIAvailable(configFileName)) {
//            // load individual or configured frame file
//            return configuredPathResolver(configFileName);
//        } else if (StringUtil.isEmpty(configuredConfigFileName)) {
//            // if no explicit frame file was configured, then use defaults...
//            return createDefaultResolver();
//        } else {
//            // ...otherwise raise an exception
//            throw new ConfigurationError("Feed4JUnit configuration file not found: " + configuredConfigFileName);
//        }
//    }
//
//    private PathResolver createDefaultResolver() {
//        return applyBasePath(new DefaultPathResolver());
//    }
//
//    private PathResolver configuredPathResolver(String configFileName) {
//        try {
//            Map<String, String> properties = IOUtil.readProperties(configFileName);
//            String pathResolverSpec = properties.get("pathResolver");
//            if (pathResolverSpec != null) {
//                PathResolver resolver;
//                resolver =  (PathResolver) DatabeneScriptParser.parseBeanSpec(pathResolverSpec).evaluate(getContext());
//                return applyBasePath(resolver);
//            } else
//                return createDefaultResolver();
//        } catch (IOException e) {
//            throw new ConfigurationError("Error reading frame file '" + configFileName + "'", e);
//        }
//    }
//
//    private PathResolver applyBasePath(PathResolver resolver) {
//        String confdBasePath = System.getProperty(FEED4JUNIT_BASE_PATH);
//        if (confdBasePath != null)
//            resolver.setBasePath(confdBasePath);
//        return resolver;
//    }
//
//    private void validatePublicVoidMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
//        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
//        for (FrameworkMethod eachTestMethod : methods)
//            eachTestMethod.validatePublicVoid(isStatic, errors);
//    }
//
//    private List<FrameworkMethodWithParameters> computeParameterizedTestMethods(Method method, BeneratorContext context) {
//        Integer threads = getThreadCount(method);
//        long timeout = getTimeout(method);
//        String info = null;
//        List<FrameworkMethodWithParameters> result = new ArrayList<FrameworkMethodWithParameters>();
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        Generator<Object[]> paramGenerator = getAnnotationMapper().createAndInitMethodParamsGenerator(method, context);
//        Class<?>[] expectedTypes = parameterTypes;
//        ProductWrapper<Object[]> wrapper = new ProductWrapper<Object[]>();
//        int count = 0;
//        while ((wrapper = paramGenerator.generate(wrapper)) != null) {
//            Object[] generatedParams = wrapper.unwrap();
//            if (generatedParams.length > expectedTypes.length) // imported data may have more columns than the method parameters, ...
//                generatedParams = Arrays.copyOfRange(generatedParams, 0, expectedTypes.length); // ...so cut them
//            for (int i = 0; i < generatedParams.length; i++) {
//                generatedParams[i] = Entity2JavaConverter.convertAny(generatedParams[i]);
//                generatedParams[i] = AnyConverter.convert(generatedParams[i], parameterTypes[i]);
//            }
//            // generated params may be to few, e.g. if an XLS row was imported with trailing nulls,
//            // so create an array of appropriate size
//            Object[] usedParams = new Object[parameterTypes.length];
//            System.arraycopy(generatedParams, 0, usedParams, 0, Math.min(generatedParams.length, usedParams.length));
//            result.add(new FrameworkMethodWithParameters(method, usedParams, threads, timeout,info));
//            count++;
//        }
//        if (count == 0)
//            throw new RuntimeException("No parameter values available for method: " + method);
//        return result;
//    }
//
//    private Integer getThreadCount(Method method) {
//        ThreadPoolSize methodAnnotation = method.getAnnotation(ThreadPoolSize.class);
//        if (methodAnnotation != null)
//            return methodAnnotation.value();
//        Class<?> testClass = method.getDeclaringClass();
//        ThreadPoolSize classAnnotation = testClass.getAnnotation(ThreadPoolSize.class);
//        if (classAnnotation != null)
//            return classAnnotation.value();
//        return null;
//    }
//
//    private long getTimeout(Method method) {
//        return DEFAULT_TIMEOUT;
//    }
//
//    private AnnotationMapper getAnnotationMapper() {
//        // lazy initialization is necessary since the constructor is not executed by JUnit
//        if (annotationMapper == null) {
//            PathResolver pathResolver = configuredPathResolver();
////            annotationMapper = new AnnotationMapper(new EquivalenceGeneratorFactory(), getDataModel(), pathResolver);
//            annotationMapper = new AnnotationMapper( getDataModel(), pathResolver);
//        }
//        return annotationMapper;
//    }
//
//    private BeneratorContext getContext() {
//        // lazy initialization is necessary since the constructor is not executed by JUnit
//        if (context == null) {
//            context = new DefaultBeneratorContext();
//            DataModel dataModel = context.getDataModel();
//            new BeanDescriptorProvider(dataModel);
////            new BeanDescProvider(dataModel);
//        }
//        return context;
//    }
//
//    private DataModel getDataModel() {
//        return getContext().getDataModel();
//    }
//
//    @Override
//    protected List<MethodRule> rules(Object o) {
//        List<MethodRule> rules = super.rules(o);
//        try {
//            InputStream in = new ClassPathResource("rules.yml").getInputStream();
////            List<String> ruleList = (List<String>) new Yaml().load(in);
////            Collections.reverse(ruleList);
////            for (String rule : ruleList) {
////                rules.add(0, (MethodRule) Class.forName(rule).newInstance());
////            }
//        } catch (Exception ignored) {
//        }
//        rules.add(0, new MockitoInit());
//
//        return rules;
//    }
//}
