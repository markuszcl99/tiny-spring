package com.tiny.spring.beans.factory;

import com.tiny.spring.beans.BeansException;

/**
 * @author: markus
 * @date: 2023/10/7 8:43 PM
 * @Description: IoC底层容器的根类
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanFactory {
    /**
     * 根据beanName获取Bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 判断当前容器中是否包含beanName的实例
     *
     * @param beanName
     * @return
     */
    Boolean containsBean(String beanName);

    /**
     * 注册单例Bean
     *
     * @param beanName
     * @param obj
     */
    void registerBean(String beanName, Object obj);
}
