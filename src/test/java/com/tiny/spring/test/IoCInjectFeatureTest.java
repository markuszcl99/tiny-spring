package com.tiny.spring.test;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.bean.A;

/**
 * @author: markus
 * @date: 2023/10/9 12:17 AM
 * @Description: setter、构造器注入功能测试
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class IoCInjectFeatureTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        A a1 = (A) applicationContext.getBean("a1");
        A a2 = (A) applicationContext.getBean("a2");

        System.out.println(a1);
        System.out.println(a2);
    }
}
