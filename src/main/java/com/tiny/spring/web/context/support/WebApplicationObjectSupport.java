package com.tiny.spring.web.context.support;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.ApplicationContextAware;
import com.tiny.spring.context.support.ApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/26 11:00 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class WebApplicationObjectSupport implements ApplicationContextAware {

    @Nullable
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        // todo 初始化拦截器
    }

    @Nullable
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
