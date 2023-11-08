package com.tiny.spring.aop;

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

    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;


    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
