package com.tiny.spring.aop.framework;

import com.tiny.spring.aop.*;
import com.tiny.spring.aop.aopalliance.intercept.MethodInterceptor;
import com.tiny.spring.aop.framework.adapter.AfterReturningAdviceInterceptor;
import com.tiny.spring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.tiny.spring.aop.support.DefaultAdvisor;
import com.tiny.spring.aop.support.NameMatchMethodPointcutAdvisor;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.BeanFactoryAware;
import com.tiny.spring.beans.factory.FactoryBean;
import com.tiny.spring.util.ClassUtils;

/**
 * @author: markus
 * @date: 2023/11/8 8:27 AM
 * @Description: aop 代理
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {

    private AopProxyFactory aopProxyFactory;
    private BeanFactory beanFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public ProxyFactoryBean(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    private synchronized void initializeAdvisor() {
        Object advice = this.beanFactory.getBean(this.interceptorName);
        this.advisor = (PointcutAdvisor) advice;
    }


    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, advisor);
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    @Override
    public Object getObject() throws Exception {
        // 初始化advisor
        initializeAdvisor();
        return getSingletonInstance();
    }

    private synchronized Object getSingletonInstance() {
        // 获取代理
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    private Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
