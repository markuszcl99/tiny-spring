package com.tiny.spring.aop.framework;

/**
 * @author: markus
 * @date: 2023/11/12 12:28 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
