package com.tiny.spring.beans.factory.support;

import com.tiny.spring.beans.BeansException;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

/**
 * @author: markus
 * @date: 2023/10/14 5:42 PM
 * @Description: 不对外提供服务，只是框架内部用于调用的方法(扩展ConfigurableBeanFactory，提供集合类Bean配置管理实现)
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory {

    /**
     * 预初始化所有非懒加载的单例Bean实例
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
