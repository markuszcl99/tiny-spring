package com.tiny.spring.beans.factory.config;

import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.HierarchicalBeanFactory;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.beans.factory.config.SingletonBeanRegistry;

/**
 * @author: markus
 * @date: 2023/10/14 5:43 PM
 * @Description: 不对外提供服务，只是框架内部用于调用的方法
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void setParentBeanFactory(BeanFactory parentBeanFactory) throws IllegalStateException;

    boolean isFactoryBean(String name) throws NoSuchBeanDefinitionException;

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);

}
