package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.BeanFactory;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.SimpleBeanFactory;
import com.tiny.spring.core.io.Resource;
import org.dom4j.Element;

/**
 * @author: markus
 * @date: 2023/10/7 8:50 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlBeanDefinitionReader {
    SimpleBeanFactory beanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String className = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
