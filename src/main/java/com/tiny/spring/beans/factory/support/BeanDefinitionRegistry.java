package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.factory.config.BeanDefinition;

/**
 * @author: markus
 * @date: 2023/10/7 11:57 PM
 * @Description: BeanDefinition注册器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();
}
