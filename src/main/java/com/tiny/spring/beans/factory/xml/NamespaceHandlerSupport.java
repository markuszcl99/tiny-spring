package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.config.BeanDefinition;
import org.dom4j.Element;

/**
 * @author: markus
 * @date: 2023/10/24 12:17 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class NamespaceHandlerSupport implements NamespaceHandler {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}
