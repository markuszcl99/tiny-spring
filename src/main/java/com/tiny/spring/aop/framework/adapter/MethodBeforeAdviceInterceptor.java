package com.tiny.spring.aop.framework.adapter;

import com.tiny.spring.aop.BeforeAdvice;
import com.tiny.spring.aop.MethodBeforeAdvice;
import com.tiny.spring.aop.aopalliance.intercept.MethodInterceptor;
import com.tiny.spring.aop.aopalliance.intercept.MethodInvocation;

/**
 * @author: markus
 * @date: 2023/11/12 1:34 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {

    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
