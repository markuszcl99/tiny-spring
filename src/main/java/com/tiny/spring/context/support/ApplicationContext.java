package com.tiny.spring.context.support;

import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.context.ApplicationEventPublisher;

/**
 * @author: markus
 * @date: 2023/10/14 3:51 PM
 * @Description: 提供给应用层程序员的上下文接口
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface ApplicationContext extends BeanFactory, ApplicationEventPublisher {
}
