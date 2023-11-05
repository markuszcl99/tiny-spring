package com.tiny.spring.test.jdbc;

import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
import com.tiny.spring.test.jdbc.entity.User;
import com.tiny.spring.test.jdbc.service.UserService;

/**
 * @author: markus
 * @date: 2023/11/5 6:23 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class JdbcTemplateFeatureTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        User userInfo = userService.getUserInfo(1L);
        System.out.println(userInfo);
    }
}
