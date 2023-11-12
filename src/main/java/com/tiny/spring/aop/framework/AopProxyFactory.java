package com.tiny.spring.aop.framework;

/**
 * @author: markus
 * @date: 2023/11/9 8:25 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target, Advisor advisor);
}
