package com.tiny.spring.beans.factory.config;

/**
 * @author: markus
 * @date: 2023/10/7 8:14 PM
 * @Description: Bean配置元信息
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanDefinition {
    private String id;
    private String className;

    public BeanDefinition() {
    }

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
