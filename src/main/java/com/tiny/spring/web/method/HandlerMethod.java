package com.tiny.spring.web.method;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/10/26 11:56 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class HandlerMethod {

    private final Object bean;
    private final Method method;

    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }
}
