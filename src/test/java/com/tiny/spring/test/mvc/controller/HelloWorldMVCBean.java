package com.tiny.spring.test.mvc.controller;

import com.tiny.spring.stereotype.Controller;
import com.tiny.spring.test.mvc.domain.User;
import com.tiny.spring.web.bind.annotation.RequestMapping;

/**
 * @author: markus
 * @date: 2023/10/16 10:41 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Controller
@RequestMapping("/tiny-mvc")
public class HelloWorldMVCBean {

    public String doGet() {
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }

    public String doTest() {
        return "Hello world! @RequestMapping feature test success!";
    }

    public String chinese() {
        return "测试一下中文。";
    }

    @RequestMapping("/test-param")
    public String testParam(User user) {
        return "Hello world! @RequestMapping carry param feature test success! param user: " + user;
    }
}
