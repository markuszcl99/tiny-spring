package com.tiny.spring.web.method;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.InitializingBean;
import com.tiny.spring.context.ApplicationContextAware;
import com.tiny.spring.context.ConfigurableApplicationContext;
import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.core.annotation.AnnotatedElementUtils;
import com.tiny.spring.web.bind.WebDataBinder;
import com.tiny.spring.web.bind.WebDataBinderFactory;
import com.tiny.spring.web.bind.annotation.ResponseBody;
import com.tiny.spring.web.bind.support.WebBindingInitializer;
import com.tiny.spring.web.http.convert.HttpMessageConverter;
import com.tiny.spring.web.servlet.HandlerAdapter;
import com.tiny.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 请求参数 数据绑定
     */
    private WebBindingInitializer webBindingInitializer;
    /**
     * 将返回的数据序列化为json信息返回给前端
     */
    private HttpMessageConverter httpMessageConverter;

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handlerInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handlerInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
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
        Object result = method.invoke(obj, methodParamObjs);
        Object returnType = method.getReturnType();

        ModelAndView mv = null;
        // 返回数据
        if (AnnotatedElementUtils.hasAnnotation(method, ResponseBody.class)) {
            // 将返回结果序列化并写入到response中
            this.httpMessageConverter.write(result, response);
        } else if (returnType == Void.class) {

        } else {
            // 返回页面
            if (result instanceof ModelAndView) {
                mv = (ModelAndView) result;
            } else if (result instanceof String) {
                String viewName = (String) result;
                mv = new ModelAndView(viewName);
            }
        }
        return mv;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.webBindingInitializer = (WebBindingInitializer) applicationContext.getBean("webBindingInitializer");
        this.httpMessageConverter = (HttpMessageConverter) applicationContext.getBean("httpMessageConverter");
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
