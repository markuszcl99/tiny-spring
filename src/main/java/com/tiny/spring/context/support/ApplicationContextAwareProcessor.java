package com.tiny.spring.context.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.config.BeanPostProcessor;
import com.tiny.spring.context.ApplicationContextAware;
import com.tiny.spring.context.ConfigurableApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/28 11:42 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ConfigurableApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
