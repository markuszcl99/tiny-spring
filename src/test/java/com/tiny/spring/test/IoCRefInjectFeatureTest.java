package com.tiny.spring.test;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.service.AService;

/**
 * @author: markus
 * @date: 2023/10/9 10:17 PM
 * @Description: 引用对象注入功能测试
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class IoCRefInjectFeatureTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) context.getBean("aService");
        aService.sayHello();
    }
}
