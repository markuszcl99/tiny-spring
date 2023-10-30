package com.tiny.spring.web.method;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.InitializingBean;
import com.tiny.spring.context.ApplicationContextAware;
import com.tiny.spring.context.ConfigurableApplicationContext;
import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.web.bind.WebDataBinder;
import com.tiny.spring.web.bind.WebDataBinderFactory;
import com.tiny.spring.web.bind.support.WebBindingInitializer;
import com.tiny.spring.web.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author: markus
 * @date: 2023/10/29 2:36 PM
 * @Description: handler适配器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter, ApplicationContextAware, InitializingBean {

    private ConfigurableApplicationContext applicationContext;

    private WebBindingInitializer webBindingInitializer;

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws InstantiationException, IllegalAccessException {
        handlerInternal(request, response, (HandlerMethod) handler);
    }

    private void handlerInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws InstantiationException, IllegalAccessException {
        WebDataBinderFactory webDataBinderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handler.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder webDataBinder = webDataBinderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webBindingInitializer.initBinder(webDataBinder);
            webDataBinder.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }

        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object result = null;
        try {
            result = method.invoke(obj, methodParamObjs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().append(result.toString());
            // 防止中文乱码
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.webBindingInitializer = (WebBindingInitializer) applicationContext.getBean("webBindingInitializer");
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
