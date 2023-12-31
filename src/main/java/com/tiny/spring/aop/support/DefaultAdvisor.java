package com.tiny.spring.aop.support;

import com.tiny.spring.aop.Advisor;
import com.tiny.spring.aop.aopalliance.aop.Advice;
import com.tiny.spring.aop.aopalliance.intercept.MethodInterceptor;

/**
 * @author: markus
 * @date: 2023/11/12 12:58 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultAdvisor implements Advisor {

    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    }

    public DefaultAdvisor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Advice getAdvice() {
        return this.methodInterceptor;
    }
}
