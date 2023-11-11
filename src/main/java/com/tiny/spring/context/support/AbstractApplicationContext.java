package com.tiny.spring.context.support;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.tiny.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.context.ApplicationEvent;
import com.tiny.spring.context.ApplicationListener;
import com.tiny.spring.context.ConfigurableApplicationContext;
import com.tiny.spring.context.event.ApplicationEventMulticaster;
import com.tiny.spring.context.event.ContextRefreshedEvent;
import com.tiny.spring.context.event.SimpleApplicationEventMulticaster;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: markus
 * @date: 2023/10/14 3:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private DefaultListableBeanFactory beanFactory;
    private final Object startupShutdownMonitor;

    private final Set<ApplicationListener> applicationListeners = new LinkedHashSet<>();
    @Nullable
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Nullable
    private ApplicationContext parent;

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
                // 初始化事件广播器
                initApplicationEventMulticaster();
                // 刷新其他指定的Bean，交给子类去实现
                onRefresh();
                // Check for listener beans and register them.
                registerListeners();
                // 初始化所有非懒加载的单例Bean
                finishBeanFactoryInitialization(beanFactory);
                // 容器启动的最后一步，发布容器刷新完成事件
                finishRefresh();
            } catch (BeansException ex) {
                // 将异常传播给调用者
                throw ex;
            }

        }
    }

    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    protected void onRefresh() {
        // spring默认空实现，交给子类去处理

    }

    protected void registerListeners() {
        // 这里进行事件监听器的注册，用户可进行Bean配置，然后注册。
        // todo 待我们完成容器支持泛型的动作再实现这里
    }

    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) throws BeansException {
        beanFactory.preInstantiateSingletons();
    }

    protected void prepareRefresh() {

    }

    protected DefaultListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    /**
     * 实际上 这个是由 org.springframework.context.support.AbstractRefreshableApplicationContext 实现的
     * 这里 我们现在AbstractApplicationContext中实现
     */
    private void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        this.beanFactory = beanFactory;
        // 扫描BeanDefinition定义
        this.loadBeanDefinitions(beanFactory);
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory(getInternalParentBeanFactory());
    }

    private BeanFactory getInternalParentBeanFactory() {
        return (getParent() instanceof ConfigurableApplicationContext) ?
                ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent();
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
        beanFactory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        // 这里注册一下ApplicationContextAwareProcessor
        ApplicationContextAwareProcessor applicationContextAwareProcessor = new ApplicationContextAwareProcessor(this);
        beanFactory.addBeanPostProcessor(applicationContextAwareProcessor);
    }

    protected void initApplicationEventMulticaster() {
        // spring 源码中会先去获取BeanFactory中由应用层人员配置的multicaster，我们这里就省略了，直接初始化一个
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster();

        // 注册静态添加的事件监听器
        for (ApplicationListener applicationListener : this.applicationListeners) {
            getApplicationEventMulticaster().addApplicationListener(applicationListener);
        }
    }

    ApplicationEventMulticaster getApplicationEventMulticaster() throws IllegalStateException {
        if (this.applicationEventMulticaster == null) {
            throw new IllegalStateException("ApplicationEventMulticaster not initialized - " +
                    "call 'refresh' before multicasting events via the context: " + this);
        }
        return this.applicationEventMulticaster;
    }

    @Override
    public void publishEvent(Object event) {
        // spring源码要复杂一些，我们这里只进行一些简单的广播动作
        if (event instanceof ApplicationEvent) {
            ApplicationEvent applicationEvent = (ApplicationEvent) event;
            // 获取广播器并将事件广播出去
            getApplicationEventMulticaster().multicastEvent(applicationEvent);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        if (applicationEventMulticaster != null) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
        this.applicationListeners.add(applicationListener);
    }

    @Override
    public void setParent(ApplicationContext parent) {
        this.parent = parent;
    }

    @Override
    public ApplicationContext getParent() {
        return this.parent;
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
        return this.beanFactory;
    }

    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 交给子类来实现 例如基于xml驱动、基于注解驱动，各有个的实现方式
     *
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return this.beanFactory.getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.beanFactory.getBeansOfType(type);
    }
}
