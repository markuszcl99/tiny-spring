package com.tiny.spring.aop;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:54 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface MethodMatcher {
    /**
     * 方法匹配
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
