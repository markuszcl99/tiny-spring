package com.tiny.spring.test.mvc.editor;

import com.tiny.spring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: markus
 * @date: 2023/10/30 11:05 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class CustomDateEditor extends PropertyEditorSupport {

    private Class<Date> dateClass;
    private DateTimeFormatter dateTimeFormatter;
    private boolean allowEmpty;
    private Date value;

    public CustomDateEditor() {
        this(Date.class, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<?> dateClass) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class dateClass, boolean allowEmpty) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", allowEmpty);
    }

    public CustomDateEditor(Class dateClass, String pattern, boolean allowEmpty) throws IllegalArgumentException {
        this.dateClass = dateClass;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Date) value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getAsText() {
        Date value = this.value;
        if (value == null) {
            return "";
        } else {
            LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(dateTimeFormatter);
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            LocalDate localDate = LocalDate.parse(text);
            setValue(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }
}
