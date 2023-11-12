package com.tiny.spring.aop.support;

import com.tiny.spring.aop.MethodMatcher;
import com.tiny.spring.aop.Pointcut;
import com.tiny.spring.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:57 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut {

    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName)) {
            return true;
        }
        return false;
    }

    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
