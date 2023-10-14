package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: markus
 * @date: 2023/10/13 10:55 PM
 * @Description: BeanFactory 抽象类实现
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws BeansException {
        // 先尝试直接拿bean实例
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
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
        return singleton;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
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
}
