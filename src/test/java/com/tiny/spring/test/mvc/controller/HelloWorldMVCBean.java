package com.tiny.spring.test.mvc.controller;

import com.tiny.spring.web.RequestMapping;

/**
 * @author: markus
 * @date: 2023/10/16 10:41 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class HelloWorldMVCBean {

    public String doGet() {
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }

    @RequestMapping("/test")
    public String doTest() {
        return "Hello world! @RequestMapping feature test success!";
    }
}
