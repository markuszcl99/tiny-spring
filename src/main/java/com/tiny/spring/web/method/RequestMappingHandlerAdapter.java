package com.tiny.spring.web.method;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.InitializingBean;
import com.tiny.spring.context.ApplicationContextAware;
import com.tiny.spring.context.ConfigurableApplicationContext;
import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.web.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/10/29 2:36 PM
 * @Description: handler适配器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter, ApplicationContextAware, InitializingBean {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, Object handler) {
        handlerInternal(request, response, (HandlerMethod) handler);
    }

    private void handlerInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object result = null;
        try {
            result = method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().append(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        }
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
