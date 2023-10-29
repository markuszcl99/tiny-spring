package com.tiny.spring.web.servlet;

import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.core.io.support.PropertiesLoaderUtils;
import com.tiny.spring.util.ClassUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author: markus
 * @date: 2023/10/16 10:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DispatcherServlet extends FrameworkServlet {

    private static final Properties defaultStrategies;

    static {
        String path = DispatcherServlet.class.getClassLoader().getResource("DispatcherServlet.properties").getPath();
        defaultStrategies = PropertiesLoaderUtils.loadProperties(path);
    }

    /**
     * 处理url到controller的映射的组件
     */
    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    /**
     * 执行controller的适配器的组件
     */
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void onRefresh(ApplicationContext context) {
        initHandlerMapping(context);
        initHandlerAdapters(context);
    }

    private void initHandlerAdapters(ApplicationContext context) {
        this.handlerAdapters = null;
        Map<String, HandlerAdapter> handlerAdapterBeanMap = context.getBeansOfType(HandlerAdapter.class);
        if (!handlerAdapterBeanMap.isEmpty()) {
            this.handlerAdapters = new ArrayList<>(handlerAdapterBeanMap.values());
        }
        if (this.handlerAdapters == null) {
            this.handlerAdapters = getDefaultStrategies(context, HandlerAdapter.class);
        }
    }

    private void initHandlerMapping(ApplicationContext context) {
        this.handlerMappings = null;
        // spring源码是这样做：首先会在spring容器中查找HandlerMapping，如果找不到则会去DispatcherServlet.properties中加载默认配置
        // 1. 先去容器找一下是否有配置
        Map<String, HandlerMapping> handlerMappingBeanMap = context.getBeansOfType(HandlerMapping.class);
        if (!handlerMappingBeanMap.isEmpty()) {
            this.handlerMappings = new ArrayList<>(handlerMappingBeanMap.values());
        }
        // 2. 如果handlerMappings 还是为空，则去默认配置中加载
        if (handlerMappings == null) {
            this.handlerMappings = getDefaultStrategies(context, HandlerMapping.class);
        }
    }

    protected <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value == null) {
            return new LinkedList<>();
        }
        String[] splits = value.split(",");
        List<T> strategies = new LinkedList<>();
        for (String split : splits) {
            try {
                Class<?> clazz = Class.forName(split);
                Object strategy = this.createDefaultStrategy(context, clazz);
                strategies.add((T) strategy);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return strategies;
    }

    private Object createDefaultStrategy(ApplicationContext context, Class<?> clazz) {
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}

