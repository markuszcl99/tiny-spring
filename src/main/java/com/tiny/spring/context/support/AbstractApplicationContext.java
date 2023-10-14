package com.tiny.spring.context.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
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
            prepareRefresh();
            // 创建IoC引擎
            DefaultListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
            // 对BeanFactory进行一些前置准备，例如类加载器、特殊的BeanPostProcessor等。我们这里先设置空实现
            prepareBeanFactory(beanFactory);
            try {
                // 对BeanFactory做一些后置处理，默认空实现，子类可进行扩展，例如修改底层IoC引擎，修改Bean的定义等
                postProcessBeanFactory(beanFactory);
                // 调用BeanFactory的后置处理器，可以修改一些BeanDefinition等，我们这里先默认空实现
                invokeBeanFactoryPostProcessors(beanFactory);
                // 注册BeanPostProcessor
                registerBeanPostProcessors(beanFactory);
                // 其他的还不涉及，我也先不写了。后续涉及到再实现

                // 初始化所有非懒加载的单例Bean
                finishBeanFactoryInitialization(beanFactory);
            } catch (BeansException ex) {
                // 将异常传播给调用者
                throw ex;
            }

        }
    }

    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) throws BeansException {
        beanFactory.preInstantiateSingletons();
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

    protected void prepareBeanFactory(DefaultListableBeanFactory beanFactory) {

    }

    protected void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {
    }

    protected void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {

    }

    protected void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory) {
        // 这里是对容器中配置的BeanPostProcessor进行注册，todo 后面我们再实现这个逻辑
        // 我们取个巧，在这里将AutowiredAnnotationBeanPostProcessor注册进来，正常情况下它是由AnnotationConfigApplicationContext引入的。
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessors(autowiredAnnotationBeanPostProcessor);
    }

    /**
     * 交给子类来实现 例如基于xml驱动、基于注解驱动，各有个的实现方式
     *
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
