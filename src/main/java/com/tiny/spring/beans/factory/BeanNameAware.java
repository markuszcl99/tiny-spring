package com.tiny.spring.beans.factory;

/**
 * @author: markus
 * @date: 2023/10/26 10:57 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String beanName);
}
