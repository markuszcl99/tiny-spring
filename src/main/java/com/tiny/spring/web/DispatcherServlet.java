package com.tiny.spring.web;

import com.tiny.spring.core.io.ClassPathXmlResource;
import com.tiny.spring.core.io.Resource;
import com.tiny.spring.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * @author: markus
 * @date: 2023/10/16 10:51 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * 用于存储需要扫描的package列表
     */
    private List<String> packageNames = new ArrayList<>();
    /**
     * 用于存储controller的名称与对象的映射关系
     */
    private Map<String, Object> controllerObjects = new HashMap<>();
    /**
     * 用于存储controller名称数组列表
     */
    private List<String> controllerNames = new ArrayList<>();
    /**
     * 用于存储controller 名称与类的映射关系
     */
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    /**
     * 保存自定义的@RequestMapping名称（URL的名称）的列表
     */
    private Set<String> urlMappingNames = new HashSet<>();
    /**
     * 保存URL名称与对象的映射关系
     */
    private Map<String, Object> mappingObjects = new HashMap<>();
    /**
     * 保存URL名称与方法的映射关系
     */
    private Map<String, Method> mappingMethods = new HashMap<>();
    private String sContextConfigLocation;

    private WebApplicationContext webApplicationContext;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        Refresh();
    }

    /**
     * 读取mappingValues中的Bean定义
     */
    protected void Refresh() {
        initController();
        initMapping();
    }

    /**
     * 对扫描的每一个类进行加载和实例化
     */
    protected void initController() {
        //扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName); //加载类
                this.controllerClasses.put(controllerName, clz);
            } catch (Exception e) {
            }
            try {
                obj = clz.newInstance(); //实例化bean
                this.controllerObjects.put(controllerName, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 初始化URL映射
     */
    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object controllerObject = this.controllerObjects.get(controllerName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String methodName = method.getName();
                    // 建立方法名和URL的映射
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.mappingObjects.put(urlMapping, controllerObject);
                    this.mappingMethods.put(urlMapping, method);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取请求的path
        String sPath = request.getServletPath();
        if (!urlMappingNames.contains(sPath)) {
            return;
        }

        Object obj = this.mappingObjects.get(sPath);
        Object invokeResult = null;

        try {
            Method method = this.mappingMethods.get(sPath);
            invokeResult = method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (invokeResult != null) {
            // 防止中文乱码
            response.setContentType("text/html;charset=UTF-8");
            // 将响应结果写入到response中
            response.getWriter().append(invokeResult.toString());
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }
}

