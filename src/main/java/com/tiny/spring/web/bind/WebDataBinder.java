package com.tiny.spring.web.bind;

import com.tiny.spring.beans.BeanWrapperImpl;
import com.tiny.spring.beans.PropertyEditorRegistrySupport;
import com.tiny.spring.beans.factory.PropertyValues;
import com.tiny.spring.core.MethodParameter;
import com.tiny.spring.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditor;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/29 6:59 PM
 * @Description: 数据绑定
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class WebDataBinder {
    private Object target;
    private Class<?> type;
    private String objectName;

    private BeanWrapperImpl propertyAccessor;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
        this.type = target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request) {
        PropertyValues pvs = assignParameters(request);
        addBindValues(pvs, request);
        doBind(pvs);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    protected void addBindValues(PropertyValues pvs, HttpServletRequest request) {

    }

    private void doBind(PropertyValues pvs) {
        applyPropertyValues(pvs);
    }

    private void applyPropertyValues(PropertyValues pvs) {
        getPropertyAccessor().setPropertyValues(pvs);
    }

    protected BeanWrapperImpl getPropertyAccessor() {
        return this.propertyAccessor;
    }

    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }
}
