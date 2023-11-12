package com.tiny.spring.aop.framework;

import com.tiny.spring.beans.factory.FactoryBean;
import com.tiny.spring.util.ClassUtils;

/**
 * @author: markus
 * @date: 2023/11/8 8:27 AM
 * @Description: aop 代理
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
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

    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String[] getInterceptorNames() {
        return interceptorNames;
    }

    public void setInterceptorNames(String[] interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target);
    }

    @Override
    public Object getObject() throws Exception {
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
}
