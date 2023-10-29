package com.tiny.spring.beans.propertyeditors;

import java.beans.PropertyEditorSupport;

/**
 * @author: markus
 * @date: 2023/10/29 11:23 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class StringEditor extends PropertyEditorSupport {

    /**
     * 数据类型
     */
    private Class<? extends String> strClass;

    /**
     * 指定格式
     */
    private String strFormat;

    private boolean allowEmpty;

    private Object value;

    public StringEditor(Class<? extends String> strClass, boolean allowEmpty) {
        this(strClass, "", allowEmpty);
    }

    public StringEditor(Class<? extends String> strClass, String srtFormat, boolean allowEmpty) {
        this.strClass = strClass;
        this.strFormat = strFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getAsText() {
        return value.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text);
    }
}
