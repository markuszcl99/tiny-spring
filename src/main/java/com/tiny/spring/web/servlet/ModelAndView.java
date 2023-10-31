package com.tiny.spring.web.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/31 11:24 PM
 * @Description: 包装后端返回的数据Model以及前端页面View
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ModelAndView {
    private Object view;
    private Map model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(String viewName, Map modelData) {
        this.view = viewName;
        if (modelData != null) {
            addAllAttributes(modelData);
        }
    }

    public ModelAndView(View view, Map model) {
        this.view = view;
        if (model != null) {
            addAllAttributes(model);
        }
    }

    public ModelAndView(String viewName, String modelName, Object modelObject) {
        this.view = viewName;
        addObject(modelName, modelObject);
    }

    public ModelAndView(View view, String modelName, Object modelObject) {
        this.view = view;
        addObject(modelName, modelObject);
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }

    public boolean hasView() {
        return (this.view != null);
    }

    public boolean isReference() {
        return (this.view instanceof String);
    }

    public Map getModel() {
        return this.model;
    }

    private void addAllAttributes(Map modelData) {
        if (modelData != null) {
            model.putAll(modelData);
        }
    }

    public void addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        addAttribute(attributeName, attributeValue);
        return this;
    }
}
