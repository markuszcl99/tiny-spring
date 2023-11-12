package com.tiny.spring.test.aop;

import com.tiny.spring.aop.framework.MethodInterceptor;
import com.tiny.spring.aop.framework.MethodInvocation;

/**
 * @author: markus
 * @date: 2023/11/12 1:16 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class LogRecordInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 方法调用前，打印调用开始时间、目标方法
        System.out.println("开始时间: " + System.currentTimeMillis());
        System.out.println("目标方法: " + invocation.getMethod());
        // 真正的方法调用
        Object obj = invocation.proceed();
        System.out.println("结束时间: " + System.currentTimeMillis());
        return obj;
    }
}
