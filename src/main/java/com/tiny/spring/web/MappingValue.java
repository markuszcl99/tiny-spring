package com.tiny.spring.web;

/**
 * @author: markus
 * @date: 2023/10/16 10:42 PM
 * @Description: servlet.xml文件映射
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class MappingValue {
    private String uri;
    private String clazz;
    private String method;

    public MappingValue(String uri, String clazz, String method) {
        this.uri = uri;
        this.clazz = clazz;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
