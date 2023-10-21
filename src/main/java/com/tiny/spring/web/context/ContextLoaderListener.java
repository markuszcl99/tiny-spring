package com.tiny.spring.web.context;

import com.tiny.spring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: markus
 * @date: 2023/10/17 11:25 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    private WebApplicationContext context;

    public ContextLoaderListener() {

    }

    public ContextLoaderListener(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void initWebApplicationContext(ServletContext servletContext) {
        // 先创建IoC容器
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        // 先启动Listener中的webApplicationContext的上下文，将IoC中的Bean先给注册上
        WebApplicationContext wac = new XmlWebApplicationContext(sContextLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
        configureAndRefreshWebApplicationContext((ConfigurableWebApplicationContext) wac);
    }

    private void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        // 直接启动上下文
        wac.refresh();
    }
}
