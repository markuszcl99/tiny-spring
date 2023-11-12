package com.tiny.spring.aop.framework;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 12:19 PM
 * @Description: 对业务逻辑通过反射调用的一段代码的封装
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface MethodInvocation {
    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;
}
