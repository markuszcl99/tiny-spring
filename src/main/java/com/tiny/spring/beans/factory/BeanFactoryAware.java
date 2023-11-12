package com.tiny.spring.beans.factory;

/**
 * @author: markus
 * @date: 2023/11/12 1:27 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory);
}
