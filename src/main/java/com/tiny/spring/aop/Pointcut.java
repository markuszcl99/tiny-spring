package com.tiny.spring.aop;

/**
 * @author: markus
 * @date: 2023/11/12 1:52 PM
 * @Description: 切点
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
}
