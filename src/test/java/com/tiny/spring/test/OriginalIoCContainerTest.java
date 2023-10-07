package com.tiny.spring.test;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.service.AService;

/**
 * @author: markus
 * @date: 2023/10/7 8:37 PM
 * @Description: 最原始的IoC容器功能测试
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class OriginalIoCContainerTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) classPathXmlApplicationContext.getBean("aService");
        aService.sayHello();
    }
}
