package com.tiny.spring.web.servlet.view;

import com.tiny.spring.web.servlet.View;
import com.tiny.spring.web.servlet.ViewResolver;

/**
 * @author: markus
 * @date: 2023/10/31 11:46 PM
 * @Description: 解析用户的配置，拿到前端展示的视图
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class InternalResourceViewResolver implements ViewResolver {

    private Class<?> viewClass = null;
    private String viewClassName = "";
    private String prefix = "/jsp/";
    private String suffix = ".jsp";
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        Class<?> viewClass = getViewClass();

        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());

        String contentType = getContentType();
        view.setContentType(contentType);
        return view;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class clz = null;
        try {
            clz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }

    public void setViewClass(Class viewClass) {
        this.viewClass = viewClass;
    }

    protected Class getViewClass() {
        return this.viewClass;
    }

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    protected String getPrefix() {
        return this.prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    protected String getSuffix() {
        return this.suffix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String getContentType() {
        return this.contentType;
    }
}
