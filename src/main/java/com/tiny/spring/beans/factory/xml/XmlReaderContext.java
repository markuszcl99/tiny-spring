package com.tiny.spring.beans.factory.xml;

/**
 * @author: markus
 * @date: 2023/10/24 12:07 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class XmlReaderContext {
    private final XmlBeanDefinitionReader reader;
    private final NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlReaderContext(XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver) {
        this.reader = reader;
        this.namespaceHandlerResolver = namespaceHandlerResolver;
    }

    public XmlBeanDefinitionReader getReader() {
        return reader;
    }

    public NamespaceHandlerResolver getNamespaceHandlerResolver() {
        return namespaceHandlerResolver;
    }
}
