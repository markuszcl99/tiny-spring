package com.tiny.spring.context.event;

import com.tiny.spring.context.ApplicationEvent;
import com.tiny.spring.context.ApplicationListener;

/**
 * @author: markus
 * @date: 2023/10/15 1:45 PM
 * @Description: 可以管理多个ApplicationListener对象并向其发布事件的对象
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加事件监听器
     *
     * @param applicationListener
     */
    void addApplicationListener(ApplicationListener<?> applicationListener);

    /**
     * 删除事件监听器
     *
     * @param applicationListener
     */
    void removeApplicationListener(ApplicationListener<?> applicationListener);


    /**
     * 将事件广播到相应的监听器中
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
