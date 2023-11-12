package com.tiny.spring.aop.support;

import com.tiny.spring.aop.*;
import com.tiny.spring.aop.aopalliance.aop.Advice;
import com.tiny.spring.aop.aopalliance.intercept.MethodInterceptor;
import com.tiny.spring.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.tiny.spring.aop.framework.adapter.MethodBeforeAdviceInterceptor;

/**
 * @author: markus
 * @date: 2023/11/12 2:39 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor {

    private Advice advice = null;
    private MethodInterceptor methodInterceptor;
    private String mappedName;
    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

    public NameMatchMethodPointcutAdvisor() {
    }

    public NameMatchMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }


    public void setAdvice(Advice advice) {
        this.advice = advice;
        MethodInterceptor mi = null;
        if (advice instanceof BeforeAdvice) {
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
        } else if (advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
        } else if (advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor) advice;
        }
        setMethodInterceptor(mi);
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(mappedName);
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
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
