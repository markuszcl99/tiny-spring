package com.tiny.spring.beans.factory.config;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;

/**
 * @author: markus
 * @date: 2023/10/13 11:22 PM
 * @Description: 扩展BeanFactory支持注解注入的能力
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 对正在创建Bean的实例应用BeanPostProcessor，调用他们的postProcessBeforeInitialization方法。
     * 该方法可能会返回一个包装原始对象的对象
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 对正在创建Bean的实例应用BeanPostProcessor，调用他们的postProcessAfterInitialization方法。
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

    <T> T createBean(Class<T> clazz);
}
