package com.tiny.spring.web.servlet;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.context.support.ApplicationContext;
import com.tiny.spring.web.context.ConfigurableWebApplicationContext;
import com.tiny.spring.web.context.WebApplicationContext;
import com.tiny.spring.web.context.XmlWebApplicationContext;
import com.tiny.spring.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: markus
 * @date: 2023/10/20 11:54 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class FrameworkServlet extends HttpServletBean {

    public static final Class<?> DEFAULT_CONTEXT_CLASS = XmlWebApplicationContext.class;

    private Class<?> contextClass = DEFAULT_CONTEXT_CLASS;

    @Nullable
    protected String contextConfigLocation;

    /**
     * mvc 容器
     */
    @Nullable
    private WebApplicationContext webApplicationContext;

    @Override
    protected void initServletBean() {
        try {
            this.webApplicationContext = initWebApplicationContext();
            initFrameworkServlet();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    protected void initFrameworkServlet() throws ServletException {
    }

    private WebApplicationContext initWebApplicationContext() {
        WebApplicationContext rootApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        WebApplicationContext wac = null;
        // 中间有很多判断逻辑，我们先不实现

        // 我们默认这个WebApplicationContext就是null，直接创建
        wac = createWebApplicationContext(rootApplicationContext);
        onRefresh(wac);
        return wac;
    }

    protected void onRefresh(ApplicationContext context) {

    }

    private WebApplicationContext createWebApplicationContext(@Nullable WebApplicationContext rootApplicationContext) {
        Class<?> contextClass = getContextClass();
        ConfigurableWebApplicationContext wac = null;
        try {
            wac = (ConfigurableWebApplicationContext) contextClass.newInstance();
            // 设置父容器--IoC容器
            wac.setParent(rootApplicationContext);
            wac.setServletContext(this.getServletContext());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // 刷新启动上下文
        configureAndRefreshWebApplicationContext(wac);

        return wac;
    }

    public void setContextConfigLocation(@Nullable String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    private String getContextConfigLocation() {
        return this.contextConfigLocation;
    }

    private void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        // 配置一些属性，我们这里先不管
        String configLocation = getContextConfigLocation();
        if (configLocation != null) {
            wac.setConfigLocation(configLocation);
        }
        // 直接启动上下文
        wac.refresh();
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }


    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) {
        // 1. 处理其他事情

        // 2. 处理请求
        try {
            doService(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 处理其他事情
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
