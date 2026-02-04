package com.suven.framework.core.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示代码：说明 parameter、paramMap、typedMap 是同一个对象的引用
 * 
 * 这个类用于解释 MybatisPageInnerInterceptor 中的对象引用关系
 */
public class ParameterReferenceDemo {
    
    public static void main(String[] args) {
        // 模拟 MyBatis 传入的参数
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", "张三");
        parameter.put("age", 25);
        
        System.out.println("原始 parameter: " + parameter);
        System.out.println("parameter 的 hashCode: " + System.identityHashCode(parameter));
        
        // 模拟模式匹配：paramMap 就是 parameter 的引用
        if (parameter instanceof Map<?, ?> paramMap) {
            System.out.println("paramMap 的 hashCode: " + System.identityHashCode(paramMap));
            System.out.println("paramMap == parameter: " + (paramMap == parameter)); // true
            
            // 类型转换：typedMap 和 paramMap 指向同一个对象
            @SuppressWarnings("unchecked")
            Map<String, Object> typedMap = (Map<String, Object>) paramMap;
            
            System.out.println("typedMap 的 hashCode: " + System.identityHashCode(typedMap));
            System.out.println("typedMap == paramMap: " + (typedMap == paramMap)); // true
            System.out.println("typedMap == parameter: " + (typedMap == parameter)); // true
            
            // 修改 typedMap
            typedMap.put("page", "分页对象");
            
            // 验证：所有引用都指向同一个对象，修改会同步
            System.out.println("\n修改 typedMap 后：");
            System.out.println("parameter: " + parameter);
            System.out.println("paramMap: " + paramMap);
            System.out.println("typedMap: " + typedMap);
            
            // 结论：三个变量都指向同一个对象，修改任何一个都会影响其他
            System.out.println("\n结论：parameter、paramMap、typedMap 是同一个对象的引用");
            System.out.println("修改 typedMap 会直接修改 parameter，MyBatis 会使用修改后的参数");
        }
    }
}

