package com.tiny.spring.context.event;

/**
 * @author: markus
 * @date: 2023/10/15 1:41 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ContextStartedEvent extends ApplicationContextEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextStartedEvent(Object source) {
        super(source);
    }
}
