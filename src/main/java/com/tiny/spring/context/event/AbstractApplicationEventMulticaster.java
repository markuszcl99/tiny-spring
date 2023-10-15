package com.tiny.spring.context.event;

import com.tiny.spring.context.ApplicationListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: markus
 * @date: 2023/10/15 10:29 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

    private List<ApplicationListener> applicationListeners = new CopyOnWriteArrayList<>();

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        synchronized (this.applicationListeners) {
            applicationListeners.remove(applicationListener);
            applicationListeners.add(applicationListener);
        }
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.remove(applicationListener);
    }

    public List<ApplicationListener> getApplicationListeners() {
        return applicationListeners;
    }
}
