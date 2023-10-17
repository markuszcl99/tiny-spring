package com.tiny.spring.web.context;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author: markus
 * @date: 2023/10/17 11:32 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String pathname) {
        super(pathname);
    }


    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
