package com.tiny.spring.beans.factory;

/**
 * @author: markus
 * @date: 2023/10/8 11:02 PM
 * @Description: 属性值抽象
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
