package com.tiny.spring.web.method;

import com.tiny.spring.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/10/26 11:56 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class HandlerMethod {

    private Object bean;
    private Method method;
    private Class<?> beanType;
    private MethodParameter[] parameters;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;

    public HandlerMethod(Object bean, Method method) {
        this.setBean(bean);
        this.setMethod(method);
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }
}
