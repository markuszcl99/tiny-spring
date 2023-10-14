package com.tiny.spring.beans.factory;

import com.tiny.spring.beans.BeansException;

/**
 * @author: markus
 * @date: 2023/10/14 4:39 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanCreationException extends BeansException {

    public final String beanName;

    public BeanCreationException(String beanName) {
        super("bean named '" + beanName + "' Initialization of bean failed");
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg) {
        super("bean named '" + beanName + "' create error");
        this.beanName = beanName;
    }
}
