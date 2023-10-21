package com.tiny.spring.web.servlet;

import com.tiny.spring.util.FieldUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @author: markus
 * @date: 2023/10/20 11:56 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class HttpServletBean extends HttpServlet {
    @Override
    public void init() throws ServletException {
        // 1. 设置一些Servlet属性信息，这里我们先不管
        //  1.1 spring源码是通过反射来完成，我们这里先直接将具体的字段进行赋值就好了，简单来先
        ServletConfig servletConfig = getServletConfig();
        Enumeration<String> enumeration = servletConfig.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String param = enumeration.nextElement();
            String value = servletConfig.getInitParameter(param);
            try {
                Field field = FieldUtils.getFiled(this.getClass(), param);
                if (field != null) {
                    // 设置可访问
                    field.setAccessible(true);
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 2. 让子类去决定如何初始化
        initServletBean();
    }

    protected void initServletBean() {

    }
}
