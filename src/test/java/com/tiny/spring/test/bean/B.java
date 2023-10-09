package com.tiny.spring.test.bean;

/**
 * @author: markus
 * @date: 2023/10/9 10:20 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class B {
    private String property1;
    private String property2;
    private A a;

    public B() {
    }

    public B(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "B{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                '}';
    }
}
