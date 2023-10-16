package com.tiny.spring.web;

import com.tiny.spring.core.io.ClassPathXmlResource;
import com.tiny.spring.core.io.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/16 10:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DispatcherServlet extends HttpServlet {

    private String sContextConfigLocation;
    private Map<String, MappingValue> mappingValues;
    private Map<String, Class<?>> mappingClazz = new HashMap<>();
    private Map<String, Object> mappingObjects = new HashMap<>();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Resource resource = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(resource);
        Refresh();
    }

    /**
     * 读取mappingValues中的Bean定义
     */
    protected void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClazz();
            Object obj = null;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
                obj = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            mappingClazz.put(id, clazz);
            mappingObjects.put(id, obj);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求的path
        String sPath = request.getServletPath();
        // 如果mappingValue中没有定义这样的路径，则直接返回，不处理
        if (this.mappingValues.get(sPath) == null) {
            return;
        }

        Class<?> clazz = this.mappingClazz.get(sPath);
        Object obj = this.mappingObjects.get(sPath);
        String methodName = this.mappingValues.get(sPath).getMethod();
        Object invokeResult = null;
        try {
            Method method = clazz.getMethod(methodName);
            invokeResult = method.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (invokeResult != null) {
            // 防止中文乱码
            response.setContentType("text/html;charset=UTF-8");
            // 将响应结果写入到response中
            response.getWriter().append(invokeResult.toString());
        }
    }

}
