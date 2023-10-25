package com.tiny.spring.beans.factory.xml;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.beans.factory.config.BeanDefinition;
import org.dom4j.Element;

/**
 * @author: markus
 * @date: 2023/10/25 10:44 PM
 * @Description: BeanDefinition 解析器 接口定义
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface BeanDefinitionParser {

    @Nullable
    BeanDefinition parse(Element element, ParserContext parserContext);
}
