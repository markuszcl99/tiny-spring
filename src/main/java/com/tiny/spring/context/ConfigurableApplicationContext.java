package com.tiny.spring.context;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.config.ConfigurableBeanFactory;
import com.tiny.spring.context.support.ApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/15 10:50 PM
 * @Description: 不对外提供功能，框架内部使用
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    void addApplicationListener(ApplicationListener<?> applicationListener);

    void setParent(ApplicationContext parent);

    void refresh() throws BeansException, IllegalStateException;

    ConfigurableBeanFactory getBeanFactory();
}
