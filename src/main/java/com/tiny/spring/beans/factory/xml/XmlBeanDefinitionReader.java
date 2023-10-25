package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.*;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import com.tiny.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiny.spring.core.io.Resource;
import org.dom4j.Element;
import org.w3c.dom.Node;

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

    BeanDefinitionParserDelegate delegate;

    public static final String BEAN_ELEMENT = BeanDefinitionParserDelegate.BEAN_ELEMENT;


    public XmlBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.delegate = new BeanDefinitionParserDelegate(new XmlReaderContext(this, new DefaultNamespaceHandlerResolver()));
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            if (delegate.isDefaultNamespace(element)) {
                parseDefaultElement(element, delegate);
            } else {
                delegate.parseCustomElement(element);
            }
        }
    }

    private void parseDefaultElement(Element element, BeanDefinitionParserDelegate delegate) {
        if (delegate.nodeNameEquals(element, BEAN_ELEMENT)) {
            processBeanDefinition(element, delegate);
        }
    }

    private void processBeanDefinition(Element element, BeanDefinitionParserDelegate delegate) {
        BeanDefinition beanDefinition = delegate.parseBeanDefinitionElement(element);
        this.beanFactory.registerBeanDefinition(beanDefinition.getId(), beanDefinition);
    }

    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public BeanDefinitionParserDelegate getDelegate() {
        return delegate;
    }
}
