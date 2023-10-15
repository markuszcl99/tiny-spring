package com.tiny.spring.context.event;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.context.ApplicationEvent;
import com.tiny.spring.context.ApplicationListener;

import java.util.concurrent.Executor;

/**
 * @author: markus
 * @date: 2023/10/15 10:30 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    @Nullable
    private Executor taskExecutor;


    public void setExecutor(Executor executor) {
        this.taskExecutor = executor;
    }

    @Nullable
    protected Executor getTaskExecutor() {
        return this.taskExecutor;
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<?> listener : getApplicationListeners()) {
            // 如果线程池不为空 则使用线程池异步执行
            if (taskExecutor != null) {
                taskExecutor.execute(() -> {
                    invokeListener(listener, event);
                });
            } else {
                invokeListener(listener, event);
            }
        }
    }


    /**
     * 广播事件
     * @param listener
     * @param event
     */
    protected void invokeListener(ApplicationListener listener, ApplicationEvent event) {
        try {
            listener.onApplicationEvent(event);
        } catch (Exception ex) {

        }
    }
}
