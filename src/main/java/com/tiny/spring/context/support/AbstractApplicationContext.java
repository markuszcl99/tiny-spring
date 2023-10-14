package com.tiny.spring.context.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author: markus
 * @date: 2023/10/14 3:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultListableBeanFactory beanFactory;
    private final Object startupShutdownMonitor;

    public AbstractApplicationContext() {
        this.startupShutdownMonitor = new Object();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException {
        return this.beanFactory.isSingleton(beanName);
    }

    @Override
    public boolean isPrototype(String beanName) throws NoSuchBeanDefinitionException {
        return this.beanFactory.isPrototype(beanName);
    }

    @Override
    public Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        return this.beanFactory.getType(beanName);
    }

    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // 容器启动前的准备阶段操作
            this.prepareRefresh();
            // 创建IoC引擎
            DefaultListableBeanFactory beanFactory = this.obtainFreshBeanFactory();

        }
    }

    protected void prepareRefresh() {

    }

    protected DefaultListableBeanFactory obtainFreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 扫描BeanDefinition定义
        this.loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
        return beanFactory;
    }

    /**
     * 交给子类来实现 例如基于xml驱动、基于注解驱动，各有个的实现方式
     *
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
