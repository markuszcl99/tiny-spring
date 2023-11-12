package com.tiny.spring.aop.framework;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:31 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
