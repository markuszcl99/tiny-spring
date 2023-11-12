package com.tiny.spring.aop.framework.adapter;

import com.tiny.spring.aop.framework.AfterAdvice;
import com.tiny.spring.aop.framework.AfterReturningAdvice;
import com.tiny.spring.aop.framework.MethodInterceptor;
import com.tiny.spring.aop.framework.MethodInvocation;

/**
 * @author: markus
 * @date: 2023/11/12 1:37 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object obj = invocation.proceed();
        this.advice.afterReturning(obj, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return obj;
    }
}
