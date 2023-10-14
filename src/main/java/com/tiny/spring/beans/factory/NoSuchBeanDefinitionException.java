package com.tiny.spring.beans.factory;

import com.tiny.spring.beans.BeansException;

/**
 * @author: markus
 * @date: 2023/10/14 3:34 PM
 * @Description: 没有Bean 异常
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class NoSuchBeanDefinitionException extends BeansException {

    public final String beanName;

    public NoSuchBeanDefinitionException(String beanName) {
        super("No bean named '" + beanName + "' available");
        this.beanName = beanName;
    }

    public NoSuchBeanDefinitionException(String beanName, String msg) {
        super("No bean named '" + beanName + "' available: " + msg);
        this.beanName = beanName;
    }
}
