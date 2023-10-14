package com.tiny.spring.beans.factory.config;

import com.tiny.spring.beans.BeansException;

/**
 * @author: markus
 * @date: 2023/10/11 10:56 PM
 * @Description: Bean的后置处理器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanPostProcessor {
    /**
     * Bean初始化之前
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Bean初始化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
