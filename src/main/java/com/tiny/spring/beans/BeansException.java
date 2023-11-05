package com.tiny.spring.beans;

/**
 * @author: markus
 * @date: 2023/10/7 8:42 PM
 * @Description: Bean相关异常处理类
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
