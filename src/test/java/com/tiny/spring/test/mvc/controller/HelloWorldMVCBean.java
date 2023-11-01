package com.tiny.spring.test.mvc.controller;

import com.tiny.spring.stereotype.Controller;
import com.tiny.spring.test.mvc.domain.User;
import com.tiny.spring.web.bind.annotation.RequestMapping;
import com.tiny.spring.web.bind.annotation.ResponseBody;
import com.tiny.spring.web.servlet.ModelAndView;

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
    @ResponseBody
    public User testParam(User user) {
        return user;
    }

    @RequestMapping("/test-view-name")
    public String testViewName(User user) {
        return "success";
    }
}
