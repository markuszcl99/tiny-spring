package com.tiny.spring.aop.framework.autoproxy;

import com.tiny.spring.aop.PointcutAdvisor;
import com.tiny.spring.aop.framework.AopProxyFactory;
import com.tiny.spring.aop.framework.DefaultAopProxyFactory;
import com.tiny.spring.aop.framework.ProxyFactoryBean;
import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.BeanFactoryAware;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.util.PatternMatchUtils;

/**
 * @author: markus
 * @date: 2023/11/12 3:04 PM
 * @Description: 实现自动生成目标对象代理
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanNameAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    /**
     * 代理对象名称模式，eg. action*
     */
    private String pattern;
    private BeanFactory beanFactory;
    private AopProxyFactory aopProxyFactory;
    private String interceptorName;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isMatch(beanName, pattern)) {
            // 创建一个代理工厂Bean
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.setAopProxyFactory(aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            return proxyFactoryBean;
        }
        return bean;
    }

    protected boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
