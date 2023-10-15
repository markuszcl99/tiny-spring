package com.tiny.spring.test;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.ApplicationEvent;
import com.tiny.spring.context.ApplicationListener;
import com.tiny.spring.context.event.ContextRefreshedEvent;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.bean.A;

/**
 * @author: markus
 * @date: 2023/10/15 11:06 PM
 * @Description: spring事件功能测试
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ApplicationContextRefreshedEventFeatureTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml", false);
        applicationContext.addApplicationListener(new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                System.out.println("ContextRefreshedEvent 事件监听成功!");
                System.out.println("get application context" + event.getApplicationContext());
            }
        });
        applicationContext.refresh();
        A a = (A) applicationContext.getBean("a1");
        System.out.println(a);
    }
}
