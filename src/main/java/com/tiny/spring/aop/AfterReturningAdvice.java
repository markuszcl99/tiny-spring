package com.tiny.spring.aop;

import com.tiny.spring.aop.AfterAdvice;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:31 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
