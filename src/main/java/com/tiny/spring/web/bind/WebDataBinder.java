package com.tiny.spring.web.bind;

import com.tiny.spring.beans.BeanWrapperImpl;
import com.tiny.spring.beans.factory.PropertyValues;
import com.tiny.spring.core.MethodParameter;
import com.tiny.spring.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
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


    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
        this.type = target.getClass();
    }

    public void bind(HttpServletRequest request) {
        PropertyValues pvs = assignParameters(request);
        addBindValues(pvs, request);
        doBind(pvs);
    }

    public <T> T convertIfNecessary(Object value, Class<T> requiredType, Parameter parameter) {
        // 进行类型转换
        return null;
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
        return new BeanWrapperImpl(this.target);
    }

    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }
}
