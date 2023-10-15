package com.tiny.spring.context;

import java.util.EventObject;

/**
 * @author: markus
 * @date: 2023/10/15 1:36 PM
 * @Description: 事件
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    protected String msg = null;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
        this.msg = source.toString();
    }
}
