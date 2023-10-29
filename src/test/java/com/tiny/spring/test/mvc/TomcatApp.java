package com.tiny.spring.test.mvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * @author: markus
 * @date: 2023/10/16 11:14 PM
 * @Description: Tomcat启动
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class TomcatApp {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("Hello World!");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String classPath = classLoader.getResource("").getPath();
        System.out.println("classpath is [" + classPath + "]");

        Tomcat tomcat = new Tomcat();
        String webappDirLocation = "webapp";
        StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);
        tomcat.start();
        tomcat.getServer().await();
    }
}
