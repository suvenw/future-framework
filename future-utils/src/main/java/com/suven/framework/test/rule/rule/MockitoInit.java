package com.suven.framework.test.rule.rule;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
//import org.mockito.MockitoAnnotations;

/**
 * Created by Alex on 2014/5/27.
 */
public class MockitoInit implements MethodRule {
    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
//         MockitoAnnotations.initMocks(target);
         return base;
    }
}
