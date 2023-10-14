package com.tiny.spring.test;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.bean.B;
import com.tiny.spring.test.bean.C;

/**
 * @author: markus
 * @date: 2023/10/14 5:52 PM
 * @Description: @Autowired 注解字段注入功能测试
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class AutowiredAnnotationFieldInjectFeatureTest {
    public static void main(String[] args) throws BeansException {
        // 没有实现 ConfigAnnotationApplicationContext，先用xml，并在代码中取巧将AutowiredAnnotationBeanPostProcessor注册到BeanFactory中。
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        C c = (C) context.getBean("c1");
        System.out.println(c);

    }
}
