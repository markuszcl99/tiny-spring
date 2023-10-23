package com.tiny.spring.beans.factory.xml;

import com.tiny.spring.beans.factory.config.BeanDefinition;
import org.dom4j.Element;

/**
 * @author: markus
 * @date: 2023/10/24 12:02 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface NamespaceHandler {
    /**
     * 在解析自定义元素之前、构造之后被调用
     */
    void init();

    /**
     * 解析自定义标签，并生成相应的BeanDefinition
     * @param element
     * @param parserContext
     * @return
     */
    BeanDefinition parse(Element element, ParserContext parserContext);


}
