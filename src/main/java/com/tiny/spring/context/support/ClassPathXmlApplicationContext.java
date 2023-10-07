package com.tiny.spring.context.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.SimpleBeanFactory;
import com.tiny.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.tiny.spring.core.io.ClassPathXmlResource;
import com.tiny.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/7 8:16 PM
 * @Description: 基于xml的Spring应用上下文
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String pathname) {
        Resource resource = new ClassPathXmlResource(pathname);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    /**
     * 对外提供的方法，让外部程序获取Bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

}
