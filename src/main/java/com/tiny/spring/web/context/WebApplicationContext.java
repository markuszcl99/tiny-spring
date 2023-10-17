package com.tiny.spring.web.context;


import com.tiny.spring.context.support.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author: markus
 * @date: 2023/10/17 11:27 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName();

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);
}
