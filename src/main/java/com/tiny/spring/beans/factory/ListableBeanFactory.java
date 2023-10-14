package com.tiny.spring.beans.factory;

import com.tiny.spring.beans.BeansException;

import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/14 11:26 PM
 * @Description: 扩展BeanFactory，支持集合类型
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ListableBeanFactory extends BeanFactory {

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
