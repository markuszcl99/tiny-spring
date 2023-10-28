package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.NoSuchBeanDefinitionException;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: markus
 * @date: 2023/10/14 3:04 PM
 * @Description: IoC引擎
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /**
     * BeanDefinition 缓存
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private final List<String> beanDefinitionNames = new ArrayList<>(256);

    private final Map<Class<?>, String[]> singletonBeanNamesByType = new ConcurrentHashMap<>(64);

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
        return getBeanDefinition(beanName).getBeanClass();
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

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionNames.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            if (beanDefinition.isSingleton() && !containsBean(beanName)) {
                // 直接getBean，所有非懒加载的单例Bean都会注册了。
                getBean(beanName);
            }
        }
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> beanDefinitionNames = this.beanDefinitionNames;
        List<String> result = new ArrayList<>();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
            try {
                Class<?> beanClass = Class.forName(beanDefinition.getClassName());
                if (type.isAssignableFrom(beanClass)) {
                    result.add(beanName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        if (beanNames == null || beanNames.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, T> result = new HashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }
}
