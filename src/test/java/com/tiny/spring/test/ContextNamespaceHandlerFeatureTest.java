package com.tiny.spring.test;

import com.tiny.spring.context.support.ClassPathXmlApplicationContext;

/**
 * @author: markus
 * @date: 2023/10/25 11:41 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ContextNamespaceHandlerFeatureTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("============打印IoC容器中的Bean实例============");
        for (String beanDefinitionName : applicationContext.getBeanFactory().getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
