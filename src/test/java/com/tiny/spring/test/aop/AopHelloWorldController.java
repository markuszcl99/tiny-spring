package com.tiny.spring.test.aop;

import com.tiny.spring.beans.factory.annotation.Autowired;
import com.tiny.spring.stereotype.Controller;
import com.tiny.spring.web.bind.annotation.RequestMapping;
import com.tiny.spring.web.bind.annotation.ResponseBody;


/**
 * @author: markus
 * @date: 2023/11/11 10:05 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Controller
@RequestMapping("/aop")
public class AopHelloWorldController {

    @Autowired
    IAction action;

    @RequestMapping("/action")
    public String doTestAop() {
        action.doAction();
        return "test aop, hello world";
    }
}
