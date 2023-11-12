package com.tiny.spring.aop.aopalliance.intercept;

/**
 * @author: markus
 * @date: 2023/11/12 12:18 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
