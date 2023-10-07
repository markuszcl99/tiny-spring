package com.tiny.spring.beans.factory.config;

/**
 * @author: markus
 * @date: 2023/10/7 11:40 PM
 * @Description: 单例Bean注册器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface SingletonBeanRegistry {
    /**
     * 单例Bean注册
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例对象
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 是否包含某个单例Bean
     * @param beanName
     * @return
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取单例BeanName集合
     * @return
     */
    String[] getSingletonNames();
}
