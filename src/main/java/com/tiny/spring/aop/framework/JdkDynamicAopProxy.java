package com.tiny.spring.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: markus
 * @date: 2023/11/9 8:26 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    Object target;
    Advisor advisor;

    public JdkDynamicAopProxy(Object target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        Object obj = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        return obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("doAction")) {
            Class<?> targetClass = target != null ? target.getClass() : null;
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            // 封装方法调用
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            // 在方法前后进行拦截
            return interceptor.invoke(invocation);
        }
        return null;
    }
}
