package com.tiny.spring.beans.factory;

/**
 * @author: markus
 * @date: 2023/10/8 11:04 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isRef() {
        return isRef;
    }
}
