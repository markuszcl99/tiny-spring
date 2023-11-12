package com.tiny.spring.beans.factory.support;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.BeanFactoryUtils;
import com.tiny.spring.beans.factory.FactoryBean;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.beans.factory.config.ConfigurableBeanFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: markus
 * @date: 2023/10/13 10:55 PM
 * @Description: BeanFactory 抽象类实现
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    /**
     * 父容器，可能为空
     */
    @Nullable
    private BeanFactory parentBeanFactory;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws BeansException {
        // 先尝试直接拿bean实例
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
            // 先去父容器里去看一下
            // Check if bean definition exists in this factory.
            BeanFactory parentBeanFactory = getParentBeanFactory();
            if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
                return parentBeanFactory.getBean(beanName);
            }

            // 如果没有实例，则尝试从早期Bean引用缓存中去获取一下 todo 先在这里获取下，后续会像spring源码一样 抽象到DefaultSingletonBeanRegistry中去。
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                // 如果早期Bean引用缓存中还是没有，那就老老实实创建实例吧
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("The bean name does not exit. bean name [" + beanName + "]");
                } else {
                    // 获取Bean的定义
                    try {
                        singleton = createBean(beanName, beanDefinition);
                    } catch (BeansException e) {
                        throw e;
                    }
                    // 注册最终成形的Bean实例
                    this.registerSingleton(beanDefinition.getId(), singleton);
                }
            }
        }
        singleton = getObjectForBeanInstance(singleton, beanName);
        return singleton;
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
        return getObjectFromFactoryBean(factoryBean, beanName);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void addBeanPostProcessors(List<BeanPostProcessor> postProcessors) {
        this.beanPostProcessors.removeAll(postProcessors);
        this.beanPostProcessors.addAll(postProcessors);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 先删除一下，避免重复添加
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    @Override
    public boolean isFactoryBean(String beanName) throws NoSuchBeanDefinitionException {
        Object beanInstance = getSingleton(beanName);
        if (beanInstance != null) {
            return (beanInstance instanceof FactoryBean);
        }
        return false;
    }

    @Override
    public void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException {
        this.parentBeanFactory = parentBeanFactory;
    }
    //---------------------------------------------------------------------
    // Implementation of HierarchicalBeanFactory interface

    //---------------------------------------------------------------------

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.parentBeanFactory;
    }

    @Override
    public boolean containsLocalBean(String beanName) {
        return ((containsSingleton(beanName) || containsBeanDefinition(beanName)) &&
                (!BeanFactoryUtils.isFactoryDereference(beanName) || isFactoryBean(beanName)));
    }

    //---------------------------------------------------------------------
    // Abstract methods to be implemented by subclasses

    //---------------------------------------------------------------------

    /**
     * 创建Bean实例，交给子类去实现
     *
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 获取Bean定义，交给子类去实现
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract boolean containsBeanDefinition(String beanName);
}
