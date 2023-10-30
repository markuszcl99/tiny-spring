package com.tiny.spring.beans.factory.config;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;

/**
 * @author: markus
 * @date: 2023/10/18 12:00 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
