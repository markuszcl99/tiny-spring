package com.tiny.spring.context;

/**
 * @author: markus
 * @date: 2023/10/15 1:42 PM
 * @Description: 事件发布器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ApplicationEventPublisher {
    default void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    void publishEvent(Object event);
}
