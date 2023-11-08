package com.tiny.spring.beans.factory;


/**
 * @author: markus
 * @date: 2023/11/8 8:05 AM
 * @Description: 生产Bean的Bean
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface FactoryBean<T> {

    public Object getObject() throws Exception;

    public Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}
