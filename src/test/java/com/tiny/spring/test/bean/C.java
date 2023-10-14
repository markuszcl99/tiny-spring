package com.tiny.spring.test.bean;

import com.tiny.spring.beans.factory.annotation.Autowired;

/**
 * @author: markus
 * @date: 2023/10/14 5:55 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class C {
    @Autowired
    private B b1;

    public B getB1() {
        return b1;
    }

    public void setB1(B b1) {
        this.b1 = b1;
    }

    @Override
    public String toString() {
        return "C{" +
                "b1=" + b1 +
                '}';
    }
}
