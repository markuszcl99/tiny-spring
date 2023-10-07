package com.tiny.spring.test.service.impl;

import com.tiny.spring.test.service.AService;

/**
 * @author: markus
 * @date: 2023/10/7 8:38 PM
 * @Description: AService 实现
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class AServiceImpl implements AService {
    @Override
    public void sayHello() {
        System.out.println("a service bean say hello!");
    }
}
