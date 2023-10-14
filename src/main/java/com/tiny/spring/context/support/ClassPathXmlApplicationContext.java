package com.tiny.spring.context.support;

import com.tiny.spring.beans.BeansException;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.tiny.spring.core.io.ClassPathXmlResource;
import com.tiny.spring.core.io.Resource;

/**
 * @author: markus
 * @date: 2023/10/7 8:16 PM
 * @Description: 基于xml的Spring应用上下文
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private Resource resources;

    public ClassPathXmlApplicationContext(String pathname) throws BeansException {
        this(pathname, true);
    }

    public ClassPathXmlApplicationContext(String pathname, boolean isRefresh) throws BeansException {
        this.resources = new ClassPathXmlResource(pathname);
        if (isRefresh) {
            refresh();
        }
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(this.resources);
    }

}
