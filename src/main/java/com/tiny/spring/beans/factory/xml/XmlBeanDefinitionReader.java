package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.*;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.core.io.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: markus
 * @date: 2023/10/7 8:50 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlBeanDefinitionReader {
    DefaultListableBeanFactory beanFactory;

    public XmlBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String className = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            // 存储属性中的引用对象 bean的id
            List<String> refs = new ArrayList<>();
            for (Element propertyElement : propertyElements) {
                String pType = propertyElement.attributeValue("type");
                String pName = propertyElement.attributeValue("name");
                String pValue = propertyElement.attributeValue("value");
                String pRef = propertyElement.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                pvs.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(pvs);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            // 处理构造器参数
            List<Element> constructorArgumentElements = element.elements("constructor-arg");
            ArgumentValues avs = new ArgumentValues();
            for (Element constructorArgumentElement : constructorArgumentElements) {
                String aType = constructorArgumentElement.attributeValue("type");
                String aName = constructorArgumentElement.attributeValue("name");
                String aValue = constructorArgumentElement.attributeValue("value");
                avs.addArgumentValue(new ArgumentValue(aValue, aType, aName));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
