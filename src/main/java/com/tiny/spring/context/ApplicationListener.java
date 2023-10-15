package com.tiny.spring.context;

import java.util.EventListener;

/**
 * @author: markus
 * @date: 2023/10/15 1:38 PM
 * @Description: 事件监听器-应用层可以实现该接口完成事件的监听
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);

}
