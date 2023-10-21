//package com.tiny.spring.web.context;
//
//import com.tiny.spring.beans.BeansException;
//import com.tiny.spring.beans.factory.config.BeanDefinition;
//import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
//import com.tiny.spring.beans.factory.xml.XmlBeanDefinitionReader;
//import com.tiny.spring.context.support.AbstractApplicationContext;
//import com.tiny.spring.context.support.ClassPathXmlApplicationContext;
//import com.tiny.spring.core.io.ClassPathXmlResource;
//import com.tiny.spring.core.io.Resource;
//import com.tiny.spring.web.XmlScanComponentHelper;
//
//import javax.servlet.ServletContext;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author: markus
// * @date: 2023/10/17 11:32 PM
// * @Description: 四不像 随便写写
// * @Blog: https://markuszhang.com
// * It's my honor to share what I've learned with you!
// */
//public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements ConfigurableWebApplicationContext {
//
//    private ServletContext servletContext;
//    /**
//     * 这里是自己实现的，spring源码不这样，todo 后续进行调整
//     */
//    private String configLocation;
//
//    public AnnotationConfigWebApplicationContext(String configLocation) {
//        this.configLocation = configLocation;
//        // 刷新上下文
//        refresh();
//    }
//
//
//    @Override
//    public ServletContext getServletContext() {
//        return this.servletContext;
//    }
//
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        this.servletContext = servletContext;
//    }
//
//    @Override
//    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
//        // 这块应该是加载IoC容器中的bean，并不是MVC容器中的
//        // 我先按照xml的方式进行加载 todo 后续看情况是否调整 反正主要目的就是把配置的BeanDefinition加载至IoC容器中 🐶
//        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
//        Resource resource = new ClassPathXmlResource(configLocation);
//        beanDefinitionReader.loadBeanDefinitions(resource);
//    }
//
//    private void doLoadBeanDefinitions(List<String> controllerNames, DefaultListableBeanFactory beanFactory) {
//        for (String controller : controllerNames) {
//            String beanId = controller;
//            String beanClassName = controller;
//            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
//            beanFactory.registerBeanDefinition(beanId, beanDefinition);
//        }
//    }
//
//    private List<String> scanPackages(List<String> packageNames) {
//        List<String> tempControllerNames = new ArrayList<>();
//        for (String packageName : packageNames) {
//            tempControllerNames.addAll(scanPackage(packageName));
//        }
//        return tempControllerNames;
//    }
//
//    private List<String> scanPackage(String packageName) {
//        List<String> tempControllerNames = new ArrayList<>();
//        URI uri = null;
//        try {
//            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        File dir = new File(uri);
//        for (File file : dir.listFiles()) {
//            if (file.isDirectory()) {
//                scanPackage(packageName + "." + file.getName());
//            } else {
//                String controllerName = packageName + "." + file.getName().replace(".class", "");
//                tempControllerNames.add(controllerName);
//            }
//        }
//        return tempControllerNames;
//    }
//}
