package com.tiny.spring.web.servlet;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.core.io.support.PropertiesLoaderUtils;
import com.tiny.spring.util.ClassUtils;
import com.tiny.spring.util.StringUtils;

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

    /**
     * 视图解析器
     */
    private List<ViewResolver> viewResolvers;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void onRefresh(ApplicationContext context) {
        initHandlerMapping(context);
        initHandlerAdapters(context);
        initViewResolvers(context);
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecutionChain mappedHandler = null;
        mappedHandler = getHandler(request);
        if (mappedHandler == null) {
            return;
        }
        HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
        ModelAndView mv = ha.handler(request, response, mappedHandler.getHandler());
        if (mv != null) {
            // 不为空的话，就渲染一下
            render(mv, request, response);
        }

    }

    private void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = mv.getViewName();
        if (StringUtils.hasText(viewName)) {
            View view = getViewResolver(viewName);
            if (view == null) {
                throw new ServletException("Could not resolve view with name '" + mv.getViewName() + "' in servlet with name '" + this.getServletName() + "'");
            }
            view.render(mv.getModel(), request, response);
        }
    }

    private View getViewResolver(String viewName) throws Exception {
        for (ViewResolver viewResolver : this.viewResolvers) {
            View view = viewResolver.resolveViewName(viewName);
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new ServletException("No adapter for handler [" + handler +
                "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
    }

    @Nullable
    private HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            for (HandlerMapping handlerMapping : handlerMappings) {
                HandlerExecutionChain handler = handlerMapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    private void initViewResolvers(ApplicationContext context) {
        this.viewResolvers = null;
        Map<String, ViewResolver> viewResolverMap = context.getBeansOfType(ViewResolver.class);
        if (!viewResolverMap.isEmpty()) {
            this.viewResolvers = new ArrayList<>(viewResolverMap.values());
        }
        if (this.viewResolvers == null) {
            this.viewResolvers = getDefaultStrategies(context, ViewResolver.class);
        }
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

}

