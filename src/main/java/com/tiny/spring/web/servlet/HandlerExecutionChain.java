package com.tiny.spring.web.servlet;

/**
 * @author: markus
 * @date: 2023/10/26 10:59 PM
 * @Description: 执行链
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class HandlerExecutionChain {
    // 具体的controller
    private final Object handler;
    // 拦截器那些  我们暂时先不管

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    public Object getHandler() {
        return handler;
    }
}
