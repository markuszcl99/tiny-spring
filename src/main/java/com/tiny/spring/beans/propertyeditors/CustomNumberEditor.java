package com.tiny.spring.beans.propertyeditors;

import com.tiny.spring.util.NumberUtils;
import com.tiny.spring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author: markus
 * @date: 2023/10/29 11:03 PM
 * @Description: Number类型转换
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class CustomNumberEditor extends PropertyEditorSupport {
    /**
     * 数据类型
     */
    private Class<? extends Number> numberClass;

    /**
     * 指定格式
     */
    private NumberFormat numberFormat;

    private boolean allowEmpty;

    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (this.numberFormat != null) {
            // 给定格式器
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            this.value = NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass);
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getAsText() {
        Object value = this.value;
        if (value == null) {
            return "";
        }
        if (this.numberFormat != null) {
            return this.numberFormat.format(value);
        } else {
            return value.toString();
        }
    }
}
