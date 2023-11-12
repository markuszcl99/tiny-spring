package com.tiny.spring.aop.framework;

import com.tiny.spring.aop.Advisor;
import com.tiny.spring.aop.PointcutAdvisor;
import com.tiny.spring.aop.aopalliance.intercept.MethodInterceptor;
import com.tiny.spring.aop.aopalliance.intercept.MethodInvocation;

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
    PointcutAdvisor advisor;

    public JdkDynamicAopProxy(Object target, PointcutAdvisor advisor) {
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
        Class<?> targetClass = target != null ? target.getClass() : null;
        if (this.advisor.getPointcut().getMethodMatcher().matches(method, targetClass)) {
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            // 封装方法调用
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            // 在方法前后进行拦截
            return interceptor.invoke(invocation);
        }
        return null;
    }
}
