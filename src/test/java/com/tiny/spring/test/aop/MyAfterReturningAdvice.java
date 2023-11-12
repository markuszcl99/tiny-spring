package com.tiny.spring.test.aop;

import com.tiny.spring.aop.framework.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:43 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class MyAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("--------------My Interceptor after method invoke----------------");
        System.out.println("返回值为: " + returnValue);
    }
}
