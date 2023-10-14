package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.ArgumentValue;
import com.tiny.spring.beans.factory.ArgumentValues;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: markus
 * @date: 2023/10/14 3:04 PM
 * @Description: IoC引擎
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    /**
     * BeanDefinition 缓存
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private final List<String> beanDefinitionNames = new ArrayList<>(256);

    @Override
    public boolean containsBean(String beanName) {
        // 实际上要复杂一些，还需要判断父容器等等，我们这里只判断容器中是否有Bean实例或者Bean定义即可
        return containsSingleton(beanName) || containsBeanDefinition(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException {
        if (!containsBeanDefinition(beanName)) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return getBeanDefinition(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) throws NoSuchBeanDefinitionException {
        if (!containsBeanDefinition(beanName)) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return getBeanDefinition(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        if (!containsBeanDefinition(beanName)) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return getBeanDefinition(beanName).getBeanClass().getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
