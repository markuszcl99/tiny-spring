package com.tiny.spring.context;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.Aware;
import com.tiny.spring.context.support.ApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/26 10:55 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
