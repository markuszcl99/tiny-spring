package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.config.BeanDefinition;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/10/24 12:17 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class NamespaceHandlerSupport implements NamespaceHandler {

    private final Map<String, BeanDefinitionParser> parsers = new HashMap<>();

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String elementName = element.getName();
        BeanDefinitionParser parser = this.parsers.get(elementName);
        return parser.parse(element, parserContext);
    }

    public void registerBeanDefinitionParser(String elementName, BeanDefinitionParser parser) {
        this.parsers.put(elementName, parser);
    }
}
