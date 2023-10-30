package com.tiny.spring.web.context;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.tiny.spring.context.support.AbstractApplicationContext;
import com.tiny.spring.core.io.ClassPathXmlResource;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/10/17 11:58 PM
 * @Description: xml驱动 加载IoC容器+MVC容器
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlWebApplicationContext extends AbstractApplicationContext implements ConfigurableWebApplicationContext {

    private ServletContext servletContext;
    @Nullable
    private String configLocation;

    public XmlWebApplicationContext() {
    }

    public XmlWebApplicationContext(String pathname) {
        this.configLocation = pathname;
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
//        // 加载controller bean
//        if (configLocation != null) {
//            try {
//                URL xmlPath = this.getServletContext().getResource(configLocation);
//                List<String> controllerPackageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
//                List<String> controllerNames = scanPackages(controllerPackageNames);
//                doLoadBeanDefinition(controllerNames);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathXmlResource(configLocation));
    }

    public void doLoadBeanDefinition(List<String> controllerNames) {
        for (String controllerName : controllerNames) {
            String beanId = controllerName;
            String beanClassName = controllerName;
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            getBeanFactory().registerBeanDefinition(beanId, beanDefinition);
        }
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
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
