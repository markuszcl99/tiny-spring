package com.tiny.spring.test.bean;

/**
 * @author: markus
 * @date: 2023/10/8 11:43 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class A {
    private String property1;
    private String property2;
    private String property3;

    private Integer age;

    public A() {
    }

    public A(String property1, String property2, String property3) {
        this.property1 = property1;
        this.property2 = property2;
        this.property3 = property3;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "A{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", property3='" + property3 + '\'' +
                ", age=" + age +
                '}';
    }
}
