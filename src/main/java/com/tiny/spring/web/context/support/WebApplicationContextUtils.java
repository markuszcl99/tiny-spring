package com.tiny.spring.web.context.support;

import com.tiny.spring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author: markus
 * @date: 2023/10/21 12:00 AM
 * @Description: WebApplicationContext相关工具类
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class WebApplicationContextUtils {
    public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
        return getWebApplicationContext(sc, WebApplicationContext.class.getName() + ".ROOT");
    }

    public static WebApplicationContext getWebApplicationContext(ServletContext sc, String attrName) {
        Object attr = sc.getAttribute(attrName);
        if (attr == null) {
            return null;
        }
        if (!(attr instanceof WebApplicationContext)) {
            throw new IllegalStateException("Context attribute is not of type WebApplicationContext: " + attr);
        }
        return (WebApplicationContext) attr;
    }
}
