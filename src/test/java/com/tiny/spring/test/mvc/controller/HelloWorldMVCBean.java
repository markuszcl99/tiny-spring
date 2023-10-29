package com.tiny.spring.test.mvc.controller;

import com.tiny.spring.stereotype.Controller;

/**
 * @author: markus
 * @date: 2023/10/16 10:41 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Controller
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
}
