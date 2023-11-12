package com.tiny.spring.aop;

/**
 * @author: markus
 * @date: 2023/11/12 1:55 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
