package com.tiny.spring.aop.framework;

import com.tiny.spring.aop.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/11/12 1:09 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object proxy;
    protected final Object target;
    protected final Method method;
    protected Object[] arguments;
    private Class<?> targetClass;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
    }


    public Object getProxy() {
        return proxy;
    }

    public Object getTarget() {
        return target;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }
}
